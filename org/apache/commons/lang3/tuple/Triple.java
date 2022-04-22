package org.apache.commons.lang3.tuple;

import java.io.*;
import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.*;

public abstract class Triple implements Comparable, Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static Triple of(final Object o, final Object o2, final Object o3) {
        return new ImmutableTriple(o, o2, o3);
    }
    
    public abstract Object getLeft();
    
    public abstract Object getMiddle();
    
    public abstract Object getRight();
    
    public int compareTo(final Triple triple) {
        return new CompareToBuilder().append(this.getLeft(), triple.getLeft()).append(this.getMiddle(), triple.getMiddle()).append(this.getRight(), triple.getRight()).toComparison();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Triple) {
            final Triple triple = (Triple)o;
            return ObjectUtils.equals(this.getLeft(), triple.getLeft()) && ObjectUtils.equals(this.getMiddle(), triple.getMiddle()) && ObjectUtils.equals(this.getRight(), triple.getRight());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return ((this.getLeft() == null) ? 0 : this.getLeft().hashCode()) ^ ((this.getMiddle() == null) ? 0 : this.getMiddle().hashCode()) ^ ((this.getRight() == null) ? 0 : this.getRight().hashCode());
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append('(').append(this.getLeft()).append(',').append(this.getMiddle()).append(',').append(this.getRight()).append(')').toString();
    }
    
    public String toString(final String s) {
        return String.format(s, this.getLeft(), this.getMiddle(), this.getRight());
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Triple)o);
    }
}
