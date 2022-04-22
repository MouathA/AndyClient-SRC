package org.apache.logging.log4j.core;

import org.apache.logging.log4j.spi.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import java.util.*;
import org.apache.logging.log4j.core.filter.*;
import org.apache.logging.log4j.core.config.*;

public class Logger extends AbstractLogger
{
    protected PrivateConfig config;
    private final LoggerContext context;
    
    protected Logger(final LoggerContext context, final String s, final MessageFactory messageFactory) {
        super(s, messageFactory);
        this.context = context;
        this.config = new PrivateConfig(context.getConfiguration(), this);
    }
    
    public Logger getParent() {
        final LoggerConfig loggerConfig = this.config.loggerConfig.getName().equals(this.getName()) ? this.config.loggerConfig.getParent() : this.config.loggerConfig;
        if (loggerConfig == null) {
            return null;
        }
        if (this.context.hasLogger(loggerConfig.getName())) {
            return this.context.getLogger(loggerConfig.getName(), this.getMessageFactory());
        }
        return new Logger(this.context, loggerConfig.getName(), this.getMessageFactory());
    }
    
    public LoggerContext getContext() {
        return this.context;
    }
    
    public synchronized void setLevel(final Level level) {
        if (level != null) {
            this.config = new PrivateConfig(this.config, level);
        }
    }
    
    public Level getLevel() {
        return PrivateConfig.access$000(this.config);
    }
    
    @Override
    public void log(final Marker marker, final String s, final Level level, Message message, final Throwable t) {
        if (message == null) {
            message = new SimpleMessage("");
        }
        this.config.config.getConfigurationMonitor().checkConfiguration();
        this.config.loggerConfig.log(this.getName(), marker, s, level, message, t);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final String s) {
        return this.config.filter(level, marker, s);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final String s, final Throwable t) {
        return this.config.filter(level, marker, s, t);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final String s, final Object... array) {
        return this.config.filter(level, marker, s, array);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.config.filter(level, marker, o, t);
    }
    
    public boolean isEnabled(final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.config.filter(level, marker, message, t);
    }
    
    public void addAppender(final Appender appender) {
        this.config.config.addLoggerAppender(this, appender);
    }
    
    public void removeAppender(final Appender appender) {
        this.config.loggerConfig.removeAppender(appender.getName());
    }
    
    public Map getAppenders() {
        return this.config.loggerConfig.getAppenders();
    }
    
    public Iterator getFilters() {
        final Filter filter = this.config.loggerConfig.getFilter();
        if (filter == null) {
            return new ArrayList().iterator();
        }
        if (filter instanceof CompositeFilter) {
            return ((CompositeFilter)filter).iterator();
        }
        final ArrayList<CompositeFilter> list = new ArrayList<CompositeFilter>();
        list.add((CompositeFilter)filter);
        return list.iterator();
    }
    
    public int filterCount() {
        final Filter filter = this.config.loggerConfig.getFilter();
        if (filter == null) {
            return 0;
        }
        if (filter instanceof CompositeFilter) {
            return ((CompositeFilter)filter).size();
        }
        return 1;
    }
    
    public void addFilter(final Filter filter) {
        this.config.config.addLoggerFilter(this, filter);
    }
    
    public boolean isAdditive() {
        return this.config.loggerConfig.isAdditive();
    }
    
    public void setAdditive(final boolean b) {
        this.config.config.setLoggerAdditive(this, b);
    }
    
    void updateConfiguration(final Configuration configuration) {
        this.config = new PrivateConfig(configuration, this);
    }
    
    @Override
    public String toString() {
        final String string = "" + this.getName() + ":" + this.getLevel();
        if (this.context == null) {
            return string;
        }
        final String name = this.context.getName();
        return (name == null) ? string : (string + " in " + name);
    }
    
    protected class PrivateConfig
    {
        public final LoggerConfig loggerConfig;
        public final Configuration config;
        private final Level level;
        private final int intLevel;
        private final Logger logger;
        final Logger this$0;
        
        public PrivateConfig(final Logger this$0, final Configuration config, final Logger logger) {
            this.this$0 = this$0;
            this.config = config;
            this.loggerConfig = config.getLoggerConfig(this$0.getName());
            this.level = this.loggerConfig.getLevel();
            this.intLevel = this.level.intLevel();
            this.logger = logger;
        }
        
        public PrivateConfig(final Logger this$0, final PrivateConfig privateConfig, final Level level) {
            this.this$0 = this$0;
            this.config = privateConfig.config;
            this.loggerConfig = privateConfig.loggerConfig;
            this.level = level;
            this.intLevel = this.level.intLevel();
            this.logger = privateConfig.logger;
        }
        
        public PrivateConfig(final Logger this$0, final PrivateConfig privateConfig, final LoggerConfig loggerConfig) {
            this.this$0 = this$0;
            this.config = privateConfig.config;
            this.loggerConfig = loggerConfig;
            this.level = loggerConfig.getLevel();
            this.intLevel = this.level.intLevel();
            this.logger = privateConfig.logger;
        }
        
        public void logEvent(final LogEvent logEvent) {
            this.config.getConfigurationMonitor().checkConfiguration();
            this.loggerConfig.log(logEvent);
        }
        
        boolean filter(final Level level, final Marker marker, final String s) {
            this.config.getConfigurationMonitor().checkConfiguration();
            final Filter filter = this.config.getFilter();
            if (filter != null) {
                final Filter.Result filter2 = filter.filter(this.logger, level, marker, s, new Object[0]);
                if (filter2 != Filter.Result.NEUTRAL) {
                    return filter2 == Filter.Result.ACCEPT;
                }
            }
            return this.intLevel >= level.intLevel();
        }
        
        boolean filter(final Level level, final Marker marker, final String s, final Throwable t) {
            this.config.getConfigurationMonitor().checkConfiguration();
            final Filter filter = this.config.getFilter();
            if (filter != null) {
                final Filter.Result filter2 = filter.filter(this.logger, level, marker, (Object)s, t);
                if (filter2 != Filter.Result.NEUTRAL) {
                    return filter2 == Filter.Result.ACCEPT;
                }
            }
            return this.intLevel >= level.intLevel();
        }
        
        boolean filter(final Level level, final Marker marker, final String s, final Object... array) {
            this.config.getConfigurationMonitor().checkConfiguration();
            final Filter filter = this.config.getFilter();
            if (filter != null) {
                final Filter.Result filter2 = filter.filter(this.logger, level, marker, s, array);
                if (filter2 != Filter.Result.NEUTRAL) {
                    return filter2 == Filter.Result.ACCEPT;
                }
            }
            return this.intLevel >= level.intLevel();
        }
        
        boolean filter(final Level level, final Marker marker, final Object o, final Throwable t) {
            this.config.getConfigurationMonitor().checkConfiguration();
            final Filter filter = this.config.getFilter();
            if (filter != null) {
                final Filter.Result filter2 = filter.filter(this.logger, level, marker, o, t);
                if (filter2 != Filter.Result.NEUTRAL) {
                    return filter2 == Filter.Result.ACCEPT;
                }
            }
            return this.intLevel >= level.intLevel();
        }
        
        boolean filter(final Level level, final Marker marker, final Message message, final Throwable t) {
            this.config.getConfigurationMonitor().checkConfiguration();
            final Filter filter = this.config.getFilter();
            if (filter != null) {
                final Filter.Result filter2 = filter.filter(this.logger, level, marker, message, t);
                if (filter2 != Filter.Result.NEUTRAL) {
                    return filter2 == Filter.Result.ACCEPT;
                }
            }
            return this.intLevel >= level.intLevel();
        }
        
        static Level access$000(final PrivateConfig privateConfig) {
            return privateConfig.level;
        }
    }
}
