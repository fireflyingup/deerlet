package com.fireflyingup.deerlet.core.constants;

import com.fireflyingup.deerlet.core.Application;

public class Self {

    /**
     * 类加载器
     */
    public static ClassLoader selfClassLoader = Application.class.getClassLoader();

    /**
     * 类前缀
     */
    public static String classNamePrefix = "com.fireflyingup.deerlet";

    public static void main(String[] args) {
        try {
            System.out.println(1);
        } catch (Exception e) {
            System.out.println(2);
        } finally {
            System.out.println(3);
        }
    }

}
