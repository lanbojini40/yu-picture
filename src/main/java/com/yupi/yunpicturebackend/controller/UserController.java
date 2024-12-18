package com.yupi.yunpicturebackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yunpicturebackend.annotation.AuthCheck;
import com.yupi.yunpicturebackend.common.BaseResponse;
import com.yupi.yunpicturebackend.common.DeleteRequest;
import com.yupi.yunpicturebackend.common.ResultUtils;
import com.yupi.yunpicturebackend.constant.UserConstant;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.exception.ThrowUtils;
import com.yupi.yunpicturebackend.model.dto.user.*;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.LoginUserVO;
import com.yupi.yunpicturebackend.model.vo.UserVO;
import com.yupi.yunpicturebackend.service.UserService;
import lombok.Builder;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController //这个注解表示的是请求响应类，会把每个返回值变成json形式
@RequestMapping("/user")//这个注解表示的请求的路径
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping ("/register")//会与上面的mapping的路径进行拼接

    //由于接收的参数是一个对象，所以要加上@RequestBody注解 这样前端就可以传递一个对象过来
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        //使用自己定义的异常工具类抛出异常
        ThrowUtils.throwIf(userRegisterRequest==null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        //ResultUtils.success封装的是返回结果
        return ResultUtils.success(result);
    }
    @PostMapping ("/login")//会与上面的mapping的路径进行拼接
    //由于接收的参数是一个对象，所以要加上@RequestBody注解 这样前端就可以传递一个对象过来
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        //使用自己定义的异常工具类抛出异常
        ThrowUtils.throwIf(userLoginRequest==null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        //ResultUtils.success封装的是返回结果
        return ResultUtils.success(loginUserVO);
    }
    /**
     * 获取当前登录用户
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping ("/logout")//会与上面的mapping的路径进行拼接
    //由于接收的参数是一个对象，所以要加上@RequestBody注解 这样前端就可以传递一个对象过来
    public BaseResponse<Boolean> userLogout( HttpServletRequest request) {
        ThrowUtils.throwIf(request==null, ErrorCode.PARAMS_ERROR);
        boolean result = userService.userLogout(request);
        //ResultUtils.success封装的是返回结果
        return ResultUtils.success(result);
    }
    /**
     * 添加用户 由于是管理员权限，所以需要加上权限校验
     * @param
     * @return
     */
    @PostMapping ("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)

    public BaseResponse<Long> addUser(@RequestBody UserAddRequest  userAddRequest) {
        ThrowUtils.throwIf(userAddRequest==null, ErrorCode.PARAMS_ERROR);
        User user =new User();
        BeanUtil.copyProperties(userAddRequest, user);
        final String DEFAULT_PASSWORD = "12345678";
        String encryptPassword = userService.getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVOById(long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 删除用户（仅管理员）
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户（仅管理员）
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     *
     * @param userQueryRequest 查询请求参数
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }


}
