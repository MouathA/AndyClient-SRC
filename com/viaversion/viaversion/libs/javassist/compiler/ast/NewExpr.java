package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class NewExpr extends ASTList implements TokenId
{
    private static final long serialVersionUID = 1L;
    protected boolean newArray;
    protected int arrayType;
    
    public NewExpr(final ASTList list, final ASTList list2) {
        super(list, new ASTList(list2));
        this.newArray = false;
        this.arrayType = 307;
    }
    
    public NewExpr(final int arrayType, final ASTList list, final ArrayInit arrayInit) {
        super(null, new ASTList(list));
        this.newArray = true;
        this.arrayType = arrayType;
        if (arrayInit != null) {
            ASTList.append(this, arrayInit);
        }
    }
    
    public static NewExpr makeObjectArray(final ASTList list, final ASTList list2, final ArrayInit arrayInit) {
        final NewExpr newExpr = new NewExpr(list, list2);
        newExpr.newArray = true;
        if (arrayInit != null) {
            ASTList.append(newExpr, arrayInit);
        }
        return newExpr;
    }
    
    public boolean isArray() {
        return this.newArray;
    }
    
    public int getArrayType() {
        return this.arrayType;
    }
    
    public ASTList getClassName() {
        return (ASTList)this.getLeft();
    }
    
    public ASTList getArguments() {
        return (ASTList)this.getRight().getLeft();
    }
    
    public ASTList getArraySize() {
        return this.getArguments();
    }
    
    public ArrayInit getInitializer() {
        final ASTree right = this.getRight().getRight();
        if (right == null) {
            return null;
        }
        return (ArrayInit)right.getLeft();
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atNewExpr(this);
    }
    
    @Override
    protected String getTag() {
        return this.newArray ? "new[]" : "new";
    }
}
