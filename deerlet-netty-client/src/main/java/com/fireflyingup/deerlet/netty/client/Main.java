package com.fireflyingup.deerlet.netty.client;


public class Main {

    public static void main(String[] args) throws Exception {
        DeerletNettyClient deerletNettyClient = new DeerletNettyClient().setIp("127.0.0.1").setPort(6666);
        deerletNettyClient.start();
    }
}
