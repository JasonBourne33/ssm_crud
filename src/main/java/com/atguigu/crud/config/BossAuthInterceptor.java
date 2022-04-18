package com.atguigu.crud.config;

import com.github.pagehelper.util.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BossAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("entering interceptor");
        System.out.println(request.getRequestURI());
        String token=request.getHeader("token");
        if(!StringUtils.hasText(token)){
            response.setStatus(401);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("you are not authorised");
        }else {
            if (token.equals("111111")){
                return true;
            }else {
                response.setStatus(401);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("your token is incurrect");
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
