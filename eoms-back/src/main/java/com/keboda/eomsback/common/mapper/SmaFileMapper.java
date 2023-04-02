package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.SmaFile;
import org.apache.ibatis.annotations.Param;

public interface SmaFileMapper {

    SmaFile select(@Param("centre") String centre);
}
