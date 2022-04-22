package com.google.common.collect;

import java.util.logging.*;
import javax.annotation.*;
import javax.annotation.concurrent.*;
import com.google.common.annotations.*;
import com.google.common.primitives.*;
import com.google.common.base.*;
import java.io.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;
import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.*;

class MapMakerInternalMap extends AbstractMap implements ConcurrentMap, Serializable
{
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_THRESHOLD = 63;
    static final int DRAIN_MAX = 16;
    static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
    private static final Logger logger;
    final transient int segmentMask;
    final transient int segmentShift;
    final transient Segment[] segments;
    final int concurrencyLevel;
    final Equivalence keyEquivalence;
    final Equivalence valueEquivalence;
    final Strength keyStrength;
    final Strength valueStrength;
    final int maximumSize;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final Queue removalNotificationQueue;
    final MapMaker.RemovalListener removalListener;
    final transient EntryFactory entryFactory;
    final Ticker ticker;
    static final ValueReference UNSET;
    static final Queue DISCARDING_QUEUE;
    transient Set keySet;
    transient Collection values;
    transient Set entrySet;
    private static final long serialVersionUID = 5L;
    
    MapMakerInternalMap(final MapMaker mapMaker) {
        this.concurrencyLevel = Math.min(mapMaker.getConcurrencyLevel(), 65536);
        this.keyStrength = mapMaker.getKeyStrength();
        this.valueStrength = mapMaker.getValueStrength();
        this.keyEquivalence = mapMaker.getKeyEquivalence();
        this.valueEquivalence = this.valueStrength.defaultEquivalence();
        this.maximumSize = mapMaker.maximumSize;
        this.expireAfterAccessNanos = mapMaker.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = mapMaker.getExpireAfterWriteNanos();
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, this.expires(), this.evictsBySize());
        this.ticker = mapMaker.getTicker();
        this.removalListener = mapMaker.getRemovalListener();
        this.removalNotificationQueue = ((this.removalListener == GenericMapMaker.NullListener.INSTANCE) ? discardingQueue() : new ConcurrentLinkedQueue());
        int n = Math.min(mapMaker.getInitialCapacity(), 1073741824);
        if (this.evictsBySize()) {
            n = Math.min(n, this.maximumSize);
        }
        while (1 < this.concurrencyLevel && (!this.evictsBySize() || 2 <= this.maximumSize)) {
            int n2 = 0;
            ++n2;
        }
        this.segmentShift = 32;
        this.segmentMask = 0;
        this.segments = this.newSegmentArray(1);
        int n3 = n / 1;
        if (n3 * 1 < n) {
            ++n3;
        }
        while (1 < n3) {}
        if (this.evictsBySize()) {
            int n4 = this.maximumSize / 1 + 1;
            final int n5 = this.maximumSize % 1;
            while (0 < this.segments.length) {
                if (n5 == 0) {
                    --n4;
                }
                this.segments[0] = this.createSegment(1, 0);
                int n6 = 0;
                ++n6;
            }
        }
        else {
            while (0 < this.segments.length) {
                this.segments[0] = this.createSegment(1, -1);
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    boolean evictsBySize() {
        return this.maximumSize != -1;
    }
    
    boolean expires() {
        return this.expiresAfterWrite() || this.expiresAfterAccess();
    }
    
    boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0L;
    }
    
    boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0L;
    }
    
    boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }
    
    boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }
    
    static ValueReference unset() {
        return MapMakerInternalMap.UNSET;
    }
    
    static ReferenceEntry nullEntry() {
        return NullEntry.INSTANCE;
    }
    
    static Queue discardingQueue() {
        return MapMakerInternalMap.DISCARDING_QUEUE;
    }
    
    static int rehash(int n) {
        n += (n << 15 ^ 0xFFFFCD7D);
        n ^= n >>> 10;
        n += n << 3;
        n ^= n >>> 6;
        n += (n << 2) + (n << 14);
        return n ^ n >>> 16;
    }
    
    @GuardedBy("Segment.this")
    @VisibleForTesting
    ReferenceEntry newEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
        return this.segmentFor(n).newEntry(o, n, referenceEntry);
    }
    
    @GuardedBy("Segment.this")
    @VisibleForTesting
    ReferenceEntry copyEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
        return this.segmentFor(referenceEntry.getHash()).copyEntry(referenceEntry, referenceEntry2);
    }
    
    @GuardedBy("Segment.this")
    @VisibleForTesting
    ValueReference newValueReference(final ReferenceEntry referenceEntry, final Object o) {
        return this.valueStrength.referenceValue(this.segmentFor(referenceEntry.getHash()), referenceEntry, o);
    }
    
    int hash(final Object o) {
        return rehash(this.keyEquivalence.hash(o));
    }
    
    void reclaimValue(final ValueReference valueReference) {
        final ReferenceEntry entry = valueReference.getEntry();
        final int hash = entry.getHash();
        this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }
    
    void reclaimKey(final ReferenceEntry referenceEntry) {
        final int hash = referenceEntry.getHash();
        this.segmentFor(hash).reclaimKey(referenceEntry, hash);
    }
    
    @VisibleForTesting
    boolean isLive(final ReferenceEntry referenceEntry) {
        return this.segmentFor(referenceEntry.getHash()).getLiveValue(referenceEntry) != null;
    }
    
    Segment segmentFor(final int n) {
        return this.segments[n >>> this.segmentShift & this.segmentMask];
    }
    
    Segment createSegment(final int n, final int n2) {
        return new Segment(this, n, n2);
    }
    
    Object getLiveValue(final ReferenceEntry referenceEntry) {
        if (referenceEntry.getKey() == null) {
            return null;
        }
        final Object value = referenceEntry.getValueReference().get();
        if (value == null) {
            return null;
        }
        if (this.expires() && this.isExpired(referenceEntry)) {
            return null;
        }
        return value;
    }
    
    boolean isExpired(final ReferenceEntry referenceEntry) {
        return this.isExpired(referenceEntry, this.ticker.read());
    }
    
    boolean isExpired(final ReferenceEntry referenceEntry, final long n) {
        return n - referenceEntry.getExpirationTime() > 0L;
    }
    
    @GuardedBy("Segment.this")
    static void connectExpirables(final ReferenceEntry previousExpirable, final ReferenceEntry nextExpirable) {
        previousExpirable.setNextExpirable(nextExpirable);
        nextExpirable.setPreviousExpirable(previousExpirable);
    }
    
    @GuardedBy("Segment.this")
    static void nullifyExpirable(final ReferenceEntry referenceEntry) {
        final ReferenceEntry nullEntry = nullEntry();
        referenceEntry.setNextExpirable(nullEntry);
        referenceEntry.setPreviousExpirable(nullEntry);
    }
    
    void processPendingNotifications() {
        MapMaker.RemovalNotification removalNotification;
        while ((removalNotification = this.removalNotificationQueue.poll()) != null) {
            this.removalListener.onRemoval(removalNotification);
        }
    }
    
    @GuardedBy("Segment.this")
    static void connectEvictables(final ReferenceEntry previousEvictable, final ReferenceEntry nextEvictable) {
        previousEvictable.setNextEvictable(nextEvictable);
        nextEvictable.setPreviousEvictable(previousEvictable);
    }
    
    @GuardedBy("Segment.this")
    static void nullifyEvictable(final ReferenceEntry referenceEntry) {
        final ReferenceEntry nullEntry = nullEntry();
        referenceEntry.setNextEvictable(nullEntry);
        referenceEntry.setPreviousEvictable(nullEntry);
    }
    
    final Segment[] newSegmentArray(final int n) {
        return new Segment[n];
    }
    
    @Override
    public boolean isEmpty() {
        long n = 0L;
        final Segment[] segments = this.segments;
        int n2 = 0;
        while (0 < segments.length) {
            if (segments[0].count != 0) {
                return false;
            }
            n += segments[0].modCount;
            ++n2;
        }
        if (n != 0L) {
            while (0 < segments.length) {
                if (segments[0].count != 0) {
                    return false;
                }
                n -= segments[0].modCount;
                ++n2;
            }
            if (n != 0L) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int size() {
        final Segment[] segments = this.segments;
        long n = 0L;
        while (0 < segments.length) {
            n += segments[0].count;
            int n2 = 0;
            ++n2;
        }
        return Ints.saturatedCast(n);
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).get(o, hash);
    }
    
    ReferenceEntry getEntry(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).getEntry(o, hash);
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        if (o == null) {
            return false;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).containsKey(o, hash);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        if (o == null) {
            return false;
        }
        final Segment[] segments = this.segments;
        long n = -1L;
        while (0 < 3) {
            long n2 = 0L;
            final Segment[] array = segments;
            while (0 < array.length) {
                final Segment segment = array[0];
                final int count = segment.count;
                final AtomicReferenceArray table = segment.table;
                while (0 < table.length()) {
                    for (ReferenceEntry next = table.get(0); next != null; next = next.getNext()) {
                        final Object liveValue = segment.getLiveValue(next);
                        if (liveValue != null && this.valueEquivalence.equivalent(o, liveValue)) {
                            return true;
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
                n2 += segment.modCount;
                int n4 = 0;
                ++n4;
            }
            if (n2 == n) {
                break;
            }
            n = n2;
            int n5 = 0;
            ++n5;
        }
        return false;
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        final int hash = this.hash(o);
        return this.segmentFor(hash).put(o, hash, o2, false);
    }
    
    @Override
    public Object putIfAbsent(final Object o, final Object o2) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        final int hash = this.hash(o);
        return this.segmentFor(hash).put(o, hash, o2, true);
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public Object remove(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).remove(o, hash);
    }
    
    @Override
    public boolean remove(@Nullable final Object o, @Nullable final Object o2) {
        if (o == null || o2 == null) {
            return false;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).remove(o, hash, o2);
    }
    
    @Override
    public boolean replace(final Object o, @Nullable final Object o2, final Object o3) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o3);
        if (o2 == null) {
            return false;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).replace(o, hash, o2, o3);
    }
    
    @Override
    public Object replace(final Object o, final Object o2) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(o2);
        final int hash = this.hash(o);
        return this.segmentFor(hash).replace(o, hash, o2);
    }
    
    @Override
    public void clear() {
        final Segment[] segments = this.segments;
        while (0 < segments.length) {
            segments[0].clear();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Set keySet() {
        final Set keySet = this.keySet;
        return (keySet != null) ? keySet : (this.keySet = new KeySet());
    }
    
    @Override
    public Collection values() {
        final Collection values = this.values;
        return (values != null) ? values : (this.values = new Values());
    }
    
    @Override
    public Set entrySet() {
        final Set entrySet = this.entrySet;
        return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet());
    }
    
    Object writeReplace() {
        return new SerializationProxy(this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this);
    }
    
    static {
        logger = Logger.getLogger(MapMakerInternalMap.class.getName());
        UNSET = new ValueReference() {
            @Override
            public Object get() {
                return null;
            }
            
            @Override
            public ReferenceEntry getEntry() {
                return null;
            }
            
            @Override
            public ValueReference copyFor(final ReferenceQueue referenceQueue, @Nullable final Object o, final ReferenceEntry referenceEntry) {
                return this;
            }
            
            @Override
            public boolean isComputingReference() {
                return false;
            }
            
            @Override
            public Object waitForValue() {
                return null;
            }
            
            @Override
            public void clear(final ValueReference valueReference) {
            }
        };
        DISCARDING_QUEUE = new AbstractQueue() {
            @Override
            public boolean offer(final Object o) {
                return true;
            }
            
            @Override
            public Object peek() {
                return null;
            }
            
            @Override
            public Object poll() {
                return null;
            }
            
            @Override
            public int size() {
                return 0;
            }
            
            @Override
            public Iterator iterator() {
                return Iterators.emptyIterator();
            }
        };
    }
    
    private static final class SerializationProxy extends AbstractSerializationProxy
    {
        private static final long serialVersionUID = 3L;
        
        SerializationProxy(final Strength strength, final Strength strength2, final Equivalence equivalence, final Equivalence equivalence2, final long n, final long n2, final int n3, final int n4, final MapMaker.RemovalListener removalListener, final ConcurrentMap concurrentMap) {
            super(strength, strength2, equivalence, equivalence2, n, n2, n3, n4, removalListener, concurrentMap);
        }
        
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            this.writeMapTo(objectOutputStream);
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.delegate = this.readMapMaker(objectInputStream).makeMap();
            this.readEntries(objectInputStream);
        }
        
        private Object readResolve() {
            return this.delegate;
        }
    }
    
    enum Strength
    {
        STRONG {
            @Override
            ValueReference referenceValue(final Segment segment, final ReferenceEntry referenceEntry, final Object o) {
                return new StrongValueReference(o);
            }
            
            @Override
            Equivalence defaultEquivalence() {
                return Equivalence.equals();
            }
        }, 
        SOFT {
            @Override
            ValueReference referenceValue(final Segment segment, final ReferenceEntry referenceEntry, final Object o) {
                return new SoftValueReference(segment.valueReferenceQueue, o, referenceEntry);
            }
            
            @Override
            Equivalence defaultEquivalence() {
                return Equivalence.identity();
            }
        }, 
        WEAK {
            @Override
            ValueReference referenceValue(final Segment segment, final ReferenceEntry referenceEntry, final Object o) {
                return new WeakValueReference(segment.valueReferenceQueue, o, referenceEntry);
            }
            
            @Override
            Equivalence defaultEquivalence() {
                return Equivalence.identity();
            }
        };
        
        private static final Strength[] $VALUES;
        
        private Strength(final String s, final int n) {
        }
        
        abstract ValueReference referenceValue(final Segment p0, final ReferenceEntry p1, final Object p2);
        
        abstract Equivalence defaultEquivalence();
        
        Strength(final String s, final int n, final MapMakerInternalMap$1 valueReference) {
            this(s, n);
        }
        
        static {
            $VALUES = new Strength[] { Strength.STRONG, Strength.SOFT, Strength.WEAK };
        }
    }
    
    static class Segment extends ReentrantLock
    {
        final MapMakerInternalMap map;
        int count;
        int modCount;
        int threshold;
        AtomicReferenceArray table;
        final int maxSegmentSize;
        final ReferenceQueue keyReferenceQueue;
        final ReferenceQueue valueReferenceQueue;
        final Queue recencyQueue;
        final AtomicInteger readCount;
        @GuardedBy("Segment.this")
        final Queue evictionQueue;
        @GuardedBy("Segment.this")
        final Queue expirationQueue;
        
        Segment(final MapMakerInternalMap map, final int n, final int maxSegmentSize) {
            this.readCount = new AtomicInteger();
            this.map = map;
            this.maxSegmentSize = maxSegmentSize;
            this.initTable(this.newEntryArray(n));
            this.keyReferenceQueue = (map.usesKeyReferences() ? new ReferenceQueue() : null);
            this.valueReferenceQueue = (map.usesValueReferences() ? new ReferenceQueue() : null);
            this.recencyQueue = ((map.evictsBySize() || map.expiresAfterAccess()) ? new ConcurrentLinkedQueue() : MapMakerInternalMap.discardingQueue());
            this.evictionQueue = (map.evictsBySize() ? new EvictionQueue() : MapMakerInternalMap.discardingQueue());
            this.expirationQueue = (map.expires() ? new ExpirationQueue() : MapMakerInternalMap.discardingQueue());
        }
        
        AtomicReferenceArray newEntryArray(final int n) {
            return new AtomicReferenceArray(n);
        }
        
        void initTable(final AtomicReferenceArray table) {
            this.threshold = table.length() * 3 / 4;
            if (this.threshold == this.maxSegmentSize) {
                ++this.threshold;
            }
            this.table = table;
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry newEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            return this.map.entryFactory.newEntry(this, o, n, referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry copyEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            final ValueReference valueReference = referenceEntry.getValueReference();
            final Object value = valueReference.get();
            if (value == null && !valueReference.isComputingReference()) {
                return null;
            }
            final ReferenceEntry copyEntry = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            copyEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, copyEntry));
            return copyEntry;
        }
        
        @GuardedBy("Segment.this")
        void setValue(final ReferenceEntry referenceEntry, final Object o) {
            referenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, referenceEntry, o));
            this.recordWrite(referenceEntry);
        }
        
        void tryDrainReferenceQueues() {
            if (this.tryLock()) {
                this.drainReferenceQueues();
                this.unlock();
            }
        }
        
        @GuardedBy("Segment.this")
        void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.drainValueReferenceQueue();
            }
        }
        
        @GuardedBy("Segment.this")
        void drainKeyReferenceQueue() {
            Reference poll;
            while ((poll = this.keyReferenceQueue.poll()) != null) {
                this.map.reclaimKey((ReferenceEntry)poll);
                int n = 0;
                ++n;
                if (0 == 16) {
                    break;
                }
            }
        }
        
        @GuardedBy("Segment.this")
        void drainValueReferenceQueue() {
            Reference poll;
            while ((poll = this.valueReferenceQueue.poll()) != null) {
                this.map.reclaimValue((ValueReference)poll);
                int n = 0;
                ++n;
                if (0 == 16) {
                    break;
                }
            }
        }
        
        void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.clearValueReferenceQueue();
            }
        }
        
        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {}
        }
        
        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {}
        }
        
        void recordRead(final ReferenceEntry referenceEntry) {
            if (this.map.expiresAfterAccess()) {
                this.recordExpirationTime(referenceEntry, this.map.expireAfterAccessNanos);
            }
            this.recencyQueue.add(referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        void recordLockedRead(final ReferenceEntry referenceEntry) {
            this.evictionQueue.add(referenceEntry);
            if (this.map.expiresAfterAccess()) {
                this.recordExpirationTime(referenceEntry, this.map.expireAfterAccessNanos);
                this.expirationQueue.add(referenceEntry);
            }
        }
        
        @GuardedBy("Segment.this")
        void recordWrite(final ReferenceEntry referenceEntry) {
            this.drainRecencyQueue();
            this.evictionQueue.add(referenceEntry);
            if (this.map.expires()) {
                this.recordExpirationTime(referenceEntry, this.map.expiresAfterAccess() ? this.map.expireAfterAccessNanos : this.map.expireAfterWriteNanos);
                this.expirationQueue.add(referenceEntry);
            }
        }
        
        @GuardedBy("Segment.this")
        void drainRecencyQueue() {
            ReferenceEntry referenceEntry;
            while ((referenceEntry = this.recencyQueue.poll()) != null) {
                if (this.evictionQueue.contains(referenceEntry)) {
                    this.evictionQueue.add(referenceEntry);
                }
                if (this.map.expiresAfterAccess() && this.expirationQueue.contains(referenceEntry)) {
                    this.expirationQueue.add(referenceEntry);
                }
            }
        }
        
        void recordExpirationTime(final ReferenceEntry referenceEntry, final long n) {
            referenceEntry.setExpirationTime(this.map.ticker.read() + n);
        }
        
        void tryExpireEntries() {
            if (this.tryLock()) {
                this.expireEntries();
                this.unlock();
            }
        }
        
        @GuardedBy("Segment.this")
        void expireEntries() {
            this.drainRecencyQueue();
            if (this.expirationQueue.isEmpty()) {
                return;
            }
            final long read = this.map.ticker.read();
            ReferenceEntry referenceEntry;
            while ((referenceEntry = this.expirationQueue.peek()) != null && this.map.isExpired(referenceEntry, read)) {
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), MapMaker.RemovalCause.EXPIRED)) {
                    throw new AssertionError();
                }
            }
        }
        
        void enqueueNotification(final ReferenceEntry referenceEntry, final MapMaker.RemovalCause removalCause) {
            this.enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference().get(), removalCause);
        }
        
        void enqueueNotification(@Nullable final Object o, final int n, @Nullable final Object o2, final MapMaker.RemovalCause removalCause) {
            if (this.map.removalNotificationQueue != MapMakerInternalMap.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(new MapMaker.RemovalNotification(o, o2, removalCause));
            }
        }
        
        @GuardedBy("Segment.this")
        boolean evictEntries() {
            if (!this.map.evictsBySize() || this.count < this.maxSegmentSize) {
                return false;
            }
            this.drainRecencyQueue();
            final ReferenceEntry referenceEntry = this.evictionQueue.remove();
            if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), MapMaker.RemovalCause.SIZE)) {
                throw new AssertionError();
            }
            return true;
        }
        
        ReferenceEntry getFirst(final int n) {
            final AtomicReferenceArray table = this.table;
            return table.get(n & table.length() - 1);
        }
        
        ReferenceEntry getEntry(final Object o, final int n) {
            if (this.count != 0) {
                for (ReferenceEntry referenceEntry = this.getFirst(n); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    if (referenceEntry.getHash() == n) {
                        final Object key = referenceEntry.getKey();
                        if (key == null) {
                            this.tryDrainReferenceQueues();
                        }
                        else if (this.map.keyEquivalence.equivalent(o, key)) {
                            return referenceEntry;
                        }
                    }
                }
            }
            return null;
        }
        
        ReferenceEntry getLiveEntry(final Object o, final int n) {
            final ReferenceEntry entry = this.getEntry(o, n);
            if (entry == null) {
                return null;
            }
            if (this.map.expires() && this.map.isExpired(entry)) {
                this.tryExpireEntries();
                return null;
            }
            return entry;
        }
        
        Object get(final Object o, final int n) {
            final ReferenceEntry liveEntry = this.getLiveEntry(o, n);
            if (liveEntry == null) {
                final Object o2 = null;
                this.postReadCleanup();
                return o2;
            }
            final Object value = liveEntry.getValueReference().get();
            if (value != null) {
                this.recordRead(liveEntry);
            }
            else {
                this.tryDrainReferenceQueues();
            }
            final Object o3 = value;
            this.postReadCleanup();
            return o3;
        }
        
        boolean containsKey(final Object o, final int n) {
            if (this.count == 0) {
                this.postReadCleanup();
                return false;
            }
            final ReferenceEntry liveEntry = this.getLiveEntry(o, n);
            if (liveEntry == null) {
                this.postReadCleanup();
                return false;
            }
            final boolean b = liveEntry.getValueReference().get() != null;
            this.postReadCleanup();
            return false;
        }
        
        @VisibleForTesting
        boolean containsValue(final Object o) {
            if (this.count != 0) {
                final AtomicReferenceArray table = this.table;
                while (0 < table.length()) {
                    for (ReferenceEntry next = table.get(0); next != null; next = next.getNext()) {
                        final Object liveValue = this.getLiveValue(next);
                        if (liveValue != null) {
                            if (this.map.valueEquivalence.equivalent(o, liveValue)) {
                                this.postReadCleanup();
                                return true;
                            }
                        }
                    }
                    int n = 0;
                    ++n;
                }
            }
            this.postReadCleanup();
            return false;
        }
        
        Object put(final Object o, final int n, final Object o2, final boolean b) {
            this.lock();
            this.preWriteCleanup();
            int count = this.count + 1;
            if (count > this.threshold) {
                this.expand();
                count = this.count + 1;
            }
            final AtomicReferenceArray table = this.table;
            final int n2 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n2);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    if (value == null) {
                        ++this.modCount;
                        this.setValue(next, o2);
                        if (!valueReference.isComputingReference()) {
                            this.enqueueNotification(o, n, value, MapMaker.RemovalCause.COLLECTED);
                            count = this.count;
                        }
                        else if (this.evictEntries()) {
                            count = this.count + 1;
                        }
                        this.count = count;
                        final Object o3 = null;
                        this.unlock();
                        this.postWriteCleanup();
                        return o3;
                    }
                    if (b) {
                        this.recordLockedRead(next);
                        final Object o4 = value;
                        this.unlock();
                        this.postWriteCleanup();
                        return o4;
                    }
                    ++this.modCount;
                    this.enqueueNotification(o, n, value, MapMaker.RemovalCause.REPLACED);
                    this.setValue(next, o2);
                    final Object o5 = value;
                    this.unlock();
                    this.postWriteCleanup();
                    return o5;
                }
                else {
                    next = next.getNext();
                }
            }
            ++this.modCount;
            final ReferenceEntry entry = this.newEntry(o, n, referenceEntry);
            this.setValue(entry, o2);
            table.set(n2, entry);
            if (this.evictEntries()) {
                count = this.count + 1;
            }
            this.count = count;
            final Object o6 = null;
            this.unlock();
            this.postWriteCleanup();
            return o6;
        }
        
        @GuardedBy("Segment.this")
        void expand() {
            final AtomicReferenceArray table = this.table;
            final int length = table.length();
            if (length >= 1073741824) {
                return;
            }
            int count = this.count;
            final AtomicReferenceArray entryArray = this.newEntryArray(length << 1);
            this.threshold = entryArray.length() * 3 / 4;
            final int n = entryArray.length() - 1;
            while (0 < length) {
                final ReferenceEntry referenceEntry = table.get(0);
                if (referenceEntry != null) {
                    final ReferenceEntry next = referenceEntry.getNext();
                    final int n2 = referenceEntry.getHash() & n;
                    if (next == null) {
                        entryArray.set(n2, referenceEntry);
                    }
                    else {
                        ReferenceEntry referenceEntry2 = referenceEntry;
                        int n3 = n2;
                        for (ReferenceEntry next2 = next; next2 != null; next2 = next2.getNext()) {
                            final int n4 = next2.getHash() & n;
                            if (n4 != n3) {
                                n3 = n4;
                                referenceEntry2 = next2;
                            }
                        }
                        entryArray.set(n3, referenceEntry2);
                        for (ReferenceEntry next3 = referenceEntry; next3 != referenceEntry2; next3 = next3.getNext()) {
                            final int n5 = next3.getHash() & n;
                            final ReferenceEntry copyEntry = this.copyEntry(next3, entryArray.get(n5));
                            if (copyEntry != null) {
                                entryArray.set(n5, copyEntry);
                            }
                            else {
                                this.removeCollectedEntry(next3);
                                --count;
                            }
                        }
                    }
                }
                int n6 = 0;
                ++n6;
            }
            this.table = entryArray;
            this.count = count;
        }
        
        boolean replace(final Object o, final int n, final Object o2, final Object o3) {
            this.lock();
            this.preWriteCleanup();
            final AtomicReferenceArray table = this.table;
            final int n2 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n2);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    if (value == null) {
                        if (this.isCollected(valueReference)) {
                            final int n3 = this.count - 1;
                            ++this.modCount;
                            this.enqueueNotification(key, n, value, MapMaker.RemovalCause.COLLECTED);
                            final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry, next);
                            final int n4 = this.count - 1;
                            table.set(n2, removeFromChain);
                            this.count = 0;
                        }
                        this.unlock();
                        this.postWriteCleanup();
                        return false;
                    }
                    if (this.map.valueEquivalence.equivalent(o2, value)) {
                        ++this.modCount;
                        this.enqueueNotification(o, n, value, MapMaker.RemovalCause.REPLACED);
                        this.setValue(next, o3);
                        this.unlock();
                        this.postWriteCleanup();
                        return false;
                    }
                    this.recordLockedRead(next);
                    this.unlock();
                    this.postWriteCleanup();
                    return false;
                }
                else {
                    next = next.getNext();
                }
            }
            this.unlock();
            this.postWriteCleanup();
            return false;
        }
        
        Object replace(final Object o, final int n, final Object o2) {
            this.lock();
            this.preWriteCleanup();
            final AtomicReferenceArray table = this.table;
            final int n2 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n2);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    if (value == null) {
                        if (this.isCollected(valueReference)) {
                            final int n3 = this.count - 1;
                            ++this.modCount;
                            this.enqueueNotification(key, n, value, MapMaker.RemovalCause.COLLECTED);
                            final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry, next);
                            final int count = this.count - 1;
                            table.set(n2, removeFromChain);
                            this.count = count;
                        }
                        final Object o3 = null;
                        this.unlock();
                        this.postWriteCleanup();
                        return o3;
                    }
                    ++this.modCount;
                    this.enqueueNotification(o, n, value, MapMaker.RemovalCause.REPLACED);
                    this.setValue(next, o2);
                    final Object o4 = value;
                    this.unlock();
                    this.postWriteCleanup();
                    return o4;
                }
                else {
                    next = next.getNext();
                }
            }
            final Object o5 = null;
            this.unlock();
            this.postWriteCleanup();
            return o5;
        }
        
        Object remove(final Object o, final int n) {
            this.lock();
            this.preWriteCleanup();
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            for (ReferenceEntry referenceEntry = next = table.get(n3); next != null; next = next.getNext()) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    MapMaker.RemovalCause removalCause;
                    if (value != null) {
                        removalCause = MapMaker.RemovalCause.EXPLICIT;
                    }
                    else {
                        if (!this.isCollected(valueReference)) {
                            final Object o2 = null;
                            this.unlock();
                            this.postWriteCleanup();
                            return o2;
                        }
                        removalCause = MapMaker.RemovalCause.COLLECTED;
                    }
                    ++this.modCount;
                    this.enqueueNotification(key, n, value, removalCause);
                    final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry, next);
                    final int count = this.count - 1;
                    table.set(n3, removeFromChain);
                    this.count = count;
                    final Object o3 = value;
                    this.unlock();
                    this.postWriteCleanup();
                    return o3;
                }
            }
            final Object o4 = null;
            this.unlock();
            this.postWriteCleanup();
            return o4;
        }
        
        boolean remove(final Object o, final int n, final Object o2) {
            this.lock();
            this.preWriteCleanup();
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            for (ReferenceEntry referenceEntry = next = table.get(n3); next != null; next = next.getNext()) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    MapMaker.RemovalCause removalCause;
                    if (this.map.valueEquivalence.equivalent(o2, value)) {
                        removalCause = MapMaker.RemovalCause.EXPLICIT;
                    }
                    else {
                        if (!this.isCollected(valueReference)) {
                            this.unlock();
                            this.postWriteCleanup();
                            return false;
                        }
                        removalCause = MapMaker.RemovalCause.COLLECTED;
                    }
                    ++this.modCount;
                    this.enqueueNotification(key, n, value, removalCause);
                    final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry, next);
                    final int count = this.count - 1;
                    table.set(n3, removeFromChain);
                    this.count = count;
                    final boolean b = removalCause == MapMaker.RemovalCause.EXPLICIT;
                    this.unlock();
                    this.postWriteCleanup();
                    return b;
                }
            }
            this.unlock();
            this.postWriteCleanup();
            return false;
        }
        
        void clear() {
            if (this.count != 0) {
                this.lock();
                final AtomicReferenceArray table = this.table;
                int n = 0;
                if (this.map.removalNotificationQueue != MapMakerInternalMap.DISCARDING_QUEUE) {
                    while (0 < table.length()) {
                        for (ReferenceEntry next = table.get(0); next != null; next = next.getNext()) {
                            if (!next.getValueReference().isComputingReference()) {
                                this.enqueueNotification(next, MapMaker.RemovalCause.EXPLICIT);
                            }
                        }
                        ++n;
                    }
                }
                while (0 < table.length()) {
                    table.set(0, null);
                    ++n;
                }
                this.clearReferenceQueues();
                this.evictionQueue.clear();
                this.expirationQueue.clear();
                this.readCount.set(0);
                ++this.modCount;
                this.count = 0;
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry removeFromChain(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            this.evictionQueue.remove(referenceEntry2);
            this.expirationQueue.remove(referenceEntry2);
            int count = this.count;
            ReferenceEntry next = referenceEntry2.getNext();
            for (ReferenceEntry next2 = referenceEntry; next2 != referenceEntry2; next2 = next2.getNext()) {
                final ReferenceEntry copyEntry = this.copyEntry(next2, next);
                if (copyEntry != null) {
                    next = copyEntry;
                }
                else {
                    this.removeCollectedEntry(next2);
                    --count;
                }
            }
            this.count = count;
            return next;
        }
        
        void removeCollectedEntry(final ReferenceEntry referenceEntry) {
            this.enqueueNotification(referenceEntry, MapMaker.RemovalCause.COLLECTED);
            this.evictionQueue.remove(referenceEntry);
            this.expirationQueue.remove(referenceEntry);
        }
        
        boolean reclaimKey(final ReferenceEntry referenceEntry, final int n) {
            this.lock();
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            for (ReferenceEntry referenceEntry2 = next = table.get(n3); next != null; next = next.getNext()) {
                if (next == referenceEntry) {
                    ++this.modCount;
                    this.enqueueNotification(next.getKey(), n, next.getValueReference().get(), MapMaker.RemovalCause.COLLECTED);
                    final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry2, next);
                    final int count = this.count - 1;
                    table.set(n3, removeFromChain);
                    this.count = count;
                    this.unlock();
                    this.postWriteCleanup();
                    return true;
                }
            }
            this.unlock();
            this.postWriteCleanup();
            return false;
        }
        
        boolean reclaimValue(final Object o, final int n, final ValueReference valueReference) {
            this.lock();
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n3);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    if (next.getValueReference() == valueReference) {
                        ++this.modCount;
                        this.enqueueNotification(o, n, valueReference.get(), MapMaker.RemovalCause.COLLECTED);
                        final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry, next);
                        final int count = this.count - 1;
                        table.set(n3, removeFromChain);
                        this.count = count;
                        this.unlock();
                        if (!this.isHeldByCurrentThread()) {
                            this.postWriteCleanup();
                        }
                        return true;
                    }
                    this.unlock();
                    if (!this.isHeldByCurrentThread()) {
                        this.postWriteCleanup();
                    }
                    return false;
                }
                else {
                    next = next.getNext();
                }
            }
            this.unlock();
            if (!this.isHeldByCurrentThread()) {
                this.postWriteCleanup();
            }
            return false;
        }
        
        boolean clearValue(final Object o, final int n, final ValueReference valueReference) {
            this.lock();
            final AtomicReferenceArray table = this.table;
            final int n2 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n2);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    if (next.getValueReference() == valueReference) {
                        table.set(n2, this.removeFromChain(referenceEntry, next));
                        this.unlock();
                        this.postWriteCleanup();
                        return true;
                    }
                    this.unlock();
                    this.postWriteCleanup();
                    return false;
                }
                else {
                    next = next.getNext();
                }
            }
            this.unlock();
            this.postWriteCleanup();
            return false;
        }
        
        @GuardedBy("Segment.this")
        boolean removeEntry(final ReferenceEntry referenceEntry, final int n, final MapMaker.RemovalCause removalCause) {
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            for (ReferenceEntry referenceEntry2 = next = table.get(n3); next != null; next = next.getNext()) {
                if (next == referenceEntry) {
                    ++this.modCount;
                    this.enqueueNotification(next.getKey(), n, next.getValueReference().get(), removalCause);
                    final ReferenceEntry removeFromChain = this.removeFromChain(referenceEntry2, next);
                    final int count = this.count - 1;
                    table.set(n3, removeFromChain);
                    this.count = count;
                    return true;
                }
            }
            return false;
        }
        
        boolean isCollected(final ValueReference valueReference) {
            return !valueReference.isComputingReference() && valueReference.get() == null;
        }
        
        Object getLiveValue(final ReferenceEntry referenceEntry) {
            if (referenceEntry.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            final Object value = referenceEntry.getValueReference().get();
            if (value == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if (this.map.expires() && this.map.isExpired(referenceEntry)) {
                this.tryExpireEntries();
                return null;
            }
            return value;
        }
        
        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 0x3F) == 0x0) {
                this.runCleanup();
            }
        }
        
        @GuardedBy("Segment.this")
        void preWriteCleanup() {
            this.runLockedCleanup();
        }
        
        void postWriteCleanup() {
            this.runUnlockedCleanup();
        }
        
        void runCleanup() {
            this.runLockedCleanup();
            this.runUnlockedCleanup();
        }
        
        void runLockedCleanup() {
            if (this.tryLock()) {
                this.drainReferenceQueues();
                this.expireEntries();
                this.readCount.set(0);
                this.unlock();
            }
        }
        
        void runUnlockedCleanup() {
            if (!this.isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }
    }
    
    interface ReferenceEntry
    {
        ValueReference getValueReference();
        
        void setValueReference(final ValueReference p0);
        
        ReferenceEntry getNext();
        
        int getHash();
        
        Object getKey();
        
        long getExpirationTime();
        
        void setExpirationTime(final long p0);
        
        ReferenceEntry getNextExpirable();
        
        void setNextExpirable(final ReferenceEntry p0);
        
        ReferenceEntry getPreviousExpirable();
        
        void setPreviousExpirable(final ReferenceEntry p0);
        
        ReferenceEntry getNextEvictable();
        
        void setNextEvictable(final ReferenceEntry p0);
        
        ReferenceEntry getPreviousEvictable();
        
        void setPreviousEvictable(final ReferenceEntry p0);
    }
    
    interface ValueReference
    {
        Object get();
        
        Object waitForValue() throws ExecutionException;
        
        ReferenceEntry getEntry();
        
        ValueReference copyFor(final ReferenceQueue p0, @Nullable final Object p1, final ReferenceEntry p2);
        
        void clear(@Nullable final ValueReference p0);
        
        boolean isComputingReference();
    }
    
    static final class EvictionQueue extends AbstractQueue
    {
        final ReferenceEntry head;
        
        EvictionQueue() {
            this.head = new AbstractReferenceEntry() {
                ReferenceEntry nextEvictable = this;
                ReferenceEntry previousEvictable = this;
                final EvictionQueue this$0;
                
                @Override
                public ReferenceEntry getNextEvictable() {
                    return this.nextEvictable;
                }
                
                @Override
                public void setNextEvictable(final ReferenceEntry nextEvictable) {
                    this.nextEvictable = nextEvictable;
                }
                
                @Override
                public ReferenceEntry getPreviousEvictable() {
                    return this.previousEvictable;
                }
                
                @Override
                public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
                    this.previousEvictable = previousEvictable;
                }
            };
        }
        
        public boolean offer(final ReferenceEntry referenceEntry) {
            MapMakerInternalMap.connectEvictables(referenceEntry.getPreviousEvictable(), referenceEntry.getNextEvictable());
            MapMakerInternalMap.connectEvictables(this.head.getPreviousEvictable(), referenceEntry);
            MapMakerInternalMap.connectEvictables(referenceEntry, this.head);
            return true;
        }
        
        @Override
        public ReferenceEntry peek() {
            final ReferenceEntry nextEvictable = this.head.getNextEvictable();
            return (nextEvictable == this.head) ? null : nextEvictable;
        }
        
        @Override
        public ReferenceEntry poll() {
            final ReferenceEntry nextEvictable = this.head.getNextEvictable();
            if (nextEvictable == this.head) {
                return null;
            }
            this.remove(nextEvictable);
            return nextEvictable;
        }
        
        @Override
        public boolean remove(final Object o) {
            final ReferenceEntry referenceEntry = (ReferenceEntry)o;
            final ReferenceEntry previousEvictable = referenceEntry.getPreviousEvictable();
            final ReferenceEntry nextEvictable = referenceEntry.getNextEvictable();
            MapMakerInternalMap.connectEvictables(previousEvictable, nextEvictable);
            MapMakerInternalMap.nullifyEvictable(referenceEntry);
            return nextEvictable != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean contains(final Object o) {
            return ((ReferenceEntry)o).getNextEvictable() != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean isEmpty() {
            return this.head.getNextEvictable() == this.head;
        }
        
        @Override
        public int size() {
            for (ReferenceEntry referenceEntry = this.head.getNextEvictable(); referenceEntry != this.head; referenceEntry = referenceEntry.getNextEvictable()) {
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        @Override
        public void clear() {
            ReferenceEntry nextEvictable2;
            for (ReferenceEntry nextEvictable = this.head.getNextEvictable(); nextEvictable != this.head; nextEvictable = nextEvictable2) {
                nextEvictable2 = nextEvictable.getNextEvictable();
                MapMakerInternalMap.nullifyEvictable(nextEvictable);
            }
            this.head.setNextEvictable(this.head);
            this.head.setPreviousEvictable(this.head);
        }
        
        @Override
        public Iterator iterator() {
            return new AbstractSequentialIterator(this.peek()) {
                final EvictionQueue this$0;
                
                protected ReferenceEntry computeNext(final ReferenceEntry referenceEntry) {
                    final ReferenceEntry nextEvictable = referenceEntry.getNextEvictable();
                    return (nextEvictable == this.this$0.head) ? null : nextEvictable;
                }
                
                @Override
                protected Object computeNext(final Object o) {
                    return this.computeNext((ReferenceEntry)o);
                }
            };
        }
        
        @Override
        public Object peek() {
            return this.peek();
        }
        
        @Override
        public Object poll() {
            return this.poll();
        }
        
        @Override
        public boolean offer(final Object o) {
            return this.offer((ReferenceEntry)o);
        }
    }
    
    private enum NullEntry implements ReferenceEntry
    {
        INSTANCE("INSTANCE", 0);
        
        private static final NullEntry[] $VALUES;
        
        private NullEntry(final String s, final int n) {
        }
        
        @Override
        public ValueReference getValueReference() {
            return null;
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
        }
        
        @Override
        public ReferenceEntry getNext() {
            return null;
        }
        
        @Override
        public int getHash() {
            return 0;
        }
        
        @Override
        public Object getKey() {
            return null;
        }
        
        @Override
        public long getExpirationTime() {
            return 0L;
        }
        
        @Override
        public void setExpirationTime(final long n) {
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry referenceEntry) {
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry referenceEntry) {
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry referenceEntry) {
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry referenceEntry) {
        }
        
        static {
            $VALUES = new NullEntry[] { NullEntry.INSTANCE };
        }
    }
    
    abstract static class AbstractReferenceEntry implements ReferenceEntry
    {
        @Override
        public ValueReference getValueReference() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNext() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int getHash() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object getKey() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setExpirationTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
    }
    
    static final class ExpirationQueue extends AbstractQueue
    {
        final ReferenceEntry head;
        
        ExpirationQueue() {
            this.head = new AbstractReferenceEntry() {
                ReferenceEntry nextExpirable = this;
                ReferenceEntry previousExpirable = this;
                final ExpirationQueue this$0;
                
                @Override
                public long getExpirationTime() {
                    return Long.MAX_VALUE;
                }
                
                @Override
                public void setExpirationTime(final long n) {
                }
                
                @Override
                public ReferenceEntry getNextExpirable() {
                    return this.nextExpirable;
                }
                
                @Override
                public void setNextExpirable(final ReferenceEntry nextExpirable) {
                    this.nextExpirable = nextExpirable;
                }
                
                @Override
                public ReferenceEntry getPreviousExpirable() {
                    return this.previousExpirable;
                }
                
                @Override
                public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
                    this.previousExpirable = previousExpirable;
                }
            };
        }
        
        public boolean offer(final ReferenceEntry referenceEntry) {
            MapMakerInternalMap.connectExpirables(referenceEntry.getPreviousExpirable(), referenceEntry.getNextExpirable());
            MapMakerInternalMap.connectExpirables(this.head.getPreviousExpirable(), referenceEntry);
            MapMakerInternalMap.connectExpirables(referenceEntry, this.head);
            return true;
        }
        
        @Override
        public ReferenceEntry peek() {
            final ReferenceEntry nextExpirable = this.head.getNextExpirable();
            return (nextExpirable == this.head) ? null : nextExpirable;
        }
        
        @Override
        public ReferenceEntry poll() {
            final ReferenceEntry nextExpirable = this.head.getNextExpirable();
            if (nextExpirable == this.head) {
                return null;
            }
            this.remove(nextExpirable);
            return nextExpirable;
        }
        
        @Override
        public boolean remove(final Object o) {
            final ReferenceEntry referenceEntry = (ReferenceEntry)o;
            final ReferenceEntry previousExpirable = referenceEntry.getPreviousExpirable();
            final ReferenceEntry nextExpirable = referenceEntry.getNextExpirable();
            MapMakerInternalMap.connectExpirables(previousExpirable, nextExpirable);
            MapMakerInternalMap.nullifyExpirable(referenceEntry);
            return nextExpirable != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean contains(final Object o) {
            return ((ReferenceEntry)o).getNextExpirable() != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean isEmpty() {
            return this.head.getNextExpirable() == this.head;
        }
        
        @Override
        public int size() {
            for (ReferenceEntry referenceEntry = this.head.getNextExpirable(); referenceEntry != this.head; referenceEntry = referenceEntry.getNextExpirable()) {
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        @Override
        public void clear() {
            ReferenceEntry nextExpirable2;
            for (ReferenceEntry nextExpirable = this.head.getNextExpirable(); nextExpirable != this.head; nextExpirable = nextExpirable2) {
                nextExpirable2 = nextExpirable.getNextExpirable();
                MapMakerInternalMap.nullifyExpirable(nextExpirable);
            }
            this.head.setNextExpirable(this.head);
            this.head.setPreviousExpirable(this.head);
        }
        
        @Override
        public Iterator iterator() {
            return new AbstractSequentialIterator(this.peek()) {
                final ExpirationQueue this$0;
                
                protected ReferenceEntry computeNext(final ReferenceEntry referenceEntry) {
                    final ReferenceEntry nextExpirable = referenceEntry.getNextExpirable();
                    return (nextExpirable == this.this$0.head) ? null : nextExpirable;
                }
                
                @Override
                protected Object computeNext(final Object o) {
                    return this.computeNext((ReferenceEntry)o);
                }
            };
        }
        
        @Override
        public Object peek() {
            return this.peek();
        }
        
        @Override
        public Object poll() {
            return this.poll();
        }
        
        @Override
        public boolean offer(final Object o) {
            return this.offer((ReferenceEntry)o);
        }
    }
    
    enum EntryFactory
    {
        STRONG {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongEntry(o, n, referenceEntry);
            }
        }, 
        STRONG_EXPIRABLE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongExpirableEntry(o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyExpirableEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        STRONG_EVICTABLE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongEvictableEntry(o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyEvictableEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        STRONG_EXPIRABLE_EVICTABLE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongExpirableEvictableEntry(o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyExpirableEntry(referenceEntry, copyEntry);
                this.copyEvictableEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        WEAK {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
        }, 
        WEAK_EXPIRABLE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakExpirableEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyExpirableEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        WEAK_EVICTABLE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakEvictableEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyEvictableEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        WEAK_EXPIRABLE_EVICTABLE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakExpirableEvictableEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyExpirableEntry(referenceEntry, copyEntry);
                this.copyEvictableEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        };
        
        static final int EXPIRABLE_MASK = 1;
        static final int EVICTABLE_MASK = 2;
        static final EntryFactory[][] factories;
        private static final EntryFactory[] $VALUES;
        
        private EntryFactory(final String s, final int n) {
        }
        
        static EntryFactory getFactory(final Strength strength, final boolean b, final boolean b2) {
            return EntryFactory.factories[strength.ordinal()][(b ? 1 : 0) | (b2 ? 2 : 0)];
        }
        
        abstract ReferenceEntry newEntry(final Segment p0, final Object p1, final int p2, @Nullable final ReferenceEntry p3);
        
        @GuardedBy("Segment.this")
        ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            return this.newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }
        
        @GuardedBy("Segment.this")
        void copyExpirableEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            referenceEntry2.setExpirationTime(referenceEntry.getExpirationTime());
            MapMakerInternalMap.connectExpirables(referenceEntry.getPreviousExpirable(), referenceEntry2);
            MapMakerInternalMap.connectExpirables(referenceEntry2, referenceEntry.getNextExpirable());
            MapMakerInternalMap.nullifyExpirable(referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        void copyEvictableEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            MapMakerInternalMap.connectEvictables(referenceEntry.getPreviousEvictable(), referenceEntry2);
            MapMakerInternalMap.connectEvictables(referenceEntry2, referenceEntry.getNextEvictable());
            MapMakerInternalMap.nullifyEvictable(referenceEntry);
        }
        
        EntryFactory(final String s, final int n, final MapMakerInternalMap$1 valueReference) {
            this(s, n);
        }
        
        static {
            $VALUES = new EntryFactory[] { EntryFactory.STRONG, EntryFactory.STRONG_EXPIRABLE, EntryFactory.STRONG_EVICTABLE, EntryFactory.STRONG_EXPIRABLE_EVICTABLE, EntryFactory.WEAK, EntryFactory.WEAK_EXPIRABLE, EntryFactory.WEAK_EVICTABLE, EntryFactory.WEAK_EXPIRABLE_EVICTABLE };
            factories = new EntryFactory[][] { { EntryFactory.STRONG, EntryFactory.STRONG_EXPIRABLE, EntryFactory.STRONG_EVICTABLE, EntryFactory.STRONG_EXPIRABLE_EVICTABLE }, new EntryFactory[0], { EntryFactory.WEAK, EntryFactory.WEAK_EXPIRABLE, EntryFactory.WEAK_EVICTABLE, EntryFactory.WEAK_EXPIRABLE_EVICTABLE } };
        }
    }
    
    static final class WeakExpirableEvictableEntry extends WeakEntry implements ReferenceEntry
    {
        long time;
        @GuardedBy("Segment.this")
        ReferenceEntry nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousEvictable;
        
        WeakExpirableEvictableEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = MapMakerInternalMap.nullEntry();
            this.previousExpirable = MapMakerInternalMap.nullEntry();
            this.nextEvictable = MapMakerInternalMap.nullEntry();
            this.previousEvictable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public long getExpirationTime() {
            return this.time;
        }
        
        @Override
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this.nextExpirable;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry nextExpirable) {
            this.nextExpirable = nextExpirable;
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
            this.previousExpirable = previousExpirable;
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this.nextEvictable;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry nextEvictable) {
            this.nextEvictable = nextEvictable;
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
            this.previousEvictable = previousEvictable;
        }
    }
    
    static class WeakEntry extends WeakReference implements ReferenceEntry
    {
        final int hash;
        final ReferenceEntry next;
        ValueReference valueReference;
        
        WeakEntry(final ReferenceQueue referenceQueue, final Object o, final int hash, @Nullable final ReferenceEntry next) {
            super(o, referenceQueue);
            this.valueReference = MapMakerInternalMap.unset();
            this.hash = hash;
            this.next = next;
        }
        
        @Override
        public Object getKey() {
            return this.get();
        }
        
        @Override
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setExpirationTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ValueReference getValueReference() {
            return this.valueReference;
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
            this.valueReference.clear(this.valueReference = valueReference);
        }
        
        @Override
        public int getHash() {
            return this.hash;
        }
        
        @Override
        public ReferenceEntry getNext() {
            return this.next;
        }
    }
    
    static final class WeakEvictableEntry extends WeakEntry implements ReferenceEntry
    {
        @GuardedBy("Segment.this")
        ReferenceEntry nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousEvictable;
        
        WeakEvictableEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.nextEvictable = MapMakerInternalMap.nullEntry();
            this.previousEvictable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this.nextEvictable;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry nextEvictable) {
            this.nextEvictable = nextEvictable;
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
            this.previousEvictable = previousEvictable;
        }
    }
    
    static final class WeakExpirableEntry extends WeakEntry implements ReferenceEntry
    {
        long time;
        @GuardedBy("Segment.this")
        ReferenceEntry nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousExpirable;
        
        WeakExpirableEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = MapMakerInternalMap.nullEntry();
            this.previousExpirable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public long getExpirationTime() {
            return this.time;
        }
        
        @Override
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this.nextExpirable;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry nextExpirable) {
            this.nextExpirable = nextExpirable;
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
            this.previousExpirable = previousExpirable;
        }
    }
    
    static final class StrongExpirableEvictableEntry extends StrongEntry implements ReferenceEntry
    {
        long time;
        @GuardedBy("Segment.this")
        ReferenceEntry nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousEvictable;
        
        StrongExpirableEvictableEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(o, n, referenceEntry);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = MapMakerInternalMap.nullEntry();
            this.previousExpirable = MapMakerInternalMap.nullEntry();
            this.nextEvictable = MapMakerInternalMap.nullEntry();
            this.previousEvictable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public long getExpirationTime() {
            return this.time;
        }
        
        @Override
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this.nextExpirable;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry nextExpirable) {
            this.nextExpirable = nextExpirable;
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
            this.previousExpirable = previousExpirable;
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this.nextEvictable;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry nextEvictable) {
            this.nextEvictable = nextEvictable;
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
            this.previousEvictable = previousEvictable;
        }
    }
    
    static class StrongEntry implements ReferenceEntry
    {
        final Object key;
        final int hash;
        final ReferenceEntry next;
        ValueReference valueReference;
        
        StrongEntry(final Object key, final int hash, @Nullable final ReferenceEntry next) {
            this.valueReference = MapMakerInternalMap.unset();
            this.key = key;
            this.hash = hash;
            this.next = next;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setExpirationTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ValueReference getValueReference() {
            return this.valueReference;
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
            this.valueReference.clear(this.valueReference = valueReference);
        }
        
        @Override
        public int getHash() {
            return this.hash;
        }
        
        @Override
        public ReferenceEntry getNext() {
            return this.next;
        }
    }
    
    static final class StrongEvictableEntry extends StrongEntry implements ReferenceEntry
    {
        @GuardedBy("Segment.this")
        ReferenceEntry nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousEvictable;
        
        StrongEvictableEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(o, n, referenceEntry);
            this.nextEvictable = MapMakerInternalMap.nullEntry();
            this.previousEvictable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this.nextEvictable;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry nextEvictable) {
            this.nextEvictable = nextEvictable;
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
            this.previousEvictable = previousEvictable;
        }
    }
    
    static final class StrongExpirableEntry extends StrongEntry implements ReferenceEntry
    {
        long time;
        @GuardedBy("Segment.this")
        ReferenceEntry nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousExpirable;
        
        StrongExpirableEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(o, n, referenceEntry);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = MapMakerInternalMap.nullEntry();
            this.previousExpirable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public long getExpirationTime() {
            return this.time;
        }
        
        @Override
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this.nextExpirable;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry nextExpirable) {
            this.nextExpirable = nextExpirable;
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
            this.previousExpirable = previousExpirable;
        }
    }
    
    static final class WeakValueReference extends WeakReference implements ValueReference
    {
        final ReferenceEntry entry;
        
        WeakValueReference(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry entry) {
            super(o, referenceQueue);
            this.entry = entry;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return this.entry;
        }
        
        @Override
        public void clear(final ValueReference valueReference) {
            this.clear();
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return new WeakValueReference(referenceQueue, o, referenceEntry);
        }
        
        @Override
        public boolean isComputingReference() {
            return false;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
    }
    
    static final class SoftValueReference extends SoftReference implements ValueReference
    {
        final ReferenceEntry entry;
        
        SoftValueReference(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry entry) {
            super(o, referenceQueue);
            this.entry = entry;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return this.entry;
        }
        
        @Override
        public void clear(final ValueReference valueReference) {
            this.clear();
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return new SoftValueReference(referenceQueue, o, referenceEntry);
        }
        
        @Override
        public boolean isComputingReference() {
            return false;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
    }
    
    static final class StrongValueReference implements ValueReference
    {
        final Object referent;
        
        StrongValueReference(final Object referent) {
            this.referent = referent;
        }
        
        @Override
        public Object get() {
            return this.referent;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return null;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return this;
        }
        
        @Override
        public boolean isComputingReference() {
            return false;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
        
        @Override
        public void clear(final ValueReference valueReference) {
        }
    }
    
    abstract static class AbstractSerializationProxy extends ForwardingConcurrentMap implements Serializable
    {
        private static final long serialVersionUID = 3L;
        final Strength keyStrength;
        final Strength valueStrength;
        final Equivalence keyEquivalence;
        final Equivalence valueEquivalence;
        final long expireAfterWriteNanos;
        final long expireAfterAccessNanos;
        final int maximumSize;
        final int concurrencyLevel;
        final MapMaker.RemovalListener removalListener;
        transient ConcurrentMap delegate;
        
        AbstractSerializationProxy(final Strength keyStrength, final Strength valueStrength, final Equivalence keyEquivalence, final Equivalence valueEquivalence, final long expireAfterWriteNanos, final long expireAfterAccessNanos, final int maximumSize, final int concurrencyLevel, final MapMaker.RemovalListener removalListener, final ConcurrentMap delegate) {
            this.keyStrength = keyStrength;
            this.valueStrength = valueStrength;
            this.keyEquivalence = keyEquivalence;
            this.valueEquivalence = valueEquivalence;
            this.expireAfterWriteNanos = expireAfterWriteNanos;
            this.expireAfterAccessNanos = expireAfterAccessNanos;
            this.maximumSize = maximumSize;
            this.concurrencyLevel = concurrencyLevel;
            this.removalListener = removalListener;
            this.delegate = delegate;
        }
        
        @Override
        protected ConcurrentMap delegate() {
            return this.delegate;
        }
        
        void writeMapTo(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeInt(this.delegate.size());
            for (final Map.Entry<Object, V> entry : this.delegate.entrySet()) {
                objectOutputStream.writeObject(entry.getKey());
                objectOutputStream.writeObject(entry.getValue());
            }
            objectOutputStream.writeObject(null);
        }
        
        MapMaker readMapMaker(final ObjectInputStream objectInputStream) throws IOException {
            final MapMaker concurrencyLevel = new MapMaker().initialCapacity(objectInputStream.readInt()).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
            concurrencyLevel.removalListener(this.removalListener);
            if (this.expireAfterWriteNanos > 0L) {
                concurrencyLevel.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
            }
            if (this.expireAfterAccessNanos > 0L) {
                concurrencyLevel.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
            }
            if (this.maximumSize != -1) {
                concurrencyLevel.maximumSize(this.maximumSize);
            }
            return concurrencyLevel;
        }
        
        void readEntries(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            while (true) {
                final Object object = objectInputStream.readObject();
                if (object == null) {
                    break;
                }
                this.delegate.put(object, objectInputStream.readObject());
            }
        }
        
        @Override
        protected Map delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    final class EntrySet extends AbstractSet
    {
        final MapMakerInternalMap this$0;
        
        EntrySet(final MapMakerInternalMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.new EntryIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            if (key == null) {
                return false;
            }
            final Object value = this.this$0.get(key);
            return value != null && this.this$0.valueEquivalence.equivalent(entry.getValue(), value);
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            return key != null && this.this$0.remove(key, entry.getValue());
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
    }
    
    final class EntryIterator extends HashIterator
    {
        final MapMakerInternalMap this$0;
        
        EntryIterator(final MapMakerInternalMap this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Map.Entry next() {
            return this.nextEntry();
        }
        
        @Override
        public Object next() {
            return this.next();
        }
    }
    
    abstract class HashIterator implements Iterator
    {
        int nextSegmentIndex;
        int nextTableIndex;
        Segment currentSegment;
        AtomicReferenceArray currentTable;
        ReferenceEntry nextEntry;
        WriteThroughEntry nextExternal;
        WriteThroughEntry lastReturned;
        final MapMakerInternalMap this$0;
        
        HashIterator(final MapMakerInternalMap this$0) {
            this.this$0 = this$0;
            this.nextSegmentIndex = this$0.segments.length - 1;
            this.nextTableIndex = -1;
            this.advance();
        }
        
        @Override
        public abstract Object next();
        
        final void advance() {
            this.nextExternal = null;
            if (this.nextInChain()) {
                return;
            }
            if (this.nextInTable()) {
                return;
            }
            while (this.nextSegmentIndex >= 0) {
                this.currentSegment = this.this$0.segments[this.nextSegmentIndex--];
                if (this.currentSegment.count != 0) {
                    this.currentTable = this.currentSegment.table;
                    this.nextTableIndex = this.currentTable.length() - 1;
                    if (this.nextInTable()) {
                        return;
                    }
                    continue;
                }
            }
        }
        
        boolean nextInChain() {
            if (this.nextEntry != null) {
                this.nextEntry = this.nextEntry.getNext();
                while (this.nextEntry != null) {
                    if (this.advanceTo(this.nextEntry)) {
                        return true;
                    }
                    this.nextEntry = this.nextEntry.getNext();
                }
            }
            return false;
        }
        
        boolean nextInTable() {
            while (this.nextTableIndex >= 0) {
                final ReferenceEntry nextEntry = this.currentTable.get(this.nextTableIndex--);
                this.nextEntry = nextEntry;
                if (nextEntry != null && (this.advanceTo(this.nextEntry) || this.nextInChain())) {
                    return true;
                }
            }
            return false;
        }
        
        boolean advanceTo(final ReferenceEntry referenceEntry) {
            final Object key = referenceEntry.getKey();
            final Object liveValue = this.this$0.getLiveValue(referenceEntry);
            if (liveValue != null) {
                this.nextExternal = this.this$0.new WriteThroughEntry(key, liveValue);
                this.currentSegment.postReadCleanup();
                return false;
            }
            this.currentSegment.postReadCleanup();
            return false;
        }
        
        @Override
        public boolean hasNext() {
            return this.nextExternal != null;
        }
        
        WriteThroughEntry nextEntry() {
            if (this.nextExternal == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.nextExternal;
            this.advance();
            return this.lastReturned;
        }
        
        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.lastReturned != null);
            this.this$0.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }
    
    final class WriteThroughEntry extends AbstractMapEntry
    {
        final Object key;
        Object value;
        final MapMakerInternalMap this$0;
        
        WriteThroughEntry(final MapMakerInternalMap this$0, final Object key, final Object value) {
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
        public boolean equals(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }
        
        @Override
        public Object setValue(final Object value) {
            final Object put = this.this$0.put(this.key, value);
            this.value = value;
            return put;
        }
    }
    
    final class Values extends AbstractCollection
    {
        final MapMakerInternalMap this$0;
        
        Values(final MapMakerInternalMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.new ValueIterator();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsValue(o);
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
    }
    
    final class ValueIterator extends HashIterator
    {
        final MapMakerInternalMap this$0;
        
        ValueIterator(final MapMakerInternalMap this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Object next() {
            return this.nextEntry().getValue();
        }
    }
    
    final class KeySet extends AbstractSet
    {
        final MapMakerInternalMap this$0;
        
        KeySet(final MapMakerInternalMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.new KeyIterator();
        }
        
        @Override
        public int size() {
            return this.this$0.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.this$0.remove(o) != null;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
    }
    
    final class KeyIterator extends HashIterator
    {
        final MapMakerInternalMap this$0;
        
        KeyIterator(final MapMakerInternalMap this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Object next() {
            return this.nextEntry().getKey();
        }
    }
    
    static final class CleanupMapTask implements Runnable
    {
        final WeakReference mapReference;
        
        public CleanupMapTask(final MapMakerInternalMap mapMakerInternalMap) {
            this.mapReference = new WeakReference((T)mapMakerInternalMap);
        }
        
        @Override
        public void run() {
            final MapMakerInternalMap mapMakerInternalMap = (MapMakerInternalMap)this.mapReference.get();
            if (mapMakerInternalMap == null) {
                throw new CancellationException();
            }
            final Segment[] segments = mapMakerInternalMap.segments;
            while (0 < segments.length) {
                segments[0].runCleanup();
                int n = 0;
                ++n;
            }
        }
    }
    
    static final class SoftExpirableEvictableEntry extends SoftEntry implements ReferenceEntry
    {
        long time;
        @GuardedBy("Segment.this")
        ReferenceEntry nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousEvictable;
        
        SoftExpirableEvictableEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = MapMakerInternalMap.nullEntry();
            this.previousExpirable = MapMakerInternalMap.nullEntry();
            this.nextEvictable = MapMakerInternalMap.nullEntry();
            this.previousEvictable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public long getExpirationTime() {
            return this.time;
        }
        
        @Override
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this.nextExpirable;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry nextExpirable) {
            this.nextExpirable = nextExpirable;
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
            this.previousExpirable = previousExpirable;
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this.nextEvictable;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry nextEvictable) {
            this.nextEvictable = nextEvictable;
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
            this.previousEvictable = previousEvictable;
        }
    }
    
    static class SoftEntry extends SoftReference implements ReferenceEntry
    {
        final int hash;
        final ReferenceEntry next;
        ValueReference valueReference;
        
        SoftEntry(final ReferenceQueue referenceQueue, final Object o, final int hash, @Nullable final ReferenceEntry next) {
            super(o, referenceQueue);
            this.valueReference = MapMakerInternalMap.unset();
            this.hash = hash;
            this.next = next;
        }
        
        @Override
        public Object getKey() {
            return this.get();
        }
        
        @Override
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setExpirationTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ValueReference getValueReference() {
            return this.valueReference;
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
            this.valueReference.clear(this.valueReference = valueReference);
        }
        
        @Override
        public int getHash() {
            return this.hash;
        }
        
        @Override
        public ReferenceEntry getNext() {
            return this.next;
        }
    }
    
    static final class SoftEvictableEntry extends SoftEntry implements ReferenceEntry
    {
        @GuardedBy("Segment.this")
        ReferenceEntry nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousEvictable;
        
        SoftEvictableEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.nextEvictable = MapMakerInternalMap.nullEntry();
            this.previousEvictable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public ReferenceEntry getNextEvictable() {
            return this.nextEvictable;
        }
        
        @Override
        public void setNextEvictable(final ReferenceEntry nextEvictable) {
            this.nextEvictable = nextEvictable;
        }
        
        @Override
        public ReferenceEntry getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        @Override
        public void setPreviousEvictable(final ReferenceEntry previousEvictable) {
            this.previousEvictable = previousEvictable;
        }
    }
    
    static final class SoftExpirableEntry extends SoftEntry implements ReferenceEntry
    {
        long time;
        @GuardedBy("Segment.this")
        ReferenceEntry nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry previousExpirable;
        
        SoftExpirableEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = MapMakerInternalMap.nullEntry();
            this.previousExpirable = MapMakerInternalMap.nullEntry();
        }
        
        @Override
        public long getExpirationTime() {
            return this.time;
        }
        
        @Override
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        @Override
        public ReferenceEntry getNextExpirable() {
            return this.nextExpirable;
        }
        
        @Override
        public void setNextExpirable(final ReferenceEntry nextExpirable) {
            this.nextExpirable = nextExpirable;
        }
        
        @Override
        public ReferenceEntry getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        @Override
        public void setPreviousExpirable(final ReferenceEntry previousExpirable) {
            this.previousExpirable = previousExpirable;
        }
    }
}
