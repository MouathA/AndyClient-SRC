package com.viaversion.viaversion.libs.javassist.tools.reflect;

import java.lang.reflect.*;

public class CannotInvokeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private Throwable err;
    
    public Throwable getReason() {
        return this.err;
    }
    
    public CannotInvokeException(final String s) {
        super(s);
        this.err = null;
    }
    
    public CannotInvokeException(final InvocationTargetException ex) {
        super("by " + ex.getTargetException().toString());
        this.err = null;
        this.err = ex.getTargetException();
    }
    
    public CannotInvokeException(final IllegalAccessException err) {
        super("by " + err.toString());
        this.err = null;
        this.err = err;
    }
    
    public CannotInvokeException(final ClassNotFoundException err) {
        super("by " + err.toString());
        this.err = null;
        this.err = err;
    }
}
