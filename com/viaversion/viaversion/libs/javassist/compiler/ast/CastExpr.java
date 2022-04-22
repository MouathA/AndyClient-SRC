package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class CastExpr extends ASTList implements TokenId
{
    private static final long serialVersionUID = 1L;
    protected int castType;
    protected int arrayDim;
    
    public CastExpr(final ASTList list, final int arrayDim, final ASTree asTree) {
        super(list, new ASTList(asTree));
        this.castType = 307;
        this.arrayDim = arrayDim;
    }
    
    public CastExpr(final int castType, final int arrayDim, final ASTree asTree) {
        super(null, new ASTList(asTree));
        this.castType = castType;
        this.arrayDim = arrayDim;
    }
    
    public int getType() {
        return this.castType;
    }
    
    public int getArrayDim() {
        return this.arrayDim;
    }
    
    public ASTList getClassName() {
        return (ASTList)this.getLeft();
    }
    
    public ASTree getOprand() {
        return this.getRight().getLeft();
    }
    
    public void setOprand(final ASTree left) {
        this.getRight().setLeft(left);
    }
    
    public String getTag() {
        return "cast:" + this.castType + ":" + this.arrayDim;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atCastExpr(this);
    }
}
