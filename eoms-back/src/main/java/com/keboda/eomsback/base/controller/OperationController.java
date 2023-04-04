package com.keboda.eomsback.base.controller;

import com.keboda.eomsback.base.pojo.Operation;
import com.keboda.eomsback.base.service.IOperationService;
import com.keboda.eomsback.common.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.OpenOption;
import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private IOperationService iOperationService;

    @RequestMapping("/searchOperation")
    public BaseResult searchOperation(@RequestBody Operation operation){
        List<Operation> list;
        try{list = iOperationService.searchOperation(operation);}
        catch (Exception e){return BaseResult.fail(e.getMessage());}
        return BaseResult.success(list);
    }
}
