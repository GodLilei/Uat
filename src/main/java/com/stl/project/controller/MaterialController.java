package com.stl.project.controller;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.ActiveMaterial;
import com.stl.project.entity.LocalMaterial;
import com.stl.project.servicesofimpl.MaterialImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/Express")
public class MaterialController {
    @Resource
    private MaterialImpl material;

    @RequestMapping(value = "/activeOne")
    public BaseResponse activeOne(@RequestBody ActiveMaterial param){
        return material.activeOne(param);
    }
    @RequestMapping(value = "/activeMore")
    public BaseResponse activeMore(@RequestBody ActiveMaterial param){
        return material.activeMore(param);
    }
    @RequestMapping(value = "/materialFind")
    public List<LocalMaterial> materialFind(@RequestBody LocalMaterial param){
        return material.materialFind(param);
    }
    @RequestMapping(value = "/matUsed")
    public BaseResponse matUsed(@RequestBody String param){
        return material.matUsed(param);
    }
    @RequestMapping(value = "/saveMat")
    public BaseResponse saveMat(@RequestBody LocalMaterial param){
        return material.saveMat(param);
    }
}
