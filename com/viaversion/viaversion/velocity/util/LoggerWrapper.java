package com.viaversion.viaversion.velocity.util;

import java.util.logging.*;
import java.text.*;

public class LoggerWrapper extends Logger
{
    private final org.slf4j.Logger base;
    
    public LoggerWrapper(final org.slf4j.Logger base) {
        super("logger", null);
        this.base = base;
    }
    
    @Override
    public void log(final LogRecord logRecord) {
        this.log(logRecord.getLevel(), logRecord.getMessage());
    }
    
    @Override
    public void log(final Level level, final String s) {
        if (level == Level.FINE) {
            this.base.debug(s);
        }
        else if (level == Level.WARNING) {
            this.base.warn(s);
        }
        else if (level == Level.SEVERE) {
            this.base.error(s);
        }
        else if (level == Level.INFO) {
            this.base.info(s);
        }
        else {
            this.base.trace(s);
        }
    }
    
    @Override
    public void log(final Level level, final String s, final Object o) {
        if (level == Level.FINE) {
            this.base.debug(s, o);
        }
        else if (level == Level.WARNING) {
            this.base.warn(s, o);
        }
        else if (level == Level.SEVERE) {
            this.base.error(s, o);
        }
        else if (level == Level.INFO) {
            this.base.info(s, o);
        }
        else {
            this.base.trace(s, o);
        }
    }
    
    @Override
    public void log(final Level level, final String s, final Object[] array) {
        this.log(level, MessageFormat.format(s, array));
    }
    
    @Override
    public void log(final Level level, final String s, final Throwable t) {
        if (level == Level.FINE) {
            this.base.debug(s, t);
        }
        else if (level == Level.WARNING) {
            this.base.warn(s, t);
        }
        else if (level == Level.SEVERE) {
            this.base.error(s, t);
        }
        else if (level == Level.INFO) {
            this.base.info(s, t);
        }
        else {
            this.base.trace(s, t);
        }
    }
}
