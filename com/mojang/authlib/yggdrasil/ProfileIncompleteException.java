package com.mojang.authlib.yggdrasil;

public class ProfileIncompleteException extends RuntimeException
{
    public ProfileIncompleteException() {
    }
    
    public ProfileIncompleteException(final String s) {
        super(s);
    }
    
    public ProfileIncompleteException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ProfileIncompleteException(final Throwable t) {
        super(t);
    }
}
