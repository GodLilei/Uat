package com.stl.project.basecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DataDriver {

    Object[][] getData(List<Map<String, String>> list){
        Object[][] result = null;
        result = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++){
            result[i] = new Object[]{list.get(i)};
        }
        return result;
    }
}
