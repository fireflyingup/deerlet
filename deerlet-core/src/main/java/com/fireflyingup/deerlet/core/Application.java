package com.fireflyingup.deerlet.core;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fireflyingup.deerlet.core.transformer.InformationGenTransformer;
import com.fireflyingup.deerlet.core.transformer.TransformerManager;
import com.fireflyingup.deerlet.netty.server.DeerletNettyServer;
import com.fireflyingup.deerlet.netty.server.MyServerHandler;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author: Fire Flying
 * @create: 2023-07-25 15:46
 **/

public class Application {

    private Instrumentation inst;

    private String args;

    public static TransformerManager transformerManager;

    private String host = "127.0.0.1";

    private Integer port = 6666;

    public Application(Instrumentation inst, String args) throws Exception {
        this.inst = inst;
        this.args = args;

        try {
            transformerManager = new TransformerManager(inst);
        } catch (Throwable e) {
            e.printStackTrace();
        }

//        Args args1 = new Args();
//        if (ObjectUtils.isNotEmpty(args)) {
//            JCommander.newBuilder().addObject(args1).build().parse(args.split(" "));
//        }
        // 启动server
        new Thread(() -> {
            DeerletNettyServer deerletNettyServer = new DeerletNettyServer()
                    .setIp("127.0.0.1")
                    .setPort(6666).addHandler(new MyServerHandler());
            try {
                deerletNettyServer.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        System.out.println(1);
    }

    public boolean start() {
        return true;
    }

    public Instrumentation getInst() {
        return inst;
    }

    public String getArgs() {
        return args;
    }
}

