package com.keboda.eomsback.common.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.common.pojo.SfbFile;
import com.keboda.eomsback.common.pojo.vo.SfbSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SfbFileMapper {
    List<SfbFile> sfb01Arr(@Param("queryStr") String queryStr, @Param("centre") String centre);

    SfbFile selectDateByCode(@Param("sfb01") String sfb01, @Param("centre") String centre);

    Page<SfbFile> sfbArr(SfbSearchVo searchVo);
}
