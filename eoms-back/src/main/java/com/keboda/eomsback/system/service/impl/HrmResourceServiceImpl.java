package com.keboda.eomsback.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.keboda.eomsback.system.mapper.HrmResourceMapper;
import com.keboda.eomsback.system.pojo.HrmResource;
import com.keboda.eomsback.system.service.IHrmResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("oa")
public class HrmResourceServiceImpl implements IHrmResourceService {

    @Resource
    private HrmResourceMapper hrmResourceMapper;

}
