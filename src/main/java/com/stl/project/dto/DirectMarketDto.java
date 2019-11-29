package com.stl.project.dto;

import lombok.Data;

@Data
public class DirectMarketDto {
    private String code = "0";//0:正常，-1：异常，1：停止。
    private String cus;
    private String timeout;
    private String message;
}
