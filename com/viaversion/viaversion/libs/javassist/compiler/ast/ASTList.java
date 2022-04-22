package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.*;

public class ASTList extends ASTree
{
    private static final long serialVersionUID = 1L;
    private ASTree left;
    private ASTList right;
    
    public ASTList(final ASTree left, final ASTList right) {
        this.left = left;
        this.right = right;
    }
    
    public ASTList(final ASTree left) {
        this.left = left;
        this.right = null;
    }
    
    public static ASTList make(final ASTree asTree, final ASTree asTree2, final ASTree asTree3) {
        return new ASTList(asTree, new ASTList(asTree2, new ASTList(asTree3)));
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
    public void setRight(final ASTree asTree) {
        this.right = (ASTList)asTree;
    }
    
    public ASTree head() {
        return this.left;
    }
    
    public void setHead(final ASTree left) {
        this.left = left;
    }
    
    public ASTList tail() {
        return this.right;
    }
    
    public void setTail(final ASTList right) {
        this.right = right;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atASTList(this);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("(<");
        sb.append(this.getTag());
        sb.append('>');
        for (ASTList right = this; right != null; right = right.right) {
            sb.append(' ');
            final ASTree left = right.left;
            sb.append((left == null) ? "<null>" : left.toString());
        }
        sb.append(')');
        return sb.toString();
    }
    
    public int length() {
        return length(this);
    }
    
    public static int length(ASTList right) {
        if (right == null) {
            return 0;
        }
        while (right != null) {
            right = right.right;
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    public ASTList sublist(int n) {
        ASTList right = this;
        while (n-- > 0) {
            right = right.right;
        }
        return right;
    }
    
    public boolean subst(final ASTree left, final ASTree asTree) {
        for (ASTList right = this; right != null; right = right.right) {
            if (right.left == asTree) {
                right.left = left;
                return true;
            }
        }
        return false;
    }
    
    public static ASTList append(final ASTList list, final ASTree asTree) {
        return concat(list, new ASTList(asTree));
    }
    
    public static ASTList concat(final ASTList list, final ASTList right) {
        if (list == null) {
            return right;
        }
        ASTList right2;
        for (right2 = list; right2.right != null; right2 = right2.right) {}
        right2.right = right;
        return list;
    }
}
