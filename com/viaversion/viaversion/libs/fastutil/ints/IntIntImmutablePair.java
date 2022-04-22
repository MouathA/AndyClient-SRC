package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public class IntIntImmutablePair implements IntIntPair, Serializable
{
    private static final long serialVersionUID = 0L;
    protected final int left;
    protected final int right;
    
    public IntIntImmutablePair(final int left, final int right) {
        this.left = left;
        this.right = right;
    }
    
    public static IntIntImmutablePair of(final int n, final int n2) {
        return new IntIntImmutablePair(n, n2);
    }
    
    @Override
    public int leftInt() {
        return this.left;
    }
    
    @Override
    public int rightInt() {
        return this.right;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof IntIntPair) {
            return this.left == ((IntIntPair)o).leftInt() && this.right == ((IntIntPair)o).rightInt();
        }
        return o instanceof Pair && Objects.equals(this.left, ((Pair)o).left()) && Objects.equals(this.right, ((Pair)o).right());
    }
    
    @Override
    public int hashCode() {
        return this.left * 19 + this.right;
    }
    
    @Override
    public String toString() {
        return "<" + this.leftInt() + "," + this.rightInt() + ">";
    }
}
