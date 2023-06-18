package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.PmkFile;
import org.apache.ibatis.annotations.Param;

public interface PmkFileMapper {

    int insertSelective(PmkFile record);

    int updateByPrimaryKeySelective(PmkFile record);

    void InvalidDocumentDelete(@Param("centre") String centre);
}