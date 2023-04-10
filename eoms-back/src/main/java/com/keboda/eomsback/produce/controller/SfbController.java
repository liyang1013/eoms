package com.keboda.eomsback.produce.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.base.pojo.SearchVo;
import com.keboda.eomsback.produce.pojo.SfbFile;
import com.keboda.eomsback.produce.pojo.vo.SfbModify;
import com.keboda.eomsback.produce.pojo.vo.SfbProcessVo;
import com.keboda.eomsback.produce.pojo.vo.SfbSearchVo;
import com.keboda.eomsback.produce.service.ISfbService;
import com.keboda.eomsback.base.pojo.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sfb")
public class SfbController {

    @Autowired
    private ISfbService iSfbService;

    @RequestMapping("/sfb01Arr")
    public BaseResult sfb01Arr(String queryStr,String centre){
        List<SfbFile> asf01_arr = iSfbService.sfb01Arr(queryStr,centre);
        return BaseResult.success(asf01_arr);
    }

    @RequestMapping("/sfbProcess")
    public BaseResult sfbProcess(String sfb01,String centre){
        SfbProcessVo sfbProcessVo = iSfbService.sfbProcess(sfb01,centre);
        return BaseResult.success(sfbProcessVo);
    }

    @RequestMapping("/searchSfbList")
    public BaseResult searchSfbList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<SfbFile> sfbFilePage = iSfbService.searchSfbList(searchVo);
        return BaseResult.success(sfbFilePage,sfbFilePage.getTotal());

    }

    @RequestMapping("/modifyDate")
    public BaseResult modifyDate(@RequestBody SfbModify sfbModify){
        iSfbService.modifyDate(sfbModify);
        return BaseResult.success("修改工单日期成功");
    }
}
