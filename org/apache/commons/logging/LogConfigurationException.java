package org.apache.commons.logging;

public class LogConfigurationException extends RuntimeException
{
    private static final long serialVersionUID = 8486587136871052495L;
    protected Throwable cause;
    
    public LogConfigurationException() {
        this.cause = null;
    }
    
    public LogConfigurationException(final String s) {
        super(s);
        this.cause = null;
    }
    
    public LogConfigurationException(final Throwable t) {
        this((t == null) ? null : t.toString(), t);
    }
    
    public LogConfigurationException(final String s, final Throwable cause) {
        super(s + " (Caused by " + cause + ")");
        this.cause = null;
        this.cause = cause;
    }
    
    public Throwable getCause() {
        return this.cause;
    }
}
