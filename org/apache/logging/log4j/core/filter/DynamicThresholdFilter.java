package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "DynamicThresholdFilter", category = "Core", elementType = "filter", printObject = true)
public final class DynamicThresholdFilter extends AbstractFilter
{
    private Map levelMap;
    private Level defaultThreshold;
    private final String key;
    
    private DynamicThresholdFilter(final String key, final Map levelMap, final Level defaultThreshold, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        this.levelMap = new HashMap();
        this.defaultThreshold = Level.ERROR;
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        }
        this.key = key;
        this.levelMap = levelMap;
        this.defaultThreshold = defaultThreshold;
    }
    
    public String getKey() {
        return this.key;
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final String s, final Object... array) {
        return this.filter(level);
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.filter(level);
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.filter(level);
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        return this.filter(logEvent.getLevel());
    }
    
    private Filter.Result filter(final Level level) {
        final String value = ThreadContext.get(this.key);
        if (value != null) {
            Level defaultThreshold = this.levelMap.get(value);
            if (defaultThreshold == null) {
                defaultThreshold = this.defaultThreshold;
            }
            return level.isAtLeastAsSpecificAs(defaultThreshold) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }
    
    public Map getLevelMap() {
        return this.levelMap;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("key=").append(this.key);
        sb.append(", default=").append(this.defaultThreshold);
        if (this.levelMap.size() > 0) {
            sb.append("{");
            for (final Map.Entry<String, V> entry : this.levelMap.entrySet()) {
                sb.append(", ");
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
            sb.append("}");
        }
        return sb.toString();
    }
    
    @PluginFactory
    public static DynamicThresholdFilter createFilter(@PluginAttribute("key") final String s, @PluginElement("Pairs") final KeyValuePair[] array, @PluginAttribute("defaultThreshold") final String s2, @PluginAttribute("onMatch") final String s3, @PluginAttribute("onMismatch") final String s4) {
        final Filter.Result result = Filter.Result.toResult(s3);
        final Filter.Result result2 = Filter.Result.toResult(s4);
        final HashMap<String, Level> hashMap = new HashMap<String, Level>();
        while (0 < array.length) {
            final KeyValuePair keyValuePair = array[0];
            hashMap.put(keyValuePair.getKey(), Level.toLevel(keyValuePair.getValue()));
            int n = 0;
            ++n;
        }
        return new DynamicThresholdFilter(s, hashMap, Level.toLevel(s2, Level.ERROR), result, result2);
    }
}
