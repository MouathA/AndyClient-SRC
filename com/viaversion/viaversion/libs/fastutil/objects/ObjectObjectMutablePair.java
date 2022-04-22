package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.*;

public class ObjectObjectMutablePair implements Pair, Serializable
{
    private static final long serialVersionUID = 0L;
    protected Object left;
    protected Object right;
    
    public ObjectObjectMutablePair(final Object left, final Object right) {
        this.left = left;
        this.right = right;
    }
    
    public static ObjectObjectMutablePair of(final Object o, final Object o2) {
        return new ObjectObjectMutablePair(o, o2);
    }
    
    @Override
    public Object left() {
        return this.left;
    }
    
    @Override
    public ObjectObjectMutablePair left(final Object left) {
        this.left = left;
        return this;
    }
    
    @Override
    public Object right() {
        return this.right;
    }
    
    @Override
    public ObjectObjectMutablePair right(final Object right) {
        this.right = right;
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof Pair && Objects.equals(this.left, ((Pair)o).left()) && Objects.equals(this.right, ((Pair)o).right());
    }
    
    @Override
    public int hashCode() {
        return ((this.left == null) ? 0 : this.left.hashCode()) * 19 + ((this.right == null) ? 0 : this.right.hashCode());
    }
    
    @Override
    public String toString() {
        return "<" + this.left() + "," + this.right() + ">";
    }
    
    @Override
    public Pair right(final Object o) {
        return this.right(o);
    }
    
    @Override
    public Pair left(final Object o) {
        return this.left(o);
    }
}
