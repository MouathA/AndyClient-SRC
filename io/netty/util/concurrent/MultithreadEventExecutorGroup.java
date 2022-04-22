package io.netty.util.concurrent;

import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.concurrent.*;

public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup
{
    private final EventExecutor[] children;
    private final AtomicInteger childIndex;
    private final AtomicInteger terminatedChildren;
    private final Promise terminationFuture;
    private final EventExecutorChooser chooser;
    
    protected MultithreadEventExecutorGroup(final int p0, final ThreadFactory p1, final Object... p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   io/netty/util/concurrent/AbstractEventExecutorGroup.<init>:()V
        //     4: aload_0        
        //     5: new             Ljava/util/concurrent/atomic/AtomicInteger;
        //     8: dup            
        //     9: invokespecial   java/util/concurrent/atomic/AtomicInteger.<init>:()V
        //    12: putfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.childIndex:Ljava/util/concurrent/atomic/AtomicInteger;
        //    15: aload_0        
        //    16: new             Ljava/util/concurrent/atomic/AtomicInteger;
        //    19: dup            
        //    20: invokespecial   java/util/concurrent/atomic/AtomicInteger.<init>:()V
        //    23: putfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.terminatedChildren:Ljava/util/concurrent/atomic/AtomicInteger;
        //    26: aload_0        
        //    27: new             Lio/netty/util/concurrent/DefaultPromise;
        //    30: dup            
        //    31: getstatic       io/netty/util/concurrent/GlobalEventExecutor.INSTANCE:Lio/netty/util/concurrent/GlobalEventExecutor;
        //    34: invokespecial   io/netty/util/concurrent/DefaultPromise.<init>:(Lio/netty/util/concurrent/EventExecutor;)V
        //    37: putfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.terminationFuture:Lio/netty/util/concurrent/Promise;
        //    40: iload_1        
        //    41: ifgt            68
        //    44: new             Ljava/lang/IllegalArgumentException;
        //    47: dup            
        //    48: ldc             "nThreads: %d (expected: > 0)"
        //    50: iconst_1       
        //    51: anewarray       Ljava/lang/Object;
        //    54: dup            
        //    55: iconst_0       
        //    56: iload_1        
        //    57: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    60: aastore        
        //    61: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    64: invokespecial   java/lang/IllegalArgumentException.<init>:(Ljava/lang/String;)V
        //    67: athrow         
        //    68: aload_2        
        //    69: ifnonnull       77
        //    72: aload_0        
        //    73: invokevirtual   io/netty/util/concurrent/MultithreadEventExecutorGroup.newDefaultThreadFactory:()Ljava/util/concurrent/ThreadFactory;
        //    76: astore_2       
        //    77: aload_0        
        //    78: iload_1        
        //    79: anewarray       Lio/netty/util/concurrent/SingleThreadEventExecutor;
        //    82: putfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //    85: aload_0        
        //    86: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //    89: arraylength    
        //    90: invokestatic    io/netty/util/concurrent/MultithreadEventExecutorGroup.isPowerOfTwo:(I)Z
        //    93: ifeq            112
        //    96: aload_0        
        //    97: new             Lio/netty/util/concurrent/MultithreadEventExecutorGroup$PowerOfTwoEventExecutorChooser;
        //   100: dup            
        //   101: aload_0        
        //   102: aconst_null    
        //   103: invokespecial   io/netty/util/concurrent/MultithreadEventExecutorGroup$PowerOfTwoEventExecutorChooser.<init>:(Lio/netty/util/concurrent/MultithreadEventExecutorGroup;Lio/netty/util/concurrent/MultithreadEventExecutorGroup$1;)V
        //   106: putfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.chooser:Lio/netty/util/concurrent/MultithreadEventExecutorGroup$EventExecutorChooser;
        //   109: goto            125
        //   112: aload_0        
        //   113: new             Lio/netty/util/concurrent/MultithreadEventExecutorGroup$GenericEventExecutorChooser;
        //   116: dup            
        //   117: aload_0        
        //   118: aconst_null    
        //   119: invokespecial   io/netty/util/concurrent/MultithreadEventExecutorGroup$GenericEventExecutorChooser.<init>:(Lio/netty/util/concurrent/MultithreadEventExecutorGroup;Lio/netty/util/concurrent/MultithreadEventExecutorGroup$1;)V
        //   122: putfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.chooser:Lio/netty/util/concurrent/MultithreadEventExecutorGroup$EventExecutorChooser;
        //   125: iconst_0       
        //   126: iload_1        
        //   127: if_icmpge       344
        //   130: aload_0        
        //   131: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //   134: iconst_0       
        //   135: aload_0        
        //   136: aload_2        
        //   137: aload_3        
        //   138: invokevirtual   io/netty/util/concurrent/MultithreadEventExecutorGroup.newChild:(Ljava/util/concurrent/ThreadFactory;[Ljava/lang/Object;)Lio/netty/util/concurrent/EventExecutor;
        //   141: aastore        
        //   142: iconst_1       
        //   143: ifne            338
        //   146: iconst_0       
        //   147: iconst_0       
        //   148: if_icmpge       169
        //   151: aload_0        
        //   152: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //   155: iconst_0       
        //   156: aaload         
        //   157: invokeinterface io/netty/util/concurrent/EventExecutor.shutdownGracefully:()Lio/netty/util/concurrent/Future;
        //   162: pop            
        //   163: iinc            6, 1
        //   166: goto            146
        //   169: iconst_0       
        //   170: iconst_0       
        //   171: if_icmpge       229
        //   174: aload_0        
        //   175: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //   178: iconst_0       
        //   179: aaload         
        //   180: astore          7
        //   182: aload           7
        //   184: invokeinterface io/netty/util/concurrent/EventExecutor.isTerminated:()Z
        //   189: ifne            209
        //   192: aload           7
        //   194: ldc2_w          2147483647
        //   197: getstatic       java/util/concurrent/TimeUnit.SECONDS:Ljava/util/concurrent/TimeUnit;
        //   200: invokeinterface io/netty/util/concurrent/EventExecutor.awaitTermination:(JLjava/util/concurrent/TimeUnit;)Z
        //   205: pop            
        //   206: goto            182
        //   209: goto            223
        //   212: astore          8
        //   214: invokestatic    java/lang/Thread.currentThread:()Ljava/lang/Thread;
        //   217: invokevirtual   java/lang/Thread.interrupt:()V
        //   220: goto            229
        //   223: iinc            6, 1
        //   226: goto            169
        //   229: goto            338
        //   232: astore          6
        //   234: new             Ljava/lang/IllegalStateException;
        //   237: dup            
        //   238: ldc             "failed to create a child event loop"
        //   240: aload           6
        //   242: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   245: athrow         
        //   246: astore          9
        //   248: iconst_1       
        //   249: ifne            335
        //   252: iconst_0       
        //   253: iconst_0       
        //   254: if_icmpge       275
        //   257: aload_0        
        //   258: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //   261: iconst_0       
        //   262: aaload         
        //   263: invokeinterface io/netty/util/concurrent/EventExecutor.shutdownGracefully:()Lio/netty/util/concurrent/Future;
        //   268: pop            
        //   269: iinc            10, 1
        //   272: goto            252
        //   275: iconst_0       
        //   276: iconst_0       
        //   277: if_icmpge       335
        //   280: aload_0        
        //   281: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //   284: iconst_0       
        //   285: aaload         
        //   286: astore          11
        //   288: aload           11
        //   290: invokeinterface io/netty/util/concurrent/EventExecutor.isTerminated:()Z
        //   295: ifne            315
        //   298: aload           11
        //   300: ldc2_w          2147483647
        //   303: getstatic       java/util/concurrent/TimeUnit.SECONDS:Ljava/util/concurrent/TimeUnit;
        //   306: invokeinterface io/netty/util/concurrent/EventExecutor.awaitTermination:(JLjava/util/concurrent/TimeUnit;)Z
        //   311: pop            
        //   312: goto            288
        //   315: goto            329
        //   318: astore          12
        //   320: invokestatic    java/lang/Thread.currentThread:()Ljava/lang/Thread;
        //   323: invokevirtual   java/lang/Thread.interrupt:()V
        //   326: goto            335
        //   329: iinc            10, 1
        //   332: goto            275
        //   335: aload           9
        //   337: athrow         
        //   338: iinc            4, 1
        //   341: goto            125
        //   344: new             Lio/netty/util/concurrent/MultithreadEventExecutorGroup$1;
        //   347: dup            
        //   348: aload_0        
        //   349: invokespecial   io/netty/util/concurrent/MultithreadEventExecutorGroup$1.<init>:(Lio/netty/util/concurrent/MultithreadEventExecutorGroup;)V
        //   352: astore          4
        //   354: aload_0        
        //   355: getfield        io/netty/util/concurrent/MultithreadEventExecutorGroup.children:[Lio/netty/util/concurrent/EventExecutor;
        //   358: astore          5
        //   360: aload           5
        //   362: arraylength    
        //   363: istore          6
        //   365: iconst_0       
        //   366: iconst_0       
        //   367: if_icmpge       397
        //   370: aload           5
        //   372: iconst_0       
        //   373: aaload         
        //   374: astore          8
        //   376: aload           8
        //   378: invokeinterface io/netty/util/concurrent/EventExecutor.terminationFuture:()Lio/netty/util/concurrent/Future;
        //   383: aload           4
        //   385: invokeinterface io/netty/util/concurrent/Future.addListener:(Lio/netty/util/concurrent/GenericFutureListener;)Lio/netty/util/concurrent/Future;
        //   390: pop            
        //   391: iinc            7, 1
        //   394: goto            365
        //   397: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(this.getClass());
    }
    
    @Override
    public EventExecutor next() {
        return this.chooser.next();
    }
    
    @Override
    public Iterator iterator() {
        return this.children().iterator();
    }
    
    public final int executorCount() {
        return this.children.length;
    }
    
    protected Set children() {
        final Set<Object> setFromMap = Collections.newSetFromMap(new LinkedHashMap<Object, Boolean>());
        Collections.addAll(setFromMap, this.children);
        return setFromMap;
    }
    
    protected abstract EventExecutor newChild(final ThreadFactory p0, final Object... p1) throws Exception;
    
    @Override
    public Future shutdownGracefully(final long n, final long n2, final TimeUnit timeUnit) {
        final EventExecutor[] children = this.children;
        while (0 < children.length) {
            children[0].shutdownGracefully(n, n2, timeUnit);
            int n3 = 0;
            ++n3;
        }
        return this.terminationFuture();
    }
    
    @Override
    public Future terminationFuture() {
        return this.terminationFuture;
    }
    
    @Deprecated
    @Override
    public void shutdown() {
        final EventExecutor[] children = this.children;
        while (0 < children.length) {
            children[0].shutdown();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean isShuttingDown() {
        final EventExecutor[] children = this.children;
        while (0 < children.length) {
            if (!children[0].isShuttingDown()) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public boolean isShutdown() {
        final EventExecutor[] children = this.children;
        while (0 < children.length) {
            if (!children[0].isShutdown()) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public boolean isTerminated() {
        final EventExecutor[] children = this.children;
        while (0 < children.length) {
            if (!children[0].isTerminated()) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final long n2 = System.nanoTime() + timeUnit.toNanos(n);
        final EventExecutor[] children = this.children;
    Label_0079:
        while (0 < children.length) {
            final EventExecutor eventExecutor = children[0];
            while (true) {
                final long n3 = n2 - System.nanoTime();
                if (n3 <= 0L) {
                    break Label_0079;
                }
                if (eventExecutor.awaitTermination(n3, TimeUnit.NANOSECONDS)) {
                    int n4 = 0;
                    ++n4;
                    break;
                }
            }
        }
        return this.isTerminated();
    }
    
    private static boolean isPowerOfTwo(final int n) {
        return (n & -n) == n;
    }
    
    static AtomicInteger access$200(final MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.terminatedChildren;
    }
    
    static EventExecutor[] access$300(final MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.children;
    }
    
    static Promise access$400(final MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.terminationFuture;
    }
    
    static AtomicInteger access$500(final MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.childIndex;
    }
    
    private final class GenericEventExecutorChooser implements EventExecutorChooser
    {
        final MultithreadEventExecutorGroup this$0;
        
        private GenericEventExecutorChooser(final MultithreadEventExecutorGroup this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public EventExecutor next() {
            return MultithreadEventExecutorGroup.access$300(this.this$0)[Math.abs(MultithreadEventExecutorGroup.access$500(this.this$0).getAndIncrement() % MultithreadEventExecutorGroup.access$300(this.this$0).length)];
        }
        
        GenericEventExecutorChooser(final MultithreadEventExecutorGroup multithreadEventExecutorGroup, final MultithreadEventExecutorGroup$1 futureListener) {
            this(multithreadEventExecutorGroup);
        }
    }
    
    private interface EventExecutorChooser
    {
        EventExecutor next();
    }
    
    private final class PowerOfTwoEventExecutorChooser implements EventExecutorChooser
    {
        final MultithreadEventExecutorGroup this$0;
        
        private PowerOfTwoEventExecutorChooser(final MultithreadEventExecutorGroup this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public EventExecutor next() {
            return MultithreadEventExecutorGroup.access$300(this.this$0)[MultithreadEventExecutorGroup.access$500(this.this$0).getAndIncrement() & MultithreadEventExecutorGroup.access$300(this.this$0).length - 1];
        }
        
        PowerOfTwoEventExecutorChooser(final MultithreadEventExecutorGroup multithreadEventExecutorGroup, final MultithreadEventExecutorGroup$1 futureListener) {
            this(multithreadEventExecutorGroup);
        }
    }
}
