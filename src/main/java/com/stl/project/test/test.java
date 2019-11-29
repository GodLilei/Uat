package com.stl.project.test;

import com.alibaba.fastjson.JSON;
import com.stl.project.basecase.BaseCase;
import com.stl.project.basecase.BaseStep;
import com.stl.project.businesslogic.TestLogic;
import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.httputil.HttpRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Epic("类似一级目录")
@Feature("类似二级目录")
public class test extends BaseCase {


//    @BeforeClass(description = "创建任务和全局配置")
//    public void beforclass(){
//        System.out.println("beforclass");
//        step("第1步",1);
//    }

    /**
     * 删除创建的任务
     */
//    @AfterClass(description = "删除创建的任务")
//    public void afterclass(){
//        System.out.println("afterclass");
//        step("第1步",4);
//
//    }
    private BaseStep baseStep = new BaseStep();

    @Story("Story类似三级目录")
    @Test(description = "测试用例2")
    public void awtApi001_001(){
        System.out.println("测试用例2");
        baseStep.first("2-1");
        baseStep.second("2-2");
        baseStep.third("2-3");
    }
    @Story("Story类似三级目录")
    @Test(description = "测试用例1")
    public void awtApi001_002(){
        HttpRequest httpRequest = new HttpRequest();
        JSONChange jsonChange = new JSONChange();
        Date d = new Date();
        String c = String.valueOf(d.getTime());
        Map<String,Object> map = jsonChange.JsonStringToMap("{\"type\":\"1\",\n" +
                "\"orgCode\":\"" + "210077" + "\",\n" +
                "\"waybillNo\":\"80" + c.substring(c.length()-10) + "\"}");
        String mm = httpRequest.requestPost("http://jingang.yto56.com.cn/matcn/waybillNoEncodeServlet",map,
                "application/x-www-form-urlencoded",null,"");
        System.out.println(mm);
    }

    @Test(dataProvider = "dts")
    public void myTest(Map<String,String> map) {
        TestLogic testLogic = new TestLogic(map,cookies);

    }
}
