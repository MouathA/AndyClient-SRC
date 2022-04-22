package org.apache.commons.lang3.tuple;

public class MutablePair extends Pair
{
    private static final long serialVersionUID = 4954918890077093841L;
    public Object left;
    public Object right;
    
    public static MutablePair of(final Object o, final Object o2) {
        return new MutablePair(o, o2);
    }
    
    public MutablePair() {
    }
    
    public MutablePair(final Object left, final Object right) {
        this.left = left;
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
    public Object getRight() {
        return this.right;
    }
    
    public void setRight(final Object right) {
        this.right = right;
    }
    
    @Override
    public Object setValue(final Object right) {
        final Object right2 = this.getRight();
        this.setRight(right);
        return right2;
    }
}
