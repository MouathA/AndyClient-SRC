package org.apache.commons.lang3.tuple;

public final class ImmutableTriple extends Triple
{
    private static final long serialVersionUID = 1L;
    public final Object left;
    public final Object middle;
    public final Object right;
    
    public static ImmutableTriple of(final Object o, final Object o2, final Object o3) {
        return new ImmutableTriple(o, o2, o3);
    }
    
    public ImmutableTriple(final Object left, final Object middle, final Object right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    @Override
    public Object getLeft() {
        return this.left;
    }
    
    @Override
    public Object getMiddle() {
        return this.middle;
    }
    
    @Override
    public Object getRight() {
        return this.right;
    }
}
