package com.keboda.eomsback.stock.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.mapper.TcWiaFileMapper;
import com.keboda.eomsback.stock.pojo.TcWiaFile;
import com.keboda.eomsback.stock.service.IBarCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BarCodeServiceImpl implements IBarCodeService {
    @Resource
    private TcWiaFileMapper tcWiaFileMapper;

    @Override
    public Page<TcWiaFile> searchStockGapPageHelper(SearchVo searchVo) {
        System.out.println(searchVo);
        return tcWiaFileMapper.searchStockGapPageHelper(searchVo);
    }
}
