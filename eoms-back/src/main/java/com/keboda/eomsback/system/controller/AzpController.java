package com.keboda.eomsback.system.controller;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.system.service.IAzpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/azp")
public class AzpController {

    @Autowired
    private IAzpService iAzpService;

    @GetMapping("/getAllCentre")
    public BaseResult getAllCentre(){
        return BaseResult.success(iAzpService.getAllCentre());
    }
}
