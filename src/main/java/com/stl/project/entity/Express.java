package com.stl.project.entity;

import com.alibaba.druid.sql.visitor.functions.Char;
import lombok.Data;

@Data
public class Express {

    private long orderid;
    private String error_message = "0";
    private String id;

    private String exp_type = "10";//快件类型，10，单号。20，包签号码
    private Integer pkg_qty = 1;
    private String fee_flag = null;
    private Double fee_amt = 0.00;
    private String frequency_no = null;//频次
    private String delegate_org_code = null;
    private String order_no = null;
    private String aux_op_code = "NEW";//操作：NEW,UPDATE,DELETE
    private Integer route_code = 0;
    private String aux_route_code = null;
    private String status = "1";
    private String transfer_status = "1";
    private String error_times = "0";
    private String sn_no = "696";
    private String occ_status = "140";
    private String create_terminal = "50-7B-9D-69-56-61/172.16.52.24:ytodb:.4";
    private String device_type = "PC";
    private String remark = "CN100301";
    private String ref_id = null;
    private String trace_status = "1";
    private Integer query_flag = 0;
    private Integer data_id = 1;
    private String express_content_code;
    private String business_type_code = "EXP";
    private String effective_type_code;//汽运
    private String auto_taking = "off";
    private String expire_date = "0";

    private String emp_code;//客户名称 customer
    private String emp_name = "K";//客户名字

    private String waybill_no;//运单号码

    private String car_no;//车签号码
    private String pkg_no;//包签号码
    /**
     * 操作网点
     */
    private String source_org_code;//始发网点
    private String des_org_code;//目的网点
    private String build_org_code;//建包组织机构
    private String handon_org_code;//派件组织机构
    private String signature_org_code;//签收组织机构
    private String out_org_code;//下车组织机构
    private String taking_org_code;//揽收组织机构
    private String return_org_code;//退回操作网点
    private String jipao_org_code;//计泡操作网点
    private String previous_org_code;//上一个操作网点
    private String next_org_code;//下一个操作网点

    /***/
    private String seller = "2575775285";//商家
    /**
     * 操作人信息
     */
    private String op_user_code;//创建人编号
    private String op_user_name;//创建人姓名
    private String signature_name;//签收人
    private String signature_tel;//派件人电话
    private String delivery_id;

    private String return_waybill_no;//返回单号

    /**
     * 时间
     */
    private String upload_time;//上传时间
    private String create_time;//创建时间
    private String modify_time;//修改时间
    private String operate_time;//处理时间
    private String delivery_time;//签收里面的标识时间

    private Integer op_code;//操作码
    private String io_type = "01";

    /**
     * 重量
     */
    private Double weigh_weight = 0.00;
    private Double input_weight;//重量
    private Double out_weight;//下车重量
    private Double volume_weight = 0.00;//体积重
    private Double pkg_length = 0.00;//长
    private Double pkg_width = 0.00;//宽
    private Double pkg_height = 0.00;//高
    private Double fee_weight = 0.00;

    private String modify_terminal = "74-27-EA-15-DE-CA";//JOB-AUTO

    private String build_org_type;//建包组织类型：BRANCH,TRANSFER_CENTER
    private String out_org_type;//下车组织类型：BRANCH,TRANSFER_CENTER
    private String jipao_org_type;//计泡下车类型

    private Char data_status;

}

