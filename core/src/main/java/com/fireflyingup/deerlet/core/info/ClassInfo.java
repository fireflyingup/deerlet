package com.fireflyingup.deerlet.core.info;

import java.util.Arrays;

public class ClassInfo {

    private String className;

    private ClassLoader classLoader;

    private int access;

    private String superName;

    private String[] interfaces;

    @Override
    public String toString() {
        return "ClassInfo{" +
                "className='" + className + '\'' +
                ", classLoader=" + classLoader +
                ", access=" + access +
                ", superName='" + superName + '\'' +
                ", interfaces=" + Arrays.toString(interfaces) +
                '}';
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }
}
