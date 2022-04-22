package org.apache.http.auth;

import org.apache.http.*;
import org.apache.http.annotation.*;

@Immutable
public class AuthenticationException extends ProtocolException
{
    private static final long serialVersionUID = -6794031905674764776L;
    
    public AuthenticationException() {
    }
    
    public AuthenticationException(final String s) {
        super(s);
    }
    
    public AuthenticationException(final String s, final Throwable t) {
        super(s, t);
    }
}
