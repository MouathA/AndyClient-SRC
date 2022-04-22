package io.netty.buffer;

final class PoolSubpage
{
    final PoolChunk chunk;
    private final int memoryMapIdx;
    private final int runOffset;
    private final int pageSize;
    private final long[] bitmap;
    PoolSubpage prev;
    PoolSubpage next;
    boolean doNotDestroy;
    int elemSize;
    private int maxNumElems;
    private int bitmapLength;
    private int nextAvail;
    private int numAvail;
    static final boolean $assertionsDisabled;
    
    PoolSubpage(final int pageSize) {
        this.chunk = null;
        this.memoryMapIdx = -1;
        this.runOffset = -1;
        this.elemSize = -1;
        this.pageSize = pageSize;
        this.bitmap = null;
    }
    
    PoolSubpage(final PoolChunk chunk, final int memoryMapIdx, final int runOffset, final int pageSize, final int n) {
        this.chunk = chunk;
        this.memoryMapIdx = memoryMapIdx;
        this.runOffset = runOffset;
        this.pageSize = pageSize;
        this.bitmap = new long[pageSize >>> 10];
        this.init(n);
    }
    
    void init(final int elemSize) {
        this.doNotDestroy = true;
        this.elemSize = elemSize;
        if (elemSize != 0) {
            final int n = this.pageSize / elemSize;
            this.numAvail = n;
            this.maxNumElems = n;
            this.nextAvail = 0;
            this.bitmapLength = this.maxNumElems >>> 6;
            if ((this.maxNumElems & 0x3F) != 0x0) {
                ++this.bitmapLength;
            }
            while (0 < this.bitmapLength) {
                this.bitmap[0] = 0L;
                int n2 = 0;
                ++n2;
            }
        }
        this.addToPool();
    }
    
    long allocate() {
        if (this.elemSize == 0) {
            return this.toHandle(0);
        }
        if (this.numAvail == 0 || !this.doNotDestroy) {
            return -1L;
        }
        final int nextAvail = this.getNextAvail();
        final int n = nextAvail >>> 6;
        final int n2 = nextAvail & 0x3F;
        assert (this.bitmap[n] >>> n2 & 0x1L) == 0x0L;
        final long[] bitmap = this.bitmap;
        final int n3 = n;
        bitmap[n3] |= 1L << n2;
        if (--this.numAvail == 0) {
            this.removeFromPool();
        }
        return this.toHandle(nextAvail);
    }
    
    boolean free(final int nextAvail) {
        if (this.elemSize == 0) {
            return true;
        }
        final int n = nextAvail >>> 6;
        final int n2 = nextAvail & 0x3F;
        assert (this.bitmap[n] >>> n2 & 0x1L) != 0x0L;
        final long[] bitmap = this.bitmap;
        final int n3 = n;
        bitmap[n3] ^= 1L << n2;
        this.setNextAvail(nextAvail);
        if (this.numAvail++ == 0) {
            this.addToPool();
            return true;
        }
        if (this.numAvail != this.maxNumElems) {
            return true;
        }
        if (this.prev == this.next) {
            return true;
        }
        this.doNotDestroy = false;
        this.removeFromPool();
        return false;
    }
    
    private void addToPool() {
        final PoolSubpage subpagePoolHead = this.chunk.arena.findSubpagePoolHead(this.elemSize);
        assert this.prev == null && this.next == null;
        this.prev = subpagePoolHead;
        this.next = subpagePoolHead.next;
        this.next.prev = this;
        subpagePoolHead.next = this;
    }
    
    private void removeFromPool() {
        assert this.prev != null && this.next != null;
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
    }
    
    private void setNextAvail(final int nextAvail) {
        this.nextAvail = nextAvail;
    }
    
    private int getNextAvail() {
        final int nextAvail = this.nextAvail;
        if (nextAvail >= 0) {
            this.nextAvail = -1;
            return nextAvail;
        }
        return this.findNextAvail();
    }
    
    private int findNextAvail() {
        final long[] bitmap = this.bitmap;
        while (0 < this.bitmapLength) {
            final long n = bitmap[0];
            if (~n != 0x0L) {
                return this.findNextAvail0(0, n);
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    private int findNextAvail0(final int n, long n2) {
        final int maxNumElems = this.maxNumElems;
        final int n3 = n << 6;
        while ((n2 & 0x1L) != 0x0L) {
            n2 >>>= 1;
            int n4 = 0;
            ++n4;
        }
        final int n5 = n3 | 0x0;
        if (n5 < maxNumElems) {
            return n5;
        }
        return -1;
    }
    
    private long toHandle(final int n) {
        return 0x4000000000000000L | (long)n << 32 | (long)this.memoryMapIdx;
    }
    
    @Override
    public String toString() {
        if (!this.doNotDestroy) {
            return "(" + this.memoryMapIdx + ": not in use)";
        }
        return String.valueOf('(') + this.memoryMapIdx + ": " + (this.maxNumElems - this.numAvail) + '/' + this.maxNumElems + ", offset: " + this.runOffset + ", length: " + this.pageSize + ", elemSize: " + this.elemSize + ')';
    }
    
    static {
        $assertionsDisabled = !PoolSubpage.class.desiredAssertionStatus();
    }
}
