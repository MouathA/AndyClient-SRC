package org.apache.http.concurrent;

public interface FutureCallback
{
    void completed(final Object p0);
    
    void failed(final Exception p0);
    
    void cancelled();
}
