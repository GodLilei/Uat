package com.myproject.demo.controller;

import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.Dto.ExpressResponse;
import com.myproject.demo.Dto.Response;
import com.myproject.demo.entity.Express;
import com.myproject.demo.impl.ExpressDaoImpl;
import com.myproject.demo.utils.Tool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExpressSitController {

    @Resource
    private ExpressDaoImpl expressDao;

    @ResponseBody
    @RequestMapping(value = "/extractSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse extract(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.extract(taking);
            expressResponse.setMessage("取件操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/takingSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse taking(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.taking(taking);
            expressResponse.setMessage("揽收操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/buildSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse build(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.build(taking);
            expressResponse.setMessage("建包操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/waybillGetSit",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponse waybillGet(@RequestBody Express taking){
        return expressDao.waybillGet(taking);
    }

    @ResponseBody
    @RequestMapping(value = "/xiadanSit",method = {RequestMethod.GET,RequestMethod.POST})
    public Response xiadan(@RequestBody Express taking){
        return expressDao.xiadan2(taking);
    }

    @ResponseBody
    @RequestMapping(value = "/shangcheSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse shangche(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.shangche(taking);
            expressResponse.setMessage("上车操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/outSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse out(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.xiache(taking);
            expressResponse.setMessage("下车操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/bubbleSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse bubble(@RequestBody Express express){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.bubble(express);
            expressResponse.setMessage("计泡操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/unpackSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse unpack(@RequestBody Express express){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.unpack(express);
            expressResponse.setMessage("拆包操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/outReturnSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse outReturn(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.out_return(taking);
            expressResponse.setMessage("退回操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/handonSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse handon(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.handon(taking);
            expressResponse.setMessage("派件操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/signatureSit",method = {RequestMethod.GET,RequestMethod.POST})
    public ExpressResponse signature(@RequestBody Express taking){
        ExpressResponse expressResponse = new ExpressResponse();
        try{
            expressDao.signature(taking);
            expressResponse.setMessage("签收操作成功");
        }catch (Exception e){
            expressResponse.setCode("-1");
            expressResponse.setMessage(e.getMessage());
        }
        return expressResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/queryTakingSit",method = {RequestMethod.GET,RequestMethod.POST})
    public Response queryTakingSit(@RequestBody Express taking){
        Response response = null;
        response = expressDao.takingStatus(taking.getWaybill_no());
        return response;
    }
    @ResponseBody
    @RequestMapping(value = "/queryHandonSit",method = {RequestMethod.GET,RequestMethod.POST})
    public Response queryHandonSit(@RequestBody Express taking){
        Response response = null;
        response = expressDao.handonStatus(taking.getWaybill_no());
        return response;
    }
    @ResponseBody
    @RequestMapping(value = "/querySignatureSit",method = {RequestMethod.GET,RequestMethod.POST})
    public Response querySignatureSit(@RequestBody Express taking){
        Response response = null;
        response = expressDao.signatureStatus(taking.getWaybill_no());
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/autoTakingZ",method = {RequestMethod.GET,RequestMethod.POST})
    public String autoTakingZ(@RequestBody Express taking){
        return expressDao.test(taking);
    }

}
