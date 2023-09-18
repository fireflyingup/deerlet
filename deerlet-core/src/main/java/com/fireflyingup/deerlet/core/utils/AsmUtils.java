package com.fireflyingup.deerlet.core.utils;

import com.fireflyingup.deerlet.core.Application;
import com.fireflyingup.deerlet.core.constants.Self;
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

    /**
     * 判断是不是自己加载器加载的类
     * @param clazz
     * @return
     */
    public static boolean isSelf(Class<?> clazz) {
        if (clazz != null) {
            ClassLoader classLoader = clazz.getClassLoader();
            if (classLoader != null && Self.selfClassLoader == classLoader) {
                return true;
            }
            String name = clazz.getName();
            if (name.startsWith(Self.classNamePrefix)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Application.class.getName());
    }

}
