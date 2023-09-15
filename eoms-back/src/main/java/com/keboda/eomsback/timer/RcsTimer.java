package com.keboda.eomsback.timer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.keboda.eomsback.utils.SocketUtils;
import com.keboda.eomsback.utils.WeChatPlusUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class RcsTimer {

    private static final String URL = "http://172.17.200.154:8090/rest/robots/states";

    private static final String authorization = "mrbase64 mrrest:YWRtaW4mYWRtaW4=";

    private static final String sender = "JX2302304|JX2001001|JX1907006";


    /**
     * 检查RCS可用性，发送提醒
     */
    @Scheduled(cron = "${timer.checkRcsStatus.corn}")
    private void checkRCSStatus() {

        boolean isReachable = SocketUtils.isReachable("172.17.200.154", 8090);
        if (!isReachable) {
            WeChatPlusUtils.sendMessage(sender, "无法访问 RCS端口 172.17.200.154:8090");
            return;
        }

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
        } catch (Exception e) {
            WeChatPlusUtils.sendMessage(sender, "RCS接口 172.17.200.154:8090/rest/robots/states 异常: " + e.getMessage());
            return;
        }
        if (res.getStatus() != 200) {
            WeChatPlusUtils.sendMessage(sender, "RCS接口 172.17.200.154:8090/rest/robots/states 异常: " + res.getStatus());
        }
    }
}
