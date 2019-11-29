package com.myproject.demo.impl;

import com.alibaba.fastjson.JSON;
import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.Dto.Response;
import com.myproject.demo.entity.Express;
import com.myproject.demo.entity.Rookie;
import com.myproject.demo.services.ExpressServices;
import com.myproject.demo.services.RookieXiadanServices;
import com.myproject.demo.utils.HttpClientResponse;
import com.myproject.demo.utils.HttpServer;
import com.myproject.demo.utils.InterTestHttpServer;
import com.myproject.demo.utils.Tool;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Service;

//import javax.annotation.Resource;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ExpressDaoImpl {

    @Resource
    private ExpressServices expressServices;
    @Resource
    private RookieXiadanServices rookieXiadanServices;
    private Express express;
    @Resource
    private Tool tool;

    /**
     * 随机数
     * @return 随机数
     */
    private String randomId(){
        String num = (int)(Math.random()*10000) + "";
        return "2db7304d-d637-aaa1-bbd0-8c6fa87d" + num;
    }

    /**
     * 日期转字符串
     */
    private String dateString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     * 公共参数：时间，单号，id，客户
     * @param oldExpress 1
     * @return 1
     */
    Express setExpress(Express express, Express oldExpress,int time){
        express.setWaybill_no(oldExpress.getWaybill_no());
        express.setId(randomId());//
        express.setEmp_code(oldExpress.getEmp_code());
        express.setOp_user_code(oldExpress.getOp_user_code());
        express.setOp_user_name(oldExpress.getOp_user_name());
        express.setSource_org_code(oldExpress.getSource_org_code());
        express.setTaking_org_code(oldExpress.getTaking_org_code());
        express.setDes_org_code(oldExpress.getDes_org_code());
        express.setEffective_type_code(oldExpress.getEffective_type_code());
        express.setUpload_time(tool.dateOfAfter("day",time));
        express.setCreate_time(tool.dateOfAfter("day",time));
        express.setModify_time(tool.dateOfAfter("day",time));
        express.setOperate_time(tool.dateOfAfter("day",time));
        return  express;
    }

    //取件
    public void extract(Express oldexpress){
        log.info("----->取件操作开始：执行机构：" + oldexpress.getTaking_org_code());
        express = new Express();
        express.setOp_code(311);
        express.setTaking_org_code(oldexpress.getTaking_org_code());
        express.setInput_weight(oldexpress.getInput_weight());
        expressServices.taking(setExpress(express,oldexpress,0));
    }

    //揽收
    public void taking(Express oldexpress){
        log.info("----->揽收操作开始：执行机构：" + oldexpress.getTaking_org_code());
        express = new Express();
        express.setOp_code(310);
        express.setTaking_org_code(oldexpress.getTaking_org_code());
        express.setInput_weight(oldexpress.getInput_weight());
        expressServices.taking(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
    }
    //建包
    public void build(Express oldexpress){
        log.info("----->建包操作开始：执行机构："
                + oldexpress.getBuild_org_code() + ",type：" + oldexpress.getBuild_org_type());
        express = new Express();
        express.setOp_code(111);
        if (oldexpress.getExpress_content_code().equals("DOC")){
            express.setCreate_terminal("862493100087844/A0-8C-FD-E7-63-60/2.2.4");
            express.setDevice_type("PDA");
            oldexpress.setOp_user_name(oldexpress.getOp_user_name().concat("_1"));
            log.info("----->检测到PDA建包，操作员：" + oldexpress.getOp_user_name());
        }
        express.setExpress_content_code(oldexpress.getExpress_content_code());
        express.setBuild_org_code(oldexpress.getBuild_org_code());
        express.setPkg_no(oldexpress.getPkg_no());
        express.setNext_org_code(oldexpress.getNext_org_code());
        express.setBuild_org_type(oldexpress.getBuild_org_type());
        expressServices.build(setExpress(express,oldexpress,0));
    }

    //拆包
    public void unpack(Express oldexpress){
        log.info("----->拆包操作开始：执行机构："
                + oldexpress.getBuild_org_code() + ",type：" + oldexpress.getBuild_org_type());
        express = new Express();
        express.setOp_code(181);
        express.setInput_weight(oldexpress.getInput_weight());
        express.setFee_weight(oldexpress.getInput_weight());
        express.setBuild_org_code(oldexpress.getBuild_org_code());
        express.setPkg_no(oldexpress.getPkg_no());
        express.setBuild_org_type(oldexpress.getBuild_org_type());
        expressServices.build(setExpress(express,oldexpress,0));
    }

    //上车
    public void shangche(Express oldexpress){
        log.info("----->上车操作开始：执行机构："
                + oldexpress.getOut_org_code() + ",type：" + oldexpress.getOut_org_type());
        express = new Express();
        express.setOp_code(131);//上车操作码
        if (oldexpress.getWaybill_no().contains("WB") || oldexpress.getWaybill_no().contains("DZB")){
            log.info("----->检测到包签号码，执行整包上车操作，" + oldexpress.getWaybill_no());
            express.setExp_type("20");//20：包，10：快件
        }
        if (oldexpress.getExpress_content_code().equals("DOC")){
            express.setCreate_terminal("862493100087844/A0-8C-FD-E7-63-60/2.2.4");
            express.setDevice_type("PDA");
            oldexpress.setOp_user_name(oldexpress.getOp_user_name().concat("_1"));
            oldexpress.setOut_weight(0.15);
            express.setWeigh_weight(0.15);
            express.setVolume_weight(0.15);
            log.info("----->检测到PDA上车，操作员：" + oldexpress.getOp_user_name());
        }
        express.setExpress_content_code(oldexpress.getExpress_content_code());
        express.setInput_weight(oldexpress.getOut_weight());
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        express.setPrevious_org_code(oldexpress.getPrevious_org_code());
        express.setNext_org_code(oldexpress.getNext_org_code());
        expressServices.xiache(setExpress(express,oldexpress,0));
    }

    //下车
    public void xiache(Express oldexpress){
        log.info("----->下车操作开始：执行机构："
                + oldexpress.getOut_org_code() + ",type：" + oldexpress.getOut_org_type());
        express = new Express();
        express.setOp_code(171);//下车操作码
        if (oldexpress.getWaybill_no().contains("WB") || oldexpress.getWaybill_no().contains("DZB")){
            log.info("----->检测到包签号码，执行整包下车操作，" + oldexpress.getWaybill_no());
            express.setExp_type("20");
        }
        if (oldexpress.getExpress_content_code().equals("DOC")){
            express.setCreate_terminal("862493100087844/A0-8C-FD-E7-63-60/2.2.4");
            express.setDevice_type("PDA");
            oldexpress.setOp_user_name(oldexpress.getOp_user_name().concat("_1"));
            oldexpress.setOut_weight(0.15);
            express.setWeigh_weight(0.15);
            express.setVolume_weight(0.15);
            log.info("----->检测到PDA下车，操作员：" + oldexpress.getOp_user_name());
        }
        express.setExpress_content_code(oldexpress.getExpress_content_code());
        express.setWeigh_weight(oldexpress.getOut_weight());
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        express.setPrevious_org_code(oldexpress.getPrevious_org_code());
        express.setNext_org_code(oldexpress.getNext_org_code());
        expressServices.xiache(setExpress(express,oldexpress,   0));
    }

    //计泡
    public void bubble(Express oldexpress){
        log.info("----->计泡操作开始：执行机构："
                + oldexpress.getJipao_org_code() + ",type：" + oldexpress.getJipao_org_type());
        express = new Express();
        express.setOp_code(179);//下车操作码
        express.setInput_weight(0.00);
        express.setOut_org_code(oldexpress.getJipao_org_code());
        express.setOut_org_type(oldexpress.getJipao_org_type());
        express.setPkg_height(oldexpress.getPkg_height());
        express.setPkg_length(oldexpress.getPkg_length());
        express.setPkg_width(oldexpress.getPkg_width());
        express.setVolume_weight(oldexpress.getVolume_weight());
        express.setCar_no(oldexpress.getCar_no());
        expressServices.xiache(setExpress(express,oldexpress,0));
    }

    //退回
    public void out_return(Express oldexpress){
        log.info("----->退回操作开始：执行机构："
                + oldexpress.getOut_org_code() + ",type：" + oldexpress.getOut_org_type());
        express = new Express();
        express.setOp_code(171);//下车操作码
        express.setInput_weight(oldexpress.getOut_weight());
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        express.setIo_type("02");
        expressServices.xiache(setExpress(express,oldexpress,0));
    }
    //派件
    public void handon(Express oldexpress){
        log.info("----->派件操作开始：执行机构：" + oldexpress.getHandon_org_code());
        express = new Express();
        express.setOp_code(710);//操作码
        express.setHandon_org_code(oldexpress.getHandon_org_code());

        expressServices.handon(setExpress(express,oldexpress,0));
    }
    //签收
    public void signature(Express oldexpress){
        log.info("----->签收操作开始：执行机构：" + oldexpress.getSignature_org_code());
        express = new Express();
        express.setOp_code(740);
        express.setSignature_name(oldexpress.getSignature_name());
        express.setSignature_org_code(oldexpress.getSignature_org_code());
        express.setDelivery_time(tool.nowDate());
        express.setQuery_flag(Integer.parseInt(oldexpress.getOp_user_code()));
        express.setDelivery_id(dateString().concat(oldexpress.getWaybill_no()));
        expressServices.signature(setExpress(express,oldexpress,0));
    }
    //检查是否签收
    public int checkHandon(String waybill){
        return expressServices.checkHandon(waybill);
    }

    public BaseResponse waybillGet(Express taking){
        BaseResponse baseResponse = new BaseResponse();
        try{
            String waybillNo = tool.returnWaybillNo(taking.getSource_org_code());
            baseResponse.setCode("0");
//            baseResponse.setData(JSON.parseArray("[{\"wbn\":\"" + msg.substring(msg.length()-18) + "\"}]"));
            baseResponse.setData(JSON.parseArray("[{\"wbn\":\"" + waybillNo + "\"}]"));
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
            String msg = httpServer.weChatSend(taking.getEmp_code(),taking.getSource_org_code(),taking.getWaybill_no
                    (),taking.getSeller(),taking.getDes_org_code());
            if (msg.contentEquals("单号已被使用，请重新拉单") ||msg.contentEquals("http://jingangtest.yto56.com.cn/ordws/Vip18Servlet接口下单失败")){
                baseResponse.setCode("1");
                baseResponse.setMsg(msg);
            }else {
                baseResponse.setCode("0");
                baseResponse.setMsg("下单成功！网点：" + taking.getSource_org_code() + " \n客户：" + taking.getEmp_code() +
                        "\n单号：" + taking.getWaybill_no());
            }
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMsg(e.getMessage());
        }
        return baseResponse;
    }

    public Response xiadan2(Express taking){
        log.info("----->下单执行中：" + taking.getWaybill_no() + "-" + taking.getSource_org_code() + "-" + taking.getEmp_code());
        Response response = new Response();
        Rookie rookie = new Rookie();
        rookie.setCURTIME(tool.nowDate());
        rookie.setWAYBILL_NO(taking.getWaybill_no());
        rookie.setBIZ_ID("TB".concat(tool.userDateString()).concat(tool.dateString()).concat(tool.fourRandom()));
        rookie.setBRANCH_CODE(taking.getSource_org_code());
        rookie.setBack_1(taking.getDes_org_code());
        rookie.setSELLER_ID(taking.getSeller());
        rookie.setCustomerCode(taking.getEmp_code());
        try{
            rookieXiadanServices.rookieXiadan(rookie);
            response.setCode("0");
            response.setMessage("下单成功！网点：" + taking.getSource_org_code() + " \n客户：" + taking.getEmp_code() +
                    "\n单号：" + taking.getWaybill_no());
        }catch (Exception e){
            response.setCode("1");
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public Response takingStatus(String waybill_no){
        log.info("----->单号揽收检测中：" + waybill_no);
        String status = "";
        Response response = null;
        try{
            List<Express> ready = expressServices.queryTaking(waybill_no);
            List<Express> unsuccess = expressServices.queryTaking_unsuccess(waybill_no);
            List<Express> deal = expressServices.queryTaking_deal(waybill_no);
            status = checkStatusMethod(status, ready, unsuccess, deal);
        }catch (Exception e){
            response = new Response("1",e.getMessage());
        }
        response = new Response("0",status);
        return response;
    }

    public Response handonStatus(String waybill_no){
        log.info("----->单号派件检测中：" + waybill_no);
        String status = "";
        Response response = null;
        try{
            List<Express> ready = expressServices.queryHandon(waybill_no);
            List<Express> unsuccess = expressServices.queryHandon_unsuccess(waybill_no);
            List<Express> deal = expressServices.queryHandon_deal(waybill_no);
            status = checkStatusMethod(status, ready, unsuccess, deal);
        }catch (Exception e){
            response = new Response("1",e.getMessage());
        }
        response = new Response("0",status);
        return response;
    }
    public Response signatureStatus(String waybill_no){
        log.info("----->单号签收检测中：" + waybill_no);
        String status = "";
        Response response = null;
        try{
            List<Express> ready = expressServices.querySignature(waybill_no);
            List<Express> unsuccess = expressServices.querySignature_unsuccess(waybill_no);
            List<Express> deal = expressServices.querySignature_deal(waybill_no);
            status = checkStatusMethod(status, ready, unsuccess, deal);
        }catch (Exception e){
            response = new Response("1",e.getMessage());
        }
        response = new Response("0",status);
        return response;
    }
    private String checkStatusMethod(String status, List<Express> ready, List<Express> unsuccess, List<Express> deal) {
        if (!ready.isEmpty()){
            status = "检测到数据，还未处理，请等待。";
        } if (!unsuccess.isEmpty()){
            status = "检测到数据处于unsuccess表中，错误原因：" + unsuccess.get(0).getError_message();
        } if (!deal.isEmpty()){
            status = "数据正常";
        } if (ready.isEmpty() && unsuccess.isEmpty() && deal.isEmpty()){
            status = status.concat("未发现此单号信息（未操作，处理中），请稍后再试");
        }
        return status;
    }

    public String test(Express oldeExpress){
        Express taking = new Express();
        String waybillNo = tool.returnWaybillNo(oldeExpress.getSource_org_code());//单号网点
        taking.setWaybill_no(waybillNo);
        taking.setDes_org_code(oldeExpress.getDes_org_code());//目的网点
        taking.setEmp_code(oldeExpress.getEmp_code());//客户
        taking.setSource_org_code(oldeExpress.getSource_org_code());//物料所属网点
        taking.setTaking_org_code(oldeExpress.getTaking_org_code());//操作网点&始发网点
        taking.setSeller(oldeExpress.getSeller());//商家
        taking.setCreate_time(tool.nowDate());
        xiadan2(taking);
        taking.setId(randomId());
        expressServices.test(taking);
        taking.setId(randomId());
        expressServices.test1(taking);
        return waybillNo;
    }
}
