package org.apache.commons.lang3.tuple;

import java.util.*;
import java.io.*;
import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.*;

public abstract class Pair implements Map.Entry, Comparable, Serializable
{
    private static final long serialVersionUID = 4954918890077093841L;
    
    public static Pair of(final Object o, final Object o2) {
        return new ImmutablePair(o, o2);
    }
    
    public abstract Object getLeft();
    
    public abstract Object getRight();
    
    @Override
    public final Object getKey() {
        return this.getLeft();
    }
    
    @Override
    public Object getValue() {
        return this.getRight();
    }
    
    public int compareTo(final Pair pair) {
        return new CompareToBuilder().append(this.getLeft(), pair.getLeft()).append(this.getRight(), pair.getRight()).toComparison();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Map.Entry) {
            final Map.Entry entry = (Map.Entry)o;
            return ObjectUtils.equals(this.getKey(), entry.getKey()) && ObjectUtils.equals(this.getValue(), entry.getValue());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return ((this.getKey() == null) ? 0 : this.getKey().hashCode()) ^ ((this.getValue() == null) ? 0 : this.getValue().hashCode());
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append('(').append(this.getLeft()).append(',').append(this.getRight()).append(')').toString();
    }
    
    public String toString(final String s) {
        return String.format(s, this.getLeft(), this.getRight());
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Pair)o);
    }
}
