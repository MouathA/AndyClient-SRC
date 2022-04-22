package org.lwjgl;

public class LWJGLException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public LWJGLException() {
    }
    
    public LWJGLException(final String s) {
        super(s);
    }
    
    public LWJGLException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public LWJGLException(final Throwable t) {
        super(t);
    }
}
