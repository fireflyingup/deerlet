package com.fireflyingup.deerlet.boot.utils;

import com.fireflyingup.deerlet.common.PrintLog;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Fire Flying
 * @create: 2023-07-20 17:46
 **/

public class JvmEnvUtils {

    public static String getJps() {
        String property = System.getProperty("java.home");
        if (ObjectUtils.isEmpty(property)) {
            property = System.getProperty("JAVA_HOME");
            if (property.isEmpty()) {
                PrintLog.error("could not find any jdk.");
                return null;
            }
        }
        String[] jps = { "bin/jps", "bin/jps.exe", "../bin/jps", "../bin/jps.exe" };

        for (String jp : jps) {
            File file = new File(property, jp);
            if (file.exists()) {
                PrintLog.warn("get jsp path : " + file.getAbsolutePath());
                return file.getAbsolutePath();
            }
        }

        PrintLog.error("could not find any jps command .");
        return null;
    }

    public static String getJava() {

    }

    public static String getJavaHome() {
        String property = System.getProperty("java.home");
        if (ObjectUtils.isEmpty(property)) {
            property = System.getProperty("JAVA_HOME");
            if (property.isEmpty()) {
                PrintLog.error("could not find any jdk.");
                return null;
            }
        }
        return property;
    }
}
