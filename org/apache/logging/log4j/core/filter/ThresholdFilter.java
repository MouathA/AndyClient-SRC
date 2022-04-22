package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "ThresholdFilter", category = "Core", elementType = "filter", printObject = true)
public final class ThresholdFilter extends AbstractFilter
{
    private final Level level;
    
    private ThresholdFilter(final Level level, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        this.level = level;
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
        return level.isAtLeastAsSpecificAs(this.level) ? this.onMatch : this.onMismatch;
    }
    
    @Override
    public String toString() {
        return this.level.toString();
    }
    
    @PluginFactory
    public static ThresholdFilter createFilter(@PluginAttribute("level") final String s, @PluginAttribute("onMatch") final String s2, @PluginAttribute("onMismatch") final String s3) {
        return new ThresholdFilter(Level.toLevel(s, Level.ERROR), Filter.Result.toResult(s2, Filter.Result.NEUTRAL), Filter.Result.toResult(s3, Filter.Result.DENY));
    }
}
