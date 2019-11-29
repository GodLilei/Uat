package com.stl.project.entity.businessentity;

import lombok.Data;

@Data
public class DirectMarket {
    private String id;
    private String customer_code;//客户
    private String market_code;//市场部编码
    private String settle_target;//结算对象（0:与分公司结算，1：与客户结算）
    private String settle_fee_flag;//结算费用（0：快递费，1：快递费和操作费）
    private String oper_code;//代操作网点
    private String trans_fee_node;//计费节点标识，（0：揽收，1：下车，2：签收）
    private String settle_cycle;//结算周期（C01：每天 C02: 周结 C03:半月结 C04 月结 ）
    private String return_charge_flag;//退回计费标识，0计费,1计费
    private String weight_model;//重量取值模式（0:揽收重量 1:中转费计费重量）

    public String changeNode(String node){
        if ("0".equals(node)) return "揽收节点";
        if ("1".equals(node)) return "中心下车节点";
        if ("2".equals(node)) return "签收节点";
        return "";
    }
    public String changeCycle(String node){
        if ("C01".equals(node)) return "日结";
        if ("C02".equals(node)) return "周结";
        if ("C03".equals(node)) return "半月";
        if ("C04".equals(node)) return "月结";
        return "";
    }
}
