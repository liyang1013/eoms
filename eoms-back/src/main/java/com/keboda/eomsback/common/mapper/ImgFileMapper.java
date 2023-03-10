package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.ImgFile;
import com.keboda.eomsback.common.pojo.ImgFileKey;

public interface ImgFileMapper {
    int deleteByPrimaryKey(ImgFileKey key);

    int insert(ImgFile record);

    int insertSelective(ImgFile record);

    ImgFile selectByPrimaryKey(ImgFileKey key);

    int updateByPrimaryKeySelective(ImgFile record);

    int updateByPrimaryKey(ImgFile record);
}