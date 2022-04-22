package org.apache.commons.lang3.concurrent;

import java.util.concurrent.*;

public class CallableBackgroundInitializer extends BackgroundInitializer
{
    private final Callable callable;
    
    public CallableBackgroundInitializer(final Callable callable) {
        this.checkCallable(callable);
        this.callable = callable;
    }
    
    public CallableBackgroundInitializer(final Callable callable, final ExecutorService executorService) {
        super(executorService);
        this.checkCallable(callable);
        this.callable = callable;
    }
    
    @Override
    protected Object initialize() throws Exception {
        return this.callable.call();
    }
    
    private void checkCallable(final Callable callable) {
        if (callable == null) {
            throw new IllegalArgumentException("Callable must not be null!");
        }
    }
}
