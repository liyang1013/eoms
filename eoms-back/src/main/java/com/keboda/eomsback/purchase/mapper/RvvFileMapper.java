package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvvFile;
import com.keboda.eomsback.purchase.pojo.RvvFileKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RvvFileMapper {

    void updateQty(@Param("centre") String centre,@Param("rvv01") String rvv01,@Param("rvv02") Integer rvv02, @Param("rvv17") BigDecimal rvv17);

    List<RvvFile> searchRvvList(SearchVo searchVo);
}