package io.netty.util.internal;

import io.netty.util.concurrent.*;
import io.netty.util.*;

public final class PendingWrite
{
    private static final Recycler RECYCLER;
    private final Recycler.Handle handle;
    private Object msg;
    private Promise promise;
    
    public static PendingWrite newInstance(final Object msg, final Promise promise) {
        final PendingWrite pendingWrite = (PendingWrite)PendingWrite.RECYCLER.get();
        pendingWrite.msg = msg;
        pendingWrite.promise = promise;
        return pendingWrite;
    }
    
    private PendingWrite(final Recycler.Handle handle) {
        this.handle = handle;
    }
    
    public boolean recycle() {
        this.msg = null;
        this.promise = null;
        return PendingWrite.RECYCLER.recycle(this, this.handle);
    }
    
    public boolean failAndRecycle(final Throwable failure) {
        ReferenceCountUtil.release(this.msg);
        if (this.promise != null) {
            this.promise.setFailure(failure);
        }
        return this.recycle();
    }
    
    public boolean successAndRecycle() {
        if (this.promise != null) {
            this.promise.setSuccess(null);
        }
        return this.recycle();
    }
    
    public Object msg() {
        return this.msg;
    }
    
    public Promise promise() {
        return this.promise;
    }
    
    public Promise recycleAndGet() {
        final Promise promise = this.promise;
        this.recycle();
        return promise;
    }
    
    PendingWrite(final Recycler.Handle handle, final PendingWrite$1 recycler) {
        this(handle);
    }
    
    static {
        RECYCLER = new Recycler() {
            @Override
            protected PendingWrite newObject(final Handle handle) {
                return new PendingWrite(handle, null);
            }
            
            @Override
            protected Object newObject(final Handle handle) {
                return this.newObject(handle);
            }
        };
    }
}
