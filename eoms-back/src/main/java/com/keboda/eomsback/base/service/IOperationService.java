package com.keboda.eomsback.base.service;

import com.keboda.eomsback.base.pojo.Operation;

import java.io.FileNotFoundException;
import java.util.List;

public interface IOperationService {
    List<Operation> searchOperation(Operation operation) throws FileNotFoundException;
}
