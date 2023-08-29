package com.fireflyingup.deerlet.core.utils;

import org.objectweb.asm.Opcodes;

public class AsmUtils {

    /**
     * 是否抽象属性
     */
    public static boolean isAbstract(int access) {
        return (Opcodes.ACC_ABSTRACT & access) == Opcodes.ACC_ABSTRACT;
    }

    /**
     * 是否接口属性
     */
    public static boolean isInterface(int access) {
        return (Opcodes.ACC_INTERFACE & access) == Opcodes.ACC_INTERFACE;
    }

}
