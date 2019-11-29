package com.stl.project.servicesofimpl;

import com.alibaba.fastjson.JSON;
import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.Express;
import com.stl.project.entity.Rookie;
import com.stl.project.servicesofdatasource.RookieDB;
import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.date.DateUtil;
import com.stl.project.tools.httputil.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class RookieImpl {
    @Resource
    private RookieDB rookieDB;
    @Resource
    private BaseResponse baseResponse;
    @Resource
    private DateUtil dateUtil;
    @Resource
    private JSONChange jsonChange;
    @Resource
    private HttpRequest httpRequest;
    /**返回单号*/
    public String returnWaybillNo(String orgCode){
        Date d = new Date();
        String c = String.valueOf(d.getTime());
        Map<String,Object> map = jsonChange.JsonStringToMap("{\"type\":\"1\",\n" +
                "\"orgCode\":\"" + orgCode + "\",\n" +
                "\"waybillNo\":\"80" + c.substring(c.length()-10) + "\"}");
        return httpRequest.requestPost("http://jingang.yto56.com.cn/matcn/waybillNoEncodeServlet",map,"application/x-www-form-urlencoded",null,"");
    }


    public BaseResponse waybillGet(Express taking){
        BaseResponse baseResponse = new BaseResponse();
        try{
            String waybillNo = returnWaybillNo(taking.getSource_org_code());
            baseResponse.setCode("0");
//            baseResponse.setData(JSON.parseArray("[{\"wbn\":\"" + msg.substring(msg.length()-18) + "\"}]"));
            baseResponse.setData(JSON.parseArray("[{\"wbn\":\"" + waybillNo + "\"}]"));
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }

    public BaseResponse xiadan(Express taking){
        Rookie rookie = new Rookie();
        rookie.setCURTIME(dateUtil.nowDate());
        rookie.setWAYBILL_NO(taking.getWaybill_no());
        rookie.setBIZ_ID("TB".concat(dateUtil.ymdString()).concat(dateUtil.hmsString()).concat(dateUtil.fourRandom()));
        rookie.setBRANCH_CODE(taking.getSource_org_code());
        rookie.setBack_1(taking.getDes_org_code());
        rookie.setSELLER_ID(taking.getSeller());
        rookie.setCustomerCode(taking.getEmp_code());
        try{
            rookieDB.rookieXiadan(rookie);
            baseResponse.setCode("0");
            baseResponse.setMessage("下单成功！网点：" + taking.getSource_org_code() + " \n客户：" + taking.getEmp_code() +
                    "\n单号：" + taking.getWaybill_no());
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
}
