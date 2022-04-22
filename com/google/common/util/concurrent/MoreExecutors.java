package com.google.common.util.concurrent;

import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.concurrent.locks.*;
import java.util.*;
import com.google.common.annotations.*;
import java.util.concurrent.*;

public final class MoreExecutors
{
    private MoreExecutors() {
    }
    
    @Beta
    public static ExecutorService getExitingExecutorService(final ThreadPoolExecutor threadPoolExecutor, final long n, final TimeUnit timeUnit) {
        return new Application().getExitingExecutorService(threadPoolExecutor, n, timeUnit);
    }
    
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, final long n, final TimeUnit timeUnit) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor, n, timeUnit);
    }
    
    @Beta
    public static void addDelayedShutdownHook(final ExecutorService executorService, final long n, final TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(executorService, n, timeUnit);
    }
    
    @Beta
    public static ExecutorService getExitingExecutorService(final ThreadPoolExecutor threadPoolExecutor) {
        return new Application().getExitingExecutorService(threadPoolExecutor);
    }
    
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor);
    }
    
    private static void useDaemonThreadFactory(final ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(threadPoolExecutor.getThreadFactory()).build());
    }
    
    public static ListeningExecutorService sameThreadExecutor() {
        return new SameThreadExecutorService(null);
    }
    
    public static ListeningExecutorService listeningDecorator(final ExecutorService executorService) {
        return (executorService instanceof ListeningExecutorService) ? ((ListeningExecutorService)executorService) : ((executorService instanceof ScheduledExecutorService) ? new ScheduledListeningDecorator((ScheduledExecutorService)executorService) : new ListeningDecorator(executorService));
    }
    
    public static ListeningScheduledExecutorService listeningDecorator(final ScheduledExecutorService scheduledExecutorService) {
        return (scheduledExecutorService instanceof ListeningScheduledExecutorService) ? ((ListeningScheduledExecutorService)scheduledExecutorService) : new ScheduledListeningDecorator(scheduledExecutorService);
    }
    
    static Object invokeAnyImpl(final ListeningExecutorService listeningExecutorService, final Collection collection, final boolean b, long n) throws InterruptedException, ExecutionException, TimeoutException {
        Preconditions.checkNotNull(listeningExecutorService);
        int size = collection.size();
        Preconditions.checkArgument(size > 0);
        final ArrayList arrayListWithCapacity = Lists.newArrayListWithCapacity(size);
        final LinkedBlockingQueue linkedBlockingQueue = Queues.newLinkedBlockingQueue();
        long n2 = b ? System.nanoTime() : 0L;
        final Iterator<Callable> iterator = collection.iterator();
        arrayListWithCapacity.add(submitAndAddQueueListener(listeningExecutorService, iterator.next(), linkedBlockingQueue));
        --size;
        while (true) {
            Future<Object> future = linkedBlockingQueue.poll();
            int n3 = 0;
            if (future == null) {
                if (size > 0) {
                    --size;
                    arrayListWithCapacity.add(submitAndAddQueueListener(listeningExecutorService, iterator.next(), linkedBlockingQueue));
                    ++n3;
                }
                else if (b) {
                    future = linkedBlockingQueue.poll(n, TimeUnit.NANOSECONDS);
                    if (future == null) {
                        throw new TimeoutException();
                    }
                    final long nanoTime = System.nanoTime();
                    n -= nanoTime - n2;
                    n2 = nanoTime;
                }
                else {
                    future = linkedBlockingQueue.take();
                }
            }
            if (future != null) {
                --n3;
                final Object value = future.get();
                final Iterator<Future> iterator2 = (Iterator<Future>)arrayListWithCapacity.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().cancel(true);
                }
                return value;
            }
        }
    }
    
    private static ListenableFuture submitAndAddQueueListener(final ListeningExecutorService listeningExecutorService, final Callable callable, final BlockingQueue blockingQueue) {
        final ListenableFuture submit = listeningExecutorService.submit(callable);
        submit.addListener(new Runnable(blockingQueue, submit) {
            final BlockingQueue val$queue;
            final ListenableFuture val$future;
            
            @Override
            public void run() {
                this.val$queue.add(this.val$future);
            }
        }, sameThreadExecutor());
        return submit;
    }
    
    @Beta
    public static ThreadFactory platformThreadFactory() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokestatic    java/util/concurrent/Executors.defaultThreadFactory:()Ljava/util/concurrent/ThreadFactory;
        //     6: areturn        
        //     7: ldc             "com.google.appengine.api.ThreadManager"
        //     9: invokestatic    java/lang/Class.forName:(Ljava/lang/String;)Ljava/lang/Class;
        //    12: ldc             "currentRequestThreadFactory"
        //    14: iconst_0       
        //    15: anewarray       Ljava/lang/Class;
        //    18: invokevirtual   java/lang/Class.getMethod:(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
        //    21: aconst_null    
        //    22: iconst_0       
        //    23: anewarray       Ljava/lang/Object;
        //    26: invokevirtual   java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
        //    29: checkcast       Ljava/util/concurrent/ThreadFactory;
        //    32: areturn        
        //    33: astore_0       
        //    34: new             Ljava/lang/RuntimeException;
        //    37: dup            
        //    38: ldc             "Couldn't invoke ThreadManager.currentRequestThreadFactory"
        //    40: aload_0        
        //    41: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //    44: athrow         
        //    45: astore_0       
        //    46: new             Ljava/lang/RuntimeException;
        //    49: dup            
        //    50: ldc             "Couldn't invoke ThreadManager.currentRequestThreadFactory"
        //    52: aload_0        
        //    53: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //    56: athrow         
        //    57: astore_0       
        //    58: new             Ljava/lang/RuntimeException;
        //    61: dup            
        //    62: ldc             "Couldn't invoke ThreadManager.currentRequestThreadFactory"
        //    64: aload_0        
        //    65: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //    68: athrow         
        //    69: astore_0       
        //    70: aload_0        
        //    71: invokevirtual   java/lang/reflect/InvocationTargetException.getCause:()Ljava/lang/Throwable;
        //    74: invokestatic    com/google/common/base/Throwables.propagate:(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
        //    77: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static Thread newThread(final String name, final Runnable runnable) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(runnable);
        final Thread thread = platformThreadFactory().newThread(runnable);
        thread.setName(name);
        return thread;
    }
    
    static Executor renamingDecorator(final Executor p0, final Supplier p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     4: pop            
        //     5: aload_1        
        //     6: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     9: pop            
        //    10: ifnonnull       15
        //    13: aload_0        
        //    14: areturn        
        //    15: new             Lcom/google/common/util/concurrent/MoreExecutors$2;
        //    18: dup            
        //    19: aload_0        
        //    20: aload_1        
        //    21: invokespecial   com/google/common/util/concurrent/MoreExecutors$2.<init>:(Ljava/util/concurrent/Executor;Lcom/google/common/base/Supplier;)V
        //    24: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static ExecutorService renamingDecorator(final ExecutorService p0, final Supplier p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     4: pop            
        //     5: aload_1        
        //     6: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     9: pop            
        //    10: ifnonnull       15
        //    13: aload_0        
        //    14: areturn        
        //    15: new             Lcom/google/common/util/concurrent/MoreExecutors$3;
        //    18: dup            
        //    19: aload_0        
        //    20: aload_1        
        //    21: invokespecial   com/google/common/util/concurrent/MoreExecutors$3.<init>:(Ljava/util/concurrent/ExecutorService;Lcom/google/common/base/Supplier;)V
        //    24: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static ScheduledExecutorService renamingDecorator(final ScheduledExecutorService p0, final Supplier p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     4: pop            
        //     5: aload_1        
        //     6: invokestatic    com/google/common/base/Preconditions.checkNotNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     9: pop            
        //    10: ifnonnull       15
        //    13: aload_0        
        //    14: areturn        
        //    15: new             Lcom/google/common/util/concurrent/MoreExecutors$4;
        //    18: dup            
        //    19: aload_0        
        //    20: aload_1        
        //    21: invokespecial   com/google/common/util/concurrent/MoreExecutors$4.<init>:(Ljava/util/concurrent/ScheduledExecutorService;Lcom/google/common/base/Supplier;)V
        //    24: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Beta
    public static boolean shutdownAndAwaitTermination(final ExecutorService executorService, final long n, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        executorService.shutdown();
        final long n2 = TimeUnit.NANOSECONDS.convert(n, timeUnit) / 2L;
        if (!executorService.awaitTermination(n2, TimeUnit.NANOSECONDS)) {
            executorService.shutdownNow();
            executorService.awaitTermination(n2, TimeUnit.NANOSECONDS);
        }
        return executorService.isTerminated();
    }
    
    static void access$000(final ThreadPoolExecutor threadPoolExecutor) {
        useDaemonThreadFactory(threadPoolExecutor);
    }
    
    private static class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService
    {
        final ScheduledExecutorService delegate;
        
        ScheduledListeningDecorator(final ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.delegate = (ScheduledExecutorService)Preconditions.checkNotNull(scheduledExecutorService);
        }
        
        @Override
        public ListenableScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
            final ListenableFutureTask create = ListenableFutureTask.create(runnable, null);
            return new ListenableScheduledTask(create, this.delegate.schedule(create, n, timeUnit));
        }
        
        @Override
        public ListenableScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
            final ListenableFutureTask create = ListenableFutureTask.create(callable);
            return new ListenableScheduledTask(create, this.delegate.schedule(create, n, timeUnit));
        }
        
        @Override
        public ListenableScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
            final NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(neverSuccessfulListenableFutureTask, this.delegate.scheduleAtFixedRate(neverSuccessfulListenableFutureTask, n, n2, timeUnit));
        }
        
        @Override
        public ListenableScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
            final NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask = new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(neverSuccessfulListenableFutureTask, this.delegate.scheduleWithFixedDelay(neverSuccessfulListenableFutureTask, n, n2, timeUnit));
        }
        
        @Override
        public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
            return this.scheduleWithFixedDelay(runnable, n, n2, timeUnit);
        }
        
        @Override
        public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
            return this.scheduleAtFixedRate(runnable, n, n2, timeUnit);
        }
        
        @Override
        public ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
            return this.schedule(callable, n, timeUnit);
        }
        
        @Override
        public ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
            return this.schedule(runnable, n, timeUnit);
        }
        
        private static final class NeverSuccessfulListenableFutureTask extends AbstractFuture implements Runnable
        {
            private final Runnable delegate;
            
            public NeverSuccessfulListenableFutureTask(final Runnable runnable) {
                this.delegate = (Runnable)Preconditions.checkNotNull(runnable);
            }
            
            @Override
            public void run() {
                this.delegate.run();
            }
        }
        
        private static final class ListenableScheduledTask extends SimpleForwardingListenableFuture implements ListenableScheduledFuture
        {
            private final ScheduledFuture scheduledDelegate;
            
            public ListenableScheduledTask(final ListenableFuture listenableFuture, final ScheduledFuture scheduledDelegate) {
                super(listenableFuture);
                this.scheduledDelegate = scheduledDelegate;
            }
            
            @Override
            public boolean cancel(final boolean b) {
                final boolean cancel = super.cancel(b);
                if (cancel) {
                    this.scheduledDelegate.cancel(b);
                }
                return cancel;
            }
            
            @Override
            public long getDelay(final TimeUnit timeUnit) {
                return this.scheduledDelegate.getDelay(timeUnit);
            }
            
            @Override
            public int compareTo(final Delayed delayed) {
                return this.scheduledDelegate.compareTo(delayed);
            }
            
            @Override
            public int compareTo(final Object o) {
                return this.compareTo((Delayed)o);
            }
        }
    }
    
    private static class ListeningDecorator extends AbstractListeningExecutorService
    {
        private final ExecutorService delegate;
        
        ListeningDecorator(final ExecutorService executorService) {
            this.delegate = (ExecutorService)Preconditions.checkNotNull(executorService);
        }
        
        @Override
        public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
            return this.delegate.awaitTermination(n, timeUnit);
        }
        
        @Override
        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }
        
        @Override
        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }
        
        @Override
        public void shutdown() {
            this.delegate.shutdown();
        }
        
        @Override
        public List shutdownNow() {
            return this.delegate.shutdownNow();
        }
        
        @Override
        public void execute(final Runnable runnable) {
            this.delegate.execute(runnable);
        }
    }
    
    private static class SameThreadExecutorService extends AbstractListeningExecutorService
    {
        private final Lock lock;
        private final Condition termination;
        private int runningTasks;
        private boolean shutdown;
        
        private SameThreadExecutorService() {
            this.lock = new ReentrantLock();
            this.termination = this.lock.newCondition();
            this.runningTasks = 0;
            this.shutdown = false;
        }
        
        @Override
        public void execute(final Runnable runnable) {
            this.startTask();
            runnable.run();
            this.endTask();
        }
        
        @Override
        public boolean isShutdown() {
            this.lock.lock();
            final boolean shutdown = this.shutdown;
            this.lock.unlock();
            return shutdown;
        }
        
        @Override
        public void shutdown() {
            this.lock.lock();
            this.shutdown = true;
            this.lock.unlock();
        }
        
        @Override
        public List shutdownNow() {
            this.shutdown();
            return Collections.emptyList();
        }
        
        @Override
        public boolean isTerminated() {
            this.lock.lock();
            final boolean b = this.shutdown && this.runningTasks == 0;
            this.lock.unlock();
            return b;
        }
        
        @Override
        public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
            long n2 = timeUnit.toNanos(n);
            this.lock.lock();
            while (!this.isTerminated()) {
                if (n2 <= 0L) {
                    this.lock.unlock();
                    return false;
                }
                n2 = this.termination.awaitNanos(n2);
            }
            this.lock.unlock();
            return false;
        }
        
        private void startTask() {
            this.lock.lock();
            if (this.isShutdown()) {
                throw new RejectedExecutionException("Executor already shutdown");
            }
            ++this.runningTasks;
            this.lock.unlock();
        }
        
        private void endTask() {
            this.lock.lock();
            --this.runningTasks;
            if (this.isTerminated()) {
                this.termination.signalAll();
            }
            this.lock.unlock();
        }
        
        SameThreadExecutorService(final MoreExecutors$1 runnable) {
            this();
        }
    }
    
    @VisibleForTesting
    static class Application
    {
        final ExecutorService getExitingExecutorService(final ThreadPoolExecutor threadPoolExecutor, final long n, final TimeUnit timeUnit) {
            MoreExecutors.access$000(threadPoolExecutor);
            final ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(threadPoolExecutor);
            this.addDelayedShutdownHook(unconfigurableExecutorService, n, timeUnit);
            return unconfigurableExecutorService;
        }
        
        final ScheduledExecutorService getExitingScheduledExecutorService(final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, final long n, final TimeUnit timeUnit) {
            MoreExecutors.access$000(scheduledThreadPoolExecutor);
            final ScheduledExecutorService unconfigurableScheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);
            this.addDelayedShutdownHook(unconfigurableScheduledExecutorService, n, timeUnit);
            return unconfigurableScheduledExecutorService;
        }
        
        final void addDelayedShutdownHook(final ExecutorService executorService, final long n, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(executorService);
            Preconditions.checkNotNull(timeUnit);
            this.addShutdownHook(MoreExecutors.newThread("DelayedShutdownHook-for-" + executorService, new Runnable(executorService, n, timeUnit) {
                final ExecutorService val$service;
                final long val$terminationTimeout;
                final TimeUnit val$timeUnit;
                final Application this$0;
                
                @Override
                public void run() {
                    this.val$service.shutdown();
                    this.val$service.awaitTermination(this.val$terminationTimeout, this.val$timeUnit);
                }
            }));
        }
        
        final ExecutorService getExitingExecutorService(final ThreadPoolExecutor threadPoolExecutor) {
            return this.getExitingExecutorService(threadPoolExecutor, 120L, TimeUnit.SECONDS);
        }
        
        final ScheduledExecutorService getExitingScheduledExecutorService(final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            return this.getExitingScheduledExecutorService(scheduledThreadPoolExecutor, 120L, TimeUnit.SECONDS);
        }
        
        @VisibleForTesting
        void addShutdownHook(final Thread thread) {
            Runtime.getRuntime().addShutdownHook(thread);
        }
    }
}
