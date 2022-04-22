package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class ObjectIntImmutablePair implements ObjectIntPair, Serializable
{
    private static final long serialVersionUID = 0L;
    protected final Object left;
    protected final int right;
    
    public ObjectIntImmutablePair(final Object left, final int right) {
        this.left = left;
        this.right = right;
    }
    
    public static ObjectIntImmutablePair of(final Object o, final int n) {
        return new ObjectIntImmutablePair(o, n);
    }
    
    @Override
    public Object left() {
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
}
