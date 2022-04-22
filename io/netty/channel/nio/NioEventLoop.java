package io.netty.channel.nio;

import java.util.concurrent.atomic.*;
import io.netty.channel.*;
import java.nio.channels.spi.*;
import java.lang.reflect.*;
import java.nio.channels.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;

public final class NioEventLoop extends SingleThreadEventLoop
{
    private static final InternalLogger logger;
    private static final int CLEANUP_INTERVAL = 256;
    private static final boolean DISABLE_KEYSET_OPTIMIZATION;
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    Selector selector;
    private SelectedSelectionKeySet selectedKeys;
    private final SelectorProvider provider;
    private final AtomicBoolean wakenUp;
    private int ioRatio;
    private int cancelledKeys;
    private boolean needsToSelectAgain;
    
    NioEventLoop(final NioEventLoopGroup nioEventLoopGroup, final ThreadFactory threadFactory, final SelectorProvider provider) {
        super(nioEventLoopGroup, threadFactory, false);
        this.wakenUp = new AtomicBoolean();
        this.ioRatio = 50;
        if (provider == null) {
            throw new NullPointerException("selectorProvider");
        }
        this.provider = provider;
        this.selector = this.openSelector();
    }
    
    private Selector openSelector() {
        final AbstractSelector openSelector = this.provider.openSelector();
        if (NioEventLoop.DISABLE_KEYSET_OPTIMIZATION) {
            return openSelector;
        }
        final SelectedSelectionKeySet selectedKeys = new SelectedSelectionKeySet();
        final Class<?> forName = Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
        if (!forName.isAssignableFrom(openSelector.getClass())) {
            return openSelector;
        }
        final Field declaredField = forName.getDeclaredField("selectedKeys");
        final Field declaredField2 = forName.getDeclaredField("publicSelectedKeys");
        declaredField.setAccessible(true);
        declaredField2.setAccessible(true);
        declaredField.set(openSelector, selectedKeys);
        declaredField2.set(openSelector, selectedKeys);
        this.selectedKeys = selectedKeys;
        NioEventLoop.logger.trace("Instrumented an optimized java.util.Set into: {}", openSelector);
        return openSelector;
    }
    
    @Override
    protected Queue newTaskQueue() {
        return PlatformDependent.newMpscQueue();
    }
    
    public void register(final SelectableChannel selectableChannel, final int n, final NioTask nioTask) {
        if (selectableChannel == null) {
            throw new NullPointerException("ch");
        }
        if (n == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        }
        if ((n & ~selectableChannel.validOps()) != 0x0) {
            throw new IllegalArgumentException("invalid interestOps: " + n + "(validOps: " + selectableChannel.validOps() + ')');
        }
        if (nioTask == null) {
            throw new NullPointerException("task");
        }
        if (this.isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        }
        selectableChannel.register(this.selector, n, nioTask);
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
    
    public void rebuildSelector() {
        if (!this.inEventLoop()) {
            this.execute(new Runnable() {
                final NioEventLoop this$0;
                
                @Override
                public void run() {
                    this.this$0.rebuildSelector();
                }
            });
            return;
        }
        final Selector selector = this.selector;
        if (selector == null) {
            return;
        }
        final Selector openSelector = this.openSelector();
        for (final SelectionKey selectionKey : selector.keys()) {
            final Object attachment = selectionKey.attachment();
            if (!selectionKey.isValid() || selectionKey.channel().keyFor(openSelector) != null) {
                continue;
            }
            final int interestOps = selectionKey.interestOps();
            selectionKey.cancel();
            final SelectionKey register = selectionKey.channel().register(openSelector, interestOps, attachment);
            if (attachment instanceof AbstractNioChannel) {
                ((AbstractNioChannel)attachment).selectionKey = register;
            }
            int n = 0;
            ++n;
        }
        this.selector = openSelector;
        selector.close();
        NioEventLoop.logger.info("Migrated " + 0 + " channel(s) to the new Selector.");
    }
    
    @Override
    protected void run() {
        while (true) {
            final boolean andSet = this.wakenUp.getAndSet(false);
            if (this.hasTasks()) {
                this.selectNow();
            }
            else {
                this.select(andSet);
                if (this.wakenUp.get()) {
                    this.selector.wakeup();
                }
            }
            this.cancelledKeys = 0;
            this.needsToSelectAgain = false;
            final int ioRatio = this.ioRatio;
            if (ioRatio == 100) {
                this.processSelectedKeys();
                this.runAllTasks();
            }
            else {
                final long nanoTime = System.nanoTime();
                this.processSelectedKeys();
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
    
    private void processSelectedKeys() {
        if (this.selectedKeys != null) {
            this.processSelectedKeysOptimized(this.selectedKeys.flip());
        }
        else {
            this.processSelectedKeysPlain(this.selector.selectedKeys());
        }
    }
    
    @Override
    protected void cleanup() {
        this.selector.close();
    }
    
    void cancel(final SelectionKey selectionKey) {
        selectionKey.cancel();
        ++this.cancelledKeys;
        if (this.cancelledKeys >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }
    
    @Override
    protected Runnable pollTask() {
        final Runnable pollTask = super.pollTask();
        if (this.needsToSelectAgain) {
            this.selectAgain();
        }
        return pollTask;
    }
    
    private void processSelectedKeysPlain(Set selectedKeys) {
        if (selectedKeys.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> iterator = selectedKeys.iterator();
        while (true) {
            final SelectionKey selectionKey = iterator.next();
            final Object attachment = selectionKey.attachment();
            iterator.remove();
            if (attachment instanceof AbstractNioChannel) {
                processSelectedKey(selectionKey, (AbstractNioChannel)attachment);
            }
            else {
                processSelectedKey(selectionKey, (NioTask)attachment);
            }
            if (!iterator.hasNext()) {
                break;
            }
            if (!this.needsToSelectAgain) {
                continue;
            }
            this.selectAgain();
            selectedKeys = this.selector.selectedKeys();
            if (selectedKeys.isEmpty()) {
                break;
            }
            iterator = selectedKeys.iterator();
        }
    }
    
    private void processSelectedKeysOptimized(SelectionKey[] flip) {
        while (true) {
            final SelectionKey selectionKey = flip[-1];
            if (selectionKey == null) {
                break;
            }
            flip[-1] = null;
            final Object attachment = selectionKey.attachment();
            if (attachment instanceof AbstractNioChannel) {
                processSelectedKey(selectionKey, (AbstractNioChannel)attachment);
            }
            else {
                processSelectedKey(selectionKey, (NioTask)attachment);
            }
            int n = 0;
            if (this.needsToSelectAgain) {
                while (flip[-1] != null) {
                    flip[-1] = null;
                    ++n;
                }
                this.selectAgain();
                flip = this.selectedKeys.flip();
            }
            ++n;
        }
    }
    
    private static void processSelectedKey(final SelectionKey selectionKey, final AbstractNioChannel abstractNioChannel) {
        final AbstractNioChannel.NioUnsafe unsafe = abstractNioChannel.unsafe();
        if (!selectionKey.isValid()) {
            unsafe.close(unsafe.voidPromise());
            return;
        }
        final int readyOps = selectionKey.readyOps();
        if ((readyOps & 0x11) != 0x0 || readyOps == 0) {
            unsafe.read();
            if (!abstractNioChannel.isOpen()) {
                return;
            }
        }
        if ((readyOps & 0x4) != 0x0) {
            abstractNioChannel.unsafe().forceFlush();
        }
        if ((readyOps & 0x8) != 0x0) {
            selectionKey.interestOps(selectionKey.interestOps() & 0xFFFFFFF7);
            unsafe.finishConnect();
        }
    }
    
    private static void processSelectedKey(final SelectionKey selectionKey, final NioTask nioTask) {
        nioTask.channelReady(selectionKey.channel(), selectionKey);
        switch (2) {
            case 0: {
                selectionKey.cancel();
                invokeChannelUnregistered(nioTask, selectionKey, null);
                break;
            }
            case 1: {
                if (!selectionKey.isValid()) {
                    invokeChannelUnregistered(nioTask, selectionKey, null);
                    break;
                }
                break;
            }
        }
    }
    
    private void closeAll() {
        this.selectAgain();
        final Set<SelectionKey> keys = this.selector.keys();
        final ArrayList list = new ArrayList<AbstractNioChannel>(keys.size());
        for (final SelectionKey selectionKey : keys) {
            final Object attachment = selectionKey.attachment();
            if (attachment instanceof AbstractNioChannel) {
                list.add((AbstractNioChannel)attachment);
            }
            else {
                selectionKey.cancel();
                invokeChannelUnregistered((NioTask)attachment, selectionKey, null);
            }
        }
        for (final AbstractNioChannel abstractNioChannel : list) {
            abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
        }
    }
    
    private static void invokeChannelUnregistered(final NioTask nioTask, final SelectionKey selectionKey, final Throwable t) {
        nioTask.channelUnregistered(selectionKey.channel(), t);
    }
    
    @Override
    protected void wakeup(final boolean b) {
        if (!b && this.wakenUp.compareAndSet(false, true)) {
            this.selector.wakeup();
        }
    }
    
    void selectNow() throws IOException {
        this.selector.selectNow();
        if (this.wakenUp.get()) {
            this.selector.wakeup();
        }
    }
    
    private void select(final boolean b) throws IOException {
        final Selector selector = this.selector;
        long nanoTime = System.nanoTime();
        final long n = nanoTime + this.delayNanos(nanoTime);
        while (true) {
            final long n2 = (n - nanoTime + 500000L) / 1000000L;
            if (n2 <= 0L) {
                break;
            }
            final int select = selector.select(n2);
            int n3 = 0;
            ++n3;
            if (select != 0 || b || this.wakenUp.get() || this.hasTasks()) {
                break;
            }
            if (this.hasScheduledTasks()) {
                break;
            }
            if (Thread.interrupted()) {
                if (NioEventLoop.logger.isDebugEnabled()) {
                    NioEventLoop.logger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
                }
                break;
            }
            final long nanoTime2 = System.nanoTime();
            if (nanoTime2 - TimeUnit.MILLISECONDS.toNanos(n2) < nanoTime) {
                if (NioEventLoop.SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && 1 >= NioEventLoop.SELECTOR_AUTO_REBUILD_THRESHOLD) {
                    NioEventLoop.logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding selector.", (Object)1);
                    this.rebuildSelector();
                    this.selector.selectNow();
                    break;
                }
            }
            nanoTime = nanoTime2;
        }
    }
    
    private void selectAgain() {
        this.needsToSelectAgain = false;
        this.selector.selectNow();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
        DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
        final String s = "sun.nio.ch.bugLevel";
        if (SystemPropertyUtil.get(s) == null) {
            System.setProperty(s, "");
        }
        SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        SELECTOR_AUTO_REBUILD_THRESHOLD = 0;
        if (NioEventLoop.logger.isDebugEnabled()) {
            NioEventLoop.logger.debug("-Dio.netty.noKeySetOptimization: {}", (Object)NioEventLoop.DISABLE_KEYSET_OPTIMIZATION);
            NioEventLoop.logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", (Object)NioEventLoop.SELECTOR_AUTO_REBUILD_THRESHOLD);
        }
    }
}
