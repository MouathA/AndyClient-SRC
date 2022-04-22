package io.netty.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.util.*;

public abstract class SingleThreadEventExecutor extends AbstractEventExecutor
{
    private static final InternalLogger logger;
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_TERMINATED = 5;
    private static final Runnable WAKEUP_TASK;
    private static final AtomicIntegerFieldUpdater STATE_UPDATER;
    private final EventExecutorGroup parent;
    private final Queue taskQueue;
    final Queue delayedTaskQueue;
    private final Thread thread;
    private final Semaphore threadLock;
    private final Set shutdownHooks;
    private final boolean addTaskWakesUp;
    private long lastExecutionTime;
    private int state;
    private long gracefulShutdownQuietPeriod;
    private long gracefulShutdownTimeout;
    private long gracefulShutdownStartTime;
    private final Promise terminationFuture;
    private static final long SCHEDULE_PURGE_INTERVAL;
    static final boolean $assertionsDisabled;
    
    protected SingleThreadEventExecutor(final EventExecutorGroup parent, final ThreadFactory threadFactory, final boolean addTaskWakesUp) {
        this.delayedTaskQueue = new PriorityQueue();
        this.threadLock = new Semaphore(0);
        this.shutdownHooks = new LinkedHashSet();
        this.state = 1;
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        this.parent = parent;
        this.addTaskWakesUp = addTaskWakesUp;
        this.thread = threadFactory.newThread(new Runnable() {
            final SingleThreadEventExecutor this$0;
            
            @Override
            public void run() {
                this.this$0.updateLastExecutionTime();
                this.this$0.run();
                int value;
                do {
                    value = SingleThreadEventExecutor.access$100().get(this.this$0);
                } while (value < 3 && !SingleThreadEventExecutor.access$100().compareAndSet(this.this$0, value, 3));
                if (true && SingleThreadEventExecutor.access$200(this.this$0) == 0L) {
                    SingleThreadEventExecutor.access$000().error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " + "before run() implementation terminates.");
                }
                while (!this.this$0.confirmShutdown()) {}
                this.this$0.cleanup();
                SingleThreadEventExecutor.access$100().set(this.this$0, 5);
                SingleThreadEventExecutor.access$300(this.this$0).release();
                if (!SingleThreadEventExecutor.access$400(this.this$0).isEmpty()) {
                    SingleThreadEventExecutor.access$000().warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.access$400(this.this$0).size() + ')');
                }
                SingleThreadEventExecutor.access$500(this.this$0).setSuccess(null);
            }
        });
        this.taskQueue = this.newTaskQueue();
    }
    
    protected Queue newTaskQueue() {
        return new LinkedBlockingQueue();
    }
    
    @Override
    public EventExecutorGroup parent() {
        return this.parent;
    }
    
    protected void interruptThread() {
        this.thread.interrupt();
    }
    
    protected Runnable pollTask() {
        assert this.inEventLoop();
        Runnable runnable;
        do {
            runnable = this.taskQueue.poll();
        } while (runnable == SingleThreadEventExecutor.WAKEUP_TASK);
        return runnable;
    }
    
    protected Runnable takeTask() {
        assert this.inEventLoop();
        if (!(this.taskQueue instanceof BlockingQueue)) {
            throw new UnsupportedOperationException();
        }
        final BlockingQueue blockingQueue = (BlockingQueue)this.taskQueue;
        while (true) {
            final ScheduledFutureTask scheduledFutureTask = this.delayedTaskQueue.peek();
            if (scheduledFutureTask == null) {
                Runnable runnable = blockingQueue.take();
                if (runnable == SingleThreadEventExecutor.WAKEUP_TASK) {
                    runnable = null;
                }
                return runnable;
            }
            final long delayNanos = scheduledFutureTask.delayNanos();
            Runnable runnable2 = null;
            if (delayNanos > 0L) {
                runnable2 = blockingQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
            }
            if (runnable2 == null) {
                this.fetchFromDelayedQueue();
                runnable2 = blockingQueue.poll();
            }
            if (runnable2 != null) {
                return runnable2;
            }
        }
    }
    
    private void fetchFromDelayedQueue() {
        long nanoTime = 0L;
        while (true) {
            final ScheduledFutureTask scheduledFutureTask = this.delayedTaskQueue.peek();
            if (scheduledFutureTask == null) {
                break;
            }
            if (nanoTime == 0L) {
                nanoTime = ScheduledFutureTask.nanoTime();
            }
            if (scheduledFutureTask.deadlineNanos() > nanoTime) {
                break;
            }
            this.delayedTaskQueue.remove();
            this.taskQueue.add(scheduledFutureTask);
        }
    }
    
    protected Runnable peekTask() {
        assert this.inEventLoop();
        return this.taskQueue.peek();
    }
    
    protected boolean hasTasks() {
        assert this.inEventLoop();
        return !this.taskQueue.isEmpty();
    }
    
    protected boolean hasScheduledTasks() {
        assert this.inEventLoop();
        final ScheduledFutureTask scheduledFutureTask = this.delayedTaskQueue.peek();
        return scheduledFutureTask != null && scheduledFutureTask.deadlineNanos() <= ScheduledFutureTask.nanoTime();
    }
    
    public final int pendingTasks() {
        return this.taskQueue.size();
    }
    
    protected void addTask(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        this.isShutdown();
        this.taskQueue.add(runnable);
    }
    
    protected boolean removeTask(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        return this.taskQueue.remove(runnable);
    }
    
    protected boolean runAllTasks(final long n) {
        this.fetchFromDelayedQueue();
        Runnable runnable = this.pollTask();
        if (runnable == null) {
            return false;
        }
        final long n2 = ScheduledFutureTask.nanoTime() + n;
        long n3 = 0L;
        while (true) {
            do {
                runnable.run();
                ++n3;
                if ((n3 & 0x3FL) == 0x0L) {
                    final long lastExecutionTime = ScheduledFutureTask.nanoTime();
                    if (lastExecutionTime >= n2) {
                        this.lastExecutionTime = lastExecutionTime;
                        return true;
                    }
                }
                runnable = this.pollTask();
            } while (runnable != null);
            final long lastExecutionTime = ScheduledFutureTask.nanoTime();
            continue;
        }
    }
    
    protected long delayNanos(final long n) {
        final ScheduledFutureTask scheduledFutureTask = this.delayedTaskQueue.peek();
        if (scheduledFutureTask == null) {
            return SingleThreadEventExecutor.SCHEDULE_PURGE_INTERVAL;
        }
        return scheduledFutureTask.delayNanos(n);
    }
    
    protected void updateLastExecutionTime() {
        this.lastExecutionTime = ScheduledFutureTask.nanoTime();
    }
    
    protected abstract void run();
    
    protected void cleanup() {
    }
    
    protected void wakeup(final boolean b) {
        if (!b || SingleThreadEventExecutor.STATE_UPDATER.get(this) == 3) {
            this.taskQueue.add(SingleThreadEventExecutor.WAKEUP_TASK);
        }
    }
    
    @Override
    public boolean inEventLoop(final Thread thread) {
        return thread == this.thread;
    }
    
    public void addShutdownHook(final Runnable runnable) {
        if (this.inEventLoop()) {
            this.shutdownHooks.add(runnable);
        }
        else {
            this.execute(new Runnable(runnable) {
                final Runnable val$task;
                final SingleThreadEventExecutor this$0;
                
                @Override
                public void run() {
                    SingleThreadEventExecutor.access$600(this.this$0).add(this.val$task);
                }
            });
        }
    }
    
    public void removeShutdownHook(final Runnable runnable) {
        if (this.inEventLoop()) {
            this.shutdownHooks.remove(runnable);
        }
        else {
            this.execute(new Runnable(runnable) {
                final Runnable val$task;
                final SingleThreadEventExecutor this$0;
                
                @Override
                public void run() {
                    SingleThreadEventExecutor.access$600(this.this$0).remove(this.val$task);
                }
            });
        }
    }
    
    @Override
    public Future shutdownGracefully(final long p0, final long p1, final TimeUnit p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: lconst_0       
        //     2: lcmp           
        //     3: ifge            40
        //     6: new             Ljava/lang/IllegalArgumentException;
        //     9: dup            
        //    10: new             Ljava/lang/StringBuilder;
        //    13: dup            
        //    14: invokespecial   java/lang/StringBuilder.<init>:()V
        //    17: ldc_w           "quietPeriod: "
        //    20: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    23: lload_1        
        //    24: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //    27: ldc_w           " (expected >= 0)"
        //    30: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    33: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    36: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    39: athrow         
        //    40: lload_3        
        //    41: lload_1        
        //    42: lcmp           
        //    43: ifge            90
        //    46: new             Ljava/lang/IllegalArgumentException;
        //    49: dup            
        //    50: new             Ljava/lang/StringBuilder;
        //    53: dup            
        //    54: invokespecial   java/lang/StringBuilder.<init>:()V
        //    57: ldc_w           "timeout: "
        //    60: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    63: lload_3        
        //    64: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //    67: ldc_w           " (expected >= quietPeriod ("
        //    70: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    73: lload_1        
        //    74: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //    77: ldc_w           "))"
        //    80: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    83: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    86: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    89: athrow         
        //    90: aload           5
        //    92: ifnonnull       106
        //    95: new             Ljava/lang/NullPointerException;
        //    98: dup            
        //    99: ldc_w           "unit"
        //   102: invokespecial   java/lang/NullPointerException.<init>:(Ljava/lang/String;)V
        //   105: athrow         
        //   106: aload_0        
        //   107: if_icmplt       115
        //   110: aload_0        
        //   111: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.terminationFuture:()Lio/netty/util/concurrent/Future;
        //   114: areturn        
        //   115: aload_0        
        //   116: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.inEventLoop:()Z
        //   119: istore          6
        //   121: aload_0        
        //   122: if_icmplt       130
        //   125: aload_0        
        //   126: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.terminationFuture:()Lio/netty/util/concurrent/Future;
        //   129: areturn        
        //   130: getstatic       io/netty/util/concurrent/SingleThreadEventExecutor.STATE_UPDATER:Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
        //   133: aload_0        
        //   134: invokevirtual   java/util/concurrent/atomic/AtomicIntegerFieldUpdater.get:(Ljava/lang/Object;)I
        //   137: istore          8
        //   139: iload           6
        //   141: ifeq            147
        //   144: goto            183
        //   147: iload           8
        //   149: lookupswitch {
        //                1: 176
        //                2: 176
        //          default: 179
        //        }
        //   176: goto            183
        //   179: iload           8
        //   181: istore          9
        //   183: getstatic       io/netty/util/concurrent/SingleThreadEventExecutor.STATE_UPDATER:Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
        //   186: aload_0        
        //   187: iload           8
        //   189: iconst_3       
        //   190: invokevirtual   java/util/concurrent/atomic/AtomicIntegerFieldUpdater.compareAndSet:(Ljava/lang/Object;II)Z
        //   193: ifeq            199
        //   196: goto            202
        //   199: goto            121
        //   202: aload_0        
        //   203: aload           5
        //   205: lload_1        
        //   206: invokevirtual   java/util/concurrent/TimeUnit.toNanos:(J)J
        //   209: putfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownQuietPeriod:J
        //   212: aload_0        
        //   213: aload           5
        //   215: lload_3        
        //   216: invokevirtual   java/util/concurrent/TimeUnit.toNanos:(J)J
        //   219: putfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownTimeout:J
        //   222: iload           8
        //   224: iconst_1       
        //   225: if_icmpne       235
        //   228: aload_0        
        //   229: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.thread:Ljava/lang/Thread;
        //   232: invokevirtual   java/lang/Thread.start:()V
        //   235: goto            244
        //   238: aload_0        
        //   239: iload           6
        //   241: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.wakeup:(Z)V
        //   244: aload_0        
        //   245: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.terminationFuture:()Lio/netty/util/concurrent/Future;
        //   248: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public Future terminationFuture() {
        return this.terminationFuture;
    }
    
    @Deprecated
    @Override
    public void shutdown() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmplt       5
        //     4: return         
        //     5: aload_0        
        //     6: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.inEventLoop:()Z
        //     9: istore_1       
        //    10: aload_0        
        //    11: if_icmplt       15
        //    14: return         
        //    15: getstatic       io/netty/util/concurrent/SingleThreadEventExecutor.STATE_UPDATER:Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
        //    18: aload_0        
        //    19: invokevirtual   java/util/concurrent/atomic/AtomicIntegerFieldUpdater.get:(Ljava/lang/Object;)I
        //    22: istore_3       
        //    23: iload_1        
        //    24: ifeq            30
        //    27: goto            62
        //    30: iload_3        
        //    31: tableswitch {
        //                2: 56
        //                3: 56
        //                4: 56
        //          default: 59
        //        }
        //    56: goto            62
        //    59: iload_3        
        //    60: istore          4
        //    62: getstatic       io/netty/util/concurrent/SingleThreadEventExecutor.STATE_UPDATER:Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
        //    65: aload_0        
        //    66: iload_3        
        //    67: iconst_4       
        //    68: invokevirtual   java/util/concurrent/atomic/AtomicIntegerFieldUpdater.compareAndSet:(Ljava/lang/Object;II)Z
        //    71: ifeq            77
        //    74: goto            80
        //    77: goto            10
        //    80: iload_3        
        //    81: iconst_1       
        //    82: if_icmpne       92
        //    85: aload_0        
        //    86: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.thread:Ljava/lang/Thread;
        //    89: invokevirtual   java/lang/Thread.start:()V
        //    92: goto            100
        //    95: aload_0        
        //    96: iload_1        
        //    97: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.wakeup:(Z)V
        //   100: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean isTerminated() {
        return SingleThreadEventExecutor.STATE_UPDATER.get(this) == 5;
    }
    
    protected boolean confirmShutdown() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmplt       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.inEventLoop:()Z
        //    10: ifne            24
        //    13: new             Ljava/lang/IllegalStateException;
        //    16: dup            
        //    17: ldc_w           "must be invoked from an event loop"
        //    20: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //    23: athrow         
        //    24: aload_0        
        //    25: invokespecial   io/netty/util/concurrent/SingleThreadEventExecutor.cancelDelayedTasks:()V
        //    28: aload_0        
        //    29: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownStartTime:J
        //    32: lconst_0       
        //    33: lcmp           
        //    34: ifne            44
        //    37: aload_0        
        //    38: invokestatic    io/netty/util/concurrent/ScheduledFutureTask.nanoTime:()J
        //    41: putfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownStartTime:J
        //    44: aload_0        
        //    45: ifnonnull       52
        //    48: aload_0        
        //    49: ifne            65
        //    52: aload_0        
        //    53: if_icmplt       58
        //    56: iconst_1       
        //    57: ireturn        
        //    58: aload_0        
        //    59: iconst_1       
        //    60: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.wakeup:(Z)V
        //    63: iconst_0       
        //    64: ireturn        
        //    65: invokestatic    io/netty/util/concurrent/ScheduledFutureTask.nanoTime:()J
        //    68: lstore_1       
        //    69: aload_0        
        //    70: if_icmplt       87
        //    73: lload_1        
        //    74: aload_0        
        //    75: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownStartTime:J
        //    78: lsub           
        //    79: aload_0        
        //    80: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownTimeout:J
        //    83: lcmp           
        //    84: ifle            89
        //    87: iconst_1       
        //    88: ireturn        
        //    89: lload_1        
        //    90: aload_0        
        //    91: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.lastExecutionTime:J
        //    94: lsub           
        //    95: aload_0        
        //    96: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.gracefulShutdownQuietPeriod:J
        //    99: lcmp           
        //   100: ifgt            120
        //   103: aload_0        
        //   104: iconst_1       
        //   105: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.wakeup:(Z)V
        //   108: ldc2_w          100
        //   111: invokestatic    java/lang/Thread.sleep:(J)V
        //   114: goto            118
        //   117: astore_3       
        //   118: iconst_0       
        //   119: ireturn        
        //   120: iconst_1       
        //   121: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void cancelDelayedTasks() {
        if (this.delayedTaskQueue.isEmpty()) {
            return;
        }
        final ScheduledFutureTask[] array = (ScheduledFutureTask[])this.delayedTaskQueue.toArray(new ScheduledFutureTask[this.delayedTaskQueue.size()]);
        while (0 < array.length) {
            array[0].cancel(false);
            int n = 0;
            ++n;
        }
        this.delayedTaskQueue.clear();
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (this.inEventLoop()) {
            throw new IllegalStateException("cannot await termination of the current thread");
        }
        if (this.threadLock.tryAcquire(n, timeUnit)) {
            this.threadLock.release();
        }
        return this.isTerminated();
    }
    
    @Override
    public void execute(final Runnable p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       14
        //     4: new             Ljava/lang/NullPointerException;
        //     7: dup            
        //     8: ldc             "task"
        //    10: invokespecial   java/lang/NullPointerException.<init>:(Ljava/lang/String;)V
        //    13: athrow         
        //    14: aload_0        
        //    15: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.inEventLoop:()Z
        //    18: istore_2       
        //    19: iload_2        
        //    20: ifeq            31
        //    23: aload_0        
        //    24: aload_1        
        //    25: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.addTask:(Ljava/lang/Runnable;)V
        //    28: goto            49
        //    31: aload_0        
        //    32: invokespecial   io/netty/util/concurrent/SingleThreadEventExecutor.startThread:()V
        //    35: aload_0        
        //    36: aload_1        
        //    37: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.addTask:(Ljava/lang/Runnable;)V
        //    40: aload_0        
        //    41: if_icmplt       49
        //    44: aload_0        
        //    45: aload_1        
        //    46: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.removeTask:(Ljava/lang/Runnable;)Z
        //    49: aload_0        
        //    50: getfield        io/netty/util/concurrent/SingleThreadEventExecutor.addTaskWakesUp:Z
        //    53: ifne            69
        //    56: aload_0        
        //    57: aload_1        
        //    58: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.wakesUpForTask:(Ljava/lang/Runnable;)Z
        //    61: ifeq            69
        //    64: aload_0        
        //    65: iload_2        
        //    66: invokevirtual   io/netty/util/concurrent/SingleThreadEventExecutor.wakeup:(Z)V
        //    69: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected boolean wakesUpForTask(final Runnable runnable) {
        return true;
    }
    
    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }
    
    @Override
    public ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", n));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, runnable, null, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n))));
    }
    
    @Override
    public ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        if (callable == null) {
            throw new NullPointerException("callable");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", n));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n))));
    }
    
    @Override
    public ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", n));
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", n2));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(runnable, (Object)null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n)), timeUnit.toNanos(n2)));
    }
    
    @Override
    public ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", n));
        }
        if (n2 <= 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", n2));
        }
        return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(runnable, (Object)null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(n)), -timeUnit.toNanos(n2)));
    }
    
    private ScheduledFuture schedule(final ScheduledFutureTask scheduledFutureTask) {
        if (scheduledFutureTask == null) {
            throw new NullPointerException("task");
        }
        if (this.inEventLoop()) {
            this.delayedTaskQueue.add(scheduledFutureTask);
        }
        else {
            this.execute(new Runnable(scheduledFutureTask) {
                final ScheduledFutureTask val$task;
                final SingleThreadEventExecutor this$0;
                
                @Override
                public void run() {
                    this.this$0.delayedTaskQueue.add(this.val$task);
                }
            });
        }
        return scheduledFutureTask;
    }
    
    private void startThread() {
        if (SingleThreadEventExecutor.STATE_UPDATER.get(this) == 1 && SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(this, 1, 2)) {
            this.delayedTaskQueue.add(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(null), (Object)null), ScheduledFutureTask.deadlineNanos(SingleThreadEventExecutor.SCHEDULE_PURGE_INTERVAL), -SingleThreadEventExecutor.SCHEDULE_PURGE_INTERVAL));
            this.thread.start();
        }
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture scheduleWithFixedDelay(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.scheduleWithFixedDelay(runnable, n, n2, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture scheduleAtFixedRate(final Runnable runnable, final long n, final long n2, final TimeUnit timeUnit) {
        return this.scheduleAtFixedRate(runnable, n, n2, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture schedule(final Callable callable, final long n, final TimeUnit timeUnit) {
        return this.schedule(callable, n, timeUnit);
    }
    
    @Override
    public java.util.concurrent.ScheduledFuture schedule(final Runnable runnable, final long n, final TimeUnit timeUnit) {
        return this.schedule(runnable, n, timeUnit);
    }
    
    static InternalLogger access$000() {
        return SingleThreadEventExecutor.logger;
    }
    
    static AtomicIntegerFieldUpdater access$100() {
        return SingleThreadEventExecutor.STATE_UPDATER;
    }
    
    static long access$200(final SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.gracefulShutdownStartTime;
    }
    
    static Semaphore access$300(final SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.threadLock;
    }
    
    static Queue access$400(final SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.taskQueue;
    }
    
    static Promise access$500(final SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.terminationFuture;
    }
    
    static Set access$600(final SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.shutdownHooks;
    }
    
    static {
        $assertionsDisabled = !SingleThreadEventExecutor.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
        WAKEUP_TASK = new Runnable() {
            @Override
            public void run() {
            }
        };
        AtomicIntegerFieldUpdater<SingleThreadEventExecutor> state_UPDATER = (AtomicIntegerFieldUpdater<SingleThreadEventExecutor>)PlatformDependent.newAtomicIntegerFieldUpdater(SingleThreadEventExecutor.class, "state");
        if (state_UPDATER == null) {
            state_UPDATER = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");
        }
        STATE_UPDATER = state_UPDATER;
        SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
    }
    
    private final class PurgeTask implements Runnable
    {
        final SingleThreadEventExecutor this$0;
        
        private PurgeTask(final SingleThreadEventExecutor this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            final Iterator iterator = this.this$0.delayedTaskQueue.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isCancelled()) {
                    iterator.remove();
                }
            }
        }
        
        PurgeTask(final SingleThreadEventExecutor singleThreadEventExecutor, final SingleThreadEventExecutor$1 runnable) {
            this(singleThreadEventExecutor);
        }
    }
}
