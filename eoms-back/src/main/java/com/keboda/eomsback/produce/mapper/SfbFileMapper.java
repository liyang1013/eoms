package com.keboda.eomsback.produce.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.base.pojo.SearchVo;
import com.keboda.eomsback.produce.pojo.SfbFile;
import com.keboda.eomsback.produce.pojo.vo.SfbSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SfbFileMapper {
    List<SfbFile> sfb01Arr(@Param("queryStr") String queryStr, @Param("centre") String centre);

    SfbFile selectDateByCode(@Param("sfb01") String sfb01, @Param("centre") String centre);

    Page<SfbFile> searchSfbList(SearchVo searchVo);

    void updateDate(@Param("sfb") SfbFile sfb, @Param("centre") String centre, @Param("ddate") Date ddate,@Param("flag") Boolean flag);
}
