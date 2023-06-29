package com.keboda.eomsback.timer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.keboda.eomsback.flux.pojo.RobotsStatusFile;
import com.keboda.eomsback.flux.service.IRobotsStatusService;
import com.keboda.eomsback.utils.WeChatPlusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.*;

@Configuration
@EnableScheduling
public class RCSRobotsTimer {

    private static final String URL = "http://172.17.200.154:8090/rest/robots/states";

    private static final String authorization = "mrbase64 mrrest:YWRtaW4mYWRtaW4=";

    private static final String sender = "JX2302304|JX2001001|JX1907006";

    @Autowired
    private IRobotsStatusService iRobotsStatusService;

    /**
     * 定时写入RCS小车状态
     */
    @Scheduled(cron = "${timer.rcsRobotsTimer.corn}")
    private void writersRobotsStatus(){

        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>() {
            private static final long serialVersionUID = 1L;
            {
                ArrayList<Integer> id = new ArrayList<>();
                put("params", id);
            }
        };
        HttpResponse res = null;
        try {
             res = HttpRequest.post(URL)
                    .header("authorization", authorization)
                    .timeout(2000)
                    .body(JSONUtil.toJsonStr(sortedMap))
                    .execute();
        }catch (Exception e){
            WeChatPlusUtils.sendMessage(sender,"RCS小车状态接口异常："+e.getMessage());
        }

        if(res.getStatus() == 200){
            JSONObject jsonObject = new JSONObject(res.body());
            JSONArray data = new JSONArray(jsonObject.get("data"));
            for(Object obj : data){

                JSONObject car = new JSONObject(obj);
                JSONObject postion = new JSONObject(car.get("position"));
                JSONObject target = new JSONObject(car.get("target"));

                RobotsStatusFile robotsStatusFile = new RobotsStatusFile();
                robotsStatusFile.setId((Integer)car.get("id"));
                robotsStatusFile.setIp((String)car.get("ip"));
                robotsStatusFile.setAgvtype((String)car.get("agvType"));
                robotsStatusFile.setXPosition((BigDecimal)(postion.get("x")));
                robotsStatusFile.setYPosition((BigDecimal)postion.get("y"));
                robotsStatusFile.setXTarget((BigDecimal)target.get("x"));
                robotsStatusFile.setYTarget((BigDecimal)target.get("y"));
                robotsStatusFile.setEnable((Boolean) car.get("enable"));
                robotsStatusFile.setFloorid((Integer)postion.get("floorId"));
                robotsStatusFile.setMapid((Integer)car.get("mapId"));
                robotsStatusFile.setTaskState((String) car.get("taskState"));
                robotsStatusFile.setAgvState((String) car.get("agvState"));
                robotsStatusFile.setLoaded((Boolean) car.get("loaded"));
                robotsStatusFile.setBattery((BigDecimal) car.get("battery"));
                robotsStatusFile.setOnline((Boolean) car.get("online"));
                robotsStatusFile.setUpdatetime(new Date());

                RobotsStatusFile robotsStatus = iRobotsStatusService.selectByKey(robotsStatusFile.getId());

                if(robotsStatus == null){
                    iRobotsStatusService.insertByKeySelective(robotsStatusFile);
                }else{
                    iRobotsStatusService.updateByKeySelective(robotsStatusFile);
                }
            }
        }else{
            WeChatPlusUtils.sendMessage(sender,"RCS小车状态接口异常状态:"+res.getStatus());
        }
    }
}