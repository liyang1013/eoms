package com.keboda.eomsback.system.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.GenFile;

public interface IGenService {
    GenFile login(GenFile genFile);

    Page<GenFile> searchGenList(SearchVo searchVo);
}
