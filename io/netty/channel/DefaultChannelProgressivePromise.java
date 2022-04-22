package io.netty.channel;

import io.netty.util.concurrent.*;

public class DefaultChannelProgressivePromise extends DefaultProgressivePromise implements ChannelProgressivePromise, ChannelFlushPromiseNotifier.FlushCheckpoint
{
    private final Channel channel;
    private long checkpoint;
    
    public DefaultChannelProgressivePromise(final Channel channel) {
        this.channel = channel;
    }
    
    public DefaultChannelProgressivePromise(final Channel channel, final EventExecutor eventExecutor) {
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
    public ChannelProgressivePromise setSuccess() {
        return this.setSuccess((Void)null);
    }
    
    @Override
    public ChannelProgressivePromise setSuccess(final Void success) {
        super.setSuccess((Object)success);
        return this;
    }
    
    @Override
    public boolean trySuccess() {
        return this.trySuccess(null);
    }
    
    @Override
    public ChannelProgressivePromise setFailure(final Throwable failure) {
        super.setFailure(failure);
        return this;
    }
    
    @Override
    public ChannelProgressivePromise setProgress(final long n, final long n2) {
        super.setProgress(n, n2);
        return this;
    }
    
    @Override
    public ChannelProgressivePromise addListener(final GenericFutureListener genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ChannelProgressivePromise addListeners(final GenericFutureListener... array) {
        super.addListeners(array);
        return this;
    }
    
    @Override
    public ChannelProgressivePromise removeListener(final GenericFutureListener genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ChannelProgressivePromise removeListeners(final GenericFutureListener... array) {
        super.removeListeners(array);
        return this;
    }
    
    @Override
    public ChannelProgressivePromise sync() throws InterruptedException {
        super.sync();
        return this;
    }
    
    @Override
    public ChannelProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }
    
    @Override
    public ChannelProgressivePromise await() throws InterruptedException {
        super.await();
        return this;
    }
    
    @Override
    public ChannelProgressivePromise awaitUninterruptibly() {
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
    public ChannelProgressivePromise promise() {
        return this;
    }
    
    @Override
    protected void checkDeadLock() {
        if (this.channel().isRegistered()) {
            super.checkDeadLock();
        }
    }
    
    @Override
    public ProgressivePromise setFailure(final Throwable failure) {
        return this.setFailure(failure);
    }
    
    @Override
    public ProgressivePromise setSuccess(final Object o) {
        return this.setSuccess((Void)o);
    }
    
    @Override
    public ProgressivePromise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public ProgressivePromise await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public ProgressivePromise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public ProgressivePromise sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public ProgressivePromise removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public ProgressivePromise removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public ProgressivePromise addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public ProgressivePromise addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public ProgressivePromise setProgress(final long n, final long n2) {
        return this.setProgress(n, n2);
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
    public Promise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public Promise await() throws InterruptedException {
        return this.await();
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
    public Promise setFailure(final Throwable failure) {
        return this.setFailure(failure);
    }
    
    @Override
    public Promise setSuccess(final Object o) {
        return this.setSuccess((Void)o);
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
    public ProgressiveFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public ProgressiveFuture await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public ProgressiveFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public ProgressiveFuture sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public ProgressiveFuture removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public ProgressiveFuture removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public ProgressiveFuture addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public ProgressiveFuture addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public ChannelProgressiveFuture awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public ChannelProgressiveFuture await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public ChannelProgressiveFuture syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public ChannelProgressiveFuture sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public ChannelProgressiveFuture removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public ChannelProgressiveFuture removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public ChannelProgressiveFuture addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public ChannelProgressiveFuture addListener(final GenericFutureListener genericFutureListener) {
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
    
    @Override
    public ChannelPromise awaitUninterruptibly() {
        return this.awaitUninterruptibly();
    }
    
    @Override
    public ChannelPromise await() throws InterruptedException {
        return this.await();
    }
    
    @Override
    public ChannelPromise syncUninterruptibly() {
        return this.syncUninterruptibly();
    }
    
    @Override
    public ChannelPromise sync() throws InterruptedException {
        return this.sync();
    }
    
    @Override
    public ChannelPromise removeListeners(final GenericFutureListener[] array) {
        return this.removeListeners(array);
    }
    
    @Override
    public ChannelPromise removeListener(final GenericFutureListener genericFutureListener) {
        return this.removeListener(genericFutureListener);
    }
    
    @Override
    public ChannelPromise addListeners(final GenericFutureListener[] array) {
        return this.addListeners(array);
    }
    
    @Override
    public ChannelPromise addListener(final GenericFutureListener genericFutureListener) {
        return this.addListener(genericFutureListener);
    }
    
    @Override
    public ChannelPromise setFailure(final Throwable failure) {
        return this.setFailure(failure);
    }
    
    @Override
    public ChannelPromise setSuccess() {
        return this.setSuccess();
    }
    
    @Override
    public ChannelPromise setSuccess(final Void success) {
        return this.setSuccess(success);
    }
    
    @Override
    public ChannelPromise promise() {
        return this.promise();
    }
}
