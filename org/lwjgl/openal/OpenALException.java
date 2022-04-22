package org.lwjgl.openal;

public class OpenALException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public OpenALException() {
    }
    
    public OpenALException(final int n) {
        super("OpenAL error: " + AL10.alGetString(n) + " (" + n + ")");
    }
    
    public OpenALException(final String s) {
        super(s);
    }
    
    public OpenALException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public OpenALException(final Throwable t) {
        super(t);
    }
}
