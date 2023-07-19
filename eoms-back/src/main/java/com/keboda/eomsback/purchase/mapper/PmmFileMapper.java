package com.keboda.eomsback.purchase.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmmFile;

public interface PmmFileMapper {

    int insertSelective(PmmFile record);

    int updateByPrimaryKeySelective(PmmFile record);

    Page<PmmFile> searchPmmListPageHelper(SearchVo searchVo);
}