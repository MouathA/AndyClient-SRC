package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
abstract class RegularImmutableTable extends ImmutableTable
{
    abstract Table.Cell getCell(final int p0);
    
    @Override
    final ImmutableSet createCellSet() {
        return this.isEmpty() ? ImmutableSet.of() : new CellSet(null);
    }
    
    abstract Object getValue(final int p0);
    
    @Override
    final ImmutableCollection createValues() {
        return this.isEmpty() ? ImmutableList.of() : new Values(null);
    }
    
    static RegularImmutableTable forCells(final List list, @Nullable final Comparator comparator, @Nullable final Comparator comparator2) {
        Preconditions.checkNotNull(list);
        if (comparator != null || comparator2 != null) {
            Collections.sort((List<Object>)list, new Comparator(comparator, comparator2) {
                final Comparator val$rowComparator;
                final Comparator val$columnComparator;
                
                public int compare(final Table.Cell cell, final Table.Cell cell2) {
                    final int n = (this.val$rowComparator == null) ? 0 : this.val$rowComparator.compare(cell.getRowKey(), cell2.getRowKey());
                    if (n != 0) {
                        return n;
                    }
                    return (this.val$columnComparator == null) ? 0 : this.val$columnComparator.compare(cell.getColumnKey(), cell2.getColumnKey());
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((Table.Cell)o, (Table.Cell)o2);
                }
            });
        }
        return forCellsInternal(list, comparator, comparator2);
    }
    
    static RegularImmutableTable forCells(final Iterable iterable) {
        return forCellsInternal(iterable, null, null);
    }
    
    private static final RegularImmutableTable forCellsInternal(final Iterable iterable, @Nullable final Comparator comparator, @Nullable final Comparator comparator2) {
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        final ImmutableSet.Builder builder2 = ImmutableSet.builder();
        final ImmutableList copy = ImmutableList.copyOf(iterable);
        for (final Table.Cell cell : copy) {
            builder.add(cell.getRowKey());
            builder2.add(cell.getColumnKey());
        }
        ImmutableSet set = builder.build();
        if (comparator != null) {
            final ArrayList arrayList = Lists.newArrayList(set);
            Collections.sort((List<Object>)arrayList, comparator);
            set = ImmutableSet.copyOf(arrayList);
        }
        ImmutableSet set2 = builder2.build();
        if (comparator2 != null) {
            final ArrayList arrayList2 = Lists.newArrayList(set2);
            Collections.sort((List<Object>)arrayList2, comparator2);
            set2 = ImmutableSet.copyOf(arrayList2);
        }
        return (copy.size() > set.size() * (long)set2.size() / 2L) ? new DenseImmutableTable(copy, set, set2) : new SparseImmutableTable(copy, set, set2);
    }
    
    @Override
    Collection createValues() {
        return this.createValues();
    }
    
    @Override
    Set createCellSet() {
        return this.createCellSet();
    }
    
    private final class Values extends ImmutableList
    {
        final RegularImmutableTable this$0;
        
        private Values(final RegularImmutableTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public Object get(final int n) {
            return this.this$0.getValue(n);
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
        
        Values(final RegularImmutableTable regularImmutableTable, final RegularImmutableTable$1 comparator) {
            this(regularImmutableTable);
        }
    }
    
    private final class CellSet extends ImmutableSet
    {
        final RegularImmutableTable this$0;
        
        private CellSet(final RegularImmutableTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return this.asList().iterator();
        }
        
        @Override
        ImmutableList createAsList() {
            return new ImmutableAsList() {
                final CellSet this$1;
                
                @Override
                public Table.Cell get(final int n) {
                    return this.this$1.this$0.getCell(n);
                }
                
                @Override
                ImmutableCollection delegateCollection() {
                    return this.this$1;
                }
                
                @Override
                public Object get(final int n) {
                    return this.get(n);
                }
            };
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            if (o instanceof Table.Cell) {
                final Table.Cell cell = (Table.Cell)o;
                final Object value = this.this$0.get(cell.getRowKey(), cell.getColumnKey());
                return value != null && value.equals(cell.getValue());
            }
            return false;
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        CellSet(final RegularImmutableTable regularImmutableTable, final RegularImmutableTable$1 comparator) {
            this(regularImmutableTable);
        }
    }
}
