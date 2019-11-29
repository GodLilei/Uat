package com.stl.project.servicesofimpl;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class NavigationImpl {

    public String allowLogin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String name = "";
        if (cookies == null || cookies.length == 0){
            return "/login";
        }else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("name")) {
                    name = cookie.getValue();
                }
            }
        }
        if ("".equals(name)){
            return "/";
        }else {
            return "/main";
        }
    }
}
