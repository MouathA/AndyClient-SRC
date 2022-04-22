package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;

public abstract class AbstractIntSet extends AbstractIntCollection implements Cloneable, IntSet
{
    protected AbstractIntSet() {
    }
    
    @Override
    public abstract IntIterator iterator();
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        final Set set = (Set)o;
        if (set.size() != this.size()) {
            return false;
        }
        if (set instanceof IntSet) {
            return this.containsAll((IntCollection)set);
        }
        return this.containsAll(set);
    }
    
    @Override
    public int hashCode() {
        int size = this.size();
        final IntIterator iterator = this.iterator();
        while (size-- != 0) {
            final int n = 0 + iterator.nextInt();
        }
        return 0;
    }
    
    @Override
    public boolean remove(final int n) {
        return super.rem(n);
    }
    
    @Deprecated
    @Override
    public boolean rem(final int n) {
        return this.remove(n);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
