package com.myproject.demo.controller;

import com.alibaba.fastjson.JSON;
import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.Dto.Response;
import com.myproject.demo.entity.ActiveWayBillNo;
import com.myproject.demo.entity.Express;
import com.myproject.demo.entity.ExpressProcess;
import com.myproject.demo.impl.ActiveWaybillImpl;
import com.myproject.demo.utils.BrowserConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.List;

@Controller
public class OldUatController {
    @Resource
    private ActiveWaybillImpl activeWaybill;


    @ResponseBody
    @RequestMapping(value = "/checkActiveSit",method = {RequestMethod.GET,RequestMethod.POST})
    public Response activeWaybill(@RequestBody ActiveWayBillNo activeWayBillNo){
        Response response = new Response();
        try{
            response.setMessage(activeWaybill.addActive(activeWayBillNo));
            response.setCode("0");
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setCode("-1");
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/pwReset",method = {RequestMethod.GET,RequestMethod.POST})
    public Response pwReset(@RequestBody String code){
        Response response = new Response();
        try{
            response.setMessage(activeWaybill.pwReset(code));
            response.setCode("0");
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setCode("-1");
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/checkMenu",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse checkMenu(@RequestBody String menu){
        BaseResponse response = new BaseResponse();
        try{
            List<String> list = activeWaybill.checkMenu(menu);
            response.setData(JSON.parseArray(JSON.toJSONString(list)));
            response.setCode("0");

//            BrowserConfig browserConfig = new BrowserConfig();
//            browserConfig.openBrowser();
//            browserConfig.openWeb("http://jingangtest.yto56.com.cn/mdm/logout.action");
//            browserConfig.submit("login_mode");
//            browserConfig.getVildateImage("//*[@id=\"vildateImg\"]");
//            browserConfig.writeValue("admin","Aa123456",browserConfig.getVerificationCode(""));
//            browserConfig.submit("submitbtn");
//            browserConfig.openWeb(list.get(0));

        }catch (Exception e){
            response.setMsg(e.getMessage());
            response.setCode("-1");
        }
        return response;
    }
}
