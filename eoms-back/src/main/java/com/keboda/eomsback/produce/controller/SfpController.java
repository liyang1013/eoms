package com.keboda.eomsback.produce.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.SfbFile;
import com.keboda.eomsback.produce.pojo.SfpFile;
import com.keboda.eomsback.produce.service.ISfpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sfp")
public class SfpController {

    @Autowired
    private ISfpService iSfpService;

    @RequestMapping("/searchSfpListPageHelper")
    public BaseResult searchSfpListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<SfpFile> page = iSfpService.searchSfpListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/searchSfseList")
    public BaseResult searchSfseList(@RequestBody SearchVo searchVo){
        return BaseResult.success(iSfpService.searchSfseList(searchVo));
    }
}
