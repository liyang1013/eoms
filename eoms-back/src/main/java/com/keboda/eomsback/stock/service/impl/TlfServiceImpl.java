package com.keboda.eomsback.stock.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.mapper.TlfFileMapper;
import com.keboda.eomsback.stock.pojo.EWStockGap;
import com.keboda.eomsback.stock.service.ITlfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TlfServiceImpl implements ITlfService {

    @Resource
    private TlfFileMapper tlfFileMapper;

    @Override
    public Page<EWStockGap> searchEWStockGapPageHelper(SearchVo searchVo) {
        return tlfFileMapper.searchEWStockGapPageHelper(searchVo);
    }

    /**
     * 批量更新异动日期
     *
     * @param centre 中心
     * @param tlf026 单据号
     * @param tlf036 单据号
     * @param ddate  日期
     * @param tlf907 异动类型
     */
    @Override
    @Transactional
    public void alterDate(String centre, String tlf026, String tlf036, Date ddate, String tlf907) {
        tlfFileMapper.alterDate(centre, tlf026, tlf036, ddate, tlf907);
    }

    /**
     * 按行更新tlf异动数量
     *
     * @param centre 中心
     * @param tlf026 单据号
     * @param tlf027 单据项次
     * @param tlf036 单据号
     * @param tlf037 单据项次
     * @param tlf10  异动数量
     * @param tlf18  异动更新之后数量
     */
    @Override
    @Transactional
    public void alterQty(String centre, String tlf026, Integer tlf027, String tlf036, Integer tlf037, BigDecimal tlf10, BigDecimal tlf18) {
        tlfFileMapper.alterQty(centre, tlf026, tlf027, tlf036, tlf037, tlf10, tlf18);
    }
}
