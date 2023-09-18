package com.fireflyingup.deerlet.core;

import com.fireflyingup.deerlet.core.transformer.InformationGenTransformer;
import com.fireflyingup.deerlet.core.transformer.TransformerManager;
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

    public Application(Instrumentation inst, String args) {
        this.inst = inst;
        this.args = args;
        try {
            transformerManager = new TransformerManager(inst);
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
