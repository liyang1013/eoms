package com.keboda.eomsback.flux.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.flux.pojo.TaskTimeVo;
import com.keboda.eomsback.flux.pojo.RobotsTaskTime;
import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.pojo.Hoist;

public interface IWcsService {
    Page<RobotsTaskTime> taskTimePageHelper(TaskTimeVo vo);

    void writerCarinfo(Car car);

    void writerHoistinfo(Hoist hoist);
}
