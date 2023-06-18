package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.PmnFile;
import com.keboda.eomsback.purchase.pojo.PmnFileKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface PmnFileMapper {

    int insertSelective(PmnFile record);

    int updateByPrimaryKeySelective(PmnFile record);

    void updatePmn50(@Param("centre") String centre, @Param("pmn01") String pmn01, @Param("pmn02") Integer pmn02, @Param("pmn50") BigDecimal pmn50);

    void InvalidDocumentDelete(@Param("centre") String centre);
}