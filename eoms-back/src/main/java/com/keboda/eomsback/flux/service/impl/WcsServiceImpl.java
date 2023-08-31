package com.keboda.eomsback.flux.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.TaskTimeVo;
import com.keboda.eomsback.flux.mapper.WcsMapper;
import com.keboda.eomsback.flux.pojo.TaskTime;
import com.keboda.eomsback.flux.service.IRcsService;
import com.keboda.eomsback.flux.service.IWcsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@DS("fluxWcs")
public class WcsServiceImpl implements IWcsService {

    @Resource
    private WcsMapper wcsMapper;

    @Override
    public Page<TaskTime> taskTimePageHelper(TaskTimeVo vo) {
        return wcsMapper.taskTimePageHelper(vo);
    }
}
