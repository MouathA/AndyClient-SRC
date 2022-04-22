package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import java.util.*;
import java.io.*;
import java.security.*;
import java.text.*;

public class SimpleLog implements Log, Serializable
{
    private static final long serialVersionUID = 136942970684951178L;
    protected static final String systemPrefix = "org.apache.commons.logging.simplelog.";
    protected static final Properties simpleLogProps;
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    protected static boolean showLogName;
    protected static boolean showShortName;
    protected static boolean showDateTime;
    protected static String dateTimeFormat;
    protected static DateFormat dateFormatter;
    public static final int LOG_LEVEL_TRACE = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_FATAL = 6;
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_OFF = 7;
    protected String logName;
    protected int currentLogLevel;
    private String shortLogName;
    static Class class$java$lang$Thread;
    static Class class$org$apache$commons$logging$impl$SimpleLog;
    
    private static String getStringProperty(final String s) {
        final String property = System.getProperty(s);
        return (property == null) ? SimpleLog.simpleLogProps.getProperty(s) : property;
    }
    
    private static String getStringProperty(final String s, final String s2) {
        final String stringProperty = getStringProperty(s);
        return (stringProperty == null) ? s2 : stringProperty;
    }
    
    private static boolean getBooleanProperty(final String s, final boolean b) {
        final String stringProperty = getStringProperty(s);
        return (stringProperty == null) ? b : "true".equalsIgnoreCase(stringProperty);
    }
    
    public SimpleLog(String substring) {
        this.logName = null;
        this.shortLogName = null;
        this.logName = substring;
        this.setLevel(3);
        String s = getStringProperty("org.apache.commons.logging.simplelog.log." + this.logName);
        for (int n = String.valueOf(substring).lastIndexOf("."); null == s && n > -1; s = getStringProperty("org.apache.commons.logging.simplelog.log." + substring), n = String.valueOf(substring).lastIndexOf(".")) {
            substring = substring.substring(0, n);
        }
        if (null == s) {
            s = getStringProperty("org.apache.commons.logging.simplelog.defaultlog");
        }
        if ("all".equalsIgnoreCase(s)) {
            this.setLevel(0);
        }
        else if ("trace".equalsIgnoreCase(s)) {
            this.setLevel(1);
        }
        else if ("debug".equalsIgnoreCase(s)) {
            this.setLevel(2);
        }
        else if ("info".equalsIgnoreCase(s)) {
            this.setLevel(3);
        }
        else if ("warn".equalsIgnoreCase(s)) {
            this.setLevel(4);
        }
        else if ("error".equalsIgnoreCase(s)) {
            this.setLevel(5);
        }
        else if ("fatal".equalsIgnoreCase(s)) {
            this.setLevel(6);
        }
        else if ("off".equalsIgnoreCase(s)) {
            this.setLevel(7);
        }
    }
    
    public void setLevel(final int currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }
    
    public int getLevel() {
        return this.currentLogLevel;
    }
    
    protected void log(final int n, final Object o, final Throwable t) {
        final StringBuffer sb = new StringBuffer();
        if (SimpleLog.showDateTime) {
            final Date date = new Date();
            // monitorenter(dateFormatter = SimpleLog.dateFormatter)
            final String format = SimpleLog.dateFormatter.format(date);
            // monitorexit(dateFormatter)
            sb.append(format);
            sb.append(" ");
        }
        switch (n) {
            case 1: {
                sb.append("[TRACE] ");
                break;
            }
            case 2: {
                sb.append("[DEBUG] ");
                break;
            }
            case 3: {
                sb.append("[INFO] ");
                break;
            }
            case 4: {
                sb.append("[WARN] ");
                break;
            }
            case 5: {
                sb.append("[ERROR] ");
                break;
            }
            case 6: {
                sb.append("[FATAL] ");
                break;
            }
        }
        if (SimpleLog.showShortName) {
            if (this.shortLogName == null) {
                final String substring = this.logName.substring(this.logName.lastIndexOf(".") + 1);
                this.shortLogName = substring.substring(substring.lastIndexOf("/") + 1);
            }
            sb.append(String.valueOf(this.shortLogName)).append(" - ");
        }
        else if (SimpleLog.showLogName) {
            sb.append(String.valueOf(this.logName)).append(" - ");
        }
        sb.append(String.valueOf(o));
        if (t != null) {
            sb.append(" <");
            sb.append(t.toString());
            sb.append(">");
            final StringWriter stringWriter = new StringWriter(1024);
            final PrintWriter printWriter = new PrintWriter(stringWriter);
            t.printStackTrace(printWriter);
            printWriter.close();
            sb.append(stringWriter.toString());
        }
        this.write(sb);
    }
    
    protected void write(final StringBuffer sb) {
        System.err.println(sb.toString());
    }
    
    public final void debug(final Object o) {
        if (this >= 2) {
            this.log(2, o, null);
        }
    }
    
    public final void debug(final Object o, final Throwable t) {
        if (this >= 2) {
            this.log(2, o, t);
        }
    }
    
    public final void trace(final Object o) {
        if (this >= 1) {
            this.log(1, o, null);
        }
    }
    
    public final void trace(final Object o, final Throwable t) {
        if (this >= 1) {
            this.log(1, o, t);
        }
    }
    
    public final void info(final Object o) {
        if (this >= 3) {
            this.log(3, o, null);
        }
    }
    
    public final void info(final Object o, final Throwable t) {
        if (this >= 3) {
            this.log(3, o, t);
        }
    }
    
    public final void warn(final Object o) {
        if (this >= 4) {
            this.log(4, o, null);
        }
    }
    
    public final void warn(final Object o, final Throwable t) {
        if (this >= 4) {
            this.log(4, o, t);
        }
    }
    
    public final void error(final Object o) {
        if (this >= 5) {
            this.log(5, o, null);
        }
    }
    
    public final void error(final Object o, final Throwable t) {
        if (this >= 5) {
            this.log(5, o, t);
        }
    }
    
    public final void fatal(final Object o) {
        if (this >= 6) {
            this.log(6, o, null);
        }
    }
    
    public final void fatal(final Object o, final Throwable t) {
        if (this >= 6) {
            this.log(6, o, t);
        }
    }
    
    public final boolean isDebugEnabled() {
        return this.isLevelEnabled(2);
    }
    
    public final boolean isErrorEnabled() {
        return this.isLevelEnabled(5);
    }
    
    public final boolean isFatalEnabled() {
        return this.isLevelEnabled(6);
    }
    
    public final boolean isInfoEnabled() {
        return this.isLevelEnabled(3);
    }
    
    public final boolean isTraceEnabled() {
        return this.isLevelEnabled(1);
    }
    
    public final boolean isWarnEnabled() {
        return this.isLevelEnabled(4);
    }
    
    private static ClassLoader getContextClassLoader() {
        ClassLoader classLoader = (ClassLoader)((SimpleLog.class$java$lang$Thread == null) ? (SimpleLog.class$java$lang$Thread = class$("java.lang.Thread")) : SimpleLog.class$java$lang$Thread).getMethod("getContextClassLoader", (Class[])null).invoke(Thread.currentThread(), (Object[])null);
        if (classLoader == null) {
            classLoader = ((SimpleLog.class$org$apache$commons$logging$impl$SimpleLog == null) ? (SimpleLog.class$org$apache$commons$logging$impl$SimpleLog = class$("org.apache.commons.logging.impl.SimpleLog")) : SimpleLog.class$org$apache$commons$logging$impl$SimpleLog).getClassLoader();
        }
        return classLoader;
    }
    
    private static InputStream getResourceAsStream(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<InputStream>)new PrivilegedAction(s) {
            private final String val$name = val$name;
            
            public Object run() {
                final ClassLoader access$000 = SimpleLog.access$000();
                if (access$000 != null) {
                    return access$000.getResourceAsStream(this.val$name);
                }
                return ClassLoader.getSystemResourceAsStream(this.val$name);
            }
        });
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static ClassLoader access$000() {
        return getContextClassLoader();
    }
    
    static {
        simpleLogProps = new Properties();
        SimpleLog.showLogName = false;
        SimpleLog.showShortName = true;
        SimpleLog.showDateTime = false;
        SimpleLog.dateTimeFormat = "yyyy/MM/dd HH:mm:ss:SSS zzz";
        SimpleLog.dateFormatter = null;
        final InputStream resourceAsStream = getResourceAsStream("simplelog.properties");
        if (null != resourceAsStream) {
            SimpleLog.simpleLogProps.load(resourceAsStream);
            resourceAsStream.close();
        }
        SimpleLog.showLogName = getBooleanProperty("org.apache.commons.logging.simplelog.showlogname", SimpleLog.showLogName);
        SimpleLog.showShortName = getBooleanProperty("org.apache.commons.logging.simplelog.showShortLogname", SimpleLog.showShortName);
        SimpleLog.showDateTime = getBooleanProperty("org.apache.commons.logging.simplelog.showdatetime", SimpleLog.showDateTime);
        if (SimpleLog.showDateTime) {
            SimpleLog.dateTimeFormat = getStringProperty("org.apache.commons.logging.simplelog.dateTimeFormat", SimpleLog.dateTimeFormat);
            SimpleLog.dateFormatter = new SimpleDateFormat(SimpleLog.dateTimeFormat);
        }
    }
}
