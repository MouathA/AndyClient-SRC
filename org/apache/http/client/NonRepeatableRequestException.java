package org.apache.http.client;

import org.apache.http.*;
import org.apache.http.annotation.*;

@Immutable
public class NonRepeatableRequestException extends ProtocolException
{
    private static final long serialVersionUID = 82685265288806048L;
    
    public NonRepeatableRequestException() {
    }
    
    public NonRepeatableRequestException(final String s) {
        super(s);
    }
    
    public NonRepeatableRequestException(final String s, final Throwable t) {
        super(s, t);
    }
}
