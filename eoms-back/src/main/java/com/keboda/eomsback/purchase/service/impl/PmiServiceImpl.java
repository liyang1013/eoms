package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.PmiFileMapper;
import com.keboda.eomsback.purchase.mapper.PmjFileMapper;
import com.keboda.eomsback.purchase.pojo.PmiFile;
import com.keboda.eomsback.purchase.pojo.PmjFile;
import com.keboda.eomsback.purchase.service.IPmiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmiServiceImpl implements IPmiService {

    @Resource
    private PmiFileMapper pmiFileMapper;
    @Resource
    private PmjFileMapper pmjFileMapper;


    @Override
    public Page<PmiFile> searchPmiListPageHelper(SearchVo searchVo) {
        return pmiFileMapper.searchPmiListPageHelper(searchVo);
    }

    @Override
    public List<PmjFile> searchPmjList(SearchVo searchVo) {
        return pmjFileMapper.searchPmjList(searchVo);
    }
}
