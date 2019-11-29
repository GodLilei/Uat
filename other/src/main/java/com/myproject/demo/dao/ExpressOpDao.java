package com.myproject.demo.dao;

import com.myproject.demo.entity.Express;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressOpDao {
    /**
     * 揽收
     * @param express 1
     */
    public void taking(@Param("taking")Express express);
    /**
     * 建包
     * @param express 1
     */
    public void build(@Param("build")Express express);
    /**
     * 下车
     * @param express 1
     */
    public void xiache(@Param("xiache")Express express);
    /**
     * 派件
     * @param express 1
     */
    public void handon(@Param("handon")Express express);
    /**
     * 签收
     * @param express 1
     */
    public void signature(@Param("signature")Express express);

    /**
     *
     * @param waybill 1
     * @return 1
     */
    public Integer checkHandon(String waybill);

    /**检查单号揽收状态*/
    public List<Express> queryTaking(String waybill_no);
    public List<Express> queryTaking_unsuccess(String waybill_no);
    public List<Express> queryTaking_deal(String waybill_no);

    /**检查单号派件状态*/
    public List<Express> queryHandon(String waybill_no);
    public List<Express> queryHandon_unsuccess(String waybill_no);
    public List<Express> queryHandon_deal(String waybill_no);

    /**检查单号签收状态*/
    public List<Express> querySignature(String waybill_no);
    public List<Express> querySignature_unsuccess(String waybill_no);
    public List<Express> querySignature_deal(String waybill_no);

    public void test(@Param("taking") Express taking);
    public void test1(@Param("taking")Express taking);
}
