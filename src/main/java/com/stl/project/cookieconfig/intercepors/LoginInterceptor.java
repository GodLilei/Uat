package com.stl.project.cookieconfig.intercepors;

import com.stl.project.entity.LoginPojo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        String uname = (String)session.getAttribute("uname");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("name")) {
                    return true;
                }
            }
        }
        response.sendRedirect("/login");
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
