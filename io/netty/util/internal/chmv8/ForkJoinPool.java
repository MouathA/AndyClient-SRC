package io.netty.util.internal.chmv8;

import sun.misc.*;
import java.security.*;
import io.netty.util.internal.*;
import java.util.*;
import java.util.concurrent.*;

public class ForkJoinPool extends AbstractExecutorService
{
    static final ThreadLocal submitters;
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    private static final RuntimePermission modifyThreadPermission;
    static final ForkJoinPool common;
    private static int poolNumberSequence;
    private static final long IDLE_TIMEOUT = 2000000000L;
    private static final long FAST_IDLE_TIMEOUT = 200000000L;
    private static final long TIMEOUT_SLOP = 2000000L;
    private static final int MAX_HELP = 64;
    private static final int SEED_INCREMENT = 1640531527;
    private static final int AC_SHIFT = 48;
    private static final int TC_SHIFT = 32;
    private static final int ST_SHIFT = 31;
    private static final int EC_SHIFT = 16;
    private static final int SMASK = 65535;
    private static final int MAX_CAP = 32767;
    private static final int EVENMASK = 65534;
    private static final int SQMASK = 126;
    private static final int SHORT_SIGN = 32768;
    private static final int INT_SIGN = Integer.MIN_VALUE;
    private static final long STOP_BIT = 2147483648L;
    private static final long AC_MASK = -281474976710656L;
    private static final long TC_MASK = 281470681743360L;
    private static final long TC_UNIT = 4294967296L;
    private static final long AC_UNIT = 281474976710656L;
    private static final int UAC_SHIFT = 16;
    private static final int UTC_SHIFT = 0;
    private static final int UAC_MASK = -65536;
    private static final int UTC_MASK = 65535;
    private static final int UAC_UNIT = 65536;
    private static final int UTC_UNIT = 1;
    private static final int E_MASK = Integer.MAX_VALUE;
    private static final int E_SEQ = 65536;
    private static final int SHUTDOWN = Integer.MIN_VALUE;
    private static final int PL_LOCK = 2;
    private static final int PL_SIGNAL = 1;
    private static final int PL_SPINS = 256;
    static final int LIFO_QUEUE = 0;
    static final int FIFO_QUEUE = 1;
    static final int SHARED_QUEUE = -1;
    long pad00;
    long pad01;
    long pad02;
    long pad03;
    long pad04;
    long pad05;
    long pad06;
    long stealCount;
    long ctl;
    int plock;
    int indexSeed;
    final short parallelism;
    final short mode;
    WorkQueue[] workQueues;
    final ForkJoinWorkerThreadFactory factory;
    final Thread.UncaughtExceptionHandler ueh;
    final String workerNamePrefix;
    Object pad10;
    Object pad11;
    Object pad12;
    Object pad13;
    Object pad14;
    Object pad15;
    Object pad16;
    Object pad17;
    Object pad18;
    Object pad19;
    Object pad1a;
    Object pad1b;
    private static final Unsafe U;
    private static final long CTL;
    private static final long PARKBLOCKER;
    private static final int ABASE;
    private static final int ASHIFT;
    private static final long STEALCOUNT;
    private static final long PLOCK;
    private static final long INDEXSEED;
    private static final long QBASE;
    private static final long QLOCK;
    
    private static void checkPermission() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ForkJoinPool.modifyThreadPermission);
        }
    }
    
    private static final synchronized int nextPoolId() {
        return ++ForkJoinPool.poolNumberSequence;
    }
    
    private int acquirePlock() {
        int plock;
        int n;
        while (((plock = this.plock) & 0x2) != 0x0 || !ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, plock, n = plock + 2)) {
            if (256 >= 0) {
                if (ThreadLocalRandom.current().nextInt() < 0) {
                    continue;
                }
                int n2 = 0;
                --n2;
            }
            else {
                if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, plock, plock | 0x1)) {
                    continue;
                }
                // monitorenter(this)
                if ((this.plock & 0x1) != 0x0) {
                    this.wait();
                }
                else {
                    this.notifyAll();
                }
            }
            // monitorexit(this)
        }
        return n;
    }
    
    private void releasePlock(final int plock) {
        this.plock = plock;
        // monitorenter(this)
        this.notifyAll();
    }
    // monitorexit(this)
    
    private void tryAddWorker() {
        long ctl;
        int n;
        int n2;
        while ((n = (int)((ctl = this.ctl) >>> 32)) < 0 && (n & 0x8000) != 0x0 && (n2 = (int)ctl) >= 0) {
            if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, (long)((n + 1 & 0xFFFF) | (n + 65536 & 0xFFFF0000)) << 32 | (long)n2)) {
                final Throwable t = null;
                ForkJoinWorkerThread thread = null;
                final ForkJoinWorkerThreadFactory factory;
                if ((factory = this.factory) != null && (thread = factory.newThread(this)) != null) {
                    thread.start();
                    break;
                }
                this.deregisterWorker(thread, t);
                break;
            }
        }
    }
    
    final WorkQueue registerWorker(final ForkJoinWorkerThread forkJoinWorkerThread) {
        forkJoinWorkerThread.setDaemon(true);
        final Thread.UncaughtExceptionHandler ueh;
        if ((ueh = this.ueh) != null) {
            forkJoinWorkerThread.setUncaughtExceptionHandler(ueh);
        }
        Unsafe u;
        long indexseed;
        int indexSeed;
        int n;
        do {
            u = ForkJoinPool.U;
            indexseed = ForkJoinPool.INDEXSEED;
            indexSeed = this.indexSeed;
            n = indexSeed + 1640531527;
        } while (!u.compareAndSwapInt(this, indexseed, indexSeed, n) || n == 0);
        final WorkQueue workQueue = new WorkQueue(this, forkJoinWorkerThread, this.mode, n);
        int n2 = 0;
        Label_0108: {
            if (((n2 = this.plock) & 0x2) == 0x0) {
                final Unsafe u2 = ForkJoinPool.U;
                final long plock = ForkJoinPool.PLOCK;
                final int n3 = n2;
                n2 += 2;
                if (u2.compareAndSwapInt(this, plock, n3, n2)) {
                    break Label_0108;
                }
            }
            n2 = this.acquirePlock();
        }
        final int n4 = (n2 & Integer.MIN_VALUE) | (n2 + 2 & Integer.MAX_VALUE);
        WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            int length = workQueues.length;
            int n5 = length - 1;
            int eventCount;
            if (workQueues[eventCount = ((n << 1 | 0x1) & n5)] != null) {
                while (workQueues[eventCount = (eventCount + ((length <= 4) ? 2 : ((length >>> 1 & 0xFFFE) + 2)) & n5)] != null) {
                    int n6 = 0;
                    ++n6;
                    if (0 >= length) {
                        workQueues = (this.workQueues = Arrays.copyOf(workQueues, length <<= 1));
                        n5 = length - 1;
                    }
                }
            }
            workQueue.poolIndex = (short)eventCount;
            workQueues[workQueue.eventCount = eventCount] = workQueue;
        }
        if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, n2, n4)) {
            this.releasePlock(n4);
        }
        forkJoinWorkerThread.setName(this.workerNamePrefix.concat(Integer.toString(workQueue.poolIndex >>> 1)));
        return workQueue;
    }
    
    final void deregisterWorker(final ForkJoinWorkerThread forkJoinWorkerThread, final Throwable t) {
        WorkQueue workQueue = null;
        if (forkJoinWorkerThread != null && (workQueue = forkJoinWorkerThread.workQueue) != null) {
            workQueue.qlock = -1;
            Unsafe u;
            long stealcount;
            long stealCount;
            do {
                u = ForkJoinPool.U;
                stealcount = ForkJoinPool.STEALCOUNT;
                stealCount = this.stealCount;
            } while (!u.compareAndSwapLong(this, stealcount, stealCount, stealCount + workQueue.nsteals));
            int n = 0;
            Label_0086: {
                if (((n = this.plock) & 0x2) == 0x0) {
                    final Unsafe u2 = ForkJoinPool.U;
                    final long plock = ForkJoinPool.PLOCK;
                    final int n2 = n;
                    n += 2;
                    if (u2.compareAndSwapInt(this, plock, n2, n)) {
                        break Label_0086;
                    }
                }
                n = this.acquirePlock();
            }
            final int n3 = (n & Integer.MIN_VALUE) | (n + 2 & Integer.MAX_VALUE);
            final short poolIndex = workQueue.poolIndex;
            final WorkQueue[] workQueues = this.workQueues;
            if (workQueues != null && poolIndex >= 0 && poolIndex < workQueues.length && workQueues[poolIndex] == workQueue) {
                workQueues[poolIndex] = null;
            }
            if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, n, n3)) {
                this.releasePlock(n3);
            }
        }
        long ctl;
        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl = this.ctl, (ctl - 281474976710656L & 0xFFFF000000000000L) | (ctl - 4294967296L & 0xFFFF00000000L) | (ctl & 0xFFFFFFFFL))) {}
        if (!this.tryTerminate(false, false) && workQueue != null && workQueue.array != null) {
            workQueue.cancelAll();
            long ctl2;
            int n4;
            int n5;
            while ((n4 = (int)((ctl2 = this.ctl) >>> 32)) < 0 && (n5 = (int)ctl2) >= 0) {
                if (n5 > 0) {
                    final WorkQueue[] workQueues2;
                    final int n6;
                    if ((workQueues2 = this.workQueues) == null || (n6 = (n5 & 0xFFFF)) >= workQueues2.length) {
                        break;
                    }
                    final WorkQueue workQueue2;
                    if ((workQueue2 = workQueues2[n6]) == null) {
                        break;
                    }
                    final long n7 = (long)(workQueue2.nextWait & Integer.MAX_VALUE) | (long)(n4 + 65536) << 32;
                    if (workQueue2.eventCount != (n5 | Integer.MIN_VALUE)) {
                        break;
                    }
                    if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl2, n7)) {
                        continue;
                    }
                    workQueue2.eventCount = (n5 + 65536 & Integer.MAX_VALUE);
                    final Thread parker;
                    if ((parker = workQueue2.parker) != null) {
                        ForkJoinPool.U.unpark(parker);
                        break;
                    }
                    break;
                }
                else {
                    if ((short)n4 < 0) {
                        this.tryAddWorker();
                        break;
                    }
                    break;
                }
            }
        }
        if (t != null) {
            ForkJoinTask.rethrow(t);
        }
    }
    
    final void externalPush(final ForkJoinTask forkJoinTask) {
        final Submitter submitter = ForkJoinPool.submitters.get();
        final int plock = this.plock;
        final WorkQueue[] workQueues = this.workQueues;
        final int n;
        final int seed;
        final WorkQueue workQueue;
        if (submitter != null && plock > 0 && workQueues != null && (n = workQueues.length - 1) >= 0 && (workQueue = workQueues[n & (seed = submitter.seed) & 0x7E]) != null && seed != 0 && ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 0, 1)) {
            final ForkJoinTask[] array;
            if ((array = workQueue.array) != null) {
                final int n2 = array.length - 1;
                final int top;
                final int n3;
                if (n2 > (n3 = (top = workQueue.top) - workQueue.base)) {
                    ForkJoinPool.U.putOrderedObject(array, ((n2 & top) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE, forkJoinTask);
                    workQueue.top = top + 1;
                    workQueue.qlock = 0;
                    if (n3 <= 1) {
                        this.signalWork(workQueues, workQueue);
                    }
                    return;
                }
            }
            workQueue.qlock = 0;
        }
        this.fullExternalPush(forkJoinTask);
    }
    
    private void fullExternalPush(final ForkJoinTask forkJoinTask) {
        Submitter submitter = ForkJoinPool.submitters.get();
        while (true) {
            if (submitter == null) {
                if (ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.INDEXSEED, this.indexSeed, 0) && false) {
                    ForkJoinPool.submitters.set(submitter = new Submitter(0));
                }
            }
            else if (!false) {
                final int seed = submitter.seed;
                submitter.seed = 0;
            }
            final int plock;
            if ((plock = this.plock) < 0) {
                throw new RejectedExecutionException();
            }
            final WorkQueue[] workQueues;
            final int n;
            if (plock == 0 || (workQueues = this.workQueues) == null || (n = workQueues.length - 1) < 0) {
                final short parallelism = this.parallelism;
                final int n2 = (parallelism > 1) ? (parallelism - 1) : 1;
                final int n3 = n2 | n2 >>> 1;
                final int n4 = n3 | n3 >>> 2;
                final int n5 = n4 | n4 >>> 4;
                final int n6 = n5 | n5 >>> 8;
                final int n7 = (n6 | n6 >>> 16) + 1 << 1;
                final WorkQueue[] workQueues3;
                final WorkQueue[] workQueues2 = (WorkQueue[])(((workQueues3 = this.workQueues) == null || workQueues3.length == 0) ? new WorkQueue[n7] : null);
                int n8 = 0;
                Label_0259: {
                    if (((n8 = this.plock) & 0x2) == 0x0) {
                        final Unsafe u = ForkJoinPool.U;
                        final long plock2 = ForkJoinPool.PLOCK;
                        final int n9 = n8;
                        n8 += 2;
                        if (u.compareAndSwapInt(this, plock2, n9, n8)) {
                            break Label_0259;
                        }
                    }
                    n8 = this.acquirePlock();
                }
                final WorkQueue[] workQueues4;
                if (((workQueues4 = this.workQueues) == null || workQueues4.length == 0) && workQueues2 != null) {
                    this.workQueues = workQueues2;
                }
                final int n10 = (n8 & Integer.MIN_VALUE) | (n8 + 2 & Integer.MAX_VALUE);
                if (ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, n8, n10)) {
                    continue;
                }
                this.releasePlock(n10);
            }
            else {
                final int n11;
                final WorkQueue workQueue;
                if ((workQueue = workQueues[n11 = (0x0 & n & 0x7E)]) != null) {
                    if (workQueue.qlock != 0 || !ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 0, 1)) {
                        continue;
                    }
                    ForkJoinTask[] array = workQueue.array;
                    final int top = workQueue.top;
                    if ((array != null && array.length > top + 1 - workQueue.base) || (array = workQueue.growArray()) != null) {
                        ForkJoinPool.U.putOrderedObject(array, ((array.length - 1 & top) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE, forkJoinTask);
                        workQueue.top = top + 1;
                    }
                    workQueue.qlock = 0;
                    if (true) {
                        this.signalWork(workQueues, workQueue);
                        return;
                    }
                    continue;
                }
                else {
                    if ((this.plock & 0x2) != 0x0) {
                        continue;
                    }
                    final WorkQueue workQueue2 = new WorkQueue(this, null, -1, 0);
                    workQueue2.poolIndex = (short)n11;
                    int n12 = 0;
                    Label_0562: {
                        if (((n12 = this.plock) & 0x2) == 0x0) {
                            final Unsafe u2 = ForkJoinPool.U;
                            final long plock3 = ForkJoinPool.PLOCK;
                            final int n13 = n12;
                            n12 += 2;
                            if (u2.compareAndSwapInt(this, plock3, n13, n12)) {
                                break Label_0562;
                            }
                        }
                        n12 = this.acquirePlock();
                    }
                    final WorkQueue[] workQueues5;
                    if ((workQueues5 = this.workQueues) != null && n11 < workQueues5.length && workQueues5[n11] == null) {
                        workQueues5[n11] = workQueue2;
                    }
                    final int n14 = (n12 & Integer.MIN_VALUE) | (n12 + 2 & Integer.MAX_VALUE);
                    if (ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, n12, n14)) {
                        continue;
                    }
                    this.releasePlock(n14);
                }
            }
        }
    }
    
    final void incrementActiveCount() {
        long ctl;
        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl = this.ctl, (ctl & 0xFFFFFFFFFFFFL) | (ctl & 0xFFFF000000000000L) + 281474976710656L)) {}
    }
    
    final void signalWork(final WorkQueue[] array, final WorkQueue workQueue) {
        long ctl;
        int n;
        while ((n = (int)((ctl = this.ctl) >>> 32)) < 0) {
            final int n2;
            if ((n2 = (int)ctl) <= 0) {
                if ((short)n < 0) {
                    this.tryAddWorker();
                }
            }
            else {
                final int n3;
                if (array != null && array.length > (n3 = (n2 & 0xFFFF))) {
                    final WorkQueue workQueue2;
                    if ((workQueue2 = array[n3]) != null) {
                        final long n4 = (long)(workQueue2.nextWait & Integer.MAX_VALUE) | (long)(n + 65536) << 32;
                        final int eventCount = n2 + 65536 & Integer.MAX_VALUE;
                        if (workQueue2.eventCount == (n2 | Integer.MIN_VALUE) && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n4)) {
                            workQueue2.eventCount = eventCount;
                            final Thread parker;
                            if ((parker = workQueue2.parker) != null) {
                                ForkJoinPool.U.unpark(parker);
                            }
                        }
                        else if (workQueue == null || workQueue.base < workQueue.top) {
                            continue;
                        }
                    }
                }
            }
        }
    }
    
    final void runWorker(final WorkQueue workQueue) {
        workQueue.growArray();
        int n2;
        for (int hint = workQueue.hint; this.scan(workQueue, hint) == 0; hint = (n2 ^ n2 << 5)) {
            final int n = hint ^ hint << 13;
            n2 = (n ^ n >>> 17);
        }
    }
    
    private final int scan(final WorkQueue workQueue, final int n) {
        final long ctl = this.ctl;
        final WorkQueue[] workQueues;
        final int n2;
        if ((workQueues = this.workQueues) != null && (n2 = workQueues.length - 1) >= 0 && workQueue != null) {
            int n3 = n2 + n2 + 1;
            final int eventCount = workQueue.eventCount;
            WorkQueue workQueue2;
            int base;
            ForkJoinTask[] array;
            while ((workQueue2 = workQueues[n - n3 & n2]) == null || (base = workQueue2.base) - workQueue2.top >= 0 || (array = workQueue2.array) == null) {
                if (--n3 < 0) {
                    final int nextWait;
                    if ((eventCount | (nextWait = (int)ctl)) < 0) {
                        return this.awaitWork(workQueue, ctl, eventCount);
                    }
                    if (this.ctl == ctl) {
                        final long n4 = (long)eventCount | (ctl - 281474976710656L & 0xFFFFFFFF00000000L);
                        workQueue.nextWait = nextWait;
                        workQueue.eventCount = (eventCount | Integer.MIN_VALUE);
                        if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl, n4)) {
                            workQueue.eventCount = eventCount;
                        }
                        return 0;
                    }
                    return 0;
                }
            }
            final long n5 = ((array.length - 1 & base) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
            final ForkJoinTask forkJoinTask;
            if ((forkJoinTask = (ForkJoinTask)ForkJoinPool.U.getObjectVolatile(array, n5)) != null) {
                if (eventCount < 0) {
                    this.helpRelease(ctl, workQueues, workQueue, workQueue2, base);
                }
                else if (workQueue2.base == base && ForkJoinPool.U.compareAndSwapObject(array, n5, forkJoinTask, null)) {
                    ForkJoinPool.U.putOrderedInt(workQueue2, ForkJoinPool.QBASE, base + 1);
                    if (base + 1 - workQueue2.top < 0) {
                        this.signalWork(workQueues, workQueue2);
                    }
                    workQueue.runTask(forkJoinTask);
                }
            }
        }
        return 0;
    }
    
    private final int awaitWork(final WorkQueue workQueue, final long n, final int n2) {
        int qlock;
        if ((qlock = workQueue.qlock) >= 0 && workQueue.eventCount == n2 && this.ctl == n && !Thread.interrupted()) {
            final int n3 = (int)n;
            final int n4 = (int)(n >>> 32);
            final int n5 = (n4 >> 16) + this.parallelism;
            if (n3 < 0 || (n5 <= 0 && this.tryTerminate(false, false))) {
                final int qlock2 = -1;
                workQueue.qlock = qlock2;
                qlock = qlock2;
            }
            else {
                final int nsteals;
                if ((nsteals = workQueue.nsteals) != 0) {
                    workQueue.nsteals = 0;
                    Unsafe u;
                    long stealcount;
                    long stealCount;
                    do {
                        u = ForkJoinPool.U;
                        stealcount = ForkJoinPool.STEALCOUNT;
                        stealCount = this.stealCount;
                    } while (!u.compareAndSwapLong(this, stealcount, stealCount, stealCount + nsteals));
                }
                else {
                    final long n6 = (n5 > 0 || n2 != (n3 | Integer.MIN_VALUE)) ? 0L : ((long)(workQueue.nextWait & Integer.MAX_VALUE) | (long)(n4 + 65536) << 32);
                    long n8;
                    long n9;
                    if (n6 != 0L) {
                        final short n7 = (short)(-(short)(n >>> 32));
                        n8 = ((n7 < 0) ? 200000000L : ((n7 + 1) * 2000000000L));
                        n9 = System.nanoTime() + n8 - 2000000L;
                    }
                    else {
                        final long n10 = 0L;
                        n9 = 0L;
                        n8 = n10;
                    }
                    if (workQueue.eventCount == n2 && this.ctl == n) {
                        final Thread currentThread = Thread.currentThread();
                        ForkJoinPool.U.putObject(currentThread, ForkJoinPool.PARKBLOCKER, this);
                        workQueue.parker = currentThread;
                        if (workQueue.eventCount == n2 && this.ctl == n) {
                            ForkJoinPool.U.park(false, n8);
                        }
                        workQueue.parker = null;
                        ForkJoinPool.U.putObject(currentThread, ForkJoinPool.PARKBLOCKER, null);
                        if (n8 != 0L && this.ctl == n && n9 - System.nanoTime() <= 0L && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, n, n6)) {
                            final int qlock3 = -1;
                            workQueue.qlock = qlock3;
                            qlock = qlock3;
                        }
                    }
                }
            }
        }
        return qlock;
    }
    
    private final void helpRelease(final long n, final WorkQueue[] array, final WorkQueue workQueue, final WorkQueue workQueue2, final int n2) {
        final int n3;
        final int n4;
        final WorkQueue workQueue3;
        if (workQueue != null && workQueue.eventCount < 0 && (n3 = (int)n) > 0 && array != null && array.length > (n4 = (n3 & 0xFFFF)) && (workQueue3 = array[n4]) != null && this.ctl == n) {
            final long n5 = (long)(workQueue3.nextWait & Integer.MAX_VALUE) | (long)((int)(n >>> 32) + 65536) << 32;
            final int eventCount = n3 + 65536 & Integer.MAX_VALUE;
            if (workQueue2 != null && workQueue2.base == n2 && workQueue.eventCount < 0 && workQueue3.eventCount == (n3 | Integer.MIN_VALUE) && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, n, n5)) {
                workQueue3.eventCount = eventCount;
                final Thread parker;
                if ((parker = workQueue3.parker) != null) {
                    ForkJoinPool.U.unpark(parker);
                }
            }
        }
    }
    
    private int tryHelpStealer(final WorkQueue workQueue, final ForkJoinTask forkJoinTask) {
        Label_0469: {
            if (forkJoinTask != null && workQueue != null && workQueue.base - workQueue.top >= 0) {
            Label_0020:
                while (true) {
                    ForkJoinTask forkJoinTask2 = forkJoinTask;
                    WorkQueue workQueue2 = workQueue;
                    int status;
                Label_0026:
                    while ((status = forkJoinTask.status) >= 0) {
                        final WorkQueue[] workQueues;
                        if ((workQueues = this.workQueues) == null) {
                            break;
                        }
                        final int n;
                        if ((n = workQueues.length - 1) <= 0) {
                            break;
                        }
                        WorkQueue workQueue3 = null;
                        Label_0181: {
                            int i;
                            if ((workQueue3 = workQueues[i = ((workQueue2.hint | 0x1) & n)]) == null || workQueue3.currentSteal != forkJoinTask2) {
                                do {
                                    if (((i = (i + 2 & n)) & 0xF) == 0x1) {
                                        if (forkJoinTask2.status < 0) {
                                            continue Label_0020;
                                        }
                                        if (workQueue2.currentJoin != forkJoinTask2) {
                                            continue Label_0020;
                                        }
                                    }
                                    if ((workQueue3 = workQueues[i]) != null && workQueue3.currentSteal == forkJoinTask2) {
                                        workQueue2.hint = i;
                                        break Label_0181;
                                    }
                                } while (i != i);
                                break;
                            }
                        }
                        while (forkJoinTask2.status >= 0) {
                            final int base;
                            final ForkJoinTask[] array;
                            if ((base = workQueue3.base) - workQueue3.top < 0 && (array = workQueue3.array) != null) {
                                final int n2 = ((array.length - 1 & base) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                                ForkJoinTask pop = (ForkJoinTask)ForkJoinPool.U.getObjectVolatile(array, n2);
                                if (forkJoinTask2.status < 0 || workQueue2.currentJoin != forkJoinTask2) {
                                    break;
                                }
                                if (workQueue3.currentSteal != forkJoinTask2) {
                                    break;
                                }
                                if (workQueue3.base != base) {
                                    continue;
                                }
                                if (pop == null) {
                                    break Label_0469;
                                }
                                if (ForkJoinPool.U.compareAndSwapObject(array, n2, pop, null)) {
                                    ForkJoinPool.U.putOrderedInt(workQueue3, ForkJoinPool.QBASE, base + 1);
                                    final ForkJoinTask currentSteal = workQueue.currentSteal;
                                    final int top = workQueue.top;
                                    do {
                                        (workQueue.currentSteal = pop).doExec();
                                    } while (forkJoinTask.status >= 0 && workQueue.top != top && (pop = workQueue.pop()) != null);
                                    workQueue.currentSteal = currentSteal;
                                    break Label_0469;
                                }
                                continue;
                            }
                            else {
                                final ForkJoinTask currentJoin = workQueue3.currentJoin;
                                if (forkJoinTask2.status < 0 || workQueue2.currentJoin != forkJoinTask2) {
                                    break;
                                }
                                if (workQueue3.currentSteal != forkJoinTask2) {
                                    break;
                                }
                                if (currentJoin == null) {
                                    break Label_0469;
                                }
                                int n3 = 0;
                                ++n3;
                                if (0 == 64) {
                                    break Label_0469;
                                }
                                forkJoinTask2 = currentJoin;
                                workQueue2 = workQueue3;
                                continue Label_0026;
                            }
                        }
                        continue Label_0020;
                    }
                    break;
                }
            }
        }
        return 1;
    }
    
    private int helpComplete(final WorkQueue workQueue, final CountedCompleter countedCompleter) {
        final WorkQueue[] workQueues;
        final int n;
        if ((workQueues = this.workQueues) != null && (n = workQueues.length - 1) >= 0 && workQueue != null && countedCompleter != null) {
            int poolIndex = workQueue.poolIndex;
            final int n2 = n + n + 1;
            long ctl = 0L;
            int n3 = n2;
            while (countedCompleter.status >= 0) {
                if (workQueue.internalPopAndExecCC(countedCompleter)) {
                    n3 = n2;
                }
                else {
                    if (countedCompleter.status < 0) {
                        break;
                    }
                    final WorkQueue workQueue2;
                    if ((workQueue2 = workQueues[poolIndex & n]) != null && workQueue2.pollAndExecCC(countedCompleter)) {
                        n3 = n2;
                    }
                    else if (--n3 < 0) {
                        if (ctl == (ctl = this.ctl)) {
                            break;
                        }
                        n3 = n2;
                    }
                }
                poolIndex += 2;
            }
        }
        return 0;
    }
    
    final boolean tryCompensate(final long n) {
        final WorkQueue[] workQueues = this.workQueues;
        final short parallelism = this.parallelism;
        final int n2 = (int)n;
        final int n3;
        if (workQueues != null && (n3 = workQueues.length - 1) >= 0 && n2 >= 0 && this.ctl == n) {
            final WorkQueue workQueue = workQueues[n2 & n3];
            if (n2 != 0 && workQueue != null) {
                final long n4 = (long)(workQueue.nextWait & Integer.MAX_VALUE) | (n & 0xFFFFFFFF00000000L);
                final int eventCount = n2 + 65536 & Integer.MAX_VALUE;
                if (workQueue.eventCount == (n2 | Integer.MIN_VALUE) && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, n, n4)) {
                    workQueue.eventCount = eventCount;
                    final Thread parker;
                    if ((parker = workQueue.parker) != null) {
                        ForkJoinPool.U.unpark(parker);
                    }
                    return true;
                }
            }
            else {
                final short n5;
                if ((n5 = (short)(n >>> 32)) >= 0 && (int)(n >> 48) + parallelism > 1) {
                    if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, n, (n - 281474976710656L & 0xFFFF000000000000L) | (n & 0xFFFFFFFFFFFFL))) {
                        return true;
                    }
                }
                else if (n5 + parallelism < 32767 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, n, (n + 4294967296L & 0xFFFF00000000L) | (n & 0xFFFF0000FFFFFFFFL))) {
                    final Throwable t = null;
                    ForkJoinWorkerThread thread = null;
                    final ForkJoinWorkerThreadFactory factory;
                    if ((factory = this.factory) != null && (thread = factory.newThread(this)) != null) {
                        thread.start();
                        return true;
                    }
                    this.deregisterWorker(thread, t);
                }
            }
        }
        return false;
    }
    
    final int awaitJoin(final WorkQueue workQueue, final ForkJoinTask currentJoin) {
        final int status;
        if (currentJoin != null && (status = currentJoin.status) >= 0 && workQueue != null) {
            final ForkJoinTask currentJoin2 = workQueue.currentJoin;
            workQueue.currentJoin = currentJoin;
            int status2;
            while (workQueue.tryRemoveAndExec(currentJoin) && (status2 = currentJoin.status) >= 0) {}
            if (0 >= 0 && currentJoin instanceof CountedCompleter) {
                this.helpComplete(workQueue, (CountedCompleter)currentJoin);
            }
            long ctl = 0L;
            int status3;
            while (0 >= 0 && (status3 = currentJoin.status) >= 0) {
                final int status4;
                if (this.tryHelpStealer(workQueue, currentJoin) == 0 && (status4 = currentJoin.status) >= 0) {
                    if (!this.tryCompensate(ctl)) {
                        ctl = this.ctl;
                    }
                    else {
                        final int status5;
                        if (currentJoin.trySetSignal() && (status5 = currentJoin.status) >= 0) {
                            // monitorenter(currentJoin)
                            if (currentJoin.status >= 0) {
                                currentJoin.wait();
                            }
                            else {
                                currentJoin.notifyAll();
                            }
                        }
                        // monitorexit(currentJoin)
                        long ctl2;
                        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl2 = this.ctl, (ctl2 & 0xFFFFFFFFFFFFL) | (ctl2 & 0xFFFF000000000000L) + 281474976710656L)) {}
                    }
                }
            }
            workQueue.currentJoin = currentJoin2;
        }
        return 0;
    }
    
    final void helpJoinOnce(final WorkQueue workQueue, final ForkJoinTask currentJoin) {
        int n;
        if (workQueue != null && currentJoin != null && (n = currentJoin.status) >= 0) {
            final ForkJoinTask currentJoin2 = workQueue.currentJoin;
            workQueue.currentJoin = currentJoin;
            while (workQueue.tryRemoveAndExec(currentJoin) && (n = currentJoin.status) >= 0) {}
            if (n >= 0) {
                if (currentJoin instanceof CountedCompleter) {
                    this.helpComplete(workQueue, (CountedCompleter)currentJoin);
                }
                while (currentJoin.status >= 0 && this.tryHelpStealer(workQueue, currentJoin) > 0) {}
            }
            workQueue.currentJoin = currentJoin2;
        }
    }
    
    private WorkQueue findNonEmptyStealQueue() {
        final int nextInt = ThreadLocalRandom.current().nextInt();
        while (true) {
            final int plock = this.plock;
            final WorkQueue[] workQueues;
            final int n;
            if ((workQueues = this.workQueues) != null && (n = workQueues.length - 1) >= 0) {
                for (int i = n + 1 << 2; i >= 0; --i) {
                    final WorkQueue workQueue;
                    if ((workQueue = workQueues[(nextInt - i << 1 | 0x1) & n]) != null && workQueue.base - workQueue.top < 0) {
                        return workQueue;
                    }
                }
            }
            if (this.plock == plock) {
                return null;
            }
        }
    }
    
    final void helpQuiescePool(final WorkQueue workQueue) {
        final ForkJoinTask currentSteal = workQueue.currentSteal;
        while (true) {
            final ForkJoinTask nextLocalTask;
            if ((nextLocalTask = workQueue.nextLocalTask()) != null) {
                nextLocalTask.doExec();
            }
            else {
                final WorkQueue nonEmptyStealQueue;
                if ((nonEmptyStealQueue = this.findNonEmptyStealQueue()) != null) {
                    if (!false) {
                        long ctl;
                        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl = this.ctl, (ctl & 0xFFFFFFFFFFFFL) | (ctl & 0xFFFF000000000000L) + 281474976710656L)) {}
                    }
                    final int base;
                    final ForkJoinTask poll;
                    if ((base = nonEmptyStealQueue.base) - nonEmptyStealQueue.top >= 0 || (poll = nonEmptyStealQueue.pollAt(base)) == null) {
                        continue;
                    }
                    (workQueue.currentSteal = poll).doExec();
                    workQueue.currentSteal = currentSteal;
                }
                else if (false) {
                    final long ctl2;
                    final long n = ((ctl2 = this.ctl) & 0xFFFFFFFFFFFFL) | (ctl2 & 0xFFFF000000000000L) - 281474976710656L;
                    if ((int)(n >> 48) + this.parallelism == 0) {
                        break;
                    }
                    if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl2, n)) {}
                }
                else {
                    final long ctl3;
                    if ((int)((ctl3 = this.ctl) >> 48) + this.parallelism <= 0 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, ctl3, (ctl3 & 0xFFFFFFFFFFFFL) | (ctl3 & 0xFFFF000000000000L) + 281474976710656L)) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    final ForkJoinTask nextTaskFor(final WorkQueue workQueue) {
        ForkJoinTask nextLocalTask;
        while ((nextLocalTask = workQueue.nextLocalTask()) == null) {
            final WorkQueue nonEmptyStealQueue;
            if ((nonEmptyStealQueue = this.findNonEmptyStealQueue()) == null) {
                return null;
            }
            final int base;
            final ForkJoinTask poll;
            if ((base = nonEmptyStealQueue.base) - nonEmptyStealQueue.top < 0 && (poll = nonEmptyStealQueue.pollAt(base)) != null) {
                return poll;
            }
        }
        return nextLocalTask;
    }
    
    static int getSurplusQueuedTaskCount() {
        final Thread currentThread;
        if ((currentThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread forkJoinWorkerThread;
            final ForkJoinPool pool;
            final short parallelism = (pool = (forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread).pool).parallelism;
            final WorkQueue workQueue = forkJoinWorkerThread.workQueue;
            final int n = workQueue.top - workQueue.base;
            final int n2 = (int)(pool.ctl >> 48) + parallelism;
            final int n3;
            int n4;
            int n5;
            return n - ((n2 > (n3 = parallelism >>> 1)) ? 0 : ((n2 > (n4 = n3 >>> 1)) ? 1 : ((n2 > (n5 = n4 >>> 1)) ? 2 : ((n2 > n5 >>> 1) ? 4 : 8))));
        }
        return 0;
    }
    
    private boolean tryTerminate(final boolean p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.common:Lio/netty/util/internal/chmv8/ForkJoinPool;
        //     4: if_acmpne       9
        //     7: iconst_0       
        //     8: ireturn        
        //     9: aload_0        
        //    10: getfield        io/netty/util/internal/chmv8/ForkJoinPool.plock:I
        //    13: dup            
        //    14: istore_3       
        //    15: iflt            86
        //    18: iload_2        
        //    19: ifne            24
        //    22: iconst_0       
        //    23: ireturn        
        //    24: iload_3        
        //    25: iconst_2       
        //    26: iand           
        //    27: ifne            48
        //    30: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    33: aload_0        
        //    34: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.PLOCK:J
        //    37: iload_3        
        //    38: iinc            3, 2
        //    41: iload_3        
        //    42: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //    45: ifne            53
        //    48: aload_0        
        //    49: invokespecial   io/netty/util/internal/chmv8/ForkJoinPool.acquirePlock:()I
        //    52: istore_3       
        //    53: iload_3        
        //    54: iconst_2       
        //    55: iadd           
        //    56: ldc             2147483647
        //    58: iand           
        //    59: ldc             -2147483648
        //    61: ior            
        //    62: istore          4
        //    64: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    67: aload_0        
        //    68: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.PLOCK:J
        //    71: iload_3        
        //    72: iload           4
        //    74: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //    77: ifne            86
        //    80: aload_0        
        //    81: iload           4
        //    83: invokespecial   io/netty/util/internal/chmv8/ForkJoinPool.releasePlock:(I)V
        //    86: aload_0        
        //    87: getfield        io/netty/util/internal/chmv8/ForkJoinPool.ctl:J
        //    90: dup2           
        //    91: lstore          4
        //    93: ldc2_w          2147483648
        //    96: land           
        //    97: lconst_0       
        //    98: lcmp           
        //    99: ifeq            142
        //   102: lload           4
        //   104: bipush          32
        //   106: lushr          
        //   107: l2i            
        //   108: i2s            
        //   109: aload_0        
        //   110: getfield        io/netty/util/internal/chmv8/ForkJoinPool.parallelism:S
        //   113: iadd           
        //   114: ifgt            140
        //   117: aload_0        
        //   118: dup            
        //   119: astore          6
        //   121: monitorenter   
        //   122: aload_0        
        //   123: invokevirtual   java/lang/Object.notifyAll:()V
        //   126: aload           6
        //   128: monitorexit    
        //   129: goto            140
        //   132: astore          7
        //   134: aload           6
        //   136: monitorexit    
        //   137: aload           7
        //   139: athrow         
        //   140: iconst_1       
        //   141: ireturn        
        //   142: iload_1        
        //   143: ifne            225
        //   146: lload           4
        //   148: bipush          48
        //   150: lshr           
        //   151: l2i            
        //   152: aload_0        
        //   153: getfield        io/netty/util/internal/chmv8/ForkJoinPool.parallelism:S
        //   156: iadd           
        //   157: ifle            162
        //   160: iconst_0       
        //   161: ireturn        
        //   162: aload_0        
        //   163: getfield        io/netty/util/internal/chmv8/ForkJoinPool.workQueues:[Lio/netty/util/internal/chmv8/ForkJoinPool$WorkQueue;
        //   166: dup            
        //   167: astore          6
        //   169: ifnull          225
        //   172: iconst_0       
        //   173: aload           6
        //   175: arraylength    
        //   176: if_icmpge       225
        //   179: aload           6
        //   181: iconst_0       
        //   182: aaload         
        //   183: dup            
        //   184: astore          7
        //   186: ifnull          219
        //   189: aload           7
        //   191: invokevirtual   io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.isEmpty:()Z
        //   194: ifeq            209
        //   197: iconst_0       
        //   198: ifeq            219
        //   201: aload           7
        //   203: getfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.eventCount:I
        //   206: iflt            219
        //   209: aload_0        
        //   210: aload           6
        //   212: aload           7
        //   214: invokevirtual   io/netty/util/internal/chmv8/ForkJoinPool.signalWork:([Lio/netty/util/internal/chmv8/ForkJoinPool$WorkQueue;Lio/netty/util/internal/chmv8/ForkJoinPool$WorkQueue;)V
        //   217: iconst_0       
        //   218: ireturn        
        //   219: iinc            8, 1
        //   222: goto            172
        //   225: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   228: aload_0        
        //   229: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.CTL:J
        //   232: lload           4
        //   234: lload           4
        //   236: ldc2_w          2147483648
        //   239: lor            
        //   240: invokevirtual   sun/misc/Unsafe.compareAndSwapLong:(Ljava/lang/Object;JJJ)Z
        //   243: ifeq            86
        //   246: iconst_0       
        //   247: iconst_3       
        //   248: if_icmpge       495
        //   251: aload_0        
        //   252: getfield        io/netty/util/internal/chmv8/ForkJoinPool.workQueues:[Lio/netty/util/internal/chmv8/ForkJoinPool$WorkQueue;
        //   255: dup            
        //   256: astore          7
        //   258: ifnull          489
        //   261: aload           7
        //   263: arraylength    
        //   264: istore          10
        //   266: iconst_0       
        //   267: iload           10
        //   269: if_icmpge       345
        //   272: aload           7
        //   274: iconst_0       
        //   275: aaload         
        //   276: dup            
        //   277: astore          8
        //   279: ifnull          339
        //   282: aload           8
        //   284: iconst_m1      
        //   285: putfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.qlock:I
        //   288: iconst_0       
        //   289: ifle            339
        //   292: aload           8
        //   294: invokevirtual   io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.cancelAll:()V
        //   297: iconst_0       
        //   298: iconst_1       
        //   299: if_icmple       339
        //   302: aload           8
        //   304: getfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.owner:Lio/netty/util/internal/chmv8/ForkJoinWorkerThread;
        //   307: dup            
        //   308: astore          9
        //   310: ifnull          339
        //   313: aload           9
        //   315: invokevirtual   java/lang/Thread.isInterrupted:()Z
        //   318: ifne            331
        //   321: aload           9
        //   323: invokevirtual   java/lang/Thread.interrupt:()V
        //   326: goto            331
        //   329: astore          12
        //   331: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   334: aload           9
        //   336: invokevirtual   sun/misc/Unsafe.unpark:(Ljava/lang/Object;)V
        //   339: iinc            11, 1
        //   342: goto            266
        //   345: aload_0        
        //   346: getfield        io/netty/util/internal/chmv8/ForkJoinPool.ctl:J
        //   349: dup2           
        //   350: lstore          13
        //   352: l2i            
        //   353: ldc             2147483647
        //   355: iand           
        //   356: dup            
        //   357: istore          12
        //   359: ifeq            489
        //   362: iload           12
        //   364: ldc             65535
        //   366: iand           
        //   367: dup            
        //   368: istore          11
        //   370: iload           10
        //   372: if_icmpge       489
        //   375: iconst_0       
        //   376: iflt            489
        //   379: aload           7
        //   381: iconst_0       
        //   382: aaload         
        //   383: dup            
        //   384: astore          8
        //   386: ifnull          489
        //   389: aload           8
        //   391: getfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.nextWait:I
        //   394: ldc             2147483647
        //   396: iand           
        //   397: i2l            
        //   398: lload           13
        //   400: ldc2_w          281474976710656
        //   403: ladd           
        //   404: ldc2_w          -281474976710656
        //   407: land           
        //   408: lor            
        //   409: lload           13
        //   411: ldc2_w          281472829227008
        //   414: land           
        //   415: lor            
        //   416: lstore          16
        //   418: aload           8
        //   420: getfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.eventCount:I
        //   423: iload           12
        //   425: ldc             -2147483648
        //   427: ior            
        //   428: if_icmpne       486
        //   431: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   434: aload_0        
        //   435: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.CTL:J
        //   438: lload           13
        //   440: lload           16
        //   442: invokevirtual   sun/misc/Unsafe.compareAndSwapLong:(Ljava/lang/Object;JJJ)Z
        //   445: ifeq            486
        //   448: aload           8
        //   450: iload           12
        //   452: ldc             65536
        //   454: iadd           
        //   455: ldc             2147483647
        //   457: iand           
        //   458: putfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.eventCount:I
        //   461: aload           8
        //   463: iconst_m1      
        //   464: putfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.qlock:I
        //   467: aload           8
        //   469: getfield        io/netty/util/internal/chmv8/ForkJoinPool$WorkQueue.parker:Ljava/lang/Thread;
        //   472: dup            
        //   473: astore          15
        //   475: ifnull          486
        //   478: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   481: aload           15
        //   483: invokevirtual   sun/misc/Unsafe.unpark:(Ljava/lang/Object;)V
        //   486: goto            345
        //   489: iinc            6, 1
        //   492: goto            246
        //   495: goto            86
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static WorkQueue commonSubmitterQueue() {
        final Submitter submitter;
        final ForkJoinPool common;
        final WorkQueue[] workQueues;
        final int n;
        return ((submitter = ForkJoinPool.submitters.get()) != null && (common = ForkJoinPool.common) != null && (workQueues = common.workQueues) != null && (n = workQueues.length - 1) >= 0) ? workQueues[n & submitter.seed & 0x7E] : null;
    }
    
    final boolean tryExternalUnpush(final ForkJoinTask forkJoinTask) {
        final Submitter submitter = ForkJoinPool.submitters.get();
        final WorkQueue[] workQueues = this.workQueues;
        final int n;
        final WorkQueue workQueue;
        final int top;
        final ForkJoinTask[] array;
        if (submitter != null && workQueues != null && (n = workQueues.length - 1) >= 0 && (workQueue = workQueues[submitter.seed & n & 0x7E]) != null && workQueue.base != (top = workQueue.top) && (array = workQueue.array) != null) {
            final long n2 = ((array.length - 1 & top - 1) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
            if (ForkJoinPool.U.getObject(array, n2) == forkJoinTask && ForkJoinPool.U.compareAndSwapInt(workQueue, ForkJoinPool.QLOCK, 0, 1)) {
                if (workQueue.top == top && workQueue.array == array && ForkJoinPool.U.compareAndSwapObject(array, n2, forkJoinTask, null)) {
                    workQueue.top = top - 1;
                }
                workQueue.qlock = 0;
            }
        }
        return true;
    }
    
    final int externalHelpComplete(final CountedCompleter countedCompleter) {
        final Submitter submitter = ForkJoinPool.submitters.get();
        final WorkQueue[] workQueues = this.workQueues;
        final int n;
        final int seed;
        final WorkQueue workQueue;
        if (submitter != null && workQueues != null && (n = workQueues.length - 1) >= 0 && (workQueue = workQueues[(seed = submitter.seed) & n & 0x7E]) != null && countedCompleter != null) {
            final int n2 = n + n + 1;
            long ctl = 0L;
            int n3 = seed | 0x1;
            int n4 = n2;
            while (countedCompleter.status >= 0) {
                if (workQueue.externalPopAndExecCC(countedCompleter)) {
                    n4 = n2;
                }
                else {
                    if (countedCompleter.status < 0) {
                        break;
                    }
                    final WorkQueue workQueue2;
                    if ((workQueue2 = workQueues[n3 & n]) != null && workQueue2.pollAndExecCC(countedCompleter)) {
                        n4 = n2;
                    }
                    else if (--n4 < 0) {
                        if (ctl == (ctl = this.ctl)) {
                            break;
                        }
                        n4 = n2;
                    }
                }
                n3 += 2;
            }
        }
        return 0;
    }
    
    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
    }
    
    public ForkJoinPool(final int n) {
        this(n, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
    }
    
    public ForkJoinPool(final int n, final ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, final Thread.UncaughtExceptionHandler uncaughtExceptionHandler, final boolean b) {
        this(checkParallelism(n), checkFactory(forkJoinWorkerThreadFactory), uncaughtExceptionHandler, b ? 1 : 0, "ForkJoinPool-" + nextPoolId() + "-worker-");
    }
    
    private static int checkParallelism(final int n) {
        if (n <= 0 || n > 32767) {
            throw new IllegalArgumentException();
        }
        return n;
    }
    
    private static ForkJoinWorkerThreadFactory checkFactory(final ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory) {
        if (forkJoinWorkerThreadFactory == null) {
            throw new NullPointerException();
        }
        return forkJoinWorkerThreadFactory;
    }
    
    private ForkJoinPool(final int n, final ForkJoinWorkerThreadFactory factory, final Thread.UncaughtExceptionHandler ueh, final int n2, final String workerNamePrefix) {
        this.workerNamePrefix = workerNamePrefix;
        this.factory = factory;
        this.ueh = ueh;
        this.mode = (short)n2;
        this.parallelism = (short)n;
        final long n3 = -n;
        this.ctl = ((n3 << 48 & 0xFFFF000000000000L) | (n3 << 32 & 0xFFFF00000000L));
    }
    
    public static ForkJoinPool commonPool() {
        return ForkJoinPool.common;
    }
    
    public Object invoke(final ForkJoinTask forkJoinTask) {
        if (forkJoinTask == null) {
            throw new NullPointerException();
        }
        this.externalPush(forkJoinTask);
        return forkJoinTask.join();
    }
    
    public void execute(final ForkJoinTask forkJoinTask) {
        if (forkJoinTask == null) {
            throw new NullPointerException();
        }
        this.externalPush(forkJoinTask);
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        ForkJoinTask forkJoinTask;
        if (runnable instanceof ForkJoinTask) {
            forkJoinTask = (ForkJoinTask)runnable;
        }
        else {
            forkJoinTask = new ForkJoinTask.RunnableExecuteAction(runnable);
        }
        this.externalPush(forkJoinTask);
    }
    
    public ForkJoinTask submit(final ForkJoinTask forkJoinTask) {
        if (forkJoinTask == null) {
            throw new NullPointerException();
        }
        this.externalPush(forkJoinTask);
        return forkJoinTask;
    }
    
    @Override
    public ForkJoinTask submit(final Callable callable) {
        final ForkJoinTask.AdaptedCallable adaptedCallable = new ForkJoinTask.AdaptedCallable(callable);
        this.externalPush(adaptedCallable);
        return adaptedCallable;
    }
    
    @Override
    public ForkJoinTask submit(final Runnable runnable, final Object o) {
        final ForkJoinTask.AdaptedRunnable adaptedRunnable = new ForkJoinTask.AdaptedRunnable(runnable, o);
        this.externalPush(adaptedRunnable);
        return adaptedRunnable;
    }
    
    @Override
    public ForkJoinTask submit(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException();
        }
        ForkJoinTask forkJoinTask;
        if (runnable instanceof ForkJoinTask) {
            forkJoinTask = (ForkJoinTask)runnable;
        }
        else {
            forkJoinTask = new ForkJoinTask.AdaptedRunnableAction(runnable);
        }
        this.externalPush(forkJoinTask);
        return forkJoinTask;
    }
    
    @Override
    public List invokeAll(final Collection p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_1        
        //     5: invokeinterface java/util/Collection.size:()I
        //    10: invokespecial   java/util/ArrayList.<init>:(I)V
        //    13: astore_2       
        //    14: aload_1        
        //    15: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    20: astore          4
        //    22: aload           4
        //    24: invokeinterface java/util/Iterator.hasNext:()Z
        //    29: ifeq            71
        //    32: aload           4
        //    34: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    39: checkcast       Ljava/util/concurrent/Callable;
        //    42: astore          5
        //    44: new             Lio/netty/util/internal/chmv8/ForkJoinTask$AdaptedCallable;
        //    47: dup            
        //    48: aload           5
        //    50: invokespecial   io/netty/util/internal/chmv8/ForkJoinTask$AdaptedCallable.<init>:(Ljava/util/concurrent/Callable;)V
        //    53: astore          6
        //    55: aload_2        
        //    56: aload           6
        //    58: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    61: pop            
        //    62: aload_0        
        //    63: aload           6
        //    65: invokevirtual   io/netty/util/internal/chmv8/ForkJoinPool.externalPush:(Lio/netty/util/internal/chmv8/ForkJoinTask;)V
        //    68: goto            22
        //    71: aload_2        
        //    72: invokevirtual   java/util/ArrayList.size:()I
        //    75: istore          5
        //    77: iconst_0       
        //    78: iconst_0       
        //    79: if_icmpge       99
        //    82: aload_2        
        //    83: iconst_0       
        //    84: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    87: checkcast       Lio/netty/util/internal/chmv8/ForkJoinTask;
        //    90: invokevirtual   io/netty/util/internal/chmv8/ForkJoinTask.quietlyJoin:()V
        //    93: iinc            4, 1
        //    96: goto            77
        //    99: aload_2        
        //   100: astore          4
        //   102: iconst_1       
        //   103: ifne            139
        //   106: aload_2        
        //   107: invokevirtual   java/util/ArrayList.size:()I
        //   110: istore          6
        //   112: iconst_0       
        //   113: iload           6
        //   115: if_icmpge       139
        //   118: aload_2        
        //   119: iconst_0       
        //   120: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   123: checkcast       Ljava/util/concurrent/Future;
        //   126: iconst_0       
        //   127: invokeinterface java/util/concurrent/Future.cancel:(Z)Z
        //   132: pop            
        //   133: iinc            5, 1
        //   136: goto            112
        //   139: aload           4
        //   141: areturn        
        //   142: astore          7
        //   144: iconst_1       
        //   145: ifne            181
        //   148: aload_2        
        //   149: invokevirtual   java/util/ArrayList.size:()I
        //   152: istore          9
        //   154: iconst_0       
        //   155: iload           9
        //   157: if_icmpge       181
        //   160: aload_2        
        //   161: iconst_0       
        //   162: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   165: checkcast       Ljava/util/concurrent/Future;
        //   168: iconst_0       
        //   169: invokeinterface java/util/concurrent/Future.cancel:(Z)Z
        //   174: pop            
        //   175: iinc            8, 1
        //   178: goto            154
        //   181: aload           7
        //   183: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public ForkJoinWorkerThreadFactory getFactory() {
        return this.factory;
    }
    
    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.ueh;
    }
    
    public int getParallelism() {
        final short parallelism;
        return ((parallelism = this.parallelism) > 0) ? parallelism : true;
    }
    
    public static int getCommonPoolParallelism() {
        return 1;
    }
    
    public int getPoolSize() {
        return this.parallelism + (short)(this.ctl >>> 32);
    }
    
    public boolean getAsyncMode() {
        return this.mode == 1;
    }
    
    public int getRunningThreadCount() {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (1 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[1]) != null && workQueue.isApparentlyUnblocked()) {
                    int n = 0;
                    ++n;
                }
                final int n2;
                n2 += 2;
            }
        }
        return 0;
    }
    
    public int getActiveThreadCount() {
        final int n = this.parallelism + (int)(this.ctl >> 48);
        return (n <= 0) ? 0 : n;
    }
    
    public boolean isQuiescent() {
        return this.parallelism + (int)(this.ctl >> 48) <= 0;
    }
    
    public long getStealCount() {
        long stealCount = this.stealCount;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (1 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[1]) != null) {
                    stealCount += workQueue.nsteals;
                }
                final int n;
                n += 2;
            }
        }
        return stealCount;
    }
    
    public long getQueuedTaskCount() {
        long n = 0L;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (1 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[1]) != null) {
                    n += workQueue.queueSize();
                }
                final int n2;
                n2 += 2;
            }
        }
        return n;
    }
    
    public int getQueuedSubmissionCount() {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (0 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[0]) != null) {
                    final int n = 0 + workQueue.queueSize();
                }
                final int n2;
                n2 += 2;
            }
        }
        return 0;
    }
    
    public boolean hasQueuedSubmissions() {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (0 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[0]) != null && !workQueue.isEmpty()) {
                    return true;
                }
                final int n;
                n += 2;
            }
        }
        return false;
    }
    
    protected ForkJoinTask pollSubmission() {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (0 < workQueues.length) {
                final WorkQueue workQueue;
                final ForkJoinTask poll;
                if ((workQueue = workQueues[0]) != null && (poll = workQueue.poll()) != null) {
                    return poll;
                }
                final int n;
                n += 2;
            }
        }
        return null;
    }
    
    protected int drainTasksTo(final Collection collection) {
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (0 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[0]) != null) {
                    ForkJoinTask poll;
                    while ((poll = workQueue.poll()) != null) {
                        collection.add(poll);
                        int n = 0;
                        ++n;
                    }
                }
                int n2 = 0;
                ++n2;
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        long n = 0L;
        long n2 = 0L;
        long stealCount = this.stealCount;
        final long ctl = this.ctl;
        final WorkQueue[] workQueues;
        if ((workQueues = this.workQueues) != null) {
            while (0 < workQueues.length) {
                final WorkQueue workQueue;
                if ((workQueue = workQueues[0]) != null) {
                    final int queueSize = workQueue.queueSize();
                    if (!false) {
                        n2 += queueSize;
                    }
                    else {
                        n += queueSize;
                        stealCount += workQueue.nsteals;
                        if (workQueue.isApparentlyUnblocked()) {
                            int n3 = 0;
                            ++n3;
                        }
                    }
                }
                int parallelism = 0;
                ++parallelism;
            }
        }
        int parallelism = this.parallelism;
        final int n4 = 0 + (short)(ctl >>> 32);
        final int n5 = 0 + (int)(ctl >> 48);
        if (0 < 0) {}
        String s;
        if ((ctl & 0x80000000L) != 0x0L) {
            s = ((n4 == 0) ? "Terminated" : "Terminating");
        }
        else {
            s = ((this.plock < 0) ? "Shutting down" : "Running");
        }
        return super.toString() + "[" + s + ", parallelism = " + 0 + ", size = " + n4 + ", active = " + 0 + ", running = " + 0 + ", steals = " + stealCount + ", tasks = " + n + ", submissions = " + n2 + "]";
    }
    
    @Override
    public void shutdown() {
        this.tryTerminate(false, true);
    }
    
    @Override
    public List shutdownNow() {
        this.tryTerminate(true, true);
        return Collections.emptyList();
    }
    
    @Override
    public boolean isTerminated() {
        final long ctl = this.ctl;
        return (ctl & 0x80000000L) != 0x0L && (short)(ctl >>> 32) + this.parallelism <= 0;
    }
    
    public boolean isTerminating() {
        final long ctl = this.ctl;
        return (ctl & 0x80000000L) != 0x0L && (short)(ctl >>> 32) + this.parallelism > 0;
    }
    
    @Override
    public boolean isShutdown() {
        return this.plock < 0;
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this == ForkJoinPool.common) {
            this.awaitQuiescence(n, timeUnit);
            return false;
        }
        long nanos = timeUnit.toNanos(n);
        if (this.isTerminated()) {
            return true;
        }
        if (nanos <= 0L) {
            return false;
        }
        final long n2 = System.nanoTime() + nanos;
        // monitorenter(this)
        while (!this.isTerminated()) {
            if (nanos <= 0L) {
                // monitorexit(this)
                return false;
            }
            final long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
            this.wait((millis > 0L) ? millis : 1L);
            nanos = n2 - System.nanoTime();
        }
        // monitorexit(this)
        return true;
    }
    
    public boolean awaitQuiescence(final long n, final TimeUnit timeUnit) {
        final long nanos = timeUnit.toNanos(n);
        final Thread currentThread = Thread.currentThread();
        final ForkJoinWorkerThread forkJoinWorkerThread;
        if (currentThread instanceof ForkJoinWorkerThread && (forkJoinWorkerThread = (ForkJoinWorkerThread)currentThread).pool == this) {
            this.helpQuiescePool(forkJoinWorkerThread.workQueue);
            return true;
        }
        final long nanoTime = System.nanoTime();
        WorkQueue[] workQueues;
        int n2;
        while (!this.isQuiescent() && (workQueues = this.workQueues) != null && (n2 = workQueues.length - 1) >= 0) {
            if (!true) {
                if (System.nanoTime() - nanoTime > nanos) {
                    return false;
                }
                Thread.yield();
            }
            int i = n2 + 1 << 2;
            while (i >= 0) {
                final WorkQueue[] array = workQueues;
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                final WorkQueue workQueue;
                final int base;
                if ((workQueue = array[n3 & n2]) != null && (base = workQueue.base) - workQueue.top < 0) {
                    final ForkJoinTask poll;
                    if ((poll = workQueue.pollAt(base)) != null) {
                        poll.doExec();
                        break;
                    }
                    break;
                }
                else {
                    --i;
                }
            }
        }
        return true;
    }
    
    static void quiesceCommonPool() {
        ForkJoinPool.common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    
    public static void managedBlock(final ManagedBlocker managedBlocker) throws InterruptedException {
        final Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            final ForkJoinPool pool = ((ForkJoinWorkerThread)currentThread).pool;
            while (!managedBlocker.isReleasable()) {
                if (pool.tryCompensate(pool.ctl)) {
                    while (!managedBlocker.isReleasable() && !managedBlocker.block()) {}
                    pool.incrementActiveCount();
                    break;
                }
            }
        }
        else {
            while (!managedBlocker.isReleasable() && !managedBlocker.block()) {}
        }
    }
    
    @Override
    protected RunnableFuture newTaskFor(final Runnable runnable, final Object o) {
        return new ForkJoinTask.AdaptedRunnable(runnable, o);
    }
    
    @Override
    protected RunnableFuture newTaskFor(final Callable callable) {
        return new ForkJoinTask.AdaptedCallable(callable);
    }
    
    private static ForkJoinPool makeCommonPool() {
        ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
        final String property = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
        final String property2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
        final String property3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
        if (property != null) {
            Integer.parseInt(property);
        }
        if (property2 != null) {
            defaultForkJoinWorkerThreadFactory = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(property2).newInstance();
        }
        if (property3 != null) {
            uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(property3).newInstance();
        }
        final int n;
        if (32767 >= 0 || (n = Runtime.getRuntime().availableProcessors() - 1) < 0) {}
        if (32767 > 32767) {}
        return new ForkJoinPool(32767, defaultForkJoinWorkerThreadFactory, uncaughtExceptionHandler, 0, "ForkJoinPool.commonPool-worker-");
    }
    
    private static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }
    
    @Override
    public Future submit(final Callable callable) {
        return this.submit(callable);
    }
    
    @Override
    public Future submit(final Runnable runnable, final Object o) {
        return this.submit(runnable, o);
    }
    
    @Override
    public Future submit(final Runnable runnable) {
        return this.submit(runnable);
    }
    
    static Unsafe access$000() {
        return getUnsafe();
    }
    
    static ForkJoinPool access$100() {
        return makeCommonPool();
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //     6: ldc             Lio/netty/util/internal/chmv8/ForkJoinPool;.class
        //     8: astore_0       
        //     9: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    12: aload_0        
        //    13: ldc_w           "ctl"
        //    16: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    19: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //    22: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.CTL:J
        //    25: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    28: aload_0        
        //    29: ldc_w           "stealCount"
        //    32: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    35: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //    38: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.STEALCOUNT:J
        //    41: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    44: aload_0        
        //    45: ldc_w           "plock"
        //    48: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    51: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //    54: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.PLOCK:J
        //    57: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    60: aload_0        
        //    61: ldc_w           "indexSeed"
        //    64: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    67: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //    70: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.INDEXSEED:J
        //    73: ldc             Ljava/lang/Thread;.class
        //    75: astore_1       
        //    76: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    79: aload_1        
        //    80: ldc_w           "parkBlocker"
        //    83: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //    86: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //    89: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.PARKBLOCKER:J
        //    92: ldc             Lio/netty/util/internal/chmv8/ForkJoinPool$WorkQueue;.class
        //    94: astore_2       
        //    95: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //    98: aload_2        
        //    99: ldc_w           "base"
        //   102: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //   105: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //   108: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.QBASE:J
        //   111: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   114: aload_2        
        //   115: ldc_w           "qlock"
        //   118: invokevirtual   java/lang/Class.getDeclaredField:(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //   121: invokevirtual   sun/misc/Unsafe.objectFieldOffset:(Ljava/lang/reflect/Field;)J
        //   124: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.QLOCK:J
        //   127: ldc_w           [Lio/netty/util/internal/chmv8/ForkJoinTask;.class
        //   130: astore_3       
        //   131: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   134: aload_3        
        //   135: invokevirtual   sun/misc/Unsafe.arrayBaseOffset:(Ljava/lang/Class;)I
        //   138: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.ABASE:I
        //   141: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.U:Lsun/misc/Unsafe;
        //   144: aload_3        
        //   145: invokevirtual   sun/misc/Unsafe.arrayIndexScale:(Ljava/lang/Class;)I
        //   148: istore          4
        //   150: iload           4
        //   152: iload           4
        //   154: iconst_1       
        //   155: isub           
        //   156: iand           
        //   157: ifeq            171
        //   160: new             Ljava/lang/Error;
        //   163: dup            
        //   164: ldc_w           "data type scale not a power of two"
        //   167: invokespecial   java/lang/Error.<init>:(Ljava/lang/String;)V
        //   170: athrow         
        //   171: bipush          31
        //   173: iload           4
        //   175: invokestatic    java/lang/Integer.numberOfLeadingZeros:(I)I
        //   178: isub           
        //   179: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.ASHIFT:I
        //   182: goto            195
        //   185: astore_0       
        //   186: new             Ljava/lang/Error;
        //   189: dup            
        //   190: aload_0        
        //   191: invokespecial   java/lang/Error.<init>:(Ljava/lang/Throwable;)V
        //   194: athrow         
        //   195: new             Ljava/lang/ThreadLocal;
        //   198: dup            
        //   199: invokespecial   java/lang/ThreadLocal.<init>:()V
        //   202: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.submitters:Ljava/lang/ThreadLocal;
        //   205: new             Lio/netty/util/internal/chmv8/ForkJoinPool$DefaultForkJoinWorkerThreadFactory;
        //   208: dup            
        //   209: invokespecial   io/netty/util/internal/chmv8/ForkJoinPool$DefaultForkJoinWorkerThreadFactory.<init>:()V
        //   212: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.defaultForkJoinWorkerThreadFactory:Lio/netty/util/internal/chmv8/ForkJoinPool$ForkJoinWorkerThreadFactory;
        //   215: new             Ljava/lang/RuntimePermission;
        //   218: dup            
        //   219: ldc_w           "modifyThread"
        //   222: invokespecial   java/lang/RuntimePermission.<init>:(Ljava/lang/String;)V
        //   225: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.modifyThreadPermission:Ljava/lang/RuntimePermission;
        //   228: new             Lio/netty/util/internal/chmv8/ForkJoinPool$1;
        //   231: dup            
        //   232: invokespecial   io/netty/util/internal/chmv8/ForkJoinPool$1.<init>:()V
        //   235: invokestatic    java/security/AccessController.doPrivileged:(Ljava/security/PrivilegedAction;)Ljava/lang/Object;
        //   238: checkcast       Lio/netty/util/internal/chmv8/ForkJoinPool;
        //   241: putstatic       io/netty/util/internal/chmv8/ForkJoinPool.common:Lio/netty/util/internal/chmv8/ForkJoinPool;
        //   244: getstatic       io/netty/util/internal/chmv8/ForkJoinPool.common:Lio/netty/util/internal/chmv8/ForkJoinPool;
        //   247: getfield        io/netty/util/internal/chmv8/ForkJoinPool.parallelism:S
        //   250: istore_0       
        //   251: iload_0        
        //   252: ifle            259
        //   255: iload_0        
        //   256: goto            259
        //   259: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0259 (coming from #0256).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public interface ManagedBlocker
    {
        boolean block() throws InterruptedException;
        
        boolean isReleasable();
    }
    
    static final class Submitter
    {
        int seed;
        
        Submitter(final int seed) {
            this.seed = seed;
        }
    }
    
    static final class WorkQueue
    {
        static final int INITIAL_QUEUE_CAPACITY = 8192;
        static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
        long pad00;
        long pad01;
        long pad02;
        long pad03;
        long pad04;
        long pad05;
        long pad06;
        int eventCount;
        int nextWait;
        int nsteals;
        int hint;
        short poolIndex;
        final short mode;
        int qlock;
        int base;
        int top;
        ForkJoinTask[] array;
        final ForkJoinPool pool;
        final ForkJoinWorkerThread owner;
        Thread parker;
        ForkJoinTask currentJoin;
        ForkJoinTask currentSteal;
        Object pad10;
        Object pad11;
        Object pad12;
        Object pad13;
        Object pad14;
        Object pad15;
        Object pad16;
        Object pad17;
        Object pad18;
        Object pad19;
        Object pad1a;
        Object pad1b;
        Object pad1c;
        Object pad1d;
        private static final Unsafe U;
        private static final long QBASE;
        private static final long QLOCK;
        private static final int ABASE;
        private static final int ASHIFT;
        
        WorkQueue(final ForkJoinPool pool, final ForkJoinWorkerThread owner, final int n, final int hint) {
            this.pool = pool;
            this.owner = owner;
            this.mode = (short)n;
            this.hint = hint;
            final int n2 = 4096;
            this.top = n2;
            this.base = n2;
        }
        
        final int queueSize() {
            final int n = this.base - this.top;
            return (n >= 0) ? 0 : (-n);
        }
        
        final boolean isEmpty() {
            final int top;
            final int n = this.base - (top = this.top);
            final ForkJoinTask[] array;
            final int n2;
            return n >= 0 || (n == -1 && ((array = this.array) == null || (n2 = array.length - 1) < 0 || WorkQueue.U.getObject(array, ((n2 & top - 1) << WorkQueue.ASHIFT) + (long)WorkQueue.ABASE) == null));
        }
        
        final void push(final ForkJoinTask forkJoinTask) {
            final int top = this.top;
            final ForkJoinTask[] array;
            if ((array = this.array) != null) {
                final int n = array.length - 1;
                WorkQueue.U.putOrderedObject(array, ((n & top) << WorkQueue.ASHIFT) + WorkQueue.ABASE, forkJoinTask);
                final int top2 = top + 1;
                this.top = top2;
                final int n2;
                if ((n2 = top2 - this.base) <= 2) {
                    final ForkJoinPool pool = this.pool;
                    pool.signalWork(pool.workQueues, this);
                }
                else if (n2 >= n) {
                    this.growArray();
                }
            }
        }
        
        final ForkJoinTask[] growArray() {
            final ForkJoinTask[] array = this.array;
            final int n = (array != null) ? (array.length << 1) : 8192;
            if (n > 67108864) {
                throw new RejectedExecutionException("Queue capacity exceeded");
            }
            final ForkJoinTask[] array2 = new ForkJoinTask[n];
            this.array = array2;
            final ForkJoinTask[] array3 = array2;
            final int n2;
            if (array != null && (n2 = array.length - 1) >= 0) {
                final int top = this.top;
                int base;
                if (top - (base = this.base) > 0) {
                    final int n3 = n - 1;
                    do {
                        final int n4 = ((base & n2) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final int n5 = ((base & n3) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final ForkJoinTask forkJoinTask = (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, n4);
                        if (forkJoinTask != null && WorkQueue.U.compareAndSwapObject(array, n4, forkJoinTask, null)) {
                            WorkQueue.U.putObjectVolatile(array3, n5, forkJoinTask);
                        }
                    } while (++base != top);
                }
            }
            return array3;
        }
        
        final ForkJoinTask pop() {
            final ForkJoinTask[] array;
            final int n;
            if ((array = this.array) != null && (n = array.length - 1) >= 0) {
                int top;
                while ((top = this.top - 1) - this.base >= 0) {
                    final long n2 = ((n & top) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                    final ForkJoinTask forkJoinTask;
                    if ((forkJoinTask = (ForkJoinTask)WorkQueue.U.getObject(array, n2)) == null) {
                        break;
                    }
                    if (WorkQueue.U.compareAndSwapObject(array, n2, forkJoinTask, null)) {
                        this.top = top;
                        return forkJoinTask;
                    }
                }
            }
            return null;
        }
        
        final ForkJoinTask pollAt(final int n) {
            final ForkJoinTask[] array;
            if ((array = this.array) != null) {
                final int n2 = ((array.length - 1 & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final ForkJoinTask forkJoinTask;
                if ((forkJoinTask = (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, n2)) != null && this.base == n && WorkQueue.U.compareAndSwapObject(array, n2, forkJoinTask, null)) {
                    WorkQueue.U.putOrderedInt(this, WorkQueue.QBASE, n + 1);
                    return forkJoinTask;
                }
            }
            return null;
        }
        
        final ForkJoinTask poll() {
            int base;
            ForkJoinTask[] array;
            while ((base = this.base) - this.top < 0 && (array = this.array) != null) {
                final int n = ((array.length - 1 & base) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final ForkJoinTask forkJoinTask = (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, n);
                if (forkJoinTask != null) {
                    if (WorkQueue.U.compareAndSwapObject(array, n, forkJoinTask, null)) {
                        WorkQueue.U.putOrderedInt(this, WorkQueue.QBASE, base + 1);
                        return forkJoinTask;
                    }
                    continue;
                }
                else {
                    if (this.base != base) {
                        continue;
                    }
                    if (base + 1 == this.top) {
                        break;
                    }
                    Thread.yield();
                }
            }
            return null;
        }
        
        final ForkJoinTask nextLocalTask() {
            return (this.mode == 0) ? this.pop() : this.poll();
        }
        
        final ForkJoinTask peek() {
            final ForkJoinTask[] array = this.array;
            final int n;
            if (array == null || (n = array.length - 1) < 0) {
                return null;
            }
            return (ForkJoinTask)WorkQueue.U.getObjectVolatile(array, ((((this.mode == 0) ? (this.top - 1) : this.base) & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE);
        }
        
        final boolean tryUnpush(final ForkJoinTask forkJoinTask) {
            final ForkJoinTask[] array;
            int top;
            if ((array = this.array) != null && (top = this.top) != this.base && WorkQueue.U.compareAndSwapObject(array, ((array.length - 1 & --top) << WorkQueue.ASHIFT) + WorkQueue.ABASE, forkJoinTask, null)) {
                this.top = top;
                return true;
            }
            return false;
        }
        
        final void cancelAll() {
            ForkJoinTask.cancelIgnoringExceptions(this.currentJoin);
            ForkJoinTask.cancelIgnoringExceptions(this.currentSteal);
            ForkJoinTask poll;
            while ((poll = this.poll()) != null) {
                ForkJoinTask.cancelIgnoringExceptions(poll);
            }
        }
        
        final void pollAndExecAll() {
            ForkJoinTask poll;
            while ((poll = this.poll()) != null) {
                poll.doExec();
            }
        }
        
        final void runTask(final ForkJoinTask currentSteal) {
            this.currentSteal = currentSteal;
            if (currentSteal != null) {
                currentSteal.doExec();
                final ForkJoinTask[] array = this.array;
                final short mode = this.mode;
                ++this.nsteals;
                this.currentSteal = null;
                if (mode != 0) {
                    this.pollAndExecAll();
                }
                else if (array != null) {
                    final int n = array.length - 1;
                    int top;
                    while ((top = this.top - 1) - this.base >= 0) {
                        final long n2 = ((n & top) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final ForkJoinTask forkJoinTask = (ForkJoinTask)WorkQueue.U.getObject(array, n2);
                        if (forkJoinTask == null) {
                            break;
                        }
                        if (!WorkQueue.U.compareAndSwapObject(array, n2, forkJoinTask, null)) {
                            continue;
                        }
                        this.top = top;
                        forkJoinTask.doExec();
                    }
                }
            }
        }
        
        final boolean tryRemoveAndExec(final ForkJoinTask forkJoinTask) {
            final ForkJoinTask[] array;
            final int n;
            int top;
            final int base;
            int n2;
            if (forkJoinTask != null && (array = this.array) != null && (n = array.length - 1) >= 0 && (n2 = (top = this.top) - (base = this.base)) > 0) {
                while (true) {
                    final long n3 = ((--top & n) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                    final ForkJoinTask forkJoinTask2 = (ForkJoinTask)WorkQueue.U.getObject(array, n3);
                    if (forkJoinTask2 == null) {
                        break;
                    }
                    if (forkJoinTask2 == forkJoinTask) {
                        if (top + 1 == this.top) {
                            if (!WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask, null)) {
                                break;
                            }
                            this.top = top;
                            break;
                        }
                        else {
                            if (this.base == base) {
                                WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask, new EmptyTask());
                                break;
                            }
                            break;
                        }
                    }
                    else {
                        if (forkJoinTask2.status < 0) {
                            if (top + 1 == this.top) {
                                if (WorkQueue.U.compareAndSwapObject(array, n3, forkJoinTask2, null)) {
                                    this.top = top;
                                    break;
                                }
                                break;
                            }
                        }
                        if (--n2 != 0) {
                            continue;
                        }
                        if (!false && this.base == base) {
                            break;
                        }
                        break;
                    }
                }
                if (true) {
                    forkJoinTask.doExec();
                }
            }
            return false;
        }
        
        final boolean pollAndExecCC(final CountedCompleter countedCompleter) {
            final int base;
            final ForkJoinTask[] array;
            if ((base = this.base) - this.top < 0 && (array = this.array) != null) {
                final long n = ((array.length - 1 & base) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final Object objectVolatile;
                if ((objectVolatile = WorkQueue.U.getObjectVolatile(array, n)) == null) {
                    return true;
                }
                if (objectVolatile instanceof CountedCompleter) {
                    CountedCompleter completer;
                    final CountedCompleter countedCompleter2 = completer = (CountedCompleter)objectVolatile;
                    while (completer != countedCompleter) {
                        if ((completer = completer.completer) == null) {
                            return false;
                        }
                    }
                    if (this.base == base && WorkQueue.U.compareAndSwapObject(array, n, countedCompleter2, null)) {
                        WorkQueue.U.putOrderedInt(this, WorkQueue.QBASE, base + 1);
                        countedCompleter2.doExec();
                    }
                    return true;
                }
            }
            return false;
        }
        
        final boolean externalPopAndExecCC(final CountedCompleter countedCompleter) {
            final int top;
            final ForkJoinTask[] array;
            if (this.base - (top = this.top) < 0 && (array = this.array) != null) {
                final long n = ((array.length - 1 & top - 1) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final Object object;
                if ((object = WorkQueue.U.getObject(array, n)) instanceof CountedCompleter) {
                    CountedCompleter completer;
                    final CountedCompleter countedCompleter2 = completer = (CountedCompleter)object;
                    while (completer != countedCompleter) {
                        if ((completer = completer.completer) == null) {
                            return false;
                        }
                    }
                    if (WorkQueue.U.compareAndSwapInt(this, WorkQueue.QLOCK, 0, 1)) {
                        if (this.top == top && this.array == array && WorkQueue.U.compareAndSwapObject(array, n, countedCompleter2, null)) {
                            this.top = top - 1;
                            this.qlock = 0;
                            countedCompleter2.doExec();
                        }
                        else {
                            this.qlock = 0;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        
        final boolean internalPopAndExecCC(final CountedCompleter countedCompleter) {
            final int top;
            final ForkJoinTask[] array;
            if (this.base - (top = this.top) < 0 && (array = this.array) != null) {
                final long n = ((array.length - 1 & top - 1) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final Object object;
                if ((object = WorkQueue.U.getObject(array, n)) instanceof CountedCompleter) {
                    CountedCompleter completer;
                    final CountedCompleter countedCompleter2 = completer = (CountedCompleter)object;
                    while (completer != countedCompleter) {
                        if ((completer = completer.completer) == null) {
                            return false;
                        }
                    }
                    if (WorkQueue.U.compareAndSwapObject(array, n, countedCompleter2, null)) {
                        this.top = top - 1;
                        countedCompleter2.doExec();
                    }
                    return true;
                }
            }
            return false;
        }
        
        final boolean isApparentlyUnblocked() {
            final ForkJoinWorkerThread owner;
            final Thread.State state;
            return this.eventCount >= 0 && (owner = this.owner) != null && (state = owner.getState()) != Thread.State.BLOCKED && state != Thread.State.WAITING && state != Thread.State.TIMED_WAITING;
        }
        
        static {
            U = ForkJoinPool.access$000();
            final Class<WorkQueue> clazz = WorkQueue.class;
            final Class<ForkJoinTask[]> clazz2 = ForkJoinTask[].class;
            QBASE = WorkQueue.U.objectFieldOffset(clazz.getDeclaredField("base"));
            QLOCK = WorkQueue.U.objectFieldOffset(clazz.getDeclaredField("qlock"));
            ABASE = WorkQueue.U.arrayBaseOffset(clazz2);
            final int arrayIndexScale = WorkQueue.U.arrayIndexScale(clazz2);
            if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
                throw new Error("data type scale not a power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
        }
    }
    
    static final class EmptyTask extends ForkJoinTask
    {
        private static final long serialVersionUID = -7721805057305804111L;
        
        EmptyTask() {
            this.status = -268435456;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void void1) {
        }
        
        public final boolean exec() {
            return true;
        }
        
        public void setRawResult(final Object o) {
            this.setRawResult((Void)o);
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class DefaultForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory
    {
        @Override
        public final ForkJoinWorkerThread newThread(final ForkJoinPool forkJoinPool) {
            return new ForkJoinWorkerThread(forkJoinPool);
        }
    }
    
    public interface ForkJoinWorkerThreadFactory
    {
        ForkJoinWorkerThread newThread(final ForkJoinPool p0);
    }
}
