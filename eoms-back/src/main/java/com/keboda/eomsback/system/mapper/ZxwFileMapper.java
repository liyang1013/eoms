package com.keboda.eomsback.system.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.pojo.Authority;
import com.keboda.eomsback.system.pojo.Department;
import com.keboda.eomsback.system.pojo.ZxwFile;
import com.keboda.eomsback.system.pojo.ZxwFileKey;

import java.util.List;

public interface ZxwFileMapper {

    int insertSelective(ZxwFile record);

    int updateByPrimaryKeySelective(ZxwFile record);

    Page<ZxwFile> searchZxwListPageHelper(SearchVo searchVo);

    List<Authority> authorityReview(String power);

    List<Department> searchDepartmentByWorkCode(String workcode);
}