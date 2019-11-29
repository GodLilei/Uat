package com.stl.project.servicesofdatasource;

import com.stl.project.dao.ExpressDao;
import com.stl.project.entity.Express;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FrontDB {
    @Resource
    private ExpressDao expressDao;

    public void taking(Express express){
        expressDao.taking(express);
    }
    public void build(Express express){
        expressDao.build(express);
    }
    public void xiache(Express express){
        expressDao.xiache(express);
    }
    public void handon(Express express){
        expressDao.handon(express);
    }
    public void signature(Express express){
        expressDao.signature(express);
    }
    public void autoTaking_x(Express express){
        expressDao.autoTaking_x(express);
    }
    public void autoTaking_t(Express express){
        expressDao.autoTaking_t(express);
    }
}
