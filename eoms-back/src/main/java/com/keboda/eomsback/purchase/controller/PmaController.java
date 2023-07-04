package com.keboda.eomsback.purchase.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmaFile;
import com.keboda.eomsback.purchase.pojo.PmcFile;
import com.keboda.eomsback.purchase.service.IPmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pma")
public class PmaController {

    @Autowired
    private IPmaService iPmaService;

    @PostMapping("/searchPmaListPageHelper")
    public BaseResult searchPmaListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<PmaFile> page = iPmaService.searchPmaListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }
}
