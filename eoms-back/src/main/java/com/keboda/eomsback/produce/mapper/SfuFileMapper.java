package com.keboda.eomsback.produce.mapper;

import com.keboda.eomsback.produce.pojo.SfuFile;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SfuFileMapper {

    List<SfuFile> selectDateByCode(@Param("sfb01") String sfb01, @Param("centre") String centre);

    void updateDate(@Param("sfu") SfuFile sfu, @Param("centre") String centre, @Param("ddate") Date ddate,@Param("flag")Boolean flag);
}
