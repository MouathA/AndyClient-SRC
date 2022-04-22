package io.netty.channel;

import io.netty.util.concurrent.*;

public class DefaultChannelPromise extends DefaultPromise implements ChannelPromise, ChannelFlushPromiseNotifier.FlushCheckpoint
{
    private final Channel channel;
    private long checkpoint;
    
    public DefaultChannelPromise(final Channel channel) {
        this.channel = channel;
    }
    
    public DefaultChannelPromise(final Channel channel, final EventExecutor eventExecutor) {
        super(eventExecutor);
        this.channel = channel;
    }
    
    @Override
    protected EventExecutor executor() {
        final EventExecutor executor = super.executor();
        if (executor == null) {
            return this.channel().eventLoop();
        }
        return executor;
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public ChannelPromise setSuccess() {
        return this.setSuccess(null);
    }
    
    @Override
    public ChannelPromise setSuccess(final Void success) {
        super.setSuccess(success);
        return this;
    }
    
    @Override
    public boolean trySuccess() {
        return this.trySuccess(null);
    }
    
    @Override
    public ChannelPromise setFailure(final Throwable failure) {
        super.setFailure(failure);
        return this;
    }
    
    @Override
    public ChannelPromise addListener(final GenericFutureListener genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ChannelPromise addListeners(final GenericFutureListener... array) {
        super.addListeners(array);
        return this;
    }
    
    @Override
    public ChannelPromise removeListener(final GenericFutureListener genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ChannelPromise removeListeners(final GenericFutureListener... array) {
        super.removeListeners(array);
        return this;
    }
    
    @Override
    public ChannelPromise sync() throws InterruptedException {
        super.sync();
        return this;
    }
    
    @Override
    public ChannelPromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }
    
    @Override
    public ChannelPromise await() throws InterruptedException {
        super.await();
        return this;
    }
    
    @Override
    public ChannelPromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
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
        return this;
    }
    
    @Override
    protected void checkDeadLock() {
        if (this.channel().isRegistered()) {
            super.checkDeadLock();
        }
    }
    
    @Override
    public Promise setFailure(final Throwable failure) {
        return this.setFailure(failure);
    }
    
    @Override
    public Promise setSuccess(final Object o) {
        return this.setSuccess((Void)o);
    }
    
    @Override
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public Promise await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public Promise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public Promise sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public Promise removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public Promise removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public Promise addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public Promise addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public Future await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public Future syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public Future sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public Future removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public Future removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public Future addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public Future addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public ChannelFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public ChannelFuture await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public ChannelFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public ChannelFuture removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public ChannelFuture removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public ChannelFuture addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public ChannelFuture addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
}
