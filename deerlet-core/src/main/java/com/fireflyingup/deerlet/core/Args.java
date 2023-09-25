package com.fireflyingup.deerlet.core;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = {"-host"}, description = "host of target agent")
    private String host = "127.0.0.1";

    @Parameter(names = {"-port"}, description = "port of target agent")
    private Integer port = 6666;

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
