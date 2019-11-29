package com.myproject.demo.impl;

import com.myproject.demo.entity.ActiveWayBillNo;
import com.myproject.demo.entity.CheckMenu;
import com.myproject.demo.services.OldUatServices;
import com.myproject.demo.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ActiveWaybillImpl {
    @Resource
    private OldUatServices oldUatServices;

    private static final String TABLE_NAME = "YTEXP.T_EXP_WAYBILL_";

    public String addActive(ActiveWayBillNo activeWayBillNo){

        activeWayBillNo.setTableName(TABLE_NAME.concat(activeWayBillNo.getWaybill_no()
                .substring(activeWayBillNo.getWaybill_no().length()-3)));
        String flag = "";
        Tool tools = new Tool();
        activeWayBillNo.setMat_create_time(tools.dateOfAfter("day",-1));
        activeWayBillNo.setMat_expire_time(tools.dateOfAfter("year",2));
        activeWayBillNo.setCreate_time(tools.nowDate());
        activeWayBillNo.setModify_time(tools.nowDate());
        try{
            List<ActiveWayBillNo> list = checkActive(activeWayBillNo);
            if (!list.isEmpty()){
                log.info("----->检测该单号："+activeWayBillNo.getWaybill_no()+"已经激活，返回");
                flag = "该单号：" + activeWayBillNo.getWaybill_no() + "已经激活，无需再次激活";
            }else {
                log.info("----->单号："+activeWayBillNo.getWaybill_no()+"开始激活,请稍后");
                oldUatServices.addActive(activeWayBillNo);
                flag = "单号：" + activeWayBillNo.getWaybill_no() + "激活成功！";
                log.info("----->激活成功。");
            }
        }catch (Exception e){
            flag = e.getMessage();
        }
        return flag;
    }

    public String pwReset(String code){
        log.info("----->密码重置：code:"+ code);
        String msg = "密码重置成功：Aa123456";
        try{
            oldUatServices.pwReset(code);
        }catch (Exception e){
            msg = e.getMessage();
        }
        return msg;
    }

    public List<String> checkMenu(String menu){
        List<String> resultList = new ArrayList<>();
        List<CheckMenu> menuList = oldUatServices.checkMenu(menu);
        for (CheckMenu iList:menuList){
            if (!"empty".equals(iList.getLocation())){
                List<CheckMenu> appNameList = oldUatServices.checkAppName(iList.getApp_id());
                resultList.add(iList.getMenu_name());
                resultList.add("http://jingangtest.yto56.com.cn/".concat(appNameList.get(0).getApp_name().concat(iList.getLocation())));
            }
        }
        return resultList;
    }

    private List<ActiveWayBillNo> checkActive(ActiveWayBillNo activeWayBillNo){
        log.info("----->查询单号：" + activeWayBillNo.getWaybill_no() + "是否激活");
        return oldUatServices.checkActive(activeWayBillNo);
    }

}
