package com.keboda.eomsback.authorityReview.mapper;

import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PermissionMapper {

    Permission selectByKey(Permission permission);

    void insertSelective(Permission permission);

    void deletePositionRecords(AuthorityRecordsVo vo);

    List<AuthorityRecordsVo> searchRecordsList(@Param("year") Integer year, @Param("code") String code);

    void updateSelective(Permission permission);
}