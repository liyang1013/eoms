package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.ImaFile;
import com.keboda.eomsback.common.pojo.ImgFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ImaFileMapper {

    List<ImaFile> imaArr(@Param("ima01") String queryStr, @Param("centre") String centre);

    List<ImgFile> imgArr(@Param("ima01") String ima01, @Param("centre") String centre);
}
