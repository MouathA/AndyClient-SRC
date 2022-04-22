package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import java.io.*;
import org.apache.log4j.*;

public class Log4JLogger implements Log, Serializable
{
    private static final long serialVersionUID = 5160705895411730424L;
    private static final String FQCN;
    private transient Logger logger;
    private final String name;
    private static final Priority traceLevel;
    static Class class$org$apache$commons$logging$impl$Log4JLogger;
    static Class class$org$apache$log4j$Level;
    static Class class$org$apache$log4j$Priority;
    
    public Log4JLogger() {
        this.logger = null;
        this.name = null;
    }
    
    public Log4JLogger(final String name) {
        this.logger = null;
        this.name = name;
        this.logger = this.getLogger();
    }
    
    public Log4JLogger(final Logger logger) {
        this.logger = null;
        if (logger == null) {
            throw new IllegalArgumentException("Warning - null logger in constructor; possible log4j misconfiguration.");
        }
        this.name = ((Category)logger).getName();
        this.logger = logger;
    }
    
    public void trace(final Object o) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, Log4JLogger.traceLevel, o, (Throwable)null);
    }
    
    public void trace(final Object o, final Throwable t) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, Log4JLogger.traceLevel, o, t);
    }
    
    public void debug(final Object o) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.DEBUG, o, (Throwable)null);
    }
    
    public void debug(final Object o, final Throwable t) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.DEBUG, o, t);
    }
    
    public void info(final Object o) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.INFO, o, (Throwable)null);
    }
    
    public void info(final Object o, final Throwable t) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.INFO, o, t);
    }
    
    public void warn(final Object o) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.WARN, o, (Throwable)null);
    }
    
    public void warn(final Object o, final Throwable t) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.WARN, o, t);
    }
    
    public void error(final Object o) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.ERROR, o, (Throwable)null);
    }
    
    public void error(final Object o, final Throwable t) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.ERROR, o, t);
    }
    
    public void fatal(final Object o) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.FATAL, o, (Throwable)null);
    }
    
    public void fatal(final Object o, final Throwable t) {
        ((Category)this.getLogger()).log(Log4JLogger.FQCN, (Priority)Level.FATAL, o, t);
    }
    
    public Logger getLogger() {
        Logger logger = this.logger;
        if (logger == null) {
            // monitorenter(this)
            logger = this.logger;
            if (logger == null) {
                logger = (this.logger = Logger.getLogger(this.name));
            }
        }
        // monitorexit(this)
        return logger;
    }
    
    public boolean isDebugEnabled() {
        return ((Category)this.getLogger()).isDebugEnabled();
    }
    
    public boolean isErrorEnabled() {
        return ((Category)this.getLogger()).isEnabledFor((Priority)Level.ERROR);
    }
    
    public boolean isFatalEnabled() {
        return ((Category)this.getLogger()).isEnabledFor((Priority)Level.FATAL);
    }
    
    public boolean isInfoEnabled() {
        return ((Category)this.getLogger()).isInfoEnabled();
    }
    
    public boolean isTraceEnabled() {
        return ((Category)this.getLogger()).isEnabledFor(Log4JLogger.traceLevel);
    }
    
    public boolean isWarnEnabled() {
        return ((Category)this.getLogger()).isEnabledFor((Priority)Level.WARN);
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        FQCN = ((Log4JLogger.class$org$apache$commons$logging$impl$Log4JLogger == null) ? (Log4JLogger.class$org$apache$commons$logging$impl$Log4JLogger = class$("org.apache.commons.logging.impl.Log4JLogger")) : Log4JLogger.class$org$apache$commons$logging$impl$Log4JLogger).getName();
        if (!((Log4JLogger.class$org$apache$log4j$Priority == null) ? (Log4JLogger.class$org$apache$log4j$Priority = class$("org.apache.log4j.Priority")) : Log4JLogger.class$org$apache$log4j$Priority).isAssignableFrom((Log4JLogger.class$org$apache$log4j$Level == null) ? (Log4JLogger.class$org$apache$log4j$Level = class$("org.apache.log4j.Level")) : Log4JLogger.class$org$apache$log4j$Level)) {
            throw new InstantiationError("Log4J 1.2 not available");
        }
        traceLevel = (Priority)((Log4JLogger.class$org$apache$log4j$Level == null) ? (Log4JLogger.class$org$apache$log4j$Level = class$("org.apache.log4j.Level")) : Log4JLogger.class$org$apache$log4j$Level).getDeclaredField("TRACE").get(null);
    }
}
