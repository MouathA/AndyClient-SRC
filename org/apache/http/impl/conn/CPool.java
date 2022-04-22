package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import org.apache.commons.logging.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import org.apache.http.pool.*;

@ThreadSafe
class CPool extends AbstractConnPool
{
    private static final AtomicLong COUNTER;
    private final Log log;
    private final long timeToLive;
    private final TimeUnit tunit;
    
    public CPool(final ConnFactory connFactory, final int n, final int n2, final long timeToLive, final TimeUnit tunit) {
        super(connFactory, n, n2);
        this.log = LogFactory.getLog(CPool.class);
        this.timeToLive = timeToLive;
        this.tunit = tunit;
    }
    
    protected CPoolEntry createEntry(final HttpRoute httpRoute, final ManagedHttpClientConnection managedHttpClientConnection) {
        return new CPoolEntry(this.log, Long.toString(CPool.COUNTER.getAndIncrement()), httpRoute, managedHttpClientConnection, this.timeToLive, this.tunit);
    }
    
    @Override
    protected PoolEntry createEntry(final Object o, final Object o2) {
        return this.createEntry((HttpRoute)o, (ManagedHttpClientConnection)o2);
    }
    
    static {
        COUNTER = new AtomicLong();
    }
}
