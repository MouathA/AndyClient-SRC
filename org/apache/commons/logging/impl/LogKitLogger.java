package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import java.io.*;
import org.apache.log.*;

public class LogKitLogger implements Log, Serializable
{
    private static final long serialVersionUID = 3768538055836059519L;
    protected transient Logger logger;
    protected String name;
    
    public LogKitLogger(final String name) {
        this.logger = null;
        this.name = null;
        this.name = name;
        this.logger = this.getLogger();
    }
    
    public Logger getLogger() {
        Logger logger = this.logger;
        if (logger == null) {
            // monitorenter(this)
            logger = this.logger;
            if (logger == null) {
                logger = (this.logger = Hierarchy.getDefaultHierarchy().getLoggerFor(this.name));
            }
        }
        // monitorexit(this)
        return logger;
    }
    
    public void trace(final Object o) {
        this.debug(o);
    }
    
    public void trace(final Object o, final Throwable t) {
        this.debug(o, t);
    }
    
    public void debug(final Object o) {
        if (o != null) {
            this.getLogger().debug(String.valueOf(o));
        }
    }
    
    public void debug(final Object o, final Throwable t) {
        if (o != null) {
            this.getLogger().debug(String.valueOf(o), t);
        }
    }
    
    public void info(final Object o) {
        if (o != null) {
            this.getLogger().info(String.valueOf(o));
        }
    }
    
    public void info(final Object o, final Throwable t) {
        if (o != null) {
            this.getLogger().info(String.valueOf(o), t);
        }
    }
    
    public void warn(final Object o) {
        if (o != null) {
            this.getLogger().warn(String.valueOf(o));
        }
    }
    
    public void warn(final Object o, final Throwable t) {
        if (o != null) {
            this.getLogger().warn(String.valueOf(o), t);
        }
    }
    
    public void error(final Object o) {
        if (o != null) {
            this.getLogger().error(String.valueOf(o));
        }
    }
    
    public void error(final Object o, final Throwable t) {
        if (o != null) {
            this.getLogger().error(String.valueOf(o), t);
        }
    }
    
    public void fatal(final Object o) {
        if (o != null) {
            this.getLogger().fatalError(String.valueOf(o));
        }
    }
    
    public void fatal(final Object o, final Throwable t) {
        if (o != null) {
            this.getLogger().fatalError(String.valueOf(o), t);
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
}
