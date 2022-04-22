package com.mojang.authlib.exceptions;

public class InvalidCredentialsException extends AuthenticationException
{
    public InvalidCredentialsException() {
    }
    
    public InvalidCredentialsException(final String s) {
        super(s);
    }
    
    public InvalidCredentialsException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public InvalidCredentialsException(final Throwable t) {
        super(t);
    }
}
