package com.myproject.demo.entity;

import lombok.Data;

@Data
public class ActiveWayBillNo {

    private String tableName;
    private String waybill_no;
    private String mat_create_time;
    private String mat_org_code;
    private String mat_expire_time;
    private String pid;
    private String taking_time;
    private String delivery_time;
    private String delivery_count;
    private String delivery_signoff_flag;
    private String signoff_time;
    private String signoff_upload_time;
    private String create_time;
    private String modify_time;
    private String rowid_exp_digest;
    private String order_create_time;
}
