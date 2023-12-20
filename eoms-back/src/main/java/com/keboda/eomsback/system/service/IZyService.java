package com.keboda.eomsback.system.service;

import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;

public interface IZyService {
    AuthorityRecordsVo selectPermissionByCode(String permissioncode);
}
