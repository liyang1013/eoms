package com.keboda.eomsback.purchase.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import com.keboda.eomsback.purchase.pojo.RvbFile;

import java.util.List;

public interface IRvaService {
    Page<RvaFile> searchRvaListPageHelper(SearchVo searchVo);

    List<RvbFile> searchRvbList(SearchVo searchVo);

    void alterRvaConf(RvaFile rvaFile);

    void alterRvbQty(List<RvbFile> rvbFiles);

    void alterRvaDate(RvaFile rvaFile);
}
