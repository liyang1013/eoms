package com.keboda.eomsback.common.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.common.pojo.SfbFile;
import com.keboda.eomsback.common.pojo.vo.SfbModify;
import com.keboda.eomsback.common.pojo.vo.SfbProcessVo;
import com.keboda.eomsback.common.pojo.vo.SfbSearchVo;
import com.keboda.eomsback.common.service.ISfbService;
import com.keboda.eomsback.common.BaseResult;
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
        List<SfbFile> asf01_arr;
        try{asf01_arr = iSfbService.sfb01Arr(queryStr,centre); }
        catch (Exception e) { return BaseResult.fail(e.getMessage()); }
        return BaseResult.success(asf01_arr);
    }

    @RequestMapping("/sfbProcess")
    public BaseResult sfbProcess(String sfb01,String centre){
        SfbProcessVo sfbProcessVo;
        try{sfbProcessVo = iSfbService.sfbProcess(sfb01,centre);}
        catch (Exception e){return BaseResult.fail(e.getMessage());}
        return BaseResult.success(sfbProcessVo);
    }

    @RequestMapping("/sfbArr")
    public BaseResult sfbArr(@RequestBody SfbSearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<SfbFile> sfbFilePage;
        try{sfbFilePage = iSfbService.sfbArr(searchVo);}
        catch (Exception e){return BaseResult.fail(e.getMessage());}
        return BaseResult.success(sfbFilePage,sfbFilePage.getTotal());

    }

    @RequestMapping("/modifyDate")
    public BaseResult modifyDate(@RequestBody SfbModify sfbModify){
        try{iSfbService.modifyDate(sfbModify);}
        catch (Exception e){return BaseResult.fail(e.getMessage());}
        return BaseResult.success("修改工单日期成功");
    }
}
