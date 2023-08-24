package com.keboda.eomsback.flux.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.TaskTimeVo;
import com.keboda.eomsback.flux.pojo.TaskTime;

public interface WcsMapper {
    Page<TaskTime> taskTimePageHelper(TaskTimeVo vo);
}
