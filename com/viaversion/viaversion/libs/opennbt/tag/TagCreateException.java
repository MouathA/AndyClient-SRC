package com.viaversion.viaversion.libs.opennbt.tag;

public class TagCreateException extends Exception
{
    private static final long serialVersionUID = -2022049594558041160L;
    
    public TagCreateException() {
    }
    
    public TagCreateException(final String s) {
        super(s);
    }
    
    public TagCreateException(final Throwable t) {
        super(t);
    }
    
    public TagCreateException(final String s, final Throwable t) {
        super(s, t);
    }
}
