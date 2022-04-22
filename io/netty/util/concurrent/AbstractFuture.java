package io.netty.util.concurrent;

import java.util.concurrent.*;

public abstract class AbstractFuture implements Future
{
    @Override
    public Object get() throws InterruptedException, ExecutionException {
        this.await();
        final Throwable cause = this.cause();
        if (cause == null) {
            return this.getNow();
        }
        throw new ExecutionException(cause);
    }
    
    @Override
    public Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!this.await(n, timeUnit)) {
            throw new TimeoutException();
        }
        final Throwable cause = this.cause();
        if (cause == null) {
            return this.getNow();
        }
        throw new ExecutionException(cause);
    }
}
