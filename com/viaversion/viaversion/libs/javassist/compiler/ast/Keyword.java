package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Keyword extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected int tokenId;
    
    public Keyword(final int tokenId) {
        this.tokenId = tokenId;
    }
    
    public int get() {
        return this.tokenId;
    }
    
    @Override
    public String toString() {
        return "id:" + this.tokenId;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atKeyword(this);
    }
}
