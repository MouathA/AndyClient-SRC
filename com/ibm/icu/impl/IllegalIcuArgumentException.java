package com.ibm.icu.impl;

public class IllegalIcuArgumentException extends IllegalArgumentException
{
    private static final long serialVersionUID = 3789261542830211225L;
    
    public IllegalIcuArgumentException(final String s) {
        super(s);
    }
    
    public IllegalIcuArgumentException(final Throwable t) {
        super(t);
    }
    
    public IllegalIcuArgumentException(final String s, final Throwable t) {
        super(s, t);
    }
    
    @Override
    public synchronized IllegalIcuArgumentException initCause(final Throwable t) {
        return (IllegalIcuArgumentException)super.initCause(t);
    }
    
    @Override
    public Throwable initCause(final Throwable t) {
        return this.initCause(t);
    }
}
