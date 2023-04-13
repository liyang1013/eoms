package com.keboda.eomsback.stock.mapper;

import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.ImdFile;

import java.util.List;

public interface ImdFileMapper {

    List<ImdFile> searchImdList(SearchVo searchVo);
}