package com.keboda.eomsback.stock.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.stock.pojo.ImaFile;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.ImgFile;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface ImaFileMapper {

    List<ImgFile> imgArr(@Param("ima01") String ima01, @Param("centre") String centre);

    Page<ImaFile> searchImaListPageHelper(SearchVo searchVo);

    void alterStockParameter(@Param("centre") String centre, @Param("ima01") String ima01, @Param("ima27") BigDecimal  ima27, @Param("imaud09") BigDecimal imaud09, @Param("ima271") BigDecimal ima271);

    ImaFile selectByKey(@Param("centre") String centre, @Param("ima01") String ima01);

    int updateByPrimaryKeySelective(ImaFile record);
}
