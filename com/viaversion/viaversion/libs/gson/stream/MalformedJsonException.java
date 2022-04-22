package com.viaversion.viaversion.libs.gson.stream;

import java.io.*;

public final class MalformedJsonException extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public MalformedJsonException(final String s) {
        super(s);
    }
    
    public MalformedJsonException(final String s, final Throwable t) {
        super(s);
        this.initCause(t);
    }
    
    public MalformedJsonException(final Throwable t) {
        this.initCause(t);
    }
}
