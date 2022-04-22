package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class ImmutableTable extends AbstractTable
{
    private static final ImmutableTable EMPTY;
    
    public static ImmutableTable of() {
        return ImmutableTable.EMPTY;
    }
    
    public static ImmutableTable of(final Object o, final Object o2, final Object o3) {
        return new SingletonImmutableTable(o, o2, o3);
    }
    
    public static ImmutableTable copyOf(final Table table) {
        if (table instanceof ImmutableTable) {
            return (ImmutableTable)table;
        }
        switch (table.size()) {
            case 0: {
                return of();
            }
            case 1: {
                final Table.Cell cell = (Table.Cell)Iterables.getOnlyElement(table.cellSet());
                return of(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            default: {
                final ImmutableSet.Builder builder = ImmutableSet.builder();
                for (final Table.Cell cell2 : table.cellSet()) {
                    builder.add((Object)cellOf(cell2.getRowKey(), cell2.getColumnKey(), cell2.getValue()));
                }
                return RegularImmutableTable.forCells(builder.build());
            }
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static Table.Cell cellOf(final Object o, final Object o2, final Object o3) {
        return Tables.immutableCell(Preconditions.checkNotNull(o), Preconditions.checkNotNull(o2), Preconditions.checkNotNull(o3));
    }
    
    ImmutableTable() {
    }
    
    @Override
    public ImmutableSet cellSet() {
        return (ImmutableSet)super.cellSet();
    }
    
    @Override
    abstract ImmutableSet createCellSet();
    
    @Override
    final UnmodifiableIterator cellIterator() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public ImmutableCollection values() {
        return (ImmutableCollection)super.values();
    }
    
    @Override
    abstract ImmutableCollection createValues();
    
    @Override
    final Iterator valuesIterator() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public ImmutableMap column(final Object o) {
        Preconditions.checkNotNull(o);
        return (ImmutableMap)Objects.firstNonNull(this.columnMap().get(o), ImmutableMap.of());
    }
    
    @Override
    public ImmutableSet columnKeySet() {
        return this.columnMap().keySet();
    }
    
    @Override
    public abstract ImmutableMap columnMap();
    
    @Override
    public ImmutableMap row(final Object o) {
        Preconditions.checkNotNull(o);
        return (ImmutableMap)Objects.firstNonNull(this.rowMap().get(o), ImmutableMap.of());
    }
    
    @Override
    public ImmutableSet rowKeySet() {
        return this.rowMap().keySet();
    }
    
    @Override
    public abstract ImmutableMap rowMap();
    
    @Override
    public boolean contains(@Nullable final Object o, @Nullable final Object o2) {
        return this.get(o, o2) != null;
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.values().contains(o);
    }
    
    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Object put(final Object o, final Object o2, final Object o3) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void putAll(final Table table) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Object remove(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
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
    Collection createValues() {
        return this.createValues();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    Iterator cellIterator() {
        return this.cellIterator();
    }
    
    @Override
    Set createCellSet() {
        return this.createCellSet();
    }
    
    @Override
    public Set cellSet() {
        return this.cellSet();
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
    public Set columnKeySet() {
        return this.columnKeySet();
    }
    
    @Override
    public Set rowKeySet() {
        return this.rowKeySet();
    }
    
    @Override
    public boolean containsColumn(final Object o) {
        return super.containsColumn(o);
    }
    
    @Override
    public boolean containsRow(final Object o) {
        return super.containsRow(o);
    }
    
    @Override
    public Map columnMap() {
        return this.columnMap();
    }
    
    @Override
    public Map rowMap() {
        return this.rowMap();
    }
    
    @Override
    public Map column(final Object o) {
        return this.column(o);
    }
    
    @Override
    public Map row(final Object o) {
        return this.row(o);
    }
    
    static {
        EMPTY = new SparseImmutableTable(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
    }
    
    public static final class Builder
    {
        private final List cells;
        private Comparator rowComparator;
        private Comparator columnComparator;
        
        public Builder() {
            this.cells = Lists.newArrayList();
        }
        
        public Builder orderRowsBy(final Comparator comparator) {
            this.rowComparator = (Comparator)Preconditions.checkNotNull(comparator);
            return this;
        }
        
        public Builder orderColumnsBy(final Comparator comparator) {
            this.columnComparator = (Comparator)Preconditions.checkNotNull(comparator);
            return this;
        }
        
        public Builder put(final Object o, final Object o2, final Object o3) {
            this.cells.add(ImmutableTable.cellOf(o, o2, o3));
            return this;
        }
        
        public Builder put(final Table.Cell cell) {
            if (cell instanceof Tables.ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey());
                Preconditions.checkNotNull(cell.getColumnKey());
                Preconditions.checkNotNull(cell.getValue());
                this.cells.add(cell);
            }
            else {
                this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }
        
        public Builder putAll(final Table table) {
            final Iterator<Table.Cell> iterator = table.cellSet().iterator();
            while (iterator.hasNext()) {
                this.put(iterator.next());
            }
            return this;
        }
        
        public ImmutableTable build() {
            switch (this.cells.size()) {
                case 0: {
                    return ImmutableTable.of();
                }
                case 1: {
                    return new SingletonImmutableTable((Table.Cell)Iterables.getOnlyElement(this.cells));
                }
                default: {
                    return RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator);
                }
            }
        }
    }
}
