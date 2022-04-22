package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingTable extends ForwardingObject implements Table
{
    protected ForwardingTable() {
    }
    
    @Override
    protected abstract Table delegate();
    
    @Override
    public Set cellSet() {
        return this.delegate().cellSet();
    }
    
    @Override
    public void clear() {
        this.delegate().clear();
    }
    
    @Override
    public Map column(final Object o) {
        return this.delegate().column(o);
    }
    
    @Override
    public Set columnKeySet() {
        return this.delegate().columnKeySet();
    }
    
    @Override
    public Map columnMap() {
        return this.delegate().columnMap();
    }
    
    @Override
    public boolean contains(final Object o, final Object o2) {
        return this.delegate().contains(o, o2);
    }
    
    @Override
    public boolean containsColumn(final Object o) {
        return this.delegate().containsColumn(o);
    }
    
    @Override
    public boolean containsRow(final Object o) {
        return this.delegate().containsRow(o);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this.delegate().containsValue(o);
    }
    
    @Override
    public Object get(final Object o, final Object o2) {
        return this.delegate().get(o, o2);
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    @Override
    public Object put(final Object o, final Object o2, final Object o3) {
        return this.delegate().put(o, o2, o3);
    }
    
    @Override
    public void putAll(final Table table) {
        this.delegate().putAll(table);
    }
    
    @Override
    public Object remove(final Object o, final Object o2) {
        return this.delegate().remove(o, o2);
    }
    
    @Override
    public Map row(final Object o) {
        return this.delegate().row(o);
    }
    
    @Override
    public Set rowKeySet() {
        return this.delegate().rowKeySet();
    }
    
    @Override
    public Map rowMap() {
        return this.delegate().rowMap();
    }
    
    @Override
    public int size() {
        return this.delegate().size();
    }
    
    @Override
    public Collection values() {
        return this.delegate().values();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
