package com.stl.project.entity;

import lombok.Data;

@Data
public class LocalMaterial {
    private String waybill;
    private String materialOrg;
    private String materialCus;
    private String create_time;
    private String materialType;
    private char isUse;
    private int num;
    private String patchAdd;
}
