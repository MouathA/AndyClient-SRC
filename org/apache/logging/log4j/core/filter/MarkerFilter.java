package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "MarkerFilter", category = "Core", elementType = "filter", printObject = true)
public final class MarkerFilter extends AbstractFilter
{
    private final String name;
    
    private MarkerFilter(final String name, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        this.name = name;
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final String s, final Object... array) {
        return this.filter(marker);
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Object o, final Throwable t) {
        return this.filter(marker);
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        return this.filter(marker);
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        return this.filter(logEvent.getMarker());
    }
    
    private Filter.Result filter(final Marker marker) {
        return (marker != null && marker.isInstanceOf(this.name)) ? this.onMatch : this.onMismatch;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @PluginFactory
    public static MarkerFilter createFilter(@PluginAttribute("marker") final String s, @PluginAttribute("onMatch") final String s2, @PluginAttribute("onMismatch") final String s3) {
        if (s == null) {
            MarkerFilter.LOGGER.error("A marker must be provided for MarkerFilter");
            return null;
        }
        return new MarkerFilter(s, Filter.Result.toResult(s2), Filter.Result.toResult(s3));
    }
}
