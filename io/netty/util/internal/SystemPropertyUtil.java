package io.netty.util.internal;

import java.util.regex.*;
import java.security.*;
import java.util.logging.*;
import io.netty.util.internal.logging.*;

public final class SystemPropertyUtil
{
    private static boolean initializedLogger;
    private static final InternalLogger logger;
    private static boolean loggedException;
    private static final Pattern INTEGER_PATTERN;
    
    public static boolean contains(final String s) {
        return get(s) != null;
    }
    
    public static String get(final String s) {
        return get(s, null);
    }
    
    public static String get(final String s, final String s2) {
        if (s == null) {
            throw new NullPointerException("key");
        }
        if (s.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty.");
        }
        String property;
        if (System.getSecurityManager() == null) {
            property = System.getProperty(s);
        }
        else {
            property = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction(s) {
                final String val$key;
                
                @Override
                public String run() {
                    return System.getProperty(this.val$key);
                }
                
                @Override
                public Object run() {
                    return this.run();
                }
            });
        }
        if (property == null) {
            return s2;
        }
        return property;
    }
    
    public static boolean getBoolean(final String s, final boolean b) {
        final String value = get(s);
        if (value == null) {
            return b;
        }
        final String lowerCase = value.trim().toLowerCase();
        if (lowerCase.isEmpty()) {
            return true;
        }
        if ("true".equals(lowerCase) || "yes".equals(lowerCase) || "1".equals(lowerCase)) {
            return true;
        }
        if ("false".equals(lowerCase) || "no".equals(lowerCase) || "0".equals(lowerCase)) {
            return false;
        }
        log("Unable to parse the boolean system property '" + s + "':" + lowerCase + " - " + "using the default value: " + b);
        return b;
    }
    
    public static int getInt(final String s, final int n) {
        final String value = get(s);
        if (value == null) {
            return n;
        }
        final String lowerCase = value.trim().toLowerCase();
        if (SystemPropertyUtil.INTEGER_PATTERN.matcher(lowerCase).matches()) {
            return Integer.parseInt(lowerCase);
        }
        log("Unable to parse the integer system property '" + s + "':" + lowerCase + " - " + "using the default value: " + n);
        return n;
    }
    
    public static long getLong(final String s, final long n) {
        final String value = get(s);
        if (value == null) {
            return n;
        }
        final String lowerCase = value.trim().toLowerCase();
        if (SystemPropertyUtil.INTEGER_PATTERN.matcher(lowerCase).matches()) {
            return Long.parseLong(lowerCase);
        }
        log("Unable to parse the long integer system property '" + s + "':" + lowerCase + " - " + "using the default value: " + n);
        return n;
    }
    
    private static void log(final String s) {
        if (SystemPropertyUtil.initializedLogger) {
            SystemPropertyUtil.logger.warn(s);
        }
        else {
            Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, s);
        }
    }
    
    private static void log(final String s, final Exception ex) {
        if (SystemPropertyUtil.initializedLogger) {
            SystemPropertyUtil.logger.warn(s, ex);
        }
        else {
            Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, s, ex);
        }
    }
    
    private SystemPropertyUtil() {
    }
    
    static {
        SystemPropertyUtil.initializedLogger = false;
        logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);
        SystemPropertyUtil.initializedLogger = true;
        INTEGER_PATTERN = Pattern.compile("-?[0-9]+");
    }
}
