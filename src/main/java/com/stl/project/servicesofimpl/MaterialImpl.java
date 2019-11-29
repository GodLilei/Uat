package com.stl.project.servicesofimpl;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.ActiveMaterial;
import com.stl.project.entity.LocalMaterial;
import com.stl.project.servicesofdatasource.MysqlDB;
import com.stl.project.servicesofdatasource.SITDB;
import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class
MaterialImpl {
    @Resource
    private SITDB sitdb;
    @Resource
    private MysqlDB mysqlDB;
    @Resource
    private DateUtil dateUtil;
    @Resource
    private BaseResponse baseResponse;
    @Resource
    private JSONChange jsonChange;

    private static final String TABLE_NAME = "YTEXP.T_EXP_WAYBILL_";

    public BaseResponse activeOne(ActiveMaterial activeWayBillNo){
        activeWayBillNo.setTableName(TABLE_NAME.concat(activeWayBillNo.getWaybill_no()
                .substring(activeWayBillNo.getWaybill_no().length()-3)));
        String flag = "";
        activeWayBillNo.setMat_create_time(dateUtil.dateOfAfter("day",-1));
        activeWayBillNo.setMat_expire_time(dateUtil.dateOfAfter("year",2));
        activeWayBillNo.setCreate_time(dateUtil.nowDate());
        activeWayBillNo.setModify_time(dateUtil.nowDate());
        try{
            List<ActiveMaterial> list = checkActive(activeWayBillNo.getTableName(),new String[]{activeWayBillNo.getWaybill_no()});
            if (!list.isEmpty()){
                flag = "单号：" + activeWayBillNo.getWaybill_no() + "已经激活，无需再次激活";
                baseResponse.setCode("0");
                baseResponse.setMessage("success");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + flag + "\"}"));
            }else {
                sitdb.activeOne(activeWayBillNo);
                flag = "单号：" + activeWayBillNo.getWaybill_no() + "激活成功！";
                baseResponse.setCode("0");
                baseResponse.setMessage("success");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + flag + "\"}"));
            }
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage("failure");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + e.getMessage() + "\"}"));
        }
        return baseResponse;
    }
    private List<ActiveMaterial> checkActive(String table,String[] strings){
        if (strings.length == 1){
            return sitdb.checkActive(table,strings[0]);
        }else{
            List<ActiveMaterial> result = new ArrayList<>();
            for (String string : strings) {
                result.addAll(sitdb.checkActive(table.substring(0,table.length()-3).concat(string.substring(string.length()-3)), string));
            }
            return result;
        }
    }
    public BaseResponse activeMore(ActiveMaterial activeMaterial){
        activeMaterial.setTableName(TABLE_NAME.concat(activeMaterial.getWaybill_no()
                .substring(activeMaterial.getWaybill_no().length()-3)));
        activeMaterial.setMat_create_time(dateUtil.dateOfAfter("day",-1));
        activeMaterial.setMat_expire_time(dateUtil.dateOfAfter("year",2));
        activeMaterial.setCreate_time(dateUtil.nowDate());
        activeMaterial.setModify_time(dateUtil.nowDate());
        //获取原始批量单号
        List<String> oldList = new ArrayList<>();
        String[] oldWaybill = getWaybillNos(activeMaterial.getWaybill_no(),activeMaterial.getNum(),oldList).toArray
                (new String[0]);
        //检测这批单号里面是否全部未激活，如果存在激活的，则不激活
        List<ActiveMaterial> list = checkActive(activeMaterial.getTableName(),oldWaybill);
        if (!list.isEmpty()){//当有激活单号返回时
            //如果list不为空，则剔除不为空（list返回的单号）的数据，然后执行激活操作
            String[] existWaybill = new String[list.size()];
            for (int i = 0; i < list.size(); i++){
                existWaybill[i] = list.get(i).getWaybill_no();
            }
            existWaybill = compareStringArrays(oldWaybill,existWaybill);
            if (existWaybill.length == 0){
                baseResponse.setCode("0");
                baseResponse.setMessage("success");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"此批单号全部是激活状态\"}"));
            }else {
                for (String anExistWaybill : existWaybill) {
                    activeMaterial.setTableName(TABLE_NAME.concat(anExistWaybill.substring(activeMaterial.getWaybill_no().length() - 3)));
                    activeMaterial.setWaybill_no(anExistWaybill);
                    sitdb.activeOne(activeMaterial);
                }
                baseResponse.setCode("0");
                baseResponse.setMessage("success");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"总共：" + oldWaybill.length + "，激活：" + existWaybill.length + "\"}"));
            }
        }else{
            //list为空，直接将全部数据执行激活操作
            for (String anOldWaybill : oldWaybill) {
                activeMaterial.setTableName(TABLE_NAME.concat(anOldWaybill.substring(activeMaterial.getWaybill_no().length() - 3)));
                activeMaterial.setWaybill_no(anOldWaybill);
                sitdb.activeOne(activeMaterial);
                baseResponse.setCode("0");
                baseResponse.setMessage("success");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"全部激活成功\"}"));
            }
        }
        return baseResponse;
    }

    public List<LocalMaterial> materialFind(LocalMaterial localMaterial){
        return mysqlDB.materialFind(localMaterial);
    }

    public BaseResponse matUsed(String waybill){
        try{
            mysqlDB.matUsed(waybill);
            baseResponse.setCode("0");
            baseResponse.setMessage("success");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"成功从本地物料池中取出\"}"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage("failure");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + e.getMessage() + "\"}"));
        }
        return baseResponse;
    }

    public BaseResponse saveMat(LocalMaterial localMaterial){
        List<String> results = new ArrayList<>();
        List<String> exist = new ArrayList<>();
        List<String> waybills = getWaybillNos(localMaterial.getWaybill(),localMaterial.getNum(),results);
        String[] patchAdd = localMaterial.getPatchAdd().split("\n");
        for (String str:patchAdd){
            if (!waybills.contains(str)){
                waybills.add(str);
            }
        }
        if (!waybills.isEmpty()){
            for (String str:waybills) {
                List<LocalMaterial> isExist = mysqlDB.checkMat(str);
                if (!isExist.isEmpty()){
                    exist.add(str);
                }
            }
            String[] save = compareStringArrays(waybills.toArray(new String[0]),exist.toArray(new String[0]));
            for (String str:save){
                localMaterial.setWaybill(str);
                localMaterial.setCreate_time(dateUtil.nowDate());
                try{
                    mysqlDB.saveMat(localMaterial);
                }catch (Exception e){
                    baseResponse.setCode("-1");
                    baseResponse.setMessage("failure");
                    baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"发现错误：" + e.getMessage() + "\"}"));
                    break;
                }
            }
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"输入物料共：" + waybills.size() +
                    "<br>发现重复物料：" + exist.size() + "<br>保存成功：" + save.length + "\"}"));
            baseResponse.setMessage("success");
            baseResponse.setCode("0");
        }else {
            baseResponse.setMessage("success");
            baseResponse.setCode("0");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"无物料信息\"}"));
        }
        return baseResponse;
    }

    //[1,2,3,4]-[1,3]=[2,4]
    private String[] compareStringArrays(String[] arr1, String[] arr2){
        List<String> arrList1;
        List<String> arrList2;
        //arr1个数大于arr2
        if (arr1.length >= arr2.length){
            arrList1 = Arrays.asList(arr1);
            arrList2 = Arrays.asList(arr2);
        }else {
            arrList1 = Arrays.asList(arr2);
            arrList2 = Arrays.asList(arr1);
        }
        List<String> list1 = new ArrayList<>(arrList1);
        List<String> list2 = new ArrayList<>(arrList2);

        //list2必包含于list1里面，直接比较
        for (int i = 0; i < list1.size(); i++){
            if (list2.contains(list1.get(i))){
                list1.remove(i);
                i--;
            }
        }
        return list1.toArray(new String[0]);
    }
    /**
     * 返回激活单号list
     * @param string 起始单号
     * @param reg 激活范围
     * @param waybillNos 初始化激活单号list
     */
    private List<String> getWaybillNos(String string, int reg, List<String> waybillNos) {
        if (string.equals("")){
            return waybillNos;
        }
        waybillNos.add(string);
        //用来存单号开头的字母
        String firstName = "";
        //去除单号前的字母
        int index = 0;
        String resultString = "";
        while(index < string.length()) {
            if (string.charAt(index) >= 65 && string.charAt(index) <= 122) {
                index++;
            } else {
                firstName = string.substring(0, index);
                resultString = string.substring(index);
                break;
            }
        }
        String str = "0"+resultString;
        //拆分字符串
        String[] strs = str.split("");
        int[] arrs = new int[strs.length];
        //转int数组
        for(int i=0;i<arrs.length;i++) {
            arrs[i]=Integer.parseInt(strs[i]);
        }
        //遍历激活范围
        for(int i = 0;i < reg-1;i++){
            //遍历int数组的每一位,修改int数组的值,逐位+1
            for(int j = arrs.length;j>0;j--){
                if (arrs[j-1] + 1 <= 9) {
                    arrs[j-1]++;
                    break;
                } else {
                    arrs[j-1] = 0;
                    continue;
                }
            }
            String result = firstName+intArrToString(arrs);
            //添加到激活运单号list
            waybillNos.add(result);
        }
        return waybillNos;
    }

    /**
     * 判断首位是否是0
     * int数组转String
     * @param arrs
     */
    private String intArrToString(int arrs[]){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<arrs.length;i++){
            stringBuffer.append(arrs[i]);
        }
        //如果第一位是0
        if(stringBuffer.toString().charAt(0)=='0'){
            //结果字符串就是去掉第一位之后的String
            return stringBuffer.toString().substring(1);
        }else{
            return stringBuffer.toString();
        }
    }

}
