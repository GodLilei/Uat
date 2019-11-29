package com.stl.project.servicesofimpl;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.Express;
import com.stl.project.servicesofdatasource.FrontDB;
import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ExpressImpl {
    @Resource
    private FrontDB frontDB;
    @Resource
    private DateUtil dateUtil;
    @Resource
    private BaseResponse baseResponse;
    @Resource
    private JSONChange change;
    @Resource
    private RookieImpl rookieImpl;

    private Express express;

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
    private Express setExpress(Express express, Express oldExpress,int time){
        express.setWaybill_no(oldExpress.getWaybill_no());
        express.setId(randomId());//
        express.setEmp_code(oldExpress.getEmp_code());
        express.setOp_user_code(oldExpress.getOp_user_code());
        express.setOp_user_name(oldExpress.getOp_user_name());
        express.setSource_org_code(oldExpress.getSource_org_code());
        express.setTaking_org_code(oldExpress.getTaking_org_code());
        express.setDes_org_code(oldExpress.getDes_org_code());
        express.setEffective_type_code(oldExpress.getEffective_type_code());
        express.setUpload_time(dateUtil.dateOfAfter("day",time));
        express.setCreate_time(dateUtil.dateOfAfter("day",time));
        express.setModify_time(dateUtil.dateOfAfter("day",time));
        express.setOperate_time(dateUtil.dateOfAfter("day",time));
        return  express;
    }
    //取件
    public BaseResponse extract(Express oldexpress){
        express = new Express();
        express.setOp_code(311);
        express.setTaking_org_code(oldexpress.getTaking_org_code());
        express.setInput_weight(oldexpress.getInput_weight());
        try{
            frontDB.taking(setExpress(express,oldexpress,0));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"311\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //揽收
    public BaseResponse taking(Express oldexpress){
        express = new Express();
        express.setOp_code(310);
        express.setTaking_org_code(oldexpress.getTaking_org_code());
        express.setInput_weight(oldexpress.getInput_weight());
        try{
            frontDB.taking(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"310\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //建包
    public BaseResponse build(Express oldexpress){
        express = new Express();
        express.setOp_code(111);
        if (oldexpress.getExpress_content_code().equals("DOC")){
            express.setCreate_terminal("862493100087844/A0-8C-FD-E7-63-60/2.2.4");
            express.setDevice_type("PDA");
            oldexpress.setOp_user_name(oldexpress.getOp_user_name().concat("_1"));
        }
        express.setExpress_content_code(oldexpress.getExpress_content_code());
        express.setBuild_org_code(oldexpress.getBuild_org_code());
        express.setPkg_no(oldexpress.getPkg_no());
        express.setNext_org_code(oldexpress.getNext_org_code());
        express.setBuild_org_type(oldexpress.getBuild_org_type());
        try{
            frontDB.build(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"111\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //拆包
    public BaseResponse unpack(Express oldexpress){
        express = new Express();
        express.setOp_code(181);
        express.setInput_weight(oldexpress.getInput_weight());
        express.setFee_weight(oldexpress.getInput_weight());
        express.setBuild_org_code(oldexpress.getBuild_org_code());
        express.setPkg_no(oldexpress.getPkg_no());
        express.setBuild_org_type(oldexpress.getBuild_org_type());
        try{
            frontDB.build(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"181\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //上车
    public BaseResponse shangche(Express oldexpress){
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
        try{
            frontDB.xiache(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"131\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //下车
    public BaseResponse xiache(Express oldexpress){
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
        express.setInput_weight(oldexpress.getOut_weight());
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        express.setPrevious_org_code(oldexpress.getPrevious_org_code());
        express.setNext_org_code(oldexpress.getNext_org_code());
        try{
            frontDB.xiache(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"171_01\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //计泡
    public BaseResponse bubble(Express oldexpress){
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
        try{
            frontDB.xiache(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"179\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //退回
    public BaseResponse out_return(Express oldexpress){
        express = new Express();
        express.setOp_code(171);//下车操作码
        express.setInput_weight(oldexpress.getOut_weight());
        express.setOut_org_code(oldexpress.getOut_org_code());
        express.setOut_org_type(oldexpress.getOut_org_type());
        express.setCar_no(oldexpress.getCar_no());
        express.setIo_type("02");
        try{
            frontDB.xiache(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"171_02\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //派件
    public BaseResponse handon(Express oldexpress){
        express = new Express();
        express.setOp_code(710);//操作码
        express.setHandon_org_code(oldexpress.getHandon_org_code());
        try{
            frontDB.handon(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"710\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //签收
    public BaseResponse signature(Express oldexpress){
        express = new Express();
        express.setOp_code(740);
        express.setSignature_name(oldexpress.getSignature_name());
        express.setSignature_org_code(oldexpress.getSignature_org_code());
        express.setDelivery_time(dateUtil.nowDate());
        express.setQuery_flag(Integer.parseInt(oldexpress.getOp_user_code()));
        express.setDelivery_id(dateString().concat(oldexpress.getWaybill_no()));
        try{
            frontDB.signature(setExpress(express,oldexpress,Integer.parseInt(oldexpress.getExpire_date())*-1));
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(change.jsonStringToJSONArray("{\"op\":\"740\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
    //自动揽收
    public String autoTaking(Express oldeExpress){
        Express taking = new Express();
        String waybillNo = rookieImpl.returnWaybillNo(oldeExpress.getSource_org_code());//单号网点
        taking.setWaybill_no(waybillNo);
        taking.setDes_org_code(oldeExpress.getDes_org_code());//目的网点
        taking.setEmp_code(oldeExpress.getEmp_code());//客户
        taking.setSource_org_code(oldeExpress.getSource_org_code());//物料所属网点
        taking.setTaking_org_code(oldeExpress.getTaking_org_code());//操作网点&始发网点
        taking.setSeller(oldeExpress.getSeller());//商家
        taking.setCreate_time(dateUtil.nowDate());
        rookieImpl.xiadan(taking);
        taking.setId(randomId());
        frontDB.autoTaking_x(taking);
        taking.setId(randomId());
        frontDB.autoTaking_t(taking);
        return waybillNo;
    }
}
