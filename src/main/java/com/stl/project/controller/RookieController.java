package com.stl.project.controller;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.Express;
import com.stl.project.servicesofimpl.RookieImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/Express")

public class RookieController {
    @Resource
    private RookieImpl rookie;

    @RequestMapping(value = "/waybillGetSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse waybillGet(@RequestBody Express taking){
        return rookie.waybillGet(taking);
    }
    @RequestMapping(value = "/xiadanSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse xiadan(@RequestBody Express taking){
        return rookie.xiadan(taking);
    }
}
