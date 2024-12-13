package com.yupi.yunpicturebackend.service;

import cn.hutool.core.util.StrUtil;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.exception.ThrowUtils;
import com.yupi.yunpicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.vo.LoginUserVO;
import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletRequest;

/**
* @author 12600kf
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-12-12 16:40:49
*/
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String userPassword,String checkPassword);
    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     * 定义这个类是要返回给前端的，但是不是所有数据都要返回给前端，所以要对一些数据进行脱敏，所以定义LoginUserVO类
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    //对密码进行加密,是一个公共的方法
    String getEncryptPassword(String password);
    //根据原user获得脱敏后的用户登录信息
    LoginUserVO getLoginUserVO(User user);
}