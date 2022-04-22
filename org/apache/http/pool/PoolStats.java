package org.apache.http.pool;

import org.apache.http.annotation.*;

@Immutable
public class PoolStats
{
    private final int leased;
    private final int pending;
    private final int available;
    private final int max;
    
    public PoolStats(final int leased, final int pending, final int available, final int max) {
        this.leased = leased;
        this.pending = pending;
        this.available = available;
        this.max = max;
    }
    
    public int getLeased() {
        return this.leased;
    }
    
    public int getPending() {
        return this.pending;
    }
    
    public int getAvailable() {
        return this.available;
    }
    
    public int getMax() {
        return this.max;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[leased: ");
        sb.append(this.leased);
        sb.append("; pending: ");
        sb.append(this.pending);
        sb.append("; available: ");
        sb.append(this.available);
        sb.append("; max: ");
        sb.append(this.max);
        sb.append("]");
        return sb.toString();
    }
}
