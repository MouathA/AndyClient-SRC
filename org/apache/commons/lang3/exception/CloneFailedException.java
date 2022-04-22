package org.apache.commons.lang3.exception;

public class CloneFailedException extends RuntimeException
{
    private static final long serialVersionUID = 20091223L;
    
    public CloneFailedException(final String s) {
        super(s);
    }
    
    public CloneFailedException(final Throwable t) {
        super(t);
    }
    
    public CloneFailedException(final String s, final Throwable t) {
        super(s, t);
    }
}
