package com.keboda.eomsback.purchase.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.mapper.PmmFileMapper;
import com.keboda.eomsback.purchase.mapper.PmnFileMapper;
import com.keboda.eomsback.purchase.pojo.PmmFile;
import com.keboda.eomsback.purchase.pojo.PmnFile;
import com.keboda.eomsback.purchase.service.IPmmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmmServiceImpl implements IPmmService {

    @Resource
    private PmmFileMapper pmmFileMapper;
    @Resource
    private PmnFileMapper pmnFileMapper;

    @Override
    public Page<PmmFile> searchPmmListPageHelper(SearchVo searchVo) {
        return pmmFileMapper.searchPmmListPageHelper(searchVo);
    }

    @Override
    public List<PmnFile> searchPmnList(SearchVo searchVo) {
        return pmnFileMapper.searchPmnList(searchVo);
    }

    @Override
    @Transactional
    public void closeOut(List<PmmFile> pmmFileList) {
        for (PmmFile pmmFile : pmmFileList) {
            pmmFileMapper.closeOut(pmmFile.getCentre(),pmmFile.getPmm01());
            pmnFileMapper.closeOut(pmmFile.getCentre(),pmmFile.getPmm01());
        }
    }
}
