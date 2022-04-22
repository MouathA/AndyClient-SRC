package com.viaversion.viaversion.libs.opennbt.conversion;

public class ConversionException extends RuntimeException
{
    private static final long serialVersionUID = -2022049594558041160L;
    
    public ConversionException() {
    }
    
    public ConversionException(final String s) {
        super(s);
    }
    
    public ConversionException(final Throwable t) {
        super(t);
    }
    
    public ConversionException(final String s, final Throwable t) {
        super(s, t);
    }
}
