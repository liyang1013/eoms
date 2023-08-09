package com.keboda.eomsback.utils;

import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketCheckUtils {

    public static boolean isReachable(String ipAddress, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ipAddress, port), 5000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
