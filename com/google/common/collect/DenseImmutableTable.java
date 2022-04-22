package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.concurrent.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
@Immutable
final class DenseImmutableTable extends RegularImmutableTable
{
    private final ImmutableMap rowKeyToIndex;
    private final ImmutableMap columnKeyToIndex;
    private final ImmutableMap rowMap;
    private final ImmutableMap columnMap;
    private final int[] rowCounts;
    private final int[] columnCounts;
    private final Object[][] values;
    private final int[] iterationOrderRow;
    private final int[] iterationOrderColumn;
    
    private static ImmutableMap makeIndex(final ImmutableSet set) {
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        final Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            builder.put(iterator.next(), 0);
            int n = 0;
            ++n;
        }
        return builder.build();
    }
    
    DenseImmutableTable(final ImmutableList list, final ImmutableSet set, final ImmutableSet set2) {
        this.values = new Object[set.size()][set2.size()];
        this.rowKeyToIndex = makeIndex(set);
        this.columnKeyToIndex = makeIndex(set2);
        this.rowCounts = new int[this.rowKeyToIndex.size()];
        this.columnCounts = new int[this.columnKeyToIndex.size()];
        final int[] iterationOrderRow = new int[list.size()];
        final int[] iterationOrderColumn = new int[list.size()];
        while (0 < list.size()) {
            final Table.Cell cell = list.get(0);
            final Object rowKey = cell.getRowKey();
            final Object columnKey = cell.getColumnKey();
            final int intValue = (int)this.rowKeyToIndex.get(rowKey);
            final int intValue2 = (int)this.columnKeyToIndex.get(columnKey);
            Preconditions.checkArgument(this.values[intValue][intValue2] == null, "duplicate key: (%s, %s)", rowKey, columnKey);
            this.values[intValue][intValue2] = cell.getValue();
            final int[] rowCounts = this.rowCounts;
            final int n = intValue;
            ++rowCounts[n];
            final int[] columnCounts = this.columnCounts;
            final int n2 = intValue2;
            ++columnCounts[n2];
            iterationOrderRow[0] = intValue;
            iterationOrderColumn[0] = intValue2;
            int n3 = 0;
            ++n3;
        }
        this.iterationOrderRow = iterationOrderRow;
        this.iterationOrderColumn = iterationOrderColumn;
        this.rowMap = new RowMap(null);
        this.columnMap = new ColumnMap(null);
    }
    
    @Override
    public ImmutableMap columnMap() {
        return this.columnMap;
    }
    
    @Override
    public ImmutableMap rowMap() {
        return this.rowMap;
    }
    
    @Override
    public Object get(@Nullable final Object o, @Nullable final Object o2) {
        final Integer n = (Integer)this.rowKeyToIndex.get(o);
        final Integer n2 = (Integer)this.columnKeyToIndex.get(o2);
        return (n == null || n2 == null) ? null : this.values[n][n2];
    }
    
    @Override
    public int size() {
        return this.iterationOrderRow.length;
    }
    
    @Override
    Table.Cell getCell(final int n) {
        final int n2 = this.iterationOrderRow[n];
        final int n3 = this.iterationOrderColumn[n];
        return ImmutableTable.cellOf(this.rowKeySet().asList().get(n2), this.columnKeySet().asList().get(n3), this.values[n2][n3]);
    }
    
    @Override
    Object getValue(final int n) {
        return this.values[this.iterationOrderRow[n]][this.iterationOrderColumn[n]];
    }
    
    @Override
    public Map columnMap() {
        return this.columnMap();
    }
    
    @Override
    public Map rowMap() {
        return this.rowMap();
    }
    
    static int[] access$200(final DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.rowCounts;
    }
    
    static ImmutableMap access$300(final DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.columnKeyToIndex;
    }
    
    static Object[][] access$400(final DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.values;
    }
    
    static int[] access$500(final DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.columnCounts;
    }
    
    static ImmutableMap access$600(final DenseImmutableTable denseImmutableTable) {
        return denseImmutableTable.rowKeyToIndex;
    }
    
    private final class ColumnMap extends ImmutableArrayMap
    {
        final DenseImmutableTable this$0;
        
        private ColumnMap(final DenseImmutableTable this$0) {
            this.this$0 = this$0;
            super(DenseImmutableTable.access$500(this$0).length);
        }
        
        @Override
        ImmutableMap keyToIndex() {
            return DenseImmutableTable.access$300(this.this$0);
        }
        
        @Override
        Map getValue(final int n) {
            return this.this$0.new Column(n);
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        Object getValue(final int n) {
            return this.getValue(n);
        }
        
        ColumnMap(final DenseImmutableTable denseImmutableTable, final DenseImmutableTable$1 object) {
            this(denseImmutableTable);
        }
    }
    
    private abstract static class ImmutableArrayMap extends ImmutableMap
    {
        private final int size;
        
        ImmutableArrayMap(final int size) {
            this.size = size;
        }
        
        abstract ImmutableMap keyToIndex();
        
        private boolean isFull() {
            return this.size == this.keyToIndex().size();
        }
        
        Object getKey(final int n) {
            return this.keyToIndex().keySet().asList().get(n);
        }
        
        @Nullable
        abstract Object getValue(final int p0);
        
        @Override
        ImmutableSet createKeySet() {
            return this.isFull() ? this.keyToIndex().keySet() : super.createKeySet();
        }
        
        @Override
        public int size() {
            return this.size;
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            final Integer n = (Integer)this.keyToIndex().get(o);
            return (n == null) ? null : this.getValue(n);
        }
        
        @Override
        ImmutableSet createEntrySet() {
            return new ImmutableMapEntrySet() {
                final ImmutableArrayMap this$0;
                
                @Override
                ImmutableMap map() {
                    return this.this$0;
                }
                
                @Override
                public UnmodifiableIterator iterator() {
                    return new AbstractIterator() {
                        private int index = -1;
                        private final int maxIndex = this.this$1.this$0.keyToIndex().size();
                        final DenseImmutableTable$ImmutableArrayMap$1 this$1;
                        
                        @Override
                        protected Map.Entry computeNext() {
                            ++this.index;
                            while (this.index < this.maxIndex) {
                                final Object value = this.this$1.this$0.getValue(this.index);
                                if (value != null) {
                                    return Maps.immutableEntry(this.this$1.this$0.getKey(this.index), value);
                                }
                                ++this.index;
                            }
                            return (Map.Entry)this.endOfData();
                        }
                        
                        @Override
                        protected Object computeNext() {
                            return this.computeNext();
                        }
                    };
                }
                
                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
    }
    
    private final class Column extends ImmutableArrayMap
    {
        private final int columnIndex;
        final DenseImmutableTable this$0;
        
        Column(final DenseImmutableTable this$0, final int columnIndex) {
            this.this$0 = this$0;
            super(DenseImmutableTable.access$500(this$0)[columnIndex]);
            this.columnIndex = columnIndex;
        }
        
        @Override
        ImmutableMap keyToIndex() {
            return DenseImmutableTable.access$600(this.this$0);
        }
        
        @Override
        Object getValue(final int n) {
            return DenseImmutableTable.access$400(this.this$0)[n][this.columnIndex];
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
    }
    
    private final class RowMap extends ImmutableArrayMap
    {
        final DenseImmutableTable this$0;
        
        private RowMap(final DenseImmutableTable this$0) {
            this.this$0 = this$0;
            super(DenseImmutableTable.access$200(this$0).length);
        }
        
        @Override
        ImmutableMap keyToIndex() {
            return DenseImmutableTable.access$600(this.this$0);
        }
        
        @Override
        Map getValue(final int n) {
            return this.this$0.new Row(n);
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        Object getValue(final int n) {
            return this.getValue(n);
        }
        
        RowMap(final DenseImmutableTable denseImmutableTable, final DenseImmutableTable$1 object) {
            this(denseImmutableTable);
        }
    }
    
    private final class Row extends ImmutableArrayMap
    {
        private final int rowIndex;
        final DenseImmutableTable this$0;
        
        Row(final DenseImmutableTable this$0, final int rowIndex) {
            this.this$0 = this$0;
            super(DenseImmutableTable.access$200(this$0)[rowIndex]);
            this.rowIndex = rowIndex;
        }
        
        @Override
        ImmutableMap keyToIndex() {
            return DenseImmutableTable.access$300(this.this$0);
        }
        
        @Override
        Object getValue(final int n) {
            return DenseImmutableTable.access$400(this.this$0)[this.rowIndex][n];
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
    }
}
