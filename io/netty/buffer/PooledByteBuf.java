package io.netty.buffer;

import io.netty.util.*;
import java.nio.*;

abstract class PooledByteBuf extends AbstractReferenceCountedByteBuf
{
    private final Recycler.Handle recyclerHandle;
    protected PoolChunk chunk;
    protected long handle;
    protected Object memory;
    protected int offset;
    protected int length;
    int maxLength;
    private ByteBuffer tmpNioBuf;
    static final boolean $assertionsDisabled;
    
    protected PooledByteBuf(final Recycler.Handle recyclerHandle, final int n) {
        super(n);
        this.recyclerHandle = recyclerHandle;
    }
    
    void init(final PoolChunk chunk, final long handle, final int offset, final int length, final int maxLength) {
        assert handle >= 0L;
        assert chunk != null;
        this.chunk = chunk;
        this.handle = handle;
        this.memory = chunk.memory;
        this.offset = offset;
        this.length = length;
        this.maxLength = maxLength;
        this.setIndex(0, 0);
        this.tmpNioBuf = null;
    }
    
    void initUnpooled(final PoolChunk chunk, final int n) {
        assert chunk != null;
        this.chunk = chunk;
        this.handle = 0L;
        this.memory = chunk.memory;
        this.offset = 0;
        this.maxLength = n;
        this.length = n;
        this.setIndex(0, 0);
        this.tmpNioBuf = null;
    }
    
    @Override
    public final int capacity() {
        return this.length;
    }
    
    @Override
    public final ByteBuf capacity(final int length) {
        this.ensureAccessible();
        if (this.chunk.unpooled) {
            if (length == this.length) {
                return this;
            }
        }
        else if (length > this.length) {
            if (length <= this.maxLength) {
                this.length = length;
                return this;
            }
        }
        else {
            if (length >= this.length) {
                return this;
            }
            if (length > this.maxLength >>> 1) {
                if (this.maxLength > 512) {
                    this.length = length;
                    this.setIndex(Math.min(this.readerIndex(), length), Math.min(this.writerIndex(), length));
                    return this;
                }
                if (length > this.maxLength - 16) {
                    this.length = length;
                    this.setIndex(Math.min(this.readerIndex(), length), Math.min(this.writerIndex(), length));
                    return this;
                }
            }
        }
        this.chunk.arena.reallocate(this, length, true);
        return this;
    }
    
    @Override
    public final ByteBufAllocator alloc() {
        return this.chunk.arena.parent;
    }
    
    @Override
    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }
    
    @Override
    public final ByteBuf unwrap() {
        return null;
    }
    
    protected final ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf == null) {
            tmpNioBuf = (this.tmpNioBuf = this.newInternalNioBuffer(this.memory));
        }
        return tmpNioBuf;
    }
    
    protected abstract ByteBuffer newInternalNioBuffer(final Object p0);
    
    @Override
    protected final void deallocate() {
        if (this.handle >= 0L) {
            final long handle = this.handle;
            this.handle = -1L;
            this.memory = null;
            this.chunk.arena.free(this.chunk, handle, this.maxLength);
            this.recycle();
        }
    }
    
    private void recycle() {
        final Recycler.Handle recyclerHandle = this.recyclerHandle;
        if (recyclerHandle != null) {
            this.recycler().recycle(this, recyclerHandle);
        }
    }
    
    protected abstract Recycler recycler();
    
    protected final int idx(final int n) {
        return this.offset + n;
    }
    
    static {
        $assertionsDisabled = !PooledByteBuf.class.desiredAssertionStatus();
    }
}
