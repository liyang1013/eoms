package com.keboda.eomsback.stock.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.EWStockGap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ITlfService {
    Page<EWStockGap> searchEWStockGapPageHelper(SearchVo searchVo);

    void alterDate(String centre, String tlf026, String tlf036, Date ddate, String tlf907);

    void alterQty(String centre, String tlf026, Integer tlf027, String tlf036, Integer tlf037, BigDecimal tlf10, BigDecimal tlf18);
}
