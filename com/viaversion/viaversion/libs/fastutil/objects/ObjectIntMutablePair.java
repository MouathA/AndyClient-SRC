package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class ObjectIntMutablePair implements ObjectIntPair, Serializable
{
    private static final long serialVersionUID = 0L;
    protected Object left;
    protected int right;
    
    public ObjectIntMutablePair(final Object left, final int right) {
        this.left = left;
        this.right = right;
    }
    
    public static ObjectIntMutablePair of(final Object o, final int n) {
        return new ObjectIntMutablePair(o, n);
    }
    
    @Override
    public Object left() {
        return this.left;
    }
    
    @Override
    public ObjectIntMutablePair left(final Object left) {
        this.left = left;
        return this;
    }
    
    @Override
    public int rightInt() {
        return this.right;
    }
    
    @Override
    public ObjectIntMutablePair right(final int right) {
        this.right = right;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof ObjectIntPair) {
            return Objects.equals(this.left, ((ObjectIntPair)o).left()) && this.right == ((ObjectIntPair)o).rightInt();
        }
        return o instanceof Pair && Objects.equals(this.left, ((Pair)o).left()) && Objects.equals(this.right, ((Pair)o).right());
    }
    
    @Override
    public int hashCode() {
        return ((this.left == null) ? 0 : this.left.hashCode()) * 19 + this.right;
    }
    
    @Override
    public String toString() {
        return "<" + this.left() + "," + this.rightInt() + ">";
    }
    
    @Override
    public ObjectIntPair right(final int n) {
        return this.right(n);
    }
    
    @Override
    public Pair left(final Object o) {
        return this.left(o);
    }
}
