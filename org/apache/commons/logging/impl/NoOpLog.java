package org.apache.commons.logging.impl;

import org.apache.commons.logging.*;
import java.io.*;

public class NoOpLog implements Log, Serializable
{
    private static final long serialVersionUID = 561423906191706148L;
    
    public NoOpLog() {
    }
    
    public NoOpLog(final String s) {
    }
    
    public void trace(final Object o) {
    }
    
    public void trace(final Object o, final Throwable t) {
    }
    
    public void debug(final Object o) {
    }
    
    public void debug(final Object o, final Throwable t) {
    }
    
    public void info(final Object o) {
    }
    
    public void info(final Object o, final Throwable t) {
    }
    
    public void warn(final Object o) {
    }
    
    public void warn(final Object o, final Throwable t) {
    }
    
    public void error(final Object o) {
    }
    
    public void error(final Object o, final Throwable t) {
    }
    
    public void fatal(final Object o) {
    }
    
    public void fatal(final Object o, final Throwable t) {
    }
    
    public final boolean isDebugEnabled() {
        return false;
    }
    
    public final boolean isErrorEnabled() {
        return false;
    }
    
    public final boolean isFatalEnabled() {
        return false;
    }
    
    public final boolean isInfoEnabled() {
        return false;
    }
    
    public final boolean isTraceEnabled() {
        return false;
    }
    
    public final boolean isWarnEnabled() {
        return false;
    }
}
