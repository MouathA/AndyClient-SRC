package io.netty.buffer;

import java.nio.*;
import io.netty.util.internal.*;
import java.io.*;
import java.nio.channels.*;

public class UnpooledHeapByteBuf extends AbstractReferenceCountedByteBuf
{
    private final ByteBufAllocator alloc;
    private byte[] array;
    private ByteBuffer tmpNioBuf;
    
    protected UnpooledHeapByteBuf(final ByteBufAllocator byteBufAllocator, final int n, final int n2) {
        this(byteBufAllocator, new byte[n], 0, 0, n2);
    }
    
    protected UnpooledHeapByteBuf(final ByteBufAllocator byteBufAllocator, final byte[] array, final int n) {
        this(byteBufAllocator, array, 0, array.length, n);
    }
    
    private UnpooledHeapByteBuf(final ByteBufAllocator alloc, final byte[] array, final int n, final int n2, final int n3) {
        super(n3);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (array == null) {
            throw new NullPointerException("initialArray");
        }
        if (array.length > n3) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", array.length, n3));
        }
        this.alloc = alloc;
        this.setArray(array);
        this.setIndex(n, n2);
    }
    
    private void setArray(final byte[] array) {
        this.array = array;
        this.tmpNioBuf = null;
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
    public boolean isDirect() {
        return false;
    }
    
    @Override
    public int capacity() {
        this.ensureAccessible();
        return this.array.length;
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        this.ensureAccessible();
        if (n < 0 || n > this.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + n);
        }
        final int length = this.array.length;
        if (n > length) {
            final byte[] array = new byte[n];
            System.arraycopy(this.array, 0, array, 0, this.array.length);
            this.setArray(array);
        }
        else if (n < length) {
            final byte[] array2 = new byte[n];
            final int readerIndex = this.readerIndex();
            if (readerIndex < n) {
                int writerIndex = this.writerIndex();
                if (writerIndex > n) {
                    writerIndex = n;
                    this.writerIndex(n);
                }
                System.arraycopy(this.array, readerIndex, array2, readerIndex, writerIndex - readerIndex);
            }
            else {
                this.setIndex(n, n);
            }
            this.setArray(array2);
        }
        return this;
    }
    
    @Override
    public boolean hasArray() {
        return true;
    }
    
    @Override
    public byte[] array() {
        this.ensureAccessible();
        return this.array;
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
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.array, n, byteBuf.memoryAddress() + n2, n3);
        }
        else if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else {
            byteBuf.setBytes(n2, this.array, n, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkDstIndex(n, n3, n2, array.length);
        System.arraycopy(this.array, n, array, n2, n3);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        this.ensureAccessible();
        byteBuffer.put(this.array, n, Math.min(this.capacity() - n, byteBuffer.remaining()));
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        this.ensureAccessible();
        outputStream.write(this.array, n, n2);
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        this.ensureAccessible();
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }
    
    private int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2, final boolean b) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer;
        if (b) {
            byteBuffer = this.internalNioBuffer();
        }
        else {
            byteBuffer = ByteBuffer.wrap(this.array);
        }
        return gatheringByteChannel.write((ByteBuffer)byteBuffer.clear().position(n).limit(n + n2));
    }
    
    @Override
    public int readBytes(final GatheringByteChannel gatheringByteChannel, final int n) throws IOException {
        this.checkReadableBytes(n);
        final int bytes = this.getBytes(this.readerIndex, gatheringByteChannel, n, true);
        this.readerIndex += bytes;
        return bytes;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + n2, this.array, n, n3);
        }
        else if (byteBuf.hasArray()) {
            this.setBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else {
            byteBuf.getBytes(n2, this.array, n, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkSrcIndex(n, n3, n2, array.length);
        System.arraycopy(array, n2, this.array, n, n3);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        this.ensureAccessible();
        byteBuffer.get(this.array, n, byteBuffer.remaining());
        return this;
    }
    
    @Override
    public int setBytes(final int n, final InputStream inputStream, final int n2) throws IOException {
        this.ensureAccessible();
        return inputStream.read(this.array, n, n2);
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        this.ensureAccessible();
        return scatteringByteChannel.read((ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2));
    }
    
    @Override
    public int nioBufferCount() {
        return 1;
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        this.ensureAccessible();
        return ByteBuffer.wrap(this.array, n, n2).slice();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int n, final int n2) {
        return new ByteBuffer[] { this.nioBuffer(n, n2) };
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        this.checkIndex(n, n2);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }
    
    @Override
    public byte getByte(final int n) {
        this.ensureAccessible();
        return this._getByte(n);
    }
    
    @Override
    protected byte _getByte(final int n) {
        return this.array[n];
    }
    
    @Override
    public short getShort(final int n) {
        this.ensureAccessible();
        return this._getShort(n);
    }
    
    @Override
    protected short _getShort(final int n) {
        return (short)(this.array[n] << 8 | (this.array[n + 1] & 0xFF));
    }
    
    @Override
    public int getUnsignedMedium(final int n) {
        this.ensureAccessible();
        return this._getUnsignedMedium(n);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        return (this.array[n] & 0xFF) << 16 | (this.array[n + 1] & 0xFF) << 8 | (this.array[n + 2] & 0xFF);
    }
    
    @Override
    public int getInt(final int n) {
        this.ensureAccessible();
        return this._getInt(n);
    }
    
    @Override
    protected int _getInt(final int n) {
        return (this.array[n] & 0xFF) << 24 | (this.array[n + 1] & 0xFF) << 16 | (this.array[n + 2] & 0xFF) << 8 | (this.array[n + 3] & 0xFF);
    }
    
    @Override
    public long getLong(final int n) {
        this.ensureAccessible();
        return this._getLong(n);
    }
    
    @Override
    protected long _getLong(final int n) {
        return ((long)this.array[n] & 0xFFL) << 56 | ((long)this.array[n + 1] & 0xFFL) << 48 | ((long)this.array[n + 2] & 0xFFL) << 40 | ((long)this.array[n + 3] & 0xFFL) << 32 | ((long)this.array[n + 4] & 0xFFL) << 24 | ((long)this.array[n + 5] & 0xFFL) << 16 | ((long)this.array[n + 6] & 0xFFL) << 8 | ((long)this.array[n + 7] & 0xFFL);
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        this.ensureAccessible();
        this._setByte(n, n2);
        return this;
    }
    
    @Override
    protected void _setByte(final int n, final int n2) {
        this.array[n] = (byte)n2;
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        this.ensureAccessible();
        this._setShort(n, n2);
        return this;
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        this.array[n] = (byte)(n2 >>> 8);
        this.array[n + 1] = (byte)n2;
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        this.ensureAccessible();
        this._setMedium(n, n2);
        return this;
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        this.array[n] = (byte)(n2 >>> 16);
        this.array[n + 1] = (byte)(n2 >>> 8);
        this.array[n + 2] = (byte)n2;
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        this.ensureAccessible();
        this._setInt(n, n2);
        return this;
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        this.array[n] = (byte)(n2 >>> 24);
        this.array[n + 1] = (byte)(n2 >>> 16);
        this.array[n + 2] = (byte)(n2 >>> 8);
        this.array[n + 3] = (byte)n2;
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        this.ensureAccessible();
        this._setLong(n, n2);
        return this;
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        this.array[n] = (byte)(n2 >>> 56);
        this.array[n + 1] = (byte)(n2 >>> 48);
        this.array[n + 2] = (byte)(n2 >>> 40);
        this.array[n + 3] = (byte)(n2 >>> 32);
        this.array[n + 4] = (byte)(n2 >>> 24);
        this.array[n + 5] = (byte)(n2 >>> 16);
        this.array[n + 6] = (byte)(n2 >>> 8);
        this.array[n + 7] = (byte)n2;
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.checkIndex(n, n2);
        final byte[] array = new byte[n2];
        System.arraycopy(this.array, n, array, 0, n2);
        return new UnpooledHeapByteBuf(this.alloc(), array, this.maxCapacity());
    }
    
    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf == null) {
            tmpNioBuf = (this.tmpNioBuf = ByteBuffer.wrap(this.array));
        }
        return tmpNioBuf;
    }
    
    @Override
    protected void deallocate() {
        this.array = null;
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
}
