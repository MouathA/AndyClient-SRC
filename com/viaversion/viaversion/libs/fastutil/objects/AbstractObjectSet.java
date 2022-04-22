package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public abstract class AbstractObjectSet extends AbstractObjectCollection implements Cloneable, ObjectSet
{
    protected AbstractObjectSet() {
    }
    
    @Override
    public abstract ObjectIterator iterator();
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        final Set set = (Set)o;
        return set.size() == this.size() && this.containsAll(set);
    }
    
    @Override
    public int hashCode() {
        int size = this.size();
        final ObjectIterator iterator = this.iterator();
        while (size-- != 0) {
            final Object next = iterator.next();
            final int n = 0 + ((next == null) ? 0 : next.hashCode());
        }
        return 0;
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
