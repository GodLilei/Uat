package com.stl.project.servicesofdatasource;

import com.stl.project.dao.MaterialDao;
import com.stl.project.entity.ActiveMaterial;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SITDB {
    @Resource
    private MaterialDao materialDao;

    public List<ActiveMaterial> checkActive(String table,String array){
        return materialDao.checkActive(table,array);
    }
    public void activeOne(ActiveMaterial activeMaterial){
        materialDao.activeOne(activeMaterial);
    }
}
