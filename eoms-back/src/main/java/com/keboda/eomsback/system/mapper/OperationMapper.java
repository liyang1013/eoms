package com.keboda.eomsback.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.Operation;

public interface OperationMapper {
    int deleteByPrimaryKey(String code);
    int insertSelective(Operation record);
    int updateByPrimaryKeySelective(Operation record);
    Page<Operation> searchOperationListPageHelper(SearchVo searchVo);

    Operation selectByKey(String code);
}