package org.apache.commons.compress.archivers.dump;

import java.io.*;

public class DumpArchiveException extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public DumpArchiveException() {
    }
    
    public DumpArchiveException(final String s) {
        super(s);
    }
    
    public DumpArchiveException(final Throwable t) {
        this.initCause(t);
    }
    
    public DumpArchiveException(final String s, final Throwable t) {
        super(s);
        this.initCause(t);
    }
}
