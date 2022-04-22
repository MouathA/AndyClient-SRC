package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "asyncLogger", category = "Core", printObject = true)
public class AsyncLoggerConfig extends LoggerConfig
{
    private AsyncLoggerConfigHelper helper;
    
    public AsyncLoggerConfig() {
    }
    
    public AsyncLoggerConfig(final String s, final Level level, final boolean b) {
        super(s, level, b);
    }
    
    protected AsyncLoggerConfig(final String s, final List list, final Filter filter, final Level level, final boolean b, final Property[] array, final Configuration configuration, final boolean b2) {
        super(s, list, filter, level, b, array, configuration, b2);
    }
    
    @Override
    protected void callAppenders(final LogEvent logEvent) {
        logEvent.getSource();
        logEvent.getThreadName();
        this.helper.callAppendersFromAnotherThread(logEvent);
    }
    
    void asyncCallAppenders(final LogEvent logEvent) {
        super.callAppenders(logEvent);
    }
    
    @Override
    public void startFilter() {
        if (this.helper == null) {
            this.helper = new AsyncLoggerConfigHelper(this);
        }
        super.startFilter();
    }
    
    @Override
    public void stopFilter() {
        super.stopFilter();
    }
    
    @PluginFactory
    public static LoggerConfig createLogger(@PluginAttribute("additivity") final String s, @PluginAttribute("level") final String s2, @PluginAttribute("name") final String s3, @PluginAttribute("includeLocation") final String s4, @PluginElement("AppenderRef") final AppenderRef[] array, @PluginElement("Properties") final Property[] array2, @PluginConfiguration final Configuration configuration, @PluginElement("Filters") final Filter filter) {
        if (s3 == null) {
            AsyncLoggerConfig.LOGGER.error("Loggers cannot be configured without a name");
            return null;
        }
        return new AsyncLoggerConfig(s3.equals("root") ? "" : s3, Arrays.asList(array), filter, Level.toLevel(s2, Level.ERROR), Booleans.parseBoolean(s, true), array2, configuration, includeLocation(s4));
    }
    
    protected static boolean includeLocation(final String s) {
        return Boolean.parseBoolean(s);
    }
    
    @Plugin(name = "asyncRoot", category = "Core", printObject = true)
    public static class RootLogger extends LoggerConfig
    {
        @PluginFactory
        public static LoggerConfig createLogger(@PluginAttribute("additivity") final String s, @PluginAttribute("level") final String s2, @PluginAttribute("includeLocation") final String s3, @PluginElement("AppenderRef") final AppenderRef[] array, @PluginElement("Properties") final Property[] array2, @PluginConfiguration final Configuration configuration, @PluginElement("Filters") final Filter filter) {
            return new AsyncLoggerConfig("", Arrays.asList(array), filter, Level.toLevel(s2, Level.ERROR), Booleans.parseBoolean(s, true), array2, configuration, LoggerConfig.includeLocation(s3));
        }
    }
}
