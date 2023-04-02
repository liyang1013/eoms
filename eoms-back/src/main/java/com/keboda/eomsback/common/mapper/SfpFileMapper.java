package com.keboda.eomsback.common.mapper;

import com.keboda.eomsback.common.pojo.SfpFile;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SfpFileMapper {
    List<SfpFile> selectDateByCode(@Param("sfb01") String sfb01, @Param("centre") String centre);

    void updateDate(@Param("sfp") SfpFile sfp, @Param("centre") String centre, @Param("ddate") Date ddate,@Param("flag") Boolean flag);
}
