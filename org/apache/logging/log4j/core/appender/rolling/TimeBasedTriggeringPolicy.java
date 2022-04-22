package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.config.plugins.*;

@Plugin(name = "TimeBasedTriggeringPolicy", category = "Core", printObject = true)
public final class TimeBasedTriggeringPolicy implements TriggeringPolicy
{
    private long nextRollover;
    private final int interval;
    private final boolean modulate;
    private RollingFileManager manager;
    
    private TimeBasedTriggeringPolicy(final int interval, final boolean modulate) {
        this.interval = interval;
        this.modulate = modulate;
    }
    
    @Override
    public void initialize(final RollingFileManager manager) {
        this.manager = manager;
        this.nextRollover = manager.getPatternProcessor().getNextTime(manager.getFileTime(), this.interval, this.modulate);
    }
    
    @Override
    public boolean isTriggeringEvent(final LogEvent logEvent) {
        if (this.manager.getFileSize() == 0L) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > this.nextRollover) {
            this.nextRollover = this.manager.getPatternProcessor().getNextTime(currentTimeMillis, this.interval, this.modulate);
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "TimeBasedTriggeringPolicy";
    }
    
    @PluginFactory
    public static TimeBasedTriggeringPolicy createPolicy(@PluginAttribute("interval") final String s, @PluginAttribute("modulate") final String s2) {
        return new TimeBasedTriggeringPolicy(Integers.parseInt(s, 1), Boolean.parseBoolean(s2));
    }
}
