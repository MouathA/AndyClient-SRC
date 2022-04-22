package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;

@GwtCompatible(serializable = true)
final class UsingToStringOrdering extends Ordering implements Serializable
{
    static final UsingToStringOrdering INSTANCE;
    private static final long serialVersionUID = 0L;
    
    @Override
    public int compare(final Object o, final Object o2) {
        return o.toString().compareTo(o2.toString());
    }
    
    private Object readResolve() {
        return UsingToStringOrdering.INSTANCE;
    }
    
    @Override
    public String toString() {
        return "Ordering.usingToString()";
    }
    
    private UsingToStringOrdering() {
    }
    
    static {
        INSTANCE = new UsingToStringOrdering();
    }
}
