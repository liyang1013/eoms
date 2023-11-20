package com.keboda.eomsback.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.ZxFile;
import com.keboda.eomsback.system.pojo.ZxwFile;
import com.keboda.eomsback.system.service.IZxwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zxw")
public class ZxwController {

    @Autowired
    private IZxwService iZxwService;

    @RequestMapping("/searchZxwListPageHelper")
    public BaseResult searchZxwListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<ZxwFile> page = iZxwService.searchZxwListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/authorityReview")
    public BaseResult authorityReview(){
        iZxwService.authorityReview();
        return BaseResult.success();
    }
}
