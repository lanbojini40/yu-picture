package com.yupi.yunpicturebackend.controller;

import cn.hutool.http.useragent.UserAgent;
import com.yupi.yunpicturebackend.common.BaseResponse;
import com.yupi.yunpicturebackend.common.ResultUtils;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.exception.ThrowUtils;
import com.yupi.yunpicturebackend.model.dto.UserLoginRequest;
import com.yupi.yunpicturebackend.model.dto.UserRegisterRequest;
import com.yupi.yunpicturebackend.model.vo.LoginUserVO;
import com.yupi.yunpicturebackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

}
