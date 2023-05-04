package com.keboda.eomsback.system.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.ZxFile;

public interface ZxFileMapper {

    Page<ZxFile> searchZxListPageHelper(SearchVo searchVo);

    ZxFile selectByKey(ZxFile zxFile);
}