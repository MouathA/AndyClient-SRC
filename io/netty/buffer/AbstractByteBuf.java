package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import io.netty.util.internal.*;
import io.netty.util.*;

public abstract class AbstractByteBuf extends ByteBuf
{
    static final ResourceLeakDetector leakDetector;
    int readerIndex;
    int writerIndex;
    private int markedReaderIndex;
    private int markedWriterIndex;
    private int maxCapacity;
    private SwappedByteBuf swappedBuf;
    
    protected AbstractByteBuf(final int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity + " (expected: >= 0)");
        }
        this.maxCapacity = maxCapacity;
    }
    
    @Override
    public int maxCapacity() {
        return this.maxCapacity;
    }
    
    protected final void maxCapacity(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    @Override
    public int readerIndex() {
        return this.readerIndex;
    }
    
    @Override
    public ByteBuf readerIndex(final int readerIndex) {
        if (readerIndex < 0 || readerIndex > this.writerIndex) {
            throw new IndexOutOfBoundsException(String.format("readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", readerIndex, this.writerIndex));
        }
        this.readerIndex = readerIndex;
        return this;
    }
    
    @Override
    public int writerIndex() {
        return this.writerIndex;
    }
    
    @Override
    public ByteBuf writerIndex(final int writerIndex) {
        if (writerIndex < this.readerIndex || writerIndex > this.capacity()) {
            throw new IndexOutOfBoundsException(String.format("writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))", writerIndex, this.readerIndex, this.capacity()));
        }
        this.writerIndex = writerIndex;
        return this;
    }
    
    @Override
    public ByteBuf setIndex(final int readerIndex, final int writerIndex) {
        if (readerIndex < 0 || readerIndex > writerIndex || writerIndex > this.capacity()) {
            throw new IndexOutOfBoundsException(String.format("readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))", readerIndex, writerIndex, this.capacity()));
        }
        this.readerIndex = readerIndex;
        this.writerIndex = writerIndex;
        return this;
    }
    
    @Override
    public ByteBuf clear() {
        final int n = 0;
        this.writerIndex = n;
        this.readerIndex = n;
        return this;
    }
    
    @Override
    public boolean isReadable() {
        return this.writerIndex > this.readerIndex;
    }
    
    @Override
    public boolean isReadable(final int n) {
        return this.writerIndex - this.readerIndex >= n;
    }
    
    @Override
    public boolean isWritable() {
        return this.capacity() > this.writerIndex;
    }
    
    @Override
    public boolean isWritable(final int n) {
        return this.capacity() - this.writerIndex >= n;
    }
    
    @Override
    public int readableBytes() {
        return this.writerIndex - this.readerIndex;
    }
    
    @Override
    public int writableBytes() {
        return this.capacity() - this.writerIndex;
    }
    
    @Override
    public int maxWritableBytes() {
        return this.maxCapacity() - this.writerIndex;
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        this.markedReaderIndex = this.readerIndex;
        return this;
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        this.readerIndex(this.markedReaderIndex);
        return this;
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        this.markedWriterIndex = this.writerIndex;
        return this;
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        this.writerIndex = this.markedWriterIndex;
        return this;
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        this.ensureAccessible();
        if (this.readerIndex == 0) {
            return this;
        }
        if (this.readerIndex != this.writerIndex) {
            this.setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
            this.writerIndex -= this.readerIndex;
            this.adjustMarkers(this.readerIndex);
            this.readerIndex = 0;
        }
        else {
            this.adjustMarkers(this.readerIndex);
            final int n = 0;
            this.readerIndex = n;
            this.writerIndex = n;
        }
        return this;
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        this.ensureAccessible();
        if (this.readerIndex == 0) {
            return this;
        }
        if (this.readerIndex == this.writerIndex) {
            this.adjustMarkers(this.readerIndex);
            final int n = 0;
            this.readerIndex = n;
            this.writerIndex = n;
            return this;
        }
        if (this.readerIndex >= this.capacity() >>> 1) {
            this.setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
            this.writerIndex -= this.readerIndex;
            this.adjustMarkers(this.readerIndex);
            this.readerIndex = 0;
        }
        return this;
    }
    
    protected final void adjustMarkers(final int n) {
        final int markedReaderIndex = this.markedReaderIndex;
        if (markedReaderIndex <= n) {
            this.markedReaderIndex = 0;
            final int markedWriterIndex = this.markedWriterIndex;
            if (markedWriterIndex <= n) {
                this.markedWriterIndex = 0;
            }
            else {
                this.markedWriterIndex = markedWriterIndex - n;
            }
        }
        else {
            this.markedReaderIndex = markedReaderIndex - n;
            this.markedWriterIndex -= n;
        }
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", n));
        }
        if (n <= this.writableBytes()) {
            return this;
        }
        if (n > this.maxCapacity - this.writerIndex) {
            throw new IndexOutOfBoundsException(String.format("writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s", this.writerIndex, n, this.maxCapacity, this));
        }
        this.capacity(this.calculateNewCapacity(this.writerIndex + n));
        return this;
    }
    
    @Override
    public int ensureWritable(final int n, final boolean b) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", n));
        }
        if (n <= this.writableBytes()) {
            return 0;
        }
        if (n <= this.maxCapacity - this.writerIndex || !b) {
            this.capacity(this.calculateNewCapacity(this.writerIndex + n));
            return 2;
        }
        if (this.capacity() == this.maxCapacity()) {
            return 1;
        }
        this.capacity(this.maxCapacity());
        return 3;
    }
    
    private int calculateNewCapacity(final int n) {
        final int maxCapacity = this.maxCapacity;
        if (n == 4194304) {
            return 4194304;
        }
        if (n > 4194304) {
            if (64 > maxCapacity - 4194304) {}
            return 64;
        }
        while (64 < n) {}
        return Math.min(64, maxCapacity);
    }
    
    @Override
    public ByteBuf order(final ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        SwappedByteBuf swappedBuf = this.swappedBuf;
        if (swappedBuf == null) {
            swappedBuf = (this.swappedBuf = this.newSwappedByteBuf());
        }
        return swappedBuf;
    }
    
    protected SwappedByteBuf newSwappedByteBuf() {
        return new SwappedByteBuf(this);
    }
    
    @Override
    public byte getByte(final int n) {
        this.checkIndex(n);
        return this._getByte(n);
    }
    
    protected abstract byte _getByte(final int p0);
    
    @Override
    public boolean getBoolean(final int n) {
        return this.getByte(n) != 0;
    }
    
    @Override
    public short getUnsignedByte(final int n) {
        return (short)(this.getByte(n) & 0xFF);
    }
    
    @Override
    public short getShort(final int n) {
        this.checkIndex(n, 2);
        return this._getShort(n);
    }
    
    protected abstract short _getShort(final int p0);
    
    @Override
    public int getUnsignedShort(final int n) {
        return this.getShort(n) & 0xFFFF;
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        this.checkIndex(n, 3);
        return this._getUnsignedMedium(n);
    }
    
    protected abstract int _getUnsignedMedium(final int p0);
    
    @Override
    public int getMedium(final int n) {
        int unsignedMedium = this.getUnsignedMedium(n);
        if ((unsignedMedium & 0x800000) != 0x0) {
            unsignedMedium |= 0xFF000000;
        }
        return unsignedMedium;
    }
    
    @Override
    public int getInt(final int n) {
        this.checkIndex(n, 4);
        return this._getInt(n);
    }
    
    protected abstract int _getInt(final int p0);
    
    @Override
    public long getUnsignedInt(final int n) {
        return (long)this.getInt(n) & 0xFFFFFFFFL;
    }
    
    @Override
    public long getLong(final int n) {
        this.checkIndex(n, 8);
        return this._getLong(n);
    }
    
    protected abstract long _getLong(final int p0);
    
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
    public ByteBuf getBytes(final int n, final byte[] array) {
        this.getBytes(n, array, 0, array.length);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        this.getBytes(n, byteBuf, byteBuf.writableBytes());
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        this.getBytes(n, byteBuf, byteBuf.writerIndex(), n2);
        byteBuf.writerIndex(byteBuf.writerIndex() + n2);
        return this;
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        this.checkIndex(n);
        this._setByte(n, n2);
        return this;
    }
    
    protected abstract void _setByte(final int p0, final int p1);
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        this.setByte(n, b ? 1 : 0);
        return this;
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this.checkIndex(n, 2);
        this._setShort(n, n2);
        return this;
    }
    
    protected abstract void _setShort(final int p0, final int p1);
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        this.setShort(n, n2);
        return this;
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        this.checkIndex(n, 3);
        this._setMedium(n, n2);
        return this;
    }
    
    protected abstract void _setMedium(final int p0, final int p1);
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this.checkIndex(n, 4);
        this._setInt(n, n2);
        return this;
    }
    
    protected abstract void _setInt(final int p0, final int p1);
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        this.setInt(n, Float.floatToRawIntBits(n2));
        return this;
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this.checkIndex(n, 8);
        this._setLong(n, n2);
        return this;
    }
    
    protected abstract void _setLong(final int p0, final long p1);
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        this.setLong(n, Double.doubleToRawLongBits(n2));
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        this.setBytes(n, array, 0, array.length);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        this.setBytes(n, byteBuf, byteBuf.readableBytes());
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        this.checkIndex(n, n2);
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (n2 > byteBuf.readableBytes()) {
            throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", n2, byteBuf.readableBytes(), byteBuf));
        }
        this.setBytes(n, byteBuf, byteBuf.readerIndex(), n2);
        byteBuf.readerIndex(byteBuf.readerIndex() + n2);
        return this;
    }
    
    @Override
    public ByteBuf setZero(int n, final int n2) {
        if (n2 == 0) {
            return this;
        }
        this.checkIndex(n, n2);
        final int n3 = n2 >>> 3;
        final int n4 = n2 & 0x7;
        for (int i = n3; i > 0; --i) {
            this.setLong(n, 0L);
            n += 8;
        }
        if (n4 == 4) {
            this.setInt(n, 0);
        }
        else if (n4 < 4) {
            for (int j = n4; j > 0; --j) {
                this.setByte(n, 0);
                ++n;
            }
        }
        else {
            this.setInt(n, 0);
            n += 4;
            for (int k = n4 - 4; k > 0; --k) {
                this.setByte(n, 0);
                ++n;
            }
        }
        return this;
    }
    
    @Override
    public byte readByte() {
        this.checkReadableBytes(1);
        final int readerIndex = this.readerIndex;
        final byte byte1 = this.getByte(readerIndex);
        this.readerIndex = readerIndex + 1;
        return byte1;
    }
    
    @Override
    public boolean readBoolean() {
        return this.readByte() != 0;
    }
    
    @Override
    public short readUnsignedByte() {
        return (short)(this.readByte() & 0xFF);
    }
    
    @Override
    public short readShort() {
        this.checkReadableBytes(2);
        final short getShort = this._getShort(this.readerIndex);
        this.readerIndex += 2;
        return getShort;
    }
    
    @Override
    public int readUnsignedShort() {
        return this.readShort() & 0xFFFF;
    }
    
    @Override
    public int readMedium() {
        int unsignedMedium = this.readUnsignedMedium();
        if ((unsignedMedium & 0x800000) != 0x0) {
            unsignedMedium |= 0xFF000000;
        }
        return unsignedMedium;
    }
    
    @Override
    public int readUnsignedMedium() {
        this.checkReadableBytes(3);
        final int getUnsignedMedium = this._getUnsignedMedium(this.readerIndex);
        this.readerIndex += 3;
        return getUnsignedMedium;
    }
    
    @Override
    public int readInt() {
        this.checkReadableBytes(4);
        final int getInt = this._getInt(this.readerIndex);
        this.readerIndex += 4;
        return getInt;
    }
    
    @Override
    public long readUnsignedInt() {
        return (long)this.readInt() & 0xFFFFFFFFL;
    }
    
    @Override
    public long readLong() {
        this.checkReadableBytes(8);
        final long getLong = this._getLong(this.readerIndex);
        this.readerIndex += 8;
        return getLong;
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
        this.checkReadableBytes(n);
        if (n == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = Unpooled.buffer(n, this.maxCapacity);
        buffer.writeBytes(this, this.readerIndex, n);
        this.readerIndex += n;
        return buffer;
    }
    
    @Override
    public ByteBuf readSlice(final int n) {
        final ByteBuf slice = this.slice(this.readerIndex, n);
        this.readerIndex += n;
        return slice;
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, array, n, n2);
        this.readerIndex += n2;
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        this.readBytes(array, 0, array.length);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        this.readBytes(byteBuf, byteBuf.writableBytes());
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        if (n > byteBuf.writableBytes()) {
            throw new IndexOutOfBoundsException(String.format("length(%d) exceeds dst.writableBytes(%d) where dst is: %s", n, byteBuf.writableBytes(), byteBuf));
        }
        this.readBytes(byteBuf, byteBuf.writerIndex(), n);
        byteBuf.writerIndex(byteBuf.writerIndex() + n);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, byteBuf, n, n2);
        this.readerIndex += n2;
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        this.checkReadableBytes(remaining);
        this.getBytes(this.readerIndex, byteBuffer);
        this.readerIndex += remaining;
        return this;
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        this.checkReadableBytes(n);
        final int bytes = this.getBytes(this.readerIndex, gatheringByteChannel, n);
        this.readerIndex += bytes;
        return bytes;
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, outputStream, n);
        this.readerIndex += n;
        return this;
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        this.checkReadableBytes(n);
        this.readerIndex += n;
        return this;
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean b) {
        this.writeByte(b ? 1 : 0);
        return this;
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        this.ensureAccessible();
        this.ensureWritable(1);
        this._setByte(this.writerIndex++, n);
        return this;
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        this.ensureAccessible();
        this.ensureWritable(2);
        this._setShort(this.writerIndex, n);
        this.writerIndex += 2;
        return this;
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        this.ensureAccessible();
        this.ensureWritable(3);
        this._setMedium(this.writerIndex, n);
        this.writerIndex += 3;
        return this;
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        this.ensureAccessible();
        this.ensureWritable(4);
        this._setInt(this.writerIndex, n);
        this.writerIndex += 4;
        return this;
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        this.ensureAccessible();
        this.ensureWritable(8);
        this._setLong(this.writerIndex, n);
        this.writerIndex += 8;
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
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        this.ensureAccessible();
        this.ensureWritable(n2);
        this.setBytes(this.writerIndex, array, n, n2);
        this.writerIndex += n2;
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        this.writeBytes(array, 0, array.length);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        this.writeBytes(byteBuf, byteBuf.readableBytes());
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        if (n > byteBuf.readableBytes()) {
            throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", n, byteBuf.readableBytes(), byteBuf));
        }
        this.writeBytes(byteBuf, byteBuf.readerIndex(), n);
        byteBuf.readerIndex(byteBuf.readerIndex() + n);
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        this.ensureAccessible();
        this.ensureWritable(n2);
        this.setBytes(this.writerIndex, byteBuf, n, n2);
        this.writerIndex += n2;
        return this;
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        this.ensureAccessible();
        final int remaining = byteBuffer.remaining();
        this.ensureWritable(remaining);
        this.setBytes(this.writerIndex, byteBuffer);
        this.writerIndex += remaining;
        return this;
    }
    
    @Override
    public int writeBytes(final InputStream inputStream, final int n) throws IOException {
        this.ensureAccessible();
        this.ensureWritable(n);
        final int setBytes = this.setBytes(this.writerIndex, inputStream, n);
        if (setBytes > 0) {
            this.writerIndex += setBytes;
        }
        return setBytes;
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel scatteringByteChannel, final int n) throws IOException {
        this.ensureAccessible();
        this.ensureWritable(n);
        final int setBytes = this.setBytes(this.writerIndex, scatteringByteChannel, n);
        if (setBytes > 0) {
            this.writerIndex += setBytes;
        }
        return setBytes;
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        if (n == 0) {
            return this;
        }
        this.ensureWritable(n);
        this.checkIndex(this.writerIndex, n);
        final int n2 = n >>> 3;
        final int n3 = n & 0x7;
        for (int i = n2; i > 0; --i) {
            this.writeLong(0L);
        }
        if (n3 == 4) {
            this.writeInt(0);
        }
        else if (n3 < 4) {
            for (int j = n3; j > 0; --j) {
                this.writeByte(0);
            }
        }
        else {
            this.writeInt(0);
            for (int k = n3 - 4; k > 0; --k) {
                this.writeByte(0);
            }
        }
        return this;
    }
    
    @Override
    public ByteBuf copy() {
        return this.copy(this.readerIndex, this.readableBytes());
    }
    
    @Override
    public ByteBuf duplicate() {
        return new DuplicatedByteBuf(this);
    }
    
    @Override
    public ByteBuf slice() {
        return this.slice(this.readerIndex, this.readableBytes());
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return new SlicedByteBuf(this, n, n2);
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        return this.nioBuffer(this.readerIndex, this.readableBytes());
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        return this.nioBuffers(this.readerIndex, this.readableBytes());
    }
    
    @Override
    public String toString(final Charset charset) {
        return this.toString(this.readerIndex, this.readableBytes(), charset);
    }
    
    @Override
    public String toString(final int n, final int n2, final Charset charset) {
        if (n2 == 0) {
            return "";
        }
        ByteBuffer byteBuffer;
        if (this.nioBufferCount() == 1) {
            byteBuffer = this.nioBuffer(n, n2);
        }
        else {
            byteBuffer = ByteBuffer.allocate(n2);
            this.getBytes(n, byteBuffer);
            byteBuffer.flip();
        }
        return ByteBufUtil.decodeString(byteBuffer, charset);
    }
    
    @Override
    public int indexOf(final int n, final int n2, final byte b) {
        return ByteBufUtil.indexOf(this, n, n2, b);
    }
    
    @Override
    public int bytesBefore(final byte b) {
        return this.bytesBefore(this.readerIndex(), this.readableBytes(), b);
    }
    
    @Override
    public int bytesBefore(final int n, final byte b) {
        this.checkReadableBytes(n);
        return this.bytesBefore(this.readerIndex(), n, b);
    }
    
    @Override
    public int bytesBefore(final int n, final int n2, final byte b) {
        final int index = this.indexOf(n, n + n2, b);
        if (index < 0) {
            return -1;
        }
        return index - n;
    }
    
    @Override
    public int forEachByte(final ByteBufProcessor byteBufProcessor) {
        final int readerIndex = this.readerIndex;
        final int n = this.writerIndex - readerIndex;
        this.ensureAccessible();
        return this.forEachByteAsc0(readerIndex, n, byteBufProcessor);
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        this.checkIndex(n, n2);
        return this.forEachByteAsc0(n, n2, byteBufProcessor);
    }
    
    private int forEachByteAsc0(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        if (byteBufProcessor == null) {
            throw new NullPointerException("processor");
        }
        if (n2 == 0) {
            return -1;
        }
        final int n3 = n + n2;
        int n4 = n;
        while (byteBufProcessor.process(this._getByte(n4))) {
            ++n4;
            if (n4 >= n3) {
                return -1;
            }
        }
        return n4;
    }
    
    @Override
    public int forEachByteDesc(final ByteBufProcessor byteBufProcessor) {
        final int readerIndex = this.readerIndex;
        final int n = this.writerIndex - readerIndex;
        this.ensureAccessible();
        return this.forEachByteDesc0(readerIndex, n, byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        this.checkIndex(n, n2);
        return this.forEachByteDesc0(n, n2, byteBufProcessor);
    }
    
    private int forEachByteDesc0(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        if (byteBufProcessor == null) {
            throw new NullPointerException("processor");
        }
        if (n2 == 0) {
            return -1;
        }
        int n3 = n + n2 - 1;
        while (byteBufProcessor.process(this._getByte(n3))) {
            --n3;
            if (n3 < n) {
                return -1;
            }
        }
        return n3;
    }
    
    @Override
    public int hashCode() {
        return ByteBufUtil.hashCode(this);
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
        if (this.refCnt() == 0) {
            return StringUtil.simpleClassName(this) + "(freed)";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append("(ridx: ");
        sb.append(this.readerIndex);
        sb.append(", widx: ");
        sb.append(this.writerIndex);
        sb.append(", cap: ");
        sb.append(this.capacity());
        if (this.maxCapacity != Integer.MAX_VALUE) {
            sb.append('/');
            sb.append(this.maxCapacity);
        }
        final ByteBuf unwrap = this.unwrap();
        if (unwrap != null) {
            sb.append(", unwrapped: ");
            sb.append(unwrap);
        }
        sb.append(')');
        return sb.toString();
    }
    
    protected final void checkIndex(final int n) {
        this.ensureAccessible();
        if (n < 0 || n >= this.capacity()) {
            throw new IndexOutOfBoundsException(String.format("index: %d (expected: range(0, %d))", n, this.capacity()));
        }
    }
    
    protected final void checkIndex(final int n, final int n2) {
        this.ensureAccessible();
        if (n2 < 0) {
            throw new IllegalArgumentException("length: " + n2 + " (expected: >= 0)");
        }
        if (n < 0 || n > this.capacity() - n2) {
            throw new IndexOutOfBoundsException(String.format("index: %d, length: %d (expected: range(0, %d))", n, n2, this.capacity()));
        }
    }
    
    protected final void checkSrcIndex(final int n, final int n2, final int n3, final int n4) {
        this.checkIndex(n, n2);
        if (n3 < 0 || n3 > n4 - n2) {
            throw new IndexOutOfBoundsException(String.format("srcIndex: %d, length: %d (expected: range(0, %d))", n3, n2, n4));
        }
    }
    
    protected final void checkDstIndex(final int n, final int n2, final int n3, final int n4) {
        this.checkIndex(n, n2);
        if (n3 < 0 || n3 > n4 - n2) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n3, n2, n4));
        }
    }
    
    protected final void checkReadableBytes(final int n) {
        this.ensureAccessible();
        if (n < 0) {
            throw new IllegalArgumentException("minimumReadableBytes: " + n + " (expected: >= 0)");
        }
        if (this.readerIndex > this.writerIndex - n) {
            throw new IndexOutOfBoundsException(String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", this.readerIndex, n, this.writerIndex, this));
        }
    }
    
    protected final void ensureAccessible() {
        if (this.refCnt() == 0) {
            throw new IllegalReferenceCountException(0);
        }
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ByteBuf)o);
    }
    
    static {
        leakDetector = new ResourceLeakDetector(ByteBuf.class);
    }
}
