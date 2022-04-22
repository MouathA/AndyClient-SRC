package io.netty.util.concurrent;

public final class SucceededFuture extends CompleteFuture
{
    private final Object result;
    
    public SucceededFuture(final EventExecutor eventExecutor, final Object result) {
        super(eventExecutor);
        this.result = result;
    }
    
    @Override
    public Throwable cause() {
        return null;
    }
    
    @Override
    public boolean isSuccess() {
        return true;
    }
    
    @Override
    public Object getNow() {
        return this.result;
    }
}
