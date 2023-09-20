package com.fireflyingup.deerlet.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fireflyingup.deerlet.netty.client.DeerletNettyClient;
import com.fireflyingup.deerlet.netty.client.MyClientHandler;
import com.fireflyingup.deerlet.netty.client.SocketChannelInitializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;

public class DeerletClient {

    @Parameter(names = {"-host"}, description = "host of target agent")
    private String host = "127.0.0.1";

    @Parameter(names = {"-port"}, description = "port of target agent")
    private Integer port = 6666;

    public void run(String[] args) throws Exception {

        DeerletClient deerletClient = new DeerletClient();
        JCommander.newBuilder().addObject(deerletClient).build().parse(args);

        if (ObjectUtils.isNotEmpty(deerletClient.getHost())) {
            this.setHost(deerletClient.getHost());
        }

        if (ObjectUtils.isNotEmpty(deerletClient.getPort())) {
            this.setPort(deerletClient.getPort());
        }

        DeerletNettyClient deerletNettyClient = new DeerletNettyClient()
                .setIp(deerletClient.host)
                .setPort(deerletClient.port)
                .setChannelInitializer(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(Constants.SPLIT_CHAR.getBytes())));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new IdleStateHandler(5, 5, 10));
                        pipeline.addLast(new CommunicationHandler());
                    }
                });
        deerletNettyClient.start();

        LineReader deerlet = LineReaderBuilder.builder()
                .appName("deerlet")
                .history(new DefaultHistory())
                .build();

        while (true) {
            String s = deerlet.readLine("[deerlet]$ ");
            System.out.println(s);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
