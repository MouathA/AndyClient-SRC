package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.*;

public class CompileError extends Exception
{
    private static final long serialVersionUID = 1L;
    private Lex lex;
    private String reason;
    
    public CompileError(final String reason, final Lex lex) {
        this.reason = reason;
        this.lex = lex;
    }
    
    public CompileError(final String reason) {
        this.reason = reason;
        this.lex = null;
    }
    
    public CompileError(final CannotCompileException ex) {
        this(ex.getReason());
    }
    
    public CompileError(final NotFoundException ex) {
        this("cannot find " + ex.getMessage());
    }
    
    public Lex getLex() {
        return this.lex;
    }
    
    @Override
    public String getMessage() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return "compile error: " + this.reason;
    }
}
