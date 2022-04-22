package org.apache.commons.lang3.concurrent;

public class ConcurrentException extends Exception
{
    private static final long serialVersionUID = 6622707671812226130L;
    
    protected ConcurrentException() {
    }
    
    public ConcurrentException(final Throwable t) {
        super(ConcurrentUtils.checkedException(t));
    }
    
    public ConcurrentException(final String s, final Throwable t) {
        super(s, ConcurrentUtils.checkedException(t));
    }
}
