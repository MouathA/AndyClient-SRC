package oshi.util;

import java.util.*;
import java.io.*;

public class ExecutingCommand
{
    public static ArrayList runNative(final String s) {
        final Process exec = Runtime.getRuntime().exec(s);
        exec.waitFor();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        final ArrayList<String> list = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }
    
    public static String getFirstAnswer(final String s) {
        final ArrayList runNative = runNative(s);
        if (runNative != null) {
            return runNative.get(0);
        }
        return null;
    }
}
