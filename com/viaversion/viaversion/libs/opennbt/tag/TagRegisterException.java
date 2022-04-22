package com.viaversion.viaversion.libs.opennbt.tag;

public class TagRegisterException extends RuntimeException
{
    private static final long serialVersionUID = -2022049594558041160L;
    
    public TagRegisterException() {
    }
    
    public TagRegisterException(final String s) {
        super(s);
    }
    
    public TagRegisterException(final Throwable t) {
        super(t);
    }
    
    public TagRegisterException(final String s, final Throwable t) {
        super(s, t);
    }
}
