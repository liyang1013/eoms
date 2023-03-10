package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.SfpFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SfpFileMapper {
    List<SfpFile> selectDateByCode(@Param("sfb01") String sfb01, @Param("centre") String centre);
}
