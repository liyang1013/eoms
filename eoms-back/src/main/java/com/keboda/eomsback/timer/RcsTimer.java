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

        boolean mysql = SocketUtils.isReachable("172.17.200.154", 3306);
        boolean back = SocketUtils.isReachable("172.17.200.154", 8090);
        boolean front = SocketUtils.isReachable("172.17.200.154", 8081);
        StringBuilder sb  = new StringBuilder();

        if (!(mysql && back && front)) {
            sb.append("未检测到RCS（172.17.200.154）服务运行: ");
            sb.append("mysql(3306): ").append(mysql ? " 已开启" : " 未开启").append("\n");
            sb.append("back(8090): ").append(back ? " 已开启" : " 未开启").append("\n");
            sb.append("front(8081): ").append(front ? " 已开启" : " 未开启");

            WeChatPlusUtils.sendMessage(sender, sb.toString());
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
