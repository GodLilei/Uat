package com.stl.project.dao.businessdao;

import com.stl.project.entity.businessentity.DirectMarket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DirectMarketDao {

    List<DirectMarket> directCheck(@Param("cus")String cus,@Param("date")String date);
    void insertDirect(@Param("param")DirectMarket directMarket);
    void updateDirect(@Param("param")DirectMarket directMarket);
}
