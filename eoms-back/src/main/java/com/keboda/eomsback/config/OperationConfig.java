package com.keboda.eomsback.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.keboda.eomsback.base.pojo.Operation;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
public class OperationConfig {

    private static final List<Operation> OPERATIONS;

    static {
        String json = ResourceUtil.readUtf8Str("json/operation.json");
        OPERATIONS = JSONUtil.toList(json, Operation.class);
    }

    public static List<Operation> getOperation(){
        return OPERATIONS;
    }

    public static void addOperation(Operation operation){
        OPERATIONS.add(operation);
    }

    public static void removeOperation(Operation operation){
        OPERATIONS.remove(operation);
    }

}
