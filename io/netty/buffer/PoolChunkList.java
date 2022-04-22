package io.netty.buffer;

import io.netty.util.internal.*;

final class PoolChunkList
{
    private final PoolArena arena;
    private final PoolChunkList nextList;
    PoolChunkList prevList;
    private final int minUsage;
    private final int maxUsage;
    private PoolChunk head;
    static final boolean $assertionsDisabled;
    
    PoolChunkList(final PoolArena arena, final PoolChunkList nextList, final int minUsage, final int maxUsage) {
        this.arena = arena;
        this.nextList = nextList;
        this.minUsage = minUsage;
        this.maxUsage = maxUsage;
    }
    
    boolean allocate(final PooledByteBuf pooledByteBuf, final int n, final int n2) {
        if (this.head == null) {
            return false;
        }
        PoolChunk poolChunk = this.head;
        while (true) {
            final long allocate = poolChunk.allocate(n2);
            if (allocate >= 0L) {
                poolChunk.initBuf(pooledByteBuf, allocate, n);
                if (poolChunk.usage() >= this.maxUsage) {
                    this.remove(poolChunk);
                    this.nextList.add(poolChunk);
                }
                return true;
            }
            poolChunk = poolChunk.next;
            if (poolChunk == null) {
                return false;
            }
        }
    }
    
    void free(final PoolChunk poolChunk, final long n) {
        poolChunk.free(n);
        if (poolChunk.usage() < this.minUsage) {
            this.remove(poolChunk);
            if (this.prevList == null) {
                assert poolChunk.usage() == 0;
                this.arena.destroyChunk(poolChunk);
            }
            else {
                this.prevList.add(poolChunk);
            }
        }
    }
    
    void add(final PoolChunk head) {
        if (head.usage() >= this.maxUsage) {
            this.nextList.add(head);
            return;
        }
        head.parent = this;
        if (this.head == null) {
            this.head = head;
            head.prev = null;
            head.next = null;
        }
        else {
            head.prev = null;
            head.next = this.head;
            this.head.prev = head;
            this.head = head;
        }
    }
    
    private void remove(final PoolChunk poolChunk) {
        if (poolChunk == this.head) {
            this.head = poolChunk.next;
            if (this.head != null) {
                this.head.prev = null;
            }
        }
        else {
            final PoolChunk next = poolChunk.next;
            if ((poolChunk.prev.next = next) != null) {
                next.prev = poolChunk.prev;
            }
        }
    }
    
    @Override
    public String toString() {
        if (this.head == null) {
            return "none";
        }
        final StringBuilder sb = new StringBuilder();
        PoolChunk poolChunk = this.head;
        while (true) {
            sb.append(poolChunk);
            poolChunk = poolChunk.next;
            if (poolChunk == null) {
                break;
            }
            sb.append(StringUtil.NEWLINE);
        }
        return sb.toString();
    }
    
    static {
        $assertionsDisabled = !PoolChunkList.class.desiredAssertionStatus();
    }
}
