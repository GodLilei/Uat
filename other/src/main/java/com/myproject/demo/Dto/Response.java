package com.myproject.demo.Dto;

import lombok.Data;

@Data
public class Response {
    private String code = "0";
    private String message = "";

    public Response(String code,String message){
        this.code = code;
        this.message = message;
    }
    public Response(){
    }
}
