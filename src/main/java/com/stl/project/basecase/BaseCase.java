package com.stl.project.basecase;

import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.excel.ReadExcelUtil;
import com.stl.project.tools.seleniumutil.SeleniumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.Cookie;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BaseCase {

    private List<Map<String,String>> list = null;
    protected Set<Cookie> cookies = null;
    /**
     * 开始前步骤：
     *     读取Excel文档
     *     获取Cookies(可选)
     */
//    @BeforeClass
    public void startTest() throws Exception{
        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
        System.out.println("Before------>>read excel");
        InputStream is = readExcelUtil.inputFile("D:\\Data.xls");
        Workbook workbook = readExcelUtil.createWorkBook(is,".xls");
        Sheet sheet = readExcelUtil.getSheet(workbook,0);
        list = readExcelUtil.getSheetContent(sheet,1);
        log.info("获取接口信息："+list.size()+"个");
        SeleniumUtil seleniumUtil = new SeleniumUtil();
        JSONChange jsonChange = new JSONChange();

        seleniumUtil.openBrowser();
        seleniumUtil.openWeb("http://jingangtest.yto56.com.cn/mdm/logout.action");
        seleniumUtil.submit("login_mode");
        seleniumUtil.getVildateImage("//*[@id=\"vildateImg\"]");
        Map<String,Object> write = jsonChange.JsonStringToMap("{\n" +
                "\t\"id\": {\n" +
                "\t\t\"userName\": \"admin\",\n" +
                "\t\t\"password\": \"Aa123456\",\n" +
                "\t\t\"varification\": \"" + seleniumUtil.getVerificationCode("") + "\"\n" +
                "\t}\n" +
                "}");
        Map<String,Object> click = jsonChange.JsonStringToMap("{\n" +
                "\t\"id\": {\n" +
                "\t\t\"1\": \"submitbtn\",\n" +
                "\t}\n" +
                "}");
        seleniumUtil.writeValue(write);
        seleniumUtil.submit(click);
        cookies = seleniumUtil.getCookie();
        seleniumUtil.closeBrowser();
//        Map<String,Object> login_message = jsonChange.JsonStringToMap(list.get(0).get("login_param"));
//        Object write = login_message.get("write");
//        Object click = login_message.get("click");
//        seleniumUtil.writeValue(jsonChange.JsonStringToMap(write.toString()));
//        seleniumUtil.submit(jsonChange.JsonStringToMap(click.toString()));
    }
    /**
     * 结束后操作
     *     输出结果文档
     */
    @AfterClass
    public void endTest(){
        System.out.println("After------>>");
    }

    @DataProvider(name = "dts")
    public Object[][] getData(){
        DataDriver dataDriver = new DataDriver();
        return dataDriver.getData(list);
    }
}
