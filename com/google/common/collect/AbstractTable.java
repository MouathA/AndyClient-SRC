package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
abstract class AbstractTable implements Table
{
    private transient Set cellSet;
    private transient Collection values;
    
    @Override
    public boolean containsRow(@Nullable final Object o) {
        return Maps.safeContainsKey(this.rowMap(), o);
    }
    
    @Override
    public boolean containsColumn(@Nullable final Object o) {
        return Maps.safeContainsKey(this.columnMap(), o);
    }
    
    @Override
    public Set rowKeySet() {
        return this.rowMap().keySet();
    }
    
    @Override
    public Set columnKeySet() {
        return this.columnMap().keySet();
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        final Iterator<Map> iterator = this.rowMap().values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().containsValue(o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean contains(@Nullable final Object o, @Nullable final Object o2) {
        final Map map = (Map)Maps.safeGet(this.rowMap(), o);
        return map != null && Maps.safeContainsKey(map, o2);
    }
    
    @Override
    public Object get(@Nullable final Object o, @Nullable final Object o2) {
        final Map map = (Map)Maps.safeGet(this.rowMap(), o);
        return (map == null) ? null : Maps.safeGet(map, o2);
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public void clear() {
        Iterators.clear(this.cellSet().iterator());
    }
    
    @Override
    public Object remove(@Nullable final Object o, @Nullable final Object o2) {
        final Map map = (Map)Maps.safeGet(this.rowMap(), o);
        return (map == null) ? null : Maps.safeRemove(map, o2);
    }
    
    @Override
    public Object put(final Object o, final Object o2, final Object o3) {
        return this.row(o).put(o2, o3);
    }
    
    @Override
    public void putAll(final Table table) {
        for (final Cell cell : table.cellSet()) {
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }
    
    @Override
    public Set cellSet() {
        final Set cellSet = this.cellSet;
        return (cellSet == null) ? (this.cellSet = this.createCellSet()) : cellSet;
    }
    
    Set createCellSet() {
        return new CellSet();
    }
    
    abstract Iterator cellIterator();
    
    @Override
    public Collection values() {
        final Collection values = this.values;
        return (values == null) ? (this.values = this.createValues()) : values;
    }
    
    Collection createValues() {
        return new Values();
    }
    
    Iterator valuesIterator() {
        return new TransformedIterator((Iterator)this.cellSet().iterator()) {
            final AbstractTable this$0;
            
            Object transform(final Cell cell) {
                return cell.getValue();
            }
            
            @Override
            Object transform(final Object o) {
                return this.transform((Cell)o);
            }
        };
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return Tables.equalsImpl(this, o);
    }
    
    @Override
    public int hashCode() {
        return this.cellSet().hashCode();
    }
    
    @Override
    public String toString() {
        return this.rowMap().toString();
    }
    
    class Values extends AbstractCollection
    {
        final AbstractTable this$0;
        
        Values(final AbstractTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.valuesIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsValue(o);
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
    }
    
    class CellSet extends AbstractSet
    {
        final AbstractTable this$0;
        
        CellSet(final AbstractTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final Object o) {
            if (o instanceof Cell) {
                final Cell cell = (Cell)o;
                final Map map = (Map)Maps.safeGet(this.this$0.rowMap(), cell.getRowKey());
                return map != null && Collections2.safeContains(map.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
            }
            return false;
        }
        
        @Override
        public boolean remove(@Nullable final Object o) {
            if (o instanceof Cell) {
                final Cell cell = (Cell)o;
                final Map map = (Map)Maps.safeGet(this.this$0.rowMap(), cell.getRowKey());
                return map != null && Collections2.safeRemove(map.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
            }
            return false;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.cellIterator();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
    }
}
