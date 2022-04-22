package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "AppenderRef", category = "Core", printObject = true)
@PluginAliases({ "appender-ref" })
public final class AppenderRef
{
    private static final Logger LOGGER;
    private final String ref;
    private final Level level;
    private final Filter filter;
    
    private AppenderRef(final String ref, final Level level, final Filter filter) {
        this.ref = ref;
        this.level = level;
        this.filter = filter;
    }
    
    public String getRef() {
        return this.ref;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public Filter getFilter() {
        return this.filter;
    }
    
    @Override
    public String toString() {
        return this.ref;
    }
    
    @PluginFactory
    public static AppenderRef createAppenderRef(@PluginAttribute("ref") final String s, @PluginAttribute("level") final String s2, @PluginElement("Filters") final Filter filter) {
        if (s == null) {
            AppenderRef.LOGGER.error("Appender references must contain a reference");
            return null;
        }
        Level level = null;
        if (s2 != null) {
            level = Level.toLevel(s2, null);
            if (level == null) {
                AppenderRef.LOGGER.error("Invalid level " + s2 + " on Appender reference " + s);
            }
        }
        return new AppenderRef(s, level, filter);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
