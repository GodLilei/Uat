package com.stl.project.controller.businesscontroller;

import com.stl.project.dto.BaseResponse;
import com.stl.project.dto.DirectMarketDto;
import com.stl.project.entity.LoginPojo;
import com.stl.project.entity.businessentity.DirectMarket;
import com.stl.project.servicesofimpl.businessimpl.DirectImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/Express")
public class DirectController {
    @Resource
    private DirectImpl direct;

    @RequestMapping(value = "/configStart",method = {RequestMethod.GET,RequestMethod.POST})
    public DeferredResult configStart(@RequestBody DirectMarketDto param){
        Long a = Long.parseLong(param.getTimeout());
        DeferredResult<DirectMarketDto> deferredResult = new DeferredResult<>(a);
        direct.configStart(param.getCus(), deferredResult);
        return deferredResult;
    }
    @RequestMapping(value = "/directCheck",method = {RequestMethod.GET,RequestMethod.POST})
    public void directCheck(@RequestBody DirectMarket directMarket){
        direct.directCheck(directMarket.getCustomer_code(), directMarket);
    }
    @RequestMapping(value = "/endConfig",method = {RequestMethod.GET,RequestMethod.POST})
    public void endConfig(@RequestBody DirectMarket directMarket){
        direct.endConfig(directMarket.getCustomer_code());
    }
}
