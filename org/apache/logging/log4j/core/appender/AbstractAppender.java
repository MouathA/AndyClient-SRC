package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.filter.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.*;

public abstract class AbstractAppender extends AbstractFilterable implements Appender
{
    private final boolean ignoreExceptions;
    private ErrorHandler handler;
    private final Layout layout;
    private final String name;
    private boolean started;
    
    public static int parseInt(final String s, final int n) {
        return Integers.parseInt(s, n);
    }
    
    protected AbstractAppender(final String s, final Filter filter, final Layout layout) {
        this(s, filter, layout, true);
    }
    
    protected AbstractAppender(final String name, final Filter filter, final Layout layout, final boolean ignoreExceptions) {
        super(filter);
        this.handler = new DefaultErrorHandler(this);
        this.started = false;
        this.name = name;
        this.layout = layout;
        this.ignoreExceptions = ignoreExceptions;
    }
    
    public void error(final String s) {
        this.handler.error(s);
    }
    
    public void error(final String s, final LogEvent logEvent, final Throwable t) {
        this.handler.error(s, logEvent, t);
    }
    
    public void error(final String s, final Throwable t) {
        this.handler.error(s, t);
    }
    
    @Override
    public ErrorHandler getHandler() {
        return this.handler;
    }
    
    @Override
    public Layout getLayout() {
        return this.layout;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean ignoreExceptions() {
        return this.ignoreExceptions;
    }
    
    @Override
    public boolean isStarted() {
        return this.started;
    }
    
    @Override
    public void setHandler(final ErrorHandler handler) {
        if (handler == null) {
            AbstractAppender.LOGGER.error("The handler cannot be set to null");
        }
        if (this.isStarted()) {
            AbstractAppender.LOGGER.error("The handler cannot be changed once the appender is started");
            return;
        }
        this.handler = handler;
    }
    
    @Override
    public void start() {
        this.startFilter();
        this.started = true;
    }
    
    @Override
    public void stop() {
        this.started = false;
        this.stopFilter();
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
