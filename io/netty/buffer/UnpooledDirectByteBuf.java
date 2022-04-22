package io.netty.buffer;

import java.nio.*;
import io.netty.util.internal.*;
import java.io.*;
import java.nio.channels.*;

public class UnpooledDirectByteBuf extends AbstractReferenceCountedByteBuf
{
    private final ByteBufAllocator alloc;
    private ByteBuffer buffer;
    private ByteBuffer tmpNioBuf;
    private int capacity;
    private boolean doNotFree;
    
    protected UnpooledDirectByteBuf(final ByteBufAllocator alloc, final int n, final int n2) {
        super(n2);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("maxCapacity: " + n2);
        }
        if (n > n2) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", n, n2));
        }
        this.alloc = alloc;
        this.setByteBuffer(ByteBuffer.allocateDirect(n));
    }
    
    protected UnpooledDirectByteBuf(final ByteBufAllocator alloc, final ByteBuffer byteBuffer, final int n) {
        super(n);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (byteBuffer == null) {
            throw new NullPointerException("initialBuffer");
        }
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        }
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
        final int remaining = byteBuffer.remaining();
        if (remaining > n) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", remaining, n));
        }
        this.alloc = alloc;
        this.doNotFree = true;
        this.setByteBuffer(byteBuffer.slice().order(ByteOrder.BIG_ENDIAN));
        this.writerIndex(remaining);
    }
    
    protected ByteBuffer allocateDirect(final int n) {
        return ByteBuffer.allocateDirect(n);
    }
    
    protected void freeDirect(final ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectBuffer(byteBuffer);
    }
    
    private void setByteBuffer(final ByteBuffer buffer) {
        final ByteBuffer buffer2 = this.buffer;
        if (buffer2 != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            }
            else {
                this.freeDirect(buffer2);
            }
        }
        this.buffer = buffer;
        this.tmpNioBuf = null;
        this.capacity = buffer.remaining();
    }
    
    @Override
    public boolean isDirect() {
        return true;
    }
    
    @Override
    public int capacity() {
        return this.capacity;
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        this.ensureAccessible();
        if (n < 0 || n > this.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + n);
        }
        final int readerIndex = this.readerIndex();
        int writerIndex = this.writerIndex();
        final int capacity = this.capacity;
        if (n > capacity) {
            final ByteBuffer buffer = this.buffer;
            final ByteBuffer allocateDirect = this.allocateDirect(n);
            buffer.position(0).limit(buffer.capacity());
            allocateDirect.position(0).limit(buffer.capacity());
            allocateDirect.put(buffer);
            allocateDirect.clear();
            this.setByteBuffer(allocateDirect);
        }
        else if (n < capacity) {
            final ByteBuffer buffer2 = this.buffer;
            final ByteBuffer allocateDirect2 = this.allocateDirect(n);
            if (readerIndex < n) {
                if (writerIndex > n) {
                    writerIndex = n;
                    this.writerIndex(n);
                }
                buffer2.position(readerIndex).limit(writerIndex);
                allocateDirect2.position(readerIndex).limit(writerIndex);
                allocateDirect2.put(buffer2);
                allocateDirect2.clear();
            }
            else {
                this.setIndex(n, n);
            }
            this.setByteBuffer(allocateDirect2);
        }
        return this;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.alloc;
    }
    
    @Override
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
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
        return false;
    }
    
    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
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
        this.getBytes(n, array, n2, n3, false);
        return this;
    }
    
    private void getBytes(final int n, final byte[] array, final int n2, final int n3, final boolean b) {
        this.checkDstIndex(n, n3, n2, array.length);
        if (n2 < 0 || n2 > array.length - n3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n2, n3, array.length));
        }
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = this.buffer.duplicate();
        }
        byteBuffer.clear().position(n).limit(n + n3);
        byteBuffer.get(array, n2, n3);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, array, n, n2, true);
        this.readerIndex += n2;
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.getBytes(n, byteBuffer, false);
        return this;
    }
    
    private void getBytes(final int n, final ByteBuffer byteBuffer, final boolean b) {
        this.checkIndex(n);
        if (byteBuffer == null) {
            throw new NullPointerException("dst");
        }
        final int min = Math.min(this.capacity() - n, byteBuffer.remaining());
        ByteBuffer byteBuffer2;
        if (b) {
            byteBuffer2 = this.internalNioBuffer();
        }
        else {
            byteBuffer2 = this.buffer.duplicate();
        }
        byteBuffer2.clear().position(n).limit(n + min);
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
    public ByteBuf setByte(final int n, final int n2) {
        this.ensureAccessible();
        this._setByte(n, n2);
        return this;
    }
    
    @Override
    protected void _setByte(final int n, final int n2) {
        this.buffer.put(n, (byte)n2);
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this.ensureAccessible();
        this._setShort(n, n2);
        return this;
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        this.buffer.putShort(n, (short)n2);
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        this.ensureAccessible();
        this._setMedium(n, n2);
        return this;
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        this.setByte(n, (byte)(n2 >>> 16));
        this.setByte(n + 1, (byte)(n2 >>> 8));
        this.setByte(n + 2, (byte)n2);
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this.ensureAccessible();
        this._setInt(n, n2);
        return this;
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        this.buffer.putInt(n, n2);
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this.ensureAccessible();
        this._setLong(n, n2);
        return this;
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        this.buffer.putLong(n, n2);
    }
    
    @Override
    public ByteBuf setBytes(int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.nioBufferCount() > 0) {
            final ByteBuffer[] nioBuffers = byteBuf.nioBuffers(n2, n3);
            while (0 < nioBuffers.length) {
                final ByteBuffer byteBuffer = nioBuffers[0];
                final int remaining = byteBuffer.remaining();
                this.setBytes(n, byteBuffer);
                n += remaining;
                int n4 = 0;
                ++n4;
            }
        }
        else {
            byteBuf.getBytes(n2, this, n, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, array.length);
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(n).limit(n + n3);
        internalNioBuffer.put(array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, ByteBuffer duplicate) {
        this.ensureAccessible();
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        if (duplicate == internalNioBuffer) {
            duplicate = duplicate.duplicate();
        }
        internalNioBuffer.clear().position(n).limit(n + duplicate.remaining());
        internalNioBuffer.put(duplicate);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.getBytes(n, outputStream, n2, false);
        return this;
    }
    
    private void getBytes(final int n, final OutputStream outputStream, final int n2, final boolean b) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return;
        }
        if (this.buffer.hasArray()) {
            outputStream.write(this.buffer.array(), n + this.buffer.arrayOffset(), n2);
        }
        else {
            final byte[] array = new byte[n2];
            ByteBuffer byteBuffer;
            if (b) {
                byteBuffer = this.internalNioBuffer();
            }
            else {
                byteBuffer = this.buffer.duplicate();
            }
            byteBuffer.clear().position(n);
            byteBuffer.get(array);
            outputStream.write(array);
        }
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, outputStream, n, true);
        this.readerIndex += n;
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }
    
    private int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2, final boolean b) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 0;
        }
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = this.buffer.duplicate();
        }
        byteBuffer.clear().position(n).limit(n + n2);
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
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        this.ensureAccessible();
        if (this.buffer.hasArray()) {
            return inputStream.read(this.buffer.array(), this.buffer.arrayOffset() + n, n2);
        }
        final byte[] array = new byte[n2];
        final int read = inputStream.read(array);
        if (read <= 0) {
            return read;
        }
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(n);
        internalNioBuffer.put(array, 0, read);
        return read;
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        this.ensureAccessible();
        this.internalNioBuffer().clear().position(n).limit(n + n2);
        return scatteringByteChannel.read(this.tmpNioBuf);
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
    public ByteBuf copy(final int n, final int n2) {
        this.ensureAccessible();
        return this.alloc().directBuffer(n2, this.maxCapacity()).writeBytes((ByteBuffer)this.buffer.duplicate().clear().position(n).limit(n + n2));
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }
    
    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf == null) {
            tmpNioBuf = (this.tmpNioBuf = this.buffer.duplicate());
        }
        return tmpNioBuf;
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return ((ByteBuffer)this.buffer.duplicate().position(n).limit(n + n2)).slice();
    }
    
    @Override
    protected void deallocate() {
        final ByteBuffer buffer = this.buffer;
        if (buffer == null) {
            return;
        }
        this.buffer = null;
        if (!this.doNotFree) {
            this.freeDirect(buffer);
        }
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
}
