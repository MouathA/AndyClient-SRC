package org.apache.commons.lang3.tuple;

public class MutableTriple extends Triple
{
    private static final long serialVersionUID = 1L;
    public Object left;
    public Object middle;
    public Object right;
    
    public static MutableTriple of(final Object o, final Object o2, final Object o3) {
        return new MutableTriple(o, o2, o3);
    }
    
    public MutableTriple() {
    }
    
    public MutableTriple(final Object left, final Object middle, final Object right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    @Override
    public Object getLeft() {
        return this.left;
    }
    
    public void setLeft(final Object left) {
        this.left = left;
    }
    
    @Override
    public Object getMiddle() {
        return this.middle;
    }
    
    public void setMiddle(final Object middle) {
        this.middle = middle;
    }
    
    @Override
    public Object getRight() {
        return this.right;
    }
    
    public void setRight(final Object right) {
        this.right = right;
    }
}
