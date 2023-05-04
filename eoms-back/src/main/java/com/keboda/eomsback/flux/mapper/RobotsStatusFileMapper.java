package com.keboda.eomsback.flux.mapper;

import com.keboda.eomsback.flux.pojo.RobotsStatusFile;
import org.apache.ibatis.annotations.Param;

public interface RobotsStatusFileMapper {

    RobotsStatusFile selectByKey(@Param("id") Integer id);

    void insertByKeySelective(RobotsStatusFile robotsStatusFile);

    void updateByKeySelective(RobotsStatusFile robotsStatusFile);
}
