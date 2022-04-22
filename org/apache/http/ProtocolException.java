package org.apache.http;

public class ProtocolException extends HttpException
{
    private static final long serialVersionUID = -2143571074341228994L;
    
    public ProtocolException() {
    }
    
    public ProtocolException(final String s) {
        super(s);
    }
    
    public ProtocolException(final String s, final Throwable t) {
        super(s, t);
    }
}
