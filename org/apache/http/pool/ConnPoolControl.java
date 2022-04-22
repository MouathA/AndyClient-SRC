package org.apache.http.pool;

public interface ConnPoolControl
{
    void setMaxTotal(final int p0);
    
    int getMaxTotal();
    
    void setDefaultMaxPerRoute(final int p0);
    
    int getDefaultMaxPerRoute();
    
    void setMaxPerRoute(final Object p0, final int p1);
    
    int getMaxPerRoute(final Object p0);
    
    PoolStats getTotalStats();
    
    PoolStats getStats(final Object p0);
}
