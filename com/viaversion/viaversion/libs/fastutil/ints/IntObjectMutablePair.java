package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class IntObjectMutablePair implements IntObjectPair, Serializable
{
    private static final long serialVersionUID = 0L;
    protected int left;
    protected Object right;
    
    public IntObjectMutablePair(final int left, final Object right) {
        this.left = left;
        this.right = right;
    }
    
    public static IntObjectMutablePair of(final int n, final Object o) {
        return new IntObjectMutablePair(n, o);
    }
    
    @Override
    public int leftInt() {
        return this.left;
    }
    
    @Override
    public IntObjectMutablePair left(final int left) {
        this.left = left;
        return this;
    }
    
    @Override
    public Object right() {
        return this.right;
    }
    
    @Override
    public IntObjectMutablePair right(final Object right) {
        this.right = right;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof IntObjectPair) {
            return this.left == ((IntObjectPair)o).leftInt() && Objects.equals(this.right, ((IntObjectPair)o).right());
        }
        return o instanceof Pair && Objects.equals(this.left, ((Pair)o).left()) && Objects.equals(this.right, ((Pair)o).right());
    }
    
    @Override
    public int hashCode() {
        return this.left * 19 + ((this.right == null) ? 0 : this.right.hashCode());
    }
    
    @Override
    public String toString() {
        return "<" + this.leftInt() + "," + this.right() + ">";
    }
    
    @Override
    public IntObjectPair left(final int n) {
        return this.left(n);
    }
    
    @Override
    public Pair right(final Object o) {
        return this.right(o);
    }
}
