package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class AssignExpr extends Expr
{
    private static final long serialVersionUID = 1L;
    
    private AssignExpr(final int n, final ASTree asTree, final ASTList list) {
        super(n, asTree, list);
    }
    
    public static AssignExpr makeAssign(final int n, final ASTree asTree, final ASTree asTree2) {
        return new AssignExpr(n, asTree, new ASTList(asTree2));
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atAssignExpr(this);
    }
}
