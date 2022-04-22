package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public abstract class AbstractCheckedFuture extends SimpleForwardingListenableFuture implements CheckedFuture
{
    protected AbstractCheckedFuture(final ListenableFuture listenableFuture) {
        super(listenableFuture);
    }
    
    protected abstract Exception mapException(final Exception p0);
    
    @Override
    public Object checkedGet() throws Exception {
        return this.get();
    }
    
    @Override
    public Object checkedGet(final long n, final TimeUnit timeUnit) throws TimeoutException, Exception {
        return this.get(n, timeUnit);
    }
}
