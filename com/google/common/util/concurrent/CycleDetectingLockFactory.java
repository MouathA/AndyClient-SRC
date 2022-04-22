package com.google.common.util.concurrent;

import javax.annotation.concurrent.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.collect.*;
import java.util.logging.*;

@Beta
@ThreadSafe
public class CycleDetectingLockFactory
{
    private static final ConcurrentMap lockGraphNodesPerType;
    private static final Logger logger;
    final Policy policy;
    private static final ThreadLocal acquiredLocks;
    
    public static CycleDetectingLockFactory newInstance(final Policy policy) {
        return new CycleDetectingLockFactory(policy);
    }
    
    public ReentrantLock newReentrantLock(final String s) {
        return this.newReentrantLock(s, false);
    }
    
    public ReentrantLock newReentrantLock(final String s, final boolean b) {
        return (this.policy == Policies.DISABLED) ? new ReentrantLock(b) : new CycleDetectingReentrantLock(new LockGraphNode(s), b, null);
    }
    
    public ReentrantReadWriteLock newReentrantReadWriteLock(final String s) {
        return this.newReentrantReadWriteLock(s, false);
    }
    
    public ReentrantReadWriteLock newReentrantReadWriteLock(final String s, final boolean b) {
        return (this.policy == Policies.DISABLED) ? new ReentrantReadWriteLock(b) : new CycleDetectingReentrantReadWriteLock(new LockGraphNode(s), b, null);
    }
    
    public static WithExplicitOrdering newInstanceWithExplicitOrdering(final Class clazz, final Policy policy) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(policy);
        return new WithExplicitOrdering(policy, getOrCreateNodes(clazz));
    }
    
    private static Map getOrCreateNodes(final Class clazz) {
        final Map map = (Map)CycleDetectingLockFactory.lockGraphNodesPerType.get(clazz);
        if (map != null) {
            return map;
        }
        final Map nodes = createNodes(clazz);
        return (Map)Objects.firstNonNull(CycleDetectingLockFactory.lockGraphNodesPerType.putIfAbsent(clazz, nodes), nodes);
    }
    
    @VisibleForTesting
    static Map createNodes(final Class p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/google/common/collect/Maps.newEnumMap:(Ljava/lang/Class;)Ljava/util/EnumMap;
        //     4: astore_1       
        //     5: aload_0        
        //     6: invokevirtual   java/lang/Class.getEnumConstants:()[Ljava/lang/Object;
        //     9: checkcast       [Ljava/lang/Enum;
        //    12: astore_2       
        //    13: aload_2        
        //    14: arraylength    
        //    15: istore_3       
        //    16: iload_3        
        //    17: invokestatic    com/google/common/collect/Lists.newArrayListWithCapacity:(I)Ljava/util/ArrayList;
        //    20: astore          4
        //    22: aload_2        
        //    23: astore          5
        //    25: aload           5
        //    27: arraylength    
        //    28: istore          6
        //    30: iconst_0       
        //    31: iload           6
        //    33: if_icmpge       79
        //    36: aload           5
        //    38: iconst_0       
        //    39: aaload         
        //    40: astore          8
        //    42: new             Lcom/google/common/util/concurrent/CycleDetectingLockFactory$LockGraphNode;
        //    45: dup            
        //    46: aload           8
        //    48: invokestatic    com/google/common/util/concurrent/CycleDetectingLockFactory.getLockName:(Ljava/lang/Enum;)Ljava/lang/String;
        //    51: invokespecial   com/google/common/util/concurrent/CycleDetectingLockFactory$LockGraphNode.<init>:(Ljava/lang/String;)V
        //    54: astore          9
        //    56: aload           4
        //    58: aload           9
        //    60: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    63: pop            
        //    64: aload_1        
        //    65: aload           8
        //    67: aload           9
        //    69: invokevirtual   java/util/EnumMap.put:(Ljava/lang/Enum;Ljava/lang/Object;)Ljava/lang/Object;
        //    72: pop            
        //    73: iinc            7, 1
        //    76: goto            30
        //    79: iconst_0       
        //    80: iload_3        
        //    81: if_icmpge       112
        //    84: aload           4
        //    86: iconst_0       
        //    87: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    90: checkcast       Lcom/google/common/util/concurrent/CycleDetectingLockFactory$LockGraphNode;
        //    93: getstatic       com/google/common/util/concurrent/CycleDetectingLockFactory$Policies.THROW:Lcom/google/common/util/concurrent/CycleDetectingLockFactory$Policies;
        //    96: aload           4
        //    98: iconst_0       
        //    99: iconst_0       
        //   100: invokevirtual   java/util/ArrayList.subList:(II)Ljava/util/List;
        //   103: invokevirtual   com/google/common/util/concurrent/CycleDetectingLockFactory$LockGraphNode.checkAcquiredLocks:(Lcom/google/common/util/concurrent/CycleDetectingLockFactory$Policy;Ljava/util/List;)V
        //   106: iinc            5, 1
        //   109: goto            79
        //   112: iconst_0       
        //   113: iload_3        
        //   114: iconst_1       
        //   115: isub           
        //   116: if_icmpge       147
        //   119: aload           4
        //   121: iconst_0       
        //   122: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   125: checkcast       Lcom/google/common/util/concurrent/CycleDetectingLockFactory$LockGraphNode;
        //   128: getstatic       com/google/common/util/concurrent/CycleDetectingLockFactory$Policies.DISABLED:Lcom/google/common/util/concurrent/CycleDetectingLockFactory$Policies;
        //   131: aload           4
        //   133: iconst_1       
        //   134: iload_3        
        //   135: invokevirtual   java/util/ArrayList.subList:(II)Ljava/util/List;
        //   138: invokevirtual   com/google/common/util/concurrent/CycleDetectingLockFactory$LockGraphNode.checkAcquiredLocks:(Lcom/google/common/util/concurrent/CycleDetectingLockFactory$Policy;Ljava/util/List;)V
        //   141: iinc            5, 1
        //   144: goto            112
        //   147: aload_1        
        //   148: invokestatic    java/util/Collections.unmodifiableMap:(Ljava/util/Map;)Ljava/util/Map;
        //   151: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static String getLockName(final Enum enum1) {
        return enum1.getDeclaringClass().getSimpleName() + "." + enum1.name();
    }
    
    private CycleDetectingLockFactory(final Policy policy) {
        this.policy = (Policy)Preconditions.checkNotNull(policy);
    }
    
    private void aboutToAcquire(final CycleDetectingLock cycleDetectingLock) {
        if (!cycleDetectingLock.isAcquiredByCurrentThread()) {
            final ArrayList<LockGraphNode> list = CycleDetectingLockFactory.acquiredLocks.get();
            final LockGraphNode lockGraphNode = cycleDetectingLock.getLockGraphNode();
            lockGraphNode.checkAcquiredLocks(this.policy, list);
            list.add(lockGraphNode);
        }
    }
    
    private void lockStateChanged(final CycleDetectingLock cycleDetectingLock) {
        if (!cycleDetectingLock.isAcquiredByCurrentThread()) {
            final ArrayList list = CycleDetectingLockFactory.acquiredLocks.get();
            final LockGraphNode lockGraphNode = cycleDetectingLock.getLockGraphNode();
            for (int i = list.size() - 1; i >= 0; --i) {
                if (list.get(i) == lockGraphNode) {
                    list.remove(i);
                    break;
                }
            }
        }
    }
    
    static Logger access$100() {
        return CycleDetectingLockFactory.logger;
    }
    
    CycleDetectingLockFactory(final Policy policy, final CycleDetectingLockFactory$1 threadLocal) {
        this(policy);
    }
    
    static void access$600(final CycleDetectingLockFactory cycleDetectingLockFactory, final CycleDetectingLock cycleDetectingLock) {
        cycleDetectingLockFactory.aboutToAcquire(cycleDetectingLock);
    }
    
    static void access$700(final CycleDetectingLockFactory cycleDetectingLockFactory, final CycleDetectingLock cycleDetectingLock) {
        cycleDetectingLockFactory.lockStateChanged(cycleDetectingLock);
    }
    
    static {
        lockGraphNodesPerType = new MapMaker().weakKeys().makeMap();
        logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
        acquiredLocks = new ThreadLocal() {
            @Override
            protected ArrayList initialValue() {
                return Lists.newArrayListWithCapacity(3);
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
    }
    
    private class CycleDetectingReentrantWriteLock extends ReentrantReadWriteLock.WriteLock
    {
        final CycleDetectingReentrantReadWriteLock readWriteLock;
        final CycleDetectingLockFactory this$0;
        
        CycleDetectingReentrantWriteLock(final CycleDetectingLockFactory this$0, final CycleDetectingReentrantReadWriteLock readWriteLock) {
            this.this$0 = this$0;
            super(readWriteLock);
            this.readWriteLock = readWriteLock;
        }
        
        @Override
        public void lock() {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            super.lock();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            super.lockInterruptibly();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
        }
        
        @Override
        public boolean tryLock() {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            final boolean tryLock = super.tryLock();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
            return tryLock;
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            final boolean tryLock = super.tryLock(n, timeUnit);
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
            return tryLock;
        }
        
        @Override
        public void unlock() {
            super.unlock();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
        }
    }
    
    final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLock
    {
        private final CycleDetectingReentrantReadLock readLock;
        private final CycleDetectingReentrantWriteLock writeLock;
        private final LockGraphNode lockGraphNode;
        final CycleDetectingLockFactory this$0;
        
        private CycleDetectingReentrantReadWriteLock(final CycleDetectingLockFactory this$0, final LockGraphNode lockGraphNode, final boolean b) {
            this.this$0 = this$0;
            super(b);
            this.readLock = this$0.new CycleDetectingReentrantReadLock(this);
            this.writeLock = this$0.new CycleDetectingReentrantWriteLock(this);
            this.lockGraphNode = (LockGraphNode)Preconditions.checkNotNull(lockGraphNode);
        }
        
        @Override
        public ReadLock readLock() {
            return this.readLock;
        }
        
        @Override
        public WriteLock writeLock() {
            return this.writeLock;
        }
        
        @Override
        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }
        
        @Override
        public boolean isAcquiredByCurrentThread() {
            return this.isWriteLockedByCurrentThread() || this.getReadHoldCount() > 0;
        }
        
        @Override
        public Lock writeLock() {
            return this.writeLock();
        }
        
        @Override
        public Lock readLock() {
            return this.readLock();
        }
        
        CycleDetectingReentrantReadWriteLock(final CycleDetectingLockFactory cycleDetectingLockFactory, final LockGraphNode lockGraphNode, final boolean b, final CycleDetectingLockFactory$1 threadLocal) {
            this(cycleDetectingLockFactory, lockGraphNode, b);
        }
    }
    
    private class CycleDetectingReentrantReadLock extends ReentrantReadWriteLock.ReadLock
    {
        final CycleDetectingReentrantReadWriteLock readWriteLock;
        final CycleDetectingLockFactory this$0;
        
        CycleDetectingReentrantReadLock(final CycleDetectingLockFactory this$0, final CycleDetectingReentrantReadWriteLock readWriteLock) {
            this.this$0 = this$0;
            super(readWriteLock);
            this.readWriteLock = readWriteLock;
        }
        
        @Override
        public void lock() {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            super.lock();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            super.lockInterruptibly();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
        }
        
        @Override
        public boolean tryLock() {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            final boolean tryLock = super.tryLock();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
            return tryLock;
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.access$600(this.this$0, this.readWriteLock);
            final boolean tryLock = super.tryLock(n, timeUnit);
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
            return tryLock;
        }
        
        @Override
        public void unlock() {
            super.unlock();
            CycleDetectingLockFactory.access$700(this.this$0, this.readWriteLock);
        }
    }
    
    private interface CycleDetectingLock
    {
        LockGraphNode getLockGraphNode();
        
        boolean isAcquiredByCurrentThread();
    }
    
    private static class LockGraphNode
    {
        final Map allowedPriorLocks;
        final Map disallowedPriorLocks;
        final String lockName;
        
        LockGraphNode(final String s) {
            this.allowedPriorLocks = new MapMaker().weakKeys().makeMap();
            this.disallowedPriorLocks = new MapMaker().weakKeys().makeMap();
            this.lockName = (String)Preconditions.checkNotNull(s);
        }
        
        String getLockName() {
            return this.lockName;
        }
        
        void checkAcquiredLocks(final Policy policy, final List list) {
            while (0 < list.size()) {
                this.checkAcquiredLock(policy, list.get(0));
                int n = 0;
                ++n;
            }
        }
        
        void checkAcquiredLock(final Policy policy, final LockGraphNode lockGraphNode) {
            Preconditions.checkState(this != lockGraphNode, (Object)("Attempted to acquire multiple locks with the same rank " + lockGraphNode.getLockName()));
            if (this.allowedPriorLocks.containsKey(lockGraphNode)) {
                return;
            }
            final PotentialDeadlockException ex = this.disallowedPriorLocks.get(lockGraphNode);
            if (ex != null) {
                policy.handlePotentialDeadlock(new PotentialDeadlockException(lockGraphNode, this, ex.getConflictingStackTrace(), null));
                return;
            }
            final ExampleStackTrace pathTo = lockGraphNode.findPathTo(this, Sets.newIdentityHashSet());
            if (pathTo == null) {
                this.allowedPriorLocks.put(lockGraphNode, new ExampleStackTrace(lockGraphNode, this));
            }
            else {
                final PotentialDeadlockException ex2 = new PotentialDeadlockException(lockGraphNode, this, pathTo, null);
                this.disallowedPriorLocks.put(lockGraphNode, ex2);
                policy.handlePotentialDeadlock(ex2);
            }
        }
        
        @Nullable
        private ExampleStackTrace findPathTo(final LockGraphNode lockGraphNode, final Set set) {
            if (!set.add(this)) {
                return null;
            }
            final ExampleStackTrace exampleStackTrace = this.allowedPriorLocks.get(lockGraphNode);
            if (exampleStackTrace != null) {
                return exampleStackTrace;
            }
            for (final Map.Entry<LockGraphNode, V> entry : this.allowedPriorLocks.entrySet()) {
                final LockGraphNode lockGraphNode2 = entry.getKey();
                final ExampleStackTrace pathTo = lockGraphNode2.findPathTo(lockGraphNode, set);
                if (pathTo != null) {
                    final ExampleStackTrace exampleStackTrace2 = new ExampleStackTrace(lockGraphNode2, this);
                    exampleStackTrace2.setStackTrace(((ExampleStackTrace)entry.getValue()).getStackTrace());
                    exampleStackTrace2.initCause(pathTo);
                    return exampleStackTrace2;
                }
            }
            return null;
        }
    }
    
    private static class ExampleStackTrace extends IllegalStateException
    {
        static final StackTraceElement[] EMPTY_STACK_TRACE;
        static Set EXCLUDED_CLASS_NAMES;
        
        ExampleStackTrace(final LockGraphNode lockGraphNode, final LockGraphNode lockGraphNode2) {
            super(lockGraphNode.getLockName() + " -> " + lockGraphNode2.getLockName());
            final StackTraceElement[] stackTrace = this.getStackTrace();
            final int length = stackTrace.length;
            while (0 < length) {
                if (WithExplicitOrdering.class.getName().equals(stackTrace[0].getClassName())) {
                    this.setStackTrace(ExampleStackTrace.EMPTY_STACK_TRACE);
                    break;
                }
                if (!ExampleStackTrace.EXCLUDED_CLASS_NAMES.contains(stackTrace[0].getClassName())) {
                    this.setStackTrace(Arrays.copyOfRange(stackTrace, 0, length));
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        
        static {
            EMPTY_STACK_TRACE = new StackTraceElement[0];
            ExampleStackTrace.EXCLUDED_CLASS_NAMES = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), LockGraphNode.class.getName());
        }
    }
    
    @Beta
    public static final class WithExplicitOrdering extends CycleDetectingLockFactory
    {
        private final Map lockGraphNodes;
        
        @VisibleForTesting
        WithExplicitOrdering(final Policy policy, final Map lockGraphNodes) {
            super(policy, null);
            this.lockGraphNodes = lockGraphNodes;
        }
        
        public ReentrantLock newReentrantLock(final Enum enum1) {
            return this.newReentrantLock(enum1, false);
        }
        
        public ReentrantLock newReentrantLock(final Enum enum1, final boolean b) {
            return (this.policy == Policies.DISABLED) ? new ReentrantLock(b) : new CycleDetectingReentrantLock(this.lockGraphNodes.get(enum1), b, null);
        }
        
        public ReentrantReadWriteLock newReentrantReadWriteLock(final Enum enum1) {
            return this.newReentrantReadWriteLock(enum1, false);
        }
        
        public ReentrantReadWriteLock newReentrantReadWriteLock(final Enum enum1, final boolean b) {
            return (this.policy == Policies.DISABLED) ? new ReentrantReadWriteLock(b) : new CycleDetectingReentrantReadWriteLock(this.lockGraphNodes.get(enum1), b, null);
        }
    }
    
    @Beta
    @ThreadSafe
    public interface Policy
    {
        void handlePotentialDeadlock(final PotentialDeadlockException p0);
    }
    
    @Beta
    public static final class PotentialDeadlockException extends ExampleStackTrace
    {
        private final ExampleStackTrace conflictingStackTrace;
        
        private PotentialDeadlockException(final LockGraphNode lockGraphNode, final LockGraphNode lockGraphNode2, final ExampleStackTrace conflictingStackTrace) {
            super(lockGraphNode, lockGraphNode2);
            this.initCause(this.conflictingStackTrace = conflictingStackTrace);
        }
        
        public ExampleStackTrace getConflictingStackTrace() {
            return this.conflictingStackTrace;
        }
        
        @Override
        public String getMessage() {
            final StringBuilder sb = new StringBuilder(super.getMessage());
            for (Throwable t = this.conflictingStackTrace; t != null; t = t.getCause()) {
                sb.append(", ").append(t.getMessage());
            }
            return sb.toString();
        }
        
        PotentialDeadlockException(final LockGraphNode lockGraphNode, final LockGraphNode lockGraphNode2, final ExampleStackTrace exampleStackTrace, final CycleDetectingLockFactory$1 threadLocal) {
            this(lockGraphNode, lockGraphNode2, exampleStackTrace);
        }
    }
    
    final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLock
    {
        private final LockGraphNode lockGraphNode;
        final CycleDetectingLockFactory this$0;
        
        private CycleDetectingReentrantLock(final CycleDetectingLockFactory this$0, final LockGraphNode lockGraphNode, final boolean b) {
            this.this$0 = this$0;
            super(b);
            this.lockGraphNode = (LockGraphNode)Preconditions.checkNotNull(lockGraphNode);
        }
        
        @Override
        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }
        
        @Override
        public boolean isAcquiredByCurrentThread() {
            return this.isHeldByCurrentThread();
        }
        
        @Override
        public void lock() {
            CycleDetectingLockFactory.access$600(this.this$0, this);
            super.lock();
            CycleDetectingLockFactory.access$700(this.this$0, this);
        }
        
        @Override
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.access$600(this.this$0, this);
            super.lockInterruptibly();
            CycleDetectingLockFactory.access$700(this.this$0, this);
        }
        
        @Override
        public boolean tryLock() {
            CycleDetectingLockFactory.access$600(this.this$0, this);
            final boolean tryLock = super.tryLock();
            CycleDetectingLockFactory.access$700(this.this$0, this);
            return tryLock;
        }
        
        @Override
        public boolean tryLock(final long n, final TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.access$600(this.this$0, this);
            final boolean tryLock = super.tryLock(n, timeUnit);
            CycleDetectingLockFactory.access$700(this.this$0, this);
            return tryLock;
        }
        
        @Override
        public void unlock() {
            super.unlock();
            CycleDetectingLockFactory.access$700(this.this$0, this);
        }
        
        CycleDetectingReentrantLock(final CycleDetectingLockFactory cycleDetectingLockFactory, final LockGraphNode lockGraphNode, final boolean b, final CycleDetectingLockFactory$1 threadLocal) {
            this(cycleDetectingLockFactory, lockGraphNode, b);
        }
    }
    
    @Beta
    public enum Policies implements Policy
    {
        THROW {
            @Override
            public void handlePotentialDeadlock(final PotentialDeadlockException ex) {
                throw ex;
            }
        }, 
        WARN {
            @Override
            public void handlePotentialDeadlock(final PotentialDeadlockException ex) {
                CycleDetectingLockFactory.access$100().log(Level.SEVERE, "Detected potential deadlock", ex);
            }
        }, 
        DISABLED {
            @Override
            public void handlePotentialDeadlock(final PotentialDeadlockException ex) {
            }
        };
        
        private static final Policies[] $VALUES;
        
        private Policies(final String s, final int n) {
        }
        
        Policies(final String s, final int n, final CycleDetectingLockFactory$1 threadLocal) {
            this(s, n);
        }
        
        static {
            $VALUES = new Policies[] { Policies.THROW, Policies.WARN, Policies.DISABLED };
        }
    }
}
