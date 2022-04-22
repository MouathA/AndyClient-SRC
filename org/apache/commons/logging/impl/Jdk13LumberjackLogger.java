package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import java.util.logging.*;
import java.io.*;
import java.util.*;

public class Jdk13LumberjackLogger implements Log, Serializable
{
    private static final long serialVersionUID = -8649807923527610591L;
    protected transient Logger logger;
    protected String name;
    private String sourceClassName;
    private String sourceMethodName;
    private boolean classAndMethodFound;
    protected static final Level dummyLevel;
    
    public Jdk13LumberjackLogger(final String name) {
        this.logger = null;
        this.name = null;
        this.sourceClassName = "unknown";
        this.sourceMethodName = "unknown";
        this.classAndMethodFound = false;
        this.name = name;
        this.logger = this.getLogger();
    }
    
    private void log(final Level level, final String s, final Throwable thrown) {
        if (this.getLogger().isLoggable(level)) {
            final LogRecord logRecord = new LogRecord(level, s);
            if (!this.classAndMethodFound) {
                this.getClassAndMethod();
            }
            logRecord.setSourceClassName(this.sourceClassName);
            logRecord.setSourceMethodName(this.sourceMethodName);
            if (thrown != null) {
                logRecord.setThrown(thrown);
            }
            this.getLogger().log(logRecord);
        }
    }
    
    private void getClassAndMethod() {
        final Throwable t = new Throwable();
        t.fillInStackTrace();
        final StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        final StringTokenizer stringTokenizer = new StringTokenizer(stringWriter.getBuffer().toString(), "\n");
        stringTokenizer.nextToken();
        String s;
        for (s = stringTokenizer.nextToken(); s.indexOf(this.getClass().getName()) == -1; s = stringTokenizer.nextToken()) {}
        while (s.indexOf(this.getClass().getName()) >= 0) {
            s = stringTokenizer.nextToken();
        }
        final String substring = s.substring(s.indexOf("at ") + 3, s.indexOf(40));
        final int lastIndex = substring.lastIndexOf(46);
        this.sourceClassName = substring.substring(0, lastIndex);
        this.sourceMethodName = substring.substring(lastIndex + 1);
        this.classAndMethodFound = true;
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
