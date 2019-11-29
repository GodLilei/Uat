package com.stl.project.dao;

import com.stl.project.entity.Express;
import org.apache.ibatis.annotations.Param;

public interface ExpressDao {
    /**
     * 揽收
     * @param express 揽收参数
     */
    public void taking(@Param("taking")Express express);
    /**
     * 建包
     * @param express 建包参数
     */
    public void build(@Param("build")Express express);
    /**
     * 下车
     * @param express 下车参数
     */
    public void xiache(@Param("xiache")Express express);
    /**
     * 派件
     * @param express 派件参数
     */
    public void handon(@Param("handon")Express express);
    /**
     * 签收
     * @param express 签收参数
     */
    public void signature(@Param("signature")Express express);

    /**
     * 自动揽收-下车
     * @param taking canshu
     */
    public void autoTaking_x(@Param("taking") Express taking);
    /**
     * 自动揽收-揽收
     * @param taking canshu
     */
    public void autoTaking_t(@Param("taking")Express taking);
}
