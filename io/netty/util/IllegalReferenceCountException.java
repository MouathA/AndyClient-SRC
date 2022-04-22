package io.netty.util;

public class IllegalReferenceCountException extends IllegalStateException
{
    private static final long serialVersionUID = -2507492394288153468L;
    
    public IllegalReferenceCountException() {
    }
    
    public IllegalReferenceCountException(final int n) {
        this("refCnt: " + n);
    }
    
    public IllegalReferenceCountException(final int n, final int n2) {
        this("refCnt: " + n + ", " + ((n2 > 0) ? ("increment: " + n2) : ("decrement: " + -n2)));
    }
    
    public IllegalReferenceCountException(final String s) {
        super(s);
    }
    
    public IllegalReferenceCountException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public IllegalReferenceCountException(final Throwable t) {
        super(t);
    }
}
