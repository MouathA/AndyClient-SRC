package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class CallExpr extends Expr
{
    private static final long serialVersionUID = 1L;
    private MemberResolver.Method method;
    
    private CallExpr(final ASTree asTree, final ASTList list) {
        super(67, asTree, list);
        this.method = null;
    }
    
    public void setMethod(final MemberResolver.Method method) {
        this.method = method;
    }
    
    public MemberResolver.Method getMethod() {
        return this.method;
    }
    
    public static CallExpr makeCall(final ASTree asTree, final ASTree asTree2) {
        return new CallExpr(asTree, new ASTList(asTree2));
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atCallExpr(this);
    }
}
