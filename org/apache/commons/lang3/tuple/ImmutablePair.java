package org.apache.commons.lang3.tuple;

public final class ImmutablePair extends Pair
{
    private static final long serialVersionUID = 4954918890077093841L;
    public final Object left;
    public final Object right;
    
    public static ImmutablePair of(final Object o, final Object o2) {
        return new ImmutablePair(o, o2);
    }
    
    public ImmutablePair(final Object left, final Object right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Object getLeft() {
        return this.left;
    }
    
    @Override
    public Object getRight() {
        return this.right;
    }
    
    @Override
    public Object setValue(final Object o) {
        throw new UnsupportedOperationException();
    }
}
