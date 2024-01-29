package com.keboda.eomsback.system.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.mapper.ZxFileMapper;
import com.keboda.eomsback.system.pojo.ZxFile;
import com.keboda.eomsback.system.service.IZxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZxServiceImpl implements IZxService {

    @Resource
    private ZxFileMapper zxFileMapper;

    @Override
    public Page<ZxFile> searchListPageHelper(SearchVo searchVo) {
        return zxFileMapper.searchListPageHelper(searchVo);
    }
}
