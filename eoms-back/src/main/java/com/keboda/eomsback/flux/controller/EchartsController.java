package com.keboda.eomsback.flux.controller;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.flux.service.IEchartsWcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IEchartsWcsService iEchartsWcsService;

    @RequestMapping("/wcsTaskTypeTime")
    public BaseResult wcsTaskTypeTime(String type){
        return BaseResult.success(iEchartsWcsService.wcsTaskTypeTime(type));
    }
}
