package com.keboda.eomsback.produce.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.mapper.SfuFileMapper;
import com.keboda.eomsback.produce.mapper.SfvFileMapper;
import com.keboda.eomsback.produce.pojo.SfuFile;
import com.keboda.eomsback.produce.pojo.SfvFile;
import com.keboda.eomsback.produce.service.ISfuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SfuServiceImpl implements ISfuService {

    @Resource
    private SfuFileMapper sfuFileMapper;
    @Resource
    private SfvFileMapper sfvFileMapper;

    @Override
    public Page<SfuFile> searchSfuListPageHelper(SearchVo searchVo) {
        return sfuFileMapper.searchSfuListPageHelper(searchVo);
    }

    @Override
    public List<SfvFile> searchSfvList(SearchVo searchVo) {
        return sfvFileMapper.searchSfvList(searchVo);
    }
}
