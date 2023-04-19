package com.keboda.eomsback.system.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.Operation;

public interface OperationMapper {
    int deleteByPrimaryKey(String code);
    int insertSelective(Operation record);
    int updateByPrimaryKeySelective(Operation record);

    Page<Operation> searchOperationList(SearchVo searchVo);
}