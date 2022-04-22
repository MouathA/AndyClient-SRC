package org.apache.http.conn.ssl;

public class SSLInitializationException extends IllegalStateException
{
    private static final long serialVersionUID = -8243587425648536702L;
    
    public SSLInitializationException(final String s, final Throwable t) {
        super(s, t);
    }
}
