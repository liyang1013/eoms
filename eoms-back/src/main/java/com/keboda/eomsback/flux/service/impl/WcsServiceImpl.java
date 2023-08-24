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
    @Resource
    private IRcsService iRcsService;

    @Override
    public Page<TaskTime> taskTimePageHelper(TaskTimeVo vo) {
        Page<TaskTime> page = wcsMapper.taskTimePageHelper(vo);
        if (page != null && page.getResult().size() > 0) {
            List<String> list = new ArrayList<>();
            for (TaskTime taskTime : page.getResult()) {
                list.add(taskTime.getTaskid());
            }
            List<TaskTime> rcstask = iRcsService.taskTimeByTaskid(list);

            for (TaskTime wcstask : page.getResult()) {

                rcstask.stream().filter(item -> item.getTaskid().equals(wcstask.getTaskid())).forEach(item -> {
                    wcstask.setTasktime(item.getTasktime());
                    wcstask.setStartdate(item.getStartdate());
                    wcstask.setEnddate(item.getEnddate());
                });
            }
        }
        return page;
    }
}
