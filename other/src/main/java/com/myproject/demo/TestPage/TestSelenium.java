package com.myproject.demo.TestPage;

import com.myproject.demo.utils.BrowserConfig;
import com.myproject.demo.utils.HttpClientResponse;
import com.myproject.demo.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestSelenium {
    public static void  main(String[] args) throws IOException {
//        BrowserConfig browserConfig = new BrowserConfig();
//        browserConfig.openBrowser();
//        browserConfig.openWeb("http://10.130.36.222:8000/login");
//        browserConfig.writeValue("//*[@id=\"j_username\"]","123");
        BrowserConfig browserConfig = new BrowserConfig();



        browserConfig.openBrowser();browserConfig.openWeb("http://jingangtest.yto56.com.cn/mdm/logout.action");
        browserConfig.submit("login_mode");
        browserConfig.getVildateImage("//*[@id=\"vildateImg\"]");
        Map<String,Map<String,Object>> map = new HashMap<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("userName","admin");
        map1.put("password","Aa123456");
        map1.put("varification",browserConfig.getVerificationCode(""));
        map.put("id",map1);
        browserConfig.writeValue(map);
        browserConfig.submit("submitbtn");
//        browserConfig.openWeb(list.get(0));

        System.out.println(browserConfig.getCookie().toString().replace("[","").replace("]",""));

        HttpClientResponse httpClientResponse = new HttpClientResponse();
        Tool tool = new Tool();

        Map<String,Object> parameterMap = new HashMap<String,Object>();
        parameterMap.put("executeOrgCode", "999999");
        parameterMap.put("fromOrgCode", null);
        parameterMap.put("toCenterCode", null);
        parameterMap.put("priceTypeId", null);
        parameterMap.put("priceRuleId", null);
        parameterMap.put("scanRuleType", null);
        parameterMap.put("expressContentCode", "ALL");
        parameterMap.put("status", "TEMP");
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        parameterMap.put("enableDate", sdf.format(dt));
        parameterMap.put("isSeamless", null);

        httpClientResponse.requestPost
                ("http://jingangtest.yto56.com.cn/transferfreightweb/transferfreight/frePriceDetail_allSubmit.action",
                        parameterMap,browserConfig.getCookie());
    }
}
