package io.netty.channel;

import io.netty.util.concurrent.*;

abstract class CompleteChannelFuture extends CompleteFuture implements ChannelFuture
{
    private final Channel channel;
    
    protected CompleteChannelFuture(final Channel channel, final EventExecutor eventExecutor) {
        super(eventExecutor);
        if (channel == null) {
            throw new NullPointerException("channel");
        }
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
    public ChannelFuture addListener(final GenericFutureListener genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ChannelFuture addListeners(final GenericFutureListener... array) {
        super.addListeners(array);
        return this;
    }
    
    @Override
    public ChannelFuture removeListener(final GenericFutureListener genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ChannelFuture removeListeners(final GenericFutureListener... array) {
        super.removeListeners(array);
        return this;
    }
    
    @Override
    public ChannelFuture syncUninterruptibly() {
        return this;
    }
    
    @Override
    public ChannelFuture sync() throws InterruptedException {
        return this;
    }
    
    @Override
    public ChannelFuture await() throws InterruptedException {
        return this;
    }
    
    @Override
    public ChannelFuture awaitUninterruptibly() {
        return this;
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public Void getNow() {
        return null;
    }
    
    @Override
    public Future awaitUninterruptibly() {
        return this.awaitUninterruptibly();
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
    public Future await() throws InterruptedException {
        return this.await();
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
    public Object getNow() {
        return this.getNow();
    }
}
