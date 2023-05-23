package com.keboda.eomsback.utils;

import cn.hutool.http.HttpRequest;

//企业微信推送
public class WeChatPlusUtils {

    private static final String URL = "http://172.16.200.27:1013/WXNPSWebService.asmx/SendInfoAll";

    /**
     *
     * @param user 工号，’|‘分隔，@all则全员推送
     * @param message 发送的消息
     */
    public static void sendMessage(String user,String message){

        String sb = "content=" + message + "&" +
                "touser=" + user + "&" +
                "toparty=" + "&" +
                "totag=";

        HttpRequest.post(URL).timeout(2000).body(sb).execute();
    }
}
