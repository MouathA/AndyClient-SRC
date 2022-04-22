package io.netty.util.internal.chmv8;

import java.util.concurrent.locks.*;
import sun.misc.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.lang.ref.*;

public abstract class ForkJoinTask implements Future, Serializable
{
    int status;
    static final int DONE_MASK = -268435456;
    static final int NORMAL = -268435456;
    static final int CANCELLED = -1073741824;
    static final int EXCEPTIONAL = Integer.MIN_VALUE;
    static final int SIGNAL = 65536;
    static final int SMASK = 65535;
    private static final ExceptionNode[] exceptionTable;
    private static final ReentrantLock exceptionTableLock;
    private static final ReferenceQueue exceptionTableRefQueue;
    private static final int EXCEPTION_MAP_CAPACITY = 32;
    private static final long serialVersionUID = -7721805057305804111L;
    private static final Unsafe U;
    private static final long STATUS;
    
    private int setCompletion(final int n) {
        int status;
        while ((status = this.status) >= 0) {
            if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, status | n)) {
                if (status >>> 16 != 0) {
                    // monitorenter(this)
                    this.notifyAll();
                }
                // monitorexit(this)
                return n;
            }
        }
        return status;
    }
    
    final int doExec() {
        int n;
        if ((n = this.status) >= 0 && this.exec()) {
            n = this.setCompletion(-268435456);
        }
        return n;
    }
    
    final boolean trySetSignal() {
        final int status = this.status;
        return status >= 0 && ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, status | 0x10000);
    }
    
    private int externalAwaitDone() {
        final ForkJoinPool common = ForkJoinPool.common;
        int n;
        if ((n = this.status) >= 0) {
            if (common != null) {
                if (this instanceof CountedCompleter) {
                    n = common.externalHelpComplete((CountedCompleter)this);
                }
                else if (common.tryExternalUnpush(this)) {
                    n = this.doExec();
                }
            }
            if (n >= 0 && (n = this.status) >= 0) {
                do {
                    if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, n, n | 0x10000)) {
                        // monitorenter(this)
                        if (this.status >= 0) {
                            this.wait();
                        }
                        else {
                            this.notifyAll();
                        }
                    }
                    // monitorexit(this)
                } while ((n = this.status) >= 0);
                if (true) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return n;
    }
    
    private int externalInterruptibleAwaitDone() throws InterruptedException {
        final ForkJoinPool common = ForkJoinPool.common;
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this.status >= 0 && common != null) {
            if (this instanceof CountedCompleter) {
                common.externalHelpComplete((CountedCompleter)this);
            }
            else if (common.tryExternalUnpush(this)) {
                this.doExec();
            }
        }
        int status;
        while ((status = this.status) >= 0) {
            if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, status | 0x10000)) {
                // monitorenter(this)
                if (this.status >= 0) {
                    this.wait();
                }
                else {
                    this.notifyAll();
                }
            }
            // monitorexit(this)
        }
        return status;
    }
    
    private int doJoin() {
        final int status;
        ForkJoinWorkerThread currentThread;
        ForkJoinWorkerThread forkJoinWorkerThread;
        ForkJoinPool.WorkQueue workQueue;
        int doExec;
        return ((status = this.status) < 0) ? status : (((currentThread = (ForkJoinWorkerThread)Thread.currentThread()) instanceof ForkJoinWorkerThread) ? (((workQueue = (forkJoinWorkerThread = currentThread).workQueue).tryUnpush(this) && (doExec = this.doExec()) < 0) ? doExec : forkJoinWorkerThread.pool.awaitJoin(workQueue, this)) : this.externalAwaitDone());
    }
    
    private int doInvoke() {
        final int doExec;
        int n;
        if ((doExec = this.doExec()) < 0) {
            n = doExec;
        }
        else {
            final Thread currentThread;
            if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
                final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
                n = forkJoinWorkerThread.pool.awaitJoin(forkJoinWorkerThread.workQueue, this);
            }
            else {
                n = this.externalAwaitDone();
            }
        }
        return n;
    }
    
    final int recordExceptionalCompletion(final Throwable t) {
        int n;
        if ((n = this.status) >= 0) {
            final int identityHashCode = System.identityHashCode(this);
            final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
            exceptionTableLock.lock();
            final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
            final int n2 = identityHashCode & exceptionTable.length - 1;
            while (true) {
                for (ExceptionNode next = exceptionTable[n2]; next != null; next = next.next) {
                    if (next.get() == this) {
                        exceptionTableLock.unlock();
                        n = this.setCompletion(Integer.MIN_VALUE);
                        return n;
                    }
                }
                exceptionTable[n2] = new ExceptionNode(this, t, exceptionTable[n2]);
                continue;
            }
        }
        return n;
    }
    
    private int setExceptionalCompletion(final Throwable t) {
        final int recordExceptionalCompletion = this.recordExceptionalCompletion(t);
        if ((recordExceptionalCompletion & 0xF0000000) == Integer.MIN_VALUE) {
            this.internalPropagateException(t);
        }
        return recordExceptionalCompletion;
    }
    
    void internalPropagateException(final Throwable t) {
    }
    
    static final void cancelIgnoringExceptions(final ForkJoinTask forkJoinTask) {
        if (forkJoinTask != null && forkJoinTask.status >= 0) {
            forkJoinTask.cancel(false);
        }
    }
    
    private void clearExceptionalCompletion() {
        final int identityHashCode = System.identityHashCode(this);
        final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
        exceptionTableLock.lock();
        final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
        final int n = identityHashCode & exceptionTable.length - 1;
        Reference<Object> reference = (Reference<Object>)exceptionTable[n];
        Reference<Object> reference2 = null;
        while (reference != null) {
            final ExceptionNode next = ((ExceptionNode)reference).next;
            if (reference.get() == this) {
                if (reference2 == null) {
                    exceptionTable[n] = next;
                    break;
                }
                ((ExceptionNode)reference2).next = next;
                break;
            }
            else {
                reference2 = reference;
                reference = (Reference<Object>)next;
            }
        }
        this.status = 0;
        exceptionTableLock.unlock();
    }
    
    private Throwable getThrowableException() {
        if ((this.status & 0xF0000000) != Integer.MIN_VALUE) {
            return null;
        }
        final int identityHashCode = System.identityHashCode(this);
        final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
        exceptionTableLock.lock();
        final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
        ExceptionNode next;
        for (next = exceptionTable[identityHashCode & exceptionTable.length - 1]; next != null && next.get() != this; next = next.next) {}
        exceptionTableLock.unlock();
        final Throwable ex;
        if (next == null || (ex = next.ex) == null) {
            return null;
        }
        return ex;
    }
    
    private static void expungeStaleExceptions() {
        Reference poll;
        while ((poll = ForkJoinTask.exceptionTableRefQueue.poll()) != null) {
            if (poll instanceof ExceptionNode) {
                final ForkJoinTask forkJoinTask = ((ExceptionNode)poll).get();
                final ExceptionNode[] exceptionTable = ForkJoinTask.exceptionTable;
                final int n = System.identityHashCode(forkJoinTask) & exceptionTable.length - 1;
                ExceptionNode exceptionNode = exceptionTable[n];
                ExceptionNode exceptionNode2 = null;
                while (exceptionNode != null) {
                    final ExceptionNode next = exceptionNode.next;
                    if (exceptionNode == poll) {
                        if (exceptionNode2 == null) {
                            exceptionTable[n] = next;
                            break;
                        }
                        exceptionNode2.next = next;
                        break;
                    }
                    else {
                        exceptionNode2 = exceptionNode;
                        exceptionNode = next;
                    }
                }
            }
        }
    }
    
    static final void helpExpungeStaleExceptions() {
        final ReentrantLock exceptionTableLock = ForkJoinTask.exceptionTableLock;
        if (exceptionTableLock.tryLock()) {
            exceptionTableLock.unlock();
        }
    }
    
    static void rethrow(final Throwable t) {
        if (t != null) {
            uncheckedThrow(t);
        }
    }
    
    static void uncheckedThrow(final Throwable t) throws Throwable {
        throw t;
    }
    
    private void reportException(final int n) {
        if (n == -1073741824) {
            throw new CancellationException();
        }
        if (n == Integer.MIN_VALUE) {
            rethrow(this.getThrowableException());
        }
    }
    
    public final ForkJoinTask fork() {
        final Thread currentThread;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            ((ForkJoinWorkerThread)currentThread).workQueue.push(this);
        }
        else {
            ForkJoinPool.common.externalPush(this);
        }
        return this;
    }
    
    public final Object join() {
        final int n;
        if ((n = (this.doJoin() & 0xF0000000)) != -268435456) {
            this.reportException(n);
        }
        return this.getRawResult();
    }
    
    public final Object invoke() {
        final int n;
        if ((n = (this.doInvoke() & 0xF0000000)) != -268435456) {
            this.reportException(n);
        }
        return this.getRawResult();
    }
    
    public static void invokeAll(final ForkJoinTask forkJoinTask, final ForkJoinTask forkJoinTask2) {
        forkJoinTask2.fork();
        final int n;
        if ((n = (forkJoinTask.doInvoke() & 0xF0000000)) != -268435456) {
            forkJoinTask.reportException(n);
        }
        final int n2;
        if ((n2 = (forkJoinTask2.doJoin() & 0xF0000000)) != -268435456) {
            forkJoinTask2.reportException(n2);
        }
    }
    
    public static void invokeAll(final ForkJoinTask... array) {
        Throwable t = null;
        int n2;
        final int n = n2 = array.length - 1;
        while (1 >= 0) {
            final ForkJoinTask forkJoinTask = array[1];
            if (forkJoinTask == null) {
                if (t == null) {
                    t = new NullPointerException();
                }
            }
            else if (true) {
                forkJoinTask.fork();
            }
            else if (forkJoinTask.doInvoke() < -268435456 && t == null) {
                t = forkJoinTask.getException();
            }
            --n2;
        }
        while (1 <= n) {
            final ForkJoinTask forkJoinTask2 = array[1];
            if (forkJoinTask2 != null) {
                if (t != null) {
                    forkJoinTask2.cancel(false);
                }
                else if (forkJoinTask2.doJoin() < -268435456) {
                    t = forkJoinTask2.getException();
                }
            }
            ++n2;
        }
        if (t != null) {
            rethrow(t);
        }
    }
    
    public static Collection invokeAll(final Collection collection) {
        if (!(collection instanceof RandomAccess) || !(collection instanceof List)) {
            invokeAll((ForkJoinTask[])collection.toArray(new ForkJoinTask[collection.size()]));
            return collection;
        }
        final List<ForkJoinTask> list = (List<ForkJoinTask>)collection;
        Throwable t = null;
        int n2;
        final int n = n2 = list.size() - 1;
        while (1 >= 0) {
            final ForkJoinTask forkJoinTask = list.get(1);
            if (forkJoinTask == null) {
                if (t == null) {
                    t = new NullPointerException();
                }
            }
            else if (true) {
                forkJoinTask.fork();
            }
            else if (forkJoinTask.doInvoke() < -268435456 && t == null) {
                t = forkJoinTask.getException();
            }
            --n2;
        }
        while (1 <= n) {
            final ForkJoinTask forkJoinTask2 = list.get(1);
            if (forkJoinTask2 != null) {
                if (t != null) {
                    forkJoinTask2.cancel(false);
                }
                else if (forkJoinTask2.doJoin() < -268435456) {
                    t = forkJoinTask2.getException();
                }
            }
            ++n2;
        }
        if (t != null) {
            rethrow(t);
        }
        return collection;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        return (this.setCompletion(-1073741824) & 0xF0000000) == 0xC0000000;
    }
    
    @Override
    public final boolean isDone() {
        return this.status < 0;
    }
    
    @Override
    public final boolean isCancelled() {
        return (this.status & 0xF0000000) == 0xC0000000;
    }
    
    public final boolean isCompletedAbnormally() {
        return this.status < -268435456;
    }
    
    public final boolean isCompletedNormally() {
        return (this.status & 0xF0000000) == 0xF0000000;
    }
    
    public final Throwable getException() {
        final int n = this.status & 0xF0000000;
        return (n >= -268435456) ? null : ((n == -1073741824) ? new CancellationException() : this.getThrowableException());
    }
    
    public void completeExceptionally(final Throwable t) {
        this.setExceptionalCompletion((t instanceof RuntimeException || t instanceof Error) ? t : new RuntimeException(t));
    }
    
    public void complete(final Object rawResult) {
        this.setRawResult(rawResult);
        this.setCompletion(-268435456);
    }
    
    public final void quietlyComplete() {
        this.setCompletion(-268435456);
    }
    
    @Override
    public final Object get() throws InterruptedException, ExecutionException {
        final int n;
        if ((n = (((Thread.currentThread() instanceof ForkJoinWorkerThread) ? this.doJoin() : this.externalInterruptibleAwaitDone()) & 0xF0000000)) == -1073741824) {
            throw new CancellationException();
        }
        final Throwable throwableException;
        if (n == Integer.MIN_VALUE && (throwableException = this.getThrowableException()) != null) {
            throw new ExecutionException(throwableException);
        }
        return this.getRawResult();
    }
    
    @Override
    public final Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        long nanos = timeUnit.toNanos(n);
        int n2;
        if ((n2 = this.status) >= 0 && nanos > 0L) {
            final long n3 = System.nanoTime() + nanos;
            ForkJoinPool pool = null;
            ForkJoinPool.WorkQueue workQueue = null;
            final Thread currentThread = Thread.currentThread();
            if (currentThread instanceof ForkJoinWorkerThread) {
                final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
                pool = forkJoinWorkerThread.pool;
                workQueue = forkJoinWorkerThread.workQueue;
                pool.helpJoinOnce(workQueue, this);
            }
            else {
                final ForkJoinPool common;
                if ((common = ForkJoinPool.common) != null) {
                    if (this instanceof CountedCompleter) {
                        common.externalHelpComplete((CountedCompleter)this);
                    }
                    else if (common.tryExternalUnpush(this)) {
                        this.doExec();
                    }
                }
            }
            while ((n2 = this.status) >= 0) {
                if (workQueue != null && workQueue.qlock < 0) {
                    cancelIgnoringExceptions(this);
                }
                else if (!true) {
                    if (pool == null || pool.tryCompensate(pool.ctl)) {
                        continue;
                    }
                    continue;
                }
                else {
                    final long millis;
                    if ((millis = TimeUnit.NANOSECONDS.toMillis(nanos)) > 0L && ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, n2, n2 | 0x10000)) {
                        // monitorenter(this)
                        if (this.status >= 0) {
                            this.wait(millis);
                        }
                        else {
                            this.notifyAll();
                        }
                    }
                    // monitorexit(this)
                    if ((n2 = this.status) < 0 || true || (nanos = n3 - System.nanoTime()) <= 0L) {
                        break;
                    }
                    continue;
                }
            }
            if (pool != null && true) {
                pool.incrementActiveCount();
            }
            if (true) {
                throw new InterruptedException();
            }
        }
        final int n4;
        if ((n4 = (n2 & 0xF0000000)) != -268435456) {
            if (n4 == -1073741824) {
                throw new CancellationException();
            }
            if (n4 != Integer.MIN_VALUE) {
                throw new TimeoutException();
            }
            final Throwable throwableException;
            if ((throwableException = this.getThrowableException()) != null) {
                throw new ExecutionException(throwableException);
            }
        }
        return this.getRawResult();
    }
    
    public final void quietlyJoin() {
        this.doJoin();
    }
    
    public final void quietlyInvoke() {
        this.doInvoke();
    }
    
    public static void helpQuiesce() {
        final Thread currentThread;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
            forkJoinWorkerThread.pool.helpQuiescePool(forkJoinWorkerThread.workQueue);
        }
    }
    
    public void reinitialize() {
        if ((this.status & 0xF0000000) == Integer.MIN_VALUE) {
            this.clearExceptionalCompletion();
        }
        else {
            this.status = 0;
        }
    }
    
    public static ForkJoinPool getPool() {
        final Thread currentThread = Thread.currentThread();
        return (currentThread instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)currentThread).pool : null;
    }
    
    public static boolean inForkJoinPool() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread;
    }
    
    public boolean tryUnfork() {
        final Thread currentThread;
        return ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)currentThread).workQueue.tryUnpush(this) : ForkJoinPool.common.tryExternalUnpush(this);
    }
    
    public static int getQueuedTaskCount() {
        final Thread currentThread;
        ForkJoinPool.WorkQueue workQueue;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            workQueue = ((ForkJoinWorkerThread)currentThread).workQueue;
        }
        else {
            workQueue = ForkJoinPool.commonSubmitterQueue();
        }
        return (workQueue == null) ? 0 : workQueue.queueSize();
    }
    
    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.getSurplusQueuedTaskCount();
    }
    
    public abstract Object getRawResult();
    
    protected abstract void setRawResult(final Object p0);
    
    protected abstract boolean exec();
    
    protected static ForkJoinTask peekNextLocalTask() {
        final Thread currentThread;
        ForkJoinPool.WorkQueue workQueue;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            workQueue = ((ForkJoinWorkerThread)currentThread).workQueue;
        }
        else {
            workQueue = ForkJoinPool.commonSubmitterQueue();
        }
        return (workQueue == null) ? null : workQueue.peek();
    }
    
    protected static ForkJoinTask pollNextLocalTask() {
        final Thread currentThread;
        return ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)currentThread).workQueue.nextLocalTask() : null;
    }
    
    protected static ForkJoinTask pollTask() {
        final Thread currentThread;
        ForkJoinTask nextTask;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread;
            nextTask = forkJoinWorkerThread.pool.nextTaskFor(forkJoinWorkerThread.workQueue);
        }
        else {
            nextTask = null;
        }
        return nextTask;
    }
    
    public final short getForkJoinTaskTag() {
        return (short)this.status;
    }
    
    public final short setForkJoinTaskTag(final short n) {
        int status;
        while (!ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status = this.status, (status & 0xFFFF0000) | (n & 0xFFFF))) {}
        return (short)status;
    }
    
    public final boolean compareAndSetForkJoinTaskTag(final short n, final short n2) {
        int status;
        while ((short)(status = this.status) == n) {
            if (ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, status, (status & 0xFFFF0000) | (n2 & 0xFFFF))) {
                return true;
            }
        }
        return false;
    }
    
    public static ForkJoinTask adapt(final Runnable runnable) {
        return new AdaptedRunnableAction(runnable);
    }
    
    public static ForkJoinTask adapt(final Runnable runnable, final Object o) {
        return new AdaptedRunnable(runnable, o);
    }
    
    public static ForkJoinTask adapt(final Callable callable) {
        return new AdaptedCallable(callable);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.getException());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final Object object = objectInputStream.readObject();
        if (object != null) {
            this.setExceptionalCompletion((Throwable)object);
        }
    }
    
    private static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }
    
    static ReferenceQueue access$000() {
        return ForkJoinTask.exceptionTableRefQueue;
    }
    
    static {
        exceptionTableLock = new ReentrantLock();
        exceptionTableRefQueue = new ReferenceQueue();
        exceptionTable = new ExceptionNode[32];
        U = getUnsafe();
        STATUS = ForkJoinTask.U.objectFieldOffset(ForkJoinTask.class.getDeclaredField("status"));
    }
    
    static final class AdaptedCallable extends ForkJoinTask implements RunnableFuture
    {
        final Callable callable;
        Object result;
        private static final long serialVersionUID = 2838392045355241008L;
        
        AdaptedCallable(final Callable callable) {
            if (callable == null) {
                throw new NullPointerException();
            }
            this.callable = callable;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        public final void setRawResult(final Object result) {
            this.result = result;
        }
        
        public final boolean exec() {
            this.result = this.callable.call();
            return true;
        }
        
        @Override
        public final void run() {
            this.invoke();
        }
    }
    
    static final class RunnableExecuteAction extends ForkJoinTask
    {
        final Runnable runnable;
        private static final long serialVersionUID = 5232453952276885070L;
        
        RunnableExecuteAction(final Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            this.runnable = runnable;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            this.runnable.run();
            return true;
        }
        
        @Override
        void internalPropagateException(final Throwable t) {
            ForkJoinTask.rethrow(t);
        }
        
        public void setRawResult(final Object o) {
            this.setRawResult((Void)o);
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class AdaptedRunnableAction extends ForkJoinTask implements RunnableFuture
    {
        final Runnable runnable;
        private static final long serialVersionUID = 5232453952276885070L;
        
        AdaptedRunnableAction(final Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            this.runnable = runnable;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            this.runnable.run();
            return true;
        }
        
        @Override
        public final void run() {
            this.invoke();
        }
        
        public void setRawResult(final Object o) {
            this.setRawResult((Void)o);
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class AdaptedRunnable extends ForkJoinTask implements RunnableFuture
    {
        final Runnable runnable;
        Object result;
        private static final long serialVersionUID = 5232453952276885070L;
        
        AdaptedRunnable(final Runnable runnable, final Object result) {
            if (runnable == null) {
                throw new NullPointerException();
            }
            this.runnable = runnable;
            this.result = result;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        public final void setRawResult(final Object result) {
            this.result = result;
        }
        
        public final boolean exec() {
            this.runnable.run();
            return true;
        }
        
        @Override
        public final void run() {
            this.invoke();
        }
    }
    
    static final class ExceptionNode extends WeakReference
    {
        final Throwable ex;
        ExceptionNode next;
        final long thrower;
        
        ExceptionNode(final ForkJoinTask forkJoinTask, final Throwable ex, final ExceptionNode next) {
            super(forkJoinTask, ForkJoinTask.access$000());
            this.ex = ex;
            this.next = next;
            this.thrower = Thread.currentThread().getId();
        }
    }
}
