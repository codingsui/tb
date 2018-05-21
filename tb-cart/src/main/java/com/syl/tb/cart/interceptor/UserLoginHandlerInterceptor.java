package com.syl.tb.cart.interceptor;

import com.syl.tb.cart.pojo.User;
import com.syl.tb.cart.service.UserService;
import com.syl.tb.cart.threadlocal.UserThreadLocal;
import com.syl.tb.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginHandlerInterceptor implements HandlerInterceptor {
    public static final String TOKEN = "TB_TOKEN";

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String token = CookieUtils.getCookieValue(httpServletRequest,TOKEN);
        System.out.println("token++++"+token);
        System.out.println("token++++"+token);
        System.out.println("token++++"+token);
        System.out.println("token++++"+token);
       if (StringUtils.isEmpty(token)){
           return true;
       }
        User user = userService.queryByToken(token);
        System.out.println("user"+user);
       if (null == user){
           return true;
       }
       //登录成功
        System.out.println("登录成功");
        UserThreadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        UserThreadLocal.set(null);
    }
}
