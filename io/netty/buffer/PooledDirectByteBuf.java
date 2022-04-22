package io.netty.buffer;

import io.netty.util.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;

final class PooledDirectByteBuf extends PooledByteBuf
{
    private static final Recycler RECYCLER;
    
    static PooledDirectByteBuf newInstance(final int n) {
        final PooledDirectByteBuf pooledDirectByteBuf = (PooledDirectByteBuf)PooledDirectByteBuf.RECYCLER.get();
        pooledDirectByteBuf.setRefCnt(1);
        pooledDirectByteBuf.maxCapacity(n);
        return pooledDirectByteBuf;
    }
    
    private PooledDirectByteBuf(final Recycler.Handle handle, final int n) {
        super(handle, n);
    }
    
    protected ByteBuffer newInternalNioBuffer(final ByteBuffer byteBuffer) {
        return byteBuffer.duplicate();
    }
    
    @Override
    public boolean isDirect() {
        return true;
    }
    
    @Override
    protected byte _getByte(final int n) {
        return ((ByteBuffer)this.memory).get(this.idx(n));
    }
    
    @Override
    protected short _getShort(final int n) {
        return ((ByteBuffer)this.memory).getShort(this.idx(n));
    }
    
    @Override
    protected int _getUnsignedMedium(int idx) {
        idx = this.idx(idx);
        return (((ByteBuffer)this.memory).get(idx) & 0xFF) << 16 | (((ByteBuffer)this.memory).get(idx + 1) & 0xFF) << 8 | (((ByteBuffer)this.memory).get(idx + 2) & 0xFF);
    }
    
    @Override
    protected int _getInt(final int n) {
        return ((ByteBuffer)this.memory).getInt(this.idx(n));
    }
    
    @Override
    protected long _getLong(final int n) {
        return ((ByteBuffer)this.memory).getLong(this.idx(n));
    }
    
    @Override
    public ByteBuf getBytes(int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else if (byteBuf.nioBufferCount() > 0) {
            final ByteBuffer[] nioBuffers = byteBuf.nioBuffers(n2, n3);
            while (0 < nioBuffers.length) {
                final ByteBuffer byteBuffer = nioBuffers[0];
                final int remaining = byteBuffer.remaining();
                this.getBytes(n, byteBuffer);
                n += remaining;
                int n4 = 0;
                ++n4;
            }
        }
        else {
            byteBuf.setBytes(n2, this, n, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.getBytes(n, array, n2, n3, false);
        return this;
    }
    
    private void getBytes(int idx, final byte[] array, final int n, final int n2, final boolean b) {
        this.checkDstIndex(idx, n2, n, array.length);
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = ((ByteBuffer)this.memory).duplicate();
        }
        idx = this.idx(idx);
        byteBuffer.clear().position(idx).limit(idx + n2);
        byteBuffer.get(array, n, n2);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, array, n, n2, true);
        this.readerIndex += n2;
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.getBytes(n, byteBuffer, false);
        return this;
    }
    
    private void getBytes(int idx, final ByteBuffer byteBuffer, final boolean b) {
        this.checkIndex(idx);
        final int min = Math.min(this.capacity() - idx, byteBuffer.remaining());
        ByteBuffer byteBuffer2;
        if (b) {
            byteBuffer2 = this.internalNioBuffer();
        }
        else {
            byteBuffer2 = ((ByteBuffer)this.memory).duplicate();
        }
        idx = this.idx(idx);
        byteBuffer2.clear().position(idx).limit(idx + min);
        byteBuffer.put(byteBuffer2);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        this.checkReadableBytes(remaining);
        this.getBytes(this.readerIndex, byteBuffer, true);
        this.readerIndex += remaining;
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.getBytes(n, outputStream, n2, false);
        return this;
    }
    
    private void getBytes(final int n, final OutputStream outputStream, final int n2, final boolean b) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return;
        }
        final byte[] array = new byte[n2];
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = ((ByteBuffer)this.memory).duplicate();
        }
        byteBuffer.clear().position(this.idx(n));
        byteBuffer.get(array);
        outputStream.write(array);
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, outputStream, n, true);
        this.readerIndex += n;
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }
    
    private int getBytes(int idx, final GatheringByteChannel gatheringByteChannel, final int n, final boolean b) throws IOException {
        this.checkIndex(idx, n);
        if (n == 0) {
            return 0;
        }
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = ((ByteBuffer)this.memory).duplicate();
        }
        idx = this.idx(idx);
        byteBuffer.clear().position(idx).limit(idx + n);
        return gatheringByteChannel.write(byteBuffer);
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
        ((ByteBuffer)this.memory).put(this.idx(n), (byte)n2);
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        ((ByteBuffer)this.memory).putShort(this.idx(n), (short)n2);
    }
    
    @Override
    protected void _setMedium(int idx, final int n) {
        idx = this.idx(idx);
        ((ByteBuffer)this.memory).put(idx, (byte)(n >>> 16));
        ((ByteBuffer)this.memory).put(idx + 1, (byte)(n >>> 8));
        ((ByteBuffer)this.memory).put(idx + 2, (byte)n);
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        ((ByteBuffer)this.memory).putInt(this.idx(n), n2);
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        ((ByteBuffer)this.memory).putLong(this.idx(n), n2);
    }
    
    @Override
    public ByteBuf setBytes(int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            this.setBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else if (byteBuf.nioBufferCount() > 0) {
            final ByteBuffer[] nioBuffers = byteBuf.nioBuffers(n2, n3);
            while (0 < nioBuffers.length) {
                final ByteBuffer byteBuffer = nioBuffers[0];
                final int remaining = byteBuffer.remaining();
                this.setBytes(n, byteBuffer);
                n += remaining;
                int n4 = 0;
                ++n4;
            }
        }
        else {
            byteBuf.getBytes(n2, this, n, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(int idx, final byte[] array, final int n, final int n2) {
        this.checkSrcIndex(idx, n2, n, array.length);
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        idx = this.idx(idx);
        internalNioBuffer.clear().position(idx).limit(idx + n2);
        internalNioBuffer.put(array, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(int idx, ByteBuffer duplicate) {
        this.checkIndex(idx, duplicate.remaining());
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        if (duplicate == internalNioBuffer) {
            duplicate = duplicate.duplicate();
        }
        idx = this.idx(idx);
        internalNioBuffer.clear().position(idx).limit(idx + duplicate.remaining());
        internalNioBuffer.put(duplicate);
        return this;
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        this.checkIndex(n, n2);
        final byte[] array = new byte[n2];
        final int read = inputStream.read(array);
        if (read <= 0) {
            return read;
        }
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(this.idx(n));
        internalNioBuffer.put(array, 0, read);
        return read;
    }
    
    @Override
    public int setBytes(int idx, final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        this.checkIndex(idx, n);
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        idx = this.idx(idx);
        internalNioBuffer.clear().position(idx).limit(idx + n);
        return scatteringByteChannel.read(internalNioBuffer);
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.checkIndex(n, n2);
        final ByteBuf directBuffer = this.alloc().directBuffer(n2, this.maxCapacity());
        directBuffer.writeBytes(this, n, n2);
        return directBuffer;
    }
    
    @Override
    public int nioBufferCount() {
        return 1;
    }
    
    @Override
    public ByteBuffer nioBuffer(int idx, final int n) {
        this.checkIndex(idx, n);
        idx = this.idx(idx);
        return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(idx).limit(idx + n)).slice();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return new ByteBuffer[] { this.nioBuffer(n, n2) };
    }
    
    @Override
    public ByteBuffer internalNioBuffer(int idx, final int n) {
        this.checkIndex(idx, n);
        idx = this.idx(idx);
        return (ByteBuffer)this.internalNioBuffer().clear().position(idx).limit(idx + n);
    }
    
    @Override
    public boolean hasArray() {
        return false;
    }
    
    @Override
    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }
    
    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return false;
    }
    
    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Recycler recycler() {
        return PooledDirectByteBuf.RECYCLER;
    }
    
    @Override
    protected ByteBuffer newInternalNioBuffer(final Object o) {
        return this.newInternalNioBuffer((ByteBuffer)o);
    }
    
    PooledDirectByteBuf(final Recycler.Handle handle, final int n, final PooledDirectByteBuf$1 recycler) {
        this(handle, n);
    }
    
    static {
        RECYCLER = new Recycler() {
            @Override
            protected PooledDirectByteBuf newObject(final Handle handle) {
                return new PooledDirectByteBuf(handle, 0, null);
            }
            
            @Override
            protected Object newObject(final Handle handle) {
                return this.newObject(handle);
            }
        };
    }
}
