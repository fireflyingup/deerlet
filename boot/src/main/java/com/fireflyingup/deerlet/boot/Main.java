package com.fireflyingup.deerlet.boot;


import com.fireflyingup.deerlet.boot.utils.JvmEnvUtils;
import com.fireflyingup.deerlet.common.CommandUtils;
import com.fireflyingup.deerlet.common.PrintLog;
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

public class Main {

    public static void main(String[] args) {
        String jps = JvmEnvUtils.getJps();
        List<String> run = CommandUtils.run(jps, "-l");
        if (ObjectUtils.isEmpty(run)) {
            PrintLog.error("can not find any java progress with 'jps -l' command.");
            System.exit(1);
        }
        int index = 1;
        PrintLog.info("choose a process to attack, for example: choose 1 then enter");
        Map<Integer, String> map = new HashMap<>();
        for (String s : run) {
            if (index == 1) {
                System.out.println("* " + index + " " + s);
            } else {
                System.out.println("  " + index + " " + s);
            }
            map.put(index++, s);
        }

        String line = new Scanner(System.in).nextLine();
        if (line.trim().isEmpty()) {
            System.out.println(map.get(1));
            return;
        }
        int i = new Scanner(line).nextInt();
        String s = map.get(i);
        if (ObjectUtils.isEmpty(s)) {
            PrintLog.error("no such progress find ");
            System.exit(1);
        }

        System.out.println(s);
    }

}
