package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "failovers", category = "Core")
public final class FailoversPlugin
{
    private static final Logger LOGGER;
    
    private FailoversPlugin() {
    }
    
    @PluginFactory
    public static String[] createFailovers(@PluginElement("AppenderRef") final AppenderRef... array) {
        if (array == null) {
            FailoversPlugin.LOGGER.error("failovers must contain an appender reference");
            return null;
        }
        final String[] array2 = new String[array.length];
        while (0 < array.length) {
            array2[0] = array[0].getRef();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
