package com.keboda.eomsback.base.controller;

import com.keboda.eomsback.base.pojo.Operation;
import com.keboda.eomsback.base.service.IOperationService;
import com.keboda.eomsback.base.pojo.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private IOperationService iOperationService;

    @RequestMapping("/searchOperation")
    public BaseResult searchOperation(@RequestBody Operation operation) throws FileNotFoundException {
        List<Operation> list = iOperationService.searchOperation(operation);
        return BaseResult.success(list);
    }
}
