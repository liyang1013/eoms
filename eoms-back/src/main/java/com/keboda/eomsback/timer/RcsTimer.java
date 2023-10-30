package com.keboda.eomsback.timer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.keboda.eomsback.plc.enums.SiemensPLC;
import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.utils.ReadCarUtils;
import com.keboda.eomsback.utils.SocketUtils;
import com.keboda.eomsback.utils.WeChatPlusUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class RcsTimer {
    private static final String sender = "JX2302304|JX2001001|JX1907006";

    /**
     * 检查RCS可用性，发送提醒
     */
    @Scheduled(cron = "${timer.checkRcsStatus.corn}")
    private void checkRCSStatus() {

        boolean mysql = SocketUtils.isReachable("172.17.200.154", 3306);
        boolean back = SocketUtils.isReachable("172.17.200.154", 8090);
        boolean front = SocketUtils.isReachable("172.17.200.154", 8081);

        if (!(mysql && back && front)) {
            StringBuilder sb = new StringBuilder();
            sb.append("未检测到RCS（172.17.200.154）服务运行: ");
            sb.append("mysql(3306): ").append(mysql ? " 运行中" : " 未运行").append("\n");
            sb.append("back(8090): ").append(back ? " 运行中" : " 未运行").append("\n");
            sb.append("front(8081): ").append(front ? " 运行中" : " 未运行");

            WeChatPlusUtils.sendMessage(sender, sb.toString());

            sb.setLength(0);
            for (SiemensPLC plc : SiemensPLC.values()) {
                if (plc.name().contains("Car")) {//小车

                    Byte id = ReadCarUtils.getSxcId(plc.getSiemensS7Net());
                    sb.append("Car").append(id).append("(").append(plc.getSiemensS7Net().getIpAddress()).append(")").append(": ").append(id != -1 ? "在线" : "离线").append("\n");
                }
            }
            WeChatPlusUtils.sendMessage(sender, sb.toString());
        }
    }
}
