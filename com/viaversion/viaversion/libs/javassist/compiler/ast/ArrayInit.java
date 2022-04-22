package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class ArrayInit extends ASTList
{
    private static final long serialVersionUID = 1L;
    
    public ArrayInit(final ASTree asTree) {
        super(asTree);
    }
    
    public int size() {
        final int length = this.length();
        if (length == 1 && this.head() == null) {
            return 0;
        }
        return length;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atArrayInit(this);
    }
    
    public String getTag() {
        return "array";
    }
}
