package com.gsm.dissertation.util;

import com.gsm.dissertation.model.Users;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckUserInterceptor implements HandlerInterceptor {
    private String [] page = {".js",".css",".html",".htm","/login","/error",".jpg",".gif",".png"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        for(String s : page){
            if (path.endsWith(s)){
                return HandlerInterceptor.super.preHandle(request,response,handler);
            }
        }
        HttpSession session = request.getSession();
        Users user = (Users)session.getAttribute("user");
        if (user == null || user.getUid() <= 0){
            response.sendRedirect("/login");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request,response,handler);
    }
}
