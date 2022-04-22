package io.netty.util.concurrent;

public class DefaultProgressivePromise extends DefaultPromise implements ProgressivePromise
{
    public DefaultProgressivePromise(final EventExecutor eventExecutor) {
        super(eventExecutor);
    }
    
    protected DefaultProgressivePromise() {
    }
    
    @Override
    public ProgressivePromise setProgress(final long n, long n2) {
        if (n2 < 0L) {
            n2 = -1L;
            if (n < 0L) {
                throw new IllegalArgumentException("progress: " + n + " (expected: >= 0)");
            }
        }
        else if (n < 0L || n > n2) {
            throw new IllegalArgumentException("progress: " + n + " (expected: 0 <= progress <= total (" + n2 + "))");
        }
        if (this.isDone()) {
            throw new IllegalStateException("complete already");
        }
        this.notifyProgressiveListeners(n, n2);
        return this;
    }
    
    @Override
    public boolean tryProgress(final long n, long n2) {
        if (n2 < 0L) {
            n2 = -1L;
            if (n < 0L || this.isDone()) {
                return false;
            }
        }
        else if (n < 0L || n > n2 || this.isDone()) {
            return false;
        }
        this.notifyProgressiveListeners(n, n2);
        return true;
    }
    
    @Override
    public ProgressivePromise addListener(final GenericFutureListener genericFutureListener) {
        super.addListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ProgressivePromise addListeners(final GenericFutureListener... array) {
        super.addListeners(array);
        return this;
    }
    
    @Override
    public ProgressivePromise removeListener(final GenericFutureListener genericFutureListener) {
        super.removeListener(genericFutureListener);
        return this;
    }
    
    @Override
    public ProgressivePromise removeListeners(final GenericFutureListener... array) {
        super.removeListeners(array);
        return this;
    }
    
    @Override
    public ProgressivePromise sync() throws InterruptedException {
        super.sync();
        return this;
    }
    
    @Override
    public ProgressivePromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }
    
    @Override
    public ProgressivePromise await() throws InterruptedException {
        super.await();
        return this;
    }
    
    @Override
    public ProgressivePromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }
    
    @Override
    public ProgressivePromise setSuccess(final Object success) {
        super.setSuccess(success);
        return this;
    }
    
    @Override
    public ProgressivePromise setFailure(final Throwable failure) {
        super.setFailure(failure);
        return this;
    }
    
    @Override
    public Promise setFailure(final Throwable failure) {
        return this.setFailure(failure);
    }
    
    @Override
    public Promise setSuccess(final Object success) {
        return this.setSuccess(success);
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
}
