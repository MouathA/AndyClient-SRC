package com.viaversion.viaversion.libs.opennbt.conversion;

public class ConverterRegisterException extends RuntimeException
{
    private static final long serialVersionUID = -2022049594558041160L;
    
    public ConverterRegisterException() {
    }
    
    public ConverterRegisterException(final String s) {
        super(s);
    }
    
    public ConverterRegisterException(final Throwable t) {
        super(t);
    }
    
    public ConverterRegisterException(final String s, final Throwable t) {
        super(s, t);
    }
}
