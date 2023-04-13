package com.keboda.eomsback.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.GenFile;
import com.keboda.eomsback.system.service.IGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gen")
public class GenController {

    @Autowired
    private IGenService iGenService;

    @RequestMapping("/login")
    public BaseResult login(@RequestBody GenFile genFile){
        return BaseResult.success(iGenService.login(genFile));
    }

    @RequestMapping("/searchGenList")
    public BaseResult searchGenList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<GenFile> page = iGenService.searchGenList(searchVo);
        return BaseResult.success(page,page.getTotal());
    }
}
