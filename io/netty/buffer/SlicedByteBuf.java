package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;

public class SlicedByteBuf extends AbstractDerivedByteBuf
{
    private final ByteBuf buffer;
    private final int adjustment;
    private final int length;
    
    public SlicedByteBuf(final ByteBuf buffer, final int n, final int length) {
        super(length);
        if (n < 0 || n > buffer.capacity() - length) {
            throw new IndexOutOfBoundsException(buffer + ".slice(" + n + ", " + length + ')');
        }
        if (buffer instanceof SlicedByteBuf) {
            this.buffer = ((SlicedByteBuf)buffer).buffer;
            this.adjustment = ((SlicedByteBuf)buffer).adjustment + n;
        }
        else if (buffer instanceof DuplicatedByteBuf) {
            this.buffer = buffer.unwrap();
            this.adjustment = n;
        }
        else {
            this.buffer = buffer;
            this.adjustment = n;
        }
        this.writerIndex(this.length = length);
    }
    
    @Override
    public ByteBuf unwrap() {
        return this.buffer;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.buffer.alloc();
    }
    
    @Override
    public ByteOrder order() {
        return this.buffer.order();
    }
    
    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }
    
    @Override
    public int capacity() {
        return this.length;
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        throw new UnsupportedOperationException("sliced buffer");
    }
    
    @Override
    public boolean hasArray() {
        return this.buffer.hasArray();
    }
    
    @Override
    public byte[] array() {
        return this.buffer.array();
    }
    
    @Override
    public int arrayOffset() {
        return this.buffer.arrayOffset() + this.adjustment;
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.buffer.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        return this.buffer.memoryAddress() + this.adjustment;
    }
    
    @Override
    protected byte _getByte(final int n) {
        return this.buffer.getByte(n + this.adjustment);
    }
    
    @Override
    protected short _getShort(final int n) {
        return this.buffer.getShort(n + this.adjustment);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        return this.buffer.getUnsignedMedium(n + this.adjustment);
    }
    
    @Override
    protected int _getInt(final int n) {
        return this.buffer.getInt(n + this.adjustment);
    }
    
    @Override
    protected long _getLong(final int n) {
        return this.buffer.getLong(n + this.adjustment);
    }
    
    @Override
    public ByteBuf duplicate() {
        final ByteBuf slice = this.buffer.slice(this.adjustment, this.length);
        slice.setIndex(this.readerIndex(), this.writerIndex());
        return slice;
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.copy(n + this.adjustment, n2);
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return this.buffer.slice(n + this.adjustment, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        this.buffer.getBytes(n + this.adjustment, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        this.buffer.getBytes(n + this.adjustment, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.checkIndex(n, byteBuffer.remaining());
        this.buffer.getBytes(n + this.adjustment, byteBuffer);
        return this;
    }
    
    @Override
    protected void _setByte(final int n, final int n2) {
        this.buffer.setByte(n + this.adjustment, n2);
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        this.buffer.setShort(n + this.adjustment, n2);
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        this.buffer.setMedium(n + this.adjustment, n2);
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        this.buffer.setInt(n + this.adjustment, n2);
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        this.buffer.setLong(n + this.adjustment, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        this.buffer.setBytes(n + this.adjustment, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        this.buffer.setBytes(n + this.adjustment, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        this.checkIndex(n, byteBuffer.remaining());
        this.buffer.setBytes(n + this.adjustment, byteBuffer);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.checkIndex(n, n2);
        this.buffer.getBytes(n + this.adjustment, outputStream, n2);
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        this.checkIndex(n, n2);
        return this.buffer.getBytes(n + this.adjustment, gatheringByteChannel, n2);
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        this.checkIndex(n, n2);
        return this.buffer.setBytes(n + this.adjustment, inputStream, n2);
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        this.checkIndex(n, n2);
        return this.buffer.setBytes(n + this.adjustment, scatteringByteChannel, n2);
    }
    
    @Override
    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.nioBuffer(n + this.adjustment, n2);
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.buffer.nioBuffers(n + this.adjustment, n2);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return this.nioBuffer(n, n2);
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        final int forEachByte = this.buffer.forEachByte(n + this.adjustment, n2, byteBufProcessor);
        if (forEachByte >= this.adjustment) {
            return forEachByte - this.adjustment;
        }
        return -1;
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        final int forEachByteDesc = this.buffer.forEachByteDesc(n + this.adjustment, n2, byteBufProcessor);
        if (forEachByteDesc >= this.adjustment) {
            return forEachByteDesc - this.adjustment;
        }
        return -1;
    }
}
