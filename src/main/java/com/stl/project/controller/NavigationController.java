package com.stl.project.controller;

import com.stl.project.servicesofimpl.NavigationImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class NavigationController {

    @Resource
    private NavigationImpl navigation;

    @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public String login(){
        return "/login";
    }
    @RequestMapping(value = "/main",method = {RequestMethod.GET,RequestMethod.POST})
    public String page(HttpServletRequest request){
        return navigation.allowLogin(request);
    }
    @RequestMapping(value = "/express",method = {RequestMethod.GET,RequestMethod.POST})
    public String express(){
        return "/express";
    }
    @RequestMapping(value = "/material-cfg",method = {RequestMethod.GET,RequestMethod.POST})
    public String material_cfg(){
        return "/material";
    }
    @RequestMapping(value = "/addMat",method = {RequestMethod.GET,RequestMethod.POST})
    public String addMat(){
        return "/demowindows/addmat";
    }
    @RequestMapping(value = "/express-record",method = {RequestMethod.GET,RequestMethod.POST})
    public String express_record(){
        return "/record";
    }
    @RequestMapping(value = "/auto-cfg",method = {RequestMethod.GET,RequestMethod.POST})
    public String auto_cfg(){
        return "/autocfg";
    }
    @RequestMapping(value = "/ip-cfg",method = {RequestMethod.GET,RequestMethod.POST})
    public String ip_cfg(){
        return "/ipcfg";
    }

    @RequestMapping(value = "/price",method = {RequestMethod.GET,RequestMethod.POST})
    public String price(){
        return "/price";
    }
    @RequestMapping(value = "/direct",method = {RequestMethod.GET,RequestMethod.POST})
    public String direct(){
        return "/directmarket";
    }
    @RequestMapping(value = "/policy",method = {RequestMethod.GET,RequestMethod.POST})
    public String policy(){
        return "/policy";
    }
    @RequestMapping(value = "/express-check",method = {RequestMethod.GET,RequestMethod.POST})
    public String express_check(){
        return "/expresscheck";
    }
    @RequestMapping(value = "/uat",method = {RequestMethod.GET,RequestMethod.POST})
    public String uat(){
        return "/uat";
    }
}
