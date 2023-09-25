package com.fireflyingup.deerlet.client;

import com.fireflyingup.deerlet.netty.client.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.jline.reader.LineReader;

public class CommunicationHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;

    private LineReader deerlet;

    public CommunicationHandler(LineReader deerlet) {
        this.deerlet = deerlet;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取服务端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到服务端" + ctx.channel().remoteAddress() + "发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    public void send(Object object) {
        ctx.writeAndFlush(Unpooled.copiedBuffer(object.toString() + Constants.SPLIT_CHAR, CharsetUtil.UTF_8));
    }
}
