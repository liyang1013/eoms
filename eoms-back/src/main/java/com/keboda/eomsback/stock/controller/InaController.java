package com.keboda.eomsback.stock.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.InaFile;
import com.keboda.eomsback.stock.service.IInaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ina")
public class InaController {

    @Autowired
    private IInaService iInaService;

    @PostMapping("/searchInaListPageHelper")
    public BaseResult searchInaListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<InaFile> page = iInaService.searchInaListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @PostMapping("/searchInbList")
    public BaseResult searchInbList(@RequestBody SearchVo searchVo){
        return BaseResult.success(iInaService.searchInbList(searchVo));
    }

    @PostMapping("/alterGem")
    public BaseResult alterGem(@RequestBody InaFile inaFile){
        iInaService.alterGem(inaFile);
        return BaseResult.success("修改部门成功");
    }
}
