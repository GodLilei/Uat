package com.myproject.demo.entity;

import lombok.Data;

@Data
public class CheckMenu {
    private String location = "empty";
    private String parent_menu_id;
    private String menu_level;
    private String app_id;
    private String app_name;
    private String menu_name;
}
