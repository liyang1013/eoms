package com.keboda.eomsback.stock.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.EWStockGap;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TlfFileMapper {
    void alterQty( @Param("centre")String centre,  @Param("tlf026")String tlf026,  @Param("tlf027")Integer tlf027,  @Param("tlf036")String tlf036,  @Param("tlf037")Integer tlf037, @Param("tlf10")BigDecimal tlf10,@Param("tlf18")BigDecimal tlf18);

    void updateGem(@Param("centre") String centre, @Param("code") String code, @Param("tlf19") String tlf19);

    void alterAzf(@Param("centre") String centre, @Param("code") String code, @Param("tlf037") Integer tlf037,@Param("tlf14") String tlf14);

    void alterPja(@Param("centre") String centre, @Param("code") String code, @Param("tlf20") String tlf20);

    void alterPjb(@Param("centre") String centre, @Param("code") String code, @Param("tlf037") Integer tlf037, @Param("tlf41") String tlf41);

    Page<EWStockGap> searchEWStockGapPageHelper(SearchVo searchVo);

    void alterDate(@Param("centre") String centre, @Param("tlf026") String tlf026, @Param("tlf036") String tlf036, @Param("ddate") Date ddate, @Param("tlf907") String tlf907);
}
