package io.netty.buffer;

import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import io.netty.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class PooledByteBufAllocator extends AbstractByteBufAllocator
{
    private static final InternalLogger logger;
    private static final int DEFAULT_NUM_HEAP_ARENA;
    private static final int DEFAULT_NUM_DIRECT_ARENA;
    private static final int DEFAULT_PAGE_SIZE;
    private static final int DEFAULT_MAX_ORDER;
    private static final int DEFAULT_TINY_CACHE_SIZE;
    private static final int DEFAULT_SMALL_CACHE_SIZE;
    private static final int DEFAULT_NORMAL_CACHE_SIZE;
    private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
    private static final int DEFAULT_CACHE_TRIM_INTERVAL;
    private static final int MIN_PAGE_SIZE = 4096;
    private static final int MAX_CHUNK_SIZE = 1073741824;
    public static final PooledByteBufAllocator DEFAULT;
    private final PoolArena[] heapArenas;
    private final PoolArena[] directArenas;
    private final int tinyCacheSize;
    private final int smallCacheSize;
    private final int normalCacheSize;
    final PoolThreadLocalCache threadCache;
    
    public PooledByteBufAllocator() {
        this(false);
    }
    
    public PooledByteBufAllocator(final boolean b) {
        this(b, PooledByteBufAllocator.DEFAULT_NUM_HEAP_ARENA, PooledByteBufAllocator.DEFAULT_NUM_DIRECT_ARENA, PooledByteBufAllocator.DEFAULT_PAGE_SIZE, PooledByteBufAllocator.DEFAULT_MAX_ORDER);
    }
    
    public PooledByteBufAllocator(final int n, final int n2, final int n3, final int n4) {
        this(false, n, n2, n3, n4);
    }
    
    public PooledByteBufAllocator(final boolean b, final int n, final int n2, final int n3, final int n4) {
        this(b, n, n2, n3, n4, PooledByteBufAllocator.DEFAULT_TINY_CACHE_SIZE, PooledByteBufAllocator.DEFAULT_SMALL_CACHE_SIZE, PooledByteBufAllocator.DEFAULT_NORMAL_CACHE_SIZE);
    }
    
    public PooledByteBufAllocator(final boolean b, final int n, final int n2, final int n3, final int n4, final int tinyCacheSize, final int smallCacheSize, final int normalCacheSize) {
        super(b);
        this.threadCache = new PoolThreadLocalCache();
        this.tinyCacheSize = tinyCacheSize;
        this.smallCacheSize = smallCacheSize;
        this.normalCacheSize = normalCacheSize;
        final int validateAndCalculateChunkSize = validateAndCalculateChunkSize(n3, n4);
        if (n < 0) {
            throw new IllegalArgumentException("nHeapArena: " + n + " (expected: >= 0)");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("nDirectArea: " + n2 + " (expected: >= 0)");
        }
        final int validateAndCalculatePageShifts = validateAndCalculatePageShifts(n3);
        int n5 = 0;
        if (n > 0) {
            this.heapArenas = newArenaArray(n);
            while (0 < this.heapArenas.length) {
                this.heapArenas[0] = new PoolArena.HeapArena(this, n3, n4, validateAndCalculatePageShifts, validateAndCalculateChunkSize);
                ++n5;
            }
        }
        else {
            this.heapArenas = null;
        }
        if (n2 > 0) {
            this.directArenas = newArenaArray(n2);
            while (0 < this.directArenas.length) {
                this.directArenas[0] = new PoolArena.DirectArena(this, n3, n4, validateAndCalculatePageShifts, validateAndCalculateChunkSize);
                ++n5;
            }
        }
        else {
            this.directArenas = null;
        }
    }
    
    @Deprecated
    public PooledByteBufAllocator(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this(b, n, n2, n3, n4, n5, n6, n7);
    }
    
    private static PoolArena[] newArenaArray(final int n) {
        return new PoolArena[n];
    }
    
    private static int validateAndCalculatePageShifts(final int n) {
        if (n < 4096) {
            throw new IllegalArgumentException("pageSize: " + n + " (expected: " + 4096 + "+)");
        }
        if ((n & n - 1) != 0x0) {
            throw new IllegalArgumentException("pageSize: " + n + " (expected: power of 2)");
        }
        return 31 - Integer.numberOfLeadingZeros(n);
    }
    
    private static int validateAndCalculateChunkSize(final int n, final int n2) {
        if (n2 > 14) {
            throw new IllegalArgumentException("maxOrder: " + n2 + " (expected: 0-14)");
        }
        int n3 = n;
        for (int i = n2; i > 0; --i) {
            if (n3 > 536870912) {
                throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", n, n2, 1073741824));
            }
            n3 <<= 1;
        }
        return n3;
    }
    
    @Override
    protected ByteBuf newHeapBuffer(final int n, final int n2) {
        final PoolThreadCache poolThreadCache = (PoolThreadCache)this.threadCache.get();
        final PoolArena heapArena = poolThreadCache.heapArena;
        AbstractReferenceCountedByteBuf allocate;
        if (heapArena != null) {
            allocate = heapArena.allocate(poolThreadCache, n, n2);
        }
        else {
            allocate = new UnpooledHeapByteBuf(this, n, n2);
        }
        return AbstractByteBufAllocator.toLeakAwareBuffer(allocate);
    }
    
    @Override
    protected ByteBuf newDirectBuffer(final int n, final int n2) {
        final PoolThreadCache poolThreadCache = (PoolThreadCache)this.threadCache.get();
        final PoolArena directArena = poolThreadCache.directArena;
        AbstractReferenceCountedByteBuf allocate;
        if (directArena != null) {
            allocate = directArena.allocate(poolThreadCache, n, n2);
        }
        else if (PlatformDependent.hasUnsafe()) {
            allocate = new UnpooledUnsafeDirectByteBuf(this, n, n2);
        }
        else {
            allocate = new UnpooledDirectByteBuf(this, n, n2);
        }
        return AbstractByteBufAllocator.toLeakAwareBuffer(allocate);
    }
    
    @Override
    public boolean isDirectBufferPooled() {
        return this.directArenas != null;
    }
    
    @Deprecated
    public boolean hasThreadLocalCache() {
        return this.threadCache.isSet();
    }
    
    @Deprecated
    public void freeThreadLocalCache() {
        this.threadCache.remove();
    }
    
    static PoolArena[] access$000(final PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.heapArenas;
    }
    
    static PoolArena[] access$100(final PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.directArenas;
    }
    
    static int access$200(final PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.tinyCacheSize;
    }
    
    static int access$300(final PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.smallCacheSize;
    }
    
    static int access$400(final PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.normalCacheSize;
    }
    
    static int access$500() {
        return PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
    }
    
    static int access$600() {
        return PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
        SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
        final Object o = null;
        validateAndCalculatePageShifts(8192);
        DEFAULT_PAGE_SIZE = 8192;
        SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
        final Object o2 = null;
        validateAndCalculateChunkSize(PooledByteBufAllocator.DEFAULT_PAGE_SIZE, 11);
        DEFAULT_MAX_ORDER = 11;
        final Runtime runtime = Runtime.getRuntime();
        final int n = PooledByteBufAllocator.DEFAULT_PAGE_SIZE << PooledByteBufAllocator.DEFAULT_MAX_ORDER;
        DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int)Math.min(runtime.availableProcessors(), Runtime.getRuntime().maxMemory() / n / 2L / 3L)));
        DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int)Math.min(runtime.availableProcessors(), PlatformDependent.maxDirectMemory() / n / 2L / 3L)));
        DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
        DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
        DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
        DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
        DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
        if (PooledByteBufAllocator.logger.isDebugEnabled()) {
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.numHeapArenas: {}", (Object)PooledByteBufAllocator.DEFAULT_NUM_HEAP_ARENA);
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.numDirectArenas: {}", (Object)PooledByteBufAllocator.DEFAULT_NUM_DIRECT_ARENA);
            if (o == null) {
                PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.pageSize: {}", (Object)PooledByteBufAllocator.DEFAULT_PAGE_SIZE);
            }
            else {
                PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.pageSize: {}", (Object)PooledByteBufAllocator.DEFAULT_PAGE_SIZE, o);
            }
            if (o2 == null) {
                PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.maxOrder: {}", (Object)PooledByteBufAllocator.DEFAULT_MAX_ORDER);
            }
            else {
                PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.maxOrder: {}", (Object)PooledByteBufAllocator.DEFAULT_MAX_ORDER, o2);
            }
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.chunkSize: {}", (Object)(PooledByteBufAllocator.DEFAULT_PAGE_SIZE << PooledByteBufAllocator.DEFAULT_MAX_ORDER));
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", (Object)PooledByteBufAllocator.DEFAULT_TINY_CACHE_SIZE);
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.smallCacheSize: {}", (Object)PooledByteBufAllocator.DEFAULT_SMALL_CACHE_SIZE);
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.normalCacheSize: {}", (Object)PooledByteBufAllocator.DEFAULT_NORMAL_CACHE_SIZE);
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", (Object)PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY);
            PooledByteBufAllocator.logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", (Object)PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
        }
        DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    }
    
    final class PoolThreadLocalCache extends FastThreadLocal
    {
        private final AtomicInteger index;
        final PooledByteBufAllocator this$0;
        
        PoolThreadLocalCache(final PooledByteBufAllocator this$0) {
            this.this$0 = this$0;
            this.index = new AtomicInteger();
        }
        
        @Override
        protected PoolThreadCache initialValue() {
            final int andIncrement = this.index.getAndIncrement();
            PoolArena poolArena;
            if (PooledByteBufAllocator.access$000(this.this$0) != null) {
                poolArena = PooledByteBufAllocator.access$000(this.this$0)[Math.abs(andIncrement % PooledByteBufAllocator.access$000(this.this$0).length)];
            }
            else {
                poolArena = null;
            }
            PoolArena poolArena2;
            if (PooledByteBufAllocator.access$100(this.this$0) != null) {
                poolArena2 = PooledByteBufAllocator.access$100(this.this$0)[Math.abs(andIncrement % PooledByteBufAllocator.access$100(this.this$0).length)];
            }
            else {
                poolArena2 = null;
            }
            return new PoolThreadCache(poolArena, poolArena2, PooledByteBufAllocator.access$200(this.this$0), PooledByteBufAllocator.access$300(this.this$0), PooledByteBufAllocator.access$400(this.this$0), PooledByteBufAllocator.access$500(), PooledByteBufAllocator.access$600());
        }
        
        protected void onRemoval(final PoolThreadCache poolThreadCache) {
            poolThreadCache.free();
        }
        
        @Override
        protected void onRemoval(final Object o) throws Exception {
            this.onRemoval((PoolThreadCache)o);
        }
        
        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    }
}
