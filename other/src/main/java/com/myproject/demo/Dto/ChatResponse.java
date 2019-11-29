package com.myproject.demo.Dto;

import lombok.Data;

@Data
public class ChatResponse {
    String user;
    String message;
    String timeout;
}
