package com.mojang.realmsclient;

import java.io.*;

public class RealmsVersion
{
    private static String version;
    
    public static String getVersion() {
        if (RealmsVersion.version != null) {
            return RealmsVersion.version;
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(RealmsVersion.class.getResourceAsStream("/version")));
        RealmsVersion.version = bufferedReader.readLine();
        bufferedReader.close();
        final String version = RealmsVersion.version;
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        return version;
    }
}
