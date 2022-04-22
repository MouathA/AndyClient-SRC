package com.google.common.util.concurrent;

import com.google.common.base.*;
import javax.annotation.*;
import java.util.concurrent.*;

final class AsyncSettableFuture extends ForwardingListenableFuture
{
    private final NestedFuture nested;
    private final ListenableFuture dereferenced;
    
    public static AsyncSettableFuture create() {
        return new AsyncSettableFuture();
    }
    
    private AsyncSettableFuture() {
        this.nested = new NestedFuture(null);
        this.dereferenced = Futures.dereference(this.nested);
    }
    
    @Override
    protected ListenableFuture delegate() {
        return this.dereferenced;
    }
    
    public boolean setFuture(final ListenableFuture listenableFuture) {
        return this.nested.setFuture((ListenableFuture)Preconditions.checkNotNull(listenableFuture));
    }
    
    public boolean setValue(@Nullable final Object o) {
        return this.setFuture(Futures.immediateFuture(o));
    }
    
    public boolean setException(final Throwable t) {
        return this.setFuture(Futures.immediateFailedFuture(t));
    }
    
    public boolean isSet() {
        return this.nested.isDone();
    }
    
    @Override
    protected Future delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    private static final class NestedFuture extends AbstractFuture
    {
        private NestedFuture() {
        }
        
        boolean setFuture(final ListenableFuture listenableFuture) {
            final boolean set = this.set(listenableFuture);
            if (this.isCancelled()) {
                listenableFuture.cancel(this.wasInterrupted());
            }
            return set;
        }
        
        NestedFuture(final AsyncSettableFuture$1 object) {
            this();
        }
    }
}
