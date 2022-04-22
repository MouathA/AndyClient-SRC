package io.netty.buffer;

import io.netty.util.internal.*;
import java.nio.*;

final class ReadOnlyUnsafeDirectByteBuf extends ReadOnlyByteBufferBuf
{
    private static final boolean NATIVE_ORDER;
    private final long memoryAddress;
    
    ReadOnlyUnsafeDirectByteBuf(final ByteBufAllocator byteBufAllocator, final ByteBuffer byteBuffer) {
        super(byteBufAllocator, byteBuffer);
        this.memoryAddress = PlatformDependent.directBufferAddress(byteBuffer);
    }
    
    @Override
    protected byte _getByte(final int n) {
        return PlatformDependent.getByte(this.addr(n));
    }
    
    @Override
    protected short _getShort(final int n) {
        final short short1 = PlatformDependent.getShort(this.addr(n));
        return ReadOnlyUnsafeDirectByteBuf.NATIVE_ORDER ? short1 : Short.reverseBytes(short1);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        final long addr = this.addr(n);
        return (PlatformDependent.getByte(addr) & 0xFF) << 16 | (PlatformDependent.getByte(addr + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(addr + 2L) & 0xFF);
    }
    
    @Override
    protected int _getInt(final int n) {
        final int int1 = PlatformDependent.getInt(this.addr(n));
        return ReadOnlyUnsafeDirectByteBuf.NATIVE_ORDER ? int1 : Integer.reverseBytes(int1);
    }
    
    @Override
    protected long _getLong(final int n) {
        final long long1 = PlatformDependent.getLong(this.addr(n));
        return ReadOnlyUnsafeDirectByteBuf.NATIVE_ORDER ? long1 : Long.reverseBytes(long1);
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
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(n), byteBuf.memoryAddress() + n2, n3);
        }
        else if (byteBuf.hasArray()) {
            PlatformDependent.copyMemory(this.addr(n), byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else {
            byteBuf.setBytes(n2, this, n, n3);
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
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n2, n3, array.length));
        }
        if (n3 != 0) {
            PlatformDependent.copyMemory(this.addr(n), array, n2, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.checkIndex(n);
        if (byteBuffer == null) {
            throw new NullPointerException("dst");
        }
        final int min = Math.min(this.capacity() - n, byteBuffer.remaining());
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(n).limit(n + min);
        byteBuffer.put(internalNioBuffer);
        return this;
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
    
    private long addr(final int n) {
        return this.memoryAddress + n;
    }
    
    static {
        NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    }
}
