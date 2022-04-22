package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import java.lang.management.*;
import org.apache.logging.log4j.core.*;

@Plugin(name = "RelativeTimePatternConverter", category = "Converter")
@ConverterKeys({ "r", "relative" })
public class RelativeTimePatternConverter extends LogEventPatternConverter
{
    private long lastTimestamp;
    private final long startTime;
    private String relative;
    
    public RelativeTimePatternConverter() {
        super("Time", "time");
        this.lastTimestamp = Long.MIN_VALUE;
        this.startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
    }
    
    public static RelativeTimePatternConverter newInstance(final String[] array) {
        return new RelativeTimePatternConverter();
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final long millis = logEvent.getMillis();
        // monitorenter(this)
        if (millis != this.lastTimestamp) {
            this.lastTimestamp = millis;
            this.relative = Long.toString(millis - this.startTime);
        }
        // monitorexit(this)
        sb.append(this.relative);
    }
}
