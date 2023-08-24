package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.flux.pojo.TaskTime;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RcsMapper {
    List<TaskTime> taskTimeByTaskid(@Param("taskid") List<String> taskid);
}
