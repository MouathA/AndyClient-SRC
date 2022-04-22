package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
class StandardRowSortedTable extends StandardTable implements RowSortedTable
{
    private static final long serialVersionUID = 0L;
    
    StandardRowSortedTable(final SortedMap sortedMap, final Supplier supplier) {
        super(sortedMap, supplier);
    }
    
    private SortedMap sortedBackingMap() {
        return (SortedMap)this.backingMap;
    }
    
    @Override
    public SortedSet rowKeySet() {
        return (SortedSet)this.rowMap().keySet();
    }
    
    @Override
    public SortedMap rowMap() {
        return (SortedMap)super.rowMap();
    }
    
    @Override
    SortedMap createRowMap() {
        return new RowSortedMap(null);
    }
    
    @Override
    Map createRowMap() {
        return this.createRowMap();
    }
    
    @Override
    public Map rowMap() {
        return this.rowMap();
    }
    
    @Override
    public Set rowKeySet() {
        return this.rowKeySet();
    }
    
    static SortedMap access$100(final StandardRowSortedTable standardRowSortedTable) {
        return standardRowSortedTable.sortedBackingMap();
    }
    
    private class RowSortedMap extends RowMap implements SortedMap
    {
        final StandardRowSortedTable this$0;
        
        private RowSortedMap(final StandardRowSortedTable this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public SortedSet keySet() {
            return (SortedSet)super.keySet();
        }
        
        @Override
        SortedSet createKeySet() {
            return new Maps.SortedKeySet(this);
        }
        
        @Override
        public Comparator comparator() {
            return StandardRowSortedTable.access$100(this.this$0).comparator();
        }
        
        @Override
        public Object firstKey() {
            return StandardRowSortedTable.access$100(this.this$0).firstKey();
        }
        
        @Override
        public Object lastKey() {
            return StandardRowSortedTable.access$100(this.this$0).lastKey();
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            Preconditions.checkNotNull(o);
            return new StandardRowSortedTable(StandardRowSortedTable.access$100(this.this$0).headMap(o), this.this$0.factory).rowMap();
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(o2);
            return new StandardRowSortedTable(StandardRowSortedTable.access$100(this.this$0).subMap(o, o2), this.this$0.factory).rowMap();
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            Preconditions.checkNotNull(o);
            return new StandardRowSortedTable(StandardRowSortedTable.access$100(this.this$0).tailMap(o), this.this$0.factory).rowMap();
        }
        
        @Override
        Set createKeySet() {
            return this.createKeySet();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
        
        RowSortedMap(final StandardRowSortedTable standardRowSortedTable, final StandardRowSortedTable$1 object) {
            this(standardRowSortedTable);
        }
    }
}
