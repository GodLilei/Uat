package com.myproject.demo.services;

import com.myproject.demo.dao.ActiveWayBillNoDao;
import com.myproject.demo.entity.ActiveWayBillNo;
import com.myproject.demo.entity.CheckMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OldUatServices {
    @Resource
    private ActiveWayBillNoDao activeWayBillNoDao;

    public void addActive(ActiveWayBillNo activeWayBillNo){
        activeWayBillNoDao.addActive(activeWayBillNo);
    }
    public List<ActiveWayBillNo> checkActive(ActiveWayBillNo activeWayBillNo){
        return activeWayBillNoDao.checkActive(activeWayBillNo);
    }
    public void pwReset(String code){
        activeWayBillNoDao.pwReset(code);
    }
    public List<CheckMenu> checkMenu(String menu){
        return activeWayBillNoDao.checkMenu(menu);
    }
    public List<CheckMenu> checkAppId(String parentMenuId){
        return activeWayBillNoDao.checkAppId(parentMenuId);
    }
    public List<CheckMenu> checkAppName(String appId){
        return activeWayBillNoDao.checkAppName(appId);
    }
}
