package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;

public abstract class AbstractIntSortedSet extends AbstractIntSet implements IntSortedSet
{
    protected AbstractIntSortedSet() {
    }
    
    @Override
    public abstract IntBidirectionalIterator iterator();
    
    @Override
    public IntIterator iterator() {
        return this.iterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
