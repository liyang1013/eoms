package com.keboda.eomsback.authorityReview.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Permission;
import com.keboda.eomsback.entity.SearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PermissionMapper {


    Permission selectByKey(Permission permission);

    void insertSelective(Permission permission);

    void deleteRecords(AuthorityRecordsVo vo);

    List<AuthorityRecordsVo> searchRecordsList(@Param("year") Integer year, @Param("code") String code);
}