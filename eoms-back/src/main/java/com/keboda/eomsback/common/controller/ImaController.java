package com.keboda.eomsback.common.controller;

import com.keboda.eomsback.common.pojo.ImaFile;
import com.keboda.eomsback.common.pojo.ImgFile;
import com.keboda.eomsback.common.pojo.SfbFile;
import com.keboda.eomsback.common.service.IImaService;
import com.keboda.eomsback.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/ima")
@RestController
public class ImaController {

    @Autowired
    private IImaService iImaService;

    @RequestMapping("/imaArr")
    public BaseResult imaArr(String queryStr, String centre){
        List<ImaFile> arr;
        try{arr = iImaService.imaArr(queryStr,centre); }
        catch (Exception e) { return BaseResult.fail(e.getMessage()); }
        return BaseResult.success(arr);
    }

    @RequestMapping("/imgArr")
    public BaseResult imgArr(String ima01, String centre){
        List<ImgFile> arr;
        try{arr = iImaService.imgArr(ima01,centre); }
        catch (Exception e) { return BaseResult.fail(e.getMessage()); }
        return BaseResult.success(arr);
    }


}
