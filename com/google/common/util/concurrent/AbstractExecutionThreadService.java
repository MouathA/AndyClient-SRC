package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.logging.*;
import com.google.common.base.*;
import java.util.concurrent.*;

@Beta
public abstract class AbstractExecutionThreadService implements Service
{
    private static final Logger logger;
    private final Service delegate;
    
    protected AbstractExecutionThreadService() {
        this.delegate = new AbstractService() {
            final AbstractExecutionThreadService this$0;
            
            @Override
            protected final void doStart() {
                MoreExecutors.renamingDecorator(this.this$0.executor(), new Supplier() {
                    final AbstractExecutionThreadService$1 this$1;
                    
                    @Override
                    public String get() {
                        return this.this$1.this$0.serviceName();
                    }
                    
                    @Override
                    public Object get() {
                        return this.get();
                    }
                }).execute(new Runnable() {
                    final AbstractExecutionThreadService$1 this$1;
                    
                    @Override
                    public void run() {
                        this.this$1.this$0.startUp();
                        this.this$1.notifyStarted();
                        if (this.this$1.isRunning()) {
                            this.this$1.this$0.run();
                        }
                        this.this$1.this$0.shutDown();
                        this.this$1.notifyStopped();
                    }
                });
            }
            
            @Override
            protected void doStop() {
                this.this$0.triggerShutdown();
            }
        };
    }
    
    protected void startUp() throws Exception {
    }
    
    protected abstract void run() throws Exception;
    
    protected void shutDown() throws Exception {
    }
    
    protected void triggerShutdown() {
    }
    
    protected Executor executor() {
        return new Executor() {
            final AbstractExecutionThreadService this$0;
            
            @Override
            public void execute(final Runnable runnable) {
                MoreExecutors.newThread(this.this$0.serviceName(), runnable).start();
            }
        };
    }
    
    @Override
    public String toString() {
        return this.serviceName() + " [" + this.state() + "]";
    }
    
    @Override
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }
    
    @Override
    public final State state() {
        return this.delegate.state();
    }
    
    @Override
    public final void addListener(final Listener listener, final Executor executor) {
        this.delegate.addListener(listener, executor);
    }
    
    @Override
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }
    
    @Override
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }
    
    @Override
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }
    
    @Override
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }
    
    @Override
    public final void awaitRunning(final long n, final TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(n, timeUnit);
    }
    
    @Override
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }
    
    @Override
    public final void awaitTerminated(final long n, final TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(n, timeUnit);
    }
    
    protected String serviceName() {
        return this.getClass().getSimpleName();
    }
    
    static Logger access$000() {
        return AbstractExecutionThreadService.logger;
    }
    
    static {
        logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    }
}
