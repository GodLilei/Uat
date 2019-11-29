package com.stl.project.datasourceconfig;

import com.stl.project.servicesofdatasource.FrontDB;
import com.stl.project.servicesofdatasource.RookieDB;
import com.stl.project.servicesofdatasource.SITDB;
import com.stl.project.servicesofdatasource.UATDB;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    /**
     * 使用空方法定义切点表达式
     */
    @Pointcut("execution(* com.stl.project.servicesofdatasource..*.*(..))")
    public void declareJointPointExpression() {
    }

    @Before("declareJointPointExpression()")
    public void setDataSourceKey(JoinPoint point) {
        //根据连接点所属的类实例，动态切换数据源
//        System.out.println("--------------------->>"+point.getTarget());
        if (point.getTarget() instanceof FrontDB) {
            DataBaseContextHolder.setDatabaseType(DataBaseType.FRONTDB);
        }else if (point.getTarget() instanceof RookieDB) {
            DataBaseContextHolder.setDatabaseType(DataBaseType.ROOKIE);
        }else if (point.getTarget() instanceof SITDB) {
            DataBaseContextHolder.setDatabaseType(DataBaseType.OLDUAT);
        }else if (point.getTarget() instanceof UATDB) {
            DataBaseContextHolder.setDatabaseType(DataBaseType.UATSTL);
        }else{
            DataBaseContextHolder.setDatabaseType(DataBaseType.mysql);
        }
    }
}
