package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class BinExpr extends Expr
{
    private static final long serialVersionUID = 1L;
    
    private BinExpr(final int n, final ASTree asTree, final ASTList list) {
        super(n, asTree, list);
    }
    
    public static BinExpr makeBin(final int n, final ASTree asTree, final ASTree asTree2) {
        return new BinExpr(n, asTree, new ASTList(asTree2));
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atBinExpr(this);
    }
}
