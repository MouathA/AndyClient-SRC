package io.netty.util.internal.logging;

import org.slf4j.*;

class Slf4JLogger extends AbstractInternalLogger
{
    private static final long serialVersionUID = 108038972685130825L;
    private final transient Logger logger;
    
    Slf4JLogger(final Logger logger) {
        super(logger.getName());
        this.logger = logger;
    }
    
    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }
    
    @Override
    public void trace(final String s) {
        this.logger.trace(s);
    }
    
    @Override
    public void trace(final String s, final Object o) {
        this.logger.trace(s, o);
    }
    
    @Override
    public void trace(final String s, final Object o, final Object o2) {
        this.logger.trace(s, o, o2);
    }
    
    @Override
    public void trace(final String s, final Object... array) {
        this.logger.trace(s, array);
    }
    
    @Override
    public void trace(final String s, final Throwable t) {
        this.logger.trace(s, t);
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }
    
    @Override
    public void debug(final String s) {
        this.logger.debug(s);
    }
    
    @Override
    public void debug(final String s, final Object o) {
        this.logger.debug(s, o);
    }
    
    @Override
    public void debug(final String s, final Object o, final Object o2) {
        this.logger.debug(s, o, o2);
    }
    
    @Override
    public void debug(final String s, final Object... array) {
        this.logger.debug(s, array);
    }
    
    @Override
    public void debug(final String s, final Throwable t) {
        this.logger.debug(s, t);
    }
    
    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }
    
    @Override
    public void info(final String s) {
        this.logger.info(s);
    }
    
    @Override
    public void info(final String s, final Object o) {
        this.logger.info(s, o);
    }
    
    @Override
    public void info(final String s, final Object o, final Object o2) {
        this.logger.info(s, o, o2);
    }
    
    @Override
    public void info(final String s, final Object... array) {
        this.logger.info(s, array);
    }
    
    @Override
    public void info(final String s, final Throwable t) {
        this.logger.info(s, t);
    }
    
    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }
    
    @Override
    public void warn(final String s) {
        this.logger.warn(s);
    }
    
    @Override
    public void warn(final String s, final Object o) {
        this.logger.warn(s, o);
    }
    
    @Override
    public void warn(final String s, final Object... array) {
        this.logger.warn(s, array);
    }
    
    @Override
    public void warn(final String s, final Object o, final Object o2) {
        this.logger.warn(s, o, o2);
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
        this.logger.warn(s, t);
    }
    
    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }
    
    @Override
    public void error(final String s) {
        this.logger.error(s);
    }
    
    @Override
    public void error(final String s, final Object o) {
        this.logger.error(s, o);
    }
    
    @Override
    public void error(final String s, final Object o, final Object o2) {
        this.logger.error(s, o, o2);
    }
    
    @Override
    public void error(final String s, final Object... array) {
        this.logger.error(s, array);
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        this.logger.error(s, t);
    }
}
