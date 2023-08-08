package com.keboda.eomsback.purchase.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmmFile;
import org.apache.ibatis.annotations.Param;

public interface PmmFileMapper {

    int insertSelective(PmmFile record);

    int updateByPrimaryKeySelective(PmmFile record);

    Page<PmmFile> searchPmmListPageHelper(SearchVo searchVo);

    void InvalidDocumentDelete(@Param("centre") String centre);

    void closeOut(@Param("centre") String centre, @Param("pmm01") String pmm01);
}