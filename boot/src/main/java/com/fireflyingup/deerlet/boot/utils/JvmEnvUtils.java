package com.fireflyingup.deerlet.boot.utils;

import com.fireflyingup.deerlet.common.PrintLog;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;

/**
 * @author: Fire Flying
 * @create: 2023-07-20 17:46
 **/

public class JvmEnvUtils {

    public static String getJps() {
        String property = getJavaHome();
        String[] jps = { "bin/jps", "bin/jps.exe", "../bin/jps", "../bin/jps.exe" };
        for (String jp : jps) {
            File file = new File(property, jp);
            if (file.exists()) {
                PrintLog.debug("get jsp path : " + file.getAbsolutePath());
                return file.getAbsolutePath();
            }
        }

        PrintLog.error("could not find any jps command .");
        return null;
    }

    public static String getJava() {
        String property = getJavaHome();
        String[] javas = { "bin/java", "bin/java.exe", "../bin/java", "../bin/java.exe" };
        for (String java : javas) {
            File file = new File(property, java);
            if (file.exists()) {
                PrintLog.debug("get jsp path : " + file.getAbsolutePath());
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    public static String getJavaHome() {
        String property = System.getenv("JAVA_HOME");
        if (ObjectUtils.isEmpty(property)) {
            property = System.getProperty("java.home");
            if (property.isEmpty()) {
                PrintLog.error("could not find any jdk.");
                return null;
            }
        }
        return property;
    }
}
