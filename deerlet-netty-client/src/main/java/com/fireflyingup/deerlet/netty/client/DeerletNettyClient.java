package com.fireflyingup.deerlet.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DeerletNettyClient {

    public static NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

    private String ip;

    private Integer port;

    private ChannelInitializer<SocketChannel> channelInitializer;

    public void start() throws Exception {
        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    //使用匿名内部类初始化通道
                    .handler(channelInitializer);
            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            //对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            //关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }

    public String getIp() {
        return ip;
    }

    public DeerletNettyClient setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public DeerletNettyClient setPort(Integer port) {
        this.port = port;
        return this;
    }

    public ChannelInitializer<SocketChannel> getChannelInitializer() {
        return channelInitializer;
    }

    public DeerletNettyClient setChannelInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer = channelInitializer;
        return this;
    }
}
