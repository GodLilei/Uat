package com.myproject.demo.services;

import com.myproject.demo.dao.RookieXiadanDao;
import com.myproject.demo.entity.Rookie;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RookieXiadanServices {

    @Resource
    private RookieXiadanDao rookieXiadanDao;

    public void rookieXiadan(Rookie rookie){
        rookieXiadanDao.rookieXiadan(rookie);
    }
}
