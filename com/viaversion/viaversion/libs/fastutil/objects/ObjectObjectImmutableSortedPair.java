package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.*;

public class ObjectObjectImmutableSortedPair extends ObjectObjectImmutablePair implements SortedPair, Serializable
{
    private static final long serialVersionUID = 0L;
    
    private ObjectObjectImmutableSortedPair(final Comparable comparable, final Comparable comparable2) {
        super(comparable, comparable2);
    }
    
    public static ObjectObjectImmutableSortedPair of(final Comparable comparable, final Comparable comparable2) {
        if (comparable.compareTo(comparable2) <= 0) {
            return new ObjectObjectImmutableSortedPair(comparable, comparable2);
        }
        return new ObjectObjectImmutableSortedPair(comparable2, comparable);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof SortedPair && Objects.equals(this.left, ((SortedPair)o).left()) && Objects.equals(this.right, ((SortedPair)o).right());
    }
    
    @Override
    public String toString() {
        return "{" + this.left() + "," + this.right() + "}";
    }
}
