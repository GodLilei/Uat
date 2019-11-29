package com.stl.project.datasourceconfig;

/**
 * 作用：
 * 1、保存一个线程安全的DatabaseType容器
 */
public class DataBaseContextHolder {
    private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<>();
    static DataBaseType getDatabaseType(){
        return contextHolder.get();
    }
    public static void setDatabaseType(DataBaseType type) {
        contextHolder.set(type);
    }
}
