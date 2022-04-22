package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(serializable = true)
final class ReverseNaturalOrdering extends Ordering implements Serializable
{
    static final ReverseNaturalOrdering INSTANCE;
    private static final long serialVersionUID = 0L;
    
    public int compare(final Comparable comparable, final Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        if (comparable == comparable2) {
            return 0;
        }
        return comparable2.compareTo(comparable);
    }
    
    @Override
    public Ordering reverse() {
        return Ordering.natural();
    }
    
    public Comparable min(final Comparable comparable, final Comparable comparable2) {
        return (Comparable)NaturalOrdering.INSTANCE.max(comparable, comparable2);
    }
    
    public Comparable min(final Comparable comparable, final Comparable comparable2, final Comparable comparable3, final Comparable... array) {
        return (Comparable)NaturalOrdering.INSTANCE.max(comparable, comparable2, comparable3, (Object[])array);
    }
    
    @Override
    public Comparable min(final Iterator iterator) {
        return (Comparable)NaturalOrdering.INSTANCE.max(iterator);
    }
    
    @Override
    public Comparable min(final Iterable iterable) {
        return (Comparable)NaturalOrdering.INSTANCE.max(iterable);
    }
    
    public Comparable max(final Comparable comparable, final Comparable comparable2) {
        return (Comparable)NaturalOrdering.INSTANCE.min(comparable, comparable2);
    }
    
    public Comparable max(final Comparable comparable, final Comparable comparable2, final Comparable comparable3, final Comparable... array) {
        return (Comparable)NaturalOrdering.INSTANCE.min(comparable, comparable2, comparable3, (Object[])array);
    }
    
    @Override
    public Comparable max(final Iterator iterator) {
        return (Comparable)NaturalOrdering.INSTANCE.min(iterator);
    }
    
    @Override
    public Comparable max(final Iterable iterable) {
        return (Comparable)NaturalOrdering.INSTANCE.min(iterable);
    }
    
    private Object readResolve() {
        return ReverseNaturalOrdering.INSTANCE;
    }
    
    @Override
    public String toString() {
        return "Ordering.natural().reverse()";
    }
    
    private ReverseNaturalOrdering() {
    }
    
    @Override
    public Object max(final Object o, final Object o2, final Object o3, final Object[] array) {
        return this.max((Comparable)o, (Comparable)o2, (Comparable)o3, (Comparable[])array);
    }
    
    @Override
    public Object max(final Object o, final Object o2) {
        return this.max((Comparable)o, (Comparable)o2);
    }
    
    @Override
    public Object max(final Iterable iterable) {
        return this.max(iterable);
    }
    
    @Override
    public Object max(final Iterator iterator) {
        return this.max(iterator);
    }
    
    @Override
    public Object min(final Object o, final Object o2, final Object o3, final Object[] array) {
        return this.min((Comparable)o, (Comparable)o2, (Comparable)o3, (Comparable[])array);
    }
    
    @Override
    public Object min(final Object o, final Object o2) {
        return this.min((Comparable)o, (Comparable)o2);
    }
    
    @Override
    public Object min(final Iterable iterable) {
        return this.min(iterable);
    }
    
    @Override
    public Object min(final Iterator iterator) {
        return this.min(iterator);
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((Comparable)o, (Comparable)o2);
    }
    
    static {
        INSTANCE = new ReverseNaturalOrdering();
    }
}
