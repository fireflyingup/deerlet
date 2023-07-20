package com.fireflyingup.deerlet.common;

/**
 * @author: Fire Flying
 * @create: 2023-07-20 17:03
 **/

public class PrintLog {

    private static LEVEL level = LEVEL.INFO;
    private static boolean enableColor;


    private static final int RED = 31;
    private static final int GREEN = 32;
    private static final int YELLOW = 33;

    private static final String RESET = "\033[0m";
    private static final String INFO_PREFIX = "[INFO] ";
    private static final String DEBUG_PREFIX = "[DEBUG] ";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String WARN_PREFIX = "[WARN] ";

    private static final String INFO_COLOR_PREFIX = "[" + colorStr("INFO", GREEN) + "] ";
    private static final String DEBUG_COLOR_PREFIX = "[" + colorStr("DEBUG", GREEN) + "] ";
    private static final String ERROR_COLOR_PREFIX = "[" + colorStr("ERROR", RED) + "] ";
    private static final String WARN_COLOR_PREFIX = "[" + colorStr("WARN", YELLOW) + "] ";

    static {
        if (System.console() != null) {
            enableColor = true;
            // windows dos, do not support color
            if (OSUtils.isWindows()) {
                enableColor = false;
            }
        }
        // cygwin and mingw support color
        if (OSUtils.isCygwinOrMinGW()) {
            enableColor = true;
        }
    }

    public enum LEVEL {
        INFO, DEBUG, ERROR, WARN
    }

    public static void setLevel(LEVEL l) {
        if (l == LEVEL.DEBUG) {
            level = LEVEL.DEBUG;
            System.out.println("log level is debug");
        } else {
            level = LEVEL.INFO;
            System.out.println("log level is info");
        }
    }

    public static void info(String str) {
        if (enableColor) {
            System.out.println(INFO_COLOR_PREFIX + str);
        } else {
            System.out.println(INFO_PREFIX + str);
        }
    }

    public static void warn(String str) {
        if (enableColor) {
            System.out.println(WARN_COLOR_PREFIX + str);
        } else {
            System.out.println(WARN_PREFIX + str);
        }
    }

    public static void error(String str) {
        if (enableColor) {
            System.out.println(ERROR_COLOR_PREFIX + str);
        } else {
            System.out.println(ERROR_PREFIX + str);
        }
    }

    public static void error(String str, Throwable throwable) {
        if (enableColor) {
            System.out.println(ERROR_COLOR_PREFIX + str);
            throwable.printStackTrace(System.out);
        } else {
            System.out.println(ERROR_PREFIX + str);
            throwable.printStackTrace(System.out);
        }
    }

    public static void debug(String str) {
        if (level == LEVEL.DEBUG) {
            if (enableColor) {
                System.out.println(DEBUG_COLOR_PREFIX + str);
            } else {
                System.out.println(DEBUG_PREFIX + str);
            }
        }
    }

    private static String colorStr(String msg, int colorCode) {
        return "\033[" + colorCode + "m" + msg + RESET;
    }
}
