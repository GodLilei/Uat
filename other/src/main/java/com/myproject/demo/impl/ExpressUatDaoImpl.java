package com.myproject.demo.impl;

//import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSON;
import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.entity.Express;
import com.myproject.demo.services.ExpressServices;
import com.myproject.demo.services.ExpressUatServices;
import com.myproject.demo.utils.HttpServer;
import com.myproject.demo.utils.InterTestHttpServer;
import com.myproject.demo.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ExpressUatDaoImpl {

    @Resource
    private ExpressUatServices expressUatServices;
    @Resource
    private ExpressDaoImpl expressDaoImpl;
    private Express express;
    private Tool tool = new Tool();

    private String randomId(){
        String num = (int)(Math.random()*10000) + "";
        return "2db7304d-d637-aaa1-bbd0-8c6fa87d" + num;
    }

    public String test(){
        return randomId();
    }

    /**
     * 日期转字符串
     */
    private String dateString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    //揽收
    public void taking(Express oldexpress){
        log.info("----->Uat揽收操作开始：执行机构：" + oldexpress.getTaking_org_code());
        express = new Express();
        express.setOp_code(310);
        expressUatServices.taking(expressDaoImpl.setExpress(express, oldexpress,0));
    }

    //建包
    public void build(Express oldexpress){
        log.info("----->UAT建包操作开始：执行机构："
                + oldexpress.getBuild_org_code() + ",type" + oldexpress.getBuild_org_type());
        express = new Express();
        express.setOp_code(111);
        express.setBuild_org_code(oldexpress.getBuild_org_code());
        express.setPkg_no(oldexpress.getPkg_no());
        express.setBuild_org_type(oldexpress.getBuild_org_type());
        expressUatServices.build(expressDaoImpl.setExpress(express, oldexpress,0));
    }
    //下车
    public void xiache(Express oldexpress){
        log.info("----->UAT下车操作开始：执行机构："
                + oldexpress.getOut_org_code() + ",type" + oldexpress.getOut_org_type());
        express = new Express();
        express.setOp_code(171);//下车操作码
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        expressUatServices.xiache(expressDaoImpl.setExpress(express, oldexpress,0));
    }
    //退回
    public void out_return(Express oldexpress){
        log.info("----->UAT退回操作开始：执行机构："
                + oldexpress.getOut_org_code() + ",type" + oldexpress.getOut_org_type());
        express = new Express();
        express.setOp_code(171);//下车操作码
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        express.setIo_type("02");
        expressUatServices.xiache(expressDaoImpl.setExpress(express, oldexpress,0));
    }
    //派件
    public void handon(Express oldexpress){
        log.info("----->UAT派件操作开始：执行机构：" + oldexpress.getHandon_org_code());
        express = new Express();
        express.setOp_code(710);//操作码
        express.setHandon_org_code(oldexpress.getHandon_org_code());
        expressUatServices.handon(expressDaoImpl.setExpress(express, oldexpress,0));
    }
    //签收
    public void signature(Express oldexpress){
        log.info("----->UAT签收操作开始：执行机构：" + oldexpress.getSignature_org_code());
        express = new Express();
        express.setOp_code(740);
        express.setSignature_name(oldexpress.getSignature_name());
        express.setSignature_org_code(oldexpress.getSignature_org_code());
        express.setDelivery_time(tool.nowDate());
        express.setQuery_flag(Integer.parseInt(oldexpress.getOp_user_code()));
        express.setDelivery_id(dateString().concat(oldexpress.getWaybill_no()));
        expressUatServices.signature(expressDaoImpl.setExpress(express, oldexpress,0));
    }
    //检查是否签收
    public int checkHandon(String waybill){
        return expressUatServices.checkHandon(waybill);
    }

    public BaseResponse waybillGet(Express taking){
        BaseResponse baseResponse = new BaseResponse();
        InterTestHttpServer interTestHttpServer = new InterTestHttpServer();
        HttpServer httpServer = new HttpServer();
        Tool tools = new Tool();
        String[] key = {"ChannelCode","OrgCode","waybillNo"};
        String[] value = {"TAOBAO",taking.getSource_org_code(),"82".concat(tools.dateString()).concat(tools
                .fourRandom())};
        try{
            String wayBillNo = interTestHttpServer.HttpGetofJSONString("http://jingang.msns.cn/yttsuat/GetWaybillNo18",
                    key, value);
            baseResponse.setCode("0");
//            baseResponse.setData(JSON.parseArray("[{\"wbn\":\"" + msg.substring(msg.length()-18) + "\"}]"));
            baseResponse.setData(JSON.parseArray("[{\"wbn\":\"" + wayBillNo + "\"}]"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMsg(e.getMessage());
        }
        return baseResponse;
    }

    public BaseResponse xiadan(Express taking){
        BaseResponse baseResponse = new BaseResponse();
        HttpServer httpServer = new HttpServer();
        try{
            String msg = httpServer.weChatSendUat(taking.getEmp_code(),taking.getSource_org_code(),taking.getWaybill_no
                    (),taking.getSeller(),taking.getDes_org_code());
            if (msg.length() <= 20){
                baseResponse.setCode("1");
                baseResponse.setMsg("UAT下单接口异常，暂时无法下单！");
            }else {
                baseResponse.setCode("0");
                baseResponse.setMsg("UAT下单成功！网点：" + taking.getSource_org_code() + " 客户：" + taking.getEmp_code() +
                        "单号：" + taking.getWaybill_no());
            }
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMsg(e.getMessage());
        }
        return baseResponse;
    }
}
