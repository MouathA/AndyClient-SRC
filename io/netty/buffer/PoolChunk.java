package io.netty.buffer;

final class PoolChunk
{
    final PoolArena arena;
    final Object memory;
    final boolean unpooled;
    private final byte[] memoryMap;
    private final byte[] depthMap;
    private final PoolSubpage[] subpages;
    private final int subpageOverflowMask;
    private final int pageSize;
    private final int pageShifts;
    private final int maxOrder;
    private final int chunkSize;
    private final int log2ChunkSize;
    private final int maxSubpageAllocs;
    private final byte unusable;
    private int freeBytes;
    PoolChunkList parent;
    PoolChunk prev;
    PoolChunk next;
    static final boolean $assertionsDisabled;
    
    PoolChunk(final PoolArena arena, final Object memory, final int pageSize, final int maxOrder, final int pageShifts, final int n) {
        this.unpooled = false;
        this.arena = arena;
        this.memory = memory;
        this.pageSize = pageSize;
        this.pageShifts = pageShifts;
        this.maxOrder = maxOrder;
        this.chunkSize = n;
        this.unusable = (byte)(maxOrder + 1);
        this.log2ChunkSize = log2(n);
        this.subpageOverflowMask = -pageSize;
        this.freeBytes = n;
        assert maxOrder < 30 : "maxOrder should be < 30, but is: " + maxOrder;
        this.maxSubpageAllocs = 1 << maxOrder;
        this.memoryMap = new byte[this.maxSubpageAllocs << 1];
        this.depthMap = new byte[this.memoryMap.length];
        while (0 <= maxOrder) {
            while (0 < 1) {
                this.memoryMap[1] = 0;
                this.depthMap[1] = 0;
                int n2 = 0;
                ++n2;
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        this.subpages = this.newSubpageArray(this.maxSubpageAllocs);
    }
    
    PoolChunk(final PoolArena arena, final Object memory, final int chunkSize) {
        this.unpooled = true;
        this.arena = arena;
        this.memory = memory;
        this.memoryMap = null;
        this.depthMap = null;
        this.subpages = null;
        this.subpageOverflowMask = 0;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.maxOrder = 0;
        this.unusable = (byte)(this.maxOrder + 1);
        this.chunkSize = chunkSize;
        this.log2ChunkSize = log2(this.chunkSize);
        this.maxSubpageAllocs = 0;
    }
    
    private PoolSubpage[] newSubpageArray(final int n) {
        return new PoolSubpage[n];
    }
    
    int usage() {
        final int freeBytes = this.freeBytes;
        if (freeBytes == 0) {
            return 100;
        }
        final int n = (int)(freeBytes * 100L / this.chunkSize);
        if (n == 0) {
            return 99;
        }
        return 100 - n;
    }
    
    long allocate(final int n) {
        if ((n & this.subpageOverflowMask) != 0x0) {
            return this.allocateRun(n);
        }
        return this.allocateSubpage(n);
    }
    
    private void updateParentsAlloc(int i) {
        while (i > 1) {
            final int n = i >>> 1;
            final byte value = this.value(i);
            final byte value2 = this.value(i ^ 0x1);
            this.setValue(n, (value < value2) ? value : value2);
            i = n;
        }
    }
    
    private void updateParentsFree(int i) {
        int n = this.depth(i) + 1;
        while (i > 1) {
            final int n2 = i >>> 1;
            final byte value = this.value(i);
            final byte value2 = this.value(i ^ 0x1);
            --n;
            if (value == n && value2 == n) {
                this.setValue(n2, (byte)(n - 1));
            }
            else {
                this.setValue(n2, (value < value2) ? value : value2);
            }
            i = n2;
        }
    }
    
    private int allocateNode(final int n) {
        final int n2 = -(1 << n);
        byte b = this.value(1);
        if (b > n) {
            return -1;
        }
        while (b < n || (0x1 & n2) == 0x0) {
            b = this.value(1);
            if (b > n) {
                b = this.value(1);
            }
        }
        final byte value = this.value(1);
        assert value == n && (0x1 & n2) == 1 << n : String.format("val = %d, id & initial = %d, d = %d", value, 0x1 & n2, n);
        this.setValue(1, this.unusable);
        this.updateParentsAlloc(1);
        return 1;
    }
    
    private long allocateRun(final int n) {
        final int allocateNode = this.allocateNode(this.maxOrder - (log2(n) - this.pageShifts));
        if (allocateNode < 0) {
            return allocateNode;
        }
        this.freeBytes -= this.runLength(allocateNode);
        return allocateNode;
    }
    
    private long allocateSubpage(final int n) {
        final int allocateNode = this.allocateNode(this.maxOrder);
        if (allocateNode < 0) {
            return allocateNode;
        }
        final PoolSubpage[] subpages = this.subpages;
        final int pageSize = this.pageSize;
        this.freeBytes -= pageSize;
        final int subpageIdx = this.subpageIdx(allocateNode);
        PoolSubpage poolSubpage = subpages[subpageIdx];
        if (poolSubpage == null) {
            poolSubpage = new PoolSubpage(this, allocateNode, this.runOffset(allocateNode), pageSize, n);
            subpages[subpageIdx] = poolSubpage;
        }
        else {
            poolSubpage.init(n);
        }
        return poolSubpage.allocate();
    }
    
    void free(final long n) {
        final int n2 = (int)n;
        final int n3 = (int)(n >>> 32);
        if (n3 != 0) {
            final PoolSubpage poolSubpage = this.subpages[this.subpageIdx(n2)];
            assert poolSubpage != null && poolSubpage.doNotDestroy;
            if (poolSubpage.free(n3 & 0x3FFFFFFF)) {
                return;
            }
        }
        this.freeBytes += this.runLength(n2);
        this.setValue(n2, this.depth(n2));
        this.updateParentsFree(n2);
    }
    
    void initBuf(final PooledByteBuf pooledByteBuf, final long n, final int n2) {
        final int n3 = (int)n;
        final int n4 = (int)(n >>> 32);
        if (n4 == 0) {
            final byte value = this.value(n3);
            assert value == this.unusable : String.valueOf(value);
            pooledByteBuf.init(this, n, this.runOffset(n3), n2, this.runLength(n3));
        }
        else {
            this.initBufWithSubpage(pooledByteBuf, n, n4, n2);
        }
    }
    
    void initBufWithSubpage(final PooledByteBuf pooledByteBuf, final long n, final int n2) {
        this.initBufWithSubpage(pooledByteBuf, n, (int)(n >>> 32), n2);
    }
    
    private void initBufWithSubpage(final PooledByteBuf pooledByteBuf, final long n, final int n2, final int n3) {
        assert n2 != 0;
        final int n4 = (int)n;
        final PoolSubpage poolSubpage = this.subpages[this.subpageIdx(n4)];
        assert poolSubpage.doNotDestroy;
        assert n3 <= poolSubpage.elemSize;
        pooledByteBuf.init(this, n, this.runOffset(n4) + (n2 & 0x3FFFFFFF) * poolSubpage.elemSize, n3, poolSubpage.elemSize);
    }
    
    private byte value(final int n) {
        return this.memoryMap[n];
    }
    
    private void setValue(final int n, final byte b) {
        this.memoryMap[n] = b;
    }
    
    private byte depth(final int n) {
        return this.depthMap[n];
    }
    
    private static int log2(final int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }
    
    private int runLength(final int n) {
        return 1 << this.log2ChunkSize - this.depth(n);
    }
    
    private int runOffset(final int n) {
        return (n ^ 1 << this.depth(n)) * this.runLength(n);
    }
    
    private int subpageIdx(final int n) {
        return n ^ this.maxSubpageAllocs;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Chunk(");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(": ");
        sb.append(this.usage());
        sb.append("%, ");
        sb.append(this.chunkSize - this.freeBytes);
        sb.append('/');
        sb.append(this.chunkSize);
        sb.append(')');
        return sb.toString();
    }
    
    static {
        $assertionsDisabled = !PoolChunk.class.desiredAssertionStatus();
    }
}
