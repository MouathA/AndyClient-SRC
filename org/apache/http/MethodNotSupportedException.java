package org.apache.http;

public class MethodNotSupportedException extends HttpException
{
    private static final long serialVersionUID = 3365359036840171201L;
    
    public MethodNotSupportedException(final String s) {
        super(s);
    }
    
    public MethodNotSupportedException(final String s, final Throwable t) {
        super(s, t);
    }
}
