package org.apache.commons.lang3.concurrent;

public class ConcurrentRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = -6582182735562919670L;
    
    protected ConcurrentRuntimeException() {
    }
    
    public ConcurrentRuntimeException(final Throwable t) {
        super(ConcurrentUtils.checkedException(t));
    }
    
    public ConcurrentRuntimeException(final String s, final Throwable t) {
        super(s, ConcurrentUtils.checkedException(t));
    }
}
