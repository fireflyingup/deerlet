package com.fireflyingup.deerlet.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;

public class SocketChannelInitializer extends ChannelInitializer<ServerChannel> {
    @Override
    protected void initChannel(ServerChannel serverChannel) throws Exception {
        serverChannel.pipeline().addLast(new MyServerHandler());
    }
}
