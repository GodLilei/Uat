package com.stl.project.businesslogic;

import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.httputil.HttpRequest;
import com.stl.project.tools.seleniumutil.SeleniumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

@Slf4j
public class TestLogic extends BaseLogic{

    public TestLogic(Map<String,String> map, Set<Cookie> cookies) {
        HttpRequest httpRequest = new HttpRequest();
        JSONChange jsonChange = new JSONChange();
        String result = httpRequest.requestPost(map.get("interface_url"),jsonChange.JsonStringToMap(map.get("param"))
                ,map.get("param_type"),cookies,
                "");
        log.info(result);
        Assert.fail("看看会不会输出");

    }

}
