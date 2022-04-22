package com.ibm.icu.impl;

import java.util.*;
import java.security.*;
import java.io.*;

public class ICUConfig
{
    public static final String CONFIG_PROPS_FILE = "/com/ibm/icu/ICUConfig.properties";
    private static final Properties CONFIG_PROPS;
    
    public static String get(final String s) {
        return get(s, null);
    }
    
    public static String get(final String s, final String s2) {
        String s3;
        if (System.getSecurityManager() != null) {
            s3 = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s) {
                final String val$fname;
                
                public String run() {
                    return System.getProperty(this.val$fname);
                }
                
                public Object run() {
                    return this.run();
                }
            });
        }
        else {
            s3 = System.getProperty(s);
        }
        if (s3 == null) {
            s3 = ICUConfig.CONFIG_PROPS.getProperty(s, s2);
        }
        return s3;
    }
    
    static {
        CONFIG_PROPS = new Properties();
        final InputStream stream = ICUData.getStream("/com/ibm/icu/ICUConfig.properties");
        if (stream != null) {
            ICUConfig.CONFIG_PROPS.load(stream);
        }
    }
}
