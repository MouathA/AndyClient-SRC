package com.mojang.authlib.exceptions;

public class AuthenticationUnavailableException extends AuthenticationException
{
    public AuthenticationUnavailableException() {
    }
    
    public AuthenticationUnavailableException(final String s) {
        super(s);
    }
    
    public AuthenticationUnavailableException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public AuthenticationUnavailableException(final Throwable t) {
        super(t);
    }
}
