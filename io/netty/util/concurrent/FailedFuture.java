package io.netty.util.concurrent;

import io.netty.util.internal.*;

public final class FailedFuture extends CompleteFuture
{
    private final Throwable cause;
    
    public FailedFuture(final EventExecutor eventExecutor, final Throwable cause) {
        super(eventExecutor);
        if (cause == null) {
            throw new NullPointerException("cause");
        }
        this.cause = cause;
    }
    
    @Override
    public Throwable cause() {
        return this.cause;
    }
    
    @Override
    public boolean isSuccess() {
        return false;
    }
    
    @Override
    public Future sync() {
        PlatformDependent.throwException(this.cause);
        return this;
    }
    
    @Override
    public Future syncUninterruptibly() {
        PlatformDependent.throwException(this.cause);
        return this;
    }
    
    @Override
    public Object getNow() {
        return null;
    }
}
