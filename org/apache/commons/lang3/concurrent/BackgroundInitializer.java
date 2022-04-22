package org.apache.commons.lang3.concurrent;

import java.util.concurrent.*;

public abstract class BackgroundInitializer implements ConcurrentInitializer
{
    private ExecutorService externalExecutor;
    private ExecutorService executor;
    private Future future;
    
    protected BackgroundInitializer() {
        this(null);
    }
    
    protected BackgroundInitializer(final ExecutorService externalExecutor) {
        this.setExternalExecutor(externalExecutor);
    }
    
    public final synchronized ExecutorService getExternalExecutor() {
        return this.externalExecutor;
    }
    
    public final synchronized void setExternalExecutor(final ExecutorService externalExecutor) {
        if (this != null) {
            throw new IllegalStateException("Cannot set ExecutorService after start()!");
        }
        this.externalExecutor = externalExecutor;
    }
    
    public synchronized boolean start() {
        if (this != null) {
            this.executor = this.getExternalExecutor();
            ExecutorService executorService;
            if (this.executor == null) {
                executorService = (this.executor = this.createExecutor());
            }
            else {
                executorService = null;
            }
            this.future = this.executor.submit((Callable<Object>)this.createTask(executorService));
            return true;
        }
        return false;
    }
    
    @Override
    public Object get() throws ConcurrentException {
        return this.getFuture().get();
    }
    
    public synchronized Future getFuture() {
        if (this.future == null) {
            throw new IllegalStateException("start() must be called first!");
        }
        return this.future;
    }
    
    protected final synchronized ExecutorService getActiveExecutor() {
        return this.executor;
    }
    
    protected int getTaskCount() {
        return 1;
    }
    
    protected abstract Object initialize() throws Exception;
    
    private Callable createTask(final ExecutorService executorService) {
        return new InitializationTask(executorService);
    }
    
    private ExecutorService createExecutor() {
        return Executors.newFixedThreadPool(this.getTaskCount());
    }
    
    private class InitializationTask implements Callable
    {
        private final ExecutorService execFinally;
        final BackgroundInitializer this$0;
        
        public InitializationTask(final BackgroundInitializer this$0, final ExecutorService execFinally) {
            this.this$0 = this$0;
            this.execFinally = execFinally;
        }
        
        @Override
        public Object call() throws Exception {
            final Object initialize = this.this$0.initialize();
            if (this.execFinally != null) {
                this.execFinally.shutdown();
            }
            return initialize;
        }
    }
}
