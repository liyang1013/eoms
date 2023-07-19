package com.keboda.eomsback.purchase.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmmFile;
import com.keboda.eomsback.purchase.pojo.PmnFile;

import java.util.List;

public interface IPmmService {
    Page<PmmFile> searchPmmListPageHelper(SearchVo searchVo);

    List<PmnFile> searchPmnList(SearchVo searchVo);
}

