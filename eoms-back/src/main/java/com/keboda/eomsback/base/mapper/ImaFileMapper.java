package com.keboda.eomsback.base.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.base.pojo.ImaFile;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.common.pojo.ImgFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ImaFileMapper {

    List<ImgFile> imgArr(@Param("ima01") String ima01, @Param("centre") String centre);

    Page<ImaFile> searchImaList(SearchVo searchVo);
}
