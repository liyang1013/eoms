package com.keboda.eomsback.system.mapper;

import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;

public interface ZyFileMapper {
    AuthorityRecordsVo selectPermissionByCode(String permissioncode);
}
