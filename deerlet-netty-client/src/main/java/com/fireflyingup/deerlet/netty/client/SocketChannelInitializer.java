package com.fireflyingup.deerlet.netty.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    public List<ChannelHandlerAdapter> handlerAdapters;

    public SocketChannelInitializer(List<ChannelHandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 1024 * 24, Unpooled.copiedBuffer(Constants.SPLIT_CHAR.getBytes())));
//        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new IdleStateHandler(5, 5, 10));
        if (ObjectUtils.isNotEmpty(handlerAdapters)) {
            for (ChannelHandlerAdapter handlerAdapter : handlerAdapters) {
                pipeline.addLast(handlerAdapter);
            }
        }
    }
}
