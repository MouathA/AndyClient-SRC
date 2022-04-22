package io.netty.channel.epoll;

import java.util.concurrent.atomic.*;
import io.netty.channel.*;
import java.util.concurrent.*;
import io.netty.util.collection.*;
import io.netty.util.internal.*;
import java.util.*;
import io.netty.util.internal.logging.*;

final class EpollEventLoop extends SingleThreadEventLoop
{
    private static final InternalLogger logger;
    private static final AtomicIntegerFieldUpdater WAKEN_UP_UPDATER;
    private final int epollFd;
    private final int eventFd;
    private final IntObjectMap ids;
    private final long[] events;
    private int id;
    private boolean overflown;
    private int wakenUp;
    private int ioRatio;
    static final boolean $assertionsDisabled;
    
    EpollEventLoop(final EventLoopGroup eventLoopGroup, final ThreadFactory threadFactory, final int n) {
        super(eventLoopGroup, threadFactory, false);
        this.ids = new IntObjectHashMap();
        this.ioRatio = 50;
        this.events = new long[n];
        this.epollFd = Native.epollCreate();
        this.eventFd = Native.eventFd();
        Native.epollCtlAdd(-1, -1, 1, 0);
    }
    
    private int nextId() {
        int id = this.id;
        if (this.overflown) {
            IntObjectMap ids;
            do {
                ids = this.ids;
                ++id;
            } while (ids.containsKey(0));
            this.id = 0;
        }
        else {
            ++id;
            this.id = 0;
        }
        return 0;
    }
    
    @Override
    protected void wakeup(final boolean b) {
        if (!b && EpollEventLoop.WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)) {
            Native.eventFdWrite(this.eventFd, 1L);
        }
    }
    
    void add(final AbstractEpollChannel abstractEpollChannel) {
        assert this.inEventLoop();
        final int nextId = this.nextId();
        Native.epollCtlAdd(this.epollFd, abstractEpollChannel.fd, abstractEpollChannel.flags, nextId);
        abstractEpollChannel.id = nextId;
        this.ids.put(nextId, abstractEpollChannel);
    }
    
    void modify(final AbstractEpollChannel abstractEpollChannel) {
        assert this.inEventLoop();
        Native.epollCtlMod(this.epollFd, abstractEpollChannel.fd, abstractEpollChannel.flags, abstractEpollChannel.id);
    }
    
    void remove(final AbstractEpollChannel abstractEpollChannel) {
        assert this.inEventLoop();
        if (this.ids.remove(abstractEpollChannel.id) != null && abstractEpollChannel.isOpen()) {
            Native.epollCtlDel(this.epollFd, abstractEpollChannel.fd);
        }
    }
    
    @Override
    protected Queue newTaskQueue() {
        return PlatformDependent.newMpscQueue();
    }
    
    public int getIoRatio() {
        return this.ioRatio;
    }
    
    public void setIoRatio(final int ioRatio) {
        if (ioRatio <= 0 || ioRatio > 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = ioRatio;
    }
    
    private int epollWait(final boolean b) {
        long n = System.nanoTime();
        final long n2 = n + this.delayNanos(n);
        while (true) {
            final long n3 = (n2 - n + 500000L) / 1000000L;
            if (n3 <= 0L) {
                final int epollWait = Native.epollWait(this.epollFd, this.events, 0);
                if (epollWait > 0) {
                    return epollWait;
                }
                return 0;
            }
            else {
                final int epollWait2 = Native.epollWait(this.epollFd, this.events, (int)n3);
                int n4 = 0;
                ++n4;
                if (epollWait2 != 0 || b || this.wakenUp == 1 || this.hasTasks() || this.hasScheduledTasks()) {
                    return epollWait2;
                }
                n = System.nanoTime();
            }
        }
    }
    
    @Override
    protected void run() {
        while (true) {
            final boolean b = EpollEventLoop.WAKEN_UP_UPDATER.getAndSet(this, 0) == 1;
            int n;
            if (this.hasTasks()) {
                n = Native.epollWait(this.epollFd, this.events, 0);
            }
            else {
                n = this.epollWait(b);
                if (this.wakenUp == 1) {
                    Native.eventFdWrite(this.eventFd, 1L);
                }
            }
            final int ioRatio = this.ioRatio;
            if (ioRatio == 100) {
                if (n > 0) {
                    this.processReady(this.events, n);
                }
                this.runAllTasks();
            }
            else {
                final long nanoTime = System.nanoTime();
                if (n > 0) {
                    this.processReady(this.events, n);
                }
                this.runAllTasks((System.nanoTime() - nanoTime) * (100 - ioRatio) / ioRatio);
            }
            if (this.isShuttingDown()) {
                this.closeAll();
                if (this.confirmShutdown()) {
                    break;
                }
                continue;
            }
        }
    }
    
    private void closeAll() {
        Native.epollWait(this.epollFd, this.events, 0);
        final ArrayList<AbstractEpollChannel> list = new ArrayList<AbstractEpollChannel>(this.ids.size());
        final Iterator<IntObjectMap.Entry> iterator = this.ids.entries().iterator();
        while (iterator.hasNext()) {
            list.add((AbstractEpollChannel)iterator.next().value());
        }
        for (final AbstractEpollChannel abstractEpollChannel : list) {
            abstractEpollChannel.unsafe().close(abstractEpollChannel.unsafe().voidPromise());
        }
    }
    
    private void processReady(final long[] array, final int n) {
        while (0 < n) {
            final long n2 = array[0];
            final int n3 = (int)(n2 >> 32);
            if (n3 == 0) {
                Native.eventFdRead(this.eventFd);
            }
            else {
                final boolean b = (n2 & 0x1L) != 0x0L;
                final boolean b2 = (n2 & 0x2L) != 0x0L;
                final boolean b3 = (n2 & 0x8L) != 0x0L;
                final AbstractEpollChannel abstractEpollChannel = (AbstractEpollChannel)this.ids.get(n3);
                if (abstractEpollChannel != null) {
                    final AbstractEpollChannel.AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollChannel.AbstractEpollUnsafe)abstractEpollChannel.unsafe();
                    if (b2 && abstractEpollChannel.isOpen()) {
                        abstractEpollUnsafe.epollOutReady();
                    }
                    if (b && abstractEpollChannel.isOpen()) {
                        abstractEpollUnsafe.epollInReady();
                    }
                    if (b3 && abstractEpollChannel.isOpen()) {
                        abstractEpollUnsafe.epollRdHupReady();
                    }
                }
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    protected void cleanup() {
        Native.close(this.epollFd);
        Native.close(this.eventFd);
    }
    
    static {
        $assertionsDisabled = !EpollEventLoop.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
        AtomicIntegerFieldUpdater<EpollEventLoop> waken_UP_UPDATER = (AtomicIntegerFieldUpdater<EpollEventLoop>)PlatformDependent.newAtomicIntegerFieldUpdater(EpollEventLoop.class, "wakenUp");
        if (waken_UP_UPDATER == null) {
            waken_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
        }
        WAKEN_UP_UPDATER = waken_UP_UPDATER;
    }
}
