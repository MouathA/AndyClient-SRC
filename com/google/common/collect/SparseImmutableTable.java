package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.concurrent.*;
import java.util.*;

@GwtCompatible
@Immutable
final class SparseImmutableTable extends RegularImmutableTable
{
    private final ImmutableMap rowMap;
    private final ImmutableMap columnMap;
    private final int[] iterationOrderRow;
    private final int[] iterationOrderColumn;
    
    SparseImmutableTable(final ImmutableList list, final ImmutableSet set, final ImmutableSet set2) {
        final HashMap hashMap = Maps.newHashMap();
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (final Object next : set) {
            hashMap.put(next, linkedHashMap.size());
            linkedHashMap.put(next, new LinkedHashMap<Object, Object>());
        }
        final LinkedHashMap linkedHashMap2 = Maps.newLinkedHashMap();
        final Iterator iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            linkedHashMap2.put(iterator2.next(), new LinkedHashMap<Object, Object>());
        }
        final int[] iterationOrderRow = new int[list.size()];
        final int[] iterationOrderColumn = new int[list.size()];
        while (0 < list.size()) {
            final Table.Cell cell = list.get(0);
            final Object rowKey = cell.getRowKey();
            final Object columnKey = cell.getColumnKey();
            final Object value = cell.getValue();
            iterationOrderRow[0] = (int)hashMap.get(rowKey);
            final Map<Object, Object> map = linkedHashMap.get(rowKey);
            iterationOrderColumn[0] = map.size();
            final Object put = map.put(columnKey, value);
            if (put != null) {
                throw new IllegalArgumentException("Duplicate value for row=" + rowKey + ", column=" + columnKey + ": " + value + ", " + put);
            }
            linkedHashMap2.get(columnKey).put(rowKey, value);
            int n = 0;
            ++n;
        }
        this.iterationOrderRow = iterationOrderRow;
        this.iterationOrderColumn = iterationOrderColumn;
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        for (final Map.Entry<Object, Map<Object, Object>> entry : linkedHashMap.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
        }
        this.rowMap = builder.build();
        final ImmutableMap.Builder builder2 = ImmutableMap.builder();
        for (final Map.Entry<Object, Map<Object, Object>> entry2 : linkedHashMap2.entrySet()) {
            builder2.put(entry2.getKey(), ImmutableMap.copyOf(entry2.getValue()));
        }
        this.columnMap = builder2.build();
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
    public int size() {
        return this.iterationOrderRow.length;
    }
    
    @Override
    Table.Cell getCell(final int n) {
        final Map.Entry<K, ImmutableMap> entry = this.rowMap.entrySet().asList().get(this.iterationOrderRow[n]);
        final Map.Entry<Object, V> entry2 = entry.getValue().entrySet().asList().get(this.iterationOrderColumn[n]);
        return ImmutableTable.cellOf(entry.getKey(), entry2.getKey(), entry2.getValue());
    }
    
    @Override
    Object getValue(final int n) {
        return this.rowMap.values().asList().get(this.iterationOrderRow[n]).values().asList().get(this.iterationOrderColumn[n]);
    }
    
    @Override
    public Map columnMap() {
        return this.columnMap();
    }
    
    @Override
    public Map rowMap() {
        return this.rowMap();
    }
}
