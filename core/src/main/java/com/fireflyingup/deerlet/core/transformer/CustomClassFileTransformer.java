package com.fireflyingup.deerlet.core.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author: Fire Flying
 * @create: 2023-07-27 18:04
 **/

public class CustomClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.contains("demo")) {
            System.out.println(className);
        }
        return classfileBuffer;
    }

}
