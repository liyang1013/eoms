package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.QcfFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QcfFileMapper {

    List<QcfFile> selectDateByCode(@Param("sfb01") String sfb01, @Param("centre") String centre);
}
