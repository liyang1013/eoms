package com.keboda.eomsback.base.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.base.pojo.ImaFile;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.common.pojo.ImgFile;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface ImaFileMapper {

    List<ImgFile> imgArr(@Param("ima01") String ima01, @Param("centre") String centre);

    Page<ImaFile> searchImaList(SearchVo searchVo);

    void alterStockParameter(@Param("centre") String centre, @Param("ima01") String ima01, @Param("ima27") BigDecimal  ima27, @Param("imaud09") BigDecimal imaud09, @Param("ima271") BigDecimal ima271);
}
