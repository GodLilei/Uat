package com.myproject.demo.TestPage;

import java.util.List;

public interface IOrderChannelSoa {
	
	public String selectChannelByWaybill(List<String> waybillNos);

	public String getOrderBywaybillNo(String no);
	
	public String getDiscountInfo(String jsonString);

}
