package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;

@GwtCompatible(serializable = true)
final class NaturalOrdering extends Ordering implements Serializable
{
    static final NaturalOrdering INSTANCE;
    private static final long serialVersionUID = 0L;
    
    public int compare(final Comparable comparable, final Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        return comparable.compareTo(comparable2);
    }
    
    @Override
    public Ordering reverse() {
        return ReverseNaturalOrdering.INSTANCE;
    }
    
    private Object readResolve() {
        return NaturalOrdering.INSTANCE;
    }
    
    @Override
    public String toString() {
        return "Ordering.natural()";
    }
    
    private NaturalOrdering() {
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((Comparable)o, (Comparable)o2);
    }
    
    static {
        INSTANCE = new NaturalOrdering();
    }
}
