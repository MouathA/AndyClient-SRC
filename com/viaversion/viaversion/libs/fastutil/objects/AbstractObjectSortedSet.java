package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public abstract class AbstractObjectSortedSet extends AbstractObjectSet implements ObjectSortedSet
{
    protected AbstractObjectSortedSet() {
    }
    
    @Override
    public abstract ObjectBidirectionalIterator iterator();
    
    @Override
    public ObjectIterator iterator() {
        return this.iterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
