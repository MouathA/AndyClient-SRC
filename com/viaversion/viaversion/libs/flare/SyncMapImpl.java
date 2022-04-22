package com.viaversion.viaversion.libs.flare;

import java.util.function.*;
import java.util.*;
import java.util.concurrent.atomic.*;

final class SyncMapImpl extends AbstractMap implements SyncMap
{
    private final transient Object lock;
    private transient Map read;
    private transient boolean amended;
    private transient Map dirty;
    private transient int misses;
    private final transient IntFunction function;
    private transient EntrySetView entrySet;
    
    SyncMapImpl(final IntFunction function, final int initialCapacity) {
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
        final Iterator<ExpungingEntry> iterator = this.read.values().iterator();
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
        final Iterator<ExpungingEntry> iterator = this.read.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().exists()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean containsKey(final Object key) {
        final ExpungingEntry entry = this.getEntry(key);
        return entry != null && entry.exists();
    }
    
    @Override
    public Object get(final Object key) {
        final ExpungingEntry entry = this.getEntry(key);
        return (entry != null) ? entry.get() : null;
    }
    
    @Override
    public Object getOrDefault(final Object key, final Object defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue");
        final ExpungingEntry entry = this.getEntry(key);
        return (entry != null) ? entry.getOr(defaultValue) : defaultValue;
    }
    
    private ExpungingEntry getEntry(final Object key) {
        ExpungingEntry expungingEntry = this.read.get(key);
        if (expungingEntry == null && this.amended) {
            // monitorenter(lock = this.lock)
            if ((expungingEntry = this.read.get(key)) == null && this.amended && this.dirty != null) {
                expungingEntry = this.dirty.get(key);
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return expungingEntry;
    }
    
    @Override
    public Object computeIfAbsent(final Object key, final Function mappingFunction) {
        Objects.requireNonNull(mappingFunction, "mappingFunction");
        final ExpungingEntry expungingEntry = this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.computeIfAbsent(key, mappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = this.read.get(key)) != null) {
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
                if (this.dirty == null || (expungingEntry3 = this.dirty.get(key)) == null) {
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
    public Object computeIfPresent(final Object key, final BiFunction remappingFunction) {
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        final ExpungingEntry expungingEntry = this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.computeIfPresent(key, remappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = this.read.get(key)) != null) {
                insertionResult = expungingEntry2.computeIfPresent(key, remappingFunction);
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty != null && (expungingEntry3 = this.dirty.get(key)) != null) {
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
    public Object compute(final Object key, final BiFunction remappingFunction) {
        Objects.requireNonNull(remappingFunction, "remappingFunction");
        final ExpungingEntry expungingEntry = this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.compute(key, remappingFunction) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = this.read.get(key)) != null) {
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
                if (this.dirty == null || (expungingEntry3 = this.dirty.get(key)) == null) {
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
    public Object putIfAbsent(final Object key, final Object value) {
        Objects.requireNonNull(value, "value");
        final ExpungingEntry expungingEntry = this.read.get(key);
        InsertionResult insertionResult = (expungingEntry != null) ? expungingEntry.setIfAbsent(value) : null;
        if (insertionResult == null || insertionResult.operation() == 2) {
            // monitorenter(lock = this.lock)
            final ExpungingEntry expungingEntry2;
            if ((expungingEntry2 = this.read.get(key)) != null) {
                if (expungingEntry2.tryUnexpungeAndSet(value)) {
                    this.dirty.put(key, expungingEntry2);
                }
                else {
                    insertionResult = expungingEntry2.setIfAbsent(value);
                }
            }
            else {
                final ExpungingEntry expungingEntry3;
                if (this.dirty != null && (expungingEntry3 = this.dirty.get(key)) != null) {
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
    public Object put(final Object key, final Object value) {
        Objects.requireNonNull(value, "value");
        final ExpungingEntry expungingEntry = this.read.get(key);
        Object o = (expungingEntry != null) ? expungingEntry.get() : null;
        if (expungingEntry != null && expungingEntry.trySet(value)) {
            return o;
        }
        // monitorenter(lock = this.lock)
        final ExpungingEntry expungingEntry2;
        if ((expungingEntry2 = this.read.get(key)) != null) {
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
            if (this.dirty != null && (expungingEntry3 = this.dirty.get(key)) != null) {
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
    public Object remove(final Object key) {
        ExpungingEntry expungingEntry = this.read.get(key);
        if (expungingEntry == null && this.amended) {
            // monitorenter(lock = this.lock)
            if ((expungingEntry = this.read.get(key)) == null && this.amended && this.dirty != null) {
                expungingEntry = this.dirty.remove(key);
                this.missLocked();
            }
        }
        // monitorexit(lock)
        return (expungingEntry != null) ? expungingEntry.clear() : null;
    }
    
    @Override
    public boolean remove(final Object key, final Object value) {
        Objects.requireNonNull(value, "value");
        ExpungingEntry expungingEntry = this.read.get(key);
        if (expungingEntry == null && this.amended) {
            // monitorenter(lock = this.lock)
            if ((expungingEntry = this.read.get(key)) == null && this.amended && this.dirty != null) {
                final ExpungingEntry expungingEntry2;
                final boolean b = (expungingEntry2 = this.dirty.get(key)) != null && expungingEntry2.replace(value, null);
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
    public Object replace(final Object key, final Object value) {
        Objects.requireNonNull(value, "value");
        final ExpungingEntry entry = this.getEntry(key);
        return (entry != null) ? entry.tryReplace(value) : null;
    }
    
    @Override
    public boolean replace(final Object key, final Object oldValue, final Object newValue) {
        Objects.requireNonNull(oldValue, "oldValue");
        Objects.requireNonNull(newValue, "newValue");
        final ExpungingEntry entry = this.getEntry(key);
        return entry != null && entry.replace(oldValue, newValue);
    }
    
    @Override
    public void forEach(final BiConsumer action) {
        Objects.requireNonNull(action, "action");
        this.promote();
        for (final Map.Entry<K, ExpungingEntry> entry : this.read.entrySet()) {
            final Object value;
            if ((value = entry.getValue().get()) != null) {
                action.accept(entry.getKey(), value);
            }
        }
    }
    
    @Override
    public void replaceAll(final BiFunction function) {
        Objects.requireNonNull(function, "function");
        this.promote();
        for (final Map.Entry<K, ExpungingEntry> entry : this.read.entrySet()) {
            final ExpungingEntry expungingEntry;
            final Object value;
            if ((value = (expungingEntry = entry.getValue()).get()) != null) {
                expungingEntry.tryReplace(function.apply(entry.getKey(), value));
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
    public Set entrySet() {
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
        for (final Map.Entry<K, ExpungingEntry> entry : this.read.entrySet()) {
            if (!entry.getValue().tryExpunge()) {
                this.dirty.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    static void access$000(final SyncMapImpl syncMapImpl) {
        syncMapImpl.promote();
    }
    
    static Map access$100(final SyncMapImpl syncMapImpl) {
        return syncMapImpl.read;
    }
    
    final class EntryIterator implements Iterator
    {
        private final Iterator backingIterator;
        private Map.Entry next;
        private Map.Entry current;
        final SyncMapImpl this$0;
        
        EntryIterator(final SyncMapImpl this$0, final Iterator backingIterator) {
            this.this$0 = this$0;
            this.backingIterator = backingIterator;
            this.next = this.nextValue();
        }
        
        @Override
        public boolean hasNext() {
            return this.next != null;
        }
        
        @Override
        public Map.Entry next() {
            final Map.Entry next = this.next;
            this.current = next;
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.next = this.nextValue();
            return this.current;
        }
        
        private Map.Entry nextValue() {
            while (this.backingIterator.hasNext()) {
                final Map.Entry<Object, ExpungingEntry> entry;
                final Object value;
                if ((value = (entry = this.backingIterator.next()).getValue().get()) != null) {
                    return this.this$0.new MapEntry(entry.getKey(), value);
                }
            }
            return null;
        }
        
        @Override
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            this.this$0.remove(this.current.getKey());
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
        
        private void lambda$forEachRemaining$0(final Consumer consumer, final Map.Entry entry) {
            final Object value = entry.getValue().get();
            if (value != null) {
                consumer.accept(this.this$0.new MapEntry(entry.getKey(), value));
            }
        }
    }
    
    final class MapEntry implements Map.Entry
    {
        private final Object key;
        private Object value;
        final SyncMapImpl this$0;
        
        MapEntry(final SyncMapImpl this$0, final Object key, final Object value) {
            this.this$0 = this$0;
            this.key = key;
            this.value = value;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
        
        @Override
        public Object setValue(final Object value) {
            Objects.requireNonNull(value, "value");
            final SyncMapImpl this$0 = this.this$0;
            final Object key = this.key;
            this.value = value;
            return this$0.put(key, value);
        }
        
        @Override
        public String toString() {
            return "SyncMapImpl.MapEntry{key=" + this.getKey() + ", value=" + this.getValue() + "}";
        }
        
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)other;
            return Objects.equals(this.getKey(), entry.getKey()) && Objects.equals(this.getValue(), entry.getValue());
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.getKey(), this.getValue());
        }
    }
    
    final class EntrySetView extends AbstractSet
    {
        final SyncMapImpl this$0;
        
        EntrySetView(final SyncMapImpl this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public boolean contains(final Object entry) {
            if (!(entry instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry2 = (Map.Entry)entry;
            final Object value = this.this$0.get(entry2.getKey());
            return value != null && Objects.equals(value, entry2.getValue());
        }
        
        @Override
        public boolean remove(final Object entry) {
            return entry instanceof Map.Entry && this.this$0.remove(((Map.Entry)entry).getKey()) != null;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public Iterator iterator() {
            SyncMapImpl.access$000(this.this$0);
            return this.this$0.new EntryIterator(SyncMapImpl.access$100(this.this$0).entrySet().iterator());
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
        public InsertionResult computeIfAbsent(final Object key, final Function function) {
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
        public InsertionResult computeIfPresent(final Object key, final BiFunction remappingFunction) {
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
        public InsertionResult compute(final Object key, final BiFunction remappingFunction) {
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
        public boolean tryUnexpungeAndCompute(final Object key, final Function function) {
            return this.value == ExpungingEntryImpl.EXPUNGED && ExpungingEntryImpl.UPDATER.compareAndSet(this, ExpungingEntryImpl.EXPUNGED, function.apply(key));
        }
        
        @Override
        public boolean tryUnexpungeAndCompute(final Object key, final BiFunction remappingFunction) {
            return this.value == ExpungingEntryImpl.EXPUNGED && ExpungingEntryImpl.UPDATER.compareAndSet(this, ExpungingEntryImpl.EXPUNGED, remappingFunction.apply(key, null));
        }
        
        static {
            UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
            EXPUNGED = new Object();
        }
    }
}
