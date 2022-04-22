package io.netty.channel;

import java.util.*;
import io.netty.util.internal.*;
import java.util.concurrent.*;
import io.netty.util.concurrent.*;

public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup
{
    private final Object[] childArgs;
    private final int maxChannels;
    final ThreadFactory threadFactory;
    final Set activeChildren;
    final Queue idleChildren;
    private final ChannelException tooManyChannels;
    private boolean shuttingDown;
    private final Promise terminationFuture;
    private final FutureListener childTerminationListener;
    
    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }
    
    protected ThreadPerChannelEventLoopGroup(final int n) {
        this(n, Executors.defaultThreadFactory(), new Object[0]);
    }
    
    protected ThreadPerChannelEventLoopGroup(final int maxChannels, final ThreadFactory threadFactory, final Object... array) {
        this.activeChildren = Collections.newSetFromMap((Map<Object, Boolean>)PlatformDependent.newConcurrentHashMap());
        this.idleChildren = new ConcurrentLinkedQueue();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.childTerminationListener = new FutureListener() {
            final ThreadPerChannelEventLoopGroup this$0;
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                if (this.this$0.isTerminated()) {
                    ThreadPerChannelEventLoopGroup.access$000(this.this$0).trySuccess(null);
                }
            }
        };
        if (maxChannels < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", maxChannels));
        }
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (array == null) {
            this.childArgs = EmptyArrays.EMPTY_OBJECTS;
        }
        else {
            this.childArgs = array.clone();
        }
        this.maxChannels = maxChannels;
        this.threadFactory = threadFactory;
        (this.tooManyChannels = new ChannelException("too many channels (max: " + maxChannels + ')')).setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }
    
    protected ThreadPerChannelEventLoop newChild(final Object... array) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }
    
    @Override
    public Iterator iterator() {
        return new ReadOnlyIterator(this.activeChildren.iterator());
    }
    
    @Override
    public EventLoop next() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Future shutdownGracefully(final long n, final long n2, final TimeUnit timeUnit) {
        this.shuttingDown = true;
        final Iterator<EventLoop> iterator = this.activeChildren.iterator();
        while (iterator.hasNext()) {
            iterator.next().shutdownGracefully(n, n2, timeUnit);
        }
        final Iterator iterator2 = this.idleChildren.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().shutdownGracefully(n, n2, timeUnit);
        }
        if (this != 0) {
            this.terminationFuture.trySuccess(null);
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
        this.shuttingDown = true;
        final Iterator<EventLoop> iterator = this.activeChildren.iterator();
        while (iterator.hasNext()) {
            iterator.next().shutdown();
        }
        final Iterator iterator2 = this.idleChildren.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().shutdown();
        }
        if (this != 0) {
            this.terminationFuture.trySuccess(null);
        }
    }
    
    @Override
    public boolean isShuttingDown() {
        final Iterator<EventLoop> iterator = this.activeChildren.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isShuttingDown()) {
                return false;
            }
        }
        final Iterator iterator2 = this.idleChildren.iterator();
        while (iterator2.hasNext()) {
            if (!iterator2.next().isShuttingDown()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isShutdown() {
        final Iterator<EventLoop> iterator = this.activeChildren.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isShutdown()) {
                return false;
            }
        }
        final Iterator iterator2 = this.idleChildren.iterator();
        while (iterator2.hasNext()) {
            if (!iterator2.next().isShutdown()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        final long n2 = System.nanoTime() + timeUnit.toNanos(n);
        for (final EventLoop eventLoop : this.activeChildren) {
            long n3;
            do {
                n3 = n2 - System.nanoTime();
                if (n3 <= 0L) {
                    return this.isTerminated();
                }
            } while (!eventLoop.awaitTermination(n3, TimeUnit.NANOSECONDS));
        }
        for (final EventLoop eventLoop2 : this.idleChildren) {
            long n4;
            do {
                n4 = n2 - System.nanoTime();
                if (n4 <= 0L) {
                    return this.isTerminated();
                }
            } while (!eventLoop2.awaitTermination(n4, TimeUnit.NANOSECONDS));
        }
        return this.isTerminated();
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        final EventLoop nextChild = this.nextChild();
        return nextChild.register(channel, new DefaultChannelPromise(channel, nextChild));
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return this.nextChild().register(channel, channelPromise);
    }
    
    private EventLoop nextChild() throws Exception {
        if (this.shuttingDown) {
            throw new RejectedExecutionException("shutting down");
        }
        ThreadPerChannelEventLoop child = this.idleChildren.poll();
        if (child == null) {
            if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
                throw this.tooManyChannels;
            }
            child = this.newChild(this.childArgs);
            child.terminationFuture().addListener(this.childTerminationListener);
        }
        this.activeChildren.add(child);
        return child;
    }
    
    @Override
    public EventExecutor next() {
        return this.next();
    }
    
    static Promise access$000(final ThreadPerChannelEventLoopGroup threadPerChannelEventLoopGroup) {
        return threadPerChannelEventLoopGroup.terminationFuture;
    }
}
