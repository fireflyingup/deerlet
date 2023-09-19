package com.fireflyingup.deerlet.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fireflyingup.deerlet.netty.client.DeerletNettyClient;
import com.fireflyingup.deerlet.netty.client.SocketChannelInitializer;
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
                .setChannelInitializer(new SocketChannelInitializer());
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
