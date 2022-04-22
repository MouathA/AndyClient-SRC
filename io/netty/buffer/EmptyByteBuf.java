package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import io.netty.util.*;
import io.netty.util.internal.*;

public final class EmptyByteBuf extends ByteBuf
{
    private static final ByteBuffer EMPTY_BYTE_BUFFER;
    private static final long EMPTY_BYTE_BUFFER_ADDRESS;
    private final ByteBufAllocator alloc;
    private final ByteOrder order;
    private final String str;
    private EmptyByteBuf swapped;
    
    public EmptyByteBuf(final ByteBufAllocator byteBufAllocator) {
        this(byteBufAllocator, ByteOrder.BIG_ENDIAN);
    }
    
    private EmptyByteBuf(final ByteBufAllocator alloc, final ByteOrder order) {
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        this.alloc = alloc;
        this.order = order;
        this.str = StringUtil.simpleClassName(this) + ((order == ByteOrder.BIG_ENDIAN) ? "BE" : "LE");
    }
    
    @Override
    public int capacity() {
        return 0;
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.alloc;
    }
    
    @Override
    public ByteOrder order() {
        return this.order;
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
    
    @Override
    public boolean isDirect() {
        return true;
    }
    
    @Override
    public int maxCapacity() {
        return 0;
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        final EmptyByteBuf swapped = this.swapped;
        if (swapped != null) {
            return swapped;
        }
        return this.swapped = new EmptyByteBuf(this.alloc(), byteOrder);
    }
    
    @Override
    public int readerIndex() {
        return 0;
    }
    
    @Override
    public ByteBuf readerIndex(final int n) {
        return this.checkIndex(n);
    }
    
    @Override
    public int writerIndex() {
        return 0;
    }
    
    @Override
    public ByteBuf writerIndex(final int n) {
        return this.checkIndex(n);
    }
    
    @Override
    public ByteBuf setIndex(final int n, final int n2) {
        this.checkIndex(n);
        this.checkIndex(n2);
        return this;
    }
    
    @Override
    public int readableBytes() {
        return 0;
    }
    
    @Override
    public int writableBytes() {
        return 0;
    }
    
    @Override
    public int maxWritableBytes() {
        return 0;
    }
    
    @Override
    public boolean isReadable() {
        return false;
    }
    
    @Override
    public boolean isWritable() {
        return false;
    }
    
    @Override
    public ByteBuf clear() {
        return this;
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        return this;
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        return this;
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        return this;
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        return this;
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        return this;
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        return this;
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("minWritableBytes: " + n + " (expected: >= 0)");
        }
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }
    
    @Override
    public int ensureWritable(final int n, final boolean b) {
        if (n < 0) {
            throw new IllegalArgumentException("minWritableBytes: " + n + " (expected: >= 0)");
        }
        if (n == 0) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public boolean getBoolean(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public byte getByte(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public short getUnsignedByte(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public short getShort(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int getUnsignedShort(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int getMedium(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int getInt(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public long getUnsignedInt(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public long getLong(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public char getChar(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public float getFloat(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public double getDouble(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return this.checkIndex(n, byteBuf.writableBytes());
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.checkIndex(n, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.checkIndex(n, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array) {
        return this.checkIndex(n, array.length);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.checkIndex(n, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        return this.checkIndex(n, byteBuffer.remaining());
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) {
        return this.checkIndex(n, n2);
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) {
        this.checkIndex(n, n2);
        return 0;
    }
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.checkIndex(n, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.checkIndex(n, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        return this.checkIndex(n, array.length);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.checkIndex(n, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        return this.checkIndex(n, byteBuffer.remaining());
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) {
        this.checkIndex(n, n2);
        return 0;
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) {
        this.checkIndex(n, n2);
        return 0;
    }
    
    @Override
    public ByteBuf setZero(final int n, final int n2) {
        return this.checkIndex(n, n2);
    }
    
    @Override
    public boolean readBoolean() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public byte readByte() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public short readUnsignedByte() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public short readShort() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int readUnsignedShort() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int readMedium() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int readUnsignedMedium() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public int readInt() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public long readUnsignedInt() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public long readLong() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public char readChar() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public float readFloat() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public double readDouble() {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf readBytes(final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        return this.checkLength(byteBuf.writableBytes());
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.checkLength(n2);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        return this.checkLength(array.length);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        return this.checkLength(n2);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        return this.checkLength(byteBuffer.remaining());
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) {
        this.checkLength(n);
        return 0;
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean b) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.checkLength(n2);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        return this.checkLength(array.length);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return this.checkLength(n2);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return this.checkLength(byteBuffer.remaining());
    }
    
    @Override
    public int writeBytes(final InputStream inputStream, final int n) {
        this.checkLength(n);
        return 0;
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) {
        this.checkLength(n);
        return 0;
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        return this.checkLength(n);
    }
    
    @Override
    public int indexOf(final int n, final int n2, final byte b) {
        this.checkIndex(n);
        this.checkIndex(n2);
        return -1;
    }
    
    @Override
    public int bytesBefore(final byte b) {
        return -1;
    }
    
    @Override
    public int bytesBefore(final int n, final byte b) {
        this.checkLength(n);
        return -1;
    }
    
    @Override
    public int bytesBefore(final int n, final int n2, final byte b) {
        this.checkIndex(n, n2);
        return -1;
    }
    
    @Override
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        return -1;
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        this.checkIndex(n, n2);
        return -1;
    }
    
    @Override
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        return -1;
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        this.checkIndex(n, n2);
        return -1;
    }
    
    @Override
    public ByteBuf copy() {
        return this;
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        return this.checkIndex(n, n2);
    }
    
    @Override
    public ByteBuf slice() {
        return this;
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        return this.checkIndex(n, n2);
    }
    
    @Override
    public ByteBuf duplicate() {
        return this;
    }
    
    @Override
    public int nioBufferCount() {
        return 1;
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        return EmptyByteBuf.EMPTY_BYTE_BUFFER;
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.nioBuffer();
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        return new ByteBuffer[] { EmptyByteBuf.EMPTY_BYTE_BUFFER };
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.nioBuffers();
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return EmptyByteBuf.EMPTY_BYTE_BUFFER;
    }
    
    @Override
    public boolean hasArray() {
        return true;
    }
    
    @Override
    public byte[] array() {
        return EmptyArrays.EMPTY_BYTES;
    }
    
    @Override
    public int arrayOffset() {
        return 0;
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return false;
    }
    
    @Override
    public long memoryAddress() {
        if (this.hasMemoryAddress()) {
            return EmptyByteBuf.EMPTY_BYTE_BUFFER_ADDRESS;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String toString(final Charset charset) {
        return "";
    }
    
    @Override
    public String toString(final int n, final int n2, final Charset charset) {
        this.checkIndex(n, n2);
        return this.toString(charset);
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ByteBuf && !((ByteBuf)o).isReadable();
    }
    
    @Override
    public int compareTo(final ByteBuf byteBuf) {
        return byteBuf.isReadable() ? -1 : 0;
    }
    
    @Override
    public String toString() {
        return this.str;
    }
    
    @Override
    public boolean isReadable(final int n) {
        return false;
    }
    
    @Override
    public boolean isWritable(final int n) {
        return false;
    }
    
    @Override
    public int refCnt() {
        return 1;
    }
    
    @Override
    public ByteBuf retain() {
        return this;
    }
    
    @Override
    public ByteBuf retain(final int n) {
        return this;
    }
    
    @Override
    public boolean release() {
        return false;
    }
    
    @Override
    public boolean release(final int n) {
        return false;
    }
    
    private ByteBuf checkIndex(final int n) {
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }
    
    private ByteBuf checkIndex(final int n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("length: " + n2);
        }
        if (n != 0 || n2 != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }
    
    private ByteBuf checkLength(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("length: " + n + " (expected: >= 0)");
        }
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ByteBuf)o);
    }
    
    static {
        EMPTY_BYTE_BUFFER = ByteBuffer.allocateDirect(0);
        long directBufferAddress = 0L;
        if (PlatformDependent.hasUnsafe()) {
            directBufferAddress = PlatformDependent.directBufferAddress(EmptyByteBuf.EMPTY_BYTE_BUFFER);
        }
        EMPTY_BYTE_BUFFER_ADDRESS = directBufferAddress;
    }
}
