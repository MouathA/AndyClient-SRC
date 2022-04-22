package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.core.*;
import java.util.*;
import java.text.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "TimeFilter", category = "Core", elementType = "filter", printObject = true)
public final class TimeFilter extends AbstractFilter
{
    private static final long HOUR_MS = 3600000L;
    private static final long MINUTE_MS = 60000L;
    private static final long SECOND_MS = 1000L;
    private final long start;
    private final long end;
    private final TimeZone timezone;
    
    private TimeFilter(final long start, final long end, final TimeZone timezone, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        this.start = start;
        this.end = end;
        this.timezone = timezone;
    }
    
    @Override
    public Filter.Result filter(final LogEvent logEvent) {
        final Calendar instance = Calendar.getInstance(this.timezone);
        instance.setTimeInMillis(logEvent.getMillis());
        final long n = instance.get(11) * 3600000L + instance.get(12) * 60000L + instance.get(13) * 1000L + instance.get(14);
        return (n >= this.start && n < this.end) ? this.onMatch : this.onMismatch;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("start=").append(this.start);
        sb.append(", end=").append(this.end);
        sb.append(", timezone=").append(this.timezone.toString());
        return sb.toString();
    }
    
    @PluginFactory
    public static TimeFilter createFilter(@PluginAttribute("start") final String s, @PluginAttribute("end") final String s2, @PluginAttribute("timezone") final String s3, @PluginAttribute("onMatch") final String s4, @PluginAttribute("onMismatch") final String s5) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        long time = 0L;
        if (s != null) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            time = simpleDateFormat.parse(s).getTime();
        }
        long time2 = Long.MAX_VALUE;
        if (s2 != null) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            time2 = simpleDateFormat.parse(s2).getTime();
        }
        return new TimeFilter(time, time2, (s3 == null) ? TimeZone.getDefault() : TimeZone.getTimeZone(s3), Filter.Result.toResult(s4, Filter.Result.NEUTRAL), Filter.Result.toResult(s5, Filter.Result.DENY));
    }
}
