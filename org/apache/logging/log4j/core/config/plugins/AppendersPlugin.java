package org.apache.logging.log4j.core.config.plugins;

import org.apache.logging.log4j.core.*;
import java.util.concurrent.*;

@Plugin(name = "appenders", category = "Core")
public final class AppendersPlugin
{
    private AppendersPlugin() {
    }
    
    @PluginFactory
    public static ConcurrentMap createAppenders(@PluginElement("Appenders") final Appender[] array) {
        final ConcurrentHashMap<String, Appender> concurrentHashMap = new ConcurrentHashMap<String, Appender>();
        while (0 < array.length) {
            final Appender appender = array[0];
            concurrentHashMap.put(appender.getName(), appender);
            int n = 0;
            ++n;
        }
        return concurrentHashMap;
    }
}
