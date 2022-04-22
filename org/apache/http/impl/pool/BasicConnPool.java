package org.apache.http.impl.pool;

import org.apache.http.annotation.*;
import java.util.concurrent.atomic.*;
import org.apache.http.params.*;
import org.apache.http.config.*;
import org.apache.http.*;
import org.apache.http.pool.*;

@ThreadSafe
public class BasicConnPool extends AbstractConnPool
{
    private static final AtomicLong COUNTER;
    
    public BasicConnPool(final ConnFactory connFactory) {
        super(connFactory, 2, 20);
    }
    
    @Deprecated
    public BasicConnPool(final HttpParams httpParams) {
        super(new BasicConnFactory(httpParams), 2, 20);
    }
    
    public BasicConnPool(final SocketConfig socketConfig, final ConnectionConfig connectionConfig) {
        super(new BasicConnFactory(socketConfig, connectionConfig), 2, 20);
    }
    
    public BasicConnPool() {
        super(new BasicConnFactory(SocketConfig.DEFAULT, ConnectionConfig.DEFAULT), 2, 20);
    }
    
    protected BasicPoolEntry createEntry(final HttpHost httpHost, final HttpClientConnection httpClientConnection) {
        return new BasicPoolEntry(Long.toString(BasicConnPool.COUNTER.getAndIncrement()), httpHost, httpClientConnection);
    }
    
    @Override
    protected PoolEntry createEntry(final Object o, final Object o2) {
        return this.createEntry((HttpHost)o, (HttpClientConnection)o2);
    }
    
    static {
        COUNTER = new AtomicLong();
    }
}
