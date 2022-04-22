package org.apache.commons.logging.impl;

import javax.servlet.*;
import org.apache.commons.logging.*;

public class ServletContextCleaner implements ServletContextListener
{
    private static final Class[] RELEASE_SIGNATURE;
    static Class class$java$lang$ClassLoader;
    
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final Object[] array = { contextClassLoader };
        Class<?> loadClass;
        for (ClassLoader parent = contextClassLoader; parent != null; parent = loadClass.getClassLoader().getParent()) {
            loadClass = parent.loadClass("org.apache.commons.logging.LogFactory");
            loadClass.getMethod("release", (Class<?>[])ServletContextCleaner.RELEASE_SIGNATURE).invoke(null, array);
        }
        LogFactory.release(contextClassLoader);
    }
    
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        RELEASE_SIGNATURE = new Class[] { (ServletContextCleaner.class$java$lang$ClassLoader == null) ? (ServletContextCleaner.class$java$lang$ClassLoader = class$("java.lang.ClassLoader")) : ServletContextCleaner.class$java$lang$ClassLoader };
    }
}
