package com.keboda.eomsback.purchase.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.common.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvaFile;
import org.apache.ibatis.annotations.Param;

public interface RvaFileMapper {
    Page<RvaFile> searchRvaList( SearchVo searchVo);

    void alterRva(RvaFile rvaFile);
}