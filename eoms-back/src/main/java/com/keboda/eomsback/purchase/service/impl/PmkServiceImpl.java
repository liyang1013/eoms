package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.PmkFileMapper;
import com.keboda.eomsback.purchase.mapper.PmlFileMapper;
import com.keboda.eomsback.purchase.pojo.PmkFile;
import com.keboda.eomsback.purchase.pojo.PmlFile;
import com.keboda.eomsback.purchase.service.IPmkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmkServiceImpl implements IPmkService {

    @Resource
    private PmkFileMapper pmkFileMapper;
    @Resource
    private PmlFileMapper pmlFileMapper;

    @Override
    public Page<PmkFile> searchPmkListPageHelper(SearchVo searchVo) {
        return pmkFileMapper.searchPmkListPageHelper(searchVo);
    }

    @Override
    public List<PmlFile> searchPmlList(SearchVo searchVo) {
        return pmlFileMapper.searchPmlList(searchVo);
    }
}
