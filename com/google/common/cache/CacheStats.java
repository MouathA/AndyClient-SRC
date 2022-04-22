package com.google.common.cache;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;

@Beta
@GwtCompatible
public final class CacheStats
{
    private final long hitCount;
    private final long missCount;
    private final long loadSuccessCount;
    private final long loadExceptionCount;
    private final long totalLoadTime;
    private final long evictionCount;
    
    public CacheStats(final long hitCount, final long missCount, final long loadSuccessCount, final long loadExceptionCount, final long totalLoadTime, final long evictionCount) {
        Preconditions.checkArgument(hitCount >= 0L);
        Preconditions.checkArgument(missCount >= 0L);
        Preconditions.checkArgument(loadSuccessCount >= 0L);
        Preconditions.checkArgument(loadExceptionCount >= 0L);
        Preconditions.checkArgument(totalLoadTime >= 0L);
        Preconditions.checkArgument(evictionCount >= 0L);
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.loadSuccessCount = loadSuccessCount;
        this.loadExceptionCount = loadExceptionCount;
        this.totalLoadTime = totalLoadTime;
        this.evictionCount = evictionCount;
    }
    
    public long requestCount() {
        return this.hitCount + this.missCount;
    }
    
    public long hitCount() {
        return this.hitCount;
    }
    
    public double hitRate() {
        final long requestCount = this.requestCount();
        return (requestCount == 0L) ? 1.0 : (this.hitCount / (double)requestCount);
    }
    
    public long missCount() {
        return this.missCount;
    }
    
    public double missRate() {
        final long requestCount = this.requestCount();
        return (requestCount == 0L) ? 0.0 : (this.missCount / (double)requestCount);
    }
    
    public long loadCount() {
        return this.loadSuccessCount + this.loadExceptionCount;
    }
    
    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }
    
    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }
    
    public double loadExceptionRate() {
        final long n = this.loadSuccessCount + this.loadExceptionCount;
        return (n == 0L) ? 0.0 : (this.loadExceptionCount / (double)n);
    }
    
    public long totalLoadTime() {
        return this.totalLoadTime;
    }
    
    public double averageLoadPenalty() {
        final long n = this.loadSuccessCount + this.loadExceptionCount;
        return (n == 0L) ? 0.0 : (this.totalLoadTime / (double)n);
    }
    
    public long evictionCount() {
        return this.evictionCount;
    }
    
    public CacheStats minus(final CacheStats cacheStats) {
        return new CacheStats(Math.max(0L, this.hitCount - cacheStats.hitCount), Math.max(0L, this.missCount - cacheStats.missCount), Math.max(0L, this.loadSuccessCount - cacheStats.loadSuccessCount), Math.max(0L, this.loadExceptionCount - cacheStats.loadExceptionCount), Math.max(0L, this.totalLoadTime - cacheStats.totalLoadTime), Math.max(0L, this.evictionCount - cacheStats.evictionCount));
    }
    
    public CacheStats plus(final CacheStats cacheStats) {
        return new CacheStats(this.hitCount + cacheStats.hitCount, this.missCount + cacheStats.missCount, this.loadSuccessCount + cacheStats.loadSuccessCount, this.loadExceptionCount + cacheStats.loadExceptionCount, this.totalLoadTime + cacheStats.totalLoadTime, this.evictionCount + cacheStats.evictionCount);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.hitCount, this.missCount, this.loadSuccessCount, this.loadExceptionCount, this.totalLoadTime, this.evictionCount);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof CacheStats) {
            final CacheStats cacheStats = (CacheStats)o;
            return this.hitCount == cacheStats.hitCount && this.missCount == cacheStats.missCount && this.loadSuccessCount == cacheStats.loadSuccessCount && this.loadExceptionCount == cacheStats.loadExceptionCount && this.totalLoadTime == cacheStats.totalLoadTime && this.evictionCount == cacheStats.evictionCount;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }
}
