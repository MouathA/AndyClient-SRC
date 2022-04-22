package org.lwjgl.opengl;

public class OpenGLException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public OpenGLException(final int n) {
        this(createErrorMessage(n));
    }
    
    private static String createErrorMessage(final int n) {
        return Util.translateGLErrorString(n) + " (" + n + ")";
    }
    
    public OpenGLException() {
    }
    
    public OpenGLException(final String s) {
        super(s);
    }
    
    public OpenGLException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public OpenGLException(final Throwable t) {
        super(t);
    }
}
