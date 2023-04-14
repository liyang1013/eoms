package com.keboda.eomsback.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.mapper.OperationMapper;
import com.keboda.eomsback.system.pojo.Operation;
import com.keboda.eomsback.system.service.IOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@DS("slave")
public class OperationServiceImpl implements IOperationService {

    @Resource
    private OperationMapper operationMapper;

    @Override
    public Page<Operation> searchOperationList(SearchVo searchVo) {
        return operationMapper.searchOperationList(searchVo);
    }

    @Override
    @Transactional
    public void deletOperation(Operation operation) {
        operationMapper.deleteByPrimaryKey(operation.getCode());
    }
}
