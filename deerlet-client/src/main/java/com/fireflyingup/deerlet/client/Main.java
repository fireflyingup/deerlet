package com.fireflyingup.deerlet.client;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;

public class Main {

    public static void main(String[] args) {
        LineReader deerlet = LineReaderBuilder.builder()
                .appName("deerlet")
                .history(new DefaultHistory())
                .build();

        while (true) {
            String s = deerlet.readLine("[deerlet]$ ");
            System.out.println(s);
        }
    }

}
