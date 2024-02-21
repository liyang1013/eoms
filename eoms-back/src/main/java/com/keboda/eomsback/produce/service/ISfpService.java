package com.keboda.eomsback.produce.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.produce.pojo.SfpFile;
import com.keboda.eomsback.produce.pojo.SfsFile;

import java.util.List;

public interface ISfpService {
    Page<SfpFile> searchListPageHelper(SearchVo searchVo);

    List<SfsFile> searchSfsList(SearchVo searchVo);

    void alterSfpConf(SfpFile sfpFile);

    void alterSfsGfe(SfsFile sfsFile);
}

