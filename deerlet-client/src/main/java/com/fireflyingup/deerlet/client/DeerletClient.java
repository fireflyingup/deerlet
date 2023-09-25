package com.fireflyingup.deerlet.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fireflyingup.deerlet.common.PrintLog;
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

import java.util.Map;

public class DeerletClient {

    private String host = "127.0.0.1";

    private Integer port = 6666;

    public void run(Map<String, Object> map) throws Exception {

        if (ObjectUtils.isNotEmpty(map.get("host"))) {
            this.setHost(map.get("host").toString());
        }

        if (ObjectUtils.isNotEmpty(map.get("port"))) {
            this.setPort(Integer.valueOf(map.get("port").toString()));
        }

        LineReader deerlet = LineReaderBuilder.builder()
                .appName("deerlet")
                .history(new DefaultHistory())
                .build();

        CommunicationHandler communicationHandler = new CommunicationHandler(deerlet);

        new Thread(() -> {
            DeerletNettyClient deerletNettyClient = new DeerletNettyClient()
                    .setIp(this.host)
                    .setPort(this.port)
                    .addHandler(communicationHandler);
            try {
                deerletNettyClient.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        while (true) {
            String s = deerlet.readLine("[deerlet]$ ");
            System.out.println(s);
            communicationHandler.send(s);
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
