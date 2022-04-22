package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.compiler.ast.*;

public class NoFieldException extends CompileError
{
    private static final long serialVersionUID = 1L;
    private String fieldName;
    private ASTree expr;
    
    public NoFieldException(final String fieldName, final ASTree expr) {
        super("no such field: " + fieldName);
        this.fieldName = fieldName;
        this.expr = expr;
    }
    
    public String getField() {
        return this.fieldName;
    }
    
    public ASTree getExpr() {
        return this.expr;
    }
}
