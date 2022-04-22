package com.google.common.cache;

import java.util.logging.*;
import javax.annotation.*;
import javax.annotation.concurrent.*;
import com.google.common.primitives.*;
import com.google.common.annotations.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.lang.ref.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
class LocalCache extends AbstractMap implements ConcurrentMap
{
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_THRESHOLD = 63;
    static final int DRAIN_MAX = 16;
    static final Logger logger;
    static final ListeningExecutorService sameThreadExecutor;
    final int segmentMask;
    final int segmentShift;
    final Segment[] segments;
    final int concurrencyLevel;
    final Equivalence keyEquivalence;
    final Equivalence valueEquivalence;
    final Strength keyStrength;
    final Strength valueStrength;
    final long maxWeight;
    final Weigher weigher;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final long refreshNanos;
    final Queue removalNotificationQueue;
    final RemovalListener removalListener;
    final Ticker ticker;
    final EntryFactory entryFactory;
    final AbstractCache.StatsCounter globalStatsCounter;
    @Nullable
    final CacheLoader defaultLoader;
    static final ValueReference UNSET;
    static final Queue DISCARDING_QUEUE;
    Set keySet;
    Collection values;
    Set entrySet;
    
    LocalCache(final CacheBuilder cacheBuilder, @Nullable final CacheLoader defaultLoader) {
        this.concurrencyLevel = Math.min(cacheBuilder.getConcurrencyLevel(), 65536);
        this.keyStrength = cacheBuilder.getKeyStrength();
        this.valueStrength = cacheBuilder.getValueStrength();
        this.keyEquivalence = cacheBuilder.getKeyEquivalence();
        this.valueEquivalence = cacheBuilder.getValueEquivalence();
        this.maxWeight = cacheBuilder.getMaximumWeight();
        this.weigher = cacheBuilder.getWeigher();
        this.expireAfterAccessNanos = cacheBuilder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = cacheBuilder.getExpireAfterWriteNanos();
        this.refreshNanos = cacheBuilder.getRefreshNanos();
        this.removalListener = cacheBuilder.getRemovalListener();
        this.removalNotificationQueue = ((this.removalListener == CacheBuilder.NullListener.INSTANCE) ? discardingQueue() : new ConcurrentLinkedQueue());
        this.ticker = cacheBuilder.getTicker(this.recordsTime());
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
        this.globalStatsCounter = (AbstractCache.StatsCounter)cacheBuilder.getStatsCounterSupplier().get();
        this.defaultLoader = defaultLoader;
        int n = Math.min(cacheBuilder.getInitialCapacity(), 1073741824);
        if (this.evictsBySize() && !this.customWeigher()) {
            n = Math.min(n, (int)this.maxWeight);
        }
        while (1 < this.concurrencyLevel && (!this.evictsBySize() || 20 <= this.maxWeight)) {
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
            long n4 = this.maxWeight / 1 + 1L;
            final long n5 = this.maxWeight % 1;
            while (0 < this.segments.length) {
                if (0 == n5) {
                    --n4;
                }
                this.segments[0] = this.createSegment(1, n4, (AbstractCache.StatsCounter)cacheBuilder.getStatsCounterSupplier().get());
                int n6 = 0;
                ++n6;
            }
        }
        else {
            while (0 < this.segments.length) {
                this.segments[0] = this.createSegment(1, -1L, (AbstractCache.StatsCounter)cacheBuilder.getStatsCounterSupplier().get());
                int n7 = 0;
                ++n7;
            }
        }
    }
    
    boolean evictsBySize() {
        return this.maxWeight >= 0L;
    }
    
    boolean customWeigher() {
        return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
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
    
    boolean refreshes() {
        return this.refreshNanos > 0L;
    }
    
    boolean usesAccessQueue() {
        return this.expiresAfterAccess() || this.evictsBySize();
    }
    
    boolean usesWriteQueue() {
        return this.expiresAfterWrite();
    }
    
    boolean recordsWrite() {
        return this.expiresAfterWrite() || this.refreshes();
    }
    
    boolean recordsAccess() {
        return this.expiresAfterAccess();
    }
    
    boolean recordsTime() {
        return this.recordsWrite() || this.recordsAccess();
    }
    
    boolean usesWriteEntries() {
        return this.usesWriteQueue() || this.recordsWrite();
    }
    
    boolean usesAccessEntries() {
        return this.usesAccessQueue() || this.recordsAccess();
    }
    
    boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }
    
    boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }
    
    static ValueReference unset() {
        return LocalCache.UNSET;
    }
    
    static ReferenceEntry nullEntry() {
        return NullEntry.INSTANCE;
    }
    
    static Queue discardingQueue() {
        return LocalCache.DISCARDING_QUEUE;
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
    ValueReference newValueReference(final ReferenceEntry referenceEntry, final Object o, final int n) {
        return this.valueStrength.referenceValue(this.segmentFor(referenceEntry.getHash()), referenceEntry, Preconditions.checkNotNull(o), n);
    }
    
    int hash(@Nullable final Object o) {
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
    boolean isLive(final ReferenceEntry referenceEntry, final long n) {
        return this.segmentFor(referenceEntry.getHash()).getLiveValue(referenceEntry, n) != null;
    }
    
    Segment segmentFor(final int n) {
        return this.segments[n >>> this.segmentShift & this.segmentMask];
    }
    
    Segment createSegment(final int n, final long n2, final AbstractCache.StatsCounter statsCounter) {
        return new Segment(this, n, n2, statsCounter);
    }
    
    @Nullable
    Object getLiveValue(final ReferenceEntry referenceEntry, final long n) {
        if (referenceEntry.getKey() == null) {
            return null;
        }
        final Object value = referenceEntry.getValueReference().get();
        if (value == null) {
            return null;
        }
        if (this.isExpired(referenceEntry, n)) {
            return null;
        }
        return value;
    }
    
    boolean isExpired(final ReferenceEntry referenceEntry, final long n) {
        Preconditions.checkNotNull(referenceEntry);
        return (this.expiresAfterAccess() && n - referenceEntry.getAccessTime() >= this.expireAfterAccessNanos) || (this.expiresAfterWrite() && n - referenceEntry.getWriteTime() >= this.expireAfterWriteNanos);
    }
    
    @GuardedBy("Segment.this")
    static void connectAccessOrder(final ReferenceEntry previousInAccessQueue, final ReferenceEntry nextInAccessQueue) {
        previousInAccessQueue.setNextInAccessQueue(nextInAccessQueue);
        nextInAccessQueue.setPreviousInAccessQueue(previousInAccessQueue);
    }
    
    @GuardedBy("Segment.this")
    static void nullifyAccessOrder(final ReferenceEntry referenceEntry) {
        final ReferenceEntry nullEntry = nullEntry();
        referenceEntry.setNextInAccessQueue(nullEntry);
        referenceEntry.setPreviousInAccessQueue(nullEntry);
    }
    
    @GuardedBy("Segment.this")
    static void connectWriteOrder(final ReferenceEntry previousInWriteQueue, final ReferenceEntry nextInWriteQueue) {
        previousInWriteQueue.setNextInWriteQueue(nextInWriteQueue);
        nextInWriteQueue.setPreviousInWriteQueue(previousInWriteQueue);
    }
    
    @GuardedBy("Segment.this")
    static void nullifyWriteOrder(final ReferenceEntry referenceEntry) {
        final ReferenceEntry nullEntry = nullEntry();
        referenceEntry.setNextInWriteQueue(nullEntry);
        referenceEntry.setPreviousInWriteQueue(nullEntry);
    }
    
    void processPendingNotifications() {
        RemovalNotification removalNotification;
        while ((removalNotification = this.removalNotificationQueue.poll()) != null) {
            this.removalListener.onRemoval(removalNotification);
        }
    }
    
    final Segment[] newSegmentArray(final int n) {
        return new Segment[n];
    }
    
    public void cleanUp() {
        final Segment[] segments = this.segments;
        while (0 < segments.length) {
            segments[0].cleanUp();
            int n = 0;
            ++n;
        }
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
    
    long longSize() {
        final Segment[] segments = this.segments;
        long n = 0L;
        while (0 < segments.length) {
            n += segments[0].count;
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    @Override
    public int size() {
        return Ints.saturatedCast(this.longSize());
    }
    
    @Nullable
    @Override
    public Object get(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).get(o, hash);
    }
    
    @Nullable
    public Object getIfPresent(final Object o) {
        final int hash = this.hash(Preconditions.checkNotNull(o));
        final Object value = this.segmentFor(hash).get(o, hash);
        if (value == null) {
            this.globalStatsCounter.recordMisses(1);
        }
        else {
            this.globalStatsCounter.recordHits(1);
        }
        return value;
    }
    
    Object get(final Object o, final CacheLoader cacheLoader) throws ExecutionException {
        final int hash = this.hash(Preconditions.checkNotNull(o));
        return this.segmentFor(hash).get(o, hash, cacheLoader);
    }
    
    Object getOrLoad(final Object o) throws ExecutionException {
        return this.get(o, this.defaultLoader);
    }
    
    ImmutableMap getAllPresent(final Iterable iterable) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (final Object next : iterable) {
            final Object value = this.get(next);
            if (value == null) {
                int n = 0;
                ++n;
            }
            else {
                linkedHashMap.put(next, value);
                int n2 = 0;
                ++n2;
            }
        }
        this.globalStatsCounter.recordHits(0);
        this.globalStatsCounter.recordMisses(0);
        return ImmutableMap.copyOf(linkedHashMap);
    }
    
    ImmutableMap getAll(final Iterable iterable) throws ExecutionException {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final LinkedHashSet linkedHashSet = Sets.newLinkedHashSet();
        for (final Object next : iterable) {
            final Object value = this.get(next);
            if (!linkedHashMap.containsKey(next)) {
                linkedHashMap.put(next, value);
                if (value == null) {
                    int n = 0;
                    ++n;
                    linkedHashSet.add(next);
                }
                else {
                    int n2 = 0;
                    ++n2;
                }
            }
        }
        if (!linkedHashSet.isEmpty()) {
            final Map loadAll = this.loadAll(linkedHashSet, this.defaultLoader);
            for (final Object next2 : linkedHashSet) {
                final Object value2 = loadAll.get(next2);
                if (value2 == null) {
                    throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + next2);
                }
                linkedHashMap.put(next2, value2);
            }
        }
        final ImmutableMap copy = ImmutableMap.copyOf(linkedHashMap);
        this.globalStatsCounter.recordHits(0);
        this.globalStatsCounter.recordMisses(0);
        return copy;
    }
    
    @Nullable
    Map loadAll(final Set set, final CacheLoader cacheLoader) throws ExecutionException {
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(set);
        final Stopwatch started = Stopwatch.createStarted();
        final Map loadAll = cacheLoader.loadAll(set);
        if (!true) {
            this.globalStatsCounter.recordLoadException(started.elapsed(TimeUnit.NANOSECONDS));
        }
        if (loadAll == null) {
            this.globalStatsCounter.recordLoadException(started.elapsed(TimeUnit.NANOSECONDS));
            throw new CacheLoader.InvalidCacheLoadException(cacheLoader + " returned null map from loadAll");
        }
        started.stop();
        for (final Map.Entry<Object, V> entry : loadAll.entrySet()) {
            final Object key = entry.getKey();
            final V value = entry.getValue();
            if (key == null || value == null) {
                continue;
            }
            this.put(key, value);
        }
        if (true) {
            this.globalStatsCounter.recordLoadException(started.elapsed(TimeUnit.NANOSECONDS));
            throw new CacheLoader.InvalidCacheLoadException(cacheLoader + " returned null keys or values from loadAll");
        }
        this.globalStatsCounter.recordLoadSuccess(started.elapsed(TimeUnit.NANOSECONDS));
        return loadAll;
    }
    
    ReferenceEntry getEntry(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        final int hash = this.hash(o);
        return this.segmentFor(hash).getEntry(o, hash);
    }
    
    void refresh(final Object o) {
        final int hash = this.hash(Preconditions.checkNotNull(o));
        this.segmentFor(hash).refresh(o, hash, this.defaultLoader, false);
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
        final long read = this.ticker.read();
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
                        final Object liveValue = segment.getLiveValue(next, read);
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
    
    void invalidateAll(final Iterable iterable) {
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            this.remove(iterator.next());
        }
    }
    
    @Override
    public Set keySet() {
        final Set keySet = this.keySet;
        return (keySet != null) ? keySet : (this.keySet = new KeySet(this));
    }
    
    @Override
    public Collection values() {
        final Collection values = this.values;
        return (values != null) ? values : (this.values = new Values(this));
    }
    
    @GwtIncompatible("Not supported.")
    @Override
    public Set entrySet() {
        final Set entrySet = this.entrySet;
        return (entrySet != null) ? entrySet : (this.entrySet = new EntrySet(this));
    }
    
    static {
        logger = Logger.getLogger(LocalCache.class.getName());
        sameThreadExecutor = MoreExecutors.sameThreadExecutor();
        UNSET = new ValueReference() {
            @Override
            public Object get() {
                return null;
            }
            
            @Override
            public int getWeight() {
                return 0;
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
            public boolean isLoading() {
                return false;
            }
            
            @Override
            public boolean isActive() {
                return false;
            }
            
            @Override
            public Object waitForValue() {
                return null;
            }
            
            @Override
            public void notifyNewValue(final Object o) {
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
    
    static class LocalLoadingCache extends LocalManualCache implements LoadingCache
    {
        private static final long serialVersionUID = 1L;
        
        LocalLoadingCache(final CacheBuilder cacheBuilder, final CacheLoader cacheLoader) {
            super(new LocalCache(cacheBuilder, (CacheLoader)Preconditions.checkNotNull(cacheLoader)), null);
        }
        
        @Override
        public Object get(final Object o) throws ExecutionException {
            return this.localCache.getOrLoad(o);
        }
        
        @Override
        public Object getUnchecked(final Object o) {
            return this.get(o);
        }
        
        @Override
        public ImmutableMap getAll(final Iterable iterable) throws ExecutionException {
            return this.localCache.getAll(iterable);
        }
        
        @Override
        public void refresh(final Object o) {
            this.localCache.refresh(o);
        }
        
        @Override
        public final Object apply(final Object o) {
            return this.getUnchecked(o);
        }
        
        @Override
        Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }
    }
    
    static class LocalManualCache implements Cache, Serializable
    {
        final LocalCache localCache;
        private static final long serialVersionUID = 1L;
        
        LocalManualCache(final CacheBuilder cacheBuilder) {
            this(new LocalCache(cacheBuilder, null));
        }
        
        private LocalManualCache(final LocalCache localCache) {
            this.localCache = localCache;
        }
        
        @Nullable
        @Override
        public Object getIfPresent(final Object o) {
            return this.localCache.getIfPresent(o);
        }
        
        @Override
        public Object get(final Object o, final Callable callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return this.localCache.get(o, new CacheLoader(callable) {
                final Callable val$valueLoader;
                final LocalManualCache this$0;
                
                @Override
                public Object load(final Object o) throws Exception {
                    return this.val$valueLoader.call();
                }
            });
        }
        
        @Override
        public ImmutableMap getAllPresent(final Iterable iterable) {
            return this.localCache.getAllPresent(iterable);
        }
        
        @Override
        public void put(final Object o, final Object o2) {
            this.localCache.put(o, o2);
        }
        
        @Override
        public void putAll(final Map map) {
            this.localCache.putAll(map);
        }
        
        @Override
        public void invalidate(final Object o) {
            Preconditions.checkNotNull(o);
            this.localCache.remove(o);
        }
        
        @Override
        public void invalidateAll(final Iterable iterable) {
            this.localCache.invalidateAll(iterable);
        }
        
        @Override
        public void invalidateAll() {
            this.localCache.clear();
        }
        
        @Override
        public long size() {
            return this.localCache.longSize();
        }
        
        @Override
        public ConcurrentMap asMap() {
            return this.localCache;
        }
        
        @Override
        public CacheStats stats() {
            final AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.globalStatsCounter);
            final Segment[] segments = this.localCache.segments;
            while (0 < segments.length) {
                simpleStatsCounter.incrementBy(segments[0].statsCounter);
                int n = 0;
                ++n;
            }
            return simpleStatsCounter.snapshot();
        }
        
        @Override
        public void cleanUp() {
            this.localCache.cleanUp();
        }
        
        Object writeReplace() {
            return new ManualSerializationProxy(this.localCache);
        }
        
        LocalManualCache(final LocalCache localCache, final LocalCache$1 valueReference) {
            this(localCache);
        }
    }
    
    static class Segment extends ReentrantLock
    {
        final LocalCache map;
        int count;
        @GuardedBy("Segment.this")
        int totalWeight;
        int modCount;
        int threshold;
        AtomicReferenceArray table;
        final long maxSegmentWeight;
        final ReferenceQueue keyReferenceQueue;
        final ReferenceQueue valueReferenceQueue;
        final Queue recencyQueue;
        final AtomicInteger readCount;
        @GuardedBy("Segment.this")
        final Queue writeQueue;
        @GuardedBy("Segment.this")
        final Queue accessQueue;
        final AbstractCache.StatsCounter statsCounter;
        
        Segment(final LocalCache map, final int n, final long maxSegmentWeight, final AbstractCache.StatsCounter statsCounter) {
            this.readCount = new AtomicInteger();
            this.map = map;
            this.maxSegmentWeight = maxSegmentWeight;
            this.statsCounter = (AbstractCache.StatsCounter)Preconditions.checkNotNull(statsCounter);
            this.initTable(this.newEntryArray(n));
            this.keyReferenceQueue = (map.usesKeyReferences() ? new ReferenceQueue() : null);
            this.valueReferenceQueue = (map.usesValueReferences() ? new ReferenceQueue() : null);
            this.recencyQueue = (map.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.discardingQueue());
            this.writeQueue = (map.usesWriteQueue() ? new WriteQueue() : LocalCache.discardingQueue());
            this.accessQueue = (map.usesAccessQueue() ? new AccessQueue() : LocalCache.discardingQueue());
        }
        
        AtomicReferenceArray newEntryArray(final int n) {
            return new AtomicReferenceArray(n);
        }
        
        void initTable(final AtomicReferenceArray table) {
            this.threshold = table.length() * 3 / 4;
            if (!this.map.customWeigher() && this.threshold == this.maxSegmentWeight) {
                ++this.threshold;
            }
            this.table = table;
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry newEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(o), n, referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry copyEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            final ValueReference valueReference = referenceEntry.getValueReference();
            final Object value = valueReference.get();
            if (value == null && valueReference.isActive()) {
                return null;
            }
            final ReferenceEntry copyEntry = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            copyEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, copyEntry));
            return copyEntry;
        }
        
        @GuardedBy("Segment.this")
        void setValue(final ReferenceEntry referenceEntry, final Object o, final Object o2, final long n) {
            final ValueReference valueReference = referenceEntry.getValueReference();
            final int weigh = this.map.weigher.weigh(o, o2);
            Preconditions.checkState(weigh >= 0, (Object)"Weights must be non-negative");
            referenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, referenceEntry, o2, weigh));
            this.recordWrite(referenceEntry, weigh, n);
            valueReference.notifyNewValue(o2);
        }
        
        Object get(final Object o, final int n, final CacheLoader cacheLoader) throws ExecutionException {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(cacheLoader);
            if (this.count != 0) {
                final ReferenceEntry entry = this.getEntry(o, n);
                if (entry != null) {
                    final long read = this.map.ticker.read();
                    final Object liveValue = this.getLiveValue(entry, read);
                    if (liveValue != null) {
                        this.recordRead(entry, read);
                        this.statsCounter.recordHits(1);
                        final Object scheduleRefresh = this.scheduleRefresh(entry, o, n, liveValue, read, cacheLoader);
                        this.postReadCleanup();
                        return scheduleRefresh;
                    }
                    final ValueReference valueReference = entry.getValueReference();
                    if (valueReference.isLoading()) {
                        final Object waitForLoadingValue = this.waitForLoadingValue(entry, o, valueReference);
                        this.postReadCleanup();
                        return waitForLoadingValue;
                    }
                }
            }
            final Object lockedGetOrLoad = this.lockedGetOrLoad(o, n, cacheLoader);
            this.postReadCleanup();
            return lockedGetOrLoad;
        }
        
        Object lockedGetOrLoad(final Object o, final int n, final CacheLoader cacheLoader) throws ExecutionException {
            ValueReference valueReference = null;
            this.lock();
            final long read = this.map.ticker.read();
            this.preWriteCleanup(read);
            final int count = this.count - 1;
            final AtomicReferenceArray table = this.table;
            while (true) {
                for (ReferenceEntry next = table.get(n & table.length() - 1); next != null; next = next.getNext()) {
                    final Object key = next.getKey();
                    if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                        valueReference = next.getValueReference();
                        if (!valueReference.isLoading()) {
                            final Object value = valueReference.get();
                            if (value == null) {
                                this.enqueueNotification(key, n, valueReference, RemovalCause.COLLECTED);
                            }
                            else {
                                if (!this.map.isExpired(next, read)) {
                                    this.recordLockedRead(next, read);
                                    this.statsCounter.recordHits(1);
                                    final Object o2 = value;
                                    this.unlock();
                                    this.postWriteCleanup();
                                    return o2;
                                }
                                this.enqueueNotification(key, n, valueReference, RemovalCause.EXPIRED);
                            }
                            this.writeQueue.remove(next);
                            this.accessQueue.remove(next);
                            this.count = count;
                        }
                        this.unlock();
                        this.postWriteCleanup();
                        return this.waitForLoadingValue(next, o, valueReference);
                    }
                }
                continue;
            }
        }
        
        Object waitForLoadingValue(final ReferenceEntry referenceEntry, final Object o, final ValueReference valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(referenceEntry), "Recursive load of: %s", o);
            final Object waitForValue = valueReference.waitForValue();
            if (waitForValue == null) {
                throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + o + ".");
            }
            this.recordRead(referenceEntry, this.map.ticker.read());
            final Object o2 = waitForValue;
            this.statsCounter.recordMisses(1);
            return o2;
        }
        
        Object loadSync(final Object o, final int n, final LoadingValueReference loadingValueReference, final CacheLoader cacheLoader) throws ExecutionException {
            return this.getAndRecordStats(o, n, loadingValueReference, loadingValueReference.loadFuture(o, cacheLoader));
        }
        
        ListenableFuture loadAsync(final Object o, final int n, final LoadingValueReference loadingValueReference, final CacheLoader cacheLoader) {
            final ListenableFuture loadFuture = loadingValueReference.loadFuture(o, cacheLoader);
            loadFuture.addListener(new Runnable(o, n, loadingValueReference, loadFuture) {
                final Object val$key;
                final int val$hash;
                final LoadingValueReference val$loadingValueReference;
                final ListenableFuture val$loadingFuture;
                final Segment this$0;
                
                @Override
                public void run() {
                    this.this$0.getAndRecordStats(this.val$key, this.val$hash, this.val$loadingValueReference, this.val$loadingFuture);
                }
            }, LocalCache.sameThreadExecutor);
            return loadFuture;
        }
        
        Object getAndRecordStats(final Object o, final int n, final LoadingValueReference loadingValueReference, final ListenableFuture listenableFuture) throws ExecutionException {
            final Object uninterruptibly = Uninterruptibles.getUninterruptibly(listenableFuture);
            if (uninterruptibly == null) {
                throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + o + ".");
            }
            this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
            this.storeLoadedValue(o, n, loadingValueReference, uninterruptibly);
            final Object o2;
            if ((o2 = uninterruptibly) == null) {
                this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                this.removeLoadingValue(o, n, loadingValueReference);
            }
            return o2;
        }
        
        Object scheduleRefresh(final ReferenceEntry referenceEntry, final Object o, final int n, final Object o2, final long n2, final CacheLoader cacheLoader) {
            if (this.map.refreshes() && n2 - referenceEntry.getWriteTime() > this.map.refreshNanos && !referenceEntry.getValueReference().isLoading()) {
                final Object refresh = this.refresh(o, n, cacheLoader, true);
                if (refresh != null) {
                    return refresh;
                }
            }
            return o2;
        }
        
        @Nullable
        Object refresh(final Object o, final int n, final CacheLoader cacheLoader, final boolean b) {
            final LoadingValueReference insertLoadingValueReference = this.insertLoadingValueReference(o, n, b);
            if (insertLoadingValueReference == null) {
                return null;
            }
            final ListenableFuture loadAsync = this.loadAsync(o, n, insertLoadingValueReference, cacheLoader);
            if (loadAsync.isDone()) {
                return Uninterruptibles.getUninterruptibly(loadAsync);
            }
            return null;
        }
        
        @Nullable
        LoadingValueReference insertLoadingValueReference(final Object o, final int n, final boolean b) {
            this.lock();
            final long read = this.map.ticker.read();
            this.preWriteCleanup(read);
            final AtomicReferenceArray table = this.table;
            final int n2 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n2);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    if (valueReference.isLoading() || (b && read - next.getWriteTime() < this.map.refreshNanos)) {
                        final LoadingValueReference loadingValueReference = null;
                        this.unlock();
                        this.postWriteCleanup();
                        return loadingValueReference;
                    }
                    ++this.modCount;
                    final LoadingValueReference valueReference2 = new LoadingValueReference(valueReference);
                    next.setValueReference(valueReference2);
                    final LoadingValueReference loadingValueReference2 = valueReference2;
                    this.unlock();
                    this.postWriteCleanup();
                    return loadingValueReference2;
                }
                else {
                    next = next.getNext();
                }
            }
            ++this.modCount;
            final LoadingValueReference valueReference3 = new LoadingValueReference();
            final ReferenceEntry entry = this.newEntry(o, n, referenceEntry);
            entry.setValueReference(valueReference3);
            table.set(n2, entry);
            final LoadingValueReference loadingValueReference3 = valueReference3;
            this.unlock();
            this.postWriteCleanup();
            return loadingValueReference3;
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
            }
        }
        
        @GuardedBy("Segment.this")
        void drainValueReferenceQueue() {
            Reference poll;
            while ((poll = this.valueReferenceQueue.poll()) != null) {
                this.map.reclaimValue((ValueReference)poll);
                int n = 0;
                ++n;
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
        
        void recordRead(final ReferenceEntry referenceEntry, final long accessTime) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(accessTime);
            }
            this.recencyQueue.add(referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        void recordLockedRead(final ReferenceEntry referenceEntry, final long accessTime) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(accessTime);
            }
            this.accessQueue.add(referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        void recordWrite(final ReferenceEntry referenceEntry, final int n, final long n2) {
            this.drainRecencyQueue();
            this.totalWeight += n;
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(n2);
            }
            if (this.map.recordsWrite()) {
                referenceEntry.setWriteTime(n2);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        void drainRecencyQueue() {
            ReferenceEntry referenceEntry;
            while ((referenceEntry = this.recencyQueue.poll()) != null) {
                if (this.accessQueue.contains(referenceEntry)) {
                    this.accessQueue.add(referenceEntry);
                }
            }
        }
        
        void tryExpireEntries(final long n) {
            if (this.tryLock()) {
                this.expireEntries(n);
                this.unlock();
            }
        }
        
        @GuardedBy("Segment.this")
        void expireEntries(final long p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     4: aload_0        
            //     5: getfield        com/google/common/cache/LocalCache$Segment.writeQueue:Ljava/util/Queue;
            //     8: invokeinterface java/util/Queue.peek:()Ljava/lang/Object;
            //    13: checkcast       Lcom/google/common/cache/LocalCache$ReferenceEntry;
            //    16: dup            
            //    17: astore_3       
            //    18: ifnull          55
            //    21: aload_0        
            //    22: getfield        com/google/common/cache/LocalCache$Segment.map:Lcom/google/common/cache/LocalCache;
            //    25: aload_3        
            //    26: lload_1        
            //    27: invokevirtual   com/google/common/cache/LocalCache.isExpired:(Lcom/google/common/cache/LocalCache$ReferenceEntry;J)Z
            //    30: ifeq            55
            //    33: aload_0        
            //    34: aload_3        
            //    35: aload_3        
            //    36: invokeinterface com/google/common/cache/LocalCache$ReferenceEntry.getHash:()I
            //    41: getstatic       com/google/common/cache/RemovalCause.EXPIRED:Lcom/google/common/cache/RemovalCause;
            //    44: ifnull          4
            //    47: new             Ljava/lang/AssertionError;
            //    50: dup            
            //    51: invokespecial   java/lang/AssertionError.<init>:()V
            //    54: athrow         
            //    55: aload_0        
            //    56: getfield        com/google/common/cache/LocalCache$Segment.accessQueue:Ljava/util/Queue;
            //    59: invokeinterface java/util/Queue.peek:()Ljava/lang/Object;
            //    64: checkcast       Lcom/google/common/cache/LocalCache$ReferenceEntry;
            //    67: dup            
            //    68: astore_3       
            //    69: ifnull          106
            //    72: aload_0        
            //    73: getfield        com/google/common/cache/LocalCache$Segment.map:Lcom/google/common/cache/LocalCache;
            //    76: aload_3        
            //    77: lload_1        
            //    78: invokevirtual   com/google/common/cache/LocalCache.isExpired:(Lcom/google/common/cache/LocalCache$ReferenceEntry;J)Z
            //    81: ifeq            106
            //    84: aload_0        
            //    85: aload_3        
            //    86: aload_3        
            //    87: invokeinterface com/google/common/cache/LocalCache$ReferenceEntry.getHash:()I
            //    92: getstatic       com/google/common/cache/RemovalCause.EXPIRED:Lcom/google/common/cache/RemovalCause;
            //    95: ifnull          55
            //    98: new             Ljava/lang/AssertionError;
            //   101: dup            
            //   102: invokespecial   java/lang/AssertionError.<init>:()V
            //   105: athrow         
            //   106: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0055 (coming from #0095).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @GuardedBy("Segment.this")
        void enqueueNotification(final ReferenceEntry referenceEntry, final RemovalCause removalCause) {
            this.enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference(), removalCause);
        }
        
        @GuardedBy("Segment.this")
        void enqueueNotification(@Nullable final Object o, final int n, final ValueReference valueReference, final RemovalCause removalCause) {
            this.totalWeight -= valueReference.getWeight();
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(new RemovalNotification(o, valueReference.get(), removalCause));
            }
        }
        
        @GuardedBy("Segment.this")
        void evictEntries() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     4: invokevirtual   com/google/common/cache/LocalCache.evictsBySize:()Z
            //     7: ifne            11
            //    10: return         
            //    11: aload_0        
            //    12: invokevirtual   com/google/common/cache/LocalCache$Segment.drainRecencyQueue:()V
            //    15: aload_0        
            //    16: getfield        com/google/common/cache/LocalCache$Segment.totalWeight:I
            //    19: i2l            
            //    20: aload_0        
            //    21: getfield        com/google/common/cache/LocalCache$Segment.maxSegmentWeight:J
            //    24: lcmp           
            //    25: ifle            58
            //    28: aload_0        
            //    29: invokevirtual   com/google/common/cache/LocalCache$Segment.getNextEvictable:()Lcom/google/common/cache/LocalCache$ReferenceEntry;
            //    32: astore_1       
            //    33: aload_0        
            //    34: aload_1        
            //    35: aload_1        
            //    36: invokeinterface com/google/common/cache/LocalCache$ReferenceEntry.getHash:()I
            //    41: getstatic       com/google/common/cache/RemovalCause.SIZE:Lcom/google/common/cache/RemovalCause;
            //    44: ifnull          55
            //    47: new             Ljava/lang/AssertionError;
            //    50: dup            
            //    51: invokespecial   java/lang/AssertionError.<init>:()V
            //    54: athrow         
            //    55: goto            15
            //    58: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0055).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
            //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
            //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
            //     at java.lang.Thread.run(Unknown Source)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        ReferenceEntry getNextEvictable() {
            for (final ReferenceEntry referenceEntry : this.accessQueue) {
                if (referenceEntry.getValueReference().getWeight() > 0) {
                    return referenceEntry;
                }
            }
            throw new AssertionError();
        }
        
        ReferenceEntry getFirst(final int n) {
            final AtomicReferenceArray table = this.table;
            return table.get(n & table.length() - 1);
        }
        
        @Nullable
        ReferenceEntry getEntry(final Object o, final int n) {
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
            return null;
        }
        
        @Nullable
        ReferenceEntry getLiveEntry(final Object o, final int n, final long n2) {
            final ReferenceEntry entry = this.getEntry(o, n);
            if (entry == null) {
                return null;
            }
            if (this.map.isExpired(entry, n2)) {
                this.tryExpireEntries(n2);
                return null;
            }
            return entry;
        }
        
        Object getLiveValue(final ReferenceEntry referenceEntry, final long n) {
            if (referenceEntry.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            final Object value = referenceEntry.getValueReference().get();
            if (value == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if (this.map.isExpired(referenceEntry, n)) {
                this.tryExpireEntries(n);
                return null;
            }
            return value;
        }
        
        @Nullable
        Object get(final Object o, final int n) {
            if (this.count != 0) {
                final long read = this.map.ticker.read();
                final ReferenceEntry liveEntry = this.getLiveEntry(o, n, read);
                if (liveEntry == null) {
                    final Object o2 = null;
                    this.postReadCleanup();
                    return o2;
                }
                final Object value = liveEntry.getValueReference().get();
                if (value != null) {
                    this.recordRead(liveEntry, read);
                    final Object scheduleRefresh = this.scheduleRefresh(liveEntry, liveEntry.getKey(), n, value, read, this.map.defaultLoader);
                    this.postReadCleanup();
                    return scheduleRefresh;
                }
                this.tryDrainReferenceQueues();
            }
            final Object o3 = null;
            this.postReadCleanup();
            return o3;
        }
        
        boolean containsKey(final Object o, final int n) {
            if (this.count == 0) {
                this.postReadCleanup();
                return false;
            }
            final ReferenceEntry liveEntry = this.getLiveEntry(o, n, this.map.ticker.read());
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
                final long read = this.map.ticker.read();
                final AtomicReferenceArray table = this.table;
                while (0 < table.length()) {
                    for (ReferenceEntry next = table.get(0); next != null; next = next.getNext()) {
                        final Object liveValue = this.getLiveValue(next, read);
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
        
        @Nullable
        Object put(final Object o, final int n, final Object o2, final boolean b) {
            this.lock();
            final long read = this.map.ticker.read();
            this.preWriteCleanup(read);
            if (this.count + 1 > this.threshold) {
                this.expand();
                final int n2 = this.count + 1;
            }
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n3);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    if (value == null) {
                        ++this.modCount;
                        int count;
                        if (valueReference.isActive()) {
                            this.enqueueNotification(o, n, valueReference, RemovalCause.COLLECTED);
                            this.setValue(next, o, o2, read);
                            count = this.count;
                        }
                        else {
                            this.setValue(next, o, o2, read);
                            count = this.count + 1;
                        }
                        this.count = count;
                        this.evictEntries();
                        final Object o3 = null;
                        this.unlock();
                        this.postWriteCleanup();
                        return o3;
                    }
                    if (b) {
                        this.recordLockedRead(next, read);
                        final Object o4 = value;
                        this.unlock();
                        this.postWriteCleanup();
                        return o4;
                    }
                    ++this.modCount;
                    this.enqueueNotification(o, n, valueReference, RemovalCause.REPLACED);
                    this.setValue(next, o, o2, read);
                    this.evictEntries();
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
            this.setValue(entry, o, o2, read);
            table.set(n3, entry);
            ++this.count;
            this.evictEntries();
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
            final long read = this.map.ticker.read();
            this.preWriteCleanup(read);
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
                        if (valueReference.isActive()) {
                            final int n3 = this.count - 1;
                            ++this.modCount;
                            final ReferenceEntry removeValueFromChain = this.removeValueFromChain(referenceEntry, next, key, n, valueReference, RemovalCause.COLLECTED);
                            final int n4 = this.count - 1;
                            table.set(n2, removeValueFromChain);
                            this.count = 0;
                        }
                        this.unlock();
                        this.postWriteCleanup();
                        return false;
                    }
                    if (this.map.valueEquivalence.equivalent(o2, value)) {
                        ++this.modCount;
                        this.enqueueNotification(o, n, valueReference, RemovalCause.REPLACED);
                        this.setValue(next, o, o3, read);
                        this.evictEntries();
                        this.unlock();
                        this.postWriteCleanup();
                        return false;
                    }
                    this.recordLockedRead(next, read);
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
        
        @Nullable
        Object replace(final Object o, final int n, final Object o2) {
            this.lock();
            final long read = this.map.ticker.read();
            this.preWriteCleanup(read);
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
                        if (valueReference.isActive()) {
                            final int n3 = this.count - 1;
                            ++this.modCount;
                            final ReferenceEntry removeValueFromChain = this.removeValueFromChain(referenceEntry, next, key, n, valueReference, RemovalCause.COLLECTED);
                            final int count = this.count - 1;
                            table.set(n2, removeValueFromChain);
                            this.count = count;
                        }
                        final Object o3 = null;
                        this.unlock();
                        this.postWriteCleanup();
                        return o3;
                    }
                    ++this.modCount;
                    this.enqueueNotification(o, n, valueReference, RemovalCause.REPLACED);
                    this.setValue(next, o, o2, read);
                    this.evictEntries();
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
        
        @Nullable
        Object remove(final Object o, final int n) {
            this.lock();
            this.preWriteCleanup(this.map.ticker.read());
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            for (ReferenceEntry referenceEntry = next = table.get(n3); next != null; next = next.getNext()) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    RemovalCause removalCause;
                    if (value != null) {
                        removalCause = RemovalCause.EXPLICIT;
                    }
                    else {
                        if (!valueReference.isActive()) {
                            final Object o2 = null;
                            this.unlock();
                            this.postWriteCleanup();
                            return o2;
                        }
                        removalCause = RemovalCause.COLLECTED;
                    }
                    ++this.modCount;
                    final ReferenceEntry removeValueFromChain = this.removeValueFromChain(referenceEntry, next, key, n, valueReference, removalCause);
                    final int count = this.count - 1;
                    table.set(n3, removeValueFromChain);
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
        
        boolean storeLoadedValue(final Object o, final int n, final LoadingValueReference loadingValueReference, final Object o2) {
            this.lock();
            final long read = this.map.ticker.read();
            this.preWriteCleanup(read);
            int n2 = this.count + 1;
            if (n2 > this.threshold) {
                this.expand();
                n2 = this.count + 1;
            }
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n3);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    if (loadingValueReference == valueReference || (value == null && valueReference != LocalCache.UNSET)) {
                        ++this.modCount;
                        if (loadingValueReference.isActive()) {
                            this.enqueueNotification(o, n, loadingValueReference, (value == null) ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                            --n2;
                        }
                        this.setValue(next, o, o2, read);
                        this.count = n2;
                        this.evictEntries();
                        this.unlock();
                        this.postWriteCleanup();
                        return false;
                    }
                    this.enqueueNotification(o, n, new WeightedStrongValueReference(o2, 0), RemovalCause.REPLACED);
                    this.unlock();
                    this.postWriteCleanup();
                    return false;
                }
                else {
                    next = next.getNext();
                }
            }
            ++this.modCount;
            final ReferenceEntry entry = this.newEntry(o, n, referenceEntry);
            this.setValue(entry, o, o2, read);
            table.set(n3, entry);
            this.count = n2;
            this.evictEntries();
            this.unlock();
            this.postWriteCleanup();
            return true;
        }
        
        boolean remove(final Object o, final int n, final Object o2) {
            this.lock();
            this.preWriteCleanup(this.map.ticker.read());
            final int n2 = this.count - 1;
            final AtomicReferenceArray table = this.table;
            final int n3 = n & table.length() - 1;
            ReferenceEntry next;
            for (ReferenceEntry referenceEntry = next = table.get(n3); next != null; next = next.getNext()) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    final ValueReference valueReference = next.getValueReference();
                    final Object value = valueReference.get();
                    RemovalCause removalCause;
                    if (this.map.valueEquivalence.equivalent(o2, value)) {
                        removalCause = RemovalCause.EXPLICIT;
                    }
                    else {
                        if (value != null || !valueReference.isActive()) {
                            this.unlock();
                            this.postWriteCleanup();
                            return false;
                        }
                        removalCause = RemovalCause.COLLECTED;
                    }
                    ++this.modCount;
                    final ReferenceEntry removeValueFromChain = this.removeValueFromChain(referenceEntry, next, key, n, valueReference, removalCause);
                    final int count = this.count - 1;
                    table.set(n3, removeValueFromChain);
                    this.count = count;
                    final boolean b = removalCause == RemovalCause.EXPLICIT;
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
                while (0 < table.length()) {
                    for (ReferenceEntry next = table.get(0); next != null; next = next.getNext()) {
                        if (next.getValueReference().isActive()) {
                            this.enqueueNotification(next, RemovalCause.EXPLICIT);
                        }
                    }
                    ++n;
                }
                while (0 < table.length()) {
                    table.set(0, null);
                    ++n;
                }
                this.clearReferenceQueues();
                this.writeQueue.clear();
                this.accessQueue.clear();
                this.readCount.set(0);
                ++this.modCount;
                this.count = 0;
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        @Nullable
        @GuardedBy("Segment.this")
        ReferenceEntry removeValueFromChain(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2, @Nullable final Object o, final int n, final ValueReference valueReference, final RemovalCause removalCause) {
            this.enqueueNotification(o, n, valueReference, removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (valueReference.isLoading()) {
                valueReference.notifyNewValue(null);
                return referenceEntry;
            }
            return this.removeEntryFromChain(referenceEntry, referenceEntry2);
        }
        
        @Nullable
        @GuardedBy("Segment.this")
        ReferenceEntry removeEntryFromChain(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
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
        
        @GuardedBy("Segment.this")
        void removeCollectedEntry(final ReferenceEntry referenceEntry) {
            this.enqueueNotification(referenceEntry, RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
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
                    final ReferenceEntry removeValueFromChain = this.removeValueFromChain(referenceEntry2, next, next.getKey(), n, next.getValueReference(), RemovalCause.COLLECTED);
                    final int count = this.count - 1;
                    table.set(n3, removeValueFromChain);
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
                        final ReferenceEntry removeValueFromChain = this.removeValueFromChain(referenceEntry, next, key, n, valueReference, RemovalCause.COLLECTED);
                        final int count = this.count - 1;
                        table.set(n3, removeValueFromChain);
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
        
        boolean removeLoadingValue(final Object o, final int n, final LoadingValueReference loadingValueReference) {
            this.lock();
            final AtomicReferenceArray table = this.table;
            final int n2 = n & table.length() - 1;
            ReferenceEntry next;
            final ReferenceEntry referenceEntry = next = table.get(n2);
            while (next != null) {
                final Object key = next.getKey();
                if (next.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                    if (next.getValueReference() == loadingValueReference) {
                        if (loadingValueReference.isActive()) {
                            next.setValueReference(loadingValueReference.getOldValue());
                        }
                        else {
                            table.set(n2, this.removeEntryFromChain(referenceEntry, next));
                        }
                        this.unlock();
                        this.postWriteCleanup();
                        return false;
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
        
        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 0x3F) == 0x0) {
                this.cleanUp();
            }
        }
        
        @GuardedBy("Segment.this")
        void preWriteCleanup(final long n) {
            this.runLockedCleanup(n);
        }
        
        void postWriteCleanup() {
            this.runUnlockedCleanup();
        }
        
        void cleanUp() {
            this.runLockedCleanup(this.map.ticker.read());
            this.runUnlockedCleanup();
        }
        
        void runLockedCleanup(final long n) {
            if (this.tryLock()) {
                this.drainReferenceQueues();
                this.expireEntries(n);
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
        
        @Nullable
        ReferenceEntry getNext();
        
        int getHash();
        
        @Nullable
        Object getKey();
        
        long getAccessTime();
        
        void setAccessTime(final long p0);
        
        ReferenceEntry getNextInAccessQueue();
        
        void setNextInAccessQueue(final ReferenceEntry p0);
        
        ReferenceEntry getPreviousInAccessQueue();
        
        void setPreviousInAccessQueue(final ReferenceEntry p0);
        
        long getWriteTime();
        
        void setWriteTime(final long p0);
        
        ReferenceEntry getNextInWriteQueue();
        
        void setNextInWriteQueue(final ReferenceEntry p0);
        
        ReferenceEntry getPreviousInWriteQueue();
        
        void setPreviousInWriteQueue(final ReferenceEntry p0);
    }
    
    interface ValueReference
    {
        @Nullable
        Object get();
        
        Object waitForValue() throws ExecutionException;
        
        int getWeight();
        
        @Nullable
        ReferenceEntry getEntry();
        
        ValueReference copyFor(final ReferenceQueue p0, @Nullable final Object p1, final ReferenceEntry p2);
        
        void notifyNewValue(@Nullable final Object p0);
        
        boolean isLoading();
        
        boolean isActive();
    }
    
    static class LoadingValueReference implements ValueReference
    {
        ValueReference oldValue;
        final SettableFuture futureValue;
        final Stopwatch stopwatch;
        
        public LoadingValueReference() {
            this(LocalCache.unset());
        }
        
        public LoadingValueReference(final ValueReference oldValue) {
            this.futureValue = SettableFuture.create();
            this.stopwatch = Stopwatch.createUnstarted();
            this.oldValue = oldValue;
        }
        
        @Override
        public boolean isLoading() {
            return true;
        }
        
        @Override
        public boolean isActive() {
            return this.oldValue.isActive();
        }
        
        @Override
        public int getWeight() {
            return this.oldValue.getWeight();
        }
        
        public boolean set(@Nullable final Object o) {
            return this.futureValue.set(o);
        }
        
        public boolean setException(final Throwable exception) {
            return this.futureValue.setException(exception);
        }
        
        private ListenableFuture fullyFailedFuture(final Throwable t) {
            return Futures.immediateFailedFuture(t);
        }
        
        @Override
        public void notifyNewValue(@Nullable final Object o) {
            if (o != null) {
                this.set(o);
            }
            else {
                this.oldValue = LocalCache.unset();
            }
        }
        
        public ListenableFuture loadFuture(final Object o, final CacheLoader cacheLoader) {
            this.stopwatch.start();
            final Object value = this.oldValue.get();
            if (value == null) {
                final Object load = cacheLoader.load(o);
                return this.set(load) ? this.futureValue : Futures.immediateFuture(load);
            }
            final ListenableFuture reload = cacheLoader.reload(o, value);
            if (reload == null) {
                return Futures.immediateFuture(null);
            }
            return Futures.transform(reload, new Function() {
                final LoadingValueReference this$0;
                
                @Override
                public Object apply(final Object o) {
                    this.this$0.set(o);
                    return o;
                }
            });
        }
        
        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }
        
        @Override
        public Object waitForValue() throws ExecutionException {
            return Uninterruptibles.getUninterruptibly(this.futureValue);
        }
        
        @Override
        public Object get() {
            return this.oldValue.get();
        }
        
        public ValueReference getOldValue() {
            return this.oldValue;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return null;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, @Nullable final Object o, final ReferenceEntry referenceEntry) {
            return this;
        }
    }
    
    static final class WriteQueue extends AbstractQueue
    {
        final ReferenceEntry head;
        
        WriteQueue() {
            this.head = new AbstractReferenceEntry() {
                ReferenceEntry nextWrite = this;
                ReferenceEntry previousWrite = this;
                final WriteQueue this$0;
                
                @Override
                public long getWriteTime() {
                    return Long.MAX_VALUE;
                }
                
                @Override
                public void setWriteTime(final long n) {
                }
                
                @Override
                public ReferenceEntry getNextInWriteQueue() {
                    return this.nextWrite;
                }
                
                @Override
                public void setNextInWriteQueue(final ReferenceEntry nextWrite) {
                    this.nextWrite = nextWrite;
                }
                
                @Override
                public ReferenceEntry getPreviousInWriteQueue() {
                    return this.previousWrite;
                }
                
                @Override
                public void setPreviousInWriteQueue(final ReferenceEntry previousWrite) {
                    this.previousWrite = previousWrite;
                }
            };
        }
        
        public boolean offer(final ReferenceEntry referenceEntry) {
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), referenceEntry);
            LocalCache.connectWriteOrder(referenceEntry, this.head);
            return true;
        }
        
        @Override
        public ReferenceEntry peek() {
            final ReferenceEntry nextInWriteQueue = this.head.getNextInWriteQueue();
            return (nextInWriteQueue == this.head) ? null : nextInWriteQueue;
        }
        
        @Override
        public ReferenceEntry poll() {
            final ReferenceEntry nextInWriteQueue = this.head.getNextInWriteQueue();
            if (nextInWriteQueue == this.head) {
                return null;
            }
            this.remove(nextInWriteQueue);
            return nextInWriteQueue;
        }
        
        @Override
        public boolean remove(final Object o) {
            final ReferenceEntry referenceEntry = (ReferenceEntry)o;
            final ReferenceEntry previousInWriteQueue = referenceEntry.getPreviousInWriteQueue();
            final ReferenceEntry nextInWriteQueue = referenceEntry.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previousInWriteQueue, nextInWriteQueue);
            LocalCache.nullifyWriteOrder(referenceEntry);
            return nextInWriteQueue != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean contains(final Object o) {
            return ((ReferenceEntry)o).getNextInWriteQueue() != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }
        
        @Override
        public int size() {
            for (ReferenceEntry referenceEntry = this.head.getNextInWriteQueue(); referenceEntry != this.head; referenceEntry = referenceEntry.getNextInWriteQueue()) {
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        @Override
        public void clear() {
            ReferenceEntry nextInWriteQueue2;
            for (ReferenceEntry nextInWriteQueue = this.head.getNextInWriteQueue(); nextInWriteQueue != this.head; nextInWriteQueue = nextInWriteQueue2) {
                nextInWriteQueue2 = nextInWriteQueue.getNextInWriteQueue();
                LocalCache.nullifyWriteOrder(nextInWriteQueue);
            }
            this.head.setNextInWriteQueue(this.head);
            this.head.setPreviousInWriteQueue(this.head);
        }
        
        @Override
        public Iterator iterator() {
            return new AbstractSequentialIterator(this.peek()) {
                final WriteQueue this$0;
                
                protected ReferenceEntry computeNext(final ReferenceEntry referenceEntry) {
                    final ReferenceEntry nextInWriteQueue = referenceEntry.getNextInWriteQueue();
                    return (nextInWriteQueue == this.this$0.head) ? null : nextInWriteQueue;
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
        public long getAccessTime() {
            return 0L;
        }
        
        @Override
        public void setAccessTime(final long n) {
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            return this;
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry referenceEntry) {
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            return this;
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry referenceEntry) {
        }
        
        @Override
        public long getWriteTime() {
            return 0L;
        }
        
        @Override
        public void setWriteTime(final long n) {
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            return this;
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry referenceEntry) {
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            return this;
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry referenceEntry) {
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
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setAccessTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setWriteTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
    }
    
    static final class AccessQueue extends AbstractQueue
    {
        final ReferenceEntry head;
        
        AccessQueue() {
            this.head = new AbstractReferenceEntry() {
                ReferenceEntry nextAccess = this;
                ReferenceEntry previousAccess = this;
                final AccessQueue this$0;
                
                @Override
                public long getAccessTime() {
                    return Long.MAX_VALUE;
                }
                
                @Override
                public void setAccessTime(final long n) {
                }
                
                @Override
                public ReferenceEntry getNextInAccessQueue() {
                    return this.nextAccess;
                }
                
                @Override
                public void setNextInAccessQueue(final ReferenceEntry nextAccess) {
                    this.nextAccess = nextAccess;
                }
                
                @Override
                public ReferenceEntry getPreviousInAccessQueue() {
                    return this.previousAccess;
                }
                
                @Override
                public void setPreviousInAccessQueue(final ReferenceEntry previousAccess) {
                    this.previousAccess = previousAccess;
                }
            };
        }
        
        public boolean offer(final ReferenceEntry referenceEntry) {
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), referenceEntry);
            LocalCache.connectAccessOrder(referenceEntry, this.head);
            return true;
        }
        
        @Override
        public ReferenceEntry peek() {
            final ReferenceEntry nextInAccessQueue = this.head.getNextInAccessQueue();
            return (nextInAccessQueue == this.head) ? null : nextInAccessQueue;
        }
        
        @Override
        public ReferenceEntry poll() {
            final ReferenceEntry nextInAccessQueue = this.head.getNextInAccessQueue();
            if (nextInAccessQueue == this.head) {
                return null;
            }
            this.remove(nextInAccessQueue);
            return nextInAccessQueue;
        }
        
        @Override
        public boolean remove(final Object o) {
            final ReferenceEntry referenceEntry = (ReferenceEntry)o;
            final ReferenceEntry previousInAccessQueue = referenceEntry.getPreviousInAccessQueue();
            final ReferenceEntry nextInAccessQueue = referenceEntry.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previousInAccessQueue, nextInAccessQueue);
            LocalCache.nullifyAccessOrder(referenceEntry);
            return nextInAccessQueue != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean contains(final Object o) {
            return ((ReferenceEntry)o).getNextInAccessQueue() != NullEntry.INSTANCE;
        }
        
        @Override
        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }
        
        @Override
        public int size() {
            for (ReferenceEntry referenceEntry = this.head.getNextInAccessQueue(); referenceEntry != this.head; referenceEntry = referenceEntry.getNextInAccessQueue()) {
                int n = 0;
                ++n;
            }
            return 0;
        }
        
        @Override
        public void clear() {
            ReferenceEntry nextInAccessQueue2;
            for (ReferenceEntry nextInAccessQueue = this.head.getNextInAccessQueue(); nextInAccessQueue != this.head; nextInAccessQueue = nextInAccessQueue2) {
                nextInAccessQueue2 = nextInAccessQueue.getNextInAccessQueue();
                LocalCache.nullifyAccessOrder(nextInAccessQueue);
            }
            this.head.setNextInAccessQueue(this.head);
            this.head.setPreviousInAccessQueue(this.head);
        }
        
        @Override
        public Iterator iterator() {
            return new AbstractSequentialIterator(this.peek()) {
                final AccessQueue this$0;
                
                protected ReferenceEntry computeNext(final ReferenceEntry referenceEntry) {
                    final ReferenceEntry nextInAccessQueue = referenceEntry.getNextInAccessQueue();
                    return (nextInAccessQueue == this.this$0.head) ? null : nextInAccessQueue;
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
    
    static final class WeightedStrongValueReference extends StrongValueReference
    {
        final int weight;
        
        WeightedStrongValueReference(final Object o, final int weight) {
            super(o);
            this.weight = weight;
        }
        
        @Override
        public int getWeight() {
            return this.weight;
        }
    }
    
    static class StrongValueReference implements ValueReference
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
        public int getWeight() {
            return 1;
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
        public boolean isLoading() {
            return false;
        }
        
        @Override
        public boolean isActive() {
            return true;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
        
        @Override
        public void notifyNewValue(final Object o) {
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
        STRONG_ACCESS {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongAccessEntry(o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        STRONG_WRITE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongWriteEntry(o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        STRONG_ACCESS_WRITE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new StrongAccessWriteEntry(o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, copyEntry);
                this.copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        WEAK {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
        }, 
        WEAK_ACCESS {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakAccessEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        WEAK_WRITE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakWriteEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        }, 
        WEAK_ACCESS_WRITE {
            @Override
            ReferenceEntry newEntry(final Segment segment, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, o, n, referenceEntry);
            }
            
            @Override
            ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
                final ReferenceEntry copyEntry = super.copyEntry(segment, referenceEntry, referenceEntry2);
                this.copyAccessEntry(referenceEntry, copyEntry);
                this.copyWriteEntry(referenceEntry, copyEntry);
                return copyEntry;
            }
        };
        
        static final int ACCESS_MASK = 1;
        static final int WRITE_MASK = 2;
        static final int WEAK_MASK = 4;
        static final EntryFactory[] factories;
        private static final EntryFactory[] $VALUES;
        
        private EntryFactory(final String s, final int n) {
        }
        
        static EntryFactory getFactory(final Strength strength, final boolean b, final boolean b2) {
            return EntryFactory.factories[((strength == Strength.WEAK) ? 4 : 0) | (b ? 1 : 0) | (b2 ? 2 : 0)];
        }
        
        abstract ReferenceEntry newEntry(final Segment p0, final Object p1, final int p2, @Nullable final ReferenceEntry p3);
        
        @GuardedBy("Segment.this")
        ReferenceEntry copyEntry(final Segment segment, final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            return this.newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }
        
        @GuardedBy("Segment.this")
        void copyAccessEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            LocalCache.connectAccessOrder(referenceEntry2, referenceEntry.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(referenceEntry);
        }
        
        @GuardedBy("Segment.this")
        void copyWriteEntry(final ReferenceEntry referenceEntry, final ReferenceEntry referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            LocalCache.connectWriteOrder(referenceEntry2, referenceEntry.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(referenceEntry);
        }
        
        EntryFactory(final String s, final int n, final LocalCache$1 valueReference) {
            this(s, n);
        }
        
        static {
            $VALUES = new EntryFactory[] { EntryFactory.STRONG, EntryFactory.STRONG_ACCESS, EntryFactory.STRONG_WRITE, EntryFactory.STRONG_ACCESS_WRITE, EntryFactory.WEAK, EntryFactory.WEAK_ACCESS, EntryFactory.WEAK_WRITE, EntryFactory.WEAK_ACCESS_WRITE };
            factories = new EntryFactory[] { EntryFactory.STRONG, EntryFactory.STRONG_ACCESS, EntryFactory.STRONG_WRITE, EntryFactory.STRONG_ACCESS_WRITE, EntryFactory.WEAK, EntryFactory.WEAK_ACCESS, EntryFactory.WEAK_WRITE, EntryFactory.WEAK_ACCESS_WRITE };
        }
    }
    
    enum Strength
    {
        STRONG {
            @Override
            ValueReference referenceValue(final Segment segment, final ReferenceEntry referenceEntry, final Object o, final int n) {
                return (n == 1) ? new StrongValueReference(o) : new WeightedStrongValueReference(o, n);
            }
            
            @Override
            Equivalence defaultEquivalence() {
                return Equivalence.equals();
            }
        }, 
        SOFT {
            @Override
            ValueReference referenceValue(final Segment segment, final ReferenceEntry referenceEntry, final Object o, final int n) {
                return (n == 1) ? new SoftValueReference(segment.valueReferenceQueue, o, referenceEntry) : new WeightedSoftValueReference(segment.valueReferenceQueue, o, referenceEntry, n);
            }
            
            @Override
            Equivalence defaultEquivalence() {
                return Equivalence.identity();
            }
        }, 
        WEAK {
            @Override
            ValueReference referenceValue(final Segment segment, final ReferenceEntry referenceEntry, final Object o, final int n) {
                return (n == 1) ? new WeakValueReference(segment.valueReferenceQueue, o, referenceEntry) : new WeightedWeakValueReference(segment.valueReferenceQueue, o, referenceEntry, n);
            }
            
            @Override
            Equivalence defaultEquivalence() {
                return Equivalence.identity();
            }
        };
        
        private static final Strength[] $VALUES;
        
        private Strength(final String s, final int n) {
        }
        
        abstract ValueReference referenceValue(final Segment p0, final ReferenceEntry p1, final Object p2, final int p3);
        
        abstract Equivalence defaultEquivalence();
        
        Strength(final String s, final int n, final LocalCache$1 valueReference) {
            this(s, n);
        }
        
        static {
            $VALUES = new Strength[] { Strength.STRONG, Strength.SOFT, Strength.WEAK };
        }
    }
    
    static class WeakValueReference extends WeakReference implements ValueReference
    {
        final ReferenceEntry entry;
        
        WeakValueReference(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry entry) {
            super(o, referenceQueue);
            this.entry = entry;
        }
        
        @Override
        public int getWeight() {
            return 1;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return this.entry;
        }
        
        @Override
        public void notifyNewValue(final Object o) {
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return new WeakValueReference(referenceQueue, o, referenceEntry);
        }
        
        @Override
        public boolean isLoading() {
            return false;
        }
        
        @Override
        public boolean isActive() {
            return true;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
    }
    
    static final class WeightedWeakValueReference extends WeakValueReference
    {
        final int weight;
        
        WeightedWeakValueReference(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry, final int weight) {
            super(referenceQueue, o, referenceEntry);
            this.weight = weight;
        }
        
        @Override
        public int getWeight() {
            return this.weight;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return new WeightedWeakValueReference(referenceQueue, o, referenceEntry, this.weight);
        }
    }
    
    static class SoftValueReference extends SoftReference implements ValueReference
    {
        final ReferenceEntry entry;
        
        SoftValueReference(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry entry) {
            super(o, referenceQueue);
            this.entry = entry;
        }
        
        @Override
        public int getWeight() {
            return 1;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return this.entry;
        }
        
        @Override
        public void notifyNewValue(final Object o) {
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return new SoftValueReference(referenceQueue, o, referenceEntry);
        }
        
        @Override
        public boolean isLoading() {
            return false;
        }
        
        @Override
        public boolean isActive() {
            return true;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
    }
    
    static final class WeightedSoftValueReference extends SoftValueReference
    {
        final int weight;
        
        WeightedSoftValueReference(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry, final int weight) {
            super(referenceQueue, o, referenceEntry);
            this.weight = weight;
        }
        
        @Override
        public int getWeight() {
            return this.weight;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return new WeightedSoftValueReference(referenceQueue, o, referenceEntry, this.weight);
        }
    }
    
    static final class WeakAccessWriteEntry extends WeakEntry
    {
        long accessTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextAccess;
        @GuardedBy("Segment.this")
        ReferenceEntry previousAccess;
        long writeTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextWrite;
        @GuardedBy("Segment.this")
        ReferenceEntry previousWrite;
        
        WeakAccessWriteEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }
        
        @Override
        public long getAccessTime() {
            return this.accessTime;
        }
        
        @Override
        public void setAccessTime(final long accessTime) {
            this.accessTime = accessTime;
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            return this.nextAccess;
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry nextAccess) {
            this.nextAccess = nextAccess;
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            return this.previousAccess;
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry previousAccess) {
            this.previousAccess = previousAccess;
        }
        
        @Override
        public long getWriteTime() {
            return this.writeTime;
        }
        
        @Override
        public void setWriteTime(final long writeTime) {
            this.writeTime = writeTime;
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            return this.nextWrite;
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry nextWrite) {
            this.nextWrite = nextWrite;
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            return this.previousWrite;
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry previousWrite) {
            this.previousWrite = previousWrite;
        }
    }
    
    static class WeakEntry extends WeakReference implements ReferenceEntry
    {
        final int hash;
        final ReferenceEntry next;
        ValueReference valueReference;
        
        WeakEntry(final ReferenceQueue referenceQueue, final Object o, final int hash, @Nullable final ReferenceEntry next) {
            super(o, referenceQueue);
            this.valueReference = LocalCache.unset();
            this.hash = hash;
            this.next = next;
        }
        
        @Override
        public Object getKey() {
            return this.get();
        }
        
        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setAccessTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setWriteTime(final long n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry referenceEntry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ValueReference getValueReference() {
            return this.valueReference;
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
            this.valueReference = valueReference;
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
    
    static final class WeakWriteEntry extends WeakEntry
    {
        long writeTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextWrite;
        @GuardedBy("Segment.this")
        ReferenceEntry previousWrite;
        
        WeakWriteEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }
        
        @Override
        public long getWriteTime() {
            return this.writeTime;
        }
        
        @Override
        public void setWriteTime(final long writeTime) {
            this.writeTime = writeTime;
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            return this.nextWrite;
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry nextWrite) {
            this.nextWrite = nextWrite;
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            return this.previousWrite;
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry previousWrite) {
            this.previousWrite = previousWrite;
        }
    }
    
    static final class WeakAccessEntry extends WeakEntry
    {
        long accessTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextAccess;
        @GuardedBy("Segment.this")
        ReferenceEntry previousAccess;
        
        WeakAccessEntry(final ReferenceQueue referenceQueue, final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(referenceQueue, o, n, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
        }
        
        @Override
        public long getAccessTime() {
            return this.accessTime;
        }
        
        @Override
        public void setAccessTime(final long accessTime) {
            this.accessTime = accessTime;
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            return this.nextAccess;
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry nextAccess) {
            this.nextAccess = nextAccess;
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            return this.previousAccess;
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry previousAccess) {
            this.previousAccess = previousAccess;
        }
    }
    
    static final class StrongAccessWriteEntry extends StrongEntry
    {
        long accessTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextAccess;
        @GuardedBy("Segment.this")
        ReferenceEntry previousAccess;
        long writeTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextWrite;
        @GuardedBy("Segment.this")
        ReferenceEntry previousWrite;
        
        StrongAccessWriteEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(o, n, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }
        
        @Override
        public long getAccessTime() {
            return this.accessTime;
        }
        
        @Override
        public void setAccessTime(final long accessTime) {
            this.accessTime = accessTime;
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            return this.nextAccess;
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry nextAccess) {
            this.nextAccess = nextAccess;
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            return this.previousAccess;
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry previousAccess) {
            this.previousAccess = previousAccess;
        }
        
        @Override
        public long getWriteTime() {
            return this.writeTime;
        }
        
        @Override
        public void setWriteTime(final long writeTime) {
            this.writeTime = writeTime;
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            return this.nextWrite;
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry nextWrite) {
            this.nextWrite = nextWrite;
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            return this.previousWrite;
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry previousWrite) {
            this.previousWrite = previousWrite;
        }
    }
    
    static class StrongEntry extends AbstractReferenceEntry
    {
        final Object key;
        final int hash;
        final ReferenceEntry next;
        ValueReference valueReference;
        
        StrongEntry(final Object key, final int hash, @Nullable final ReferenceEntry next) {
            this.valueReference = LocalCache.unset();
            this.key = key;
            this.hash = hash;
            this.next = next;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public ValueReference getValueReference() {
            return this.valueReference;
        }
        
        @Override
        public void setValueReference(final ValueReference valueReference) {
            this.valueReference = valueReference;
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
    
    static final class StrongWriteEntry extends StrongEntry
    {
        long writeTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextWrite;
        @GuardedBy("Segment.this")
        ReferenceEntry previousWrite;
        
        StrongWriteEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(o, n, referenceEntry);
            this.writeTime = Long.MAX_VALUE;
            this.nextWrite = LocalCache.nullEntry();
            this.previousWrite = LocalCache.nullEntry();
        }
        
        @Override
        public long getWriteTime() {
            return this.writeTime;
        }
        
        @Override
        public void setWriteTime(final long writeTime) {
            this.writeTime = writeTime;
        }
        
        @Override
        public ReferenceEntry getNextInWriteQueue() {
            return this.nextWrite;
        }
        
        @Override
        public void setNextInWriteQueue(final ReferenceEntry nextWrite) {
            this.nextWrite = nextWrite;
        }
        
        @Override
        public ReferenceEntry getPreviousInWriteQueue() {
            return this.previousWrite;
        }
        
        @Override
        public void setPreviousInWriteQueue(final ReferenceEntry previousWrite) {
            this.previousWrite = previousWrite;
        }
    }
    
    static final class StrongAccessEntry extends StrongEntry
    {
        long accessTime;
        @GuardedBy("Segment.this")
        ReferenceEntry nextAccess;
        @GuardedBy("Segment.this")
        ReferenceEntry previousAccess;
        
        StrongAccessEntry(final Object o, final int n, @Nullable final ReferenceEntry referenceEntry) {
            super(o, n, referenceEntry);
            this.accessTime = Long.MAX_VALUE;
            this.nextAccess = LocalCache.nullEntry();
            this.previousAccess = LocalCache.nullEntry();
        }
        
        @Override
        public long getAccessTime() {
            return this.accessTime;
        }
        
        @Override
        public void setAccessTime(final long accessTime) {
            this.accessTime = accessTime;
        }
        
        @Override
        public ReferenceEntry getNextInAccessQueue() {
            return this.nextAccess;
        }
        
        @Override
        public void setNextInAccessQueue(final ReferenceEntry nextAccess) {
            this.nextAccess = nextAccess;
        }
        
        @Override
        public ReferenceEntry getPreviousInAccessQueue() {
            return this.previousAccess;
        }
        
        @Override
        public void setPreviousInAccessQueue(final ReferenceEntry previousAccess) {
            this.previousAccess = previousAccess;
        }
    }
    
    static class ManualSerializationProxy extends ForwardingCache implements Serializable
    {
        private static final long serialVersionUID = 1L;
        final Strength keyStrength;
        final Strength valueStrength;
        final Equivalence keyEquivalence;
        final Equivalence valueEquivalence;
        final long expireAfterWriteNanos;
        final long expireAfterAccessNanos;
        final long maxWeight;
        final Weigher weigher;
        final int concurrencyLevel;
        final RemovalListener removalListener;
        final Ticker ticker;
        final CacheLoader loader;
        transient Cache delegate;
        
        ManualSerializationProxy(final LocalCache localCache) {
            this(localCache.keyStrength, localCache.valueStrength, localCache.keyEquivalence, localCache.valueEquivalence, localCache.expireAfterWriteNanos, localCache.expireAfterAccessNanos, localCache.maxWeight, localCache.weigher, localCache.concurrencyLevel, localCache.removalListener, localCache.ticker, localCache.defaultLoader);
        }
        
        private ManualSerializationProxy(final Strength keyStrength, final Strength valueStrength, final Equivalence keyEquivalence, final Equivalence valueEquivalence, final long expireAfterWriteNanos, final long expireAfterAccessNanos, final long maxWeight, final Weigher weigher, final int concurrencyLevel, final RemovalListener removalListener, final Ticker ticker, final CacheLoader loader) {
            this.keyStrength = keyStrength;
            this.valueStrength = valueStrength;
            this.keyEquivalence = keyEquivalence;
            this.valueEquivalence = valueEquivalence;
            this.expireAfterWriteNanos = expireAfterWriteNanos;
            this.expireAfterAccessNanos = expireAfterAccessNanos;
            this.maxWeight = maxWeight;
            this.weigher = weigher;
            this.concurrencyLevel = concurrencyLevel;
            this.removalListener = removalListener;
            this.ticker = ((ticker == Ticker.systemTicker() || ticker == CacheBuilder.NULL_TICKER) ? null : ticker);
            this.loader = loader;
        }
        
        CacheBuilder recreateCacheBuilder() {
            final CacheBuilder removalListener = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            removalListener.strictParsing = false;
            if (this.expireAfterWriteNanos > 0L) {
                removalListener.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
            }
            if (this.expireAfterAccessNanos > 0L) {
                removalListener.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
                removalListener.weigher(this.weigher);
                if (this.maxWeight != -1L) {
                    removalListener.maximumWeight(this.maxWeight);
                }
            }
            else if (this.maxWeight != -1L) {
                removalListener.maximumSize(this.maxWeight);
            }
            if (this.ticker != null) {
                removalListener.ticker(this.ticker);
            }
            return removalListener;
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.delegate = this.recreateCacheBuilder().build();
        }
        
        private Object readResolve() {
            return this.delegate;
        }
        
        @Override
        protected Cache delegate() {
            return this.delegate;
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
    
    static final class LoadingSerializationProxy extends ManualSerializationProxy implements LoadingCache, Serializable
    {
        private static final long serialVersionUID = 1L;
        transient LoadingCache autoDelegate;
        
        LoadingSerializationProxy(final LocalCache localCache) {
            super(localCache);
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.autoDelegate = this.recreateCacheBuilder().build(this.loader);
        }
        
        @Override
        public Object get(final Object o) throws ExecutionException {
            return this.autoDelegate.get(o);
        }
        
        @Override
        public Object getUnchecked(final Object o) {
            return this.autoDelegate.getUnchecked(o);
        }
        
        @Override
        public ImmutableMap getAll(final Iterable iterable) throws ExecutionException {
            return this.autoDelegate.getAll(iterable);
        }
        
        @Override
        public final Object apply(final Object o) {
            return this.autoDelegate.apply(o);
        }
        
        @Override
        public void refresh(final Object o) {
            this.autoDelegate.refresh(o);
        }
        
        private Object readResolve() {
            return this.autoDelegate;
        }
    }
    
    final class EntrySet extends AbstractCacheSet
    {
        final LocalCache this$0;
        
        EntrySet(final LocalCache this$0, final ConcurrentMap concurrentMap) {
            this.this$0 = this$0.super(concurrentMap);
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
    }
    
    abstract class AbstractCacheSet extends AbstractSet
    {
        final ConcurrentMap map;
        final LocalCache this$0;
        
        AbstractCacheSet(final LocalCache this$0, final ConcurrentMap map) {
            this.this$0 = this$0;
            this.map = map;
        }
        
        @Override
        public int size() {
            return this.map.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }
        
        @Override
        public void clear() {
            this.map.clear();
        }
    }
    
    final class EntryIterator extends HashIterator
    {
        final LocalCache this$0;
        
        EntryIterator(final LocalCache this$0) {
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
        final LocalCache this$0;
        
        HashIterator(final LocalCache this$0) {
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
            final long read = this.this$0.ticker.read();
            final Object key = referenceEntry.getKey();
            final Object liveValue = this.this$0.getLiveValue(referenceEntry, read);
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
            Preconditions.checkState(this.lastReturned != null);
            this.this$0.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }
    
    final class WriteThroughEntry implements Map.Entry
    {
        final Object key;
        Object value;
        final LocalCache this$0;
        
        WriteThroughEntry(final LocalCache this$0, final Object key, final Object value) {
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
        public Object setValue(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
    
    final class Values extends AbstractCollection
    {
        private final ConcurrentMap map;
        final LocalCache this$0;
        
        Values(final LocalCache this$0, final ConcurrentMap map) {
            this.this$0 = this$0;
            this.map = map;
        }
        
        @Override
        public int size() {
            return this.map.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }
        
        @Override
        public void clear() {
            this.map.clear();
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.new ValueIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.map.containsValue(o);
        }
    }
    
    final class ValueIterator extends HashIterator
    {
        final LocalCache this$0;
        
        ValueIterator(final LocalCache this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Object next() {
            return this.nextEntry().getValue();
        }
    }
    
    final class KeySet extends AbstractCacheSet
    {
        final LocalCache this$0;
        
        KeySet(final LocalCache this$0, final ConcurrentMap concurrentMap) {
            this.this$0 = this$0.super(concurrentMap);
        }
        
        @Override
        public Iterator iterator() {
            return this.this$0.new KeyIterator();
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.map.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.map.remove(o) != null;
        }
    }
    
    final class KeyIterator extends HashIterator
    {
        final LocalCache this$0;
        
        KeyIterator(final LocalCache this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public Object next() {
            return this.nextEntry().getKey();
        }
    }
}
