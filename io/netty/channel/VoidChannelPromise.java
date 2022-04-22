package io.netty.channel;

import java.util.concurrent.*;
import io.netty.util.concurrent.*;

final class VoidChannelPromise extends AbstractFuture implements ChannelPromise
{
    private final Channel channel;
    private final boolean fireException;
    
    VoidChannelPromise(final Channel channel, final boolean fireException) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
        this.fireException = fireException;
    }
    
    @Override
    public VoidChannelPromise addListener(final GenericFutureListener genericFutureListener) {
        return this;
    }
    
    @Override
    public VoidChannelPromise addListeners(final GenericFutureListener... array) {
        return this;
    }
    
    @Override
    public VoidChannelPromise removeListener(final GenericFutureListener genericFutureListener) {
        return this;
    }
    
    @Override
    public VoidChannelPromise removeListeners(final GenericFutureListener... array) {
        return this;
    }
    
    @Override
    public VoidChannelPromise await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this;
    }
    
    @Override
    public boolean await(final long n, final TimeUnit timeUnit) {
        return false;
    }
    
    @Override
    public boolean await(final long n) {
        return false;
    }
    
    @Override
    public VoidChannelPromise awaitUninterruptibly() {
        return this;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long n, final TimeUnit timeUnit) {
        return false;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long n) {
        return false;
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public boolean isDone() {
        return false;
    }
    
    @Override
    public boolean isSuccess() {
        return false;
    }
    
    @Override
    public boolean setUncancellable() {
        return true;
    }
    
    @Override
    public boolean isCancellable() {
        return false;
    }
    
    @Override
    public boolean isCancelled() {
        return false;
    }
    
    @Override
    public Throwable cause() {
        return null;
    }
    
    @Override
    public VoidChannelPromise sync() {
        return this;
    }
    
    @Override
    public VoidChannelPromise syncUninterruptibly() {
        return this;
    }
    
    @Override
    public VoidChannelPromise setFailure(final Throwable t) {
        this.fireException(t);
        return this;
    }
    
    @Override
    public VoidChannelPromise setSuccess() {
        return this;
    }
    
    @Override
    public boolean tryFailure(final Throwable t) {
        this.fireException(t);
        return false;
    }
    
    @Override
    public boolean cancel(final boolean b) {
        return false;
    }
    
    @Override
    public boolean trySuccess() {
        return false;
    }
    
    private static void fail() {
        throw new IllegalStateException("void future");
    }
    
    @Override
    public VoidChannelPromise setSuccess(final Void void1) {
        return this;
    }
    
    public boolean trySuccess(final Void void1) {
        return false;
    }
    
    @Override
    public Void getNow() {
        return null;
    }
    
    private void fireException(final Throwable t) {
        if (this.fireException && this.channel.isRegistered()) {
            this.channel.pipeline().fireExceptionCaught(t);
        }
    }
    
    @Override
    public Object getNow() {
        return this.getNow();
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
    public boolean trySuccess(final Object o) {
        return this.trySuccess((Void)o);
    }
    
    @Override
    public Promise setSuccess(final Object o) {
        return this.setSuccess((Void)o);
    }
}
