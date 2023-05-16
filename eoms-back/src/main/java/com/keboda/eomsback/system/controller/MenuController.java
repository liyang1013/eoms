package com.keboda.eomsback.system.controller;

import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    @GetMapping("/getAllMenu")
    public BaseResult getAllMenu(){
        return BaseResult.success(iMenuService.getAllMenu());
    }

    @GetMapping("/getChildrenMenu")
    public BaseResult getChildrenMenu(Integer pid){
        return BaseResult.success(iMenuService.getChildrenMenu(pid));
    }
}
