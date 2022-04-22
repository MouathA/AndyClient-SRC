package io.netty.util.internal.logging;

import org.apache.commons.logging.*;

class CommonsLogger extends AbstractInternalLogger
{
    private static final long serialVersionUID = 8647838678388394885L;
    private final transient Log logger;
    
    CommonsLogger(final Log logger, final String s) {
        super(s);
        if (logger == null) {
            throw new NullPointerException("logger");
        }
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
        if (this.logger.isTraceEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.trace(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Object o, final Object o2) {
        if (this.logger.isTraceEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.trace(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void trace(final String s, final Object... array) {
        if (this.logger.isTraceEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.trace(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
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
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.debug(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Object o, final Object o2) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.debug(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void debug(final String s, final Object... array) {
        if (this.logger.isDebugEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.debug(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
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
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.info(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Object o, final Object o2) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.info(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void info(final String s, final Object... array) {
        if (this.logger.isInfoEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.info(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
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
        if (this.logger.isWarnEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.warn(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Object o, final Object o2) {
        if (this.logger.isWarnEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.warn(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void warn(final String s, final Object... array) {
        if (this.logger.isWarnEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.warn(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
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
        if (this.logger.isErrorEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o);
            this.logger.error(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Object o, final Object o2) {
        if (this.logger.isErrorEnabled()) {
            final FormattingTuple format = MessageFormatter.format(s, o, o2);
            this.logger.error(format.getMessage(), format.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Object... array) {
        if (this.logger.isErrorEnabled()) {
            final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(s, array);
            this.logger.error(arrayFormat.getMessage(), arrayFormat.getThrowable());
        }
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        this.logger.error(s, t);
    }
}
