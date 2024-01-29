package com.keboda.eomsback.authorityReview.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.authorityReview.mapper.PermissionMapper;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.pojo.Permission;
import com.keboda.eomsback.authorityReview.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("myself")
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Permission selectByKey(Permission permission) {
        return permissionMapper.selectByKey(permission);
    }

    @Override
    public void insertSelective(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    @Transactional
    public void deleteRecords(List<AuthorityRecordsVo> authorityRecordsVoList) {
        for (AuthorityRecordsVo vo : authorityRecordsVoList) {
            permissionMapper.deleteRecords(vo);
        }
    }

    @Override
    public List<AuthorityRecordsVo> searchRecordsList(Integer year,String code) {
        return permissionMapper.searchRecordsList(year,code);
    }

    @Override
    public void updateSelective(Permission permission) {
        permissionMapper.updateSelective(permission);
    }
}
