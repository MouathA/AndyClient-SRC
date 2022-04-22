package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class StringL extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected String text;
    
    public StringL(final String text) {
        this.text = text;
    }
    
    public String get() {
        return this.text;
    }
    
    @Override
    public String toString() {
        return "\"" + this.text + "\"";
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atStringL(this);
    }
}
