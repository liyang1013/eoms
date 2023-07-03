package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.PmcFileMapper;
import com.keboda.eomsback.purchase.mapper.PmyFileMapper;
import com.keboda.eomsback.purchase.pojo.PmcFile;
import com.keboda.eomsback.purchase.pojo.PmyFile;
import com.keboda.eomsback.purchase.service.IPmcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PmcServiceImpl implements IPmcService {

    @Resource
    private PmcFileMapper pmcFileMapper;

    @Override
    public Page<PmcFile> searchPmcListPageHelper(SearchVo searchVo) {
        return pmcFileMapper.searchPmcListPageHelper(searchVo);
    }
}
