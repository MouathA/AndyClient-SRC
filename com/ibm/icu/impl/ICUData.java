package com.ibm.icu.impl;

import java.net.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class ICUData
{
    public static boolean exists(final String s) {
        URL resource;
        if (System.getSecurityManager() != null) {
            resource = AccessController.doPrivileged((PrivilegedAction<URL>)new PrivilegedAction(s) {
                final String val$resourceName;
                
                public URL run() {
                    return ICUData.class.getResource(this.val$resourceName);
                }
                
                public Object run() {
                    return this.run();
                }
            });
        }
        else {
            resource = ICUData.class.getResource(s);
        }
        return resource != null;
    }
    
    private static InputStream getStream(final Class clazz, final String s, final boolean b) {
        InputStream resourceAsStream;
        if (System.getSecurityManager() != null) {
            resourceAsStream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction(clazz, s) {
                final Class val$root;
                final String val$resourceName;
                
                public InputStream run() {
                    return this.val$root.getResourceAsStream(this.val$resourceName);
                }
                
                public Object run() {
                    return this.run();
                }
            });
        }
        else {
            resourceAsStream = clazz.getResourceAsStream(s);
        }
        if (resourceAsStream == null && b) {
            throw new MissingResourceException("could not locate data " + s, clazz.getPackage().getName(), s);
        }
        return resourceAsStream;
    }
    
    private static InputStream getStream(final ClassLoader classLoader, final String s, final boolean b) {
        InputStream resourceAsStream;
        if (System.getSecurityManager() != null) {
            resourceAsStream = AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction(classLoader, s) {
                final ClassLoader val$loader;
                final String val$resourceName;
                
                public InputStream run() {
                    return this.val$loader.getResourceAsStream(this.val$resourceName);
                }
                
                public Object run() {
                    return this.run();
                }
            });
        }
        else {
            resourceAsStream = classLoader.getResourceAsStream(s);
        }
        if (resourceAsStream == null && b) {
            throw new MissingResourceException("could not locate data", classLoader.toString(), s);
        }
        return resourceAsStream;
    }
    
    public static InputStream getStream(final ClassLoader classLoader, final String s) {
        return getStream(classLoader, s, false);
    }
    
    public static InputStream getRequiredStream(final ClassLoader classLoader, final String s) {
        return getStream(classLoader, s, true);
    }
    
    public static InputStream getStream(final String s) {
        return getStream(ICUData.class, s, false);
    }
    
    public static InputStream getRequiredStream(final String s) {
        return getStream(ICUData.class, s, true);
    }
    
    public static InputStream getStream(final Class clazz, final String s) {
        return getStream(clazz, s, false);
    }
    
    public static InputStream getRequiredStream(final Class clazz, final String s) {
        return getStream(clazz, s, true);
    }
}
