package com.myproject.demo.dao;

import com.myproject.demo.entity.ActiveWayBillNo;
import com.myproject.demo.entity.CheckMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActiveWayBillNoDao {
    /**
     * description:未激活单号添加激活信息
     * @param activeWayBillNo 数据实体
     */
    public void addActive(@Param("active")ActiveWayBillNo activeWayBillNo);

    /**
     * 查询单号是否激活
     * @param activeWayBillNo 输入运单号码和表名查询是否激活
     * @return 返回信息
     */
    public List<ActiveWayBillNo> checkActive(@Param("active")ActiveWayBillNo activeWayBillNo);

    /**
     * 重置密码
     * @param code 工号
     */
    public void pwReset(@Param("code")String code);

    /**
     * 根据菜单名查找菜单url
     * @param menu 菜单名
     * @return 返回appId,menuID,URL
     */
    public List<CheckMenu> checkMenu(@Param("menu")String menu);
    public List<CheckMenu> checkAppId(@Param("parentMenuId")String parentMenuId);
    public List<CheckMenu> checkAppName(@Param("appId")String appId);
}
