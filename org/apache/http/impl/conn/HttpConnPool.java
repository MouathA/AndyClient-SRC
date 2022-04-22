package org.apache.http.impl.conn;

import java.util.concurrent.atomic.*;
import org.apache.commons.logging.*;
import java.util.concurrent.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import org.apache.http.pool.*;
import java.io.*;

@Deprecated
class HttpConnPool extends AbstractConnPool
{
    private static final AtomicLong COUNTER;
    private final Log log;
    private final long timeToLive;
    private final TimeUnit tunit;
    
    public HttpConnPool(final Log log, final ClientConnectionOperator clientConnectionOperator, final int n, final int n2, final long timeToLive, final TimeUnit tunit) {
        super(new InternalConnFactory(clientConnectionOperator), n, n2);
        this.log = log;
        this.timeToLive = timeToLive;
        this.tunit = tunit;
    }
    
    protected HttpPoolEntry createEntry(final HttpRoute httpRoute, final OperatedClientConnection operatedClientConnection) {
        return new HttpPoolEntry(this.log, Long.toString(HttpConnPool.COUNTER.getAndIncrement()), httpRoute, operatedClientConnection, this.timeToLive, this.tunit);
    }
    
    @Override
    protected PoolEntry createEntry(final Object o, final Object o2) {
        return this.createEntry((HttpRoute)o, (OperatedClientConnection)o2);
    }
    
    static {
        COUNTER = new AtomicLong();
    }
    
    static class InternalConnFactory implements ConnFactory
    {
        private final ClientConnectionOperator connOperator;
        
        InternalConnFactory(final ClientConnectionOperator connOperator) {
            this.connOperator = connOperator;
        }
        
        public OperatedClientConnection create(final HttpRoute httpRoute) throws IOException {
            return this.connOperator.createConnection();
        }
        
        public Object create(final Object o) throws IOException {
            return this.create((HttpRoute)o);
        }
    }
}
