package com.keboda.eomsback.system.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.ZxwFile;

public interface IZxwService {
    Page<ZxwFile> searchZxwListPageHelper(SearchVo searchVo);

    void authorityReview();
}
