package com.mojang.authlib.yggdrasil;

public class ProfileNotFoundException extends RuntimeException
{
    public ProfileNotFoundException() {
    }
    
    public ProfileNotFoundException(final String s) {
        super(s);
    }
    
    public ProfileNotFoundException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ProfileNotFoundException(final Throwable t) {
        super(t);
    }
}
