package io.netty.handler.ssl;

import javax.net.ssl.*;

public class NotSslRecordException extends SSLException
{
    private static final long serialVersionUID = -4316784434770656841L;
    
    public NotSslRecordException() {
        super("");
    }
    
    public NotSslRecordException(final String s) {
        super(s);
    }
    
    public NotSslRecordException(final Throwable t) {
        super(t);
    }
    
    public NotSslRecordException(final String s, final Throwable t) {
        super(s, t);
    }
}
