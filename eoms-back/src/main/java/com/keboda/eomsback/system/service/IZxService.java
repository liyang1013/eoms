package com.keboda.eomsback.system.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.ZxFile;

public interface IZxService {
    Page<ZxFile> searchZxList(SearchVo searchVo);

    ZxFile login(ZxFile zxFile);
}
