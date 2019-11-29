package com.stl.project.basecase;

import io.qameta.allure.Step;

import org.apache.log4j.Logger;

public class BaseStep {
    private static Logger logger = Logger.getLogger(BaseStep.class);

    @Step("第一步：")
    public String first(String f){
        System.out.println("1");
        return f;
    }
    @Step("第二步：{0}")
    public String second(String s){
        System.out.println("2");
        return s;
    }
    @Step("第三步：{0}")
    public String third(String t){
        System.out.println("3");
        return t;
    }
}
