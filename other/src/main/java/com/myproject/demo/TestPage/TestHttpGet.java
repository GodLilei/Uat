package com.myproject.demo.TestPage;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;

import javax.xml.ws.spi.http.HttpContext;
import java.util.LinkedList;
import java.util.List;

public class TestHttpGet {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String entityStr = null;
    private CloseableHttpResponse response = null;
    private HttpContext httpContext = null;

    /**
     * get请求
     * @param url 接口接收的参数地址
     */
    public String HttpGetofJSONString(String url) {

        int strStartIndex = 0;
        int strEndIndex = 0;
        String result = "";
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            System.out.println(statusCode+"----");
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");

            strStartIndex = entityStr.indexOf("<div class=\"navigation\">");
            strEndIndex = entityStr.indexOf("</body>") + 7;
            if (strStartIndex == -1 || strEndIndex == -1){
                return "页面：500错误，或者其他,或者Cookie过期";
            }else {
                result = entityStr.substring(strStartIndex, strEndIndex);
                if (result.contains("错误1")){
                    return "error:1";
                }
            }
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return entityStr;
//        return entityStr;
    }
}
