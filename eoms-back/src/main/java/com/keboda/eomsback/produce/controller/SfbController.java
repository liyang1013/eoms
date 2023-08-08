package com.keboda.eomsback.produce.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.SfbFile;
import com.keboda.eomsback.produce.pojo.vo.SfbModify;
import com.keboda.eomsback.produce.pojo.vo.SfbProcessVo;
import com.keboda.eomsback.produce.service.ISfbService;
import com.keboda.eomsback.entity.BaseResult;
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

    @RequestMapping("/searchSfbListPageHelper")
    public BaseResult searchSfbListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<SfbFile> sfbFilePage = iSfbService.searchSfbListPageHelper(searchVo);
        return BaseResult.success(sfbFilePage,sfbFilePage.getTotal());

    }

    @RequestMapping("/modifySfbDate")
    public BaseResult modifySfbDate(@RequestBody SfbModify sfbModify){
        iSfbService.modifySfbDate(sfbModify);
        return BaseResult.success("修改工单日期成功");
    }

    @RequestMapping("/closeOut")
    public BaseResult closeOut(@RequestBody SfbModify sfbModify){
        iSfbService.closeOut(sfbModify);
        return BaseResult.success("工单结案成功");
    }
}
