package com.fireflyingup.deerlet.core.transformer;

import com.fireflyingup.deerlet.core.info.ClassInfo;
import com.fireflyingup.deerlet.core.utils.AsmUtils;
import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
import org.apache.commons.lang3.ObjectUtils;
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

    public static Map<Class<?>, ClassInfo> relationMap = new ConcurrentHashMap<>();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 排除自身的类
        if (AsmUtils.isSelf(classBeingRedefined) || !"demo/MathGame".equals(className)) {
            return classfileBuffer;
        }
        ClassInfo classInfo = relationMap.get(classBeingRedefined);
        if (ObjectUtils.isEmpty(classInfo)) {
            classInfo = new ClassInfo();
            relationMap.put(classBeingRedefined, classInfo);
        }
        classInfo.setClassLoader(loader);
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        InformationGenVisitor informationGenVisitor = new InformationGenVisitor(Opcodes.ASM9, classWriter, classInfo);
        classReader.accept(informationGenVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    public static class InformationGenVisitor extends ClassVisitor {

        private ClassInfo classInfo;

        public InformationGenVisitor(int api, ClassVisitor classVisitor, ClassInfo classInfo) {
            super(api, classVisitor);
            this.classInfo = classInfo;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            classInfo.setClassName(name);
            classInfo.setSuperName(superName);
            classInfo.setAccess(access);
            classInfo.setInterfaces(interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new InformationGenMethod(Opcodes.ASM9, methodVisitor);
        }

        public static class InformationGenMethod extends MethodVisitor{
            public InformationGenMethod(int api, MethodVisitor methodVisitor) {
                super(api, methodVisitor);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                System.out.println(name);
                if (name.equals("primeFactors")) {
                    Label startTry = new Label();
                    Label endTry = new Label();
                    Label catchLabel = new Label();

                    // Insert try block
                    mv.visitTryCatchBlock(startTry, endTry, catchLabel, "java/lang/Exception");
                    mv.visitLabel(startTry);

                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

                    // Insert end of try block and start of catch block
                    mv.visitLabel(endTry);
                    mv.visitJumpInsn(Opcodes.GOTO, catchLabel);
                    mv.visitLabel(catchLabel);
                    mv.visitVarInsn(Opcodes.ASTORE, 100); // Store the exception in a local variable

                    // Handle exception (print or log)
                    mv.visitVarInsn(Opcodes.ALOAD, 100); // Load the exception from the local variable
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }



}
