package com.stl.project.dao;

import com.stl.project.entity.LoginPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginDao {

    public List<LoginPojo> normalLogin(@Param("param")String phone);
    public List<LoginPojo> fastLogin(@Param("param")String ip);
    public List<LoginPojo> queryIP(@Param("param")LoginPojo loginPojo);
}
