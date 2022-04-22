package org.apache.http.impl.execchain;

import java.io.*;
import org.apache.http.annotation.*;

@Immutable
public class RequestAbortedException extends InterruptedIOException
{
    private static final long serialVersionUID = 4973849966012490112L;
    
    public RequestAbortedException(final String s) {
        super(s);
    }
    
    public RequestAbortedException(final String s, final Throwable t) {
        super(s);
        if (t != null) {
            this.initCause(t);
        }
    }
}
