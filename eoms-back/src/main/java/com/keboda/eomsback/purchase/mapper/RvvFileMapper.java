package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.RvvFile;
import com.keboda.eomsback.purchase.pojo.RvvFileKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RvvFileMapper {

    List<RvvFile> selectByRvb(@Param("rvb01") String rvb01, @Param("rvb02") Integer rvb02, @Param("centre") String centre,@Param("rvv03") Integer rvv03);

    void updateQty(@Param("centre") String centre,@Param("rvv01") String rvv01,@Param("rvv02") Integer rvv02, @Param("rvv17") BigDecimal rvv17);
}