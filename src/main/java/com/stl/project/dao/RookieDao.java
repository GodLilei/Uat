package com.stl.project.dao;

import com.stl.project.entity.Rookie;
import org.apache.ibatis.annotations.Param;

public interface RookieDao {
    public void rookieXiadan(@Param("rookie")Rookie rookie);
}
