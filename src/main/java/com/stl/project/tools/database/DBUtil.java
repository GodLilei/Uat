package com.stl.project.tools.database;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class DBUtil {
    private static Logger logger = org.apache.log4j.Logger.getLogger(DBUtil.class);
    /**查询数据库数据*/
    public List selectMySQLMessage(String driver,String url,String user,String password,String sql) throws java.sql
            .SQLException{
        Connection con;
        Statement stat;
        ResultSet resultSet;
        List list = new ArrayList();
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            if (!con.isClosed()){
                logger.info("success connection!!!");
            }
            stat = con.createStatement();
            resultSet = stat.executeQuery(sql);
            list = resultSetToList(resultSet);
            resultSet.close();
            stat.close();
            con.close();
        }catch (Exception e){
            logger.error("----->已关闭" + e.getMessage());
        }
        return list;
    }

    /**ResultSet转List*/
    private List resultSetToList(ResultSet rs) throws java.sql.SQLException {
        if (rs == null)
            return Collections.EMPTY_LIST;
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> rowData;
        try{
            while (rs.next()) {
                rowData = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            logger.info("----->数据转换成功：list:" + list.size());
        }catch (Exception e){
            rs.close();
            logger.error("发生错误：" + e.getMessage());
        }
        rs.close();
        return list;
    }

}
