package com.fireflyingup.deerlet.agent;

import com.fireflyingup.deerlet.agent.loader.DeerletAgentClassLoader;
import com.fireflyingup.deerlet.common.PrintLog;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;

/**
 * @author: Fire Flying
 * @create: 2023-07-21 15:27
 **/

public class Agent {

    private static final String CORE_CLASS_NAME = "com.fireflyingup.deerlet.core.Application";
    private static final String CORE_METHOD_NAME = "start";
    private static final String GET_INSTANCE = "getInstance";

    public static void premain(String args, Instrumentation inst) {
        main(args, inst, false);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst, true);
    }

    public static void main(String args, Instrumentation inst, boolean isAttach) {
        try {
            String parentPath = getParentPath();
            File file = new File(parentPath, "core.jar");
            DeerletAgentClassLoader deerletAgentClassLoader = new DeerletAgentClassLoader(new URL[]{file.toURI().toURL()});

            Class<?> aClass = deerletAgentClassLoader.loadClass(CORE_CLASS_NAME);
            Object application = aClass.getConstructor(Instrumentation.class, String.class).newInstance(inst, args);
            boolean attach = (Boolean) aClass.getMethod(CORE_METHOD_NAME).invoke(application);
            if (attach) {
                PrintLog.info("attach success");
            } else {
                PrintLog.info("attach failed");
            }
        } catch (Throwable e) {
            PrintLog.error("error: ", e);
        }
    }

    private static String getParentPath() {
        String path = Agent.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(path);
        return file.getParent();
    }

}
