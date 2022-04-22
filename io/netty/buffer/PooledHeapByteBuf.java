package io.netty.buffer;

import io.netty.util.*;
import io.netty.util.internal.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;

final class PooledHeapByteBuf extends PooledByteBuf
{
    private static final Recycler RECYCLER;
    
    static PooledHeapByteBuf newInstance(final int n) {
        final PooledHeapByteBuf pooledHeapByteBuf = (PooledHeapByteBuf)PooledHeapByteBuf.RECYCLER.get();
        pooledHeapByteBuf.setRefCnt(1);
        pooledHeapByteBuf.maxCapacity(n);
        return pooledHeapByteBuf;
    }
    
    private PooledHeapByteBuf(final Recycler.Handle handle, final int n) {
        super(handle, n);
    }
    
    @Override
    public boolean isDirect() {
        return false;
    }
    
    @Override
    protected byte _getByte(final int n) {
        return ((byte[])this.memory)[this.idx(n)];
    }
    
    @Override
    protected short _getShort(int idx) {
        idx = this.idx(idx);
        return (short)(((byte[])this.memory)[idx] << 8 | (((byte[])this.memory)[idx + 1] & 0xFF));
    }
    
    @Override
    protected int _getUnsignedMedium(int idx) {
        idx = this.idx(idx);
        return (((byte[])this.memory)[idx] & 0xFF) << 16 | (((byte[])this.memory)[idx + 1] & 0xFF) << 8 | (((byte[])this.memory)[idx + 2] & 0xFF);
    }
    
    @Override
    protected int _getInt(int idx) {
        idx = this.idx(idx);
        return (((byte[])this.memory)[idx] & 0xFF) << 24 | (((byte[])this.memory)[idx + 1] & 0xFF) << 16 | (((byte[])this.memory)[idx + 2] & 0xFF) << 8 | (((byte[])this.memory)[idx + 3] & 0xFF);
    }
    
    @Override
    protected long _getLong(int idx) {
        idx = this.idx(idx);
        return ((long)((byte[])this.memory)[idx] & 0xFFL) << 56 | ((long)((byte[])this.memory)[idx + 1] & 0xFFL) << 48 | ((long)((byte[])this.memory)[idx + 2] & 0xFFL) << 40 | ((long)((byte[])this.memory)[idx + 3] & 0xFFL) << 32 | ((long)((byte[])this.memory)[idx + 4] & 0xFFL) << 24 | ((long)((byte[])this.memory)[idx + 5] & 0xFFL) << 16 | ((long)((byte[])this.memory)[idx + 6] & 0xFFL) << 8 | ((long)((byte[])this.memory)[idx + 7] & 0xFFL);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory((byte[])this.memory, this.idx(n), byteBuf.memoryAddress() + n2, n3);
        }
        else if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else {
            byteBuf.setBytes(n2, (byte[])this.memory, this.idx(n), n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkDstIndex(n, n3, n2, array.length);
        System.arraycopy(this.memory, this.idx(n), array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.checkIndex(n);
        byteBuffer.put((byte[])this.memory, this.idx(n), Math.min(this.capacity() - n, byteBuffer.remaining()));
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.checkIndex(n, n2);
        outputStream.write((byte[])this.memory, this.idx(n), n2);
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }
    
    private int getBytes(int idx, final GatheringByteChannel gatheringByteChannel, final int n, final boolean b) throws IOException {
        this.checkIndex(idx, n);
        idx = this.idx(idx);
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = ByteBuffer.wrap((byte[])this.memory);
        }
        return gatheringByteChannel.write((ByteBuffer)byteBuffer.clear().position(idx).limit(idx + n));
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        this.checkReadableBytes(n);
        final int bytes = this.getBytes(this.readerIndex, gatheringByteChannel, n, true);
        this.readerIndex += bytes;
        return bytes;
    }
    
    @Override
    protected void _setByte(final int n, final int n2) {
        ((byte[])this.memory)[this.idx(n)] = (byte)n2;
    }
    
    @Override
    protected void _setShort(int idx, final int n) {
        idx = this.idx(idx);
        ((byte[])this.memory)[idx] = (byte)(n >>> 8);
        ((byte[])this.memory)[idx + 1] = (byte)n;
    }
    
    @Override
    protected void _setMedium(int idx, final int n) {
        idx = this.idx(idx);
        ((byte[])this.memory)[idx] = (byte)(n >>> 16);
        ((byte[])this.memory)[idx + 1] = (byte)(n >>> 8);
        ((byte[])this.memory)[idx + 2] = (byte)n;
    }
    
    @Override
    protected void _setInt(int idx, final int n) {
        idx = this.idx(idx);
        ((byte[])this.memory)[idx] = (byte)(n >>> 24);
        ((byte[])this.memory)[idx + 1] = (byte)(n >>> 16);
        ((byte[])this.memory)[idx + 2] = (byte)(n >>> 8);
        ((byte[])this.memory)[idx + 3] = (byte)n;
    }
    
    @Override
    protected void _setLong(int idx, final long n) {
        idx = this.idx(idx);
        ((byte[])this.memory)[idx] = (byte)(n >>> 56);
        ((byte[])this.memory)[idx + 1] = (byte)(n >>> 48);
        ((byte[])this.memory)[idx + 2] = (byte)(n >>> 40);
        ((byte[])this.memory)[idx + 3] = (byte)(n >>> 32);
        ((byte[])this.memory)[idx + 4] = (byte)(n >>> 24);
        ((byte[])this.memory)[idx + 5] = (byte)(n >>> 16);
        ((byte[])this.memory)[idx + 6] = (byte)(n >>> 8);
        ((byte[])this.memory)[idx + 7] = (byte)n;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + n2, (byte[])this.memory, this.idx(n), n3);
        }
        else if (byteBuf.hasArray()) {
            this.setBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else {
            byteBuf.getBytes(n2, (byte[])this.memory, this.idx(n), n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, array.length);
        System.arraycopy(array, n2, this.memory, this.idx(n), n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        this.checkIndex(n, remaining);
        byteBuffer.get((byte[])this.memory, this.idx(n), remaining);
        return this;
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        this.checkIndex(n, n2);
        return inputStream.read((byte[])this.memory, this.idx(n), n2);
    }
    
    @Override
    public int setBytes(int idx, final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        this.checkIndex(idx, n);
        idx = this.idx(idx);
        return scatteringByteChannel.read((ByteBuffer)this.internalNioBuffer().clear().position(idx).limit(idx + n));
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.checkIndex(n, n2);
        final ByteBuf heapBuffer = this.alloc().heapBuffer(n2, this.maxCapacity());
        heapBuffer.writeBytes((byte[])this.memory, this.idx(n), n2);
        return heapBuffer;
    }
    
    @Override
    public int nioBufferCount() {
        return 1;
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return new ByteBuffer[] { this.nioBuffer(n, n2) };
    }
    
    @Override
    public ByteBuffer nioBuffer(int idx, final int n) {
        this.checkIndex(idx, n);
        idx = this.idx(idx);
        return ByteBuffer.wrap((byte[])this.memory, idx, n).slice();
    }
    
    @Override
    public ByteBuffer internalNioBuffer(int idx, final int n) {
        this.checkIndex(idx, n);
        idx = this.idx(idx);
        return (ByteBuffer)this.internalNioBuffer().clear().position(idx).limit(idx + n);
    }
    
    @Override
    public boolean hasArray() {
        return true;
    }
    
    @Override
    public byte[] array() {
        return (byte[])this.memory;
    }
    
    @Override
    public int arrayOffset() {
        return this.offset;
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return false;
    }
    
    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
    
    protected ByteBuffer newInternalNioBuffer(final byte[] array) {
        return ByteBuffer.wrap(array);
    }
    
    @Override
    protected Recycler recycler() {
        return PooledHeapByteBuf.RECYCLER;
    }
    
    @Override
    protected ByteBuffer newInternalNioBuffer(final Object o) {
        return this.newInternalNioBuffer((byte[])o);
    }
    
    PooledHeapByteBuf(final Recycler.Handle handle, final int n, final PooledHeapByteBuf$1 recycler) {
        this(handle, n);
    }
    
    static {
        RECYCLER = new Recycler() {
            @Override
            protected PooledHeapByteBuf newObject(final Handle handle) {
                return new PooledHeapByteBuf(handle, 0, null);
            }
            
            @Override
            protected Object newObject(final Handle handle) {
                return this.newObject(handle);
            }
        };
    }
}
