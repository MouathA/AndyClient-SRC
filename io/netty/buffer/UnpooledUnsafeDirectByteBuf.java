package io.netty.buffer;

import java.nio.*;
import io.netty.util.internal.*;
import java.io.*;
import java.nio.channels.*;

public class UnpooledUnsafeDirectByteBuf extends AbstractReferenceCountedByteBuf
{
    private static final boolean NATIVE_ORDER;
    private final ByteBufAllocator alloc;
    private long memoryAddress;
    private ByteBuffer buffer;
    private ByteBuffer tmpNioBuf;
    private int capacity;
    private boolean doNotFree;
    
    protected UnpooledUnsafeDirectByteBuf(final ByteBufAllocator alloc, final int n, final int n2) {
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
        this.setByteBuffer(this.allocateDirect(n));
    }
    
    protected UnpooledUnsafeDirectByteBuf(final ByteBufAllocator alloc, final ByteBuffer byteBuffer, final int n) {
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
        this.memoryAddress = PlatformDependent.directBufferAddress(buffer);
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
        return true;
    }
    
    @Override
    public long memoryAddress() {
        return this.memoryAddress;
    }
    
    @Override
    protected byte _getByte(final int n) {
        return PlatformDependent.getByte(this.addr(n));
    }
    
    @Override
    protected short _getShort(final int n) {
        final short short1 = PlatformDependent.getShort(this.addr(n));
        return UnpooledUnsafeDirectByteBuf.NATIVE_ORDER ? short1 : Short.reverseBytes(short1);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        final long addr = this.addr(n);
        return (PlatformDependent.getByte(addr) & 0xFF) << 16 | (PlatformDependent.getByte(addr + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(addr + 2L) & 0xFF);
    }
    
    @Override
    protected int _getInt(final int n) {
        final int int1 = PlatformDependent.getInt(this.addr(n));
        return UnpooledUnsafeDirectByteBuf.NATIVE_ORDER ? int1 : Integer.reverseBytes(int1);
    }
    
    @Override
    protected long _getLong(final int n) {
        final long long1 = PlatformDependent.getLong(this.addr(n));
        return UnpooledUnsafeDirectByteBuf.NATIVE_ORDER ? long1 : Long.reverseBytes(long1);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (byteBuf == null) {
            throw new NullPointerException("dst");
        }
        if (n2 < 0 || n2 > byteBuf.capacity() - n3) {
            throw new IndexOutOfBoundsException("dstIndex: " + n2);
        }
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(n), byteBuf.memoryAddress() + n2, n3);
        }
        else if (byteBuf.hasArray()) {
            PlatformDependent.copyMemory(this.addr(n), byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        }
        else {
            byteBuf.setBytes(n2, this, n, n3);
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (array == null) {
            throw new NullPointerException("dst");
        }
        if (n2 < 0 || n2 > array.length - n3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n2, n3, array.length));
        }
        if (n3 != 0) {
            PlatformDependent.copyMemory(this.addr(n), array, n2, n3);
        }
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
    protected void _setByte(final int n, final int n2) {
        PlatformDependent.putByte(this.addr(n), (byte)n2);
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        PlatformDependent.putShort(this.addr(n), UnpooledUnsafeDirectByteBuf.NATIVE_ORDER ? ((short)n2) : Short.reverseBytes((short)n2));
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        final long addr = this.addr(n);
        PlatformDependent.putByte(addr, (byte)(n2 >>> 16));
        PlatformDependent.putByte(addr + 1L, (byte)(n2 >>> 8));
        PlatformDependent.putByte(addr + 2L, (byte)n2);
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        PlatformDependent.putInt(this.addr(n), UnpooledUnsafeDirectByteBuf.NATIVE_ORDER ? n2 : Integer.reverseBytes(n2));
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        PlatformDependent.putLong(this.addr(n), UnpooledUnsafeDirectByteBuf.NATIVE_ORDER ? n2 : Long.reverseBytes(n2));
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (n2 < 0 || n2 > byteBuf.capacity() - n3) {
            throw new IndexOutOfBoundsException("srcIndex: " + n2);
        }
        if (n3 != 0) {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(byteBuf.memoryAddress() + n2, this.addr(n), n3);
            }
            else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(byteBuf.array(), byteBuf.arrayOffset() + n2, this.addr(n), n3);
            }
            else {
                byteBuf.getBytes(n2, this, n, n3);
            }
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        this.checkIndex(n, n3);
        if (n3 != 0) {
            PlatformDependent.copyMemory(array, n2, this.addr(n), n3);
        }
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
        this.ensureAccessible();
        if (n2 != 0) {
            final byte[] array = new byte[n2];
            PlatformDependent.copyMemory(this.addr(n), array, 0, n2);
            outputStream.write(array);
        }
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
        this.checkIndex(n, n2);
        final byte[] array = new byte[n2];
        final int read = inputStream.read(array);
        if (read > 0) {
            PlatformDependent.copyMemory(array, 0, this.addr(n), read);
        }
        return read;
    }
    
    @Override
    public int setBytes(final int n, final ScatteringByteChannel scatteringByteChannel, final int n2) throws IOException {
        this.ensureAccessible();
        final ByteBuffer internalNioBuffer = this.internalNioBuffer();
        internalNioBuffer.clear().position(n).limit(n + n2);
        return scatteringByteChannel.read(internalNioBuffer);
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
        this.checkIndex(n, n2);
        final ByteBuf directBuffer = this.alloc().directBuffer(n2, this.maxCapacity());
        if (n2 != 0) {
            if (directBuffer.hasMemoryAddress()) {
                PlatformDependent.copyMemory(this.addr(n), directBuffer.memoryAddress(), n2);
                directBuffer.setIndex(0, n2);
            }
            else {
                directBuffer.writeBytes(this, n, n2);
            }
        }
        return directBuffer;
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
    
    long addr(final int n) {
        return this.memoryAddress + n;
    }
    
    @Override
    protected SwappedByteBuf newSwappedByteBuf() {
        return new UnsafeDirectSwappedByteBuf(this);
    }
    
    static {
        NATIVE_ORDER = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    }
}
