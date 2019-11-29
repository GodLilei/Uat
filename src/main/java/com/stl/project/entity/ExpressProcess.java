package com.stl.project.entity;

import lombok.Data;

@Data
public class ExpressProcess {

    private String flag;
    private String waybill_no;
    private String user;
    private String create_time;
    private String customer;
    private String org_code;
    private String operate = "";
    private String start_time;
    private String end_time;
    private String other = "";
}
