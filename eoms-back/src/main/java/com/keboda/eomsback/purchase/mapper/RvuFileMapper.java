package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.RvuFile;
import org.apache.ibatis.annotations.Param;

public interface RvuFileMapper {

    RvuFile selectByKey(@Param("rvu01") String rvv01, @Param("centre") String rvvplant);
}