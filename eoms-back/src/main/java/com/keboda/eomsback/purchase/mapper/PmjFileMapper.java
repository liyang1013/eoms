package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmjFile;
import com.keboda.eomsback.purchase.pojo.PmjFileKey;

import java.util.List;

public interface PmjFileMapper {

    int insertSelective(PmjFile record);

    int updateByPrimaryKeySelective(PmjFile record);

    List<PmjFile> searchPmjList(SearchVo searchVo);
}