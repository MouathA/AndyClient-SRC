package io.netty.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.util.*;

public class HashedWheelTimer implements Timer
{
    static final InternalLogger logger;
    private static final ResourceLeakDetector leakDetector;
    private static final AtomicIntegerFieldUpdater WORKER_STATE_UPDATER;
    private final ResourceLeak leak;
    private final Worker worker;
    private final Thread workerThread;
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_STARTED = 1;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    private int workerState;
    private final long tickDuration;
    private final HashedWheelBucket[] wheel;
    private final int mask;
    private final CountDownLatch startTimeInitialized;
    private final Queue timeouts;
    private final Queue cancelledTimeouts;
    private long startTime;
    
    public HashedWheelTimer() {
        this(Executors.defaultThreadFactory());
    }
    
    public HashedWheelTimer(final long n, final TimeUnit timeUnit) {
        this(Executors.defaultThreadFactory(), n, timeUnit);
    }
    
    public HashedWheelTimer(final long n, final TimeUnit timeUnit, final int n2) {
        this(Executors.defaultThreadFactory(), n, timeUnit, n2);
    }
    
    public HashedWheelTimer(final ThreadFactory threadFactory) {
        this(threadFactory, 100L, TimeUnit.MILLISECONDS);
    }
    
    public HashedWheelTimer(final ThreadFactory threadFactory, final long n, final TimeUnit timeUnit) {
        this(threadFactory, n, timeUnit, 512);
    }
    
    public HashedWheelTimer(final ThreadFactory threadFactory, final long n, final TimeUnit timeUnit, final int n2) {
        this.worker = new Worker(null);
        this.workerState = 0;
        this.startTimeInitialized = new CountDownLatch(1);
        this.timeouts = PlatformDependent.newMpscQueue();
        this.cancelledTimeouts = PlatformDependent.newMpscQueue();
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n <= 0L) {
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + n);
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + n2);
        }
        this.wheel = createWheel(n2);
        this.mask = this.wheel.length - 1;
        this.tickDuration = timeUnit.toNanos(n);
        if (this.tickDuration >= Long.MAX_VALUE / this.wheel.length) {
            throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", n, Long.MAX_VALUE / this.wheel.length));
        }
        this.workerThread = threadFactory.newThread(this.worker);
        this.leak = HashedWheelTimer.leakDetector.open(this);
    }
    
    private static HashedWheelBucket[] createWheel(int normalizeTicksPerWheel) {
        if (normalizeTicksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + normalizeTicksPerWheel);
        }
        if (normalizeTicksPerWheel > 1073741824) {
            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + normalizeTicksPerWheel);
        }
        normalizeTicksPerWheel = normalizeTicksPerWheel(normalizeTicksPerWheel);
        final HashedWheelBucket[] array = new HashedWheelBucket[normalizeTicksPerWheel];
        while (0 < array.length) {
            array[0] = new HashedWheelBucket(null);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private static int normalizeTicksPerWheel(final int n) {
        while (1 < n) {}
        return 1;
    }
    
    public void start() {
        switch (HashedWheelTimer.WORKER_STATE_UPDATER.get(this)) {
            case 0: {
                if (HashedWheelTimer.WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
                    this.workerThread.start();
                    break;
                }
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                throw new IllegalStateException("cannot be started once stopped");
            }
            default: {
                throw new Error("Invalid WorkerState");
            }
        }
        while (this.startTime == 0L) {
            this.startTimeInitialized.await();
        }
    }
    
    @Override
    public Set stop() {
        if (Thread.currentThread() == this.workerThread) {
            throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
        }
        if (!HashedWheelTimer.WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
            HashedWheelTimer.WORKER_STATE_UPDATER.set(this, 2);
            if (this.leak != null) {
                this.leak.close();
            }
            return Collections.emptySet();
        }
        while (this.workerThread.isAlive()) {
            this.workerThread.interrupt();
            this.workerThread.join(100L);
        }
        Thread.currentThread().interrupt();
        if (this.leak != null) {
            this.leak.close();
        }
        return this.worker.unprocessedTimeouts();
    }
    
    @Override
    public Timeout newTimeout(final TimerTask timerTask, final long n, final TimeUnit timeUnit) {
        if (timerTask == null) {
            throw new NullPointerException("task");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        this.start();
        final HashedWheelTimeout hashedWheelTimeout = new HashedWheelTimeout(this, timerTask, System.nanoTime() + timeUnit.toNanos(n) - this.startTime);
        this.timeouts.add(hashedWheelTimeout);
        return hashedWheelTimeout;
    }
    
    static long access$202(final HashedWheelTimer hashedWheelTimer, final long startTime) {
        return hashedWheelTimer.startTime = startTime;
    }
    
    static long access$200(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.startTime;
    }
    
    static CountDownLatch access$300(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.startTimeInitialized;
    }
    
    static int access$400(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.mask;
    }
    
    static HashedWheelBucket[] access$500(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.wheel;
    }
    
    static AtomicIntegerFieldUpdater access$600() {
        return HashedWheelTimer.WORKER_STATE_UPDATER;
    }
    
    static Queue access$700(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.timeouts;
    }
    
    static long access$900(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.tickDuration;
    }
    
    static Queue access$1000(final HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.cancelledTimeouts;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
        leakDetector = new ResourceLeakDetector(HashedWheelTimer.class, 1, Runtime.getRuntime().availableProcessors() * 4);
        AtomicIntegerFieldUpdater<HashedWheelTimer> worker_STATE_UPDATER = (AtomicIntegerFieldUpdater<HashedWheelTimer>)PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimer.class, "workerState");
        if (worker_STATE_UPDATER == null) {
            worker_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
        }
        WORKER_STATE_UPDATER = worker_STATE_UPDATER;
    }
    
    private static final class HashedWheelBucket
    {
        private HashedWheelTimeout head;
        private HashedWheelTimeout tail;
        static final boolean $assertionsDisabled;
        
        private HashedWheelBucket() {
        }
        
        public void addTimeout(final HashedWheelTimeout hashedWheelTimeout) {
            assert hashedWheelTimeout.bucket == null;
            hashedWheelTimeout.bucket = this;
            if (this.head == null) {
                this.tail = hashedWheelTimeout;
                this.head = hashedWheelTimeout;
            }
            else {
                this.tail.next = hashedWheelTimeout;
                hashedWheelTimeout.prev = this.tail;
                this.tail = hashedWheelTimeout;
            }
        }
        
        public void expireTimeouts(final long n) {
            HashedWheelTimeout next;
            for (HashedWheelTimeout head = this.head; head != null; head = next) {
                if (head.remainingRounds <= 0L) {
                    if (HashedWheelTimeout.access$800(head) > n) {
                        throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", HashedWheelTimeout.access$800(head), n));
                    }
                    head.expire();
                }
                else if (!head.isCancelled()) {
                    final HashedWheelTimeout hashedWheelTimeout = head;
                    --hashedWheelTimeout.remainingRounds;
                }
                next = head.next;
                if (true) {
                    this.remove(head);
                }
            }
        }
        
        public void remove(final HashedWheelTimeout hashedWheelTimeout) {
            final HashedWheelTimeout next = hashedWheelTimeout.next;
            if (hashedWheelTimeout.prev != null) {
                hashedWheelTimeout.prev.next = next;
            }
            if (hashedWheelTimeout.next != null) {
                hashedWheelTimeout.next.prev = hashedWheelTimeout.prev;
            }
            if (hashedWheelTimeout == this.head) {
                if (hashedWheelTimeout == this.tail) {
                    this.tail = null;
                    this.head = null;
                }
                else {
                    this.head = next;
                }
            }
            else if (hashedWheelTimeout == this.tail) {
                this.tail = hashedWheelTimeout.prev;
            }
            hashedWheelTimeout.prev = null;
            hashedWheelTimeout.next = null;
            hashedWheelTimeout.bucket = null;
        }
        
        public void clearTimeouts(final Set set) {
            while (true) {
                final HashedWheelTimeout pollTimeout = this.pollTimeout();
                if (pollTimeout == null) {
                    break;
                }
                if (pollTimeout.isExpired()) {
                    continue;
                }
                if (pollTimeout.isCancelled()) {
                    continue;
                }
                set.add(pollTimeout);
            }
        }
        
        private HashedWheelTimeout pollTimeout() {
            final HashedWheelTimeout head = this.head;
            if (head == null) {
                return null;
            }
            final HashedWheelTimeout next = head.next;
            if (next == null) {
                final HashedWheelTimeout hashedWheelTimeout = null;
                this.head = hashedWheelTimeout;
                this.tail = hashedWheelTimeout;
            }
            else {
                this.head = next;
                next.prev = null;
            }
            head.next = null;
            head.prev = null;
            head.bucket = null;
            return head;
        }
        
        HashedWheelBucket(final HashedWheelTimer$1 object) {
            this();
        }
        
        static {
            $assertionsDisabled = !HashedWheelTimer.class.desiredAssertionStatus();
        }
    }
    
    private static final class HashedWheelTimeout extends MpscLinkedQueueNode implements Timeout
    {
        private static final int ST_INIT = 0;
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;
        private static final AtomicIntegerFieldUpdater STATE_UPDATER;
        private final HashedWheelTimer timer;
        private final TimerTask task;
        private final long deadline;
        private int state;
        long remainingRounds;
        HashedWheelTimeout next;
        HashedWheelTimeout prev;
        HashedWheelBucket bucket;
        
        HashedWheelTimeout(final HashedWheelTimer timer, final TimerTask task, final long deadline) {
            this.state = 0;
            this.timer = timer;
            this.task = task;
            this.deadline = deadline;
        }
        
        @Override
        public Timer timer() {
            return this.timer;
        }
        
        @Override
        public TimerTask task() {
            return this.task;
        }
        
        @Override
        public boolean cancel() {
            if (!this.compareAndSetState(0, 1)) {
                return false;
            }
            HashedWheelTimer.access$1000(this.timer).add(new Runnable() {
                final HashedWheelTimeout this$0;
                
                @Override
                public void run() {
                    final HashedWheelBucket bucket = this.this$0.bucket;
                    if (bucket != null) {
                        bucket.remove(this.this$0);
                    }
                }
            });
            return true;
        }
        
        public boolean compareAndSetState(final int n, final int n2) {
            return HashedWheelTimeout.STATE_UPDATER.compareAndSet(this, n, n2);
        }
        
        public int state() {
            return this.state;
        }
        
        @Override
        public boolean isCancelled() {
            return this.state() == 1;
        }
        
        @Override
        public boolean isExpired() {
            return this.state() == 2;
        }
        
        @Override
        public HashedWheelTimeout value() {
            return this;
        }
        
        public void expire() {
            if (!this.compareAndSetState(0, 2)) {
                return;
            }
            this.task.run(this);
        }
        
        @Override
        public String toString() {
            final long n = this.deadline - System.nanoTime() + HashedWheelTimer.access$200(this.timer);
            final StringBuilder sb = new StringBuilder(192);
            sb.append(StringUtil.simpleClassName(this));
            sb.append('(');
            sb.append("deadline: ");
            if (n > 0L) {
                sb.append(n);
                sb.append(" ns later");
            }
            else if (n < 0L) {
                sb.append(-n);
                sb.append(" ns ago");
            }
            else {
                sb.append("now");
            }
            if (this.isCancelled()) {
                sb.append(", cancelled");
            }
            sb.append(", task: ");
            sb.append(this.task());
            return sb.append(')').toString();
        }
        
        @Override
        public Object value() {
            return this.value();
        }
        
        static long access$800(final HashedWheelTimeout hashedWheelTimeout) {
            return hashedWheelTimeout.deadline;
        }
        
        static {
            AtomicIntegerFieldUpdater<HashedWheelTimeout> state_UPDATER = (AtomicIntegerFieldUpdater<HashedWheelTimeout>)PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimeout.class, "state");
            if (state_UPDATER == null) {
                state_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
            }
            STATE_UPDATER = state_UPDATER;
        }
    }
    
    private final class Worker implements Runnable
    {
        private final Set unprocessedTimeouts;
        private long tick;
        final HashedWheelTimer this$0;
        
        private Worker(final HashedWheelTimer this$0) {
            this.this$0 = this$0;
            this.unprocessedTimeouts = new HashSet();
        }
        
        @Override
        public void run() {
            HashedWheelTimer.access$202(this.this$0, System.nanoTime());
            if (HashedWheelTimer.access$200(this.this$0) == 0L) {
                HashedWheelTimer.access$202(this.this$0, 1L);
            }
            HashedWheelTimer.access$300(this.this$0).countDown();
            int n = 0;
            do {
                final long waitForNextTick = this.waitForNextTick();
                if (waitForNextTick > 0L) {
                    n = (int)(this.tick & (long)HashedWheelTimer.access$400(this.this$0));
                    this.processCancelledTasks();
                    final HashedWheelBucket hashedWheelBucket = HashedWheelTimer.access$500(this.this$0)[0];
                    this.transferTimeoutsToBuckets();
                    hashedWheelBucket.expireTimeouts(waitForNextTick);
                    ++this.tick;
                }
            } while (HashedWheelTimer.access$600().get(this.this$0) == 1);
            final HashedWheelBucket[] access$500 = HashedWheelTimer.access$500(this.this$0);
            while (0 < access$500.length) {
                access$500[0].clearTimeouts(this.unprocessedTimeouts);
                ++n;
            }
            while (true) {
                final HashedWheelTimeout hashedWheelTimeout = HashedWheelTimer.access$700(this.this$0).poll();
                if (hashedWheelTimeout == null) {
                    break;
                }
                if (hashedWheelTimeout.isCancelled()) {
                    continue;
                }
                this.unprocessedTimeouts.add(hashedWheelTimeout);
            }
            this.processCancelledTasks();
        }
        
        private void transferTimeoutsToBuckets() {
            while (true) {
                final HashedWheelTimeout hashedWheelTimeout = HashedWheelTimer.access$700(this.this$0).poll();
                if (hashedWheelTimeout == null) {
                    break;
                }
                if (hashedWheelTimeout.state() != 1) {
                    final long n = HashedWheelTimeout.access$800(hashedWheelTimeout) / HashedWheelTimer.access$900(this.this$0);
                    hashedWheelTimeout.remainingRounds = (n - this.tick) / HashedWheelTimer.access$500(this.this$0).length;
                    HashedWheelTimer.access$500(this.this$0)[(int)(Math.max(n, this.tick) & (long)HashedWheelTimer.access$400(this.this$0))].addTimeout(hashedWheelTimeout);
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        private void processCancelledTasks() {
            while (true) {
                final Runnable runnable = HashedWheelTimer.access$1000(this.this$0).poll();
                if (runnable == null) {
                    break;
                }
                runnable.run();
            }
        }
        
        private long waitForNextTick() {
            final long n = HashedWheelTimer.access$900(this.this$0) * (this.tick + 1L);
            long n2;
            while (true) {
                n2 = System.nanoTime() - HashedWheelTimer.access$200(this.this$0);
                long n3 = (n - n2 + 999999L) / 1000000L;
                if (n3 <= 0L) {
                    break;
                }
                if (PlatformDependent.isWindows()) {
                    n3 = n3 / 10L * 10L;
                }
                Thread.sleep(n3);
            }
            if (n2 == Long.MIN_VALUE) {
                return -9223372036854775807L;
            }
            return n2;
        }
        
        public Set unprocessedTimeouts() {
            return Collections.unmodifiableSet((Set<?>)this.unprocessedTimeouts);
        }
        
        Worker(final HashedWheelTimer hashedWheelTimer, final HashedWheelTimer$1 object) {
            this(hashedWheelTimer);
        }
    }
}
