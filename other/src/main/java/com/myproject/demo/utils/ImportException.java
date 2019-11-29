package com.myproject.demo.utils;

import lombok.Data;

public class ImportException extends Exception {
    private String value;
    public ImportException(){
        super();
    }
    public ImportException(String msg,String value){
        super(msg);
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
