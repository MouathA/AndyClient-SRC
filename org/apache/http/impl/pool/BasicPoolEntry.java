package org.apache.http.impl.pool;

import org.apache.http.pool.*;
import org.apache.http.annotation.*;
import org.apache.http.*;

@ThreadSafe
public class BasicPoolEntry extends PoolEntry
{
    public BasicPoolEntry(final String s, final HttpHost httpHost, final HttpClientConnection httpClientConnection) {
        super(s, httpHost, httpClientConnection);
    }
    
    @Override
    public void close() {
        ((HttpClientConnection)this.getConnection()).close();
    }
    
    @Override
    public boolean isClosed() {
        return !((HttpClientConnection)this.getConnection()).isOpen();
    }
}
