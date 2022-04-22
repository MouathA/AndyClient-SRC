package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(serializable = true)
@Beta
public class TreeBasedTable extends StandardRowSortedTable
{
    private final Comparator columnComparator;
    private static final long serialVersionUID = 0L;
    
    public static TreeBasedTable create() {
        return new TreeBasedTable(Ordering.natural(), Ordering.natural());
    }
    
    public static TreeBasedTable create(final Comparator comparator, final Comparator comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        return new TreeBasedTable(comparator, comparator2);
    }
    
    public static TreeBasedTable create(final TreeBasedTable treeBasedTable) {
        final TreeBasedTable treeBasedTable2 = new TreeBasedTable(treeBasedTable.rowComparator(), treeBasedTable.columnComparator());
        treeBasedTable2.putAll(treeBasedTable);
        return treeBasedTable2;
    }
    
    TreeBasedTable(final Comparator comparator, final Comparator columnComparator) {
        super(new TreeMap(comparator), new Factory(columnComparator));
        this.columnComparator = columnComparator;
    }
    
    public Comparator rowComparator() {
        return this.rowKeySet().comparator();
    }
    
    public Comparator columnComparator() {
        return this.columnComparator;
    }
    
    @Override
    public SortedMap row(final Object o) {
        return new TreeRow(o);
    }
    
    @Override
    public SortedSet rowKeySet() {
        return super.rowKeySet();
    }
    
    @Override
    public SortedMap rowMap() {
        return super.rowMap();
    }
    
    @Override
    Iterator createColumnKeyIterator() {
        final Comparator columnComparator = this.columnComparator();
        return new AbstractIterator((Iterator)Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function() {
            final TreeBasedTable this$0;
            
            public Iterator apply(final Map map) {
                return map.keySet().iterator();
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Map)o);
            }
        }), columnComparator), columnComparator) {
            Object lastValue;
            final Iterator val$merged;
            final Comparator val$comparator;
            final TreeBasedTable this$0;
            
            @Override
            protected Object computeNext() {
                while (this.val$merged.hasNext()) {
                    final Object next = this.val$merged.next();
                    if (this.lastValue == null || this.val$comparator.compare(next, this.lastValue) != 0) {
                        return this.lastValue = next;
                    }
                }
                this.lastValue = null;
                return this.endOfData();
            }
        };
    }
    
    @Override
    public Map rowMap() {
        return this.rowMap();
    }
    
    @Override
    public Set rowKeySet() {
        return this.rowKeySet();
    }
    
    @Override
    public Map row(final Object o) {
        return this.row(o);
    }
    
    @Override
    public Map columnMap() {
        return super.columnMap();
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
    public Map column(final Object o) {
        return super.column(o);
    }
    
    @Override
    public Set cellSet() {
        return super.cellSet();
    }
    
    @Override
    public Object remove(final Object o, final Object o2) {
        return super.remove(o, o2);
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
    public Object get(final Object o, final Object o2) {
        return super.get(o, o2);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return super.containsValue(o);
    }
    
    @Override
    public boolean containsRow(final Object o) {
        return super.containsRow(o);
    }
    
    @Override
    public boolean containsColumn(final Object o) {
        return super.containsColumn(o);
    }
    
    @Override
    public boolean contains(final Object o, final Object o2) {
        return super.contains(o, o2);
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
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public void putAll(final Table table) {
        super.putAll(table);
    }
    
    private class TreeRow extends Row implements SortedMap
    {
        @Nullable
        final Object lowerBound;
        @Nullable
        final Object upperBound;
        transient SortedMap wholeRow;
        final TreeBasedTable this$0;
        
        TreeRow(final TreeBasedTable treeBasedTable, final Object o) {
            this(treeBasedTable, o, null, null);
        }
        
        TreeRow(final TreeBasedTable this$0, @Nullable final Object o, @Nullable final Object lowerBound, final Object upperBound) {
            this.this$0 = this$0.super(o);
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            Preconditions.checkArgument(lowerBound == null || upperBound == null || this.compare(lowerBound, upperBound) <= 0);
        }
        
        @Override
        public SortedSet keySet() {
            return new Maps.SortedKeySet(this);
        }
        
        @Override
        public Comparator comparator() {
            return this.this$0.columnComparator();
        }
        
        int compare(final Object o, final Object o2) {
            return this.comparator().compare(o, o2);
        }
        
        boolean rangeContains(@Nullable final Object o) {
            return o != null && (this.lowerBound == null || this.compare(this.lowerBound, o) <= 0) && (this.upperBound == null || this.compare(this.upperBound, o) > 0);
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(o)) && this.rangeContains(Preconditions.checkNotNull(o2)));
            return this.this$0.new TreeRow(this.rowKey, o, o2);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(o)));
            return this.this$0.new TreeRow(this.rowKey, this.lowerBound, o);
        }
        
        @Override
        public SortedMap tailMap(final Object o) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(o)));
            return this.this$0.new TreeRow(this.rowKey, o, this.upperBound);
        }
        
        @Override
        public Object firstKey() {
            if (this.backingRowMap() == null) {
                throw new NoSuchElementException();
            }
            return this.backingRowMap().firstKey();
        }
        
        @Override
        public Object lastKey() {
            if (this.backingRowMap() == null) {
                throw new NoSuchElementException();
            }
            return this.backingRowMap().lastKey();
        }
        
        SortedMap wholeRow() {
            if (this.wholeRow == null || (this.wholeRow.isEmpty() && this.this$0.backingMap.containsKey(this.rowKey))) {
                this.wholeRow = this.this$0.backingMap.get(this.rowKey);
            }
            return this.wholeRow;
        }
        
        @Override
        SortedMap backingRowMap() {
            return (SortedMap)super.backingRowMap();
        }
        
        @Override
        SortedMap computeBackingRowMap() {
            SortedMap<Object, Object> sortedMap = (SortedMap<Object, Object>)this.wholeRow();
            if (sortedMap != null) {
                if (this.lowerBound != null) {
                    sortedMap = sortedMap.tailMap(this.lowerBound);
                }
                if (this.upperBound != null) {
                    sortedMap = sortedMap.headMap(this.upperBound);
                }
                return sortedMap;
            }
            return null;
        }
        
        @Override
        void maintainEmptyInvariant() {
            if (this.wholeRow() != null && this.wholeRow.isEmpty()) {
                this.this$0.backingMap.remove(this.rowKey);
                this.wholeRow = null;
                this.backingRowMap = null;
            }
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.rangeContains(o) && super.containsKey(o);
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(o)));
            return super.put(o, o2);
        }
        
        @Override
        Map computeBackingRowMap() {
            return this.computeBackingRowMap();
        }
        
        @Override
        Map backingRowMap() {
            return this.backingRowMap();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
    }
    
    private static class Factory implements Supplier, Serializable
    {
        final Comparator comparator;
        private static final long serialVersionUID = 0L;
        
        Factory(final Comparator comparator) {
            this.comparator = comparator;
        }
        
        @Override
        public TreeMap get() {
            return new TreeMap(this.comparator);
        }
        
        @Override
        public Object get() {
            return this.get();
        }
    }
}
