package org.apache.http.auth;

import org.apache.http.annotation.*;

@Immutable
public class InvalidCredentialsException extends AuthenticationException
{
    private static final long serialVersionUID = -4834003835215460648L;
    
    public InvalidCredentialsException() {
    }
    
    public InvalidCredentialsException(final String s) {
        super(s);
    }
    
    public InvalidCredentialsException(final String s, final Throwable t) {
        super(s, t);
    }
}
