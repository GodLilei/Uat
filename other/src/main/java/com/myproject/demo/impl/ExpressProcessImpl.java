package com.myproject.demo.impl;

import antlr.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.Dto.BatchExpProResponse;
import com.myproject.demo.Dto.Response;
import com.myproject.demo.entity.BatchExpPro;
import com.myproject.demo.entity.Express;
import com.myproject.demo.entity.ExpressProcess;
import com.myproject.demo.entity.Identification;
import com.myproject.demo.services.ExpressProcessServices;
import com.myproject.demo.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ExpressProcessImpl {

    @Resource
    private ExpressProcessServices expressProcessServices;
    @Resource
    private ExpressDaoImpl expressDao;
    @Resource
    private ExpressProcessImpl expressProcessImpl;
    @Resource
    private ExpressUatDaoImpl expressUatDao;

    @Resource
    private Tool tools;

    private void insertExpPro(ExpressProcess expressProcess){
        expressProcessServices.insertExpPro(expressProcess);
    }
    private void insertExpProUat(ExpressProcess expressProcess){
        expressProcessServices.insertExpProUat(expressProcess);
    }

    private void updateExpPro(ExpressProcess expressProcess){
        expressProcessServices.updateExpPro(expressProcess);
    }
    private void updateExpProUat(ExpressProcess expressProcess){
        expressProcessServices.updateExpProUat(expressProcess);
    }

    public List<ExpressProcess> expressDetail(ExpressProcess expressProcess){
        if (expressProcess.getUser().endsWith("all")){
            expressProcess.setFlag("all");
            expressProcess.setUser(expressProcess.getUser().substring(0,expressProcess.getUser().length()-3));
        }else if (expressProcess.getUser().equals("adminadmin")){
            expressProcess.setFlag("admin");
        }else {
            expressProcess.setFlag("day");
        }
        return expressProcessServices.expressDetail(expressProcess);
    }
    public List<ExpressProcess> expressDetailUat(ExpressProcess expressProcess){
        return expressProcessServices.expressDetailUat(expressProcess);
    }

    public void queryByWaybillNo(ExpressProcess expressProcess){
        String waybillNo = expressProcess.getWaybill_no();
        List<ExpressProcess> list = expressProcessServices.queryByWaybillNo(waybillNo);
        if (list.size() == 0){
            log.info("----->未检测到单号存在，执行insert操作" + expressProcess.getWaybill_no());
            expressProcess.setCreate_time(tools.nowDate());
            expressProcess.setUser(expressProcess.getUser().concat(tools.userDateString()));
            insertExpPro(expressProcess);
        }else {
            log.info("----->更新操作：" + expressProcess.getOperate());
            String operate = list.get(0).getOperate();
            expressProcess.setOperate(operate.concat(expressProcess.getOperate()));
            updateExpPro(expressProcess);
        }
    }
    public void queryByWaybillNoUat(ExpressProcess expressProcess){
        String waybillNo = expressProcess.getWaybill_no();
        List<ExpressProcess> list = expressProcessServices.queryByWaybillNoUat(waybillNo);
        if (list.size() == 0){
            log.info("----->未检测到单号存在，执行insert操作" + expressProcess.getWaybill_no());
            expressProcess.setCreate_time(tools.nowDate());
            expressProcess.setUser(expressProcess.getUser().concat(tools.userDateString()));
            insertExpProUat(expressProcess);
        }else {
            log.info("----->更新操作：" + expressProcess.getOperate());
            String operate = list.get(0).getOperate();
            expressProcess.setOperate(operate.concat(expressProcess.getOperate()));
            updateExpProUat(expressProcess);
        }
    }


    public void delExpPro(String[] array){
        expressProcessServices.delExpPro(array);
    }
    public void delExpProUat(String[] array){
        expressProcessServices.delExpProUat(array);
    }

    public List<Identification> checkIp(String ip,String time){
        log.info("----->检查IP地址中,IP【" + ip + "】，时间：" + time);
        Identification id = new Identification();
        id.setIpaddr(ip);
        id.setTime(time);
        List<Identification> list = expressProcessServices.checkIp(id);
        if (!list.isEmpty()){
            log.info("----->用户：" + list.get(0).getUser());
        }else {
            log.info("-----IP：【" + ip + "】未注册");
        }
        return list;
    }

    public Response batchAdd(BatchExpProResponse batchExpProResponse){
        List<BatchExpPro> list = batchExpProResponse.getBatchData();
        Response response = new Response();

        InterTestHttpServer interTestHttpServer = null;
        HttpServer httpServer = null;
        Express taking = null;
        ExpressProcess ep = null;

        StringBuilder ll = new StringBuilder();

        for (BatchExpPro aList : list) {
            interTestHttpServer = new InterTestHttpServer();
            httpServer = new HttpServer();
            taking = new Express();
            ep = new ExpressProcess();

            String customer = aList.getCustomer();
            String orgCode = aList.getOrgCode();
            String expPro = aList.getExpPro();
            String desOrgCode = aList.getDesOrgCode();

            try{
                /*获取单号*/
                String wayBillNo = tools.returnWaybillNo(orgCode);
                ll.append(wayBillNo).append("|");
                /*下单*/
                taking.setWaybill_no(wayBillNo);
                taking.setSource_org_code(orgCode);
                taking.setDes_org_code(desOrgCode);
                taking.setSeller("123456789");
                taking.setEmp_code(customer);

                expressDao.xiadan2(taking);

                /*设置单号信息，是否存在单号，如果不存在，则新增到数据库*/
                ep.setWaybill_no(wayBillNo);
                ep.setUser("system");
                queryByWaybillNo(ep);

                taking.setOp_user_code("00003520");
                taking.setOp_user_name("自动操作");

                tools.exp(expPro,expressDao,expressProcessImpl,taking,ep);

            }catch (Exception e){
                response.setCode("-1");
                response.setMessage(e.getMessage());
            }

        }
        response.setMessage(ll.toString());
        return response;
    }
    public Response batchAddUat(BatchExpProResponse batchExpProResponse){
        List<BatchExpPro> list = batchExpProResponse.getBatchData();
        Response response = new Response();

        InterTestHttpServer interTestHttpServer = null;
        HttpServer httpServer = null;
        Express taking = null;
        ExpressProcess ep = null;

        StringBuilder ll = new StringBuilder();

        for (BatchExpPro aList : list) {
            interTestHttpServer = new InterTestHttpServer();
            httpServer = new HttpServer();
            taking = new Express();
            ep = new ExpressProcess();

            String customer = aList.getCustomer();
            String orgCode = aList.getOrgCode();
            String expPro = aList.getExpPro();
            String desOrgCode = aList.getDesOrgCode();

            HttpClientResponse httpClient = new HttpClientResponse();
            Map<String,Object> map = tools.JsonStringToMap("{\n" +
                    "\"ChannelCode\":\"TAOBAO\",\n" +
                    "\"OrgCode\":\"" + orgCode + "\"\n" +
                    "}");

            try{
                /*获取单号*/
                String wayBillNo = httpClient.requestPost("http://jingang.msns.cn/yttsuat/GetWaybillNo",map);
                ll.append(wayBillNo).append("|");
                /*下单*/
                String msg = httpServer.weChatSendUat(customer,orgCode,wayBillNo,taking.getSeller(),desOrgCode);

                /*设置单号信息，是否存在单号，如果不存在，则新增到数据库*/
                ep.setWaybill_no(wayBillNo);
                ep.setUser("system");
                queryByWaybillNoUat(ep);

                taking.setWaybill_no(wayBillNo);
                taking.setEmp_code(customer);
                taking.setOp_user_code("00003520");
                taking.setOp_user_name("自动操作");

                tools.expUat(expPro,expressUatDao,expressProcessImpl,taking,ep);

            }catch (Exception e){
                response.setCode("-1");
                response.setMessage(e.getMessage());
            }

        }
        response.setMessage(ll.toString());
        return response;
    }

    public Map expDataImport(MultipartFile file) throws IOException,NullPointerException {
        InterTestHttpServer interTestHttpServer = null;
        HttpServer httpServer = null;
        Express taking = null;
        ExpressProcess ep = null;

        Map<String,String> map = new HashMap<>();
        map.put("code","0");
        map.put("msg","success");
        /*
         * file转输入流
         * 出现异常更改状态为 1
         */
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            map.put("code","1");
            map.put("msg","file转输入流异常");
        }
        HSSFWorkbook workbook = null;
        try{
            if (is != null)
                workbook = new HSSFWorkbook(is);
        }catch (IOException e){
            map.put("code","1");
            map.put("msg","解析SIT输入流异常");
        }

        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            StringBuilder index = new StringBuilder();
            StringBuilder waybill = new StringBuilder();
            String orgCode = null;
            String customer = null;
            String seller = null;
            String desOrgCode = null;
            String effective = null;
            String epp = null;
            for (int r = 1; r <= sheet.getLastRowNum(); r++){
                Row row = sheet.getRow(r);
                try{
                    if (row.getCell(0) != null)
                        orgCode = row.getCell(0).getStringCellValue();
                }catch (Exception e){
                    orgCode = "210077";
                    log.warn("SIT无物料发放网点，默认为210077");
                    index.append("无物料发放网点，默认为210077").append("<br>");
                }
                try{
                    customer = row.getCell(1).getStringCellValue();
                }catch (Exception e){
                    customer = "K21002107";
                    log.warn("SIT无物料绑定客户，默认为K21002107");
                    index.append("无物料绑定客户，默认为K21002107").append("<br>");
                }
                try{
                    seller = row.getCell(2).getStringCellValue();
                }catch (Exception e){
                    seller = "2575775285";
                    log.warn("SIT无绑定商家信息，默认为123456789");
                    index.append("无绑定商家信息，默认为123456789").append("<br>");
                }
                try{
                    desOrgCode = row.getCell(3).getStringCellValue();
                }catch (Exception e){
                    desOrgCode = "210045";
                    log.warn("SIT无下单目的网点，默认210045");
                    index.append("无下单目的网点，默认210045").append("<br>");
                }
                try{
                    effective = row.getCell(4).getStringCellValue();
                }catch (Exception e){
                    effective = "";
                    log.warn("SIT无时效类型，默认时效无关");
                    index.append("SIT无时效类型，默认时效无关").append("<br>");
                }
                try{
                    epp = row.getCell(5).getStringCellValue();
                }catch (Exception e){
                    log.error("导入模板中无走件信息");
                    map.put("code","1");
                    index.append("无SIT走件信息").append("<br>");
                    map.put("msg","fail");
                    map.put("waybill",index+"");
                    return map;
                }
                interTestHttpServer = new InterTestHttpServer();
                httpServer = new HttpServer();
                taking = new Express();
                ep = new ExpressProcess();

                try{
                    /*获取单号*/
                    String wayBillNo = tools.returnWaybillNo(orgCode);

                    taking.setEmp_code(customer);
                    taking.setSource_org_code(orgCode);
                    taking.setWaybill_no(wayBillNo);
                    taking.setSeller(seller);
                    taking.setDes_org_code(desOrgCode);

                    /*下单*/
//                    String msg = httpServer.weChatSend(customer,orgCode,wayBillNo,seller,desOrgCode);
                    expressDao.xiadan2(taking);

                    /*设置单号信息，是否存在单号，如果不存在，则新增到数据库*/
                    ep.setWaybill_no(wayBillNo);
                    ep.setUser("import");
                    queryByWaybillNo(ep);

//                    taking.setWaybill_no(wayBillNo);
//                    taking.setEmp_code(customer);
                    taking.setEffective_type_code(effective);
                    taking.setOp_user_code("00003520");
                    taking.setOp_user_name("导入");

                    tools.exp(epp,expressDao,expressProcessImpl,taking,ep);
                    waybill.append(wayBillNo).append("<br>");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            map.put("waybill",waybill + "");
        }else {
            map.put("code","1");
            System.out.println("取sheet异常");
        }
        log.info("----->自动走件完成，条数:" + map.get("waybill").split("<br>").length);
        return  map;
    }

    public BaseResponse selectDB(String sql){
        BaseResponse baseResponse = new BaseResponse();
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://10.129.220.15:3316/express";
            String user = "ytexp";
            String password = "ytexp";

            String USERNAMR = "YTSTL";
            String PASSWORD = "HOXWpMYx3o";
            String DRVIER = "oracle.jdbc.driver.OracleDriver";
            String URL = "jdbc:oracle:thin:@10.129.220.16:1521:UATMDM";

//            List list = tools.selectMySQLMessage(DRVIER,URL,USERNAMR,PASSWORD,sql);
//            baseResponse.setData(JSON.parseArray(JSON.toJSONString(list,SerializerFeature.WriteMapNullValue)));
        }catch (Exception e){
            baseResponse.setCode("0");
            baseResponse.setMsg(e.getMessage());
        }
        return baseResponse;
    }

    public Map expDataImportUat(MultipartFile file) throws IOException {
        InterTestHttpServer interTestHttpServer = null;
        HttpServer httpServer = null;
        Express taking = null;
        ExpressProcess ep = null;

        Map<String,String> map = new HashMap<>();
        map.put("code","0");
        map.put("msg","success");
        /*
         * file转输入流
         * 出现异常更改状态为 1
         */
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            map.put("code","1");
            System.out.println("file转输入流异常");
        }
        HSSFWorkbook workbook = null;
        try{
            if (is != null)
                workbook = new HSSFWorkbook(is);
        }catch (IOException e){
            map.put("code","1");
            System.out.println("解析UAT输入流异常");
        }

        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            StringBuilder index = new StringBuilder();
            String orgCode = null;
            String customer = null;
            String seller = null;
            String desOrgCode = null;
            String epp = null;
            for (int r = 1; r <= sheet.getLastRowNum(); r++){
                Row row = sheet.getRow(r);
                try{
                    orgCode = row.getCell(0).getStringCellValue();
                }catch (Exception e){
                    orgCode = "210077";
                    log.info("UAT无物料发放网点，默认为210077");
                    index.append("无物料发放网点，默认为210077");
                }
                try{
                    customer = row.getCell(1).getStringCellValue();
                }catch (Exception e){
                    customer = "K21002107";
                    log.info("UAT无物料绑定客户，默认为K21002107");
                    index.append("无物料绑定客户，默认为K21002107");
                }
                try{
                    seller = row.getCell(2).getStringCellValue();
                }catch (Exception e){
                    seller = "2575775285";
                    log.info("UAT无绑定商家信息，默认为123456789");
                    index.append("无绑定商家信息，默认为123456789");
                }
                try{
                    desOrgCode = row.getCell(3).getStringCellValue();
                }catch (Exception e){
                    desOrgCode = "210045";
                    log.info("UAT无下单目的网点，默认210045");
                    index.append("无下单目的网点，默认210045");
                }
                try{
                    epp = row.getCell(4).getStringCellValue();
                }catch (Exception e){
                    map.put("code","1");
                    index.append("无UAT走件信息");
                    map.put("msg",index.toString());
                    System.out.println("这里报错了！！");
                    return map;
                }

                String[] key = {"ChannelCode","OrgCode","waybillNo"};
                String[] value = {"TAOBAO",orgCode,"82".concat(tools.dateString()).concat(tools.fourRandom())};
                interTestHttpServer = new InterTestHttpServer();
                httpServer = new HttpServer();
                taking = new Express();
                ep = new ExpressProcess();

                try{
                    /*获取单号，83*/
                    String wayBillNo = interTestHttpServer.HttpGetofJSONString("http://jingang.msns" +
                                    ".cn/yttsuat/GetWaybillNo18",key,
                            value);
                    /*下单*/
                    String msg = httpServer.weChatSendUat(customer,orgCode,wayBillNo,seller,desOrgCode);

                    /*设置单号信息，是否存在单号，如果不存在，则新增到数据库*/
                    ep.setWaybill_no(wayBillNo);
                    ep.setUser("import");
                    queryByWaybillNoUat(ep);

                    taking.setWaybill_no(wayBillNo);
                    taking.setEmp_code(customer);
                    taking.setOp_user_code("00003520");
                    taking.setOp_user_name("UAT导入");

                    tools.expUat(epp,expressUatDao,expressProcessImpl,taking,ep);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            map.put("code","1");
            System.out.println("取sheet异常");
        }
        System.out.println(map.toString());
        return  map;
    }
}
