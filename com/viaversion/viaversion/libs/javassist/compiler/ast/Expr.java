package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Expr extends ASTList implements TokenId
{
    private static final long serialVersionUID = 1L;
    protected int operatorId;
    
    Expr(final int operatorId, final ASTree asTree, final ASTList list) {
        super(asTree, list);
        this.operatorId = operatorId;
    }
    
    Expr(final int operatorId, final ASTree asTree) {
        super(asTree);
        this.operatorId = operatorId;
    }
    
    public static Expr make(final int n, final ASTree asTree, final ASTree asTree2) {
        return new Expr(n, asTree, new ASTList(asTree2));
    }
    
    public static Expr make(final int n, final ASTree asTree) {
        return new Expr(n, asTree);
    }
    
    public int getOperator() {
        return this.operatorId;
    }
    
    public void setOperator(final int operatorId) {
        this.operatorId = operatorId;
    }
    
    public ASTree oprand1() {
        return this.getLeft();
    }
    
    public void setOprand1(final ASTree left) {
        this.setLeft(left);
    }
    
    public ASTree oprand2() {
        return this.getRight().getLeft();
    }
    
    public void setOprand2(final ASTree left) {
        this.getRight().setLeft(left);
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atExpr(this);
    }
    
    public String getName() {
        final int operatorId = this.operatorId;
        if (operatorId < 128) {
            return String.valueOf((char)operatorId);
        }
        if (350 <= operatorId && operatorId <= 371) {
            return Expr.opNames[operatorId - 350];
        }
        if (operatorId == 323) {
            return "instanceof";
        }
        return String.valueOf(operatorId);
    }
    
    @Override
    protected String getTag() {
        return "op:" + this.getName();
    }
}
