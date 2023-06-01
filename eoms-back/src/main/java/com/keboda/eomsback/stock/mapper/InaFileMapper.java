package com.keboda.eomsback.stock.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.InaFile;

public interface InaFileMapper {
    int insertSelective(InaFile record);

    int updateByPrimaryKeySelective(InaFile record);

    Page<InaFile> searchInaListPageHelper(SearchVo searchVo);

    void alterGem(InaFile inaFile);
}