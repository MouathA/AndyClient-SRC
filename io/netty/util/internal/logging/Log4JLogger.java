package io.netty.util.internal.logging;

import org.apache.log4j.*;

class Log4JLogger extends AbstractInternalLogger
{
    private static final long serialVersionUID = 2851357342488183058L;
    final transient Logger logger;
    static final String FQCN;
    final boolean traceCapable;
    
    Log4JLogger(final Logger logger) {
        super(logger.getName());
        this.logger = logger;
        this.traceCapable = this.isTraceCapable();
    }
    
    private boolean isTraceCapable() {
        this.logger.isTraceEnabled();
        return true;
    }
    
    @Override
    public boolean isTraceEnabled() {
        if (this.traceCapable) {
            return this.logger.isTraceEnabled();
        }
        return this.logger.isDebugEnabled();
    }
    
    @Override
    public void trace(final String s) {
        this.logger.log(Log4JLogger.FQCN, (Priority)(this.traceCapable ? Level.TRACE : Level.DEBUG), (Object)s, (Throwable)null);
    }
    
    @Override
    public void trace(final String s, final Object o) {
        if (this.isTraceEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.log(Log4JLogger.FQCN, (Priority)(this.traceCapable ? Level.TRACE : Level.DEBUG), (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Object o, final Object o2) {
        if (this.isTraceEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.log(Log4JLogger.FQCN, (Priority)(this.traceCapable ? Level.TRACE : Level.DEBUG), (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Object... array) {
        if (this.isTraceEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.log(Log4JLogger.FQCN, (Priority)(this.traceCapable ? Level.TRACE : Level.DEBUG), (Object)arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Throwable t) {
        this.logger.log(Log4JLogger.FQCN, (Priority)(this.traceCapable ? Level.TRACE : Level.DEBUG), (Object)s, t);
    }
    
    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }
    
    @Override
    public void debug(final String s) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.DEBUG, (Object)s, (Throwable)null);
    }
    
    @Override
    public void debug(final String s, final Object o) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.DEBUG, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Object o, final Object o2) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.DEBUG, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Object... array) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.DEBUG, (Object)arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Throwable t) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.DEBUG, (Object)s, t);
    }
    
    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }
    
    @Override
    public void info(final String s) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.INFO, (Object)s, (Throwable)null);
    }
    
    @Override
    public void info(final String s, final Object o) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.INFO, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Object o, final Object o2) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.INFO, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Object... array) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.INFO, (Object)arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Throwable t) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.INFO, (Object)s, t);
    }
    
    @Override
    public boolean isWarnEnabled() {
        return this.logger.isEnabledFor((Priority)Level.WARN);
    }
    
    @Override
    public void warn(final String s) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.WARN, (Object)s, (Throwable)null);
    }
    
    @Override
    public void warn(final String s, final Object o) {
        if (this.logger.isEnabledFor((Priority)Level.WARN)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.WARN, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Object o, final Object o2) {
        if (this.logger.isEnabledFor((Priority)Level.WARN)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.WARN, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Object... array) {
        if (this.logger.isEnabledFor((Priority)Level.WARN)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.WARN, (Object)arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.WARN, (Object)s, t);
    }
    
    @Override
    public boolean isErrorEnabled() {
        return this.logger.isEnabledFor((Priority)Level.ERROR);
    }
    
    @Override
    public void error(final String s) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.ERROR, (Object)s, (Throwable)null);
    }
    
    @Override
    public void error(final String s, final Object o) {
        if (this.logger.isEnabledFor((Priority)Level.ERROR)) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.ERROR, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Object o, final Object o2) {
        if (this.logger.isEnabledFor((Priority)Level.ERROR)) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.ERROR, (Object)format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Object... array) {
        if (this.logger.isEnabledFor((Priority)Level.ERROR)) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.log(Log4JLogger.FQCN, (Priority)Level.ERROR, (Object)arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        this.logger.log(Log4JLogger.FQCN, (Priority)Level.ERROR, (Object)s, t);
    }
    
    static {
        FQCN = Log4JLogger.class.getName();
    }
}
