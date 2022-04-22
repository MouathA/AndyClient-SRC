package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Symbol extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected String identifier;
    
    public Symbol(final String identifier) {
        this.identifier = identifier;
    }
    
    public String get() {
        return this.identifier;
    }
    
    @Override
    public String toString() {
        return this.identifier;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atSymbol(this);
    }
}
