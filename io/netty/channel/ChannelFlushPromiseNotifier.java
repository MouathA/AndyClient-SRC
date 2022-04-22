package io.netty.channel;

import java.util.*;

public final class ChannelFlushPromiseNotifier
{
    private long writeCounter;
    private final Queue flushCheckpoints;
    private final boolean tryNotify;
    
    public ChannelFlushPromiseNotifier(final boolean tryNotify) {
        this.flushCheckpoints = new ArrayDeque();
        this.tryNotify = tryNotify;
    }
    
    public ChannelFlushPromiseNotifier() {
        this(false);
    }
    
    @Deprecated
    public ChannelFlushPromiseNotifier add(final ChannelPromise channelPromise, final int n) {
        return this.add(channelPromise, (long)n);
    }
    
    public ChannelFlushPromiseNotifier add(final ChannelPromise channelPromise, final long n) {
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        }
        if (n < 0L) {
            throw new IllegalArgumentException("pendingDataSize must be >= 0 but was " + n);
        }
        final long n2 = this.writeCounter + n;
        if (channelPromise instanceof FlushCheckpoint) {
            final FlushCheckpoint flushCheckpoint = (FlushCheckpoint)channelPromise;
            flushCheckpoint.flushCheckpoint(n2);
            this.flushCheckpoints.add(flushCheckpoint);
        }
        else {
            this.flushCheckpoints.add(new DefaultFlushCheckpoint(n2, channelPromise));
        }
        return this;
    }
    
    public ChannelFlushPromiseNotifier increaseWriteCounter(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("delta must be >= 0 but was " + n);
        }
        this.writeCounter += n;
        return this;
    }
    
    public long writeCounter() {
        return this.writeCounter;
    }
    
    public ChannelFlushPromiseNotifier notifyPromises() {
        this.notifyPromises0(null);
        return this;
    }
    
    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures() {
        return this.notifyPromises();
    }
    
    public ChannelFlushPromiseNotifier notifyPromises(final Throwable failure) {
        this.notifyPromises();
        while (true) {
            final FlushCheckpoint flushCheckpoint = this.flushCheckpoints.poll();
            if (flushCheckpoint == null) {
                break;
            }
            if (this.tryNotify) {
                flushCheckpoint.promise().tryFailure(failure);
            }
            else {
                flushCheckpoint.promise().setFailure(failure);
            }
        }
        return this;
    }
    
    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(final Throwable t) {
        return this.notifyPromises(t);
    }
    
    public ChannelFlushPromiseNotifier notifyPromises(final Throwable t, final Throwable failure) {
        this.notifyPromises0(t);
        while (true) {
            final FlushCheckpoint flushCheckpoint = this.flushCheckpoints.poll();
            if (flushCheckpoint == null) {
                break;
            }
            if (this.tryNotify) {
                flushCheckpoint.promise().tryFailure(failure);
            }
            else {
                flushCheckpoint.promise().setFailure(failure);
            }
        }
        return this;
    }
    
    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(final Throwable t, final Throwable t2) {
        return this.notifyPromises(t, t2);
    }
    
    private void notifyPromises0(final Throwable failure) {
        if (this.flushCheckpoints.isEmpty()) {
            this.writeCounter = 0L;
            return;
        }
        final long writeCounter = this.writeCounter;
        while (true) {
            final FlushCheckpoint flushCheckpoint = this.flushCheckpoints.peek();
            if (flushCheckpoint == null) {
                this.writeCounter = 0L;
                break;
            }
            if (flushCheckpoint.flushCheckpoint() > writeCounter) {
                if (writeCounter > 0L && this.flushCheckpoints.size() == 1) {
                    this.writeCounter = 0L;
                    flushCheckpoint.flushCheckpoint(flushCheckpoint.flushCheckpoint() - writeCounter);
                    break;
                }
                break;
            }
            else {
                this.flushCheckpoints.remove();
                final ChannelPromise promise = flushCheckpoint.promise();
                if (failure == null) {
                    if (this.tryNotify) {
                        promise.trySuccess();
                    }
                    else {
                        promise.setSuccess();
                    }
                }
                else if (this.tryNotify) {
                    promise.tryFailure(failure);
                }
                else {
                    promise.setFailure(failure);
                }
            }
        }
        final long writeCounter2 = this.writeCounter;
        if (writeCounter2 >= 549755813888L) {
            this.writeCounter = 0L;
            for (final FlushCheckpoint flushCheckpoint2 : this.flushCheckpoints) {
                flushCheckpoint2.flushCheckpoint(flushCheckpoint2.flushCheckpoint() - writeCounter2);
            }
        }
    }
    
    private static class DefaultFlushCheckpoint implements FlushCheckpoint
    {
        private long checkpoint;
        private final ChannelPromise future;
        
        DefaultFlushCheckpoint(final long checkpoint, final ChannelPromise future) {
            this.checkpoint = checkpoint;
            this.future = future;
        }
        
        @Override
        public long flushCheckpoint() {
            return this.checkpoint;
        }
        
        @Override
        public void flushCheckpoint(final long checkpoint) {
            this.checkpoint = checkpoint;
        }
        
        @Override
        public ChannelPromise promise() {
            return this.future;
        }
    }
    
    interface FlushCheckpoint
    {
        long flushCheckpoint();
        
        void flushCheckpoint(final long p0);
        
        ChannelPromise promise();
    }
}
