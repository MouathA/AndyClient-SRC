package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.io.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
public final class Tables
{
    private static final Function UNMODIFIABLE_WRAPPER;
    
    private Tables() {
    }
    
    public static Table.Cell immutableCell(@Nullable final Object o, @Nullable final Object o2, @Nullable final Object o3) {
        return new ImmutableCell(o, o2, o3);
    }
    
    public static Table transpose(final Table table) {
        return (table instanceof TransposeTable) ? ((TransposeTable)table).original : new TransposeTable(table);
    }
    
    @Beta
    public static Table newCustomTable(final Map map, final Supplier supplier) {
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkNotNull(supplier);
        return new StandardTable(map, supplier);
    }
    
    @Beta
    public static Table transformValues(final Table table, final Function function) {
        return new TransformedTable(table, function);
    }
    
    public static Table unmodifiableTable(final Table table) {
        return new UnmodifiableTable(table);
    }
    
    @Beta
    public static RowSortedTable unmodifiableRowSortedTable(final RowSortedTable rowSortedTable) {
        return new UnmodifiableRowSortedMap(rowSortedTable);
    }
    
    private static Function unmodifiableWrapper() {
        return Tables.UNMODIFIABLE_WRAPPER;
    }
    
    static boolean equalsImpl(final Table table, @Nullable final Object o) {
        return o == table || (o instanceof Table && table.cellSet().equals(((Table)o).cellSet()));
    }
    
    static Function access$000() {
        return unmodifiableWrapper();
    }
    
    static {
        UNMODIFIABLE_WRAPPER = new Function() {
            public Map apply(final Map map) {
                return Collections.unmodifiableMap((Map<?, ?>)map);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Map)o);
            }
        };
    }
    
    static final class UnmodifiableRowSortedMap extends UnmodifiableTable implements RowSortedTable
    {
        private static final long serialVersionUID = 0L;
        
        public UnmodifiableRowSortedMap(final RowSortedTable rowSortedTable) {
            super(rowSortedTable);
        }
        
        @Override
        protected RowSortedTable delegate() {
            return (RowSortedTable)super.delegate();
        }
        
        @Override
        public SortedMap rowMap() {
            return Collections.unmodifiableSortedMap((SortedMap<Object, ?>)Maps.transformValues(this.delegate().rowMap(), Tables.access$000()));
        }
        
        @Override
        public SortedSet rowKeySet() {
            return Collections.unmodifiableSortedSet((SortedSet<Object>)this.delegate().rowKeySet());
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
        protected Table delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static class UnmodifiableTable extends ForwardingTable implements Serializable
    {
        final Table delegate;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableTable(final Table table) {
            this.delegate = (Table)Preconditions.checkNotNull(table);
        }
        
        @Override
        protected Table delegate() {
            return this.delegate;
        }
        
        @Override
        public Set cellSet() {
            return Collections.unmodifiableSet((Set<?>)super.cellSet());
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Map column(@Nullable final Object o) {
            return Collections.unmodifiableMap((Map<?, ?>)super.column(o));
        }
        
        @Override
        public Set columnKeySet() {
            return Collections.unmodifiableSet((Set<?>)super.columnKeySet());
        }
        
        @Override
        public Map columnMap() {
            return Collections.unmodifiableMap((Map<?, ?>)Maps.transformValues(super.columnMap(), Tables.access$000()));
        }
        
        @Override
        public Object put(@Nullable final Object o, @Nullable final Object o2, @Nullable final Object o3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putAll(final Table table) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object remove(@Nullable final Object o, @Nullable final Object o2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Map row(@Nullable final Object o) {
            return Collections.unmodifiableMap((Map<?, ?>)super.row(o));
        }
        
        @Override
        public Set rowKeySet() {
            return Collections.unmodifiableSet((Set<?>)super.rowKeySet());
        }
        
        @Override
        public Map rowMap() {
            return Collections.unmodifiableMap((Map<?, ?>)Maps.transformValues(super.rowMap(), Tables.access$000()));
        }
        
        @Override
        public Collection values() {
            return Collections.unmodifiableCollection((Collection<?>)super.values());
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    private static class TransformedTable extends AbstractTable
    {
        final Table fromTable;
        final Function function;
        
        TransformedTable(final Table table, final Function function) {
            this.fromTable = (Table)Preconditions.checkNotNull(table);
            this.function = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public boolean contains(final Object o, final Object o2) {
            return this.fromTable.contains(o, o2);
        }
        
        @Override
        public Object get(final Object o, final Object o2) {
            return this.contains(o, o2) ? this.function.apply(this.fromTable.get(o, o2)) : null;
        }
        
        @Override
        public int size() {
            return this.fromTable.size();
        }
        
        @Override
        public void clear() {
            this.fromTable.clear();
        }
        
        @Override
        public Object put(final Object o, final Object o2, final Object o3) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putAll(final Table table) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object remove(final Object o, final Object o2) {
            return this.contains(o, o2) ? this.function.apply(this.fromTable.remove(o, o2)) : null;
        }
        
        @Override
        public Map row(final Object o) {
            return Maps.transformValues(this.fromTable.row(o), this.function);
        }
        
        @Override
        public Map column(final Object o) {
            return Maps.transformValues(this.fromTable.column(o), this.function);
        }
        
        Function cellFunction() {
            return new Function() {
                final TransformedTable this$0;
                
                public Table.Cell apply(final Table.Cell cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), this.this$0.function.apply(cell.getValue()));
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Table.Cell)o);
                }
            };
        }
        
        @Override
        Iterator cellIterator() {
            return Iterators.transform(this.fromTable.cellSet().iterator(), this.cellFunction());
        }
        
        @Override
        public Set rowKeySet() {
            return this.fromTable.rowKeySet();
        }
        
        @Override
        public Set columnKeySet() {
            return this.fromTable.columnKeySet();
        }
        
        @Override
        Collection createValues() {
            return Collections2.transform(this.fromTable.values(), this.function);
        }
        
        @Override
        public Map rowMap() {
            return Maps.transformValues(this.fromTable.rowMap(), new Function() {
                final TransformedTable this$0;
                
                public Map apply(final Map map) {
                    return Maps.transformValues(map, this.this$0.function);
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Map)o);
                }
            });
        }
        
        @Override
        public Map columnMap() {
            return Maps.transformValues(this.fromTable.columnMap(), new Function() {
                final TransformedTable this$0;
                
                public Map apply(final Map map) {
                    return Maps.transformValues(map, this.this$0.function);
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Map)o);
                }
            });
        }
    }
    
    private static class TransposeTable extends AbstractTable
    {
        final Table original;
        private static final Function TRANSPOSE_CELL;
        
        TransposeTable(final Table table) {
            this.original = (Table)Preconditions.checkNotNull(table);
        }
        
        @Override
        public void clear() {
            this.original.clear();
        }
        
        @Override
        public Map column(final Object o) {
            return this.original.row(o);
        }
        
        @Override
        public Set columnKeySet() {
            return this.original.rowKeySet();
        }
        
        @Override
        public Map columnMap() {
            return this.original.rowMap();
        }
        
        @Override
        public boolean contains(@Nullable final Object o, @Nullable final Object o2) {
            return this.original.contains(o2, o);
        }
        
        @Override
        public boolean containsColumn(@Nullable final Object o) {
            return this.original.containsRow(o);
        }
        
        @Override
        public boolean containsRow(@Nullable final Object o) {
            return this.original.containsColumn(o);
        }
        
        @Override
        public boolean containsValue(@Nullable final Object o) {
            return this.original.containsValue(o);
        }
        
        @Override
        public Object get(@Nullable final Object o, @Nullable final Object o2) {
            return this.original.get(o2, o);
        }
        
        @Override
        public Object put(final Object o, final Object o2, final Object o3) {
            return this.original.put(o2, o, o3);
        }
        
        @Override
        public void putAll(final Table table) {
            this.original.putAll(Tables.transpose(table));
        }
        
        @Override
        public Object remove(@Nullable final Object o, @Nullable final Object o2) {
            return this.original.remove(o2, o);
        }
        
        @Override
        public Map row(final Object o) {
            return this.original.column(o);
        }
        
        @Override
        public Set rowKeySet() {
            return this.original.columnKeySet();
        }
        
        @Override
        public Map rowMap() {
            return this.original.columnMap();
        }
        
        @Override
        public int size() {
            return this.original.size();
        }
        
        @Override
        public Collection values() {
            return this.original.values();
        }
        
        @Override
        Iterator cellIterator() {
            return Iterators.transform(this.original.cellSet().iterator(), TransposeTable.TRANSPOSE_CELL);
        }
        
        static {
            TRANSPOSE_CELL = new Function() {
                public Table.Cell apply(final Table.Cell cell) {
                    return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
                }
                
                @Override
                public Object apply(final Object o) {
                    return this.apply((Table.Cell)o);
                }
            };
        }
    }
    
    abstract static class AbstractCell implements Table.Cell
    {
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Table.Cell) {
                final Table.Cell cell = (Table.Cell)o;
                return Objects.equal(this.getRowKey(), cell.getRowKey()) && Objects.equal(this.getColumnKey(), cell.getColumnKey()) && Objects.equal(this.getValue(), cell.getValue());
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.getRowKey(), this.getColumnKey(), this.getValue());
        }
        
        @Override
        public String toString() {
            return "(" + this.getRowKey() + "," + this.getColumnKey() + ")=" + this.getValue();
        }
    }
    
    static final class ImmutableCell extends AbstractCell implements Serializable
    {
        private final Object rowKey;
        private final Object columnKey;
        private final Object value;
        private static final long serialVersionUID = 0L;
        
        ImmutableCell(@Nullable final Object rowKey, @Nullable final Object columnKey, @Nullable final Object value) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
            this.value = value;
        }
        
        @Override
        public Object getRowKey() {
            return this.rowKey;
        }
        
        @Override
        public Object getColumnKey() {
            return this.columnKey;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
    }
}
