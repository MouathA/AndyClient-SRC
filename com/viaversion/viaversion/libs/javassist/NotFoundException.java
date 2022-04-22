package com.viaversion.viaversion.libs.javassist;

public class NotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public NotFoundException(final String s) {
        super(s);
    }
    
    public NotFoundException(final String s, final Exception ex) {
        super(s + " because of " + ex.toString());
    }
}
