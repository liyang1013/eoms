package com.keboda.eomsback.stock.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.mapper.InaFileMapper;
import com.keboda.eomsback.stock.mapper.InbFileMapper;
import com.keboda.eomsback.stock.mapper.TlfFileMapper;
import com.keboda.eomsback.stock.pojo.InaFile;
import com.keboda.eomsback.stock.pojo.InbFile;
import com.keboda.eomsback.stock.service.IInaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InaServiceImpl implements IInaService {

    @Resource
    private InaFileMapper inaFileMapper;
    @Resource
    private TlfFileMapper tlfFileMapper;
    @Resource
    private InbFileMapper inbFileMapper;


    @Override
    public Page<InaFile> searchInaListPageHelper(SearchVo searchVo) {
        return inaFileMapper.searchInaListPageHelper(searchVo);
    }

    @Override
    @Transactional
    public void alterGem(InaFile inaFile) {
        inaFileMapper.alterGem(inaFile);
        tlfFileMapper.updateGem(inaFile.getCentre(),inaFile.getIna01(),inaFile.getIna04());
    }

    @Override
    public List<InbFile> searchInbList(SearchVo searchVo) {
        return inbFileMapper.searchInbList(searchVo);
    }
}
