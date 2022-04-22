package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(serializable = true)
public class HashBasedTable extends StandardTable
{
    private static final long serialVersionUID = 0L;
    
    public static HashBasedTable create() {
        return new HashBasedTable(new HashMap(), new Factory(0));
    }
    
    public static HashBasedTable create(final int n, final int n2) {
        CollectPreconditions.checkNonnegative(n2, "expectedCellsPerRow");
        return new HashBasedTable(Maps.newHashMapWithExpectedSize(n), new Factory(n2));
    }
    
    public static HashBasedTable create(final Table table) {
        final HashBasedTable create = create();
        create.putAll(table);
        return create;
    }
    
    HashBasedTable(final Map map, final Factory factory) {
        super(map, factory);
    }
    
    @Override
    public boolean contains(@Nullable final Object o, @Nullable final Object o2) {
        return super.contains(o, o2);
    }
    
    @Override
    public boolean containsColumn(@Nullable final Object o) {
        return super.containsColumn(o);
    }
    
    @Override
    public boolean containsRow(@Nullable final Object o) {
        return super.containsRow(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return super.containsValue(o);
    }
    
    @Override
    public Object get(@Nullable final Object o, @Nullable final Object o2) {
        return super.get(o, o2);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return super.equals(o);
    }
    
    @Override
    public Object remove(@Nullable final Object o, @Nullable final Object o2) {
        return super.remove(o, o2);
    }
    
    @Override
    public Map columnMap() {
        return super.columnMap();
    }
    
    @Override
    public Map rowMap() {
        return super.rowMap();
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    public Set columnKeySet() {
        return super.columnKeySet();
    }
    
    @Override
    public Set rowKeySet() {
        return super.rowKeySet();
    }
    
    @Override
    public Map column(final Object o) {
        return super.column(o);
    }
    
    @Override
    public Map row(final Object o) {
        return super.row(o);
    }
    
    @Override
    public Set cellSet() {
        return super.cellSet();
    }
    
    @Override
    public Object put(final Object o, final Object o2, final Object o3) {
        return super.put(o, o2, o3);
    }
    
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public int size() {
        return super.size();
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public void putAll(final Table table) {
        super.putAll(table);
    }
    
    private static class Factory implements Supplier, Serializable
    {
        final int expectedSize;
        private static final long serialVersionUID = 0L;
        
        Factory(final int expectedSize) {
            this.expectedSize = expectedSize;
        }
        
        @Override
        public Map get() {
            return Maps.newHashMapWithExpectedSize(this.expectedSize);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
}
