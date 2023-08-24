package com.keboda.eomsback.flux.service;

import com.keboda.eomsback.flux.pojo.TaskTime;
import java.util.List;

public interface IRcsService {
    List<TaskTime> taskTimeByTaskid(List<String> taskid);
}
