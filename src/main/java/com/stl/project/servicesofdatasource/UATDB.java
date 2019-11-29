package com.stl.project.servicesofdatasource;

import com.stl.project.dao.businessdao.DirectMarketDao;
import com.stl.project.entity.businessentity.DirectMarket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UATDB {

    @Resource
    private DirectMarketDao directMarketDao;

    public List<DirectMarket> directCheck(String cus,String date) {
        return directMarketDao.directCheck(cus,date);
    }

    public void insertDirect(DirectMarket directMarket) {
        directMarketDao.insertDirect(directMarket);
    }

    public void updateDirect(DirectMarket directMarket) {
        directMarketDao.updateDirect(directMarket);
    }
}
