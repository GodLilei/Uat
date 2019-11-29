package com.stl.project.tools.httputil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
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
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Component;

@Component
public class HttpRequest {
    private static Logger logger = Logger.getLogger(HttpRequest.class);

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String entityStr = null;
    private CloseableHttpResponse response = null;

    /**
     * Post请求
     * @param url 接口地址;
     * @param param 请求参数
     * @param contentType 参数类型 json/application,application/x-www-form-urlencoded
     * @param cookies cookie
     * @param cookiesStr cookie字符串
     */
     public String requestPost(String url, Map<String,Object> param, String contentType, Set<Cookie> cookies,String cookiesStr){
         HttpPost httpPost = new HttpPost(url);//请求地址
         //设置参数
         setEntityStr(httpPost,param,contentType);
         //设置Cookie
         setCookieStrPost(httpPost,cookies,cookiesStr);
         try{
             response = httpClient.execute(httpPost);
             HttpEntity entity = response.getEntity();
             entityStr = EntityUtils.toString(entity, "UTF-8");
         }catch (Exception e){
             e.printStackTrace();
         }
         return entityStr;
     }

    /**
     * Post请求
     * @param url 接口地址;
     * @param param 请求参数
     * @param cookies cookie
     * @param cookiesStr cookie字符串
     */
     public String requestGet(String url, Map<String,Object> param, Set<Cookie> cookies, String cookiesStr){
         HttpGet httpGet = null;
         try {
             URIBuilder urlBuilder = new URIBuilder(url);
             List<NameValuePair> pairs = new ArrayList<>();
             if (param != null && param.size() > 0){
                 for (Object o:param.entrySet()){
                     Map.Entry entry = (Map.Entry) o;
                     pairs.add(new BasicNameValuePair((String)entry.getKey(),(String)entry.getValue()));
                 }
             }
             urlBuilder.addParameters(pairs);
             httpGet = new HttpGet(urlBuilder.build());
         } catch (URISyntaxException e) {
             e.printStackTrace();
         }
         setCookieStrGet(httpGet,cookies,cookiesStr);
         try {
             response = httpClient.execute(httpGet);
             HttpEntity entity = response.getEntity();
             entityStr = EntityUtils.toString(entity, "UTF-8");
         } catch (IOException e) {
             e.printStackTrace();
         }
         return entityStr;
     }

     //设置参数
     private void setEntityStr(HttpPost httpPost, Map<String,Object> param, String contentType){
         if ("application/x-www-form-urlencoded".equals(contentType)){//此参数适用于{参数名："参数JSON串"}
             List<NameValuePair> params = new ArrayList<>();
             if (param.size() != 0){
                 for (Object o : param.entrySet()) {
                     Map.Entry entry = (Map.Entry) o;
                     params.add(new BasicNameValuePair((String) entry.getKey(),(String) entry.getValue()));
                 }
             }
             try {
                 httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
         }else if ("application/json".equals(contentType)){//此参数适用于{JSON串}
             httpPost.setEntity(new StringEntity(JSON.toJSONString(param,SerializerFeature.WriteMapNullValue),
                     ContentType.APPLICATION_JSON));
         }else if ("application/xml".equals(contentType)){//此参数适用于{JSON串}
             httpPost.setEntity(new StringEntity(JSON.toJSONString(param,SerializerFeature.WriteMapNullValue),
                     ContentType.APPLICATION_XML));
         }
         httpPost.setHeader("Content-Type",contentType);
     }

     //设置cookie
     private void setCookieStrPost(HttpPost httpPost,Set<Cookie> cookies, String cookiesStr){
         if (cookies != null){
             httpPost.setHeader("Cookie",cookies.toString().replace("[", "").replace("]", ""));
         }else if (!"".equals(cookiesStr)){
             httpPost.setHeader("Cookie",cookiesStr);
         }
     }
    private void setCookieStrGet(HttpGet httpGet,Set<Cookie> cookies, String cookiesStr){
         if (cookies != null){
             httpGet.setHeader("Cookie",cookies.toString().replace("[", "").replace("]", ""));
         }else if (!"".equals(cookiesStr)){
             httpGet.setHeader("Cookie",cookiesStr);
         }
    }
}
