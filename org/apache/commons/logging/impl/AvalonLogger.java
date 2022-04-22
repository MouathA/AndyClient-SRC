package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import org.apache.avalon.framework.logger.*;

public class AvalonLogger implements Log
{
    private static Logger defaultLogger;
    private final transient Logger logger;
    
    public AvalonLogger(final Logger logger) {
        this.logger = logger;
    }
    
    public AvalonLogger(final String s) {
        if (AvalonLogger.defaultLogger == null) {
            throw new NullPointerException("default logger has to be specified if this constructor is used!");
        }
        this.logger = AvalonLogger.defaultLogger.getChildLogger(s);
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    
    public static void setDefaultLogger(final Logger defaultLogger) {
        AvalonLogger.defaultLogger = defaultLogger;
    }
    
    public void debug(final Object o, final Throwable t) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(o), t);
        }
    }
    
    public void debug(final Object o) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(o));
        }
    }
    
    public void error(final Object o, final Throwable t) {
        if (this.getLogger().isErrorEnabled()) {
            this.getLogger().error(String.valueOf(o), t);
        }
    }
    
    public void error(final Object o) {
        if (this.getLogger().isErrorEnabled()) {
            this.getLogger().error(String.valueOf(o));
        }
    }
    
    public void fatal(final Object o, final Throwable t) {
        if (this.getLogger().isFatalErrorEnabled()) {
            this.getLogger().fatalError(String.valueOf(o), t);
        }
    }
    
    public void fatal(final Object o) {
        if (this.getLogger().isFatalErrorEnabled()) {
            this.getLogger().fatalError(String.valueOf(o));
        }
    }
    
    public void info(final Object o, final Throwable t) {
        if (this.getLogger().isInfoEnabled()) {
            this.getLogger().info(String.valueOf(o), t);
        }
    }
    
    public void info(final Object o) {
        if (this.getLogger().isInfoEnabled()) {
            this.getLogger().info(String.valueOf(o));
        }
    }
    
    public boolean isDebugEnabled() {
        return this.getLogger().isDebugEnabled();
    }
    
    public boolean isErrorEnabled() {
        return this.getLogger().isErrorEnabled();
    }
    
    public boolean isFatalEnabled() {
        return this.getLogger().isFatalErrorEnabled();
    }
    
    public boolean isInfoEnabled() {
        return this.getLogger().isInfoEnabled();
    }
    
    public boolean isTraceEnabled() {
        return this.getLogger().isDebugEnabled();
    }
    
    public boolean isWarnEnabled() {
        return this.getLogger().isWarnEnabled();
    }
    
    public void trace(final Object o, final Throwable t) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(o), t);
        }
    }
    
    public void trace(final Object o) {
        if (this.getLogger().isDebugEnabled()) {
            this.getLogger().debug(String.valueOf(o));
        }
    }
    
    public void warn(final Object o, final Throwable t) {
        if (this.getLogger().isWarnEnabled()) {
            this.getLogger().warn(String.valueOf(o), t);
        }
    }
    
    public void warn(final Object o) {
        if (this.getLogger().isWarnEnabled()) {
            this.getLogger().warn(String.valueOf(o));
        }
    }
    
    static {
        AvalonLogger.defaultLogger = null;
    }
}
