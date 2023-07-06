package com.keboda.eomsback.purchase.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmiFile;
import com.keboda.eomsback.purchase.pojo.PmkFile;
import com.keboda.eomsback.purchase.service.IPmkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pmk")
public class PmkController {

    @Autowired
    private IPmkService iPmkService;

    @PostMapping("/searchPmkListPageHelper")
    public BaseResult searchPmkListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<PmkFile> page = iPmkService.searchPmkListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @PostMapping("/searchPmlList")
    public BaseResult searchPmlList(@RequestBody SearchVo searchVo){
        return BaseResult.success(iPmkService.searchPmlList(searchVo));
    }
}
