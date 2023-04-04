package com.keboda.eomsback.base.service.impl;

import com.keboda.eomsback.base.pojo.Operation;
import com.keboda.eomsback.base.service.IOperationService;
import com.keboda.eomsback.config.OperationConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements IOperationService {

    @Override
    public List<Operation> searchOperation(Operation operation) {
        return OperationConfig.getOperation().stream().filter(e->{
            if(operation.getCode() != null && !operation.getCode().trim().isEmpty()){
                return e.getCode().contains(operation.getCode());
            }
            return true;
        }).filter(e -> {
            if(operation.getName() != null && !operation.getName().trim().isEmpty()){
                return e.getName().contains(operation.getName());
            }
            return true;
        }).filter(e->{
            if(operation.getTips() != null && !operation.getTips().trim().isEmpty()){
                return e.getTips().contains(operation.getTips());
            }
            return true;
        }).collect(Collectors.toList());
    }
}
