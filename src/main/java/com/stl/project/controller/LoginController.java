package com.stl.project.controller;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.LoginPojo;
import com.stl.project.servicesofimpl.LoginImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/Express")
public class LoginController {

    @Resource
    private LoginImpl login;
    @Resource
    private BaseResponse baseResponse;

    @RequestMapping(value = "/normalLogin",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse normalLogin(HttpSession session,HttpServletResponse response, @RequestBody LoginPojo loginPojo){
        return login.normalLogin(session,response,loginPojo);
    }
    @RequestMapping(value = "/fastLogin",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse fastLogin(HttpSession session,HttpServletRequest request, HttpServletResponse response){
        return login.fastLogin(session,request,response);
    }
    @RequestMapping(value = "/queryIP",method = {RequestMethod.GET,RequestMethod.POST})
    public List<LoginPojo> queryIP(@RequestBody LoginPojo loginPojo){
        return login.queryIP(loginPojo);
    }
}
