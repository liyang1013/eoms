package com.keboda.eomsback.purchase.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.PmkFile;
import com.keboda.eomsback.purchase.pojo.PmlFile;

import java.util.List;

public interface IPmkService {
    Page<PmkFile> searchPmkListPageHelper(SearchVo searchVo);

    List<PmlFile> searchPmlList(SearchVo searchVo);
}
