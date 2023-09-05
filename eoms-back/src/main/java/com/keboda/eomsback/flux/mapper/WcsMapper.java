package com.keboda.eomsback.flux.mapper;

import com.github.pagehelper.Page;
import com.keboda.eomsback.entity.TaskTimeVo;
import com.keboda.eomsback.flux.pojo.TaskTime;
import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.pojo.Hoist;

public interface WcsMapper {
    Page<TaskTime> taskTimePageHelper(TaskTimeVo vo);

    void writerCarinfo(Car car);

    void readHoistinfo(Hoist hoist);
}
