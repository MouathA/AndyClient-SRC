package com.viaversion.viaversion.util;

import java.util.*;

public class Pair
{
    private final Object key;
    private Object value;
    
    public Pair(final Object key, final Object value) {
        this.key = key;
        this.value = value;
    }
    
    public Object key() {
        return this.key;
    }
    
    public Object value() {
        return this.value;
    }
    
    @Deprecated
    public Object getKey() {
        return this.key;
    }
    
    @Deprecated
    public Object getValue() {
        return this.value;
    }
    
    @Deprecated
    public void setValue(final Object value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Pair{" + this.key + ", " + this.value + '}';
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Pair pair = (Pair)o;
        return Objects.equals(this.key, pair.key) && Objects.equals(this.value, pair.value);
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.key != null) ? this.key.hashCode() : 0) + ((this.value != null) ? this.value.hashCode() : 0);
    }
}
