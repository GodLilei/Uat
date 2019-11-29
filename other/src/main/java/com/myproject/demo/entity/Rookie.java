package com.myproject.demo.entity;

import lombok.Data;

@Data
public class Rookie {
    private String WAYBILL_NO;
    private String BIZ_ID;
    private String COMPANY_CODE = "YTO";
    private String BRANCH_CODE;
    private String BRANCH_NAME;
    private String SELLER_ID;
    private String CONSIGNEE_NAME = "李四";
    private String CONSIGNEE_PHONE = "13116995869";
    private String SEND_PROVINCE_NAME = "上海";
    private String SEND_PROVINCE_CODE = "021";
    private String SEND_CITY_NAME = "上海市";
    private String SEND_CITY_CODE = "310000";
    private String SEND_AREA_NAME = "青浦区";
    private String SEND_AREA_CODE = "310118";
    private String SEND_TOWN_NAME;
    private String SEND_TOWN_CODE;
    private String SEND_DETAIL_ADDRESS = "富强大街1344号";
    private String CONSIGNEE_PROVINCE_NAME = "上海";
    private String CONSIGNEE_PROVINCE_CODE = "021";
    private String CONSIGNEE_CITY_NAME = "上海市";
    private String CONSIGNEE_CITY_CODE = "310000";
    private String CONSIGNEE_AREA_NAME = "闵行区";
    private String CONSIGNEE_AREA_CODE = "310112";
    private String CONSIGNEE_TOWN_NAME;
    private String CONSIGNEE_TOWN_CODE;
    private String CONSIGNEE_DETAIL_ADDRESS = "七宝镇七宝老街1号";
    private String CURTIME;
    private String STATUS = "2";
    private String SENDER_PHONE;
    private String SENDER_NAME = "张兰";
    private String DA_TOU_BI = "300";
    private String WEIGHT;
    private String VOLUME;
    private String PRODUCT_TYPE = "STANDARD_EXPRESS";
    private String IS_ALIORDER;
    private String MSG_VERSION;
    private String SELLER_NAME;
    private String BIZ_INDEX;
    private String CONSIGNEE_mobile = "13855996655";
    private String sender_mobile = "15988885555";
    private String orderChanel = "TAOBAO";
    private String version = "1540204715129";
    private String customerCode;
    private String empCode;
    private String back_1;
    private String back_2;
    private String materielCode;
}
