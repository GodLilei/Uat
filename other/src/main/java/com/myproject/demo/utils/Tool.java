package com.myproject.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myproject.demo.entity.Express;
import com.myproject.demo.entity.ExpressProcess;
import com.myproject.demo.impl.ExpressDaoImpl;
import com.myproject.demo.impl.ExpressProcessImpl;
import com.myproject.demo.impl.ExpressUatDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Slf4j
@Component
public class Tool {

    /**
     *
     * @return 今天的日期
     */
    public String nowDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @return 时分秒
     */
    public String dateString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @return 年月日字符串
     */
    public String userDateString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @return 四位随机数
     */
    public String fourRandom(){
        String four = (int)(Math.random()*10000) + "";
        while (four.length() != 4){
            four = (int)(Math.random()*10000) + "";
        }
        return four;
    }

    /**
     * 返回几天前或者几年前的数据
     * @param value 天:day,年:year,秒
     * @param num 天数
     * @return 日期
     */
    public String dateOfAfter(String value,int num){
        Calendar cal=Calendar.getInstance();
        switch (value){
            case "day":cal.add(Calendar.DATE,num);break;
            case "year":cal.add(Calendar.YEAR,num);break;
            case "second":cal.add(Calendar.SECOND,num);break;
            default:break;
        }
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    /**
     * SIT环境自动走件
     * @param expPro 走件描述
     * @param expressDao dao
     * @param expressProcessImpl 本地
     * @param taking 实体
     * @param ep 操作步骤
     * @throws InterruptedException 异常
     */
    public void exp(String expPro,ExpressDaoImpl expressDao,ExpressProcessImpl expressProcessImpl,Express taking,
                    ExpressProcess ep) throws InterruptedException, ImportException {
        log.info("----->自动走件操作开始:----------------------------------->");
        log.info("----->" + expPro);
        String[] step = expPro.split("\\|");
        for (String s:step){
            if (!"".equals(s)){
                switch (s.substring(0, 2)) {
                    case "取件": {
                        String code = s.substring(2,8);
                        double weight = 10.00;
                        if (s.length() > 9){
                            weight = Double.parseDouble(s.substring(9,s.length()-1));
                        }
                        taking.setInput_weight(weight);
                        taking.setSource_org_code(code);
                        taking.setTaking_org_code(code);
                        taking.setDes_org_code(code);
                        expressDao.extract(taking);
                        ep.setOperate("|取件" + code + "[" + weight + "]");
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "揽收": {
                        String code = s.substring(2,8);
                        double weight = 10.00;
                        if (s.length() > 9){
                            weight = Double.parseDouble(s.substring(9,s.length()-1));
                        }
                        taking.setInput_weight(weight);
                        taking.setSource_org_code(code);
                        taking.setTaking_org_code(code);
                        taking.setDes_org_code(code);
                        expressDao.taking(taking);
                        ep.setOperate("|揽收" + code + "[" + weight + "]");
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "建包": {
                        String code = s.substring(2);
                        taking.setBuild_org_code(code);
                        taking.setBuild_org_type(returnType(code));
                        taking.setPkg_no("WB1230396056");
                        taking.setExpress_content_code("PKG");
                        expressDao.build(taking);
                        ep.setOperate("|建包" + code);
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "拆包": {
                        String code = s.substring(2,8);
                        double weight = 10.00;
                        if (s.length() > 9){
                            weight = Double.parseDouble(s.substring(9,s.length()-1));
                        }
                        taking.setInput_weight(weight);
                        taking.setBuild_org_code(code);
                        taking.setBuild_org_type(returnType(code));
                        taking.setPkg_no("WB1230396056");
                        expressDao.unpack(taking);
                        ep.setOperate("|拆包" + code);
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "上车": {
                        String code = s.substring(2,8);
                        double weight = 10.00;
                        if (s.length() > 9){
                            weight = Double.parseDouble(s.substring(9,s.length()-1));
                        }
                        taking.setOut_weight(weight);
                        taking.setOut_org_code(code);
                        taking.setOut_org_type(returnType(code));
                        taking.setCar_no("CQ12344321");
                        taking.setExpress_content_code("PKG");
                        expressDao.shangche(taking);
                        ep.setOperate("|上车" + code + "[" + weight + "]");
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "下车": {
                        String code = s.substring(2,8);
                        double weight = 10.00;
                        if (s.length() > 9){
                            weight = Double.parseDouble(s.substring(9,s.length()-1));
                        }
                        taking.setOut_weight(weight);
                        taking.setOut_org_code(code);
                        taking.setOut_org_type(returnType(code));
                        taking.setCar_no("CQ12344321");
                        taking.setExpress_content_code("PKG");
                        expressDao.xiache(taking);
                        ep.setOperate("|下车" + code + "[" + weight + "]");
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "计泡": {
                        String code = s.substring(2,8);
                        String[] param = s.substring(9,s.length()-1).split(",");
                        taking.setJipao_org_code(code);
                        taking.setJipao_org_type(returnType(code));
                        taking.setCar_no("CQ12344321");
                        taking.setPkg_height(Double.parseDouble(param[0]));
                        taking.setPkg_length(Double.parseDouble(param[1]));
                        taking.setPkg_width(Double.parseDouble(param[2]));
                        Double volume = (Double.parseDouble(param[0])*Double.parseDouble(param[1])*Double
                                .parseDouble(param[2]))/5000.00;
                        taking.setVolume_weight(volume);
                        taking.setExpress_content_code("PKG");
                        expressDao.bubble(taking);
                        ep.setOperate("|计泡" + code + "[" + volume + "]");
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "退回": {
                        String code = s.substring(2,8);
                        double weight = 10.00;
                        if (s.length() > 9){
                            weight = Double.parseDouble(s.substring(9,s.length()-1));
                        }
                        taking.setOut_weight(weight);
                        taking.setOut_org_code(code);
                        taking.setOut_org_type(returnType(code));
                        taking.setCar_no("CQ12344321");
                        expressDao.out_return(taking);
                        ep.setOperate("|退回" + code + "[" + weight + "]");
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "派件": {
                        String code = s.substring(2);
                        taking.setHandon_org_code(code);
                        expressDao.handon(taking);
                        ep.setOperate("|派件" + code);
                        expressProcessImpl.queryByWaybillNo(ep);
                        Thread.sleep(1000);
                        break;
                    }
                    case "签收": {
                        int flag = 0;
                        String code = s.substring(2);
                        taking.setSignature_org_code(code);
                        taking.setSignature_name("签收自动");
//                        log.info("-----》》查询派件开始(5s/次)：" + taking.getWaybill_no());
//                        while (expressDao.checkHandon(taking.getWaybill_no()) == 0 && flag <= 4){
//                            Thread.sleep(5*1000);
//                            flag++;
//                        }
//                        log.info("-----》》查询派件结束：查询次数：" + flag);
                        expressDao.signature(taking);
                        ep.setOperate("|签收" + code);
                        expressProcessImpl.queryByWaybillNo(ep);
                        break;
                    }
                    default:throw new ImportException("走件操作异常，出现错误描述:",s.substring(0, 2));
                }
            }
        }
    }

    /**
     * UAT环境自动走件
     * @param expPro 走件描述
     * @param expressUatDao dao
     * @param expressProcessImpl 本地
     * @param taking 实体
     * @param ep 操作步骤
     * @throws InterruptedException 异常
     */
    public void expUat(String expPro, ExpressUatDaoImpl expressUatDao, ExpressProcessImpl expressProcessImpl, Express
            taking, ExpressProcess ep) throws InterruptedException{
        log.info("----->自动走件操作开始:----------------------------------->");
        log.info("----->" + expPro);
        String[] step = expPro.split("\\|");
        for (String s:step){
            switch (s.substring(0, 2)) {
                case "揽收": {
                    String code = s.substring(2,8);
                    double weight = 10.00;
                    if (s.length() > 9){
                        weight = Double.parseDouble(s.substring(9,s.length()-1));
                    }
                    taking.setInput_weight(weight);
                    taking.setSource_org_code(code);
                    taking.setTaking_org_code(code);
                    taking.setDes_org_code(code);
                    expressUatDao.taking(taking);
                    ep.setOperate("|揽收" + code + "[" + weight + "]");
                    expressProcessImpl.queryByWaybillNoUat(ep);
                    Thread.sleep(1000);
                    break;
                }
                case "建包": {
                    String code = s.substring(2);
                    taking.setBuild_org_code(code);
                    taking.setBuild_org_type("TRANSFER_CENTER");
                    taking.setPkg_no("WB1230396056");
                    expressUatDao.build(taking);
                    ep.setOperate("|建包" + code);
                    expressProcessImpl.queryByWaybillNoUat(ep);
                    Thread.sleep(1000);
                    break;
                }
                case "下车": {
                    String code = s.substring(2,8);
                    double weight = 10.00;
                    if (s.length() > 9){
                        weight = Double.parseDouble(s.substring(9,s.length()-1));
                    }
                    taking.setInput_weight(weight);
                    taking.setOut_org_code(code);
                    taking.setOut_org_type("TRANSFER_CENTER");
                    taking.setCar_no("CQ12344321");
                    expressUatDao.xiache(taking);
                    ep.setOperate("|下车" + code + "[" + weight + "]");
                    expressProcessImpl.queryByWaybillNoUat(ep);
                    Thread.sleep(1000);
                    break;
                }
                case "退回": {
                    String code = s.substring(2,8);
                    double weight = 10.00;
                    if (s.length() > 9){
                        weight = Double.parseDouble(s.substring(9,s.length()-1));
                    }
                    taking.setInput_weight(weight);
                    taking.setOut_org_code(code);
                    taking.setOut_org_type("TRANSFER_CENTER");
                    taking.setCar_no("CQ12344321");
                    expressUatDao.out_return(taking);
                    ep.setOperate("|退回" + code + "[" + weight + "]");
                    expressProcessImpl.queryByWaybillNoUat(ep);
                    Thread.sleep(1000);
                    break;
                }
                case "派件": {
                    String code = s.substring(2);
                    taking.setHandon_org_code(code);
                    expressUatDao.handon(taking);
                    ep.setOperate("|派件" + code);
                    expressProcessImpl.queryByWaybillNoUat(ep);
                    Thread.sleep(1000);
                    break;
                }
                case "签收": {
                    int flag = 0;
                    String code = s.substring(2);
                    taking.setSignature_org_code(code);
                    taking.setSignature_name("签收自动");
//                    log.info("-----》》查询派件开始(5s/次)：" + taking.getWaybill_no());
//                    while (expressUatDao.checkHandon(taking.getWaybill_no()) == 0 && flag <= 10){
//                        Thread.sleep(5000);
//                        flag++;
//                    }
//                    log.info("-----》》查询派件结束：查询次数：" + flag);
                    expressUatDao.signature(taking);
                    ep.setOperate("|签收" + code);
                    expressProcessImpl.queryByWaybillNoUat(ep);
                    break;
                }
            }
        }
    }

    /**
     * 模板下载
     * @param response 响应体
     * @throws IOException IO异常处理
     */
    public void template_downLoad(HttpServletResponse response) throws IOException {
        /*
         * 创建表格
         * */
        log.info("模板下载中------------>>>");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("走件信息录入");

        HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFFont fontStyle=workbook.createFont();
        fontStyle.setFontName("宋体");
        fontStyle.setFontHeightInPoints((short)16);
        cellStyle.setFont(fontStyle);

        HSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        /*
         * 设置列宽
         */
        sheet.setColumnWidth(0,256*18);
        sheet.setColumnWidth(1,256*18);
        sheet.setColumnWidth(2,256*18);
        sheet.setColumnWidth(3,256*18);
        sheet.setColumnWidth(4,256*18);
        sheet.setColumnWidth(5,256*80);

        /*
         * 设置行高
         */
        sheet.setDefaultRowHeight((short)(1.5*256));

        /*
         * 表名
         */
        String fileName = "Express"  + ".xls";

        //headers表示excel表中第一行的表头
        String[] headers = { "物料网点", "客户", "商家", "目的网点","时效", "走件操作"};
        //在excel表中添加表头
        HSSFRow row = sheet.createRow(0);

        row.setRowStyle(cellStyle);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);
        }

        for(int i=1;i<2;i++){
            HSSFRow row1 = sheet.createRow(i);
            row1.setRowStyle(cellStyle);
        }
        /*
         * 回收内存
         */
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        log.info("----->模板下载成功，，v，");
    }

    /**
     * json字符串转MAP
     * @param jsonString json字符串
     * @return MAP
     */
    public Map<String,Object> JsonStringToMap(String jsonString){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        JSONArray jsonArray = JSON.parseArray("["+jsonString+"]");
        for (Object aJsonArray : jsonArray) {
            JSONObject obj = (JSONObject) JSONObject.toJSON(aJsonArray);//传过来的值的size为1，去掉外层循环
            //int i = 0;
//            System.out.println(obj.size());
//            System.out.println(obj.toString());
            for (Map.Entry<String, Object> entry : obj.entrySet()) {
                resultMap.put(entry.getKey(),entry.getValue());
            }
        }
        return resultMap;
    }

    /**
     * JSON字符串转JSONObject
     * @param jsonString json字符串
     * @return JSON类
     */
    public JSONObject JsonStringToJson(String jsonString){
        return JSONObject.parseObject(jsonString);
    }

    /**返回单号 方法一*/
    public String returnWaybillNo(String orgCode){
        HttpClientResponse httpClient = new HttpClientResponse();
        Date d = new Date();
        String c = String.valueOf(d.getTime());
        Map<String,Object> map = JsonStringToMap("{\"type\":\"1\",\n" +
                "\"orgCode\":\"" + orgCode + "\",\n" +
                "\"waybillNo\":\"80" + c.substring(c.length()-10) + "\"}");
        return httpClient.requestPost("http://jingang.yto56.com.cn/matcn/waybillNoEncodeServlet",map);
    }

    /**返回单号 方法二*/
    public String returnWaybillNo2(String orgCode){
        HttpClientResponse httpClient = new HttpClientResponse();
        Map<String,Object> map = JsonStringToMap("{\n" +
                "\"ChannelCode\":\"TAOBAO\",\n" +
                "\"OrgCode\":\"" + orgCode + "\"\n" +
                "}");
        return httpClient.requestPost("http://jingang.msns.cn/yttssit/GetWaybillNo",map);
    }

    /**查询数据库数据*/
//    public List selectMySQLMessage(String driver,String url,String user,String password,String sql) throws java.sql
//            .SQLException{
//        Connection con = null;
//        Statement stat = null;
//        ResultSet resultSet = null;
//        List list = new ArrayList();
//        try {
//            Class.forName(driver);
//            con = DriverManager.getConnection(url,user,password);
//            if (!con.isClosed()){
//                log.info("success connection!!!");
//            }
//            stat = con.createStatement();
//            resultSet = stat.executeQuery(sql);
//            list = resultSetToList(resultSet);
//        }catch (Exception e){
//            assert stat != null;
//            stat.close();
//            con.close();
//            log.error("----->已关闭" + e.getMessage());
//        }finally {
//            if (resultSet != null)
//                resultSet.close();
//            if (stat != null)
//                stat.close();
//            if (con != null)
//                con.close();
//        }
//        return list;
//    }

    /**ResultSet转List*/
    private List resultSetToList(ResultSet rs) throws java.sql.SQLException {
        if (rs == null)
            return Collections.EMPTY_LIST;
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowData;
        try{
            while (rs.next()) {
                rowData = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            log.info("----->数据转换成功：list:" + list.size());
        }catch (Exception e){
            rs.close();
            log.error("发生错误：" + e.getMessage());
        }
        rs.close();
        return list;
    }

    /**根据code输出type*/
    private String returnType(String code){
        Map<String ,String> map = new HashMap<>();
        map.put("210045","BRANCH");
        map.put("210077","BRANCH");
        map.put("766001","BRANCH");
        map.put("210901","TRANSFER_CENTER");
        map.put("200901","TRANSFER_CENTER");
        map.put("100901","TRANSFER_CENTER");
        map.put("210290","SUB_DEPARTMENT");
        map.put("210046","SUB_DEPARTMENT");
        map.put("766002","SUB_DEPARTMENT");

        if (map.containsKey(code)){
            return map.get(code);
        }
        return "BRANCH";
    }
}
