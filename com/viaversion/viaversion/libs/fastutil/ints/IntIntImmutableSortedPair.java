package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public class IntIntImmutableSortedPair extends IntIntImmutablePair implements IntIntSortedPair, Serializable
{
    private static final long serialVersionUID = 0L;
    
    private IntIntImmutableSortedPair(final int n, final int n2) {
        super(n, n2);
    }
    
    public static IntIntImmutableSortedPair of(final int n, final int n2) {
        if (n <= n2) {
            return new IntIntImmutableSortedPair(n, n2);
        }
        return new IntIntImmutableSortedPair(n2, n);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof IntIntSortedPair) {
            return this.left == ((IntIntSortedPair)o).leftInt() && this.right == ((IntIntSortedPair)o).rightInt();
        }
        return o instanceof SortedPair && Objects.equals(this.left, ((SortedPair)o).left()) && Objects.equals(this.right, ((SortedPair)o).right());
    }
    
    @Override
    public String toString() {
        return "{" + this.leftInt() + "," + this.rightInt() + "}";
    }
}
