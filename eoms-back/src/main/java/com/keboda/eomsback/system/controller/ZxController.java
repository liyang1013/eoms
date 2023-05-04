package com.keboda.eomsback.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.GenFile;
import com.keboda.eomsback.system.pojo.ZxFile;
import com.keboda.eomsback.system.service.IZxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zx")
public class ZxController {

    @Autowired
    private IZxService iZxService;

    @RequestMapping("/searchZxListPageHelper")
    public BaseResult searchGenListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<ZxFile> page = iZxService.searchZxListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/login")
    public BaseResult login(@RequestBody ZxFile zxFile){
        return BaseResult.success(iZxService.login(zxFile));
    }

}
