package com.myproject.demo.TestPage;


import com.myproject.demo.utils.Tool;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

@Slf4j
public class TestConnectionMysql {

    public static void main(String[] args) throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/sqltest";
        String user = "root";
        String password = "";
        String sql = "select * from identification";

        Tool tool = new Tool();

        System.out.println(tool.selectMySQLMessage(driver,url,user,password,sql));


    }

}
