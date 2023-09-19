package com.fireflyingup.deerlet.netty.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Main {

    public static void main(String[] args) throws Exception {
        DeerletNettyServer deerletNettyServer = new DeerletNettyServer()
                .setIp("127.0.0.1").
                setPort(6666).
                setChannelInitializer(new SocketChannelInitializer());
        deerletNettyServer.start();
    }

}
