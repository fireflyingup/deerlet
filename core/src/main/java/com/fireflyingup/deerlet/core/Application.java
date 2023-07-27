package com.fireflyingup.deerlet.core;

import com.fireflyingup.deerlet.common.PrintLog;

import java.lang.instrument.Instrumentation;

/**
*
* @author: Fire Flying
*
* @create: 2023-07-25 15:46
**/

public class Application {

    private Instrumentation inst;

    private String args;

    public Application(Instrumentation inst, String args) {
        this.inst = inst;
        this.args = args;
    }

    public boolean start() {
        PrintLog.info("into start");
        return true;
    }

    public Instrumentation getInst() {
        return inst;
    }

    public String getArgs() {
        return args;
    }
}

