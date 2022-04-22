package org.apache.commons.compress.archivers;

public class ArchiveException extends Exception
{
    private static final long serialVersionUID = 2772690708123267100L;
    
    public ArchiveException(final String s) {
        super(s);
    }
    
    public ArchiveException(final String s, final Exception ex) {
        super(s);
        this.initCause(ex);
    }
}
