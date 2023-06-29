package com.keboda.eomsback.purchase.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmiFile;

public interface PmiFileMapper {

    int insertSelective(PmiFile record);

    int updateByPrimaryKeySelective(PmiFile record);

    Page<PmiFile> searchPmiListPageHelper(SearchVo searchVo);
}