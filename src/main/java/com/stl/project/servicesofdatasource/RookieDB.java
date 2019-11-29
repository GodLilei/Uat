package com.stl.project.servicesofdatasource;

import com.stl.project.dao.RookieDao;
import com.stl.project.entity.Rookie;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RookieDB {
    @Resource
    private RookieDao rookieDao;

    public void rookieXiadan(Rookie rookie){
        rookieDao.rookieXiadan(rookie);
    }
}
