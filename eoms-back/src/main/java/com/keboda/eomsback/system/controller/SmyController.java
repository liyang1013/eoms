package com.keboda.eomsback.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.SmyFile;
import com.keboda.eomsback.system.pojo.ZxFile;
import com.keboda.eomsback.system.service.ISmyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smy")
public class SmyController {

    @Autowired
    private ISmyService iSmyService;

    @RequestMapping("/searchSmyListPageHelper")
    public BaseResult searchSmyListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<SmyFile> page = iSmyService.searchSmyListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }
}
