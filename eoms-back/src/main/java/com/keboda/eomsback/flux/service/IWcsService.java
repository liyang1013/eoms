package com.keboda.eomsback.flux.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.TaskTimeVo;
import com.keboda.eomsback.flux.pojo.TaskTime;

public interface IWcsService {
    Page<TaskTime> taskTimePageHelper(TaskTimeVo vo);
}
