package com.keboda.eomsback.system.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.Operation;

import java.util.List;

public interface IOperationService {
    Page<Operation> searchOperationList(SearchVo searchVo);

    void deletOperation(Operation operation);

    void addOperation(List<Operation> operationList);
}
