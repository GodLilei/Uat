package com.myproject.demo.dao;

import com.myproject.demo.entity.Rookie;
import org.apache.ibatis.annotations.Param;

public interface RookieXiadanDao {
    public void rookieXiadan(@Param("rookie")Rookie rookie);
}
