package com.keboda.eomsback.sale.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.sale.mapper.TcWdiFileMapper;
import com.keboda.eomsback.sale.mapper.TcWdjFileMapper;
import com.keboda.eomsback.sale.pojo.TcWdiFile;
import com.keboda.eomsback.sale.service.IWdiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WdiServiceImpl implements IWdiService {

    @Resource
    private TcWdiFileMapper tcWdiFileMapper;

    @Resource
    private TcWdjFileMapper tcWdjFileMapper;


    @Override
    public Page<TcWdiFile> searchWdiListPageHelper(SearchVo searchVo) {
        return tcWdiFileMapper.searchWdiListPageHelper(searchVo);
    }
}
