package com.stl.project.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class BaseResponse {
    private String code;
    private String message;
    private JSONArray data;
}
