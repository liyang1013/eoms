package com.keboda.eomsback.system.service.impl;

import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.system.mapper.ZyFileMapper;
import com.keboda.eomsback.system.service.IZyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZyServiceimpl implements IZyService {

    @Resource
    private ZyFileMapper zyFileMapper;

    @Override
    public AuthorityRecordsVo selectPermissionByCode(String permissioncode) {
        return zyFileMapper.selectPermissionByCode(permissioncode);
    }
}
