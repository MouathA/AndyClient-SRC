package io.netty.util.internal.logging;

import java.io.*;
import io.netty.util.internal.*;

public abstract class AbstractInternalLogger implements InternalLogger, Serializable
{
    private static final long serialVersionUID = -6382972526573193470L;
    private final String name;
    
    protected AbstractInternalLogger(final String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public boolean isEnabled(final InternalLogLevel internalLogLevel) {
        switch (internalLogLevel) {
            case TRACE: {
                return this.isTraceEnabled();
            }
            case DEBUG: {
                return this.isDebugEnabled();
            }
            case INFO: {
                return this.isInfoEnabled();
            }
            case WARN: {
                return this.isWarnEnabled();
            }
            case ERROR: {
                return this.isErrorEnabled();
            }
            default: {
                throw new Error();
            }
        }
    }
    
    @Override
    public void log(final InternalLogLevel internalLogLevel, final String s, final Throwable t) {
        switch (internalLogLevel) {
            case TRACE: {
                this.trace(s, t);
                break;
            }
            case DEBUG: {
                this.debug(s, t);
                break;
            }
            case INFO: {
                this.info(s, t);
                break;
            }
            case WARN: {
                this.warn(s, t);
                break;
            }
            case ERROR: {
                this.error(s, t);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
    
    @Override
    public void log(final InternalLogLevel internalLogLevel, final String s) {
        switch (internalLogLevel) {
            case TRACE: {
                this.trace(s);
                break;
            }
            case DEBUG: {
                this.debug(s);
                break;
            }
            case INFO: {
                this.info(s);
                break;
            }
            case WARN: {
                this.warn(s);
                break;
            }
            case ERROR: {
                this.error(s);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
    
    @Override
    public void log(final InternalLogLevel internalLogLevel, final String s, final Object o) {
        switch (internalLogLevel) {
            case TRACE: {
                this.trace(s, o);
                break;
            }
            case DEBUG: {
                this.debug(s, o);
                break;
            }
            case INFO: {
                this.info(s, o);
                break;
            }
            case WARN: {
                this.warn(s, o);
                break;
            }
            case ERROR: {
                this.error(s, o);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
    
    @Override
    public void log(final InternalLogLevel internalLogLevel, final String s, final Object o, final Object o2) {
        switch (internalLogLevel) {
            case TRACE: {
                this.trace(s, o, o2);
                break;
            }
            case DEBUG: {
                this.debug(s, o, o2);
                break;
            }
            case INFO: {
                this.info(s, o, o2);
                break;
            }
            case WARN: {
                this.warn(s, o, o2);
                break;
            }
            case ERROR: {
                this.error(s, o, o2);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
    
    @Override
    public void log(final InternalLogLevel internalLogLevel, final String s, final Object... array) {
        switch (internalLogLevel) {
            case TRACE: {
                this.trace(s, array);
                break;
            }
            case DEBUG: {
                this.debug(s, array);
                break;
            }
            case INFO: {
                this.info(s, array);
                break;
            }
            case WARN: {
                this.warn(s, array);
                break;
            }
            case ERROR: {
                this.error(s, array);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
    
    protected Object readResolve() throws ObjectStreamException {
        return InternalLoggerFactory.getInstance(this.name());
    }
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.name() + ')';
    }
}
