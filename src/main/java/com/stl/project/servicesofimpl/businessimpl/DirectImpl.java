package com.stl.project.servicesofimpl.businessimpl;

import com.stl.project.dto.DirectMarketDto;
import com.stl.project.entity.businessentity.DirectMarket;
import com.stl.project.servicesofdatasource.UATDB;
import com.stl.project.tools.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DirectImpl {

    @Resource
    private UATDB uatdb;
    @Resource
    private DateUtil dateUtil;

    private final Map<String, DeferredResult<DirectMarketDto>> taskMap;

    public DirectImpl(){
        taskMap = new ConcurrentHashMap<>();
    }

    private String randomId(){
        String num = (int)(Math.random()*10000) + "";
        return "402889ab" + num + "ef390162e6f0221c" + num;
    }
    //启动轮询
    public void configStart(String cus,DeferredResult<DirectMarketDto> deferredResult) {
        deferredResult.onTimeout(() -> {
            taskMap.remove(cus);
            DirectMarketDto directMarketDto = new DirectMarketDto();
            directMarketDto.setCus(cus);
            directMarketDto.setMessage("");
            deferredResult.setResult(directMarketDto);
        });
        taskMap.put(cus, deferredResult);
    }

    //停止轮询,删除taskMap数据
    public void endConfig(String cus) {
        if (taskMap.containsKey(cus)) {
            DirectMarketDto directMarketDto = new DirectMarketDto();
            directMarketDto.setCode("1");//停止轮询
            directMarketDto.setMessage("!!!---任务已停止---!!!"+"\n");
            taskMap.get(cus).setResult(directMarketDto);
            taskMap.remove(cus);
        }
    }
    //检查客户配置信息
    public void directCheck(String cus,DirectMarket directMarket){
        if (taskMap.containsKey(cus)){
            DirectMarketDto directMarketDto = new DirectMarketDto();
            try{
                directMarketDto.setCus(cus);
                //等待ajax异步反应，创建新的返回实例对象,上次返回的对象与下次的对象不同，才可以执行
                DeferredResult<DirectMarketDto> defCheck = taskMap.get(cus);
                long start = System.currentTimeMillis();
                List<DirectMarket> list = uatdb.directCheck(cus,dateUtil.deleteHMS(dateUtil.nowDate()));
                long end = System.currentTimeMillis()-start;
                if (!list.isEmpty()){
                    directMarketDto.setMessage("----------->>>>检查客户配置信息：条数[" + list.size() + "],耗时：" + end + "ms"+"\n");
                    //数据ID
                    directMarket.setId(list.get(0).getId());
                    taskMap.get(cus).setResult(directMarketDto);
                    directUpdate(directMarket.getCustomer_code(),directMarket,defCheck);
                }else{
                    directInsert(directMarket.getCustomer_code(),directMarket,defCheck);
                }
            }catch (Exception ignored){
            }
        }
    }
    //新增客户配置信息
    private void directInsert(String cus, DirectMarket directMarket, DeferredResult<DirectMarketDto> deferredResult){
        if (taskMap.containsKey(cus)){
            DirectMarketDto directMarketDto = new DirectMarketDto();
            try{
                DeferredResult<DirectMarketDto> defInsert = taskMap.get(cus);
                while (deferredResult == defInsert){
                    defInsert = taskMap.get(cus);
                    Thread.sleep(500);
                }
                directMarketDto.setCus(cus);
                directMarketDto.setMessage("----------->>>>开始添加客户配置信息---> "+ cus +"\n");
                directMarket.setId(randomId());
                uatdb.insertDirect(directMarket);
                taskMap.get(cus).setResult(directMarketDto);
            }catch (Exception e){
                directMarketDto.setCode("-1");
                directMarketDto.setMessage("新增客户配置信息失败--->："+"\n" + e.getMessage());
                taskMap.get(cus).setResult(directMarketDto);
            }
        }
    }
    //修改用户配置信息
    private void directUpdate(String cus, DirectMarket directMarket, DeferredResult<DirectMarketDto> deferredResult){
        if (taskMap.containsKey(cus)){
            DirectMarketDto directMarketDto = new DirectMarketDto();
            try{
                DeferredResult<DirectMarketDto> defUpdate = taskMap.get(cus);
                while (deferredResult == defUpdate){
                    defUpdate = taskMap.get(cus);
                    Thread.sleep(500);
                }
                directMarketDto.setCus(cus);
                uatdb.updateDirect(directMarket);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("客户 : [ ").append(directMarket.getCustomer_code()).append(" ]"+"\n");
                stringBuilder.append("市场部 : [ ").append(directMarket.getMarket_code()).append(" ]"+"\n");
                stringBuilder.append("代操作分公司 : [ ").append(directMarket.getOper_code()).append(" ]"+"\n");
                stringBuilder.append("结算对象 : [ ").append((directMarket.getSettle_target().equals("0"))?"分公司":"客户")
                        .append(" ]"+"\n");
                stringBuilder.append("结算费用 : [ ").append((directMarket.getSettle_fee_flag().equals("0"))
                        ?"快递费":"快递费和操作费").append(" ]"+"\n");
                stringBuilder.append("计费节点 : [ ").append(directMarket.changeNode(directMarket.getTrans_fee_node()))
                        .append(" ]"+"\n");
                stringBuilder.append("结算周期 : [ ").append(directMarket.changeCycle(directMarket.getSettle_cycle()))
                        .append(" ]"+"\n");
                stringBuilder.append("退回计费 : [ ").append((directMarket.getReturn_charge_flag().equals("0"))?"计费":"不计费")
                        .append(" ]"+"\n");
                stringBuilder.append("重量取值模式 : [ ").append((directMarket.getWeight_model().equals("0"))?"揽收":"中转费")
                        .append(" ]"+"\n");
                directMarketDto.setMessage("----------->>>>更新客户配置信息："+"\n" + stringBuilder);
                taskMap.get(cus).setResult(directMarketDto);
            }catch (Exception e){
                directMarketDto.setCode("-1");
                directMarketDto.setMessage("更新客户配置信息失败--->："+"\n" + e.getMessage());
                taskMap.get(cus).setResult(directMarketDto);
            }
        }
    }
}
