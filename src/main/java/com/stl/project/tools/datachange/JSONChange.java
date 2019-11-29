package com.stl.project.tools.datachange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JSONChange {
    /**
     * JSON字符串转JSONObject
     * @param jsonString json字符串
     * @return JSON类
     */
    private JSONObject JsonStringToJson(String jsonString){
        return JSONObject.parseObject(jsonString);
    }

    /**
     * json字符串转MAP
     * @param jsonString json字符串
     * @return MAP
     */
    public Map<String,Object> JsonStringToMap(String jsonString){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        JSONObject obj = JsonStringToJson(jsonString);//传过来的值的size为1，去掉外层循环
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            resultMap.put(entry.getKey(),entry.getValue());
        }
        return resultMap;
    }
    /**
     * jsonString转JSONArray
     * @param obj 参数
     * */
    public JSONArray jsonStringToJSONArray(String obj){
        return JSONArray.parseArray("[" + obj + "]");
    }
    /**
     * pojoList转JSONArray
     * @param obj 参数
     * */
    public JSONArray pojoListToJSONArray(List obj){
        return JSONArray.parseArray(JSON.toJSONString(obj,SerializerFeature.WriteMapNullValue));
    }
}
