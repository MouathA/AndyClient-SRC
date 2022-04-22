package com.mojang.authlib.exceptions;

public class UserMigratedException extends InvalidCredentialsException
{
    public UserMigratedException() {
    }
    
    public UserMigratedException(final String s) {
        super(s);
    }
    
    public UserMigratedException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public UserMigratedException(final Throwable t) {
        super(t);
    }
}
