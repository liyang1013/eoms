package com.keboda.eomsback.purchase.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmiFile;
import com.keboda.eomsback.purchase.pojo.PmjFile;

import java.util.List;

public interface IPmiService {
    Page<PmiFile> searchPmiListPageHelper(SearchVo searchVo);

    List<PmjFile> searchPmjList(SearchVo searchVo);
}
