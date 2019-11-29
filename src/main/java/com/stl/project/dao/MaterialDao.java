package com.stl.project.dao;

import com.stl.project.entity.ActiveMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialDao {
    //检查激活信息
    public List<ActiveMaterial> checkActive(@Param("param")String tableName,@Param("array")String array);
    //物料激活
    public void activeOne(@Param("param") ActiveMaterial activeMaterial);
}
