package com.fireflyingup.deerlet.agent;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.security.CodeSource;

/**
 * @author: Fire Flying
 * @create: 2023-07-21 15:27
 **/

public class Agent {

    public static void premain(String args, Instrumentation inst) {
        main(args, inst, false);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst, true);
    }

    public static void main(String args, Instrumentation inst, boolean isAttach) {
        String path = Agent.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(path);
        System.out.println(file.getParent());
    }

}
