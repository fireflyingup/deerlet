package com.fireflyingup.deerlet.core;

import com.fireflyingup.deerlet.common.PrintLog;
import com.fireflyingup.deerlet.core.transformer.CustomClassFileTransformer;
import com.sun.corba.se.impl.orbutil.ObjectUtility;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

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
        inst.addTransformer(new CustomClassFileTransformer(), true);
        Class<?>[] allLoadedClasses = inst.getAllLoadedClasses();
        try {
            if (ObjectUtils.isNotEmpty(allLoadedClasses)) {
                for (Class<?> clazz : allLoadedClasses) {
                    if(inst.isModifiableClass(clazz)) {
                        inst.retransformClasses(clazz);
                    }
                }
            }
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Instrumentation getInst() {
        return inst;
    }

    public String getArgs() {
        return args;
    }
}

