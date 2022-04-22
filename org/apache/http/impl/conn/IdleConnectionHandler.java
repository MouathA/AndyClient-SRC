package org.apache.http.impl.conn;

import org.apache.commons.logging.*;
import org.apache.http.*;
import java.util.concurrent.*;
import java.util.*;

@Deprecated
public class IdleConnectionHandler
{
    private final Log log;
    private final Map connectionToTimes;
    
    public IdleConnectionHandler() {
        this.log = LogFactory.getLog(this.getClass());
        this.connectionToTimes = new HashMap();
    }
    
    public void add(final HttpConnection httpConnection, final long n, final TimeUnit timeUnit) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Adding connection at: " + currentTimeMillis);
        }
        this.connectionToTimes.put(httpConnection, new TimeValues(currentTimeMillis, n, timeUnit));
    }
    
    public boolean remove(final HttpConnection httpConnection) {
        final TimeValues timeValues = this.connectionToTimes.remove(httpConnection);
        if (timeValues == null) {
            this.log.warn("Removing a connection that never existed!");
            return true;
        }
        return System.currentTimeMillis() <= TimeValues.access$000(timeValues);
    }
    
    public void removeAll() {
        this.connectionToTimes.clear();
    }
    
    public void closeIdleConnections(final long n) {
        final long n2 = System.currentTimeMillis() - n;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for connections, idle timeout: " + n2);
        }
        for (final Map.Entry<HttpConnection, V> entry : this.connectionToTimes.entrySet()) {
            final HttpConnection httpConnection = entry.getKey();
            final long access$100 = TimeValues.access$100((TimeValues)entry.getValue());
            if (access$100 <= n2) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing idle connection, connection time: " + access$100);
                }
                httpConnection.close();
            }
        }
    }
    
    public void closeExpiredConnections() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for expired connections, now: " + currentTimeMillis);
        }
        for (final Map.Entry<HttpConnection, V> entry : this.connectionToTimes.entrySet()) {
            final HttpConnection httpConnection = entry.getKey();
            final TimeValues timeValues = (TimeValues)entry.getValue();
            if (TimeValues.access$000(timeValues) <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection, expired @: " + TimeValues.access$000(timeValues));
                }
                httpConnection.close();
            }
        }
    }
    
    private static class TimeValues
    {
        private final long timeAdded;
        private final long timeExpires;
        
        TimeValues(final long timeAdded, final long n, final TimeUnit timeUnit) {
            this.timeAdded = timeAdded;
            if (n > 0L) {
                this.timeExpires = timeAdded + timeUnit.toMillis(n);
            }
            else {
                this.timeExpires = Long.MAX_VALUE;
            }
        }
        
        static long access$000(final TimeValues timeValues) {
            return timeValues.timeExpires;
        }
        
        static long access$100(final TimeValues timeValues) {
            return timeValues.timeAdded;
        }
    }
}
