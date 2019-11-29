package com.stl.project.tools.seleniumutil;

import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.exceptionutil.SelenuimNameException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {
    private JSONChange jsonChange = new JSONChange();
    private ChromeDriver browser;
    /**
     * 打开浏览器
     */
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver","F:/myproject/testBranch/chromedriver.exe");
        browser = new ChromeDriver();
        maxBrowser();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    private void maxBrowser() {
        this.browser.manage().window().maximize();
    }
    /**
     * 打开网页
     * @param url 路径
     */
    public void openWeb(String url){
        browser.get(url);
    }
    /**
     * 关闭当前窗口
     */
    public void closeBrowser() {
        this.browser.close();
    }
    /**
     * 关闭浏览器
     */
    public void quitBrowser() {
        this.browser.quit();
    }
    /**
     * 将图片保存到本地
     * @param xPath 图片的xPath路径
     * @throws IOException 异常
     */
    public void getVildateImage(String xPath)throws IOException {
        WebElement ele = browser.findElement(By.xpath(xPath));//得到验证码元素
        File screenshot = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
        // Get entire page screenshot
        BufferedImage fullImg=ImageIO.read(screenshot);
        // Get the location of element on the page
        org.openqa.selenium.Point point = ele.getLocation();
        // Get width and height of the element
        int eleWidth= ele.getSize().getWidth();
        int eleHeight= ele.getSize().getHeight();
        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        // Copy the element screenshot to disk
        File screenshotLocation= new File("F:/myproject/stl_project/test.png");
        FileUtils.copyFile(screenshot, screenshotLocation);
    }
    /**
     * 输入登录元素
     * @param param 数据输入参数 key:XPath,name,id。value:对应的Key和Value
     */
    public void writeValue(Map<String,Object> param){
        Map<String,Object> var = null;
        if (param != null && param.size()>0){
            for(Map.Entry<String, Object> map:param.entrySet()){
                var = jsonChange.JsonStringToMap(map.getValue().toString());
                if ("xpath".equals(map.getKey())){
                    for (Object o:var.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.xpath((String)entry.getKey())).clear();
                        browser.findElement(By.xpath((String)entry.getKey())).sendKeys((String)entry.getValue());
                    }
                }else if ("name".equals(map.getKey())){
                    for (Object o:var.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.name((String)entry.getKey())).clear();
                        browser.findElement(By.name((String)entry.getKey())).sendKeys((String)entry.getValue());
                    }
                }else if ("id".equals(map.getKey())){
                    for (Object o:var.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.id((String)entry.getKey())).clear();
                        browser.findElement(By.id((String)entry.getKey())).sendKeys((String)entry.getValue());
                    }
                }
            }
        }
//        browser.findElement(By.id("form")).submit();
    }
    /**
     * 点击事件
     * @param id 点击id
     */
    public void submit(String id){
        browser.findElement(By.id(id)).click();
    }
    /**
     * 点击事件
     * @param param 参数
     */
    public void submit(Map<String,Object> param) throws SelenuimNameException {
        Map<String,Object> var = null;
        if (param != null && param.size()>0){
            for(Map.Entry<String, Object> map:param.entrySet()){
                var = jsonChange.JsonStringToMap(map.getValue().toString());
                if ("xpath".equals(map.getKey())){
                    for (Object o:var.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.xpath((String)entry.getValue())).click();
                    }
                }else if ("name".equals(map.getKey())){
                    for (Object o:var.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.name((String)entry.getValue())).click();
                    }
                }else if ("id".equals(map.getKey())){
                    for (Object o:var.entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.id((String)entry.getValue())).click();
                    }
                }else {
                    throw new SelenuimNameException("selenium查找属性异常",map.getKey());
                }
            }
        }
    }
    /**
     * 将图片转换为验证码
     * @param path 本地图片路径
     * @return 返回验证码
     */
    public String getVerificationCode(String path) {
        if (path.equals("")){
            path = "F:/myproject/stl_project/test.png";
        }
        File imageFile = new File(path);
        ITesseract instance = new Tesseract();//调用Tesseract
//        URL url = ClassLoader.getSystemResource("tessdata");//获得Tesseract的文字库
//        String tesspath = url.getPath().substring(1);
        instance.setDatapath("F:/myproject/testBranch/demo/target/classes/tessdata");//进行读取，默认是英文，如果要使用中文包，加上instance.setLanguage("chi_sim"); 
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e1) {
            e1.printStackTrace();
        }
        if (result != null) {
            result = result.replaceAll("[^a-z^A-Z^0-9]", "");//替换大小写及数字
        }
        return result;
    }
    /**
     * 返回Cookie
     * @return Cookie
     */
    public Set<Cookie> getCookie(){
        return browser.manage().getCookies();
    }
}
