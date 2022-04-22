package org.lwjgl.opencl;

public class OpenCLException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public OpenCLException() {
    }
    
    public OpenCLException(final String s) {
        super(s);
    }
    
    public OpenCLException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public OpenCLException(final Throwable t) {
        super(t);
    }
}
