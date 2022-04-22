package org.apache.logging.log4j.core.helpers;

import org.apache.logging.log4j.*;
import java.net.*;
import java.io.*;
import java.security.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.util.*;

public final class Loader
{
    private static boolean ignoreTCL;
    private static final Logger LOGGER;
    private static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
    
    public static ClassLoader getClassLoader() {
        return getClassLoader(Loader.class, null);
    }
    
    public static ClassLoader getClassLoader(final Class clazz, final Class clazz2) {
        final ClassLoader tcl = getTCL();
        final ClassLoader classLoader = (clazz == null) ? null : clazz.getClassLoader();
        final ClassLoader classLoader2 = (clazz2 == null) ? null : clazz2.getClass().getClassLoader();
        if (isChild(tcl, classLoader)) {
            return isChild(tcl, classLoader2) ? tcl : classLoader2;
        }
        return isChild(classLoader, classLoader2) ? classLoader : classLoader2;
    }
    
    public static URL getResource(final String s, final ClassLoader classLoader) {
        final ClassLoader tcl = getTCL();
        if (tcl != null) {
            Loader.LOGGER.trace("Trying to find [" + s + "] using context classloader " + tcl + '.');
            final URL resource = tcl.getResource(s);
            if (resource != null) {
                return resource;
            }
        }
        final ClassLoader classLoader2 = Loader.class.getClassLoader();
        if (classLoader2 != null) {
            Loader.LOGGER.trace("Trying to find [" + s + "] using " + classLoader2 + " class loader.");
            final URL resource2 = classLoader2.getResource(s);
            if (resource2 != null) {
                return resource2;
            }
        }
        if (classLoader != null) {
            Loader.LOGGER.trace("Trying to find [" + s + "] using " + classLoader + " class loader.");
            final URL resource3 = classLoader.getResource(s);
            if (resource3 != null) {
                return resource3;
            }
        }
        Loader.LOGGER.trace("Trying to find [" + s + "] using ClassLoader.getSystemResource().");
        return ClassLoader.getSystemResource(s);
    }
    
    public static InputStream getResourceAsStream(final String s, final ClassLoader classLoader) {
        final ClassLoader tcl = getTCL();
        if (tcl != null) {
            Loader.LOGGER.trace("Trying to find [" + s + "] using context classloader " + tcl + '.');
            final InputStream resourceAsStream = tcl.getResourceAsStream(s);
            if (resourceAsStream != null) {
                return resourceAsStream;
            }
        }
        final ClassLoader classLoader2 = Loader.class.getClassLoader();
        if (classLoader2 != null) {
            Loader.LOGGER.trace("Trying to find [" + s + "] using " + classLoader2 + " class loader.");
            final InputStream resourceAsStream2 = classLoader2.getResourceAsStream(s);
            if (resourceAsStream2 != null) {
                return resourceAsStream2;
            }
        }
        if (classLoader != null) {
            Loader.LOGGER.trace("Trying to find [" + s + "] using " + classLoader + " class loader.");
            final InputStream resourceAsStream3 = classLoader.getResourceAsStream(s);
            if (resourceAsStream3 != null) {
                return resourceAsStream3;
            }
        }
        Loader.LOGGER.trace("Trying to find [" + s + "] using ClassLoader.getSystemResource().");
        return ClassLoader.getSystemResourceAsStream(s);
    }
    
    private static ClassLoader getTCL() {
        ClassLoader contextClassLoader;
        if (System.getSecurityManager() == null) {
            contextClassLoader = Thread.currentThread().getContextClassLoader();
        }
        else {
            contextClassLoader = AccessController.doPrivileged((PrivilegedAction<ClassLoader>)new PrivilegedAction() {
                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
                
                @Override
                public Object run() {
                    return this.run();
                }
            });
        }
        return contextClassLoader;
    }
    
    private static boolean isChild(final ClassLoader classLoader, final ClassLoader classLoader2) {
        if (classLoader != null && classLoader2 != null) {
            ClassLoader classLoader3;
            for (classLoader3 = classLoader.getParent(); classLoader3 != null && classLoader3 != classLoader2; classLoader3 = classLoader3.getParent()) {}
            return classLoader3 != null;
        }
        return classLoader != null;
    }
    
    public static Class loadClass(final String s) throws ClassNotFoundException {
        if (Loader.ignoreTCL) {
            return Class.forName(s);
        }
        return getTCL().loadClass(s);
    }
    
    private Loader() {
    }
    
    static {
        Loader.ignoreTCL = false;
        LOGGER = StatusLogger.getLogger();
        final String stringProperty = PropertiesUtil.getProperties().getStringProperty("log4j.ignoreTCL", null);
        if (stringProperty != null) {
            Loader.ignoreTCL = OptionConverter.toBoolean(stringProperty, true);
        }
    }
}
