package com.google.common.collect;

import java.io.*;
import javax.annotation.*;
import java.lang.reflect.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public final class ArrayTable extends AbstractTable implements Serializable
{
    private final ImmutableList rowList;
    private final ImmutableList columnList;
    private final ImmutableMap rowKeyToIndex;
    private final ImmutableMap columnKeyToIndex;
    private final Object[][] array;
    private transient ColumnMap columnMap;
    private transient RowMap rowMap;
    private static final long serialVersionUID = 0L;
    
    public static ArrayTable create(final Iterable iterable, final Iterable iterable2) {
        return new ArrayTable(iterable, iterable2);
    }
    
    public static ArrayTable create(final Table table) {
        return (table instanceof ArrayTable) ? new ArrayTable((ArrayTable)table) : new ArrayTable(table);
    }
    
    private ArrayTable(final Iterable iterable, final Iterable iterable2) {
        this.rowList = ImmutableList.copyOf(iterable);
        this.columnList = ImmutableList.copyOf(iterable2);
        Preconditions.checkArgument(!this.rowList.isEmpty());
        Preconditions.checkArgument(!this.columnList.isEmpty());
        this.rowKeyToIndex = index(this.rowList);
        this.columnKeyToIndex = index(this.columnList);
        this.array = new Object[this.rowList.size()][this.columnList.size()];
        this.eraseAll();
    }
    
    private static ImmutableMap index(final List list) {
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        while (0 < list.size()) {
            builder.put(list.get(0), 0);
            int n = 0;
            ++n;
        }
        return builder.build();
    }
    
    private ArrayTable(final Table table) {
        this(table.rowKeySet(), table.columnKeySet());
        this.putAll(table);
    }
    
    private ArrayTable(final ArrayTable arrayTable) {
        this.rowList = arrayTable.rowList;
        this.columnList = arrayTable.columnList;
        this.rowKeyToIndex = arrayTable.rowKeyToIndex;
        this.columnKeyToIndex = arrayTable.columnKeyToIndex;
        final Object[][] array = new Object[this.rowList.size()][this.columnList.size()];
        this.array = array;
        this.eraseAll();
        while (0 < this.rowList.size()) {
            System.arraycopy(arrayTable.array[0], 0, array[0], 0, arrayTable.array[0].length);
            int n = 0;
            ++n;
        }
    }
    
    public ImmutableList rowKeyList() {
        return this.rowList;
    }
    
    public ImmutableList columnKeyList() {
        return this.columnList;
    }
    
    public Object at(final int n, final int n2) {
        Preconditions.checkElementIndex(n, this.rowList.size());
        Preconditions.checkElementIndex(n2, this.columnList.size());
        return this.array[n][n2];
    }
    
    public Object set(final int n, final int n2, @Nullable final Object o) {
        Preconditions.checkElementIndex(n, this.rowList.size());
        Preconditions.checkElementIndex(n2, this.columnList.size());
        final Object o2 = this.array[n][n2];
        this.array[n][n2] = o;
        return o2;
    }
    
    @GwtIncompatible("reflection")
    public Object[][] toArray(final Class clazz) {
        final Object[][] array = (Object[][])Array.newInstance(clazz, this.rowList.size(), this.columnList.size());
        while (0 < this.rowList.size()) {
            System.arraycopy(this.array[0], 0, array[0], 0, this.array[0].length);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public void eraseAll() {
        final Object[][] array = this.array;
        while (0 < array.length) {
            Arrays.fill(array[0], null);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean contains(@Nullable final Object o, @Nullable final Object o2) {
        return this.containsRow(o) && this.containsColumn(o2);
    }
    
    @Override
    public boolean containsColumn(@Nullable final Object o) {
        return this.columnKeyToIndex.containsKey(o);
    }
    
    @Override
    public boolean containsRow(@Nullable final Object o) {
        return this.rowKeyToIndex.containsKey(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        final Object[][] array = this.array;
        while (0 < array.length) {
            final Object[] array2 = array[0];
            while (0 < array2.length) {
                if (Objects.equal(o, array2[0])) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    @Override
    public Object get(@Nullable final Object o, @Nullable final Object o2) {
        final Integer n = (Integer)this.rowKeyToIndex.get(o);
        final Integer n2 = (Integer)this.columnKeyToIndex.get(o2);
        return (n == null || n2 == null) ? null : this.at(n, n2);
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public Object put(final Object o, final Object o2, @Nullable final Object o3) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        final Integer n = (Integer)this.rowKeyToIndex.get(o);
        Preconditions.checkArgument(n != null, "Row %s not in %s", o, this.rowList);
        final Integer n2 = (Integer)this.columnKeyToIndex.get(o2);
        Preconditions.checkArgument(n2 != null, "Column %s not in %s", o2, this.columnList);
        return this.set(n, n2, o3);
    }
    
    @Override
    public void putAll(final Table table) {
        super.putAll(table);
    }
    
    @Deprecated
    @Override
    public Object remove(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    public Object erase(@Nullable final Object o, @Nullable final Object o2) {
        final Integer n = (Integer)this.rowKeyToIndex.get(o);
        final Integer n2 = (Integer)this.columnKeyToIndex.get(o2);
        if (n == null || n2 == null) {
            return null;
        }
        return this.set(n, n2, null);
    }
    
    @Override
    public int size() {
        return this.rowList.size() * this.columnList.size();
    }
    
    @Override
    public Set cellSet() {
        return super.cellSet();
    }
    
    @Override
    Iterator cellIterator() {
        return new AbstractIndexedListIterator(this.size()) {
            final ArrayTable this$0;
            
            @Override
            protected Table.Cell get(final int n) {
                return new Tables.AbstractCell(n) {
                    final int rowIndex = this.val$index / ArrayTable.access$000(this.this$1.this$0).size();
                    final int columnIndex = this.val$index % ArrayTable.access$000(this.this$1.this$0).size();
                    final int val$index;
                    final ArrayTable$1 this$1;
                    
                    @Override
                    public Object getRowKey() {
                        return ArrayTable.access$100(this.this$1.this$0).get(this.rowIndex);
                    }
                    
                    @Override
                    public Object getColumnKey() {
                        return ArrayTable.access$000(this.this$1.this$0).get(this.columnIndex);
                    }
                    
                    @Override
                    public Object getValue() {
                        return this.this$1.this$0.at(this.rowIndex, this.columnIndex);
                    }
                };
            }
            
            @Override
            protected Object get(final int n) {
                return this.get(n);
            }
        };
    }
    
    @Override
    public Map column(final Object o) {
        Preconditions.checkNotNull(o);
        final Integer n = (Integer)this.columnKeyToIndex.get(o);
        return (n == null) ? ImmutableMap.of() : new Column(n);
    }
    
    @Override
    public ImmutableSet columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }
    
    @Override
    public Map columnMap() {
        final ColumnMap columnMap = this.columnMap;
        return (columnMap == null) ? (this.columnMap = new ColumnMap(null)) : columnMap;
    }
    
    @Override
    public Map row(final Object o) {
        Preconditions.checkNotNull(o);
        final Integer n = (Integer)this.rowKeyToIndex.get(o);
        return (n == null) ? ImmutableMap.of() : new Row(n);
    }
    
    @Override
    public ImmutableSet rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }
    
    @Override
    public Map rowMap() {
        final RowMap rowMap = this.rowMap;
        return (rowMap == null) ? (this.rowMap = new RowMap(null)) : rowMap;
    }
    
    @Override
    public Collection values() {
        return super.values();
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
    public Set columnKeySet() {
        return this.columnKeySet();
    }
    
    @Override
    public Set rowKeySet() {
        return this.rowKeySet();
    }
    
    static ImmutableList access$000(final ArrayTable arrayTable) {
        return arrayTable.columnList;
    }
    
    static ImmutableList access$100(final ArrayTable arrayTable) {
        return arrayTable.rowList;
    }
    
    static ImmutableMap access$200(final ArrayTable arrayTable) {
        return arrayTable.rowKeyToIndex;
    }
    
    static ImmutableMap access$500(final ArrayTable arrayTable) {
        return arrayTable.columnKeyToIndex;
    }
    
    private class RowMap extends ArrayMap
    {
        final ArrayTable this$0;
        
        private RowMap(final ArrayTable this$0) {
            this.this$0 = this$0;
            super(ArrayTable.access$200(this$0), null);
        }
        
        @Override
        String getKeyRole() {
            return "Row";
        }
        
        @Override
        Map getValue(final int n) {
            return this.this$0.new Row(n);
        }
        
        Map setValue(final int n, final Map map) {
            throw new UnsupportedOperationException();
        }
        
        public Map put(final Object o, final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            return this.put(o, (Map)o2);
        }
        
        @Override
        Object setValue(final int n, final Object o) {
            return this.setValue(n, (Map)o);
        }
        
        @Override
        Object getValue(final int n) {
            return this.getValue(n);
        }
        
        RowMap(final ArrayTable arrayTable, final ArrayTable$1 abstractIndexedListIterator) {
            this(arrayTable);
        }
    }
    
    private abstract static class ArrayMap extends Maps.ImprovedAbstractMap
    {
        private final ImmutableMap keyIndex;
        
        private ArrayMap(final ImmutableMap keyIndex) {
            this.keyIndex = keyIndex;
        }
        
        @Override
        public Set keySet() {
            return this.keyIndex.keySet();
        }
        
        Object getKey(final int n) {
            return this.keyIndex.keySet().asList().get(n);
        }
        
        abstract String getKeyRole();
        
        @Nullable
        abstract Object getValue(final int p0);
        
        @Nullable
        abstract Object setValue(final int p0, final Object p1);
        
        @Override
        public int size() {
            return this.keyIndex.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.keyIndex.isEmpty();
        }
        
        protected Set createEntrySet() {
            return new Maps.EntrySet() {
                final ArrayMap this$0;
                
                @Override
                Map map() {
                    return this.this$0;
                }
                
                @Override
                public Iterator iterator() {
                    return new AbstractIndexedListIterator(this.size()) {
                        final ArrayTable$ArrayMap$1 this$1;
                        
                        @Override
                        protected Map.Entry get(final int n) {
                            return new AbstractMapEntry(n) {
                                final int val$index;
                                final ArrayTable$ArrayMap$1$1 this$2;
                                
                                @Override
                                public Object getKey() {
                                    return this.this$2.this$1.this$0.getKey(this.val$index);
                                }
                                
                                @Override
                                public Object getValue() {
                                    return this.this$2.this$1.this$0.getValue(this.val$index);
                                }
                                
                                @Override
                                public Object setValue(final Object o) {
                                    return this.this$2.this$1.this$0.setValue(this.val$index, o);
                                }
                            };
                        }
                        
                        @Override
                        protected Object get(final int n) {
                            return this.get(n);
                        }
                    };
                }
            };
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.keyIndex.containsKey(o);
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            final Integer n = (Integer)this.keyIndex.get(o);
            if (n == null) {
                return null;
            }
            return this.getValue(n);
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            final Integer n = (Integer)this.keyIndex.get(o);
            if (n == null) {
                throw new IllegalArgumentException(this.getKeyRole() + " " + o + " not in " + this.keyIndex.keySet());
            }
            return this.setValue(n, o2);
        }
        
        @Override
        public Object remove(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        ArrayMap(final ImmutableMap immutableMap, final ArrayTable$1 abstractIndexedListIterator) {
            this(immutableMap);
        }
    }
    
    private class Row extends ArrayMap
    {
        final int rowIndex;
        final ArrayTable this$0;
        
        Row(final ArrayTable this$0, final int rowIndex) {
            this.this$0 = this$0;
            super(ArrayTable.access$500(this$0), null);
            this.rowIndex = rowIndex;
        }
        
        @Override
        String getKeyRole() {
            return "Column";
        }
        
        @Override
        Object getValue(final int n) {
            return this.this$0.at(this.rowIndex, n);
        }
        
        @Override
        Object setValue(final int n, final Object o) {
            return this.this$0.set(this.rowIndex, n, o);
        }
    }
    
    private class ColumnMap extends ArrayMap
    {
        final ArrayTable this$0;
        
        private ColumnMap(final ArrayTable this$0) {
            this.this$0 = this$0;
            super(ArrayTable.access$500(this$0), null);
        }
        
        @Override
        String getKeyRole() {
            return "Column";
        }
        
        @Override
        Map getValue(final int n) {
            return this.this$0.new Column(n);
        }
        
        Map setValue(final int n, final Map map) {
            throw new UnsupportedOperationException();
        }
        
        public Map put(final Object o, final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            return this.put(o, (Map)o2);
        }
        
        @Override
        Object setValue(final int n, final Object o) {
            return this.setValue(n, (Map)o);
        }
        
        @Override
        Object getValue(final int n) {
            return this.getValue(n);
        }
        
        ColumnMap(final ArrayTable arrayTable, final ArrayTable$1 abstractIndexedListIterator) {
            this(arrayTable);
        }
    }
    
    private class Column extends ArrayMap
    {
        final int columnIndex;
        final ArrayTable this$0;
        
        Column(final ArrayTable this$0, final int columnIndex) {
            this.this$0 = this$0;
            super(ArrayTable.access$200(this$0), null);
            this.columnIndex = columnIndex;
        }
        
        @Override
        String getKeyRole() {
            return "Row";
        }
        
        @Override
        Object getValue(final int n) {
            return this.this$0.at(n, this.columnIndex);
        }
        
        @Override
        Object setValue(final int n, final Object o) {
            return this.this$0.set(n, this.columnIndex, o);
        }
    }
}
