package com.stl.project.controller;

import com.stl.project.dto.BaseResponse;
import com.stl.project.dto.JSONContainsArrayDto;
import com.stl.project.entity.ExpressProcess;
import com.stl.project.servicesofimpl.ExpressProImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/Express")
public class ExpressProController {

    @Resource
    private ExpressProImpl expressPro;

    @RequestMapping(value = "/insertEPSit",method = {RequestMethod.GET,RequestMethod.POST})
    public void signature(@RequestBody ExpressProcess ep){
        expressPro.queryByWaybillNo(ep);
    }
    @RequestMapping(value = "/queryRecord",method = {RequestMethod.GET,RequestMethod.POST})
    public List<ExpressProcess> queryRecord(@RequestBody ExpressProcess ep){
        return expressPro.expressDetail(ep);
    }
    @RequestMapping(value = "/delExpPro",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse delExpPro(@RequestBody JSONContainsArrayDto ep){
        return expressPro.delExpPro(ep.getArrays());
    }
}
