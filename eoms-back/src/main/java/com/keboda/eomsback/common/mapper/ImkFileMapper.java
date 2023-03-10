package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.ImkFile;
import com.keboda.eomsback.common.pojo.ImkFileKey;

public interface ImkFileMapper {
    int deleteByPrimaryKey(ImkFileKey key);

    int insert(ImkFile record);

    int insertSelective(ImkFile record);

    ImkFile selectByPrimaryKey(ImkFileKey key);

    int updateByPrimaryKeySelective(ImkFile record);

    int updateByPrimaryKey(ImkFile record);
}