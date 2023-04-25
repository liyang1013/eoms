package com.keboda.eomsback.stock.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

public interface TlfFileMapper {

    void updateDate(@Param("tlf62") String tlf62, @Param("tlf0236") String tlf0236, @Param("centre") String centre, @Param("ddate") Date ddate);

    void updateQty( @Param("centre")String centre,  @Param("tlf026")String tlf026,  @Param("tlf027")Integer tlf027,  @Param("tlf036")String tlf036,  @Param("tlf037")Integer tlf037, @Param("tlf10")BigDecimal tlf10,@Param("tlf18")BigDecimal tlf18);
}
