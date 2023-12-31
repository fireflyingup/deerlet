package com.fireflyingup.deerlet.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class DeerletNettyClient {

    public static NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

    private String ip;

    private Integer port;

    private List<ChannelHandlerAdapter> handlerAdapters;

    public void start() throws Exception {
        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            SocketChannelInitializer socketChannelInitializer = new SocketChannelInitializer(handlerAdapters);
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    //使用匿名内部类初始化通道
                    .handler(socketChannelInitializer);
            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("hello world !@#$", CharsetUtil.UTF_8));
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

    public DeerletNettyClient addHandler(ChannelHandlerAdapter adapter) {
        if (ObjectUtils.isEmpty(handlerAdapters)) {
            this.handlerAdapters = new ArrayList<>();
        }
        this.handlerAdapters.add(adapter);
        return this;
    }

}
