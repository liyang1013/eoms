package com.keboda.eomsback.stock.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.stock.pojo.InaFile;
import com.keboda.eomsback.stock.pojo.InbFile;

import java.util.List;

public interface IInaService {
    Page<InaFile> searchInaListPageHelper(SearchVo searchVo);

    void alterGem(InaFile inaFile);

    List<InbFile> searchInbList(SearchVo searchVo);

    void alterAzf(InbFile inbFile);

    void alterPja(InaFile inaFile);

    void alterPjb(InbFile inbFile);
}
