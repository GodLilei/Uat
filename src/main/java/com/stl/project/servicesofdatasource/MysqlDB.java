package com.stl.project.servicesofdatasource;

import com.stl.project.dao.ExpressProDao;
import com.stl.project.dao.LocalMaterialDao;
import com.stl.project.dao.LoginDao;
import com.stl.project.entity.ExpressProcess;
import com.stl.project.entity.LocalMaterial;
import com.stl.project.entity.LoginPojo;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MysqlDB {
    @Resource
    private ExpressProDao expressProcessDao;
    @Resource
    private LocalMaterialDao localMaterialDao;
    @Resource
    private LoginDao loginDao;

    public void insertExpPro(ExpressProcess expressProcess){
        expressProcessDao.insertExpPro(expressProcess);
    }
    public List<ExpressProcess> queryByWaybillNo(String waybillNo){
        return expressProcessDao.queryByWaybillNo(waybillNo);
    };
    public void updateExpPro(ExpressProcess expressProcess){
        expressProcessDao.updateExpPro(expressProcess);
    }
    public List<ExpressProcess> expressDetail(ExpressProcess expressProcess){
        return expressProcessDao.expressDetail(expressProcess);
    }
    public void delExpPro(String[] array){
        expressProcessDao.delExpPro(array);
    };

    public List<LocalMaterial> materialFind(LocalMaterial localMaterial){
        return localMaterialDao.findMaterial(localMaterial);
    }
    public void matUsed(String waybill){
        localMaterialDao.matUsed(waybill);
    }
    public List<LocalMaterial> checkMat(String waybill){
        return localMaterialDao.checkMat(waybill);
    }
    public void saveMat(LocalMaterial localMaterial){
        localMaterialDao.saveMat(localMaterial);
    }
    public List<LoginPojo> normalLogin(String phone){
        return loginDao.normalLogin(phone);
    }
    public List<LoginPojo> fastLogin(String ip){
        return loginDao.fastLogin(ip);
    }
    public List<LoginPojo> queryIP(LoginPojo loginPojo){
        return loginDao.queryIP(loginPojo);
    }
}
