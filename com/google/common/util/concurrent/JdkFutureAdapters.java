package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

@Beta
public final class JdkFutureAdapters
{
    public static ListenableFuture listenInPoolThread(final Future future) {
        if (future instanceof ListenableFuture) {
            return (ListenableFuture)future;
        }
        return new ListenableFutureAdapter(future);
    }
    
    public static ListenableFuture listenInPoolThread(final Future future, final Executor executor) {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture) {
            return (ListenableFuture)future;
        }
        return new ListenableFutureAdapter(future, executor);
    }
    
    private JdkFutureAdapters() {
    }
    
    private static class ListenableFutureAdapter extends ForwardingFuture implements ListenableFuture
    {
        private static final ThreadFactory threadFactory;
        private static final Executor defaultAdapterExecutor;
        private final Executor adapterExecutor;
        private final ExecutionList executionList;
        private final AtomicBoolean hasListeners;
        private final Future delegate;
        
        ListenableFutureAdapter(final Future future) {
            this(future, ListenableFutureAdapter.defaultAdapterExecutor);
        }
        
        ListenableFutureAdapter(final Future future, final Executor executor) {
            this.executionList = new ExecutionList();
            this.hasListeners = new AtomicBoolean(false);
            this.delegate = (Future)Preconditions.checkNotNull(future);
            this.adapterExecutor = (Executor)Preconditions.checkNotNull(executor);
        }
        
        @Override
        protected Future delegate() {
            return this.delegate;
        }
        
        @Override
        public void addListener(final Runnable runnable, final Executor executor) {
            this.executionList.add(runnable, executor);
            if (this.hasListeners.compareAndSet(false, true)) {
                if (this.delegate.isDone()) {
                    this.executionList.execute();
                    return;
                }
                this.adapterExecutor.execute(new Runnable() {
                    final ListenableFutureAdapter this$0;
                    
                    @Override
                    public void run() {
                        Uninterruptibles.getUninterruptibly(ListenableFutureAdapter.access$000(this.this$0));
                        ListenableFutureAdapter.access$100(this.this$0).execute();
                    }
                });
            }
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        static Future access$000(final ListenableFutureAdapter listenableFutureAdapter) {
            return listenableFutureAdapter.delegate;
        }
        
        static ExecutionList access$100(final ListenableFutureAdapter listenableFutureAdapter) {
            return listenableFutureAdapter.executionList;
        }
        
        static {
            threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ListenableFutureAdapter-thread-%d").build();
            defaultAdapterExecutor = Executors.newCachedThreadPool(ListenableFutureAdapter.threadFactory);
        }
    }
}
