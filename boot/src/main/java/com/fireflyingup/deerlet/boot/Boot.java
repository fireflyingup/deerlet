package com.fireflyingup.deerlet.boot;


import com.fireflyingup.deerlet.boot.utils.JvmEnvUtils;
import com.fireflyingup.deerlet.common.CommandUtils;
import com.fireflyingup.deerlet.common.PrintLog;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;

/**
 *
 * 启动类
 *
 * 1、解析传入的参数
 * 2、
 *
 * @author: Fire Flying
 * @create: 2023-07-20 15:32
 **/

public class Boot {

    public static void main(String[] args) throws Exception {
        String pid = findPid();
        VirtualMachine virtualMachine = null;
        virtualMachine = VirtualMachine.attach(pid);
//        virtualMachine.loadAgent("/Users/guoying/big/myProject/java/deerlet/agent/target/arthas-agent-jar-with-dependenciess-agent-jar-with-dependencies.jar");
        virtualMachine.loadAgent("D:\\myProgram\\deerlet\\agent\\target\\arthas-agent-jar-with-dependencies.jar");
    }

    private static String findPid( ) {
        String jps = JvmEnvUtils.getJps();
        System.out.println(jps);
        List<String> run = CommandUtils.run(jps, "-l");
        if (ObjectUtils.isEmpty(run)) {
            PrintLog.error("can not find any java progress with 'jps -l' command.");
            System.exit(1);
        }
        int index = 1;
        PrintLog.info("choose a process to attack, for example: choose one then enter");
        Map<Integer, String> map = new HashMap<>();
        for (String s : run) {
            String[] split = s.split("\\s+");
            if (split.length < 1) {
                continue;
            }
            if (split.length > 1 && isJpsProcess(split[1])) {
                continue;
            }
            if (index == 1) {
                System.out.println("* " + index + " " + s);
            } else {
                System.out.println("  " + index + " " + s);
            }
            map.put(index++, split[0].trim());
        }

        String line = new Scanner(System.in).nextLine();
        String pidInfo;
        if (line.trim().isEmpty()) {
            pidInfo = map.get(1);
        } else {
            int i = new Scanner(line).nextInt();
            pidInfo = map.get(i);
            if (ObjectUtils.isEmpty(pidInfo)) {
                PrintLog.error("no such progress find ");
                System.exit(1);
            }
        }

        return pidInfo;
    }

    private static boolean isJpsProcess(String mainClassName) {
        return "sun.tools.jps.Jps".equals(mainClassName) || "jdk.jcmd/sun.tools.jps.Jps".equals(mainClassName);
    }
}
