package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.base.pojo.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvbFile;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RvbFileMapper {

    List<RvbFile> searchRvbList(SearchVo searchVo);

    RvbFile selectByKey(@Param("rvb01") String rvb01, @Param("rvb02") Integer rvb02,@Param("centre") String centre);

    void updateQty(@Param("centre") String centre, @Param("rvb01") String rvb01, @Param("rvb02") Integer rvb02, @Param("rvb07") BigDecimal rvb07);
}