package com.keboda.eomsback.sale.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.sale.pojo.TcWdiFile;

public interface IWdiService {
    Page<TcWdiFile> searchWdiListPageHelper(SearchVo searchVo);
}
