package com.keboda.eomsback.stock.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.TcWiaFile;

public interface TcWiaFileMapper {

    int insertSelective(TcWiaFile record);

    int updateByPrimaryKeySelective(TcWiaFile record);

    Page<TcWiaFile> searchStockGapPageHelper(SearchVo searchVo);
}