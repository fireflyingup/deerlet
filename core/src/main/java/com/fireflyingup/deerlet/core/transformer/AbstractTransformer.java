package com.fireflyingup.deerlet.core.transformer;


import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;

public abstract class AbstractTransformer implements ClassFileTransformer {


    /**
     * 是否抽象属性
     */
    public boolean isAbstract(int access) {
        return (Opcodes.ACC_ABSTRACT & access) == Opcodes.ACC_ABSTRACT;
    }


}
