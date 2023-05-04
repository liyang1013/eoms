package com.keboda.eomsback.system.service.impl;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.SearchVo;
import com.keboda.eomsback.system.mapper.ZxFileMapper;
import com.keboda.eomsback.system.pojo.ZxFile;
import com.keboda.eomsback.system.service.IZxService;
import com.keboda.eomsback.utils.JwtTokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZxServiceImpl implements IZxService {

    @Resource
    private ZxFileMapper zxFileMapper;

    @Override
    public Page<ZxFile> searchZxListPageHelper(SearchVo searchVo) {
        return zxFileMapper.searchZxListPageHelper(searchVo);
    }

    @Override
    public ZxFile login(ZxFile zxFile) {
        if(zxFile.getZx01() == null || zxFile.getZx01().trim().isEmpty()){
            throw new RuntimeException("用户名不能为空");
        }
        if(zxFile.getZx10() == null || zxFile.getZx10().trim().isEmpty()){
            throw new RuntimeException("密码不能为空");
        }
        ZxFile login = zxFileMapper.selectByKey(zxFile);
        if(login == null) throw new RuntimeException("用户名或密码错误");
        login.setToken(JwtTokenUtils.getToken(login.getZx01(),login.getZx10()));
        return login;
    }
}
