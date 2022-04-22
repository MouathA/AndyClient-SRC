package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import io.netty.util.*;

public class SwappedByteBuf extends ByteBuf
{
    private final ByteBuf buf;
    private final ByteOrder order;
    
    public SwappedByteBuf(final ByteBuf buf) {
        if (buf == null) {
            throw new NullPointerException("buf");
        }
        this.buf = buf;
        if (buf.order() == ByteOrder.BIG_ENDIAN) {
            this.order = ByteOrder.LITTLE_ENDIAN;
        }
        else {
            this.order = ByteOrder.BIG_ENDIAN;
        }
    }
    
    @Override
    public ByteOrder order() {
        return this.order;
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order) {
            return this;
        }
        return this.buf;
    }
    
    @Override
    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }
    
    @Override
    public int capacity() {
        return this.buf.capacity();
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        this.buf.capacity(n);
        return this;
    }
    
    @Override
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }
    
    @Override
    public boolean isDirect() {
        return this.buf.isDirect();
    }
    
    @Override
    public int readerIndex() {
        return this.buf.readerIndex();
    }
    
    @Override
    public ByteBuf readerIndex(final int n) {
        this.buf.readerIndex(n);
        return this;
    }
    
    @Override
    public int writerIndex() {
        return this.buf.writerIndex();
    }
    
    @Override
    public ByteBuf writerIndex(final int n) {
        this.buf.writerIndex(n);
        return this;
    }
    
    @Override
    public ByteBuf setIndex(final int n, final int n2) {
        this.buf.setIndex(n, n2);
        return this;
    }
    
    @Override
    public int readableBytes() {
        return this.buf.readableBytes();
    }
    
    @Override
    public int writableBytes() {
        return this.buf.writableBytes();
    }
    
    @Override
    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }
    
    @Override
    public boolean isReadable() {
        return this.buf.isReadable();
    }
    
    @Override
    public boolean isReadable(final int n) {
        return this.buf.isReadable(n);
    }
    
    @Override
    public boolean isWritable() {
        return this.buf.isWritable();
    }
    
    @Override
    public boolean isWritable(final int n) {
        return this.buf.isWritable(n);
    }
    
    @Override
    public ByteBuf clear() {
        this.buf.clear();
        return this;
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        this.buf.markReaderIndex();
        return this;
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        this.buf.resetReaderIndex();
        return this;
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        this.buf.markWriterIndex();
        return this;
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        this.buf.resetWriterIndex();
        return this;
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        this.buf.discardReadBytes();
        return this;
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        this.buf.discardSomeReadBytes();
        return this;
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        this.buf.ensureWritable(n);
        return this;
    }
    
    @Override
    public int ensureWritable(final int n, final boolean b) {
        return this.buf.ensureWritable(n, b);
    }
    
    @Override
    public boolean getBoolean(final int n) {
        return this.buf.getBoolean(n);
    }
    
    @Override
    public byte getByte(final int n) {
        return this.buf.getByte(n);
    }
    
    @Override
    public short getUnsignedByte(final int n) {
        return this.buf.getUnsignedByte(n);
    }
    
    @Override
    public short getShort(final int n) {
        return ByteBufUtil.swapShort(this.buf.getShort(n));
    }
    
    @Override
    public int getUnsignedShort(final int n) {
        return this.getShort(n) & 0xFFFF;
    }
    
    @Override
    public int getMedium(final int n) {
        return ByteBufUtil.swapMedium(this.buf.getMedium(n));
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        return this.getMedium(n) & 0xFFFFFF;
    }
    
    @Override
    public int getInt(final int n) {
        return ByteBufUtil.swapInt(this.buf.getInt(n));
    }
    
    @Override
    public long getUnsignedInt(final int n) {
        return (long)this.getInt(n) & 0xFFFFFFFFL;
    }
    
    @Override
    public long getLong(final int n) {
        return ByteBufUtil.swapLong(this.buf.getLong(n));
    }
    
    @Override
    public char getChar(final int n) {
        return (char)this.getShort(n);
    }
    
    @Override
    public float getFloat(final int n) {
        return Float.intBitsToFloat(this.getInt(n));
    }
    
    @Override
    public double getDouble(final int n) {
        return Double.longBitsToDouble(this.getLong(n));
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        this.buf.getBytes(n, byteBuf);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        this.buf.getBytes(n, byteBuf, n2);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.buf.getBytes(n, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array) {
        this.buf.getBytes(n, array);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.buf.getBytes(n, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.buf.getBytes(n, byteBuffer);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.buf.getBytes(n, outputStream, n2);
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.buf.getBytes(n, gatheringByteChannel, n2);
    }
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        this.buf.setBoolean(n, b);
        return this;
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        this.buf.setByte(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this.buf.setShort(n, ByteBufUtil.swapShort((short)n2));
        return this;
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        this.buf.setMedium(n, ByteBufUtil.swapMedium(n2));
        return this;
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this.buf.setInt(n, ByteBufUtil.swapInt(n2));
        return this;
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this.buf.setLong(n, ByteBufUtil.swapLong(n2));
        return this;
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        this.setShort(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        this.setInt(n, Float.floatToRawIntBits(n2));
        return this;
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        this.setLong(n, Double.doubleToRawLongBits(n2));
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        this.buf.setBytes(n, byteBuf);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        this.buf.setBytes(n, byteBuf, n2);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.buf.setBytes(n, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        this.buf.setBytes(n, array);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.buf.setBytes(n, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        this.buf.setBytes(n, byteBuffer);
        return this;
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        return this.buf.setBytes(n, inputStream, n2);
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        return this.buf.setBytes(n, scatteringByteChannel, n2);
    }
    
    @Override
    public ByteBuf setZero(final int n, final int n2) {
        this.buf.setZero(n, n2);
        return this;
    }
    
    @Override
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }
    
    @Override
    public byte readByte() {
        return this.buf.readByte();
    }
    
    @Override
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }
    
    @Override
    public short readShort() {
        return ByteBufUtil.swapShort(this.buf.readShort());
    }
    
    @Override
    public int readUnsignedShort() {
        return this.readShort() & 0xFFFF;
    }
    
    @Override
    public int readMedium() {
        return ByteBufUtil.swapMedium(this.buf.readMedium());
    }
    
    @Override
    public int readUnsignedMedium() {
        return this.readMedium() & 0xFFFFFF;
    }
    
    @Override
    public int readInt() {
        return ByteBufUtil.swapInt(this.buf.readInt());
    }
    
    @Override
    public long readUnsignedInt() {
        return (long)this.readInt() & 0xFFFFFFFFL;
    }
    
    @Override
    public long readLong() {
        return ByteBufUtil.swapLong(this.buf.readLong());
    }
    
    @Override
    public char readChar() {
        return (char)this.readShort();
    }
    
    @Override
    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }
    
    @Override
    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }
    
    @Override
    public ByteBuf readBytes(final int n) {
        return this.buf.readBytes(n).order(this.order());
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        return this.buf.readSlice(n).order(this.order);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        this.buf.readBytes(byteBuf);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        this.buf.readBytes(byteBuf, n);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.buf.readBytes(byteBuf, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        this.buf.readBytes(array);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        this.buf.readBytes(array, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        this.buf.readBytes(byteBuffer);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        this.buf.readBytes(outputStream, n);
        return this;
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, n);
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        this.buf.skipBytes(n);
        return this;
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean b) {
        this.buf.writeBoolean(b);
        return this;
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        this.buf.writeByte(n);
        return this;
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        this.buf.writeShort(ByteBufUtil.swapShort((short)n));
        return this;
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        this.buf.writeMedium(ByteBufUtil.swapMedium(n));
        return this;
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        this.buf.writeInt(ByteBufUtil.swapInt(n));
        return this;
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        this.buf.writeLong(ByteBufUtil.swapLong(n));
        return this;
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        this.writeShort(n);
        return this;
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        this.writeInt(Float.floatToRawIntBits(n));
        return this;
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        this.writeLong(Double.doubleToRawLongBits(n));
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        this.buf.writeBytes(byteBuf);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        this.buf.writeBytes(byteBuf, n);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.buf.writeBytes(byteBuf, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        this.buf.writeBytes(array);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        this.buf.writeBytes(array, n, n2);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        this.buf.writeBytes(byteBuffer);
        return this;
    }
    
    @Override
    public int writeBytes(final InputStream inputStream, final int n) throws IOException {
        return this.buf.writeBytes(inputStream, n);
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, n);
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        this.buf.writeZero(n);
        return this;
    }
    
    @Override
    public int indexOf(final int n, final int n2, final byte b) {
        return this.buf.indexOf(n, n2, b);
    }
    
    @Override
    public int bytesBefore(final byte b) {
        return this.buf.bytesBefore(b);
    }
    
    @Override
    public int bytesBefore(final int n, final byte b) {
        return this.buf.bytesBefore(n, b);
    }
    
    @Override
    public int bytesBefore(final int n, final int n2, final byte b) {
        return this.buf.bytesBefore(n, n2, b);
    }
    
    @Override
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(byteBufProcessor);
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByte(n, n2, byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buf.forEachByteDesc(n, n2, byteBufProcessor);
    }
    
    @Override
    public ByteBuf copy() {
        return this.buf.copy().order(this.order);
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        return this.buf.copy(n, n2).order(this.order);
    }
    
    @Override
    public ByteBuf slice() {
        return this.buf.slice().order(this.order);
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        return this.buf.slice(n, n2).order(this.order);
    }
    
    @Override
    public ByteBuf duplicate() {
        return this.buf.duplicate().order(this.order);
    }
    
    @Override
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer().order(this.order);
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        return this.buf.nioBuffer(n, n2).order(this.order);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return this.nioBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        final ByteBuffer[] nioBuffers = this.buf.nioBuffers();
        while (0 < nioBuffers.length) {
            nioBuffers[0] = nioBuffers[0].order(this.order);
            int n = 0;
            ++n;
        }
        return nioBuffers;
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        final ByteBuffer[] nioBuffers = this.buf.nioBuffers(n, n2);
        while (0 < nioBuffers.length) {
            nioBuffers[0] = nioBuffers[0].order(this.order);
            int n3 = 0;
            ++n3;
        }
        return nioBuffers;
    }
    
    @Override
    public boolean hasArray() {
        return this.buf.hasArray();
    }
    
    @Override
    public byte[] array() {
        return this.buf.array();
    }
    
    @Override
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        return this.buf.memoryAddress();
    }
    
    @Override
    public String toString(final Charset charset) {
        return this.buf.toString(charset);
    }
    
    @Override
    public String toString(final int n, final int n2, final Charset charset) {
        return this.buf.toString(n, n2, charset);
    }
    
    @Override
    public int refCnt() {
        return this.buf.refCnt();
    }
    
    @Override
    public ByteBuf retain() {
        this.buf.retain();
        return this;
    }
    
    @Override
    public ByteBuf retain(final int n) {
        this.buf.retain(n);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.buf.release();
    }
    
    @Override
    public boolean release(final int n) {
        return this.buf.release(n);
    }
    
    @Override
    public int hashCode() {
        return this.buf.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ByteBuf && ByteBufUtil.equals(this, (ByteBuf)o));
    }
    
    @Override
    public int compareTo(final ByteBuf byteBuf) {
        return ByteBufUtil.compare(this, byteBuf);
    }
    
    @Override
    public String toString() {
        return "Swapped(" + this.buf.toString() + ')';
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
}
