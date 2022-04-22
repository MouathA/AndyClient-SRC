package io.netty.buffer;

import io.netty.util.internal.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;

class ReadOnlyByteBufferBuf extends AbstractReferenceCountedByteBuf
{
    protected final ByteBuffer buffer;
    private final ByteBufAllocator allocator;
    private ByteBuffer tmpNioBuf;
    
    ReadOnlyByteBufferBuf(final ByteBufAllocator allocator, final ByteBuffer byteBuffer) {
        super(byteBuffer.remaining());
        if (!byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("must be a readonly buffer: " + StringUtil.simpleClassName(byteBuffer));
        }
        this.allocator = allocator;
        this.buffer = byteBuffer.slice().order(ByteOrder.BIG_ENDIAN);
        this.writerIndex(this.buffer.limit());
    }
    
    @Override
    protected void deallocate() {
    }
    
    @Override
    public byte getByte(final int n) {
        this.ensureAccessible();
        return this._getByte(n);
    }
    
    @Override
    protected byte _getByte(final int n) {
        return this.buffer.get(n);
    }
    
    @Override
    public short getShort(final int n) {
        this.ensureAccessible();
        return this._getShort(n);
    }
    
    @Override
    protected short _getShort(final int n) {
        return this.buffer.getShort(n);
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        this.ensureAccessible();
        return this._getUnsignedMedium(n);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        return (this.getByte(n) & 0xFF) << 16 | (this.getByte(n + 1) & 0xFF) << 8 | (this.getByte(n + 2) & 0xFF);
    }
    
    @Override
    public int getInt(final int n) {
        this.ensureAccessible();
        return this._getInt(n);
    }
    
    @Override
    protected int _getInt(final int n) {
        return this.buffer.getInt(n);
    }
    
    @Override
    public long getLong(final int n) {
        this.ensureAccessible();
        return this._getLong(n);
    }
    
    @Override
    protected long _getLong(final int n) {
        return this.buffer.getLong(n);
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
        this.checkDstIndex(n, n3, n2, array.length);
        if (n2 < 0 || n2 > array.length - n3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n2, n3, array.length));
        }
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(n).limit(n + n3);
        internalNioBuffer.get(array, n2, n3);
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
    protected void _setByte(final int n, final int n2) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public int capacity() {
        return this.maxCapacity();
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.allocator;
    }
    
    @Override
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
    
    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return this;
        }
        if (this.buffer.hasArray()) {
            outputStream.write(this.buffer.array(), n + this.buffer.arrayOffset(), n2);
        }
        else {
            final byte[] array = new byte[n2];
            final ByteBuffer internalNioBuffer = this.internalNioBuffer();
            internalNioBuffer.clear().position(n);
            internalNioBuffer.get(array);
            outputStream.write(array);
        }
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 0;
        }
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(n).limit(n + n2);
        return gatheringByteChannel.write(internalNioBuffer);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        throw new ReadOnlyBufferException();
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        throw new ReadOnlyBufferException();
    }
    
    protected final ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf == null) {
            tmpNioBuf = (this.tmpNioBuf = this.buffer.duplicate());
        }
        return tmpNioBuf;
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.ensureAccessible();
        final ByteBuffer byteBuffer = (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(n2);
        allocateDirect.put(byteBuffer);
        allocateDirect.order(this.order());
        allocateDirect.clear();
        return new UnpooledDirectByteBuf(this.alloc(), allocateDirect, this.maxCapacity());
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
    public ByteBuffer nioBuffer(final int n, final int n2) {
        return (ByteBuffer)this.buffer.duplicate().position(n).limit(n + n2);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        this.ensureAccessible();
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
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
        return false;
    }
    
    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
}
