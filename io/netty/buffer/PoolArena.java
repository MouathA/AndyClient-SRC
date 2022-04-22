package io.netty.buffer;

import java.nio.*;
import io.netty.util.internal.*;

abstract class PoolArena
{
    static final int numTinySubpagePools = 32;
    final PooledByteBufAllocator parent;
    private final int maxOrder;
    final int pageSize;
    final int pageShifts;
    final int chunkSize;
    final int subpageOverflowMask;
    final int numSmallSubpagePools;
    private final PoolSubpage[] tinySubpagePools;
    private final PoolSubpage[] smallSubpagePools;
    private final PoolChunkList q050;
    private final PoolChunkList q025;
    private final PoolChunkList q000;
    private final PoolChunkList qInit;
    private final PoolChunkList q075;
    private final PoolChunkList q100;
    static final boolean $assertionsDisabled;
    
    protected PoolArena(final PooledByteBufAllocator parent, final int pageSize, final int maxOrder, final int pageShifts, final int chunkSize) {
        this.parent = parent;
        this.pageSize = pageSize;
        this.maxOrder = maxOrder;
        this.pageShifts = pageShifts;
        this.chunkSize = chunkSize;
        this.subpageOverflowMask = -pageSize;
        this.tinySubpagePools = this.newSubpagePoolArray(32);
        int n = 0;
        while (0 < this.tinySubpagePools.length) {
            this.tinySubpagePools[0] = this.newSubpagePoolHead(pageSize);
            ++n;
        }
        this.numSmallSubpagePools = pageShifts - 9;
        this.smallSubpagePools = this.newSubpagePoolArray(this.numSmallSubpagePools);
        while (0 < this.smallSubpagePools.length) {
            this.smallSubpagePools[0] = this.newSubpagePoolHead(pageSize);
            ++n;
        }
        this.q100 = new PoolChunkList(this, null, 100, Integer.MAX_VALUE);
        this.q075 = new PoolChunkList(this, this.q100, 75, 100);
        this.q050 = new PoolChunkList(this, this.q075, 50, 100);
        this.q025 = new PoolChunkList(this, this.q050, 25, 75);
        this.q000 = new PoolChunkList(this, this.q025, 1, 50);
        this.qInit = new PoolChunkList(this, this.q000, Integer.MIN_VALUE, 25);
        this.q100.prevList = this.q075;
        this.q075.prevList = this.q050;
        this.q050.prevList = this.q025;
        this.q025.prevList = this.q000;
        this.q000.prevList = null;
        this.qInit.prevList = this.qInit;
    }
    
    private PoolSubpage newSubpagePoolHead(final int n) {
        final PoolSubpage poolSubpage = new PoolSubpage(n);
        poolSubpage.prev = poolSubpage;
        return poolSubpage.next = poolSubpage;
    }
    
    private PoolSubpage[] newSubpagePoolArray(final int n) {
        return new PoolSubpage[n];
    }
    
    abstract boolean isDirect();
    
    PooledByteBuf allocate(final PoolThreadCache poolThreadCache, final int n, final int n2) {
        final PooledByteBuf byteBuf = this.newByteBuf(n2);
        this.allocate(poolThreadCache, byteBuf, n);
        return byteBuf;
    }
    
    static int tinyIdx(final int n) {
        return n >>> 4;
    }
    
    static int smallIdx(final int n) {
        int n2 = 0;
        for (int i = n >>> 10; i != 0; i >>>= 1, ++n2) {}
        return 0;
    }
    
    boolean isTinyOrSmall(final int n) {
        return (n & this.subpageOverflowMask) == 0x0;
    }
    
    static boolean isTiny(final int n) {
        return (n & 0xFFFFFE00) == 0x0;
    }
    
    private void allocate(final PoolThreadCache poolThreadCache, final PooledByteBuf pooledByteBuf, final int n) {
        final int normalizeCapacity = this.normalizeCapacity(n);
        if (this.isTinyOrSmall(normalizeCapacity)) {
            int n2;
            PoolSubpage[] array;
            if (isTiny(normalizeCapacity)) {
                if (poolThreadCache.allocateTiny(this, pooledByteBuf, n, normalizeCapacity)) {
                    return;
                }
                n2 = tinyIdx(normalizeCapacity);
                array = this.tinySubpagePools;
            }
            else {
                if (poolThreadCache.allocateSmall(this, pooledByteBuf, n, normalizeCapacity)) {
                    return;
                }
                n2 = smallIdx(normalizeCapacity);
                array = this.smallSubpagePools;
            }
            // monitorenter(this)
            final PoolSubpage poolSubpage = array[n2];
            final PoolSubpage next = poolSubpage.next;
            if (next != poolSubpage) {
                assert next.doNotDestroy && next.elemSize == normalizeCapacity;
                final long allocate = next.allocate();
                assert allocate >= 0L;
                next.chunk.initBufWithSubpage(pooledByteBuf, allocate, n);
                // monitorexit(this)
                return;
            }
            else {
            }
            // monitorexit(this)
        }
        else {
            if (normalizeCapacity > this.chunkSize) {
                this.allocateHuge(pooledByteBuf, n);
                return;
            }
            if (poolThreadCache.allocateNormal(this, pooledByteBuf, n, normalizeCapacity)) {
                return;
            }
        }
        this.allocateNormal(pooledByteBuf, n, normalizeCapacity);
    }
    
    private synchronized void allocateNormal(final PooledByteBuf pooledByteBuf, final int n, final int n2) {
        if (this.q050.allocate(pooledByteBuf, n, n2) || this.q025.allocate(pooledByteBuf, n, n2) || this.q000.allocate(pooledByteBuf, n, n2) || this.qInit.allocate(pooledByteBuf, n, n2) || this.q075.allocate(pooledByteBuf, n, n2) || this.q100.allocate(pooledByteBuf, n, n2)) {
            return;
        }
        final PoolChunk chunk = this.newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
        final long allocate = chunk.allocate(n2);
        assert allocate > 0L;
        chunk.initBuf(pooledByteBuf, allocate, n);
        this.qInit.add(chunk);
    }
    
    private void allocateHuge(final PooledByteBuf pooledByteBuf, final int n) {
        pooledByteBuf.initUnpooled(this.newUnpooledChunk(n), n);
    }
    
    void free(final PoolChunk poolChunk, final long n, final int n2) {
        if (poolChunk.unpooled) {
            this.destroyChunk(poolChunk);
        }
        else {
            if (((PoolThreadCache)this.parent.threadCache.get()).add(this, poolChunk, n, n2)) {
                return;
            }
            // monitorenter(this)
            poolChunk.parent.free(poolChunk, n);
        }
        // monitorexit(this)
    }
    
    PoolSubpage findSubpagePoolHead(int i) {
        PoolSubpage[] array;
        if (isTiny(i)) {
            final int n = i >>> 4;
            array = this.tinySubpagePools;
        }
        else {
            int n = 0;
            for (i >>>= 10; i != 0; i >>>= 1, ++n) {}
            array = this.smallSubpagePools;
        }
        return array[0];
    }
    
    int normalizeCapacity(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("capacity: " + n + " (expected: 0+)");
        }
        if (n >= this.chunkSize) {
            return n;
        }
        if (!isTiny(n)) {
            int n2 = n;
            final int n3 = --n2 | n2 >>> 1;
            final int n4 = n3 | n3 >>> 2;
            final int n5 = n4 | n4 >>> 4;
            final int n6 = n5 | n5 >>> 8;
            int n7 = n6 | n6 >>> 16;
            if (++n7 < 0) {
                n7 >>>= 1;
            }
            return n7;
        }
        if ((n & 0xF) == 0x0) {
            return n;
        }
        return (n & 0xFFFFFFF0) + 16;
    }
    
    void reallocate(final PooledByteBuf pooledByteBuf, final int n, final boolean b) {
        if (n < 0 || n > pooledByteBuf.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + n);
        }
        final int length = pooledByteBuf.length;
        if (length == n) {
            return;
        }
        final PoolChunk chunk = pooledByteBuf.chunk;
        final long handle = pooledByteBuf.handle;
        final Object memory = pooledByteBuf.memory;
        final int offset = pooledByteBuf.offset;
        final int maxLength = pooledByteBuf.maxLength;
        int readerIndex = pooledByteBuf.readerIndex();
        int writerIndex = pooledByteBuf.writerIndex();
        this.allocate((PoolThreadCache)this.parent.threadCache.get(), pooledByteBuf, n);
        if (n > length) {
            this.memoryCopy(memory, offset, pooledByteBuf.memory, pooledByteBuf.offset, length);
        }
        else if (n < length) {
            if (readerIndex < n) {
                if (writerIndex > n) {
                    writerIndex = n;
                }
                this.memoryCopy(memory, offset + readerIndex, pooledByteBuf.memory, pooledByteBuf.offset + readerIndex, writerIndex - readerIndex);
            }
            else {
                writerIndex = n;
                readerIndex = n;
            }
        }
        pooledByteBuf.setIndex(readerIndex, writerIndex);
        if (b) {
            this.free(chunk, handle, maxLength);
        }
    }
    
    protected abstract PoolChunk newChunk(final int p0, final int p1, final int p2, final int p3);
    
    protected abstract PoolChunk newUnpooledChunk(final int p0);
    
    protected abstract PooledByteBuf newByteBuf(final int p0);
    
    protected abstract void memoryCopy(final Object p0, final int p1, final Object p2, final int p3, final int p4);
    
    protected abstract void destroyChunk(final PoolChunk p0);
    
    @Override
    public synchronized String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Chunk(s) at 0~25%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.qInit);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 0~50%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q000);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 25~75%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q025);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 50~100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q050);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 75~100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q075);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q100);
        sb.append(StringUtil.NEWLINE);
        sb.append("tiny subpages:");
        int n = 0;
        while (1 < this.tinySubpagePools.length) {
            final PoolSubpage poolSubpage = this.tinySubpagePools[1];
            if (poolSubpage.next != poolSubpage) {
                sb.append(StringUtil.NEWLINE);
                sb.append(1);
                sb.append(": ");
                PoolSubpage poolSubpage2 = poolSubpage.next;
                do {
                    sb.append(poolSubpage2);
                    poolSubpage2 = poolSubpage2.next;
                } while (poolSubpage2 != poolSubpage);
            }
            ++n;
        }
        sb.append(StringUtil.NEWLINE);
        sb.append("small subpages:");
        while (1 < this.smallSubpagePools.length) {
            final PoolSubpage poolSubpage3 = this.smallSubpagePools[1];
            if (poolSubpage3.next != poolSubpage3) {
                sb.append(StringUtil.NEWLINE);
                sb.append(1);
                sb.append(": ");
                PoolSubpage poolSubpage4 = poolSubpage3.next;
                do {
                    sb.append(poolSubpage4);
                    poolSubpage4 = poolSubpage4.next;
                } while (poolSubpage4 != poolSubpage3);
            }
            ++n;
        }
        sb.append(StringUtil.NEWLINE);
        return sb.toString();
    }
    
    static {
        $assertionsDisabled = !PoolArena.class.desiredAssertionStatus();
    }
    
    static final class DirectArena extends PoolArena
    {
        private static final boolean HAS_UNSAFE;
        
        DirectArena(final PooledByteBufAllocator pooledByteBufAllocator, final int n, final int n2, final int n3, final int n4) {
            super(pooledByteBufAllocator, n, n2, n3, n4);
        }
        
        @Override
        boolean isDirect() {
            return true;
        }
        
        @Override
        protected PoolChunk newChunk(final int n, final int n2, final int n3, final int n4) {
            return new PoolChunk(this, ByteBuffer.allocateDirect(n4), n, n2, n3, n4);
        }
        
        @Override
        protected PoolChunk newUnpooledChunk(final int n) {
            return new PoolChunk(this, ByteBuffer.allocateDirect(n), n);
        }
        
        @Override
        protected void destroyChunk(final PoolChunk poolChunk) {
            PlatformDependent.freeDirectBuffer((ByteBuffer)poolChunk.memory);
        }
        
        @Override
        protected PooledByteBuf newByteBuf(final int n) {
            if (DirectArena.HAS_UNSAFE) {
                return PooledUnsafeDirectByteBuf.newInstance(n);
            }
            return PooledDirectByteBuf.newInstance(n);
        }
        
        protected void memoryCopy(ByteBuffer duplicate, final int n, ByteBuffer duplicate2, final int n2, final int n3) {
            if (n3 == 0) {
                return;
            }
            if (DirectArena.HAS_UNSAFE) {
                PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(duplicate) + n, PlatformDependent.directBufferAddress(duplicate2) + n2, n3);
            }
            else {
                duplicate = duplicate.duplicate();
                duplicate2 = duplicate2.duplicate();
                duplicate.position(n).limit(n + n3);
                duplicate2.position(n2);
                duplicate2.put(duplicate);
            }
        }
        
        @Override
        protected void memoryCopy(final Object o, final int n, final Object o2, final int n2, final int n3) {
            this.memoryCopy((ByteBuffer)o, n, (ByteBuffer)o2, n2, n3);
        }
        
        static {
            HAS_UNSAFE = PlatformDependent.hasUnsafe();
        }
    }
    
    static final class HeapArena extends PoolArena
    {
        HeapArena(final PooledByteBufAllocator pooledByteBufAllocator, final int n, final int n2, final int n3, final int n4) {
            super(pooledByteBufAllocator, n, n2, n3, n4);
        }
        
        @Override
        boolean isDirect() {
            return false;
        }
        
        @Override
        protected PoolChunk newChunk(final int n, final int n2, final int n3, final int n4) {
            return new PoolChunk(this, new byte[n4], n, n2, n3, n4);
        }
        
        @Override
        protected PoolChunk newUnpooledChunk(final int n) {
            return new PoolChunk(this, new byte[n], n);
        }
        
        @Override
        protected void destroyChunk(final PoolChunk poolChunk) {
        }
        
        @Override
        protected PooledByteBuf newByteBuf(final int n) {
            return PooledHeapByteBuf.newInstance(n);
        }
        
        protected void memoryCopy(final byte[] array, final int n, final byte[] array2, final int n2, final int n3) {
            if (n3 == 0) {
                return;
            }
            System.arraycopy(array, n, array2, n2, n3);
        }
        
        @Override
        protected void memoryCopy(final Object o, final int n, final Object o2, final int n2, final int n3) {
            this.memoryCopy((byte[])o, n, (byte[])o2, n2, n3);
        }
    }
}
