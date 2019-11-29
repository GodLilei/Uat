package com.myproject.demo.TestPage;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;


import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
//import com.ibm.gbs.ai.portal.framework.util.StringUtils;

public class SoaTest {
	public static void main(String[] args) {
		HessianProxyFactory proxy = new HessianProxyFactory();
		
		try { 
			//订单渠道  http://10.1.5.14:9086/ordsoa/remote/ordsoas
			//订单渠道  http://10.129.220.142:9081/ordsoa/remote/ordsoas
			long s=System.currentTimeMillis();
			IOrderChannelSoa channelSoa=(IOrderChannelSoa) proxy.create(IOrderChannelSoa.class, "http://10.129.220.142:9081/ordsoa/remote/ordsoas");//测试
			//IOrderChannelSoa channelSoa=(IOrderChannelSoa) proxy.create(IOrderChannelSoa.class, "http://10.1.5.14:9086/ordsoa/remote/ordsoas");
			//String result = channelSoa.getOrderBywaybillNo("B90003517054");
			
			String[] waybillNo={"804137636435550715"};
			String result = channelSoa.selectChannelByWaybill(Arrays.asList(waybillNo));
			long se=System.currentTimeMillis();
			System.out.println("耗时:"+(se-s));
			System.out.println(result);
			System.out.println("------------");
			
			//优惠券  http://10.1.5.14:9086/ordsoa/remote/ordsoas
			//优惠券 http://10.129.220.142:9081/ordsoa/remote/ordsoas
			/*IOrderChannelSoa shannelSoa=(IOrderChannelSoa) proxy.create(IOrderChannelSoa.class, "http://10.129.220.142:9081/ordsoa/remote/ordsoas");//测试
			//IOrderChannelSoa shannelSoa=(IOrderChannelSoa) proxy.create(IOrderChannelSoa.class, "http://10.1.5.14:9086/ordsoa/remote/ordsoas");
			SortedMap datas = new TreeMap();
			datas.put("billno", "B90000502707");
			datas.put("type", "1");
			String value=JSON.toJSONString(datas);
			Map requstMap = null;
			String result1 = shannelSoa.getDiscountInfo(value);
			System.out.println(value);
			System.out.println(result1);
			if(result1!=null){
				requstMap=JSON.parseObject(result1, Map.class);
				JSON.toJSONString(requstMap.get("dates"));
				if(requstMap!=null){
					if(requstMap.get("result_flag")!=null){
						String resultFlag = (String) requstMap.get("result_flag");
						if("true".equals(  )){
							Map<String,Object> dateMap=null;
							if(requstMap.get("dates")!=null){
								dateMap = (Map<String, Object>) requstMap.get("dates");
								if(dateMap.get("discountmoney")!=null){
									Double amount=((Integer)dateMap.get("discountmoney")).doubleValue();
									System.out.println(amount);
								}
							}
						}
					}
				}
			}
			System.out.println("------------");*/
			
			/*Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("waybillNo", "DB0000610770");
			//物料订单信息  http://10.129.220.245:9081/matsoa/remoting/matSoaAppNotifyService 
			//物料订单信息  http://10.1.5.14:9081/matsoa/remoting/matSoaAppNotifyService
			//IStlBnetSoaBiz matClient = (IStlBnetSoaBiz) proxy.create(IStlBnetSoaBiz.class, "http://10.129.220.245:9081/matsoa/remoting/matSoaAppNotifyService");//测试
			long s=System.currentTimeMillis();
			IStlBnetSoaBiz matClient = (IStlBnetSoaBiz) proxy.create(IStlBnetSoaBiz.class, "http://10.1.5.14:9081/matsoa/remoting/matSoaAppNotifyService");
			System.out.println(JSON.toJSONString(inputMap));
			String resValue = matClient.getBournOrg(JSON.toJSONString(inputMap));
			long se=System.currentTimeMillis();
			System.out.println(se-s);
			System.out.println(resValue);*/
			
			
			//物料客户信息  http://10.129.220.245:9081/matsoa/remoting/matSoaAppNotifyService 
			//物料客户信息  http://10.1.5.14:9081/matsoa/remoting/matSoaAppNotifyService
			
			/*Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("waybillNo", "802655747699035538");
			long s2=System.currentTimeMillis();
			IStlBnetSoaBiz matClient = (IStlBnetSoaBiz) proxy.create(IStlBnetSoaBiz.class, "http://10.129.220.245:9081/matsoa/remoting/matSoaAppNotifyService");//测试
			//IStlBnetSoaBiz matClient = (IStlBnetSoaBiz) proxy.create(IStlBnetSoaBiz.class, "http://10.1.5.14:9081/matsoa/remoting/matSoaAppNotifyService");
			System.out.println(JSON.toJSONString(inputMap));
			String resValue = matClient.getActivedCusByNo(JSON.toJSONString(inputMap));
			
			String customer = null;
			if(StringUtils.isNotEmpty(resValue)){
				System.out.println(resValue);
				Map<String, String> resultMap = JSON.parseObject(resValue, Map.class);
				if(MapUtils.isNotEmpty(resultMap)){
					if("true".equals(resultMap.get("success"))){
						customer=resultMap.get("customerCode");
					}
				}
			}
			
			long s2e=System.currentTimeMillis();
			System.out.println(s2e-s2);
			System.out.println(customer);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
