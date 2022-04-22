package io.netty.buffer;

import io.netty.util.*;
import io.netty.util.internal.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.*;

final class PooledUnsafeDirectByteBuf extends PooledByteBuf
{
    private static final boolean NATIVE_ORDER;
    private static final Recycler RECYCLER;
    private long memoryAddress;
    
    static PooledUnsafeDirectByteBuf newInstance(final int n) {
        final PooledUnsafeDirectByteBuf pooledUnsafeDirectByteBuf = (PooledUnsafeDirectByteBuf)PooledUnsafeDirectByteBuf.RECYCLER.get();
        pooledUnsafeDirectByteBuf.setRefCnt(1);
        pooledUnsafeDirectByteBuf.maxCapacity(n);
        return pooledUnsafeDirectByteBuf;
    }
    
    private PooledUnsafeDirectByteBuf(final Recycler.Handle handle, final int n) {
        super(handle, n);
    }
    
    @Override
    void init(final PoolChunk poolChunk, final long n, final int n2, final int n3, final int n4) {
        super.init(poolChunk, n, n2, n3, n4);
        this.initMemoryAddress();
    }
    
    @Override
    void initUnpooled(final PoolChunk poolChunk, final int n) {
        super.initUnpooled(poolChunk, n);
        this.initMemoryAddress();
    }
    
    private void initMemoryAddress() {
        this.memoryAddress = PlatformDependent.directBufferAddress((ByteBuffer)this.memory) + this.offset;
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
        return PlatformDependent.getByte(this.addr(n));
    }
    
    @Override
    protected short _getShort(final int n) {
        final short short1 = PlatformDependent.getShort(this.addr(n));
        return PooledUnsafeDirectByteBuf.NATIVE_ORDER ? short1 : Short.reverseBytes(short1);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        final long addr = this.addr(n);
        return (PlatformDependent.getByte(addr) & 0xFF) << 16 | (PlatformDependent.getByte(addr + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(addr + 2L) & 0xFF);
    }
    
    @Override
    protected int _getInt(final int n) {
        final int int1 = PlatformDependent.getInt(this.addr(n));
        return PooledUnsafeDirectByteBuf.NATIVE_ORDER ? int1 : Integer.reverseBytes(int1);
    }
    
    @Override
    protected long _getLong(final int n) {
        final long long1 = PlatformDependent.getLong(this.addr(n));
        return PooledUnsafeDirectByteBuf.NATIVE_ORDER ? long1 : Long.reverseBytes(long1);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (byteBuf == null) {
            throw new NullPointerException("dst");
        }
        if (n2 < 0 || n2 > byteBuf.capacity() - n3) {
            throw new IndexOutOfBoundsException("dstIndex: " + n2);
        }
        if (n3 != 0) {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(this.addr(n), byteBuf.memoryAddress() + n2, n3);
            }
            else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(this.addr(n), byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
            }
            else {
                byteBuf.setBytes(n2, this, n, n3);
            }
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (array == null) {
            throw new NullPointerException("dst");
        }
        if (n2 < 0 || n2 > array.length - n3) {
            throw new IndexOutOfBoundsException("dstIndex: " + n2);
        }
        if (n3 != 0) {
            PlatformDependent.copyMemory(this.addr(n), array, n2, n3);
        }
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
        this.checkIndex(n, n2);
        if (n2 != 0) {
            final byte[] array = new byte[n2];
            PlatformDependent.copyMemory(this.addr(n), array, 0, n2);
            outputStream.write(array);
        }
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
        PlatformDependent.putByte(this.addr(n), (byte)n2);
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        PlatformDependent.putShort(this.addr(n), PooledUnsafeDirectByteBuf.NATIVE_ORDER ? ((short)n2) : Short.reverseBytes((short)n2));
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        final long addr = this.addr(n);
        PlatformDependent.putByte(addr, (byte)(n2 >>> 16));
        PlatformDependent.putByte(addr + 1L, (byte)(n2 >>> 8));
        PlatformDependent.putByte(addr + 2L, (byte)n2);
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        PlatformDependent.putInt(this.addr(n), PooledUnsafeDirectByteBuf.NATIVE_ORDER ? n2 : Integer.reverseBytes(n2));
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        PlatformDependent.putLong(this.addr(n), PooledUnsafeDirectByteBuf.NATIVE_ORDER ? n2 : Long.reverseBytes(n2));
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (n2 < 0 || n2 > byteBuf.capacity() - n3) {
            throw new IndexOutOfBoundsException("srcIndex: " + n2);
        }
        if (n3 != 0) {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(byteBuf.memoryAddress() + n2, this.addr(n), n3);
            }
            else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(byteBuf.array(), byteBuf.arrayOffset() + n2, this.addr(n), n3);
            }
            else {
                byteBuf.getBytes(n2, this, n, n3);
            }
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (n3 != 0) {
            PlatformDependent.copyMemory(array, n2, this.addr(n), n3);
        }
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
        if (read > 0) {
            PlatformDependent.copyMemory(array, 0, this.addr(n), read);
        }
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
        if (n2 != 0) {
            if (directBuffer.hasMemoryAddress()) {
                PlatformDependent.copyMemory(this.addr(n), directBuffer.memoryAddress(), n2);
                directBuffer.setIndex(0, n2);
            }
            else {
                directBuffer.writeBytes(this, n, n2);
            }
        }
        return directBuffer;
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
        return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(idx).limit(idx + n)).slice();
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
        return true;
    }
    
    @Override
    public long memoryAddress() {
        return this.memoryAddress;
    }
    
    private long addr(final int n) {
        return this.memoryAddress + n;
    }
    
    @Override
    protected Recycler recycler() {
        return PooledUnsafeDirectByteBuf.RECYCLER;
    }
    
    @Override
    protected SwappedByteBuf newSwappedByteBuf() {
        return new UnsafeDirectSwappedByteBuf(this);
    }
    
    @Override
    protected ByteBuffer newInternalNioBuffer(final Object o) {
        return this.newInternalNioBuffer((ByteBuffer)o);
    }
    
    PooledUnsafeDirectByteBuf(final Recycler.Handle handle, final int n, final PooledUnsafeDirectByteBuf$1 recycler) {
        this(handle, n);
    }
    
    static {
        NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
        RECYCLER = new Recycler() {
            @Override
            protected PooledUnsafeDirectByteBuf newObject(final Handle handle) {
                return new PooledUnsafeDirectByteBuf(handle, 0, null);
            }
            
            @Override
            protected Object newObject(final Handle handle) {
                return this.newObject(handle);
            }
        };
    }
}
