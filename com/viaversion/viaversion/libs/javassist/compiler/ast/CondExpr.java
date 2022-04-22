package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class CondExpr extends ASTList
{
    private static final long serialVersionUID = 1L;
    
    public CondExpr(final ASTree asTree, final ASTree asTree2, final ASTree asTree3) {
        super(asTree, new ASTList(asTree2, new ASTList(asTree3)));
    }
    
    public ASTree condExpr() {
        return this.head();
    }
    
    public void setCond(final ASTree head) {
        this.setHead(head);
    }
    
    public ASTree thenExpr() {
        return this.tail().head();
    }
    
    public void setThen(final ASTree head) {
        this.tail().setHead(head);
    }
    
    public ASTree elseExpr() {
        return this.tail().tail().head();
    }
    
    public void setElse(final ASTree head) {
        this.tail().tail().setHead(head);
    }
    
    public String getTag() {
        return "?:";
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atCondExpr(this);
    }
}
