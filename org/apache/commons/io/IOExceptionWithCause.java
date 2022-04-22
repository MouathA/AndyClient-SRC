package org.apache.commons.io;

import java.io.*;

public class IOExceptionWithCause extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public IOExceptionWithCause(final String s, final Throwable t) {
        super(s);
        this.initCause(t);
    }
    
    public IOExceptionWithCause(final Throwable t) {
        super((t == null) ? null : t.toString());
        this.initCause(t);
    }
}
