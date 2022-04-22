package com.mojang.authlib.exceptions;

public class AuthenticationException extends Exception
{
    public AuthenticationException() {
    }
    
    public AuthenticationException(final String s) {
        super(s);
    }
    
    public AuthenticationException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public AuthenticationException(final Throwable t) {
        super(t);
    }
}
