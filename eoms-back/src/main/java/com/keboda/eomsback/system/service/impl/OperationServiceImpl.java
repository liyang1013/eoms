package com.keboda.eomsback.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.mapper.GazFileMapper;
import com.keboda.eomsback.system.mapper.OperationMapper;
import com.keboda.eomsback.system.pojo.GazFile;
import com.keboda.eomsback.system.pojo.Operation;
import com.keboda.eomsback.system.service.IGazService;
import com.keboda.eomsback.system.service.IOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("slave")
public class OperationServiceImpl implements IOperationService {

    @Resource
    private OperationMapper operationMapper;

    @Override
    public Page<Operation> searchOperationListPageHelper(SearchVo searchVo) {
        return operationMapper.searchOperationListPageHelper(searchVo);
    }

    @Override
    @Transactional
    public void deletOperation(Operation operation) {
        operationMapper.deleteByPrimaryKey(operation.getCode());
    }

    @Override
    @Transactional
    public void addOperation(List<Operation> operationList) {
        for (Operation operation : operationList) {
            Operation op = operationMapper.selectByKey(operation.getCode());
            if(op != null) throw new RuntimeException("作业编码:"+op.getCode()+"已存在");
            operationMapper.insertSelective(operation);
        }
    }
}
