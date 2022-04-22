package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class Pair extends ASTree
{
    private static final long serialVersionUID = 1L;
    protected ASTree left;
    protected ASTree right;
    
    public Pair(final ASTree left, final ASTree right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atPair(this);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(<Pair> ");
        sb.append((this.left == null) ? "<null>" : this.left.toString());
        sb.append(" . ");
        sb.append((this.right == null) ? "<null>" : this.right.toString());
        sb.append(')');
        return sb.toString();
    }
    
    @Override
    public ASTree getLeft() {
        return this.left;
    }
    
    @Override
    public ASTree getRight() {
        return this.right;
    }
    
    @Override
    public void setLeft(final ASTree left) {
        this.left = left;
    }
    
    @Override
    public void setRight(final ASTree right) {
        this.right = right;
    }
}
