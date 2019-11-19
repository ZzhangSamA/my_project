package com.yanzhang.utils;

import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

public class IpUtilTest {

    @Test
    public void test1() {
        String ipInfo = null;
        try {
            ipInfo = IpUtil.getLocalMac("127.0.0.1");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(ipInfo);

        try {
            ipInfo = IpUtil.getLocalMac("192.168.5.16");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(ipInfo);
    }
}
