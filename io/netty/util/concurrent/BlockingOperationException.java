package io.netty.util.concurrent;

public class BlockingOperationException extends IllegalStateException
{
    private static final long serialVersionUID = 2462223247762460301L;
    
    public BlockingOperationException() {
    }
    
    public BlockingOperationException(final String s) {
        super(s);
    }
    
    public BlockingOperationException(final Throwable t) {
        super(t);
    }
    
    public BlockingOperationException(final String s, final Throwable t) {
        super(s, t);
    }
}
