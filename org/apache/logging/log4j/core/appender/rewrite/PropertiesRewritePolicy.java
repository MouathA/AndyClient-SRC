package org.apache.logging.log4j.core.appender.rewrite;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.impl.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "PropertiesRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class PropertiesRewritePolicy implements RewritePolicy
{
    protected static final Logger LOGGER;
    private final Map properties;
    private final Configuration config;
    
    private PropertiesRewritePolicy(final Configuration config, final List list) {
        this.config = config;
        this.properties = new HashMap(list.size());
        for (final Property property : list) {
            this.properties.put(property, property.getValue().contains("${"));
        }
    }
    
    @Override
    public LogEvent rewrite(final LogEvent logEvent) {
        final HashMap<String, String> hashMap = new HashMap<String, String>(logEvent.getContextMap());
        for (final Map.Entry<Property, V> entry : this.properties.entrySet()) {
            final Property property = entry.getKey();
            hashMap.put(property.getName(), ((boolean)entry.getValue()) ? this.config.getStrSubstitutor().replace(property.getValue()) : property.getValue());
        }
        return new Log4jLogEvent(logEvent.getLoggerName(), logEvent.getMarker(), logEvent.getFQCN(), logEvent.getLevel(), logEvent.getMessage(), logEvent.getThrown(), hashMap, logEvent.getContextStack(), logEvent.getThreadName(), logEvent.getSource(), logEvent.getMillis());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" {");
        for (final Map.Entry<Property, V> entry : this.properties.entrySet()) {
            if (!false) {
                sb.append(", ");
            }
            final Property property = entry.getKey();
            sb.append(property.getName()).append("=").append(property.getValue());
        }
        sb.append("}");
        return sb.toString();
    }
    
    @PluginFactory
    public static PropertiesRewritePolicy createPolicy(@PluginConfiguration final Configuration configuration, @PluginElement("Properties") final Property[] array) {
        if (array == null || array.length == 0) {
            PropertiesRewritePolicy.LOGGER.error("Properties must be specified for the PropertiesRewritePolicy");
            return null;
        }
        return new PropertiesRewritePolicy(configuration, Arrays.asList(array));
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
