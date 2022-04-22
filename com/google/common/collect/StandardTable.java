package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
class StandardTable extends AbstractTable implements Serializable
{
    @GwtTransient
    final Map backingMap;
    @GwtTransient
    final Supplier factory;
    private transient Set columnKeySet;
    private transient Map rowMap;
    private transient ColumnMap columnMap;
    private static final long serialVersionUID = 0L;
    
    StandardTable(final Map backingMap, final Supplier factory) {
        this.backingMap = backingMap;
        this.factory = factory;
    }
    
    @Override
    public boolean contains(@Nullable final Object o, @Nullable final Object o2) {
        return o != null && o2 != null && super.contains(o, o2);
    }
    
    @Override
    public boolean containsColumn(@Nullable final Object o) {
        if (o == null) {
            return false;
        }
        final Iterator<Map> iterator = this.backingMap.values().iterator();
        while (iterator.hasNext()) {
            if (Maps.safeContainsKey(iterator.next(), o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsRow(@Nullable final Object o) {
        return o != null && Maps.safeContainsKey(this.backingMap, o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return o != null && super.containsValue(o);
    }
    
    @Override
    public Object get(@Nullable final Object o, @Nullable final Object o2) {
        return (o == null || o2 == null) ? null : super.get(o, o2);
    }
    
    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }
    
    @Override
    public int size() {
        final Iterator<Map> iterator = this.backingMap.values().iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().size();
        }
        return 0;
    }
    
    @Override
    public void clear() {
        this.backingMap.clear();
    }
    
    private Map getOrCreate(final Object o) {
        Map map = this.backingMap.get(o);
        if (map == null) {
            map = (Map)this.factory.get();
            this.backingMap.put(o, map);
        }
        return map;
    }
    
    @Override
    public Object put(final Object o, final Object o2, final Object o3) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        Preconditions.checkNotNull(o3);
        return this.getOrCreate(o).put(o2, o3);
    }
    
    @Override
    public Object remove(@Nullable final Object o, @Nullable final Object o2) {
        if (o == null || o2 == null) {
            return null;
        }
        final Map map = (Map)Maps.safeGet(this.backingMap, o);
        if (map == null) {
            return null;
        }
        final Object remove = map.remove(o2);
        if (map.isEmpty()) {
            this.backingMap.remove(o);
        }
        return remove;
    }
    
    private Map removeColumn(final Object o) {
        final LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
        final Iterator<Map.Entry<K, Map>> iterator = this.backingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<K, Map> entry = iterator.next();
            final Object remove = entry.getValue().remove(o);
            if (remove != null) {
                linkedHashMap.put(entry.getKey(), remove);
                if (!entry.getValue().isEmpty()) {
                    continue;
                }
                iterator.remove();
            }
        }
        return linkedHashMap;
    }
    
    private boolean removeMapping(final Object o, final Object o2, final Object o3) {
        if (o3 != null) {
            this.remove(o, o2);
            return true;
        }
        return false;
    }
    
    @Override
    public Set cellSet() {
        return super.cellSet();
    }
    
    @Override
    Iterator cellIterator() {
        return new CellIterator(null);
    }
    
    @Override
    public Map row(final Object o) {
        return new Row(o);
    }
    
    @Override
    public Map column(final Object o) {
        return new Column(o);
    }
    
    @Override
    public Set rowKeySet() {
        return this.rowMap().keySet();
    }
    
    @Override
    public Set columnKeySet() {
        final Set columnKeySet = this.columnKeySet;
        return (columnKeySet == null) ? (this.columnKeySet = new ColumnKeySet(null)) : columnKeySet;
    }
    
    Iterator createColumnKeyIterator() {
        return new ColumnKeyIterator(null);
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    public Map rowMap() {
        final Map rowMap = this.rowMap;
        return (rowMap == null) ? (this.rowMap = this.createRowMap()) : rowMap;
    }
    
    Map createRowMap() {
        return new RowMap();
    }
    
    @Override
    public Map columnMap() {
        final ColumnMap columnMap = this.columnMap;
        return (columnMap == null) ? (this.columnMap = new ColumnMap(null)) : columnMap;
    }
    
    static boolean access$400(final StandardTable standardTable, final Object o, final Object o2, final Object o3) {
        return standardTable.containsMapping(o, o2, o3);
    }
    
    static boolean access$500(final StandardTable standardTable, final Object o, final Object o2, final Object o3) {
        return standardTable.removeMapping(o, o2, o3);
    }
    
    static Map access$1000(final StandardTable standardTable, final Object o) {
        return standardTable.removeColumn(o);
    }
    
    private class ColumnMap extends Maps.ImprovedAbstractMap
    {
        final StandardTable this$0;
        
        private ColumnMap(final StandardTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Map get(final Object o) {
            return this.this$0.containsColumn(o) ? this.this$0.column(o) : null;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.this$0.containsColumn(o);
        }
        
        @Override
        public Map remove(final Object o) {
            return this.this$0.containsColumn(o) ? StandardTable.access$1000(this.this$0, o) : null;
        }
        
        public Set createEntrySet() {
            return new ColumnMapEntrySet();
        }
        
        @Override
        public Set keySet() {
            return this.this$0.columnKeySet();
        }
        
        @Override
        Collection createValues() {
            return new ColumnMapValues();
        }
        
        @Override
        public Object remove(final Object o) {
            return this.remove(o);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        ColumnMap(final StandardTable standardTable, final StandardTable$1 object) {
            this(standardTable);
        }
        
        private class ColumnMapValues extends Maps.Values
        {
            final ColumnMap this$1;
            
            ColumnMapValues(final ColumnMap this$1) {
                super(this.this$1 = this$1);
            }
            
            @Override
            public boolean remove(final Object o) {
                for (final Map.Entry<K, Map> entry : this.this$1.entrySet()) {
                    if (entry.getValue().equals(o)) {
                        StandardTable.access$1000(this.this$1.this$0, entry.getKey());
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public boolean removeAll(final Collection collection) {
                Preconditions.checkNotNull(collection);
                for (final Object next : Lists.newArrayList(this.this$1.this$0.columnKeySet().iterator())) {
                    if (collection.contains(this.this$1.this$0.column(next))) {
                        StandardTable.access$1000(this.this$1.this$0, next);
                    }
                }
                return true;
            }
            
            @Override
            public boolean retainAll(final Collection collection) {
                Preconditions.checkNotNull(collection);
                for (final Object next : Lists.newArrayList(this.this$1.this$0.columnKeySet().iterator())) {
                    if (!collection.contains(this.this$1.this$0.column(next))) {
                        StandardTable.access$1000(this.this$1.this$0, next);
                    }
                }
                return true;
            }
        }
        
        class ColumnMapEntrySet extends TableSet
        {
            final ColumnMap this$1;
            
            ColumnMapEntrySet(final ColumnMap this$1) {
                this.this$1 = this$1;
                this$1.this$0.super(null);
            }
            
            @Override
            public Iterator iterator() {
                return Maps.asMapEntryIterator(this.this$1.this$0.columnKeySet(), new Function() {
                    final ColumnMapEntrySet this$2;
                    
                    @Override
                    public Map apply(final Object o) {
                        return this.this$2.this$1.this$0.column(o);
                    }
                    
                    @Override
                    public Object apply(final Object o) {
                        return this.apply(o);
                    }
                });
            }
            
            @Override
            public int size() {
                return this.this$1.this$0.columnKeySet().size();
            }
            
            @Override
            public boolean contains(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry entry = (Map.Entry)o;
                    if (this.this$1.this$0.containsColumn(entry.getKey())) {
                        return this.this$1.get(entry.getKey()).equals(entry.getValue());
                    }
                }
                return false;
            }
            
            @Override
            public boolean remove(final Object o) {
                if (this.contains(o)) {
                    StandardTable.access$1000(this.this$1.this$0, ((Map.Entry)o).getKey());
                    return true;
                }
                return false;
            }
            
            @Override
            public boolean removeAll(final Collection collection) {
                Preconditions.checkNotNull(collection);
                return Sets.removeAllImpl(this, collection.iterator());
            }
            
            @Override
            public boolean retainAll(final Collection collection) {
                Preconditions.checkNotNull(collection);
                for (final Object next : Lists.newArrayList(this.this$1.this$0.columnKeySet().iterator())) {
                    if (!collection.contains(Maps.immutableEntry(next, this.this$1.this$0.column(next)))) {
                        StandardTable.access$1000(this.this$1.this$0, next);
                    }
                }
                return true;
            }
        }
    }
    
    private abstract class TableSet extends Sets.ImprovedAbstractSet
    {
        final StandardTable this$0;
        
        private TableSet(final StandardTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean isEmpty() {
            return this.this$0.backingMap.isEmpty();
        }
        
        @Override
        public void clear() {
            this.this$0.backingMap.clear();
        }
        
        TableSet(final StandardTable standardTable, final StandardTable$1 object) {
            this(standardTable);
        }
    }
    
    class RowMap extends Maps.ImprovedAbstractMap
    {
        final StandardTable this$0;
        
        RowMap(final StandardTable this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.this$0.containsRow(o);
        }
        
        @Override
        public Map get(final Object o) {
            return this.this$0.containsRow(o) ? this.this$0.row(o) : null;
        }
        
        @Override
        public Map remove(final Object o) {
            return (o == null) ? null : this.this$0.backingMap.remove(o);
        }
        
        protected Set createEntrySet() {
            return new EntrySet();
        }
        
        @Override
        public Object remove(final Object o) {
            return this.remove(o);
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        class EntrySet extends TableSet
        {
            final RowMap this$1;
            
            EntrySet(final RowMap this$1) {
                this.this$1 = this$1;
                this$1.this$0.super(null);
            }
            
            @Override
            public Iterator iterator() {
                return Maps.asMapEntryIterator(this.this$1.this$0.backingMap.keySet(), new Function() {
                    final EntrySet this$2;
                    
                    @Override
                    public Map apply(final Object o) {
                        return this.this$2.this$1.this$0.row(o);
                    }
                    
                    @Override
                    public Object apply(final Object o) {
                        return this.apply(o);
                    }
                });
            }
            
            @Override
            public int size() {
                return this.this$1.this$0.backingMap.size();
            }
            
            @Override
            public boolean contains(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry entry = (Map.Entry)o;
                    return entry.getKey() != null && entry.getValue() instanceof Map && Collections2.safeContains(this.this$1.this$0.backingMap.entrySet(), entry);
                }
                return false;
            }
            
            @Override
            public boolean remove(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry entry = (Map.Entry)o;
                    return entry.getKey() != null && entry.getValue() instanceof Map && this.this$1.this$0.backingMap.entrySet().remove(entry);
                }
                return false;
            }
        }
    }
    
    private class ColumnKeyIterator extends AbstractIterator
    {
        final Map seen;
        final Iterator mapIterator;
        Iterator entryIterator;
        final StandardTable this$0;
        
        private ColumnKeyIterator(final StandardTable this$0) {
            this.this$0 = this$0;
            this.seen = (Map)this.this$0.factory.get();
            this.mapIterator = this.this$0.backingMap.values().iterator();
            this.entryIterator = Iterators.emptyIterator();
        }
        
        @Override
        protected Object computeNext() {
            while (true) {
                if (this.entryIterator.hasNext()) {
                    final Map.Entry<Object, V> entry = this.entryIterator.next();
                    if (!this.seen.containsKey(entry.getKey())) {
                        this.seen.put(entry.getKey(), entry.getValue());
                        return entry.getKey();
                    }
                    continue;
                }
                else {
                    if (!this.mapIterator.hasNext()) {
                        return this.endOfData();
                    }
                    this.entryIterator = this.mapIterator.next().entrySet().iterator();
                }
            }
        }
        
        ColumnKeyIterator(final StandardTable standardTable, final StandardTable$1 object) {
            this(standardTable);
        }
    }
    
    private class ColumnKeySet extends TableSet
    {
        final StandardTable this$0;
        
        private ColumnKeySet(final StandardTable this$0) {
            this.this$0 = this$0.super(null);
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.createColumnKeyIterator();
        }
        
        @Override
        public int size() {
            return Iterators.size(this.iterator());
        }
        
        @Override
        public boolean remove(final Object o) {
            if (o == null) {
                return false;
            }
            final Iterator<Map> iterator = this.this$0.backingMap.values().iterator();
            while (iterator.hasNext()) {
                final Map map = iterator.next();
                if (map.keySet().remove(o) && map.isEmpty()) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            Preconditions.checkNotNull(collection);
            final Iterator<Map> iterator = this.this$0.backingMap.values().iterator();
            while (iterator.hasNext()) {
                final Map map = iterator.next();
                if (Iterators.removeAll(map.keySet().iterator(), collection) && map.isEmpty()) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            Preconditions.checkNotNull(collection);
            final Iterator<Map> iterator = this.this$0.backingMap.values().iterator();
            while (iterator.hasNext()) {
                final Map map = iterator.next();
                if (map.keySet().retainAll(collection) && map.isEmpty()) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsColumn(o);
        }
        
        ColumnKeySet(final StandardTable standardTable, final StandardTable$1 object) {
            this(standardTable);
        }
    }
    
    private class Column extends Maps.ImprovedAbstractMap
    {
        final Object columnKey;
        final StandardTable this$0;
        
        Column(final StandardTable this$0, final Object o) {
            this.this$0 = this$0;
            this.columnKey = Preconditions.checkNotNull(o);
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            return this.this$0.put(o, this.columnKey, o2);
        }
        
        @Override
        public Object get(final Object o) {
            return this.this$0.get(o, this.columnKey);
        }
        
        @Override
        public boolean containsKey(final Object o) {
            return this.this$0.contains(o, this.columnKey);
        }
        
        @Override
        public Object remove(final Object o) {
            return this.this$0.remove(o, this.columnKey);
        }
        
        boolean removeFromColumnIf(final Predicate predicate) {
            final Iterator<Map.Entry<K, Map>> iterator = this.this$0.backingMap.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<K, Map> entry = iterator.next();
                final Map<K, Object> map = entry.getValue();
                final Object value = map.get(this.columnKey);
                if (value != null && predicate.apply(Maps.immutableEntry(entry.getKey(), value))) {
                    map.remove(this.columnKey);
                    if (!map.isEmpty()) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        Set createEntrySet() {
            return new EntrySet(null);
        }
        
        @Override
        Set createKeySet() {
            return new KeySet();
        }
        
        @Override
        Collection createValues() {
            return new Values();
        }
        
        private class Values extends Maps.Values
        {
            final Column this$1;
            
            Values(final Column this$1) {
                super(this.this$1 = this$1);
            }
            
            @Override
            public boolean remove(final Object o) {
                return o != null && this.this$1.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.equalTo(o)));
            }
            
            @Override
            public boolean removeAll(final Collection collection) {
                return this.this$1.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
            }
            
            @Override
            public boolean retainAll(final Collection collection) {
                return this.this$1.removeFromColumnIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }
        
        private class KeySet extends Maps.KeySet
        {
            final Column this$1;
            
            KeySet(final Column this$1) {
                super(this.this$1 = this$1);
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.this$1.this$0.contains(o, this.this$1.columnKey);
            }
            
            @Override
            public boolean remove(final Object o) {
                return this.this$1.this$0.remove(o, this.this$1.columnKey) != null;
            }
            
            @Override
            public boolean retainAll(final Collection collection) {
                return this.this$1.removeFromColumnIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }
        
        private class EntrySetIterator extends AbstractIterator
        {
            final Iterator iterator;
            final Column this$1;
            
            private EntrySetIterator(final Column this$1) {
                this.this$1 = this$1;
                this.iterator = this.this$1.this$0.backingMap.entrySet().iterator();
            }
            
            @Override
            protected Map.Entry computeNext() {
                while (this.iterator.hasNext()) {
                    final Map.Entry<K, Map> entry = this.iterator.next();
                    if (entry.getValue().containsKey(this.this$1.columnKey)) {
                        return new AbstractMapEntry((Map.Entry)entry) {
                            final Map.Entry val$entry;
                            final EntrySetIterator this$2;
                            
                            @Override
                            public Object getKey() {
                                return this.val$entry.getKey();
                            }
                            
                            @Override
                            public Object getValue() {
                                return this.val$entry.getValue().get(this.this$2.this$1.columnKey);
                            }
                            
                            @Override
                            public Object setValue(final Object o) {
                                return this.val$entry.getValue().put(this.this$2.this$1.columnKey, Preconditions.checkNotNull(o));
                            }
                        };
                    }
                }
                return (Map.Entry)this.endOfData();
            }
            
            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
            
            EntrySetIterator(final Column column, final StandardTable$1 object) {
                this(column);
            }
        }
        
        private class EntrySet extends Sets.ImprovedAbstractSet
        {
            final Column this$1;
            
            private EntrySet(final Column this$1) {
                this.this$1 = this$1;
            }
            
            @Override
            public Iterator iterator() {
                return this.this$1.new EntrySetIterator(null);
            }
            
            @Override
            public int size() {
                final Iterator<Map> iterator = this.this$1.this$0.backingMap.values().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().containsKey(this.this$1.columnKey)) {
                        int n = 0;
                        ++n;
                    }
                }
                return 0;
            }
            
            @Override
            public boolean isEmpty() {
                return !this.this$1.this$0.containsColumn(this.this$1.columnKey);
            }
            
            @Override
            public void clear() {
                this.this$1.removeFromColumnIf(Predicates.alwaysTrue());
            }
            
            @Override
            public boolean contains(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry entry = (Map.Entry)o;
                    return StandardTable.access$400(this.this$1.this$0, entry.getKey(), this.this$1.columnKey, entry.getValue());
                }
                return false;
            }
            
            @Override
            public boolean remove(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry entry = (Map.Entry)o;
                    return StandardTable.access$500(this.this$1.this$0, entry.getKey(), this.this$1.columnKey, entry.getValue());
                }
                return false;
            }
            
            @Override
            public boolean retainAll(final Collection collection) {
                return this.this$1.removeFromColumnIf(Predicates.not(Predicates.in(collection)));
            }
            
            EntrySet(final Column column, final StandardTable$1 object) {
                this(column);
            }
        }
    }
    
    class Row extends Maps.ImprovedAbstractMap
    {
        final Object rowKey;
        Map backingRowMap;
        final StandardTable this$0;
        
        Row(final StandardTable this$0, final Object o) {
            this.this$0 = this$0;
            this.rowKey = Preconditions.checkNotNull(o);
        }
        
        Map backingRowMap() {
            return (this.backingRowMap == null || (this.backingRowMap.isEmpty() && this.this$0.backingMap.containsKey(this.rowKey))) ? (this.backingRowMap = this.computeBackingRowMap()) : this.backingRowMap;
        }
        
        Map computeBackingRowMap() {
            return this.this$0.backingMap.get(this.rowKey);
        }
        
        void maintainEmptyInvariant() {
            if (this.backingRowMap() != null && this.backingRowMap.isEmpty()) {
                this.this$0.backingMap.remove(this.rowKey);
                this.backingRowMap = null;
            }
        }
        
        @Override
        public boolean containsKey(final Object o) {
            final Map backingRowMap = this.backingRowMap();
            return o != null && backingRowMap != null && Maps.safeContainsKey(backingRowMap, o);
        }
        
        @Override
        public Object get(final Object o) {
            final Map backingRowMap = this.backingRowMap();
            return (o != null && backingRowMap != null) ? Maps.safeGet(backingRowMap, o) : null;
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(o2);
            if (this.backingRowMap != null && !this.backingRowMap.isEmpty()) {
                return this.backingRowMap.put(o, o2);
            }
            return this.this$0.put(this.rowKey, o, o2);
        }
        
        @Override
        public Object remove(final Object o) {
            final Map backingRowMap = this.backingRowMap();
            if (backingRowMap == null) {
                return null;
            }
            final Object safeRemove = Maps.safeRemove(backingRowMap, o);
            this.maintainEmptyInvariant();
            return safeRemove;
        }
        
        @Override
        public void clear() {
            final Map backingRowMap = this.backingRowMap();
            if (backingRowMap != null) {
                backingRowMap.clear();
            }
            this.maintainEmptyInvariant();
        }
        
        protected Set createEntrySet() {
            return new RowEntrySet(null);
        }
        
        private final class RowEntrySet extends Maps.EntrySet
        {
            final Row this$1;
            
            private RowEntrySet(final Row this$1) {
                this.this$1 = this$1;
            }
            
            @Override
            Map map() {
                return this.this$1;
            }
            
            @Override
            public int size() {
                final Map backingRowMap = this.this$1.backingRowMap();
                return (backingRowMap == null) ? 0 : backingRowMap.size();
            }
            
            @Override
            public Iterator iterator() {
                final Map backingRowMap = this.this$1.backingRowMap();
                if (backingRowMap == null) {
                    return Iterators.emptyModifiableIterator();
                }
                return new Iterator((Iterator)backingRowMap.entrySet().iterator()) {
                    final Iterator val$iterator;
                    final RowEntrySet this$2;
                    
                    @Override
                    public boolean hasNext() {
                        return this.val$iterator.hasNext();
                    }
                    
                    @Override
                    public Map.Entry next() {
                        return new ForwardingMapEntry((Map.Entry)this.val$iterator.next()) {
                            final Map.Entry val$entry;
                            final StandardTable$Row$RowEntrySet$1 this$3;
                            
                            @Override
                            protected Map.Entry delegate() {
                                return this.val$entry;
                            }
                            
                            @Override
                            public Object setValue(final Object o) {
                                return super.setValue(Preconditions.checkNotNull(o));
                            }
                            
                            @Override
                            public boolean equals(final Object o) {
                                return this.standardEquals(o);
                            }
                            
                            @Override
                            protected Object delegate() {
                                return this.delegate();
                            }
                        };
                    }
                    
                    @Override
                    public void remove() {
                        this.val$iterator.remove();
                        this.this$2.this$1.maintainEmptyInvariant();
                    }
                    
                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }
            
            RowEntrySet(final Row row, final StandardTable$1 object) {
                this(row);
            }
        }
    }
    
    private class CellIterator implements Iterator
    {
        final Iterator rowIterator;
        Map.Entry rowEntry;
        Iterator columnIterator;
        final StandardTable this$0;
        
        private CellIterator(final StandardTable this$0) {
            this.this$0 = this$0;
            this.rowIterator = this.this$0.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.emptyModifiableIterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }
        
        @Override
        public Table.Cell next() {
            if (!this.columnIterator.hasNext()) {
                this.rowEntry = this.rowIterator.next();
                this.columnIterator = this.rowEntry.getValue().entrySet().iterator();
            }
            final Map.Entry<Object, V> entry = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), entry.getKey(), entry.getValue());
        }
        
        @Override
        public void remove() {
            this.columnIterator.remove();
            if (this.rowEntry.getValue().isEmpty()) {
                this.rowIterator.remove();
            }
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        CellIterator(final StandardTable standardTable, final StandardTable$1 object) {
            this(standardTable);
        }
    }
}
