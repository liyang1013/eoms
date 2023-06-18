package com.keboda.eomsback.purchase.mapper;

import com.keboda.eomsback.purchase.pojo.PmjFile;
import com.keboda.eomsback.purchase.pojo.PmjFileKey;

public interface PmjFileMapper {

    int insertSelective(PmjFile record);

    int updateByPrimaryKeySelective(PmjFile record);
}