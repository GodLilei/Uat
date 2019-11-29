package com.myproject.demo.utils;

//import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.openqa.selenium.Cookie;

import java.util.*;

@Slf4j
public class HttpClientResponse {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String entityStr = null;
    private String token = "TOKEN=";
    private CloseableHttpResponse response = null;

    /**
     *
     * @param url 请求路径
     * @param map 请求map
     * @return 请求结果
     */
    public String requestGet(String url, Map<String,Object> map){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        String con = null;
        try{
            URIBuilder uriBuilder = new URIBuilder(url);
            List<NameValuePair> params = new LinkedList<>();
            if (map != null && map.size() != 0){
                for (Object o : map.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    params.add(new BasicNameValuePair((String) entry.getKey(),(String) entry.getValue()));
                }
            }else {
                log.info("无参数");
            }

            uriBuilder.addParameters(params);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

//            for (Cookie c:cookies) {
//                if (c.getName().equals("TOKEN")){
//                    con = token.concat(c.getValue());
//                }
//                httpGet.setHeader(c.getName(),c.getValue());
//            }
            //log.info("----->token:" + token);
            httpGet.setConfig(requestConfig);//设置配置信息
            httpGet.setHeader("Cookie","screenResolution=1680x1050; JSESSIONID=hpsefnap9j3p1hiisev4sw1md");//设置Cookie
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
            log.info("----->Get请求成功，返回：" + entityStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityStr;
    }

    /**
     *
     * @param url 路径
     * @param jsonParams json字符串
     * @return 请求结果
     */
    public String requestPost(String url,String jsonParams){
        //post请求
        log.info("----->POST请求开始：参数类型-jsonParams");
        HttpPost httpPost = new HttpPost(url);
        String con = null;
        httpPost.setHeader("Content-Type","application/json");
        try{
            httpPost.setEntity(new StringEntity(jsonParams,ContentType.create("application/json", "utf-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
            log.info("----->POST请求成功，返回：" + entityStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityStr;
    }

    /**
     *
     * @param url 请求路径
     * @param map 参数map
     * @param cookies cookie
     * @return 请求结果
     */
//    public String requestPost(String url,Map<String,Object> map,Set<Cookie> cookies){
//        log.info("----->POST请求开始：参数类型-map");
//        HttpPost httpPost = new HttpPost(url);
//        String con = null;
//        for (Cookie c:cookies) {
//            if (c.getName().equals("TOKEN")){
//                con = token.concat(c.getValue());
//            }
//        }
//        httpPost.setHeader("Cookie",cookies.toString().replace("[","").replace("]",""));
//        List<NameValuePair> params = new ArrayList<>();
//        if (map.size() != 0){
//            for (Object o : map.entrySet()) {
//                Map.Entry entry = (Map.Entry) o;
//                params.add(new BasicNameValuePair((String) entry.getKey(),(String) entry.getValue()));
//            }
//        }
//        try{
//            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//            response = httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            entityStr = EntityUtils.toString(entity, "UTF-8");
//            log.info("----->POST请求成功，返回：" + entityStr);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return entityStr;
//    }

    /**
     * HTTPPOST请求
     * @param url 请求url
     * @param map 请求map
     * @return 返回参数
     */
    public String requestPost(String url,Map<String,Object> map){
        log.info("----->POST请求开始：参数类型-map,绑定网点：" + map.get("orgCode"));
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<>();
        if (map.size() != 0){
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                params.add(new BasicNameValuePair((String) entry.getKey(),(String) entry.getValue()));
            }
        }
        try{
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, "UTF-8");
            log.info("----->POST请求成功，返回：" + entityStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityStr;
    }
}
