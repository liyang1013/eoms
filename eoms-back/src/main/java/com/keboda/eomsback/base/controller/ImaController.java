package com.keboda.eomsback.base.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.base.pojo.ImaFile;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.common.pojo.ImgFile;
import com.keboda.eomsback.base.service.IImaService;
import com.keboda.eomsback.common.BaseResult;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/ima")
@RestController
public class ImaController {

    @Autowired
    private IImaService iImaService;

    @RequestMapping("/searchImaList")
    public BaseResult searchImaList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<ImaFile> page;
        try{page = iImaService.searchImaList(searchVo); }
        catch (Exception e) { return BaseResult.fail(e.getMessage()); }
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/imgArr")
    public BaseResult imgArr(String ima01, String centre){
        List<ImgFile> arr;
        try{arr = iImaService.imgArr(ima01,centre); }
        catch (Exception e) { return BaseResult.fail(e.getMessage()); }
        return BaseResult.success(arr);
    }


}
