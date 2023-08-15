package com.keboda.eomsback.purchase.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.purchase.pojo.RvuFile;
import org.apache.ibatis.annotations.Param;

public interface RvuFileMapper {

    Page<RvuFile> searchRvuListPageHelper(SearchVo searchVo);

    void alterRvuDate(RvuFile rvuFile);
}