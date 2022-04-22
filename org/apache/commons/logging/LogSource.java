package org.apache.commons.logging;

import java.util.*;
import java.lang.reflect.*;
import org.apache.commons.logging.impl.*;

public class LogSource
{
    protected static Hashtable logs;
    protected static boolean log4jIsAvailable;
    protected static boolean jdk14IsAvailable;
    protected static Constructor logImplctor;
    
    private LogSource() {
    }
    
    public static void setLogImplementation(final String s) throws LinkageError, NoSuchMethodException, SecurityException, ClassNotFoundException {
        LogSource.logImplctor = Class.forName(s).getConstructor("".getClass());
    }
    
    public static void setLogImplementation(final Class clazz) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException {
        LogSource.logImplctor = clazz.getConstructor("".getClass());
    }
    
    public static Log getInstance(final String s) {
        Log newLogInstance = LogSource.logs.get(s);
        if (null == newLogInstance) {
            newLogInstance = makeNewLogInstance(s);
            LogSource.logs.put(s, newLogInstance);
        }
        return newLogInstance;
    }
    
    public static Log getInstance(final Class clazz) {
        return getInstance(clazz.getName());
    }
    
    public static Log makeNewLogInstance(final String s) {
        Log log = LogSource.logImplctor.newInstance(s);
        if (null == log) {
            log = new NoOpLog(s);
        }
        return log;
    }
    
    public static String[] getLogNames() {
        return (String[])LogSource.logs.keySet().toArray(new String[LogSource.logs.size()]);
    }
    
    static {
        LogSource.logs = new Hashtable();
        LogSource.log4jIsAvailable = false;
        LogSource.jdk14IsAvailable = false;
        LogSource.logImplctor = null;
        LogSource.log4jIsAvailable = (null != Class.forName("org.apache.log4j.Logger"));
        LogSource.jdk14IsAvailable = (null != Class.forName("java.util.logging.Logger") && null != Class.forName("org.apache.commons.logging.impl.Jdk14Logger"));
        String logImplementation = System.getProperty("org.apache.commons.logging.log");
        if (logImplementation == null) {
            logImplementation = System.getProperty("org.apache.commons.logging.Log");
        }
        if (logImplementation != null) {
            setLogImplementation(logImplementation);
        }
        else if (LogSource.log4jIsAvailable) {
            setLogImplementation("org.apache.commons.logging.impl.Log4JLogger");
        }
        else if (LogSource.jdk14IsAvailable) {
            setLogImplementation("org.apache.commons.logging.impl.Jdk14Logger");
        }
        else {
            setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
        }
    }
}
