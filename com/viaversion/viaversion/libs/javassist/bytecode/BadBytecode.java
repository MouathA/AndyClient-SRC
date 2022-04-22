package com.viaversion.viaversion.libs.javassist.bytecode;

public class BadBytecode extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public BadBytecode(final int n) {
        super("bytecode " + n);
    }
    
    public BadBytecode(final String s) {
        super(s);
    }
    
    public BadBytecode(final String s, final Throwable t) {
        super(s, t);
    }
    
    public BadBytecode(final MethodInfo methodInfo, final Throwable t) {
        super(methodInfo.toString() + " in " + methodInfo.getConstPool().getClassName() + ": " + t.getMessage(), t);
    }
}
