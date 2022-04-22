package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.*;

public class CancelException extends Exception
{
    public static final CancelException CACHED;
    
    public CancelException() {
    }
    
    public CancelException(final String s) {
        super(s);
    }
    
    public CancelException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CancelException(final Throwable t) {
        super(t);
    }
    
    public CancelException(final String s, final Throwable t, final boolean b, final boolean b2) {
        super(s, t, b, b2);
    }
    
    public static CancelException generate() {
        return Via.getManager().isDebug() ? new CancelException() : CancelException.CACHED;
    }
    
    static {
        CACHED = new CancelException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these") {
            @Override
            public Throwable fillInStackTrace() {
                return this;
            }
        };
    }
}
