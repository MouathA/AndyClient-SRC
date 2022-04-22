package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.concurrent.atomic.*;

final class Int2ObjectSyncMapImpl extends AbstractInt2ObjectMap implements Int2ObjectSyncMap
{
    private static final long serialVersionUID = 1L;
    private final transient Object lock;
    private transient Int2ObjectMap read;
    private transient boolean amended;
    private transient Int2ObjectMap dirty;
    private transient int misses;
    private final transient IntFunction function;
    private transient EntrySetView entrySet;
    
    Int2ObjectSyncMapImpl(final IntFunction function, final int initialCapacity) {
        this.lock = new Object();
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
        this.function = function;
        this.read = function.apply(initialCapacity);
    }
    
    @Override
    public int size() {
        this.promote();
        final ObjectIterator iterator = this.read.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().exists()) {
                int n = 0;
                ++n;
            }
        }
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        this.promote();
        final ObjectIterator iterator = this.read.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().exists()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean containsValue(final Object value) {
        final ObjectIterator iterator = this.int2ObjectEntrySet().iterator();
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next().getValue(), value)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsKey(final int key) {
        final ExpungingEntry entry = this.getEntry(key);
        return entry != null && entry.exists();
    }
    
    @Override
    public Object get(final int key) {
        final ExpungingEntry entry = this.getEntry(key);
        return (entry != null) ? entry.get() : null;
    }
    
    @Override
    public Object getOrDefault(final int key, final Object defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        final ExpungingEntry entry = this.getEntry(key);
        return (entry != null) ? entry.getOr(defaultValue) : defaultValue;
    }
    
    public ExpungingEntry getEntry(final int key) {
        ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        if (expungingEntry == null && this.amended) {
            // monitorenter(lock = this.lock)
            if ((expungingEntry = (ExpungingEntry)this.read.get(key)) == null && this.amended && this.dirty != null) {
                expungingEntry = (ExpungingEntry)this.dirty.get(key);
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return expungingEntry;
    }
    
    @Override
    public Object computeIfAbsent(final int key, final IntFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        final ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.computeIfAbsent(key, mappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = (ExpungingEntry)this.read.get(key)) != null) {
                if (expungingEntry2.tryUnexpungeAndCompute(key, mappingFunction)) {
                    if (expungingEntry2.exists()) {
                        this.dirty.put(key, expungingEntry2);
                    }
                    // monitorexit(lock)
                    return expungingEntry2.get();
                }
                insertionResult = expungingEntry2.computeIfAbsent(key, mappingFunction);
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty == null || (expungingEntry3 = (ExpungingEntry)this.dirty.get(key)) == null) {
                    if (!this.amended) {
                        this.dirtyLocked();
                        this.amended = true;
                    }
                    final Object apply = mappingFunction.apply(key);
                    if (apply != null) {
                        this.dirty.put(key, new ExpungingEntryImpl(apply));
                    }
                    // monitorexit(lock)
                    return apply;
                }
                insertionResult = expungingEntry3.computeIfAbsent(key, mappingFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(key);
                }
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return insertionResult.current();
    }
    
    @Override
    public Object computeIfAbsent(final int key, final Int2ObjectFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        final ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.computeIfAbsentPrimitive(key, mappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = (ExpungingEntry)this.read.get(key)) != null) {
                if (expungingEntry2.tryUnexpungeAndComputePrimitive(key, mappingFunction)) {
                    if (expungingEntry2.exists()) {
                        this.dirty.put(key, expungingEntry2);
                    }
                    // monitorexit(lock)
                    return expungingEntry2.get();
                }
                insertionResult = expungingEntry2.computeIfAbsentPrimitive(key, mappingFunction);
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty == null || (expungingEntry3 = (ExpungingEntry)this.dirty.get(key)) == null) {
                    if (!this.amended) {
                        this.dirtyLocked();
                        this.amended = true;
                    }
                    final Object value2 = mappingFunction.get(key);
                    if (value2 != null) {
                        this.dirty.put(key, new ExpungingEntryImpl(value2));
                    }
                    // monitorexit(lock)
                    return value2;
                }
                insertionResult = expungingEntry3.computeIfAbsentPrimitive(key, mappingFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(key);
                }
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return (insertionResult != null) ? insertionResult.current() : null;
    }
    
    @Override
    public Object computeIfPresent(final int key, final BiFunction remappingFunction) {
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        final ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.computeIfPresent(key, remappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = (ExpungingEntry)this.read.get(key)) != null) {
                insertionResult = expungingEntry2.computeIfPresent(key, remappingFunction);
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty != null && (expungingEntry3 = (ExpungingEntry)this.dirty.get(key)) != null) {
                    insertionResult = expungingEntry3.computeIfPresent(key, remappingFunction);
                    if (insertionResult.current() == null) {
                        this.dirty.remove(key);
                    }
                    this.missLocked();
                }
            }
        }
        // monitorexit(lock)
        return (insertionResult != null) ? insertionResult.current() : null;
    }
    
    @Override
    public Object compute(final int key, final BiFunction remappingFunction) {
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        final ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.compute(key, remappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = (ExpungingEntry)this.read.get(key)) != null) {
                if (expungingEntry2.tryUnexpungeAndCompute(key, remappingFunction)) {
                    if (expungingEntry2.exists()) {
                        this.dirty.put(key, expungingEntry2);
                    }
                    // monitorexit(lock)
                    return expungingEntry2.get();
                }
                insertionResult = expungingEntry2.compute(key, remappingFunction);
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty == null || (expungingEntry3 = (ExpungingEntry)this.dirty.get(key)) == null) {
                    if (!this.amended) {
                        this.dirtyLocked();
                        this.amended = true;
                    }
                    final Object apply = remappingFunction.apply(key, null);
                    if (apply != null) {
                        this.dirty.put(key, new ExpungingEntryImpl(apply));
                    }
                    // monitorexit(lock)
                    return apply;
                }
                insertionResult = expungingEntry3.compute(key, remappingFunction);
                if (insertionResult.current() == null) {
                    this.dirty.remove(key);
                }
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return insertionResult.current();
    }
    
    @Override
    public Object putIfAbsent(final int key, final Object value) {
        Objects.requireNonNull(value, "value");
        final ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.setIfAbsent(value) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = (ExpungingEntry)this.read.get(key)) != null) {
                if (expungingEntry2.tryUnexpungeAndSet(value)) {
                    this.dirty.put(key, expungingEntry2);
                }
                else {
                    insertionResult = expungingEntry2.setIfAbsent(value);
                }
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty != null && (expungingEntry3 = (ExpungingEntry)this.dirty.get(key)) != null) {
                    insertionResult = expungingEntry3.setIfAbsent(value);
                    this.missLocked();
                }
                else {
                    if (!this.amended) {
                        this.dirtyLocked();
                        this.amended = true;
                    }
                    this.dirty.put(key, new ExpungingEntryImpl(value));
                }
            }
        }
        // monitorexit(lock)
        return (insertionResult != null) ? insertionResult.previous() : null;
    }
    
    @Override
    public Object put(final int key, final Object value) {
        Objects.requireNonNull(value, "value");
        final ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        Object o = (expungingEntry != null) ? expungingEntry.get() : null;
        if (expungingEntry != null && expungingEntry.trySet(value)) {
            return o;
        }
        // monitorenter(lock = this.lock)
        final ExpungingEntry expungingEntry2;
        if ((expungingEntry2 = (ExpungingEntry)this.read.get(key)) != null) {
            o = expungingEntry2.get();
            if (expungingEntry2.tryUnexpungeAndSet(value)) {
                this.dirty.put(key, expungingEntry2);
            }
            else {
                expungingEntry2.set(value);
            }
        }
        else {
            final ExpungingEntry expungingEntry3;
            if (this.dirty != null && (expungingEntry3 = (ExpungingEntry)this.dirty.get(key)) != null) {
                o = expungingEntry3.get();
                expungingEntry3.set(value);
                this.missLocked();
            }
            else {
                if (!this.amended) {
                    this.dirtyLocked();
                    this.amended = true;
                }
                this.dirty.put(key, new ExpungingEntryImpl(value));
            }
        }
        // monitorexit(lock)
        return o;
    }
    
    @Override
    public Object remove(final int key) {
        ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        if (expungingEntry == null && this.amended) {
            // monitorenter(lock = this.lock)
            if ((expungingEntry = (ExpungingEntry)this.read.get(key)) == null && this.amended && this.dirty != null) {
                expungingEntry = (ExpungingEntry)this.dirty.remove(key);
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return (expungingEntry != null) ? expungingEntry.clear() : null;
    }
    
    @Override
    public boolean remove(final int key, final Object value) {
        Objects.requireNonNull(value, "value");
        ExpungingEntry expungingEntry = (ExpungingEntry)this.read.get(key);
        if (expungingEntry == null && this.amended) {
            // monitorenter(lock = this.lock)
            if ((expungingEntry = (ExpungingEntry)this.read.get(key)) == null && this.amended && this.dirty != null) {
                final ExpungingEntry expungingEntry2;
                final boolean b = (expungingEntry2 = (ExpungingEntry)this.dirty.get(key)) != null && expungingEntry2.replace(value, null);
                if (b) {
                    this.dirty.remove(key);
                }
                this.missLocked();
                // monitorexit(lock)
                return b;
            }
        }
        // monitorexit(lock)
        return expungingEntry != null && expungingEntry.replace(value, null);
    }
    
    @Override
    public Object replace(final int key, final Object value) {
        Objects.requireNonNull(value, "value");
        final ExpungingEntry entry = this.getEntry(key);
        return (entry != null) ? entry.tryReplace(value) : null;
    }
    
    @Override
    public boolean replace(final int key, final Object oldValue, final Object newValue) {
        Objects.requireNonNull(oldValue, "oldValue");
        Objects.requireNonNull(newValue, "newValue");
        final ExpungingEntry entry = this.getEntry(key);
        return entry != null && entry.replace(oldValue, newValue);
    }
    
    @Override
    public void forEach(final BiConsumer action) {
        Objects.requireNonNull(action, "action");
        this.promote();
        for (final Int2ObjectMap.Entry entry : this.read.int2ObjectEntrySet()) {
            final Object value;
            if ((value = entry.getValue().get()) != null) {
                action.accept(entry.getIntKey(), value);
            }
        }
    }
    
    @Override
    public void putAll(final Map map) {
        Objects.requireNonNull(map, "map");
        for (final Map.Entry<Integer, V> entry : map.entrySet()) {
            this.put((int)entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void replaceAll(final BiFunction function) {
        Objects.requireNonNull(function, "function");
        this.promote();
        for (final Int2ObjectMap.Entry entry : this.read.int2ObjectEntrySet()) {
            final ExpungingEntry expungingEntry;
            final Object value;
            if ((value = (expungingEntry = entry.getValue()).get()) != null) {
                expungingEntry.tryReplace(function.apply(entry.getIntKey(), value));
            }
        }
    }
    
    @Override
    public void clear() {
        // monitorenter(lock = this.lock)
        this.read = this.function.apply(this.read.size());
        this.dirty = null;
        this.amended = false;
        this.misses = 0;
    }
    // monitorexit(lock)
    
    @Override
    public ObjectSet int2ObjectEntrySet() {
        if (this.entrySet != null) {
            return this.entrySet;
        }
        return this.entrySet = new EntrySetView();
    }
    
    private void promote() {
        if (this.amended) {
            // monitorenter(lock = this.lock)
            if (this.amended) {
                this.promoteLocked();
            }
        }
        // monitorexit(lock)
    }
    
    private void promoteLocked() {
        if (this.dirty != null) {
            this.read = this.dirty;
        }
        this.amended = false;
        this.dirty = null;
        this.misses = 0;
    }
    
    private void missLocked() {
        if (++this.misses >= this.dirty.size()) {
            this.promoteLocked();
        }
    }
    
    private void dirtyLocked() {
        if (this.dirty != null) {
            return;
        }
        this.dirty = this.function.apply(this.read.size());
        Int2ObjectMaps.fastForEach(this.read, this::lambda$dirtyLocked$0);
    }
    
    private void lambda$dirtyLocked$0(final Int2ObjectMap.Entry entry) {
        if (!entry.getValue().tryExpunge()) {
            this.dirty.put(entry.getIntKey(), entry.getValue());
        }
    }
    
    static void access$000(final Int2ObjectSyncMapImpl int2ObjectSyncMapImpl) {
        int2ObjectSyncMapImpl.promote();
    }
    
    static Int2ObjectMap access$100(final Int2ObjectSyncMapImpl int2ObjectSyncMapImpl) {
        return int2ObjectSyncMapImpl.read;
    }
    
    final class EntryIterator implements ObjectIterator
    {
        private final Iterator backingIterator;
        private Int2ObjectMap.Entry next;
        private Int2ObjectMap.Entry current;
        final Int2ObjectSyncMapImpl this$0;
        
        EntryIterator(final Int2ObjectSyncMapImpl this$0, final Iterator backingIterator) {
            this.this$0 = this$0;
            this.backingIterator = backingIterator;
            this.next = this.nextValue();
        }
        
        @Override
        public boolean hasNext() {
            return this.next != null;
        }
        
        @Override
        public Int2ObjectMap.Entry next() {
            final Int2ObjectMap.Entry next = this.next;
            this.current = next;
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.next = this.nextValue();
            return this.current;
        }
        
        private Int2ObjectMap.Entry nextValue() {
            while (this.backingIterator.hasNext()) {
                final Int2ObjectMap.Entry entry;
                final Object value;
                if ((value = ((ExpungingEntry)(entry = this.backingIterator.next()).getValue()).get()) != null) {
                    return this.this$0.new MapEntry(entry.getIntKey(), value);
                }
            }
            return null;
        }
        
        @Override
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            this.this$0.remove(this.current.getIntKey());
            this.current = null;
        }
        
        @Override
        public void forEachRemaining(final Consumer action) {
            Objects.requireNonNull(action, "action");
            if (this.next != null) {
                action.accept(this.next);
            }
            this.backingIterator.forEachRemaining(this::lambda$forEachRemaining$0);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        private void lambda$forEachRemaining$0(final Consumer consumer, final Int2ObjectMap.Entry entry) {
            final Object value = entry.getValue().get();
            if (value != null) {
                consumer.accept(this.this$0.new MapEntry(entry.getIntKey(), value));
            }
        }
    }
    
    final class MapEntry implements Int2ObjectMap.Entry
    {
        private final int key;
        private Object value;
        final Int2ObjectSyncMapImpl this$0;
        
        MapEntry(final Int2ObjectSyncMapImpl this$0, final int key, final Object value) {
            this.this$0 = this$0;
            this.key = key;
            this.value = value;
        }
        
        @Override
        public int getIntKey() {
            return this.key;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
        
        @Override
        public Object setValue(final Object value) {
            Objects.requireNonNull(value, "value");
            final Int2ObjectSyncMapImpl this$0 = this.this$0;
            final int key = this.key;
            this.value = value;
            return this$0.put(key, value);
        }
        
        @Override
        public String toString() {
            return "Int2ObjectSyncMapImpl.MapEntry{key=" + this.getIntKey() + ", value=" + this.getValue() + "}";
        }
        
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Int2ObjectMap.Entry)) {
                return false;
            }
            final Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)other;
            return Objects.equals(this.getIntKey(), entry.getIntKey()) && Objects.equals(this.getValue(), entry.getValue());
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.getIntKey(), this.getValue());
        }
    }
    
    final class EntrySetView extends AbstractObjectSet
    {
        final Int2ObjectSyncMapImpl this$0;
        
        EntrySetView(final Int2ObjectSyncMapImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public boolean contains(final Object entry) {
            if (!(entry instanceof Int2ObjectMap.Entry)) {
                return false;
            }
            final Int2ObjectMap.Entry entry2 = (Int2ObjectMap.Entry)entry;
            final Object value = this.this$0.get(entry2.getIntKey());
            return value != null && Objects.equals(value, entry2.getValue());
        }
        
        @Override
        public boolean remove(final Object entry) {
            return entry instanceof Int2ObjectMap.Entry && this.this$0.remove(((Int2ObjectMap.Entry)entry).getIntKey()) != null;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public ObjectIterator iterator() {
            Int2ObjectSyncMapImpl.access$000(this.this$0);
            return this.this$0.new EntryIterator(Int2ObjectSyncMapImpl.access$100(this.this$0).int2ObjectEntrySet().iterator());
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    static final class InsertionResultImpl implements InsertionResult
    {
        private static final byte UNCHANGED = 0;
        private static final byte UPDATED = 1;
        private static final byte EXPUNGED = 2;
        private final byte operation;
        private final Object previous;
        private final Object current;
        
        InsertionResultImpl(final byte operation, final Object previous, final Object current) {
            this.operation = operation;
            this.previous = previous;
            this.current = current;
        }
        
        @Override
        public byte operation() {
            return this.operation;
        }
        
        @Override
        public Object previous() {
            return this.previous;
        }
        
        @Override
        public Object current() {
            return this.current;
        }
    }
    
    static final class ExpungingEntryImpl implements ExpungingEntry
    {
        private static final AtomicReferenceFieldUpdater UPDATER;
        private static final Object EXPUNGED;
        private Object value;
        
        ExpungingEntryImpl(final Object value) {
            this.value = value;
        }
        
        @Override
        public boolean exists() {
            return this.value != null && this.value != ExpungingEntryImpl.EXPUNGED;
        }
        
        @Override
        public Object get() {
            return (this.value == ExpungingEntryImpl.EXPUNGED) ? null : this.value;
        }
        
        @Override
        public Object getOr(final Object other) {
            final Object value = this.value;
            return (value != null && value != ExpungingEntryImpl.EXPUNGED) ? this.value : other;
        }
        
        @Override
        public InsertionResult setIfAbsent(final Object value) {
            while (true) {
                final Object value2 = this.value;
                if (value2 == ExpungingEntryImpl.EXPUNGED) {
                    return new InsertionResultImpl((byte)2, null, null);
                }
                if (value2 != null) {
                    return new InsertionResultImpl((byte)0, value2, value2);
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, null, value)) {
                    return new InsertionResultImpl((byte)1, null, value);
                }
            }
        }
        
        @Override
        public InsertionResult computeIfAbsent(final int key, final IntFunction function) {
            Object apply = null;
            while (true) {
                final Object value = this.value;
                if (value == ExpungingEntryImpl.EXPUNGED) {
                    return new InsertionResultImpl((byte)2, null, null);
                }
                if (value != null) {
                    return new InsertionResultImpl((byte)0, value, value);
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, null, (apply != null) ? apply : (apply = function.apply(key)))) {
                    return new InsertionResultImpl((byte)1, null, apply);
                }
            }
        }
        
        @Override
        public InsertionResult computeIfAbsentPrimitive(final int key, final Int2ObjectFunction function) {
            Object current = null;
            while (true) {
                final Object value = this.value;
                if (value == ExpungingEntryImpl.EXPUNGED) {
                    return new InsertionResultImpl((byte)2, null, null);
                }
                if (value != null) {
                    return new InsertionResultImpl((byte)0, value, value);
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, null, (current != null) ? current : (current = (function.containsKey(key) ? function.get(key) : null)))) {
                    return new InsertionResultImpl((byte)1, null, current);
                }
            }
        }
        
        @Override
        public InsertionResult computeIfPresent(final int key, final BiFunction remappingFunction) {
            Object apply = null;
            while (true) {
                final Object value = this.value;
                if (value == ExpungingEntryImpl.EXPUNGED) {
                    return new InsertionResultImpl((byte)2, null, null);
                }
                if (value == null) {
                    return new InsertionResultImpl((byte)0, null, null);
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, value, (apply != null) ? apply : (apply = remappingFunction.apply(key, value)))) {
                    return new InsertionResultImpl((byte)1, value, apply);
                }
            }
        }
        
        @Override
        public InsertionResult compute(final int key, final BiFunction remappingFunction) {
            Object apply = null;
            while (true) {
                final Object value = this.value;
                if (value == ExpungingEntryImpl.EXPUNGED) {
                    return new InsertionResultImpl((byte)2, null, null);
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, value, (apply != null) ? apply : (apply = remappingFunction.apply(key, value)))) {
                    return new InsertionResultImpl((byte)((value != apply) ? 1 : 0), value, apply);
                }
            }
        }
        
        @Override
        public void set(final Object value) {
            ExpungingEntryImpl.UPDATER.set(this, value);
        }
        
        @Override
        public boolean replace(final Object compare, final Object value) {
            while (true) {
                final Object value2 = this.value;
                if (value2 == ExpungingEntryImpl.EXPUNGED || !Objects.equals(value2, compare)) {
                    return false;
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, value2, value)) {
                    return true;
                }
            }
        }
        
        @Override
        public Object clear() {
            while (true) {
                final Object value = this.value;
                if (value == null || value == ExpungingEntryImpl.EXPUNGED) {
                    return null;
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, value, null)) {
                    return value;
                }
            }
        }
        
        @Override
        public boolean trySet(final Object value) {
            while (true) {
                final Object value2 = this.value;
                if (value2 == ExpungingEntryImpl.EXPUNGED) {
                    return false;
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, value2, value)) {
                    return true;
                }
            }
        }
        
        @Override
        public Object tryReplace(final Object value) {
            while (true) {
                final Object value2 = this.value;
                if (value2 == null || value2 == ExpungingEntryImpl.EXPUNGED) {
                    return null;
                }
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, value2, value)) {
                    return value2;
                }
            }
        }
        
        @Override
        public boolean tryExpunge() {
            while (this.value == null) {
                if (ExpungingEntryImpl.UPDATER.compareAndSet(this, null, ExpungingEntryImpl.EXPUNGED)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean tryUnexpungeAndSet(final Object value) {
            return ExpungingEntryImpl.UPDATER.compareAndSet(this, ExpungingEntryImpl.EXPUNGED, value);
        }
        
        @Override
        public boolean tryUnexpungeAndCompute(final int key, final IntFunction function) {
            return this.value == ExpungingEntryImpl.EXPUNGED && ExpungingEntryImpl.UPDATER.compareAndSet(this, ExpungingEntryImpl.EXPUNGED, function.apply(key));
        }
        
        @Override
        public boolean tryUnexpungeAndComputePrimitive(final int key, final Int2ObjectFunction function) {
            return this.value == ExpungingEntryImpl.EXPUNGED && ExpungingEntryImpl.UPDATER.compareAndSet(this, ExpungingEntryImpl.EXPUNGED, function.containsKey(key) ? function.get(key) : null);
        }
        
        @Override
        public boolean tryUnexpungeAndCompute(final int key, final BiFunction remappingFunction) {
            return this.value == ExpungingEntryImpl.EXPUNGED && ExpungingEntryImpl.UPDATER.compareAndSet(this, ExpungingEntryImpl.EXPUNGED, remappingFunction.apply(key, null));
        }
        
        static {
            UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
            EXPUNGED = new Object();
        }
    }
}
