package org.apache.logging.log4j;

public class LoggingException extends RuntimeException
{
    private static final long serialVersionUID = 6366395965071580537L;
    
    public LoggingException(final String s) {
        super(s);
    }
    
    public LoggingException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public LoggingException(final Throwable t) {
        super(t);
    }
}
