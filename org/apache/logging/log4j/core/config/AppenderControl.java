package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.filter.*;

public class AppenderControl extends AbstractFilterable
{
    private final ThreadLocal recursive;
    private final Appender appender;
    private final Level level;
    private final int intLevel;
    
    public AppenderControl(final Appender appender, final Level level, final Filter filter) {
        super(filter);
        this.recursive = new ThreadLocal();
        this.appender = appender;
        this.level = level;
        this.intLevel = ((level == null) ? Level.ALL.intLevel() : level.intLevel());
        this.startFilter();
    }
    
    public Appender getAppender() {
        return this.appender;
    }
    
    public void callAppender(final LogEvent logEvent) {
        if (this.getFilter() != null && this.getFilter().filter(logEvent) == Filter.Result.DENY) {
            return;
        }
        if (this.level != null && this.intLevel < logEvent.getLevel().intLevel()) {
            return;
        }
        if (this.recursive.get() != null) {
            this.appender.getHandler().error("Recursive call to appender " + this.appender.getName());
            return;
        }
        this.recursive.set(this);
        if (!this.appender.isStarted()) {
            this.appender.getHandler().error("Attempted to append to non-started appender " + this.appender.getName());
            if (!this.appender.ignoreExceptions()) {
                throw new AppenderLoggingException("Attempted to append to non-started appender " + this.appender.getName());
            }
        }
        if (this.appender instanceof Filterable && ((Filterable)this.appender).isFiltered(logEvent)) {
            this.recursive.set(null);
            return;
        }
        this.appender.append(logEvent);
        this.recursive.set(null);
    }
}
