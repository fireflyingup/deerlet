package com.fireflyingup.deerlet.demo;

public class Main {

    public static void main(String[] args) throws Exception {
        while (true) {
            action();
        }
    }

    private static void action() throws Exception {
        sleep(1000);
        sleep(2000);
        sleep(3000);
    }

    private static void sleep(long time) throws Exception {
        System.out.println("sleep" + time);
        Thread.sleep(time);
    }
}
