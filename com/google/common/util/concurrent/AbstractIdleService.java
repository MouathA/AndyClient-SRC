package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.*;

@Beta
public abstract class AbstractIdleService implements Service
{
    private final Supplier threadNameSupplier;
    private final Service delegate;
    
    protected AbstractIdleService() {
        this.threadNameSupplier = new Supplier() {
            final AbstractIdleService this$0;
            
            @Override
            public String get() {
                return this.this$0.serviceName() + " " + this.this$0.state();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        };
        this.delegate = new AbstractService() {
            final AbstractIdleService this$0;
            
            @Override
            protected final void doStart() {
                MoreExecutors.renamingDecorator(this.this$0.executor(), AbstractIdleService.access$000(this.this$0)).execute(new Runnable() {
                    final AbstractIdleService$2 this$1;
                    
                    @Override
                    public void run() {
                        this.this$1.this$0.startUp();
                        this.this$1.notifyStarted();
                    }
                });
            }
            
            @Override
            protected final void doStop() {
                MoreExecutors.renamingDecorator(this.this$0.executor(), AbstractIdleService.access$000(this.this$0)).execute(new Runnable() {
                    final AbstractIdleService$2 this$1;
                    
                    @Override
                    public void run() {
                        this.this$1.this$0.shutDown();
                        this.this$1.notifyStopped();
                    }
                });
            }
        };
    }
    
    protected abstract void startUp() throws Exception;
    
    protected abstract void shutDown() throws Exception;
    
    protected Executor executor() {
        return new Executor() {
            final AbstractIdleService this$0;
            
            @Override
            public void execute(final Runnable runnable) {
                MoreExecutors.newThread((String)AbstractIdleService.access$000(this.this$0).get(), runnable).start();
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
    
    static Supplier access$000(final AbstractIdleService abstractIdleService) {
        return abstractIdleService.threadNameSupplier;
    }
}
