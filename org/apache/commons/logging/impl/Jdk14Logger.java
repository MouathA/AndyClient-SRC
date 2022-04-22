package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import java.io.*;
import java.util.logging.*;

public class Jdk14Logger implements Log, Serializable
{
    private static final long serialVersionUID = 4784713551416303804L;
    protected static final Level dummyLevel;
    protected transient Logger logger;
    protected String name;
    
    public Jdk14Logger(final String name) {
        this.logger = null;
        this.name = null;
        this.name = name;
        this.logger = this.getLogger();
    }
    
    protected void log(final Level level, final String s, final Throwable t) {
        final Logger logger = this.getLogger();
        if (logger.isLoggable(level)) {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            final String name = this.name;
            String methodName = "unknown";
            if (stackTrace != null && stackTrace.length > 2) {
                methodName = stackTrace[2].getMethodName();
            }
            if (t == null) {
                logger.logp(level, name, methodName, s);
            }
            else {
                logger.logp(level, name, methodName, s, t);
            }
        }
    }
    
    public void debug(final Object o) {
        this.log(Level.FINE, String.valueOf(o), null);
    }
    
    public void debug(final Object o, final Throwable t) {
        this.log(Level.FINE, String.valueOf(o), t);
    }
    
    public void error(final Object o) {
        this.log(Level.SEVERE, String.valueOf(o), null);
    }
    
    public void error(final Object o, final Throwable t) {
        this.log(Level.SEVERE, String.valueOf(o), t);
    }
    
    public void fatal(final Object o) {
        this.log(Level.SEVERE, String.valueOf(o), null);
    }
    
    public void fatal(final Object o, final Throwable t) {
        this.log(Level.SEVERE, String.valueOf(o), t);
    }
    
    public Logger getLogger() {
        if (this.logger == null) {
            this.logger = Logger.getLogger(this.name);
        }
        return this.logger;
    }
    
    public void info(final Object o) {
        this.log(Level.INFO, String.valueOf(o), null);
    }
    
    public void info(final Object o, final Throwable t) {
        this.log(Level.INFO, String.valueOf(o), t);
    }
    
    public boolean isDebugEnabled() {
        return this.getLogger().isLoggable(Level.FINE);
    }
    
    public boolean isErrorEnabled() {
        return this.getLogger().isLoggable(Level.SEVERE);
    }
    
    public boolean isFatalEnabled() {
        return this.getLogger().isLoggable(Level.SEVERE);
    }
    
    public boolean isInfoEnabled() {
        return this.getLogger().isLoggable(Level.INFO);
    }
    
    public boolean isTraceEnabled() {
        return this.getLogger().isLoggable(Level.FINEST);
    }
    
    public boolean isWarnEnabled() {
        return this.getLogger().isLoggable(Level.WARNING);
    }
    
    public void trace(final Object o) {
        this.log(Level.FINEST, String.valueOf(o), null);
    }
    
    public void trace(final Object o, final Throwable t) {
        this.log(Level.FINEST, String.valueOf(o), t);
    }
    
    public void warn(final Object o) {
        this.log(Level.WARNING, String.valueOf(o), null);
    }
    
    public void warn(final Object o, final Throwable t) {
        this.log(Level.WARNING, String.valueOf(o), t);
    }
    
    static {
        dummyLevel = Level.FINE;
    }
}
