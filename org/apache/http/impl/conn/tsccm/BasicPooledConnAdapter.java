package org.apache.http.impl.conn.tsccm;

import org.apache.http.impl.conn.*;
import org.apache.http.conn.*;

@Deprecated
public class BasicPooledConnAdapter extends AbstractPooledConnAdapter
{
    protected BasicPooledConnAdapter(final ThreadSafeClientConnManager threadSafeClientConnManager, final AbstractPoolEntry abstractPoolEntry) {
        super(threadSafeClientConnManager, abstractPoolEntry);
        this.markReusable();
    }
    
    @Override
    protected ClientConnectionManager getManager() {
        return super.getManager();
    }
    
    @Override
    protected AbstractPoolEntry getPoolEntry() {
        return super.getPoolEntry();
    }
    
    @Override
    protected void detach() {
        super.detach();
    }
}
