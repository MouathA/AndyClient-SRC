package org.apache.logging.log4j.core.config.plugins;

import org.apache.logging.log4j.core.config.*;
import java.util.concurrent.*;

@Plugin(name = "loggers", category = "Core")
public final class LoggersPlugin
{
    private LoggersPlugin() {
    }
    
    @PluginFactory
    public static Loggers createLoggers(@PluginElement("Loggers") final LoggerConfig[] array) {
        final ConcurrentHashMap<String, LoggerConfig> concurrentHashMap = new ConcurrentHashMap<String, LoggerConfig>();
        LoggerConfig loggerConfig = null;
        while (0 < array.length) {
            final LoggerConfig loggerConfig2 = array[0];
            if (loggerConfig2 != null) {
                if (loggerConfig2.getName().isEmpty()) {
                    loggerConfig = loggerConfig2;
                }
                concurrentHashMap.put(loggerConfig2.getName(), loggerConfig2);
            }
            int n = 0;
            ++n;
        }
        return new Loggers(concurrentHashMap, loggerConfig);
    }
}
