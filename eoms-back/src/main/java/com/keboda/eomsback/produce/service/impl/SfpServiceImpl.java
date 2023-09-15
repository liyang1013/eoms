package com.keboda.eomsback.produce.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.mapper.SfpFileMapper;
import com.keboda.eomsback.produce.mapper.SfsFileMapper;
import com.keboda.eomsback.produce.pojo.SfpFile;
import com.keboda.eomsback.produce.pojo.SfsFile;
import com.keboda.eomsback.produce.service.ISfpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SfpServiceImpl implements ISfpService {

    @Resource
    private SfpFileMapper sfpFileMapper;
    @Resource
    private SfsFileMapper sfsFileMapper;

    @Override
    public Page<SfpFile> searchSfpListPageHelper(SearchVo searchVo) {
        return sfpFileMapper.searchSfpListPageHelper(searchVo);
    }

    @Override
    public List<SfsFile> searchSfseList(SearchVo searchVo) {
        return sfsFileMapper.searchSfseList(searchVo);
    }

    @Override
    @Transactional
    public void alterSfpConf(SfpFile sfpFile) {
        sfpFileMapper.alterSfpConf(sfpFile);
    }
}
