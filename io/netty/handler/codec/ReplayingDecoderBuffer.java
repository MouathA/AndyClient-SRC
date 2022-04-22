package io.netty.handler.codec;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import io.netty.util.internal.*;
import io.netty.util.*;
import io.netty.buffer.*;

final class ReplayingDecoderBuffer extends ByteBuf
{
    private static final Signal REPLAY;
    private ByteBuf buffer;
    private boolean terminated;
    private SwappedByteBuf swapped;
    static final ReplayingDecoderBuffer EMPTY_BUFFER;
    
    ReplayingDecoderBuffer() {
    }
    
    ReplayingDecoderBuffer(final ByteBuf cumulation) {
        this.setCumulation(cumulation);
    }
    
    void setCumulation(final ByteBuf buffer) {
        this.buffer = buffer;
    }
    
    void terminate() {
        this.terminated = true;
    }
    
    @Override
    public int capacity() {
        if (this.terminated) {
            return this.buffer.capacity();
        }
        return Integer.MAX_VALUE;
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        return this;
    }
    
    @Override
    public int maxCapacity() {
        return this.capacity();
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.buffer.alloc();
    }
    
    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }
    
    @Override
    public boolean hasArray() {
        return false;
    }
    
    @Override
    public byte[] array() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException();
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
    public ByteBuf clear() {
        return this;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public int compareTo(final ByteBuf byteBuf) {
        return 0;
    }
    
    @Override
    public ByteBuf copy() {
        return this;
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.copy(n, n2);
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        return this;
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        return this;
    }
    
    @Override
    public int ensureWritable(final int n, final boolean b) {
        return 0;
    }
    
    @Override
    public ByteBuf duplicate() {
        return this;
    }
    
    @Override
    public boolean getBoolean(final int n) {
        this.checkIndex(n, 1);
        return this.buffer.getBoolean(n);
    }
    
    @Override
    public byte getByte(final int n) {
        this.checkIndex(n, 1);
        return this.buffer.getByte(n);
    }
    
    @Override
    public short getUnsignedByte(final int n) {
        this.checkIndex(n, 1);
        return this.buffer.getUnsignedByte(n);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        this.buffer.getBytes(n, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array) {
        this.checkIndex(n, array.length);
        this.buffer.getBytes(n, array);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        this.buffer.getBytes(n, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) {
        return 0;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) {
        return this;
    }
    
    @Override
    public int getInt(final int n) {
        this.checkIndex(n, 4);
        return this.buffer.getInt(n);
    }
    
    @Override
    public long getUnsignedInt(final int n) {
        this.checkIndex(n, 4);
        return this.buffer.getUnsignedInt(n);
    }
    
    @Override
    public long getLong(final int n) {
        this.checkIndex(n, 8);
        return this.buffer.getLong(n);
    }
    
    @Override
    public int getMedium(final int n) {
        this.checkIndex(n, 3);
        return this.buffer.getMedium(n);
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        this.checkIndex(n, 3);
        return this.buffer.getUnsignedMedium(n);
    }
    
    @Override
    public short getShort(final int n) {
        this.checkIndex(n, 2);
        return this.buffer.getShort(n);
    }
    
    @Override
    public int getUnsignedShort(final int n) {
        this.checkIndex(n, 2);
        return this.buffer.getUnsignedShort(n);
    }
    
    @Override
    public char getChar(final int n) {
        this.checkIndex(n, 2);
        return this.buffer.getChar(n);
    }
    
    @Override
    public float getFloat(final int n) {
        this.checkIndex(n, 4);
        return this.buffer.getFloat(n);
    }
    
    @Override
    public double getDouble(final int n) {
        this.checkIndex(n, 8);
        return this.buffer.getDouble(n);
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public int indexOf(final int n, final int n2, final byte b) {
        if (n == n2) {
            return -1;
        }
        if (Math.max(n, n2) > this.buffer.writerIndex()) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return this.buffer.indexOf(n, n2, b);
    }
    
    @Override
    public int bytesBefore(final byte b) {
        final int bytesBefore = this.buffer.bytesBefore(b);
        if (bytesBefore < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytesBefore;
    }
    
    @Override
    public int bytesBefore(final int n, final byte b) {
        final int readerIndex = this.buffer.readerIndex();
        return this.bytesBefore(readerIndex, this.buffer.writerIndex() - readerIndex, b);
    }
    
    @Override
    public int bytesBefore(final int n, final int n2, final byte b) {
        final int writerIndex = this.buffer.writerIndex();
        if (n >= writerIndex) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        if (n <= writerIndex - n2) {
            return this.buffer.bytesBefore(n, n2, b);
        }
        final int bytesBefore = this.buffer.bytesBefore(n, writerIndex - n, b);
        if (bytesBefore < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytesBefore;
    }
    
    @Override
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        final int forEachByte = this.buffer.forEachByte(byteBufProcessor);
        if (forEachByte < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return forEachByte;
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        final int writerIndex = this.buffer.writerIndex();
        if (n >= writerIndex) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        if (n <= writerIndex - n2) {
            return this.buffer.forEachByte(n, n2, byteBufProcessor);
        }
        final int forEachByte = this.buffer.forEachByte(n, writerIndex - n, byteBufProcessor);
        if (forEachByte < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return forEachByte;
    }
    
    @Override
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        if (this.terminated) {
            return this.buffer.forEachByteDesc(byteBufProcessor);
        }
        return 0;
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        if (n + n2 > this.buffer.writerIndex()) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return this.buffer.forEachByteDesc(n, n2, byteBufProcessor);
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        this.buffer.markReaderIndex();
        return this;
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        return this;
    }
    
    @Override
    public ByteOrder order() {
        return this.buffer.order();
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        SwappedByteBuf swapped = this.swapped;
        if (swapped == null) {
            swapped = (this.swapped = new SwappedByteBuf(this));
        }
        return swapped;
    }
    
    @Override
    public boolean isReadable() {
        return !this.terminated || this.buffer.isReadable();
    }
    
    @Override
    public boolean isReadable(final int n) {
        return !this.terminated || this.buffer.isReadable(n);
    }
    
    @Override
    public int readableBytes() {
        if (this.terminated) {
            return this.buffer.readableBytes();
        }
        return Integer.MAX_VALUE - this.buffer.readerIndex();
    }
    
    @Override
    public boolean readBoolean() {
        this.checkReadableBytes(1);
        return this.buffer.readBoolean();
    }
    
    @Override
    public byte readByte() {
        this.checkReadableBytes(1);
        return this.buffer.readByte();
    }
    
    @Override
    public short readUnsignedByte() {
        this.checkReadableBytes(1);
        return this.buffer.readUnsignedByte();
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        this.checkReadableBytes(n2);
        this.buffer.readBytes(array, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        this.checkReadableBytes(array.length);
        this.buffer.readBytes(array);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.checkReadableBytes(n2);
        this.buffer.readBytes(byteBuf, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        this.checkReadableBytes(byteBuf.writableBytes());
        this.buffer.readBytes(byteBuf);
        return this;
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) {
        return 0;
    }
    
    @Override
    public ByteBuf readBytes(final int n) {
        this.checkReadableBytes(n);
        return this.buffer.readBytes(n);
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        this.checkReadableBytes(n);
        return this.buffer.readSlice(n);
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) {
        return this;
    }
    
    @Override
    public int readerIndex() {
        return this.buffer.readerIndex();
    }
    
    @Override
    public ByteBuf readerIndex(final int n) {
        this.buffer.readerIndex(n);
        return this;
    }
    
    @Override
    public int readInt() {
        this.checkReadableBytes(4);
        return this.buffer.readInt();
    }
    
    @Override
    public long readUnsignedInt() {
        this.checkReadableBytes(4);
        return this.buffer.readUnsignedInt();
    }
    
    @Override
    public long readLong() {
        this.checkReadableBytes(8);
        return this.buffer.readLong();
    }
    
    @Override
    public int readMedium() {
        this.checkReadableBytes(3);
        return this.buffer.readMedium();
    }
    
    @Override
    public int readUnsignedMedium() {
        this.checkReadableBytes(3);
        return this.buffer.readUnsignedMedium();
    }
    
    @Override
    public short readShort() {
        this.checkReadableBytes(2);
        return this.buffer.readShort();
    }
    
    @Override
    public int readUnsignedShort() {
        this.checkReadableBytes(2);
        return this.buffer.readUnsignedShort();
    }
    
    @Override
    public char readChar() {
        this.checkReadableBytes(2);
        return this.buffer.readChar();
    }
    
    @Override
    public float readFloat() {
        this.checkReadableBytes(4);
        return this.buffer.readFloat();
    }
    
    @Override
    public double readDouble() {
        this.checkReadableBytes(8);
        return this.buffer.readDouble();
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        this.buffer.resetReaderIndex();
        return this;
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        return this;
    }
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        return this;
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        return this;
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) {
        return 0;
    }
    
    @Override
    public ByteBuf setZero(final int n, final int n2) {
        return this;
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) {
        return 0;
    }
    
    @Override
    public ByteBuf setIndex(final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        return this;
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        return this;
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        return this;
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        this.checkReadableBytes(n);
        this.buffer.skipBytes(n);
        return this;
    }
    
    @Override
    public ByteBuf slice() {
        return this;
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.slice(n, n2);
    }
    
    @Override
    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        return null;
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.nioBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        return null;
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.nioBuffers(n, n2);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.internalNioBuffer(n, n2);
    }
    
    @Override
    public String toString(final int n, final int n2, final Charset charset) {
        this.checkIndex(n, n2);
        return this.buffer.toString(n, n2, charset);
    }
    
    @Override
    public String toString(final Charset charset) {
        return null;
    }
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + "ridx=" + this.readerIndex() + ", " + "widx=" + this.writerIndex() + ')';
    }
    
    @Override
    public boolean isWritable() {
        return false;
    }
    
    @Override
    public boolean isWritable(final int n) {
        return false;
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
    public ByteBuf writeBoolean(final boolean b) {
        return this;
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        return this;
    }
    
    @Override
    public int writeBytes(final InputStream inputStream, final int n) {
        return 0;
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) {
        return 0;
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        return this;
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        return this;
    }
    
    @Override
    public int writerIndex() {
        return this.buffer.writerIndex();
    }
    
    @Override
    public ByteBuf writerIndex(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        return this;
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        return this;
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        return this;
    }
    
    private void checkIndex(final int n, final int n2) {
        if (n + n2 > this.buffer.writerIndex()) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
    }
    
    private void checkReadableBytes(final int n) {
        if (this.buffer.readableBytes() < n) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        return this;
    }
    
    @Override
    public int refCnt() {
        return this.buffer.refCnt();
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
    
    @Override
    public ByteBuf unwrap() {
        return this;
    }
    
    private static void reject() {
        throw new UnsupportedOperationException("not a replayable operation");
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
        REPLAY = ReplayingDecoder.REPLAY;
        (EMPTY_BUFFER = new ReplayingDecoderBuffer(Unpooled.EMPTY_BUFFER)).terminate();
    }
}
