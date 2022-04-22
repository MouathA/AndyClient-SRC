package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Stmnt extends ASTList implements TokenId
{
    private static final long serialVersionUID = 1L;
    protected int operatorId;
    
    public Stmnt(final int operatorId, final ASTree asTree, final ASTList list) {
        super(asTree, list);
        this.operatorId = operatorId;
    }
    
    public Stmnt(final int operatorId, final ASTree asTree) {
        super(asTree);
        this.operatorId = operatorId;
    }
    
    public Stmnt(final int n) {
        this(n, null);
    }
    
    public static Stmnt make(final int n, final ASTree asTree, final ASTree asTree2) {
        return new Stmnt(n, asTree, new ASTList(asTree2));
    }
    
    public static Stmnt make(final int n, final ASTree asTree, final ASTree asTree2, final ASTree asTree3) {
        return new Stmnt(n, asTree, new ASTList(asTree2, new ASTList(asTree3)));
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atStmnt(this);
    }
    
    public int getOperator() {
        return this.operatorId;
    }
    
    @Override
    protected String getTag() {
        if (this.operatorId < 128) {
            return "stmnt:" + (char)this.operatorId;
        }
        return "stmnt:" + this.operatorId;
    }
}
