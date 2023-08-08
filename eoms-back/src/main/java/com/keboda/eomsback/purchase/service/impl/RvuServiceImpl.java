package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.RvuFileMapper;
import com.keboda.eomsback.purchase.mapper.RvvFileMapper;
import com.keboda.eomsback.purchase.pojo.RvuFile;
import com.keboda.eomsback.purchase.pojo.RvvFile;
import com.keboda.eomsback.purchase.service.IRvuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RvuServiceImpl implements IRvuService {

    @Resource
    private RvuFileMapper rvuFileMapper;
    @Resource
    private RvvFileMapper rvvFileMapper;

    @Override
    public Page<RvuFile> searchRvuListPageHelper(SearchVo searchVo) {
        return rvuFileMapper.searchRvuListPageHelper(searchVo);
    }

    @Override
    public List<RvvFile> searchRvvList(SearchVo searchVo) {
        return rvvFileMapper.searchRvvList(searchVo);
    }
}
