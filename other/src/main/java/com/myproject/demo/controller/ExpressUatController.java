package com.myproject.demo.controller;

import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.Dto.ExpressResponse;
import com.myproject.demo.entity.Express;
import com.myproject.demo.impl.ExpressUatDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ExpressUatController {

    @Resource
    private ExpressUatDaoImpl expressUatDaoImpl;

    @ResponseBody
    @RequestMapping(value = "/takingUat",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse taking(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressUatDaoImpl.taking(taking);
            expressResponse.setMessage("UAT揽收操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/buildUat",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse build(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressUatDaoImpl.build(taking);
            expressResponse.setMessage("UAT建包操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/waybillGetUat",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse waybillGet(@RequestBody Express taking){
        return expressUatDaoImpl.waybillGet(taking);
    }

    @ResponseBody
    @RequestMapping(value = "/xiadanUat",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse xiadan(@RequestBody Express taking){
        return expressUatDaoImpl.xiadan(taking);
    }

    @ResponseBody
    @RequestMapping(value = "/outUat",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse out(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressUatDaoImpl.xiache(taking);
            expressResponse.setMessage("UAT下车操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/outReturnUat",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse outReturn(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressUatDaoImpl.out_return(taking);
            expressResponse.setMessage("UAT退回操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/handonUat",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse handon(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressUatDaoImpl.handon(taking);
            expressResponse.setMessage("UAT派件操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/signatureUat",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse signature(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressUatDaoImpl.signature(taking);
            expressResponse.setMessage("UAT签收操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }
}
