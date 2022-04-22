package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

public class NoSuchClassError extends Error
{
    private static final long serialVersionUID = 1L;
    private String className;
    
    public NoSuchClassError(final String className, final Error error) {
        super(error.toString(), error);
        this.className = className;
    }
    
    public String getClassName() {
        return this.className;
    }
}
