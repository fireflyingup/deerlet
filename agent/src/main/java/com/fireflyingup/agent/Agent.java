package com.fireflyingup.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author: Fire Flying
 * @create: 2023-07-21 15:27
 **/

public class Agent {

    public static void premain(String args, Instrumentation inst) {
        System.out.println("premain");
    }

    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("agentmain");
    }


    public static void main() {

    }


}
