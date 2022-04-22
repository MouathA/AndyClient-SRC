package org.apache.http;

public class HttpException extends Exception
{
    private static final long serialVersionUID = -5437299376222011036L;
    
    public HttpException() {
    }
    
    public HttpException(final String s) {
        super(s);
    }
    
    public HttpException(final String s, final Throwable t) {
        super(s);
        this.initCause(t);
    }
}
