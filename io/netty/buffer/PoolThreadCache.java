package io.netty.buffer;

import io.netty.util.*;
import io.netty.util.internal.logging.*;

final class PoolThreadCache
{
    private static final InternalLogger logger;
    final PoolArena heapArena;
    final PoolArena directArena;
    private final MemoryRegionCache[] tinySubPageHeapCaches;
    private final MemoryRegionCache[] smallSubPageHeapCaches;
    private final MemoryRegionCache[] tinySubPageDirectCaches;
    private final MemoryRegionCache[] smallSubPageDirectCaches;
    private final MemoryRegionCache[] normalHeapCaches;
    private final MemoryRegionCache[] normalDirectCaches;
    private final int numShiftsNormalDirect;
    private final int numShiftsNormalHeap;
    private final int freeSweepAllocationThreshold;
    private int allocations;
    private final Thread thread;
    private final Runnable freeTask;
    
    PoolThreadCache(final PoolArena heapArena, final PoolArena directArena, final int n, final int n2, final int n3, final int n4, final int freeSweepAllocationThreshold) {
        this.thread = Thread.currentThread();
        this.freeTask = new Runnable() {
            final PoolThreadCache this$0;
            
            @Override
            public void run() {
                PoolThreadCache.access$000(this.this$0);
            }
        };
        if (n4 < 0) {
            throw new IllegalArgumentException("maxCachedBufferCapacity: " + n4 + " (expected: >= 0)");
        }
        if (freeSweepAllocationThreshold < 1) {
            throw new IllegalArgumentException("freeSweepAllocationThreshold: " + n4 + " (expected: > 0)");
        }
        this.freeSweepAllocationThreshold = freeSweepAllocationThreshold;
        this.heapArena = heapArena;
        if ((this.directArena = directArena) != null) {
            this.tinySubPageDirectCaches = createSubPageCaches(n, 32);
            this.smallSubPageDirectCaches = createSubPageCaches(n2, directArena.numSmallSubpagePools);
            this.numShiftsNormalDirect = log2(directArena.pageSize);
            this.normalDirectCaches = createNormalCaches(n3, n4, directArena);
        }
        else {
            this.tinySubPageDirectCaches = null;
            this.smallSubPageDirectCaches = null;
            this.normalDirectCaches = null;
            this.numShiftsNormalDirect = -1;
        }
        if (heapArena != null) {
            this.tinySubPageHeapCaches = createSubPageCaches(n, 32);
            this.smallSubPageHeapCaches = createSubPageCaches(n2, heapArena.numSmallSubpagePools);
            this.numShiftsNormalHeap = log2(heapArena.pageSize);
            this.normalHeapCaches = createNormalCaches(n3, n4, heapArena);
        }
        else {
            this.tinySubPageHeapCaches = null;
            this.smallSubPageHeapCaches = null;
            this.normalHeapCaches = null;
            this.numShiftsNormalHeap = -1;
        }
        ThreadDeathWatcher.watch(this.thread, this.freeTask);
    }
    
    private static SubPageMemoryRegionCache[] createSubPageCaches(final int n, final int n2) {
        if (n > 0) {
            final SubPageMemoryRegionCache[] array = new SubPageMemoryRegionCache[n2];
            while (0 < array.length) {
                array[0] = new SubPageMemoryRegionCache(n);
                int n3 = 0;
                ++n3;
            }
            return array;
        }
        return null;
    }
    
    private static NormalMemoryRegionCache[] createNormalCaches(final int n, final int n2, final PoolArena poolArena) {
        if (n > 0) {
            final NormalMemoryRegionCache[] array = new NormalMemoryRegionCache[Math.max(1, Math.min(poolArena.chunkSize, n2) / poolArena.pageSize)];
            while (0 < array.length) {
                array[0] = new NormalMemoryRegionCache(n);
                int n3 = 0;
                ++n3;
            }
            return array;
        }
        return null;
    }
    
    private static int log2(int i) {
        while (i > 1) {
            i >>= 1;
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    boolean allocateTiny(final PoolArena poolArena, final PooledByteBuf pooledByteBuf, final int n, final int n2) {
        return this.allocate(this.cacheForTiny(poolArena, n2), pooledByteBuf, n);
    }
    
    boolean allocateSmall(final PoolArena poolArena, final PooledByteBuf pooledByteBuf, final int n, final int n2) {
        return this.allocate(this.cacheForSmall(poolArena, n2), pooledByteBuf, n);
    }
    
    boolean allocateNormal(final PoolArena poolArena, final PooledByteBuf pooledByteBuf, final int n, final int n2) {
        return this.allocate(this.cacheForNormal(poolArena, n2), pooledByteBuf, n);
    }
    
    private boolean allocate(final MemoryRegionCache memoryRegionCache, final PooledByteBuf pooledByteBuf, final int n) {
        if (memoryRegionCache == null) {
            return false;
        }
        final boolean allocate = memoryRegionCache.allocate(pooledByteBuf, n);
        if (++this.allocations >= this.freeSweepAllocationThreshold) {
            this.allocations = 0;
            this.trim();
        }
        return allocate;
    }
    
    boolean add(final PoolArena poolArena, final PoolChunk poolChunk, final long n, final int n2) {
        MemoryRegionCache memoryRegionCache;
        if (poolArena.isTinyOrSmall(n2)) {
            if (PoolArena.isTiny(n2)) {
                memoryRegionCache = this.cacheForTiny(poolArena, n2);
            }
            else {
                memoryRegionCache = this.cacheForSmall(poolArena, n2);
            }
        }
        else {
            memoryRegionCache = this.cacheForNormal(poolArena, n2);
        }
        return memoryRegionCache != null && memoryRegionCache.add(poolChunk, n);
    }
    
    void free() {
        ThreadDeathWatcher.unwatch(this.thread, this.freeTask);
        this.free0();
    }
    
    private void free0() {
        final int n = free(this.tinySubPageDirectCaches) + free(this.smallSubPageDirectCaches) + free(this.normalDirectCaches) + free(this.tinySubPageHeapCaches) + free(this.smallSubPageHeapCaches) + free(this.normalHeapCaches);
        if (n > 0 && PoolThreadCache.logger.isDebugEnabled()) {
            PoolThreadCache.logger.debug("Freed {} thread-local buffer(s) from thread: {}", (Object)n, this.thread.getName());
        }
    }
    
    private static int free(final MemoryRegionCache[] array) {
        if (array == null) {
            return 0;
        }
        while (0 < array.length) {
            final int n = 0 + free(array[0]);
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static int free(final MemoryRegionCache memoryRegionCache) {
        if (memoryRegionCache == null) {
            return 0;
        }
        return memoryRegionCache.free();
    }
    
    void trim() {
        trim(this.tinySubPageDirectCaches);
        trim(this.smallSubPageDirectCaches);
        trim(this.normalDirectCaches);
        trim(this.tinySubPageHeapCaches);
        trim(this.smallSubPageHeapCaches);
        trim(this.normalHeapCaches);
    }
    
    private static void trim(final MemoryRegionCache[] array) {
        if (array == null) {
            return;
        }
        while (0 < array.length) {
            trim(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    private static void trim(final MemoryRegionCache memoryRegionCache) {
        if (memoryRegionCache == null) {
            return;
        }
        MemoryRegionCache.access$100(memoryRegionCache);
    }
    
    private MemoryRegionCache cacheForTiny(final PoolArena poolArena, final int n) {
        final int tinyIdx = PoolArena.tinyIdx(n);
        if (poolArena.isDirect()) {
            return cache(this.tinySubPageDirectCaches, tinyIdx);
        }
        return cache(this.tinySubPageHeapCaches, tinyIdx);
    }
    
    private MemoryRegionCache cacheForSmall(final PoolArena poolArena, final int n) {
        final int smallIdx = PoolArena.smallIdx(n);
        if (poolArena.isDirect()) {
            return cache(this.smallSubPageDirectCaches, smallIdx);
        }
        return cache(this.smallSubPageHeapCaches, smallIdx);
    }
    
    private MemoryRegionCache cacheForNormal(final PoolArena poolArena, final int n) {
        if (poolArena.isDirect()) {
            return cache(this.normalDirectCaches, log2(n >> this.numShiftsNormalDirect));
        }
        return cache(this.normalHeapCaches, log2(n >> this.numShiftsNormalHeap));
    }
    
    private static MemoryRegionCache cache(final MemoryRegionCache[] array, final int n) {
        if (array == null || n > array.length - 1) {
            return null;
        }
        return array[n];
    }
    
    static void access$000(final PoolThreadCache poolThreadCache) {
        poolThreadCache.free0();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
    }
    
    private abstract static class MemoryRegionCache
    {
        private final Entry[] entries;
        private final int maxUnusedCached;
        private int head;
        private int tail;
        private int maxEntriesInUse;
        private int entriesInUse;
        
        MemoryRegionCache(final int n) {
            this.entries = new Entry[powerOfTwo(n)];
            while (0 < this.entries.length) {
                this.entries[0] = new Entry(null);
                int n2 = 0;
                ++n2;
            }
            this.maxUnusedCached = n / 2;
        }
        
        private static int powerOfTwo(int n) {
            if (n <= 2) {
                return 2;
            }
            n = (--n | n >> 1);
            n |= n >> 2;
            n |= n >> 4;
            n |= n >> 8;
            n |= n >> 16;
            return ++n;
        }
        
        protected abstract void initBuf(final PoolChunk p0, final long p1, final PooledByteBuf p2, final int p3);
        
        public boolean add(final PoolChunk chunk, final long handle) {
            final Entry entry = this.entries[this.tail];
            if (entry.chunk != null) {
                return false;
            }
            --this.entriesInUse;
            entry.chunk = chunk;
            entry.handle = handle;
            this.tail = this.nextIdx(this.tail);
            return true;
        }
        
        public boolean allocate(final PooledByteBuf pooledByteBuf, final int n) {
            final Entry entry = this.entries[this.head];
            if (entry.chunk == null) {
                return false;
            }
            ++this.entriesInUse;
            if (this.maxEntriesInUse < this.entriesInUse) {
                this.maxEntriesInUse = this.entriesInUse;
            }
            this.initBuf(entry.chunk, entry.handle, pooledByteBuf, n);
            entry.chunk = null;
            this.head = this.nextIdx(this.head);
            return true;
        }
        
        public int free() {
            this.entriesInUse = 0;
            this.maxEntriesInUse = 0;
            for (int n = this.head; freeEntry(this.entries[n]); n = this.nextIdx(n)) {
                int n2 = 0;
                ++n2;
            }
            return 0;
        }
        
        private void trim() {
            int i = this.size() - this.maxEntriesInUse;
            this.entriesInUse = 0;
            this.maxEntriesInUse = 0;
            if (i <= this.maxUnusedCached) {
                return;
            }
            int n = this.head;
            while (i > 0) {
                if (!freeEntry(this.entries[n])) {
                    return;
                }
                n = this.nextIdx(n);
                --i;
            }
        }
        
        private static boolean freeEntry(final Entry entry) {
            final PoolChunk chunk = entry.chunk;
            if (chunk == null) {
                return false;
            }
            // monitorenter(arena = chunk.arena)
            chunk.parent.free(chunk, entry.handle);
            // monitorexit(arena)
            entry.chunk = null;
            return true;
        }
        
        private int size() {
            return this.tail - this.head & this.entries.length - 1;
        }
        
        private int nextIdx(final int n) {
            return n + 1 & this.entries.length - 1;
        }
        
        static void access$100(final MemoryRegionCache memoryRegionCache) {
            memoryRegionCache.trim();
        }
        
        private static final class Entry
        {
            PoolChunk chunk;
            long handle;
            
            private Entry() {
            }
            
            Entry(final PoolThreadCache$1 runnable) {
                this();
            }
        }
    }
    
    private static final class NormalMemoryRegionCache extends MemoryRegionCache
    {
        NormalMemoryRegionCache(final int n) {
            super(n);
        }
        
        @Override
        protected void initBuf(final PoolChunk poolChunk, final long n, final PooledByteBuf pooledByteBuf, final int n2) {
            poolChunk.initBuf(pooledByteBuf, n, n2);
        }
    }
    
    private static final class SubPageMemoryRegionCache extends MemoryRegionCache
    {
        SubPageMemoryRegionCache(final int n) {
            super(n);
        }
        
        @Override
        protected void initBuf(final PoolChunk poolChunk, final long n, final PooledByteBuf pooledByteBuf, final int n2) {
            poolChunk.initBufWithSubpage(pooledByteBuf, n, n2);
        }
    }
}
