package com.yupi.yunpicturebackend.AOP;

import com.yupi.yunpicturebackend.annotation.AuthCheck;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.enums.UserRoleEnum;
import com.yupi.yunpicturebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Aspect这个注解表示是一个切面
 * @Component表示spring的bean.让spring来识别和加载
 */
@Aspect
@Component
public class AuthInterceptor {
    //获取用户信息，进行权限校验
 @Resource
    private UserService userService;

    /**
     * 进行拦截，指定带有有@annotation里面的注解的方法才会去拦截
     * @param joinPoint 表示在哪个方法进行切入
     * @param authCheck 权限校验注解
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
  public  Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
         String mustRole=authCheck.mustRole();
         //通过这全局上下文拿到当前请求的全局属性
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //获取登录用户
        User loginUser=userService.getLoginUser(request);
        //获取进行权限判断的mustrole的枚举类，mustrole表示需要满足这个权限才能执行对应的方法
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        //如果不需要权限，则放行
        if(mustRoleEnum==null){
            return  joinPoint.proceed();
        }
        //以下的代码，必须有权限才能通过
        //这是用户具有的权限
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        if(userRoleEnum==null){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)&&!UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //通过校验，放行
        return joinPoint.proceed();
    }

}
