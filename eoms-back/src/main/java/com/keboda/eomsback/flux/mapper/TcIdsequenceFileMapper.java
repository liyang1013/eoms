package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.flux.pojo.TcIdsequenceFile;
import org.apache.ibatis.annotations.Param;

public interface TcIdsequenceFileMapper {

    TcIdsequenceFile selectByKey(@Param("itype") String itype);

    void updateSequence(@Param("itype") String itype);
}
