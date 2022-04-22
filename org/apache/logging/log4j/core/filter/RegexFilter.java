package org.apache.logging.log4j.core.filter;

import java.util.regex.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "RegexFilter", category = "Core", elementType = "filter", printObject = true)
public final class RegexFilter extends AbstractFilter
{
    private final Pattern pattern;
    private final boolean useRawMessage;
    
    private RegexFilter(final boolean useRawMessage, final Pattern pattern, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        this.pattern = pattern;
        this.useRawMessage = useRawMessage;
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final String s, final Object... array) {
        return this.filter(s);
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Object o, final Throwable t) {
        if (o == null) {
            return this.onMismatch;
        }
        return this.filter(o.toString());
    }
    
    @Override
    public Filter.Result filter(final Logger logger, final Level level, final Marker marker, final Message message, final Throwable t) {
        if (message == null) {
            return this.onMismatch;
        }
        return this.filter(this.useRawMessage ? message.getFormat() : message.getFormattedMessage());
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        return this.filter(this.useRawMessage ? logEvent.getMessage().getFormat() : logEvent.getMessage().getFormattedMessage());
    }
    
    private Filter.Result filter(final String s) {
        if (s == null) {
            return this.onMismatch;
        }
        return this.pattern.matcher(s).matches() ? this.onMatch : this.onMismatch;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("useRaw=").append(this.useRawMessage);
        sb.append(", pattern=").append(this.pattern.toString());
        return sb.toString();
    }
    
    @PluginFactory
    public static RegexFilter createFilter(@PluginAttribute("regex") final String s, @PluginAttribute("useRawMsg") final String s2, @PluginAttribute("onMatch") final String s3, @PluginAttribute("onMismatch") final String s4) {
        if (s == null) {
            RegexFilter.LOGGER.error("A regular expression must be provided for RegexFilter");
            return null;
        }
        return new RegexFilter(Boolean.parseBoolean(s2), Pattern.compile(s), Filter.Result.toResult(s3), Filter.Result.toResult(s4));
    }
}
