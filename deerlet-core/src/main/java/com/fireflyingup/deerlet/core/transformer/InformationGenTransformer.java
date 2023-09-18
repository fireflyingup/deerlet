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
//        if (AsmUtils.isSelf(classBeingRedefined) || !"demo/MathGame".equals(className)) {
//            return classfileBuffer;
//        }
        if (!"com/fireflyingup/deerlet/demo/Main".equals(className)) {
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
            if (name.equals("action")) {
                return new InformationGenMethod(Opcodes.ASM9, methodVisitor);
            } else {
                return methodVisitor;
            }
        }

        public static class InformationGenMethod extends MethodVisitor{
            public InformationGenMethod(int api, MethodVisitor methodVisitor) {
                super(api, methodVisitor);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                System.out.println("into method " + name);
                Label label0 = new Label();
                Label label1 = new Label();
                Label label2 = new Label();
                mv.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
                Label label3 = new Label();
                mv.visitTryCatchBlock(label0, label1, label3, null);
                Label label4 = new Label();
                mv.visitTryCatchBlock(label2, label4, label3, null);

                mv.visitLabel(label0);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

                Label label5 = new Label();
                mv.visitLabel(label5);
                mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

                mv.visitLabel(label1);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitInsn(Opcodes.ICONST_3);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

                Label label6 = new Label();
                mv.visitLabel(label6);
                Label nextLabel = new Label();
                mv.visitJumpInsn(Opcodes.GOTO, nextLabel);

                mv.visitLabel(label2);
                mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
                mv.visitVarInsn(Opcodes.ASTORE, 0);

                Label label8 = new Label();
                mv.visitLabel(label8);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitInsn(Opcodes.ICONST_2);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                mv.visitLabel(label4);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitInsn(Opcodes.ICONST_3);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                Label label9 = new Label();
                mv.visitLabel(label9);
                mv.visitJumpInsn(Opcodes.GOTO, nextLabel);
                mv.visitLabel(label3);
                mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
                mv.visitVarInsn(Opcodes.ASTORE, 1);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitInsn(Opcodes.ICONST_3);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                Label label10 = new Label();
                mv.visitLabel(label10);
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                mv.visitInsn(Opcodes.ATHROW);

                mv.visitLabel(nextLabel);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            }
        }
    }



}
