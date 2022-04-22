package io.netty.util.concurrent;

import io.netty.util.*;
import io.netty.util.internal.logging.*;
import java.util.concurrent.*;
import io.netty.util.internal.*;
import java.util.*;

public class DefaultPromise extends AbstractFuture implements Promise
{
    private static final InternalLogger logger;
    private static final InternalLogger rejectedExecutionLogger;
    private static final int MAX_LISTENER_STACK_DEPTH = 8;
    private static final Signal SUCCESS;
    private static final Signal UNCANCELLABLE;
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER;
    private final EventExecutor executor;
    private Object result;
    private Object listeners;
    private LateListeners lateListeners;
    private short waiters;
    
    public DefaultPromise(final EventExecutor executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        this.executor = executor;
    }
    
    protected DefaultPromise() {
        this.executor = null;
    }
    
    protected EventExecutor executor() {
        return this.executor;
    }
    
    @Override
    public boolean isCancelled() {
        return isCancelled0(this.result);
    }
    
    @Override
    public boolean isCancellable() {
        return this.result == null;
    }
    
    @Override
    public boolean isDone() {
        return isDone0(this.result);
    }
    
    @Override
    public boolean isSuccess() {
        final Object result = this.result;
        return result != null && result != DefaultPromise.UNCANCELLABLE && !(result instanceof CauseHolder);
    }
    
    @Override
    public Throwable cause() {
        final Object result = this.result;
        if (result instanceof CauseHolder) {
            return ((CauseHolder)result).cause;
        }
        return null;
    }
    
    @Override
    public Promise addListener(final GenericFutureListener listeners) {
        if (listeners == null) {
            throw new NullPointerException("listener");
        }
        if (this.isDone()) {
            this.notifyLateListener(listeners);
            return this;
        }
        // monitorenter(this)
        if (!this.isDone()) {
            if (this.listeners == null) {
                this.listeners = listeners;
            }
            else if (this.listeners instanceof DefaultFutureListeners) {
                ((DefaultFutureListeners)this.listeners).add(listeners);
            }
            else {
                this.listeners = new DefaultFutureListeners((GenericFutureListener)this.listeners, listeners);
            }
            // monitorexit(this)
            return this;
        }
        // monitorexit(this)
        this.notifyLateListener(listeners);
        return this;
    }
    
    @Override
    public Promise addListeners(final GenericFutureListener... array) {
        if (array == null) {
            throw new NullPointerException("listeners");
        }
        while (0 < array.length) {
            final GenericFutureListener genericFutureListener = array[0];
            if (genericFutureListener == null) {
                break;
            }
            this.addListener(genericFutureListener);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    @Override
    public Promise removeListener(final GenericFutureListener genericFutureListener) {
        if (genericFutureListener == null) {
            throw new NullPointerException("listener");
        }
        if (this.isDone()) {
            return this;
        }
        // monitorenter(this)
        if (!this.isDone()) {
            if (this.listeners instanceof DefaultFutureListeners) {
                ((DefaultFutureListeners)this.listeners).remove(genericFutureListener);
            }
            else if (this.listeners == genericFutureListener) {
                this.listeners = null;
            }
        }
        // monitorexit(this)
        return this;
    }
    
    @Override
    public Promise removeListeners(final GenericFutureListener... array) {
        if (array == null) {
            throw new NullPointerException("listeners");
        }
        while (0 < array.length) {
            final GenericFutureListener genericFutureListener = array[0];
            if (genericFutureListener == null) {
                break;
            }
            this.removeListener(genericFutureListener);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    @Override
    public Promise sync() throws InterruptedException {
        this.await();
        this.rethrowIfFailed();
        return this;
    }
    
    @Override
    public Promise syncUninterruptibly() {
        this.awaitUninterruptibly();
        this.rethrowIfFailed();
        return this;
    }
    
    private void rethrowIfFailed() {
        final Throwable cause = this.cause();
        if (cause == null) {
            return;
        }
        PlatformDependent.throwException(cause);
    }
    
    @Override
    public Promise await() throws InterruptedException {
        if (this.isDone()) {
            return this;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException(this.toString());
        }
        // monitorenter(this)
        while (!this.isDone()) {
            this.checkDeadLock();
            this.incWaiters();
            this.wait();
            this.decWaiters();
        }
        // monitorexit(this)
        return this;
    }
    
    @Override
    public boolean await(final long n, final TimeUnit timeUnit) throws InterruptedException {
        return this.await0(timeUnit.toNanos(n), true);
    }
    
    @Override
    public boolean await(final long n) throws InterruptedException {
        return this.await0(TimeUnit.MILLISECONDS.toNanos(n), true);
    }
    
    @Override
    public Promise awaitUninterruptibly() {
        if (this.isDone()) {
            return this;
        }
        // monitorenter(this)
        while (!this.isDone()) {
            this.checkDeadLock();
            this.incWaiters();
            this.wait();
            this.decWaiters();
        }
        // monitorexit(this)
        Thread.currentThread().interrupt();
        return this;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long n, final TimeUnit timeUnit) {
        return this.await0(timeUnit.toNanos(n), false);
    }
    
    @Override
    public boolean awaitUninterruptibly(final long n) {
        return this.await0(TimeUnit.MILLISECONDS.toNanos(n), false);
    }
    
    private boolean await0(final long n, final boolean b) throws InterruptedException {
        if (this.isDone()) {
            return true;
        }
        if (n <= 0L) {
            return this.isDone();
        }
        if (b && Thread.interrupted()) {
            throw new InterruptedException(this.toString());
        }
        final long nanoTime = System.nanoTime();
        long n2 = n;
        // monitorenter(this)
        if (this.isDone()) {
            // monitorexit(this)
            Thread.currentThread().interrupt();
            return true;
        }
        if (n2 <= 0L) {
            this.isDone();
            // monitorexit(this)
            Thread.currentThread().interrupt();
            return true;
        }
        this.checkDeadLock();
        this.incWaiters();
        do {
            this.wait(n2 / 1000000L, (int)(n2 % 1000000L));
            if (this.isDone()) {
                this.decWaiters();
                // monitorexit(this)
                Thread.currentThread().interrupt();
                return true;
            }
            n2 = n - (System.nanoTime() - nanoTime);
        } while (n2 > 0L);
        this.isDone();
        this.decWaiters();
        // monitorexit(this)
        Thread.currentThread().interrupt();
        return true;
    }
    
    protected void checkDeadLock() {
        final EventExecutor executor = this.executor();
        if (executor != null && executor.inEventLoop()) {
            throw new BlockingOperationException(this.toString());
        }
    }
    
    @Override
    public Promise setSuccess(final Object o) {
        if (o != 0) {
            this.notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }
    
    @Override
    public boolean trySuccess(final Object o) {
        if (o != 0) {
            this.notifyListeners();
            return true;
        }
        return false;
    }
    
    @Override
    public Promise setFailure(final Throwable t) {
        if (t == null) {
            this.notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, t);
    }
    
    @Override
    public boolean tryFailure(final Throwable t) {
        if (t == null) {
            this.notifyListeners();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        final Object result = this.result;
        if (result == null || result == DefaultPromise.UNCANCELLABLE) {
            return false;
        }
        // monitorenter(this)
        final Object result2 = this.result;
        if (result2 == null || result2 == DefaultPromise.UNCANCELLABLE) {
            // monitorexit(this)
            return false;
        }
        this.result = DefaultPromise.CANCELLATION_CAUSE_HOLDER;
        if (this > 0) {
            this.notifyAll();
        }
        // monitorexit(this)
        this.notifyListeners();
        return true;
    }
    
    @Override
    public boolean setUncancellable() {
        final Object result = this.result;
        if (result != null) {
            return result != 0;
        }
        // monitorenter(this)
        final Object result2 = this.result;
        if (result2 != null) {
            // monitorexit(this)
            return result2 != 0;
        }
        this.result = DefaultPromise.UNCANCELLABLE;
        // monitorexit(this)
        return true;
    }
    
    @Override
    public Object getNow() {
        final Object result = this.result;
        if (result instanceof CauseHolder || result == DefaultPromise.SUCCESS) {
            return null;
        }
        return result;
    }
    
    private void incWaiters() {
        if (this.waiters == 32767) {
            throw new IllegalStateException("too many waiters: " + this);
        }
        ++this.waiters;
    }
    
    private void decWaiters() {
        --this.waiters;
    }
    
    private void notifyListeners() {
        final Object listeners = this.listeners;
        if (listeners == null) {
            return;
        }
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            final InternalThreadLocalMap value = InternalThreadLocalMap.get();
            final int futureListenerStackDepth = value.futureListenerStackDepth();
            if (futureListenerStackDepth < 8) {
                value.setFutureListenerStackDepth(futureListenerStackDepth + 1);
                if (listeners instanceof DefaultFutureListeners) {
                    notifyListeners0(this, (DefaultFutureListeners)listeners);
                }
                else {
                    notifyListener0(this, (GenericFutureListener)listeners);
                }
                this.listeners = null;
                value.setFutureListenerStackDepth(futureListenerStackDepth);
                return;
            }
        }
        if (listeners instanceof DefaultFutureListeners) {
            execute(executor, new Runnable((DefaultFutureListeners)listeners) {
                final DefaultFutureListeners val$dfl;
                final DefaultPromise this$0;
                
                @Override
                public void run() {
                    DefaultPromise.access$000(this.this$0, this.val$dfl);
                    DefaultPromise.access$102(this.this$0, null);
                }
            });
        }
        else {
            execute(executor, new Runnable((GenericFutureListener)listeners) {
                final GenericFutureListener val$l;
                final DefaultPromise this$0;
                
                @Override
                public void run() {
                    DefaultPromise.notifyListener0(this.this$0, this.val$l);
                    DefaultPromise.access$102(this.this$0, null);
                }
            });
        }
    }
    
    private static void notifyListeners0(final Future future, final DefaultFutureListeners defaultFutureListeners) {
        final GenericFutureListener[] listeners = defaultFutureListeners.listeners();
        while (0 < defaultFutureListeners.size()) {
            notifyListener0(future, listeners[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void notifyLateListener(final GenericFutureListener genericFutureListener) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            if (this.listeners != null || this.lateListeners != null) {
                LateListeners lateListeners = this.lateListeners;
                if (lateListeners == null) {
                    lateListeners = (this.lateListeners = new LateListeners());
                }
                lateListeners.add(genericFutureListener);
                execute(executor, lateListeners);
                return;
            }
            final InternalThreadLocalMap value = InternalThreadLocalMap.get();
            final int futureListenerStackDepth = value.futureListenerStackDepth();
            if (futureListenerStackDepth < 8) {
                value.setFutureListenerStackDepth(futureListenerStackDepth + 1);
                notifyListener0(this, genericFutureListener);
                value.setFutureListenerStackDepth(futureListenerStackDepth);
                return;
            }
        }
        execute(executor, new LateListenerNotifier(genericFutureListener));
    }
    
    protected static void notifyListener(final EventExecutor eventExecutor, final Future future, final GenericFutureListener genericFutureListener) {
        if (eventExecutor.inEventLoop()) {
            final InternalThreadLocalMap value = InternalThreadLocalMap.get();
            final int futureListenerStackDepth = value.futureListenerStackDepth();
            if (futureListenerStackDepth < 8) {
                value.setFutureListenerStackDepth(futureListenerStackDepth + 1);
                notifyListener0(future, genericFutureListener);
                value.setFutureListenerStackDepth(futureListenerStackDepth);
                return;
            }
        }
        execute(eventExecutor, new Runnable(future, genericFutureListener) {
            final Future val$future;
            final GenericFutureListener val$l;
            
            @Override
            public void run() {
                DefaultPromise.notifyListener0(this.val$future, this.val$l);
            }
        });
    }
    
    private static void execute(final EventExecutor eventExecutor, final Runnable runnable) {
        eventExecutor.execute(runnable);
    }
    
    static void notifyListener0(final Future future, final GenericFutureListener genericFutureListener) {
        genericFutureListener.operationComplete(future);
    }
    
    private synchronized Object progressiveListeners() {
        final Object listeners = this.listeners;
        if (listeners == null) {
            return null;
        }
        if (listeners instanceof DefaultFutureListeners) {
            final DefaultFutureListeners defaultFutureListeners = (DefaultFutureListeners)listeners;
            final int progressiveSize = defaultFutureListeners.progressiveSize();
            switch (progressiveSize) {
                case 0: {
                    return null;
                }
                case 1: {
                    final GenericFutureListener[] listeners2 = defaultFutureListeners.listeners();
                    while (0 < listeners2.length) {
                        final GenericFutureListener genericFutureListener = listeners2[0];
                        if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                            return genericFutureListener;
                        }
                        int n = 0;
                        ++n;
                    }
                    return null;
                }
                default: {
                    final GenericFutureListener[] listeners3 = defaultFutureListeners.listeners();
                    final GenericProgressiveFutureListener[] array = new GenericProgressiveFutureListener[progressiveSize];
                    while (0 < progressiveSize) {
                        final GenericFutureListener genericFutureListener2 = listeners3[0];
                        if (genericFutureListener2 instanceof GenericProgressiveFutureListener) {
                            final GenericProgressiveFutureListener[] array2 = array;
                            final int n2 = 0;
                            int n3 = 0;
                            ++n3;
                            array2[n2] = (GenericProgressiveFutureListener)genericFutureListener2;
                        }
                        int n = 0;
                        ++n;
                    }
                    return array;
                }
            }
        }
        else {
            if (listeners instanceof GenericProgressiveFutureListener) {
                return listeners;
            }
            return null;
        }
    }
    
    void notifyProgressiveListeners(final long n, final long n2) {
        final Object progressiveListeners = this.progressiveListeners();
        if (progressiveListeners == null) {
            return;
        }
        final ProgressiveFuture progressiveFuture = (ProgressiveFuture)this;
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            if (progressiveListeners instanceof GenericProgressiveFutureListener[]) {
                notifyProgressiveListeners0(progressiveFuture, (GenericProgressiveFutureListener[])progressiveListeners, n, n2);
            }
            else {
                notifyProgressiveListener0(progressiveFuture, (GenericProgressiveFutureListener)progressiveListeners, n, n2);
            }
        }
        else if (progressiveListeners instanceof GenericProgressiveFutureListener[]) {
            execute(executor, new Runnable(progressiveFuture, (GenericProgressiveFutureListener[])progressiveListeners, n, n2) {
                final ProgressiveFuture val$self;
                final GenericProgressiveFutureListener[] val$array;
                final long val$progress;
                final long val$total;
                final DefaultPromise this$0;
                
                @Override
                public void run() {
                    DefaultPromise.access$200(this.val$self, this.val$array, this.val$progress, this.val$total);
                }
            });
        }
        else {
            execute(executor, new Runnable(progressiveFuture, (GenericProgressiveFutureListener)progressiveListeners, n, n2) {
                final ProgressiveFuture val$self;
                final GenericProgressiveFutureListener val$l;
                final long val$progress;
                final long val$total;
                final DefaultPromise this$0;
                
                @Override
                public void run() {
                    DefaultPromise.access$300(this.val$self, this.val$l, this.val$progress, this.val$total);
                }
            });
        }
    }
    
    private static void notifyProgressiveListeners0(final ProgressiveFuture progressiveFuture, final GenericProgressiveFutureListener[] array, final long n, final long n2) {
        while (0 < array.length) {
            final GenericProgressiveFutureListener genericProgressiveFutureListener = array[0];
            if (genericProgressiveFutureListener == null) {
                break;
            }
            notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, n, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    private static void notifyProgressiveListener0(final ProgressiveFuture progressiveFuture, final GenericProgressiveFutureListener genericProgressiveFutureListener, final long n, final long n2) {
        genericProgressiveFutureListener.operationProgressed(progressiveFuture, n, n2);
    }
    
    @Override
    public String toString() {
        return this.toStringBuilder().toString();
    }
    
    protected StringBuilder toStringBuilder() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('@');
        sb.append(Integer.toHexString(this.hashCode()));
        final Object result = this.result;
        if (result == DefaultPromise.SUCCESS) {
            sb.append("(success)");
        }
        else if (result == DefaultPromise.UNCANCELLABLE) {
            sb.append("(uncancellable)");
        }
        else if (result instanceof CauseHolder) {
            sb.append("(failure(");
            sb.append(((CauseHolder)result).cause);
            sb.append(')');
        }
        else {
            sb.append("(incomplete)");
        }
        return sb;
    }
    
    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public Future await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public Future syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public Future sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public Future removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public Future removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public Future addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public Future addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    static void access$000(final Future future, final DefaultFutureListeners defaultFutureListeners) {
        notifyListeners0(future, defaultFutureListeners);
    }
    
    static Object access$102(final DefaultPromise defaultPromise, final Object listeners) {
        return defaultPromise.listeners = listeners;
    }
    
    static void access$200(final ProgressiveFuture progressiveFuture, final GenericProgressiveFutureListener[] array, final long n, final long n2) {
        notifyProgressiveListeners0(progressiveFuture, array, n, n2);
    }
    
    static void access$300(final ProgressiveFuture progressiveFuture, final GenericProgressiveFutureListener genericProgressiveFutureListener, final long n, final long n2) {
        notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, n, n2);
    }
    
    static Object access$100(final DefaultPromise defaultPromise) {
        return defaultPromise.listeners;
    }
    
    static void access$400(final EventExecutor eventExecutor, final Runnable runnable) {
        execute(eventExecutor, runnable);
    }
    
    static LateListeners access$500(final DefaultPromise defaultPromise) {
        return defaultPromise.lateListeners;
    }
    
    static LateListeners access$502(final DefaultPromise defaultPromise, final LateListeners lateListeners) {
        return defaultPromise.lateListeners = lateListeners;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
        rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
        SUCCESS = Signal.valueOf(DefaultPromise.class.getName() + ".SUCCESS");
        UNCANCELLABLE = Signal.valueOf(DefaultPromise.class.getName() + ".UNCANCELLABLE");
        CANCELLATION_CAUSE_HOLDER = new CauseHolder(new CancellationException());
        DefaultPromise.CANCELLATION_CAUSE_HOLDER.cause.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }
    
    private final class LateListenerNotifier implements Runnable
    {
        private GenericFutureListener l;
        final DefaultPromise this$0;
        
        LateListenerNotifier(final DefaultPromise this$0, final GenericFutureListener l) {
            this.this$0 = this$0;
            this.l = l;
        }
        
        @Override
        public void run() {
            LateListeners access$500 = DefaultPromise.access$500(this.this$0);
            if (this.l != null) {
                if (access$500 == null) {
                    DefaultPromise.access$502(this.this$0, access$500 = this.this$0.new LateListeners());
                }
                access$500.add(this.l);
                this.l = null;
            }
            access$500.run();
        }
    }
    
    private final class LateListeners extends ArrayDeque implements Runnable
    {
        private static final long serialVersionUID = -687137418080392244L;
        final DefaultPromise this$0;
        
        LateListeners(final DefaultPromise this$0) {
            this.this$0 = this$0;
            super(2);
        }
        
        @Override
        public void run() {
            if (DefaultPromise.access$100(this.this$0) == null) {
                while (true) {
                    final GenericFutureListener genericFutureListener = this.poll();
                    if (genericFutureListener == null) {
                        break;
                    }
                    DefaultPromise.notifyListener0(this.this$0, genericFutureListener);
                }
            }
            else {
                DefaultPromise.access$400(this.this$0.executor(), this);
            }
        }
    }
    
    private static final class CauseHolder
    {
        final Throwable cause;
        
        CauseHolder(final Throwable cause) {
            this.cause = cause;
        }
    }
}
