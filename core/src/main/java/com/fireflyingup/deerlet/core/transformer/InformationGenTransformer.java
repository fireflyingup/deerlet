package com.fireflyingup.deerlet.core.transformer;

import org.objectweb.asm.*;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Fire Flying
 * @create: 2023-07-27 18:04
 **/

public class InformationGenTransformer extends AbstractTransformer {

    public static Map<Class<?>, Object> relationMap = new ConcurrentHashMap<>();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className != null && !className.equals("demo/MathGame")) {
            return classfileBuffer;
        }
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        InformationGenVisitor informationGenVisitor = new InformationGenVisitor(Opcodes.ASM9, classWriter);
        classReader.accept(informationGenVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    public static class InformationGenVisitor extends ClassVisitor {

        public InformationGenVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            System.out.println(name);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }

}
