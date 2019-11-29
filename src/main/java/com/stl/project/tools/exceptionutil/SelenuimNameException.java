package com.stl.project.tools.exceptionutil;

public class SelenuimNameException extends Exception{
    private String value;
    public SelenuimNameException(){
        super();
    }
    public SelenuimNameException(String msg, String value){
        super(msg);
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
