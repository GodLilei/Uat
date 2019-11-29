package com.myproject.demo.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BrowserConfig {
    private ChromeDriver browser;
//    private String baseUrl;
//    private boolean acceptNextAlert = true;
//    private StringBuffer verificationErrors = new StringBuffer();

    //构造方法
//    public BrowserConfig(String url,String imageXPath){
//        openBrowser();
//        openWeb(url);
//        try {
//            getVildateImage(imageXPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    BrowserConfig(){}

    /**
     * 打开浏览器
     */
    public void openBrowser() {
        log.info("----->打开浏览器");
        System.setProperty("webdriver.chrome.driver","F:/myproject/testBranch/chromedriver.exe");
        browser = new ChromeDriver();
        maxBrowser();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * 打开网页
     * @param url 路径
     */
    public void openWeb(String url){
        browser.get(url);
        log.info("----->打开网页" + url);
    }

    /**
     * 浏览器最大化
     */
    private void maxBrowser() {
        this.browser.manage().window().maximize();
        log.info("----->浏览器最大化");
    }

    /**
     * 关闭当前窗口
     */
    public void closeBrowser() {
        this.browser.close();
        log.info("关闭当前窗口");
    }

    /**
     * 关闭浏览器
     */
    public void quitBrowser() {
        this.browser.quit();
        log.info("关闭浏览器");
    }

    /**
     * 将图片保存到本地
     * @param xPath 图片的xPath路径
     * @throws IOException 异常
     */
    public void getVildateImage(String xPath)throws IOException {
        log.info("----->保存验证码图片到本地......");
        WebElement ele = browser.findElement(By.xpath(xPath));//得到验证码元素
        File screenshot = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
        // Get entire page screenshot
        BufferedImage fullImg=ImageIO.read(screenshot);
        // Get the location of element on the page
        org.openqa.selenium.Point point= ele.getLocation();
        // Get width and height of the element
        int eleWidth= ele.getSize().getWidth();
        int eleHeight= ele.getSize().getHeight();
        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        // Copy the element screenshot to disk
        File screenshotLocation= new File("F:/myproject/testBranch/test.png");
        FileUtils.copyFile(screenshot, screenshotLocation);
    }

    /**
     * 输入登录元素
     * @param userName 用户名
     * @param password 密码
     * @param code 验证码
     */
    public void writeValue(String userName,String password,String code){
        browser.findElement(By.name("userName")).clear();
        browser.findElement(By.name("password")).clear();
        browser.findElement(By.id("varification")).clear();
        browser.findElement(By.name("userName")).sendKeys(userName);//输入用户名
        browser.findElement(By.name("password")).sendKeys(password);//密码
        browser.findElement(By.id("varification")).sendKeys(code);//验证码
        log.info("----->输入登录元素，userName:" + userName + ",password:" + password + ",code:" + code);
//        browser.findElement(By.id("form")).submit();
    }
    public void writeValue(Map<String,Map<String,Object>> param){
        if (param != null && param.size()>0){
            for(Map.Entry<String, Map<String, Object>> map:param.entrySet()){
                if ("xpath".equals(map.getKey())){
                    for (Object o:map.getValue().entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.xpath((String)entry.getKey())).clear();
                        browser.findElement(By.xpath((String)entry.getKey())).sendKeys((String)entry.getValue());
                    }
                }else if ("name".equals(map.getKey())){
                    for (Object o:map.getValue().entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.name((String)entry.getKey())).clear();
                        browser.findElement(By.name((String)entry.getKey())).sendKeys((String)entry.getValue());
                    }
                }else if ("id".equals(map.getKey())){
                    for (Object o:map.getValue().entrySet()){
                        Map.Entry entry = (Map.Entry) o;
                        browser.findElement(By.id((String)entry.getKey())).clear();
                        browser.findElement(By.id((String)entry.getKey())).sendKeys((String)entry.getValue());
                    }
                }
            }
        }
//        browser.findElement(By.id("form")).submit();
    }

    public void writeValue(String xpath,String value){
        boolean a = browser.findElement(By.xpath(xpath)).isDisplayed();
        log.info("" + a);
    }

    /**
     * 点击事件
     * @param id 点击id
     */
    public void submit(String id){
        browser.findElement(By.id(id)).click();
    }

    public void submitByXPath(String id){
        browser.findElement(By.xpath(id)).click();
    }

    /**
     * 将图片转换为验证码
     * @param path 本地图片路径
     * @return 返回验证码
     */
    public String getVerificationCode(String path) {
        log.info("----->图片转验证码......");
        if (path.equals("")){
            path = "F:/myproject/testBranch/test.png";
        }
        File imageFile = new File(path);
        ITesseract instance = new Tesseract();//调用Tesseract
        URL url = ClassLoader.getSystemResource("tessdata");//获得Tesseract的文字库
        String tesspath = url.getPath().substring(1);
        instance.setDatapath(tesspath);//进行读取，默认是英文，如果要使用中文包，加上instance.setLanguage("chi_sim"); 
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

    /**
     * 获取文本框里面的内容
     * @param XPath input元素的XPath
     * @return 文本值
     */
    public String getInputValue(String XPath){
        return browser.findElement(By.xpath(XPath)).getAttribute("value");
    }

    /**
     * 获取元素中的文本内容
     * @param XPath 元素XPath路径
     * @return 返回内容
     */
    public String getTagContent(String XPath){
//        return browser.findElement(By.xpath(XPath)).getAttribute("textContent");
        return browser.findElement(By.xpath(XPath)).getText();
    }
}
