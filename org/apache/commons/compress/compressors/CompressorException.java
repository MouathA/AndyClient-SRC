package org.apache.commons.compress.compressors;

public class CompressorException extends Exception
{
    private static final long serialVersionUID = -2932901310255908814L;
    
    public CompressorException(final String s) {
        super(s);
    }
    
    public CompressorException(final String s, final Throwable t) {
        super(s, t);
    }
}
