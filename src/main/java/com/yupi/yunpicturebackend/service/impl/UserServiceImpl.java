package com.yupi.yunpicturebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yunpicturebackend.constant.UserConstant;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.exception.ThrowUtils;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.enums.UserRoleEnum;
import com.yupi.yunpicturebackend.model.vo.LoginUserVO;
import com.yupi.yunpicturebackend.service.UserService;
import com.yupi.yunpicturebackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @author 12600kf
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-12-12 16:40:49
*/
@Service
@Slf4j
//抛出异常的时候打印日志
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验参数
        if (StrUtil.hasBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不一致");
        }
        ThrowUtils.throwIf(userPassword.length()<8||checkPassword.length()<8,ErrorCode.PARAMS_ERROR,"用户密码过短");
        //2.检查用户账号是否和数据库中已有数据重复，这种方式是根据记录出现的次数进行查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if (count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR ,"账号重复");
        }
        //3.加密密码
        String encryptPassword = getEncryptPassword(userPassword);
        //4.插入数据到数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        Boolean saveResult = this.save(user);
        if (!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"注册失败，数据库错误");
        }
        //虽然没有设置user的id，但是mybatis的save方法有主键回填机制，所以会自动填充userid
        return user.getId() ;
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StrUtil.hasBlank(userAccount,userPassword)){
        throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
    }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }

        ThrowUtils.throwIf(userPassword.length()<8,ErrorCode.PARAMS_ERROR,"用户密码错误");
        //2.对用户传输的密码进行加密，这样才能和数据库中加密后的密码进行校验。
        String encryptPassword = getEncryptPassword(userPassword);
        //3.查寻数据库中用户数据是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user==null){
            //抛出一个异常要new一个异常
            log.info("user login failed,userAccount can not match userPassword");
            throw new  BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在或者密码错误");
        }
        //4.保存用户的登录态 根据请求拿到独立的用户空间，然后设置登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);
        return this.getLoginUserVO(user);
    }

    @Override
    public String getEncryptPassword(String userPassword) {
        //使用md5单向加密，如果对称加密会使得对手知道密钥破解密码。同时使用加盐的操作混淆加密
        final String salt="yupi";
        return DigestUtils.md5DigestAsHex((salt+userPassword).getBytes());
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user==null){
            return null;
        }
         LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user,loginUserVO);
        return loginUserVO ;
    }
}




