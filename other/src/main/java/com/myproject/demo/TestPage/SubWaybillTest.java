package com.myproject.demo.TestPage;

import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSONObject;

public class SubWaybillTest {

    public static void main(String[] args) {
        SubWaybillTest subWaybillTest = new SubWaybillTest();
        //拼多多新的订阅接口 未上线
        subWaybillTest.sendPDDSubWay("");
        //订阅接口
        subWaybillTest.sendSubWay("");
    }
    public void sendPDDSubWay(String name){
        try {
            HttpClient client=new DefaultHttpClient();

            String data ="{\"trackingOrderNo\":\"order123456\",\"trackingNumber\":\"1234567890\"}";	//生成
//    		String url="http://localhost:8080/ordws/PDDSubWaybillServlet";
            String url="http://jingangtest.yto56.com.cn/ordws/PDDSubWaybillServlet";
            ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();
            NameValuePair nv0=new BasicNameValuePair("partnerId","85");
            al.add(nv0);
            NameValuePair nv1=new BasicNameValuePair("data",data);
            al.add(nv1);
            NameValuePair nv2=new BasicNameValuePair("dataDigest",DigestUtils.md5Hex(data+"TEST_SECRET_KEY"));//kKj1pAWu pAIytopai
            al.add(nv2);
            HttpPost method=new HttpPost(url);
            UrlEncodedFormEntity uefe=new UrlEncodedFormEntity(al,HTTP.UTF_8);
            method.setEntity(uefe);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            System.out.println(JSONObject.toJSONString(al));
            String result=client.execute(method,responseHandler);
            System.err.println("SubWaybillServlet返回信息："+result);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void sendSubWay(String name){
        try {
            HttpClient client=new DefaultHttpClient();

            String logistics_interface =getCreateSubWayXml(name);	//生成
//    		String url="http://localhost:8080/ordws/SubWaybillServlet";
            String url="http://jingangtest.yto56.com.cn/ordws/SubWaybillServlet";
            ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();
            NameValuePair nv0=new BasicNameValuePair("client_id","PDDORDER");
            al.add(nv0);
            NameValuePair nv1=new BasicNameValuePair("logistics_interface",logistics_interface);
            al.add(nv1);
            NameValuePair nv2=new BasicNameValuePair("data_digest",EncryptMD5(logistics_interface,"123456"));//kKj1pAWu pAIytopai
//    		NameValuePair nv2=new BasicNameValuePair("data_digest","YTJmNmZlNTEwYjY4OTMwMjY2NDYwOTYwNzAwYjU3Njc=");
            al.add(nv2);
            NameValuePair nv3=new BasicNameValuePair("msg_type","online");//kKj1pAWu
            al.add(nv3);
            HttpPost method=new HttpPost(url);
            UrlEncodedFormEntity uefe=new UrlEncodedFormEntity(al,HTTP.UTF_8);
            method.setEntity(uefe);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            System.out.println(JSONObject.toJSONString(al));
            String result=client.execute(method,responseHandler);
            System.err.println("SubWaybillServlet返回信息："+result);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String EncryptMD5(String logistics_interface,String parterid) throws Exception{
        System.out.println(new String(Base64.encodeBase64(DigestUtils.md5((logistics_interface+parterid).getBytes("UTF-8")))));
        return new String(Base64.encodeBase64(DigestUtils.md5((logistics_interface+parterid).getBytes("UTF-8"))));
    }

    public static String getCreateSubWayXml(String name){
        String xml="<RequestOrder><clientId>PDDORDER</clientId><waybillNo>804137636435550715</waybillNo></RequestOrder>";
        return xml;
    }
}
