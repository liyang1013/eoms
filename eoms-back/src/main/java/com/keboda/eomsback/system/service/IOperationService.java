package com.keboda.eomsback.system.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.Operation;

public interface IOperationService {
    Page<Operation> searchOperationList(SearchVo searchVo);

    void deletOperation(Operation operation);
}
