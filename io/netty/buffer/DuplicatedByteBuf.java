package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;

public class DuplicatedByteBuf extends AbstractDerivedByteBuf
{
    private final ByteBuf buffer;
    
    public DuplicatedByteBuf(final ByteBuf buffer) {
        super(buffer.maxCapacity());
        if (buffer instanceof DuplicatedByteBuf) {
            this.buffer = ((DuplicatedByteBuf)buffer).buffer;
        }
        else {
            this.buffer = buffer;
        }
        this.setIndex(buffer.readerIndex(), buffer.writerIndex());
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
        return this.buffer.capacity();
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        this.buffer.capacity(n);
        return this;
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
        return this.buffer.arrayOffset();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.buffer.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        return this.buffer.memoryAddress();
    }
    
    @Override
    public byte getByte(final int n) {
        return this._getByte(n);
    }
    
    @Override
    protected byte _getByte(final int n) {
        return this.buffer.getByte(n);
    }
    
    @Override
    public short getShort(final int n) {
        return this._getShort(n);
    }
    
    @Override
    protected short _getShort(final int n) {
        return this.buffer.getShort(n);
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        return this._getUnsignedMedium(n);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        return this.buffer.getUnsignedMedium(n);
    }
    
    @Override
    public int getInt(final int n) {
        return this._getInt(n);
    }
    
    @Override
    protected int _getInt(final int n) {
        return this.buffer.getInt(n);
    }
    
    @Override
    public long getLong(final int n) {
        return this._getLong(n);
    }
    
    @Override
    protected long _getLong(final int n) {
        return this.buffer.getLong(n);
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        return this.buffer.copy(n, n2);
    }
    
    @Override
    public ByteBuf slice(final int n, final int n2) {
        return this.buffer.slice(n, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.buffer.getBytes(n, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.buffer.getBytes(n, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.buffer.getBytes(n, byteBuffer);
        return this;
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        this._setByte(n, n2);
        return this;
    }
    
    @Override
    protected void _setByte(final int n, final int n2) {
        this.buffer.setByte(n, n2);
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this._setShort(n, n2);
        return this;
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        this.buffer.setShort(n, n2);
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        this._setMedium(n, n2);
        return this;
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        this.buffer.setMedium(n, n2);
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this._setInt(n, n2);
        return this;
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        this.buffer.setInt(n, n2);
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this._setLong(n, n2);
        return this;
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        this.buffer.setLong(n, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.buffer.setBytes(n, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.buffer.setBytes(n, byteBuf, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        this.buffer.setBytes(n, byteBuffer);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.buffer.getBytes(n, outputStream, n2);
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.buffer.getBytes(n, gatheringByteChannel, n2);
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        return this.buffer.setBytes(n, inputStream, n2);
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        return this.buffer.setBytes(n, scatteringByteChannel, n2);
    }
    
    @Override
    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return this.buffer.nioBuffers(n, n2);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        return this.nioBuffer(n, n2);
    }
    
    @Override
    public int forEachByte(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buffer.forEachByte(n, n2, byteBufProcessor);
    }
    
    @Override
    public int forEachByteDesc(final int n, final int n2, final ByteBufProcessor byteBufProcessor) {
        return this.buffer.forEachByteDesc(n, n2, byteBufProcessor);
    }
}
