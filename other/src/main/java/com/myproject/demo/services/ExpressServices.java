package com.myproject.demo.services;

import com.myproject.demo.dao.ExpressOpDao;
import com.myproject.demo.entity.Express;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExpressServices {

    @Resource
    private ExpressOpDao expressOpDao;

    /**
     * 揽收
     * @param express
     */
    public void taking(Express express){
        expressOpDao.taking(express);
    }
    /**建包*/
    public void build(Express express){
        expressOpDao.build(express);
    }
    public void xiache(Express express){
        expressOpDao.xiache(express);
    }
    public void handon(Express express){
        expressOpDao.handon(express);
    }
    public void signature(Express express){
        expressOpDao.signature(express);
    }
    public Integer checkHandon(String waybill){
        return expressOpDao.checkHandon(waybill);
    }

    /**检查单号揽收状态*/
    public List<Express> queryTaking(String waybill_no){
        return expressOpDao.queryTaking(waybill_no);
    }
    public List<Express> queryTaking_unsuccess(String waybill_no){
        return expressOpDao.queryTaking_unsuccess(waybill_no);
    }
    public List<Express> queryTaking_deal(String waybill_no){
        return  expressOpDao.queryTaking_deal(waybill_no);
    }

    /**检查单号派件状态*/
    public List<Express> queryHandon(String waybill_no){
        return expressOpDao.queryHandon(waybill_no);
    }
    public List<Express> queryHandon_unsuccess(String waybill_no){
        return expressOpDao.queryHandon_unsuccess(waybill_no);
    }
    public List<Express> queryHandon_deal(String waybill_no){
        return expressOpDao.queryHandon_deal(waybill_no);
    }

    /**检查单号签收状态*/
    public List<Express> querySignature(String waybill_no){
        return expressOpDao.querySignature(waybill_no);
    }
    public List<Express> querySignature_unsuccess(String waybill_no){
        return expressOpDao.querySignature_unsuccess(waybill_no);
    }
    public List<Express> querySignature_deal(String waybill_no){
        return expressOpDao.querySignature_deal(waybill_no);
    }

    public void test(Express express){
        expressOpDao.test(express);
    }
    public void test1(Express express){
        expressOpDao.test1(express);
    }
}
