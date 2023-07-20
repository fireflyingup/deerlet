package com.fireflyingup.deerlet.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Fire Flying
 * @create: 2023-07-20 17:00
 **/

public class CommandUtils {


    public static List<String> run(String... args) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(args);
        } catch (IOException e) {
            PrintLog.error("Run command with error", e);
            return new ArrayList<>(0);
        }

        List<String> result = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            process.waitFor();
        } catch (Exception e) {
            PrintLog.error("Read InputStream with error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    PrintLog.error("close stream ", e);
                }
            }
        }
        return result;
    }

}
