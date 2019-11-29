package com.stl.project.dao;

import com.stl.project.entity.LocalMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalMaterialDao {
    public List<LocalMaterial> findMaterial(@Param("param")LocalMaterial localMaterial);
    public void matUsed(@Param("param")String waybill);
    public List<LocalMaterial> checkMat(@Param("param")String waybill);
    public void saveMat(@Param("param")LocalMaterial localMaterial);
}
