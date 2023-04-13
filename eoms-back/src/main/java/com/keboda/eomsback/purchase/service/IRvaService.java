package com.keboda.eomsback.purchase.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import com.keboda.eomsback.purchase.pojo.RvbFile;

import java.util.List;

public interface IRvaService {
    Page<RvaFile> searchRvaList(SearchVo searchVo);

    List<RvbFile> searchRvbList(SearchVo searchVo);

    void alterRva(RvaFile rvaFile);

    void alterRvb(List<RvbFile> rvbFiles);
}
