package com.myproject.demo.TestPage;

import com.myproject.demo.controller.OldUatController;
import com.myproject.demo.entity.Express;
import com.myproject.demo.impl.ActiveWaybillImpl;
import com.myproject.demo.impl.ExpressDaoImpl;
import com.myproject.demo.impl.ExpressProcessImpl;
import com.myproject.demo.services.OldUatServices;
import com.myproject.demo.services.TodayThingsService;
import com.myproject.demo.utils.BrowserConfig;
import com.myproject.demo.utils.HttpClientResponse;
import com.myproject.demo.utils.ImportException;
import com.myproject.demo.utils.Tool;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

public class TestSout {


    public static void main(String[] args)  throws Exception {


        Map<String,Map<String,String>> mapMap = new HashMap<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("123","123");
        map1.put("456","456");
        mapMap.put("123",map1);
        mapMap.put("111",map1);
        for (Map.Entry<String, Map<String, String>> mm:mapMap.entrySet()){
            System.out.println(mm.getValue());
        }
//        HttpClientResponse httpClientResponse = new HttpClientResponse();
//        httpClientResponse.requestGet("http://10.130.36.222:8000/job/Business/build?delay=0sec",null);
//        String HTTP_URL = "https://oapi.dingtalk.com/robot/send?access_token=abf25116285fad549df7b4ee6effc2e5b74c40c9e95b84591c1ffd7e13e4ff52";
//        //https://oapi.dingtalk.com/robot/send?access_token=f2c94de142fc59b69da6fae8880400bf9892513247d21679d245c18a91bfcff1
//        String HTTP_URL1 = "https://oapi.dingtalk.com/robot/send?access_token=f2c94de142fc59b69da6fae8880400bf9892513247d21679d245c18a91bfcff1";
//
//        HttpClientResponse httpClientResponse = new HttpClientResponse();
//        httpClientResponse.requestPost(HTTP_URL,"{\n" +
//                "    \"msgtype\": \"text\", \n" +
//                "    \"text\": {\n" +
//                "        \"content\": \"日报提醒小助手：写日报！！！\"\n" +
//                "    }, \n" +
//                "    \"at\": {\n" +
//                "        \"atMobiles\": [\n" +
//                "            \"15116231720\", \n" +
//                "        ], \n" +
//                "        \"isAtAll\": true\n" +
//                "    }\n" +
//                "}");

//        httpClientResponse.requestPost(HTTP_URL,"{\n" +
//                "    \"actionCard\": {\n" +
//                "        \"title\": \"写日报，不写扣钱，还要谈话！！！\", \n" +
//                "        \"text\": \"![screenshot](@lADOpwk3K80C0M0FoA) \n" +
//                " ### 日报月报，天天要报 \n" +
//                " 写啊写啊写日报，写了一个大日报，巴扎黑！\", \n" +
//                "        \"hideAvatar\": \"0\", \n" +
//                "        \"btnOrientation\": \"0\", \n" +
//                "        \"btns\": [\n" +
//                "            {\n" +
//                "                \"title\": \"ba说了,写！\", \n" +
//                "                \"actionURL\": \"https://landray.dingtalkapps.com/alid/app/report/home.html?showmenu=false&dd_share=false&corpid=ding5be5ec1ba001ae6735c2f4657eb6378f\"\n" +
//                "            }, \n" +
//                "            {\n" +
//                "                \"title\": \"钱多，不写\", \n" +
//                "                \"actionURL\": \"https://www.dingtalk.com/\"\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }, \n" +
//                "    \"msgtype\": \"actionCard\"\n" +
//                "}");


//        ExpressDaoImpl expressDao = new ExpressDaoImpl();
//        Express express = new Express();
//        expressDao.test(express);

//        Tool tool = new Tool();
//        System.out.println(tool.dateOfAfter("second",-100));
//        BrowserConfig browserConfig = new BrowserConfig();
//        browserConfig.openBrowser();
//        browserConfig.openWeb("http://jingangtest.yto56.com.cn/mdm/logout.action");
//        browserConfig.submit("login_mode");
//        browserConfig.getVildateImage("//*[@id=\"vildateImg\"]");
//        browserConfig.writeValue("admin","Aa123456",browserConfig.getVerificationCode(""));
//        browserConfig.submit("submitbtn");

//        try {
//            int x=div(1, -1);
//            System.out.println("x:"+x);
//        }catch (ImportException e) {
//            System.out.println(e.toString());
////            System.out.println("除数出现负数了");
//            System.out.println("错误的负是："+e.getValue());
//        }
//        System.out.println("over");

//        String s = "计泡210901[10,20,30]";
//        String[] param = s.substring(9,s.length()-1).split(",");
//
//        for (String s1:param){
//            System.out.println(s1);
//        }
//        TestSelenium testSelenium = new TestSelenium();
//        testSelenium.openBrowser();
//        testSelenium.openWeb("http://jingangtest.yto56.com.cn/mdm/logout.action");
//        testSelenium.submit("login_mode");
//        testSelenium.getVildateImage("//*[@id='vildateImg']");
//        String code = testSelenium.getVerificationCode("");
//        testSelenium.writeValue("admin","Aa123456",code);
//        testSelenium.submit("submitbtn");
//        Set<Cookie> cookies = testSelenium.getCookie();
//        testSelenium.closeBrowser();
//        HttpClientResponse httpClient = new HttpClientResponse();
////        Map<String,Object> map1 = tool.JsonStringToMap("");
//        Map<String,Object> map2 = new HashMap<>();
//        map2.put("requestParameter","{ \"orgCode\":\"210901\", \"fromOrgCode\":\"210049\", \"toOrgCode\":\"766001\", " +
//                "\"priceTypeId\":\"0026\", \"weight\":\"13\", \"enableDate\":\"2019-06-10\", " +
//                "\"effectiveTypeId\":\"T010\", \"expressTypeId\":\"DOC\", \"scanRuleType\":\"\", " +
//                "\"virtualParts\":\"false\",\"innerPackage\":false}");
//        String a = httpClient.requestPost
//                ("http://172.16.51.57:8080/transferfreightweb/interfaceQuery/queryCheckOrgInfo.action",map2);
//        System.out.println(a);
//        if ("WB21045".contains("WB") && "DZB123123".contains("DZB")){
////            System.out.println("11");
////        }
//        String a = "sdjkghlkahsdggdfsjkldfhsall";
//        System.out.println(a.substring(0,a.length()-3));

//        TestHttpGet testHttpGet = new TestHttpGet();
//        long startTime = new Date().getTime();
//        String a = testHttpGet.HttpGetofJSONString("http://jingangtest.yto56.com" +
//                ".cn/transferfreightweb/transferfreight/transferPriceAuditList.action?menuId=8a015df1657fb15c01657fbbf6190623");
//        long endTime = new Date().getTime();
//        System.out.println(endTime-startTime);
//        System.out.println(a);

//        String sd = "asasasasasasasas";
//        int bb = sd.indexOf("a");
//        System.out.println(bb);
//        String a = "ajksthhfdshi";
//        System.out.println(a.contains("ajk"));

//        JSONObject a = tool.StringToJson("{\n" +
//                "\"name\":\"lilei\"\n" +
//                "}");
//        System.out.println(a.get("name"));

//        File file = new File("otherResource/test.txt");
//        System.out.println(file.exists());
//        Date d = new Date();
//        String c = String.valueOf(d.getTime());
//        Map<String,Object> map = tool.JsonStringToMap("{\n" +
//                "\"executeOrgCode\":\"999999\",\n" +
//                "\"fromOrgCode\":\"\",\n" +
//                "\"toCenterCode\":\"\",\n" +
//                "\"priceTypeId\":\"\",\n" +
//                "\"priceRuleId\":\"\",\n" +
//                "\"effectiveTypeId\":\"ALL\",\n" +
//                "\"scanRuleType\":\"\",\n" +
//                "\"expressContentCode\":\"ALL\",\n" +
//                "\"lineType\":\"\",\n" +
//                "\"status\":\"TEMP\",\n" +
//                "\"enableDate\":\"2019-06-24\",\n" +
//                "\"isSeamless\":\"\"\n" +
//                "}");
//        String waybillNo = httpClient.requestPost("http://jingangtest.yto56.com" +
//                ".cn/transferfreightweb/transferfreight/frePriceDetail_allSubmit.action",map,cookies);
//        System.out.println(waybillNo);
//        System.out.println(c.substring(c.length()-10));
//        HttpServer httpServer = new HttpServer();
//        httpServer.weChatSend("K21002107","210077",waybillNo,"123456789","766001");
//        String waybill_no = "340000249500";
//        String temp = waybill_no.substring(waybill_no.length()-3);
//        System.out.println(temp);
//        System.out.println(tool.dateOfAfter("year",2));
//        long a = dateToStamp("2019-05-31 19:18:19");
//        long b = dateToStamp("2019-05-30 17:33:27");
//        System.out.println(a + "---" + b);
//        System.out.println(((a-b)/(1000.0*60*60))*1.5/36.0);
//        InetAddress ia = InetAddress.getLocalHost();
//        System.out.println(ia);
//        getLocalMac(ia);
//        System.out.println(getMACAddress("172.16.51.37"));
    }

//    public static int div(int a, int b) throws ImportException{
//        if(b<0) {
//            throw new ImportException("出现了除数是负数的情况",b+"");//手动通过throw关键字抛出一个自定义异常对象。
//        }
//        return a/b;
//    }

//    private static long dateToStamp(String s) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = simpleDateFormat.parse(s);
//        return date.getTime();
//    }
//    public static String getMACAddress(String ip) throws Exception {
//        String line = "";
//        String macAddress = "";
//        final String MAC_ADDRESS_PREFIX = "MAC Address = ";
//        final String LOOPBACK_ADDRESS = "127.0.0.1";
//        //如果为127.0.0.1,则获取本地MAC地址。
//        if (LOOPBACK_ADDRESS.equals(ip)) {
//            InetAddress inetAddress = InetAddress.getLocalHost();
//            //貌似此方法需要JDK1.6。
//            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
//            //下面代码是把mac地址拼装成String
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < mac.length; i++) {
//                if (i != 0) {
//                    sb.append("-");
//                }
//                //mac[i] & 0xFF 是为了把byte转化为正整数
//                String s = Integer.toHexString(mac[i] & 0xFF);
//                sb.append(s.length() == 1 ? 0 + s : s);
//            }
//            //把字符串所有小写字母改为大写成为正规的mac地址并返回
//            macAddress = sb.toString().trim().toUpperCase();
//            return macAddress;
//        }
//        //获取非本地IP的MAC地址
//        try {
//            Runtime r = Runtime.getRuntime();
//            String scancmd="nbtstat -A ";
//            File file = new File("C:\\Windows\\SysWOW64"); //此文件夹只在64位系统中存在    32位系统中只有System32
//            if(file.exists()){
//                scancmd = "c:\\Windows\\sysnative\\nbtstat.exe -A ";
//            }
//            Process p = r.exec(scancmd+ip);
//            InputStreamReader isr = new InputStreamReader(p.getInputStream());
//            BufferedReader br = new BufferedReader(isr);
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//                int index = line.indexOf(MAC_ADDRESS_PREFIX);
//                if (index != -1) {
//                    macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
//                }
//            }
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace(System.out);
//        }
//        return macAddress;
//    }

}
