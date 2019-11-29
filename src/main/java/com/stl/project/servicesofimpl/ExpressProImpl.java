package com.stl.project.servicesofimpl;

import com.alibaba.fastjson.JSONArray;
import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.ExpressProcess;
import com.stl.project.servicesofdatasource.MysqlDB;
import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExpressProImpl {
    @Resource
    private MysqlDB mysqlDB;
    @Resource
    private DateUtil dateUtil;
    @Resource
    private BaseResponse baseResponse;
    @Resource
    private JSONChange jsonChange;

    private void insertExpPro(ExpressProcess expressProcess){
        mysqlDB.insertExpPro(expressProcess);
    }
    private void updateExpPro(ExpressProcess expressProcess){
        mysqlDB.updateExpPro(expressProcess);
    }
    public List<ExpressProcess> expressDetail(ExpressProcess expressProcess){
        String[] str = expressProcess.getFlag().split(" - ");
        if (str.length == 2){
            expressProcess.setStart_time(str[0].concat(" 00:00:00"));
            expressProcess.setEnd_time(str[1].concat(" 23:59:59"));
        }else{
            try {
                expressProcess.setStart_time(dateUtil.deleteHMS(dateUtil.dateOfAfter("day",-10)).concat(" 00:00:00"));
                expressProcess.setEnd_time(dateUtil.deleteHMS(dateUtil.nowDate()).concat(" 23:59:59"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mysqlDB.expressDetail(expressProcess);
    }
    public void queryByWaybillNo(ExpressProcess expressProcess){
        String waybillNo = expressProcess.getWaybill_no();
        List<ExpressProcess> list = mysqlDB.queryByWaybillNo(waybillNo);
        if (list.size() == 0){
            log.info("----->未检测到单号存在，执行insert操作" + expressProcess.getWaybill_no());
            expressProcess.setCreate_time(dateUtil.nowDate());
            expressProcess.setUser(expressProcess.getUser());
            insertExpPro(expressProcess);
        }else {
            log.info("----->更新操作：" + expressProcess.getOperate());
            String operate = list.get(0).getOperate();
            expressProcess.setOperate(operate.concat(expressProcess.getOperate()));
            updateExpPro(expressProcess);
        }
    }
    public BaseResponse delExpPro(String[] array){
        try{
            mysqlDB.delExpPro(array);
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(null);
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage("failure");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + e.getMessage() + "\"}"));
        }
        return baseResponse;
    }
}
