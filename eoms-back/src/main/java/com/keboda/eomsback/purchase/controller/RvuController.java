package com.keboda.eomsback.purchase.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import com.keboda.eomsback.purchase.pojo.RvbFile;
import com.keboda.eomsback.purchase.pojo.RvuFile;
import com.keboda.eomsback.purchase.pojo.RvvFile;
import com.keboda.eomsback.purchase.service.IRvuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/rvu")
public class RvuController {

    @Autowired
    private IRvuService iRvuService;

    @RequestMapping("/searchRvuListPageHelper")
    public BaseResult searchRvuListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<RvuFile> page = iRvuService.searchRvuListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/searchRvvList")
    public BaseResult searchRvvList(@RequestBody SearchVo searchVo){
        List<RvvFile> list = iRvuService.searchRvvList(searchVo);
        return BaseResult.success(list);
    }
}
