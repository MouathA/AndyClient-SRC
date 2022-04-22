package org.lwjgl;

import java.io.*;
import org.lwjgl.input.*;
import java.lang.reflect.*;
import java.security.*;
import java.net.*;

public final class Sys
{
    private static final String JNI_LIBRARY_NAME = "lwjgl";
    private static final String VERSION = "2.9.4";
    private static final String POSTFIX64BIT = "64";
    private static final SysImplementation implementation;
    private static final boolean is64Bit;
    
    private static void doLoadLibrary(final String s) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction(s) {
            final String val$lib_name;
            
            public Object run() {
                final String property = System.getProperty("org.lwjgl.librarypath");
                if (property != null) {
                    System.load(property + File.separator + LWJGLUtil.mapLibraryName(this.val$lib_name));
                }
                else {
                    System.loadLibrary(this.val$lib_name);
                }
                return null;
            }
        });
    }
    
    private static void loadLibrary(final String s) {
        final String property = System.getProperty("os.arch");
        if (LWJGLUtil.getPlatform() != 2 && ("amd64".equals(property) || "x86_64".equals(property))) {
            doLoadLibrary(s + "64");
            return;
        }
        doLoadLibrary(s);
    }
    
    private static SysImplementation createImplementation() {
        switch (LWJGLUtil.getPlatform()) {
            case 1: {
                return new LinuxSysImplementation();
            }
            case 3: {
                return new WindowsSysImplementation();
            }
            case 2: {
                return new MacOSXSysImplementation();
            }
            default: {
                throw new IllegalStateException("Unsupported platform");
            }
        }
    }
    
    private Sys() {
    }
    
    public static String getVersion() {
        return "2.9.4";
    }
    
    public static void initialize() {
    }
    
    public static boolean is64Bit() {
        return Sys.is64Bit;
    }
    
    public static long getTimerResolution() {
        return Sys.implementation.getTimerResolution();
    }
    
    public static long getTime() {
        return Sys.implementation.getTime() & Long.MAX_VALUE;
    }
    
    public static void alert(String s, String s2) {
        final boolean grabbed = Mouse.isGrabbed();
        if (grabbed) {
            Mouse.setGrabbed(false);
        }
        if (s == null) {
            s = "";
        }
        if (s2 == null) {
            s2 = "";
        }
        Sys.implementation.alert(s, s2);
        if (grabbed) {
            Mouse.setGrabbed(true);
        }
    }
    
    public static boolean openURL(final String s) {
        final Class<?> forName = Class.forName("javax.jnlp.ServiceManager");
        return (boolean)AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction(Class.forName("javax.jnlp.BasicService")) {
            final Class val$basicServiceClass;
            
            public Method run() throws Exception {
                return this.val$basicServiceClass.getMethod("showDocument", URL.class);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        }).invoke(AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction(forName) {
            final Class val$serviceManagerClass;
            
            public Method run() throws Exception {
                return this.val$serviceManagerClass.getMethod("lookup", String.class);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        }).invoke(forName, "javax.jnlp.BasicService"), new URL(s));
    }
    
    public static String getClipboard() {
        return Sys.implementation.getClipboard();
    }
    
    static {
        implementation = createImplementation();
        loadLibrary("lwjgl");
        is64Bit = (Sys.implementation.getPointerSize() == 8);
        final int jniVersion = Sys.implementation.getJNIVersion();
        final int requiredJNIVersion = Sys.implementation.getRequiredJNIVersion();
        if (jniVersion != requiredJNIVersion) {
            throw new LinkageError("Version mismatch: jar version is '" + requiredJNIVersion + "', native library version is '" + jniVersion + "'");
        }
        Sys.implementation.setDebug(LWJGLUtil.DEBUG);
    }
}
