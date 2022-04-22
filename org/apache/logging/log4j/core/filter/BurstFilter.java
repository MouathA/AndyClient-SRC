package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.*;
import java.util.concurrent.*;

@Plugin(name = "BurstFilter", category = "Core", elementType = "filter", printObject = true)
public final class BurstFilter extends AbstractFilter
{
    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int DEFAULT_RATE = 10;
    private static final int DEFAULT_RATE_MULTIPLE = 100;
    private static final int HASH_SHIFT = 32;
    private final Level level;
    private final long burstInterval;
    private final DelayQueue history;
    private final Queue available;
    
    private BurstFilter(final Level level, final float n, final long n2, final Filter.Result result, final Filter.Result result2) {
        super(result, result2);
        this.history = new DelayQueue();
        this.available = new ConcurrentLinkedQueue();
        this.level = level;
        this.burstInterval = (long)(1.0E9f * (n2 / n));
        while (0 < n2) {
            this.available.add(new LogDelay());
            int n3 = 0;
            ++n3;
        }
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
        if (!this.level.isAtLeastAsSpecificAs(level)) {
            return this.onMatch;
        }
        for (LogDelay logDelay = this.history.poll(); logDelay != null; logDelay = this.history.poll()) {
            this.available.add(logDelay);
        }
        final LogDelay logDelay2 = this.available.poll();
        if (logDelay2 != null) {
            logDelay2.setDelay(this.burstInterval);
            this.history.add(logDelay2);
            return this.onMatch;
        }
        return this.onMismatch;
    }
    
    public int getAvailable() {
        return this.available.size();
    }
    
    public void clear() {
        for (final LogDelay logDelay : this.history) {
            this.history.remove(logDelay);
            this.available.add(logDelay);
        }
    }
    
    @Override
    public String toString() {
        return "level=" + this.level.toString() + ", interval=" + this.burstInterval + ", max=" + this.history.size();
    }
    
    @PluginFactory
    public static BurstFilter createFilter(@PluginAttribute("level") final String s, @PluginAttribute("rate") final String s2, @PluginAttribute("maxBurst") final String s3, @PluginAttribute("onMatch") final String s4, @PluginAttribute("onMismatch") final String s5) {
        final Filter.Result result = Filter.Result.toResult(s4, Filter.Result.NEUTRAL);
        final Filter.Result result2 = Filter.Result.toResult(s5, Filter.Result.DENY);
        final Level level = Level.toLevel(s, Level.WARN);
        float n = (s2 == null) ? 10.0f : Float.parseFloat(s2);
        if (n <= 0.0f) {
            n = 10.0f;
        }
        return new BurstFilter(level, n, (s3 == null) ? ((long)(n * 100.0f)) : Long.parseLong(s3), result, result2);
    }
    
    private class LogDelay implements Delayed
    {
        private long expireTime;
        final BurstFilter this$0;
        
        public LogDelay(final BurstFilter this$0) {
            this.this$0 = this$0;
        }
        
        public void setDelay(final long n) {
            this.expireTime = n + System.nanoTime();
        }
        
        @Override
        public long getDelay(final TimeUnit timeUnit) {
            return timeUnit.convert(this.expireTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }
        
        @Override
        public int compareTo(final Delayed delayed) {
            if (this.expireTime < ((LogDelay)delayed).expireTime) {
                return -1;
            }
            if (this.expireTime > ((LogDelay)delayed).expireTime) {
                return 1;
            }
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o != null && this.getClass() == o.getClass() && this.expireTime == ((LogDelay)o).expireTime);
        }
        
        @Override
        public int hashCode() {
            return (int)(this.expireTime ^ this.expireTime >>> 32);
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Delayed)o);
        }
    }
}
