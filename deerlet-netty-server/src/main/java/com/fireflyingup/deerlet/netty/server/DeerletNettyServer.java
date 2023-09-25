package com.fireflyingup.deerlet.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class DeerletNettyServer {

    //创建两个线程组 boosGroup、workerGroup
    private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    private String ip;

    private Integer port;

    private List<ChannelHandlerAdapter> handlerAdapters;


    public void start() throws Exception {
        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            ChannelInitializer<SocketChannel> channelInitializer = new SocketChannelInitializer(handlerAdapters);
            //设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(channelInitializer);
            //给workerGroup的EventLoop对应的管道设置处理器
            //绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(ip, port).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public String getIp() {
        return ip;
    }

    public DeerletNettyServer setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public DeerletNettyServer setPort(Integer port) {
        this.port = port;
        return this;
    }

    public DeerletNettyServer addHandler(ChannelHandlerAdapter adapter) {
        if (ObjectUtils.isEmpty(handlerAdapters)) {
            this.handlerAdapters = new ArrayList<>();
        }
        this.handlerAdapters.add(adapter);
        return this;
    }
}
