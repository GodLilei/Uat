package com.stl.project.controller;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.Express;
import com.stl.project.servicesofimpl.ExpressImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/Express")
public class ExpressController {

    @Resource
    private ExpressImpl expressImpl;

    @RequestMapping(value = "/extractSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse extract(@RequestBody Express taking){
       return expressImpl.extract(taking);
    }
    @RequestMapping(value = "/takingSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse taking(@RequestBody Express taking){
        return expressImpl.taking(taking);
    }
    @RequestMapping(value = "/buildSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse build(@RequestBody Express taking){
        return expressImpl.build(taking);
    }
    @RequestMapping(value = "/unpackSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse unpack(@RequestBody Express taking){
        return expressImpl.unpack(taking);
    }
    @RequestMapping(value = "/bubbleSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse bubble(@RequestBody Express taking){
        return expressImpl.bubble(taking);
    }
    @RequestMapping(value = "/shangcheSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse shangche(@RequestBody Express taking){
        return expressImpl.shangche(taking);
    }
    @RequestMapping(value = "/outSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse xiache(@RequestBody Express taking){
        return expressImpl.xiache(taking);
    }
    @RequestMapping(value = "/outReturnSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse outReturn(@RequestBody Express taking){
        return expressImpl.out_return(taking);
    }
    @RequestMapping(value = "/handonSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse handon(@RequestBody Express taking){
        return expressImpl.handon(taking);
    }
    @RequestMapping(value = "/signatureSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse signature(@RequestBody Express taking){
        return expressImpl.signature(taking);
    }
    @RequestMapping(value = "/autoTakingZ",method = {RequestMethod.GET,RequestMethod.POST})
    public String autoTakingZ(@RequestBody Express taking){
        return expressImpl.autoTaking(taking);
    }
}
