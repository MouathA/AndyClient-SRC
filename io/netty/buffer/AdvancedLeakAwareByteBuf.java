package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import io.netty.util.*;

final class AdvancedLeakAwareByteBuf extends WrappedByteBuf
{
    private final ResourceLeak leak;
    
    AdvancedLeakAwareByteBuf(final ByteBuf byteBuf, final ResourceLeak leak) {
        super(byteBuf);
        this.leak = leak;
    }
    
    @Override
    public boolean release() {
        final boolean release = super.release();
        if (release) {
            this.leak.close();
        }
        else {
            this.leak.record();
        }
        return release;
    }
    
    @Override
    public boolean release(final int n) {
        final boolean release = super.release(n);
        if (release) {
            this.leak.close();
        }
        else {
            this.leak.record();
        }
        return release;
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        this.leak.record();
        if (this.order() == byteOrder) {
            return this;
        }
        return new AdvancedLeakAwareByteBuf(super.order(byteOrder), this.leak);
    }
    
    @Override
    public ByteBuf slice() {
        this.leak.record();
        return new AdvancedLeakAwareByteBuf(super.slice(), this.leak);
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        this.leak.record();
        return new AdvancedLeakAwareByteBuf(super.slice(n, n2), this.leak);
    }
    
    @Override
    public ByteBuf duplicate() {
        this.leak.record();
        return new AdvancedLeakAwareByteBuf(super.duplicate(), this.leak);
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        this.leak.record();
        return new AdvancedLeakAwareByteBuf(super.readSlice(n), this.leak);
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        this.leak.record();
        return super.discardReadBytes();
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        this.leak.record();
        return super.discardSomeReadBytes();
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        this.leak.record();
        return super.ensureWritable(n);
    }
    
    @Override
    public int ensureWritable(final int n, final boolean b) {
        this.leak.record();
        return super.ensureWritable(n, b);
    }
    
    @Override
    public boolean getBoolean(final int n) {
        this.leak.record();
        return super.getBoolean(n);
    }
    
    @Override
    public byte getByte(final int n) {
        this.leak.record();
        return super.getByte(n);
    }
    
    @Override
    public short getUnsignedByte(final int n) {
        this.leak.record();
        return super.getUnsignedByte(n);
    }
    
    @Override
    public short getShort(final int n) {
        this.leak.record();
        return super.getShort(n);
    }
    
    @Override
    public int getUnsignedShort(final int n) {
        this.leak.record();
        return super.getUnsignedShort(n);
    }
    
    @Override
    public int getMedium(final int n) {
        this.leak.record();
        return super.getMedium(n);
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        this.leak.record();
        return super.getUnsignedMedium(n);
    }
    
    @Override
    public int getInt(final int n) {
        this.leak.record();
        return super.getInt(n);
    }
    
    @Override
    public long getUnsignedInt(final int n) {
        this.leak.record();
        return super.getUnsignedInt(n);
    }
    
    @Override
    public long getLong(final int n) {
        this.leak.record();
        return super.getLong(n);
    }
    
    @Override
    public char getChar(final int n) {
        this.leak.record();
        return super.getChar(n);
    }
    
    @Override
    public float getFloat(final int n) {
        this.leak.record();
        return super.getFloat(n);
    }
    
    @Override
    public double getDouble(final int n) {
        this.leak.record();
        return super.getDouble(n);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        this.leak.record();
        return super.getBytes(n, byteBuf);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        this.leak.record();
        return super.getBytes(n, byteBuf, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.leak.record();
        return super.getBytes(n, byteBuf, n2, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array) {
        this.leak.record();
        return super.getBytes(n, array);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.leak.record();
        return super.getBytes(n, array, n2, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.leak.record();
        return super.getBytes(n, byteBuffer);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.leak.record();
        return super.getBytes(n, outputStream, n2);
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        this.leak.record();
        return super.getBytes(n, gatheringByteChannel, n2);
    }
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        this.leak.record();
        return super.setBoolean(n, b);
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        this.leak.record();
        return super.setByte(n, n2);
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this.leak.record();
        return super.setShort(n, n2);
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        this.leak.record();
        return super.setMedium(n, n2);
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this.leak.record();
        return super.setInt(n, n2);
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this.leak.record();
        return super.setLong(n, n2);
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        this.leak.record();
        return super.setChar(n, n2);
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        this.leak.record();
        return super.setFloat(n, n2);
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        this.leak.record();
        return super.setDouble(n, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        this.leak.record();
        return super.setBytes(n, byteBuf);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        this.leak.record();
        return super.setBytes(n, byteBuf, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.leak.record();
        return super.setBytes(n, byteBuf, n2, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        this.leak.record();
        return super.setBytes(n, array);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.leak.record();
        return super.setBytes(n, array, n2, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        this.leak.record();
        return super.setBytes(n, byteBuffer);
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        this.leak.record();
        return super.setBytes(n, inputStream, n2);
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        this.leak.record();
        return super.setBytes(n, scatteringByteChannel, n2);
    }
    
    @Override
    public ByteBuf setZero(final int n, final int n2) {
        this.leak.record();
        return super.setZero(n, n2);
    }
    
    @Override
    public boolean readBoolean() {
        this.leak.record();
        return super.readBoolean();
    }
    
    @Override
    public byte readByte() {
        this.leak.record();
        return super.readByte();
    }
    
    @Override
    public short readUnsignedByte() {
        this.leak.record();
        return super.readUnsignedByte();
    }
    
    @Override
    public short readShort() {
        this.leak.record();
        return super.readShort();
    }
    
    @Override
    public int readUnsignedShort() {
        this.leak.record();
        return super.readUnsignedShort();
    }
    
    @Override
    public int readMedium() {
        this.leak.record();
        return super.readMedium();
    }
    
    @Override
    public int readUnsignedMedium() {
        this.leak.record();
        return super.readUnsignedMedium();
    }
    
    @Override
    public int readInt() {
        this.leak.record();
        return super.readInt();
    }
    
    @Override
    public long readUnsignedInt() {
        this.leak.record();
        return super.readUnsignedInt();
    }
    
    @Override
    public long readLong() {
        this.leak.record();
        return super.readLong();
    }
    
    @Override
    public char readChar() {
        this.leak.record();
        return super.readChar();
    }
    
    @Override
    public float readFloat() {
        this.leak.record();
        return super.readFloat();
    }
    
    @Override
    public double readDouble() {
        this.leak.record();
        return super.readDouble();
    }
    
    @Override
    public ByteBuf readBytes(final int n) {
        this.leak.record();
        return super.readBytes(n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        this.leak.record();
        return super.readBytes(byteBuf);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        this.leak.record();
        return super.readBytes(byteBuf, n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.leak.record();
        return super.readBytes(byteBuf, n, n2);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        this.leak.record();
        return super.readBytes(array);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        this.leak.record();
        return super.readBytes(array, n, n2);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        this.leak.record();
        return super.readBytes(byteBuffer);
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        this.leak.record();
        return super.readBytes(outputStream, n);
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        this.leak.record();
        return super.readBytes(gatheringByteChannel, n);
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        this.leak.record();
        return super.skipBytes(n);
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean b) {
        this.leak.record();
        return super.writeBoolean(b);
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        this.leak.record();
        return super.writeByte(n);
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        this.leak.record();
        return super.writeShort(n);
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        this.leak.record();
        return super.writeMedium(n);
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        this.leak.record();
        return super.writeInt(n);
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        this.leak.record();
        return super.writeLong(n);
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        this.leak.record();
        return super.writeChar(n);
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        this.leak.record();
        return super.writeFloat(n);
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        this.leak.record();
        return super.writeDouble(n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        this.leak.record();
        return super.writeBytes(byteBuf);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        this.leak.record();
        return super.writeBytes(byteBuf, n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.leak.record();
        return super.writeBytes(byteBuf, n, n2);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        this.leak.record();
        return super.writeBytes(array);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        this.leak.record();
        return super.writeBytes(array, n, n2);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        this.leak.record();
        return super.writeBytes(byteBuffer);
    }
    
    @Override
    public int writeBytes(final InputStream inputStream, final int n) throws IOException {
        this.leak.record();
        return super.writeBytes(inputStream, n);
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        this.leak.record();
        return super.writeBytes(scatteringByteChannel, n);
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        this.leak.record();
        return super.writeZero(n);
    }
    
    @Override
    public int indexOf(final int n, final int n2, final byte b) {
        this.leak.record();
        return super.indexOf(n, n2, b);
    }
    
    @Override
    public int bytesBefore(final byte b) {
        this.leak.record();
        return super.bytesBefore(b);
    }
    
    @Override
    public int bytesBefore(final int n, final byte b) {
        this.leak.record();
        return super.bytesBefore(n, b);
    }
    
    @Override
    public int bytesBefore(final int n, final int n2, final byte b) {
        this.leak.record();
        return super.bytesBefore(n, n2, b);
    }
    
    @Override
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        this.leak.record();
        return super.forEachByte(byteBufProcessor);
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        this.leak.record();
        return super.forEachByte(n, n2, byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        this.leak.record();
        return super.forEachByteDesc(byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        this.leak.record();
        return super.forEachByteDesc(n, n2, byteBufProcessor);
    }
    
    @Override
    public ByteBuf copy() {
        this.leak.record();
        return super.copy();
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.leak.record();
        return super.copy(n, n2);
    }
    
    @Override
    public int nioBufferCount() {
        this.leak.record();
        return super.nioBufferCount();
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        this.leak.record();
        return super.nioBuffer();
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        this.leak.record();
        return super.nioBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        this.leak.record();
        return super.nioBuffers();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        this.leak.record();
        return super.nioBuffers(n, n2);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        this.leak.record();
        return super.internalNioBuffer(n, n2);
    }
    
    @Override
    public String toString(final Charset charset) {
        this.leak.record();
        return super.toString(charset);
    }
    
    @Override
    public String toString(final int n, final int n2, final Charset charset) {
        this.leak.record();
        return super.toString(n, n2, charset);
    }
    
    @Override
    public ByteBuf retain() {
        this.leak.record();
        return super.retain();
    }
    
    @Override
    public ByteBuf retain(final int n) {
        this.leak.record();
        return super.retain(n);
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        this.leak.record();
        return super.capacity(n);
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}
