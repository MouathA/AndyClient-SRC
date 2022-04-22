package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.logging.*;
import java.util.concurrent.locks.*;
import com.google.common.base.*;
import java.util.concurrent.*;
import javax.annotation.concurrent.*;

@Beta
public abstract class AbstractScheduledService implements Service
{
    private static final Logger logger;
    private final AbstractService delegate;
    
    protected AbstractScheduledService() {
        this.delegate = new AbstractService() {
            private Future runningTask;
            private ScheduledExecutorService executorService;
            private final ReentrantLock lock = new ReentrantLock();
            private final Runnable task = new Runnable() {
                final AbstractScheduledService$1 this$1;
                
                @Override
                public void run() {
                    AbstractScheduledService$1.access$100(this.this$1).lock();
                    this.this$1.this$0.runOneIteration();
                    AbstractScheduledService$1.access$100(this.this$1).unlock();
                }
            };
            final AbstractScheduledService this$0;
            
            @Override
            protected final void doStart() {
                (this.executorService = MoreExecutors.renamingDecorator(this.this$0.executor(), new Supplier() {
                    final AbstractScheduledService$1 this$1;
                    
                    @Override
                    public String get() {
                        return this.this$1.this$0.serviceName() + " " + this.this$1.state();
                    }
                    
                    @Override
                    public Object get() {
                        return this.get();
                    }
                })).execute(new Runnable() {
                    final AbstractScheduledService$1 this$1;
                    
                    @Override
                    public void run() {
                        AbstractScheduledService$1.access$100(this.this$1).lock();
                        this.this$1.this$0.startUp();
                        AbstractScheduledService$1.access$302(this.this$1, this.this$1.this$0.scheduler().schedule(AbstractScheduledService.access$400(this.this$1.this$0), AbstractScheduledService$1.access$500(this.this$1), AbstractScheduledService$1.access$600(this.this$1)));
                        this.this$1.notifyStarted();
                        AbstractScheduledService$1.access$100(this.this$1).unlock();
                    }
                });
            }
            
            @Override
            protected final void doStop() {
                this.runningTask.cancel(false);
                this.executorService.execute(new Runnable() {
                    final AbstractScheduledService$1 this$1;
                    
                    @Override
                    public void run() {
                        AbstractScheduledService$1.access$100(this.this$1).lock();
                        if (this.this$1.state() != State.STOPPING) {
                            AbstractScheduledService$1.access$100(this.this$1).unlock();
                            return;
                        }
                        this.this$1.this$0.shutDown();
                        AbstractScheduledService$1.access$100(this.this$1).unlock();
                        this.this$1.notifyStopped();
                    }
                });
            }
            
            static ReentrantLock access$100(final AbstractScheduledService$1 abstractService) {
                return abstractService.lock;
            }
            
            static Future access$302(final AbstractScheduledService$1 abstractService, final Future runningTask) {
                return abstractService.runningTask = runningTask;
            }
            
            static ScheduledExecutorService access$500(final AbstractScheduledService$1 abstractService) {
                return abstractService.executorService;
            }
            
            static Runnable access$600(final AbstractScheduledService$1 abstractService) {
                return abstractService.task;
            }
        };
    }
    
    protected abstract void runOneIteration() throws Exception;
    
    protected void startUp() throws Exception {
    }
    
    protected void shutDown() throws Exception {
    }
    
    protected abstract Scheduler scheduler();
    
    protected ScheduledExecutorService executor() {
        final ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            final AbstractScheduledService this$0;
            
            @Override
            public Thread newThread(final Runnable runnable) {
                return MoreExecutors.newThread(this.this$0.serviceName(), runnable);
            }
        });
        this.addListener(new Listener(singleThreadScheduledExecutor) {
            final ScheduledExecutorService val$executor;
            final AbstractScheduledService this$0;
            
            @Override
            public void terminated(final State state) {
                this.val$executor.shutdown();
            }
            
            @Override
            public void failed(final State state, final Throwable t) {
                this.val$executor.shutdown();
            }
        }, MoreExecutors.sameThreadExecutor());
        return singleThreadScheduledExecutor;
    }
    
    protected String serviceName() {
        return this.getClass().getSimpleName();
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
    
    static Logger access$200() {
        return AbstractScheduledService.logger;
    }
    
    static AbstractService access$400(final AbstractScheduledService abstractScheduledService) {
        return abstractScheduledService.delegate;
    }
    
    static {
        logger = Logger.getLogger(AbstractScheduledService.class.getName());
    }
    
    @Beta
    public abstract static class CustomScheduler extends Scheduler
    {
        public CustomScheduler() {
            super(null);
        }
        
        @Override
        final Future schedule(final AbstractService abstractService, final ScheduledExecutorService scheduledExecutorService, final Runnable runnable) {
            final ReschedulableCallable reschedulableCallable = new ReschedulableCallable(abstractService, scheduledExecutorService, runnable);
            reschedulableCallable.reschedule();
            return reschedulableCallable;
        }
        
        protected abstract Schedule getNextSchedule() throws Exception;
        
        @Beta
        protected static final class Schedule
        {
            private final long delay;
            private final TimeUnit unit;
            
            public Schedule(final long delay, final TimeUnit timeUnit) {
                this.delay = delay;
                this.unit = (TimeUnit)Preconditions.checkNotNull(timeUnit);
            }
            
            static long access$700(final Schedule schedule) {
                return schedule.delay;
            }
            
            static TimeUnit access$800(final Schedule schedule) {
                return schedule.unit;
            }
        }
        
        private class ReschedulableCallable extends ForwardingFuture implements Callable
        {
            private final Runnable wrappedRunnable;
            private final ScheduledExecutorService executor;
            private final AbstractService service;
            private final ReentrantLock lock;
            @GuardedBy("lock")
            private Future currentFuture;
            final CustomScheduler this$0;
            
            ReschedulableCallable(final CustomScheduler this$0, final AbstractService service, final ScheduledExecutorService executor, final Runnable wrappedRunnable) {
                this.this$0 = this$0;
                this.lock = new ReentrantLock();
                this.wrappedRunnable = wrappedRunnable;
                this.executor = executor;
                this.service = service;
            }
            
            @Override
            public Void call() throws Exception {
                this.wrappedRunnable.run();
                this.reschedule();
                return null;
            }
            
            public void reschedule() {
                this.lock.lock();
                if (this.currentFuture == null || !this.currentFuture.isCancelled()) {
                    final Schedule nextSchedule = this.this$0.getNextSchedule();
                    this.currentFuture = this.executor.schedule((Callable<Object>)this, Schedule.access$700(nextSchedule), Schedule.access$800(nextSchedule));
                }
                this.lock.unlock();
            }
            
            @Override
            public boolean cancel(final boolean b) {
                this.lock.lock();
                final boolean cancel = this.currentFuture.cancel(b);
                this.lock.unlock();
                return cancel;
            }
            
            @Override
            protected Future delegate() {
                throw new UnsupportedOperationException("Only cancel is supported by this future");
            }
            
            @Override
            protected Object delegate() {
                return this.delegate();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        }
    }
    
    public abstract static class Scheduler
    {
        public static Scheduler newFixedDelaySchedule(final long n, final long n2, final TimeUnit timeUnit) {
            return new Scheduler(n, n2, timeUnit) {
                final long val$initialDelay;
                final long val$delay;
                final TimeUnit val$unit;
                
                public Future schedule(final AbstractService abstractService, final ScheduledExecutorService scheduledExecutorService, final Runnable runnable) {
                    return scheduledExecutorService.scheduleWithFixedDelay(runnable, this.val$initialDelay, this.val$delay, this.val$unit);
                }
            };
        }
        
        public static Scheduler newFixedRateSchedule(final long n, final long n2, final TimeUnit timeUnit) {
            return new Scheduler(n, n2, timeUnit) {
                final long val$initialDelay;
                final long val$period;
                final TimeUnit val$unit;
                
                public Future schedule(final AbstractService abstractService, final ScheduledExecutorService scheduledExecutorService, final Runnable runnable) {
                    return scheduledExecutorService.scheduleAtFixedRate(runnable, this.val$initialDelay, this.val$period, this.val$unit);
                }
            };
        }
        
        abstract Future schedule(final AbstractService p0, final ScheduledExecutorService p1, final Runnable p2);
        
        private Scheduler() {
        }
        
        Scheduler(final AbstractScheduledService$1 abstractService) {
            this();
        }
    }
}
