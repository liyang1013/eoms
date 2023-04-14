package com.keboda.eomsback.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.Operation;
import com.keboda.eomsback.system.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private IOperationService iOperationService;

    @RequestMapping("/searchOperationList")
    public BaseResult searchOperationList(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<Operation> page = iOperationService.searchOperationList(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/deletOperation")
    public BaseResult deletOperation(@RequestBody Operation operation){
        iOperationService.deletOperation(operation);
        return BaseResult.success();
    }
}
