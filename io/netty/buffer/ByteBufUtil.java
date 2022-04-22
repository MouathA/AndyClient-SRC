package io.netty.buffer;

import java.nio.*;
import java.nio.charset.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;
import java.util.*;
import io.netty.util.*;

public final class ByteBufUtil
{
    private static final InternalLogger logger;
    private static final char[] HEXDUMP_TABLE;
    static final ByteBufAllocator DEFAULT_ALLOCATOR;
    private static final int THREAD_LOCAL_BUFFER_SIZE;
    
    public static String hexDump(final ByteBuf byteBuf) {
        return hexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }
    
    public static String hexDump(final ByteBuf byteBuf, final int n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("length: " + n2);
        }
        if (n2 == 0) {
            return "";
        }
        final int n3 = n + n2;
        final char[] array = new char[n2 << 1];
        int n4 = 0;
        for (int i = n; i < n3; ++i, n4 += 2) {
            System.arraycopy(ByteBufUtil.HEXDUMP_TABLE, byteBuf.getUnsignedByte(i) << 1, array, 0, 2);
        }
        return new String(array);
    }
    
    public static int hashCode(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        final int n = readableBytes >>> 2;
        final int n2 = readableBytes & 0x3;
        int readerIndex = byteBuf.readerIndex();
        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
            for (int i = n; i > 0; --i) {
                final int n3 = 31 + byteBuf.getInt(readerIndex);
                readerIndex += 4;
            }
        }
        else {
            for (int j = n; j > 0; --j) {
                final int n4 = 31 + swapInt(byteBuf.getInt(readerIndex));
                readerIndex += 4;
            }
        }
        for (int k = n2; k > 0; --k) {
            final int n5 = 31 + byteBuf.getByte(readerIndex++);
        }
        if (!true) {}
        return 1;
    }
    
    public static boolean equals(final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes != byteBuf2.readableBytes()) {
            return false;
        }
        final int n = readableBytes >>> 3;
        final int n2 = readableBytes & 0x7;
        int readerIndex = byteBuf.readerIndex();
        int readerIndex2 = byteBuf2.readerIndex();
        if (byteBuf.order() == byteBuf2.order()) {
            for (int i = n; i > 0; --i) {
                if (byteBuf.getLong(readerIndex) != byteBuf2.getLong(readerIndex2)) {
                    return false;
                }
                readerIndex += 8;
                readerIndex2 += 8;
            }
        }
        else {
            for (int j = n; j > 0; --j) {
                if (byteBuf.getLong(readerIndex) != swapLong(byteBuf2.getLong(readerIndex2))) {
                    return false;
                }
                readerIndex += 8;
                readerIndex2 += 8;
            }
        }
        for (int k = n2; k > 0; --k) {
            if (byteBuf.getByte(readerIndex) != byteBuf2.getByte(readerIndex2)) {
                return false;
            }
            ++readerIndex;
            ++readerIndex2;
        }
        return true;
    }
    
    public static int compare(final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        final int readableBytes = byteBuf.readableBytes();
        final int readableBytes2 = byteBuf2.readableBytes();
        final int min = Math.min(readableBytes, readableBytes2);
        final int n = min >>> 2;
        final int n2 = min & 0x3;
        int readerIndex = byteBuf.readerIndex();
        int readerIndex2 = byteBuf2.readerIndex();
        if (byteBuf.order() == byteBuf2.order()) {
            for (int i = n; i > 0; --i) {
                final long unsignedInt = byteBuf.getUnsignedInt(readerIndex);
                final long unsignedInt2 = byteBuf2.getUnsignedInt(readerIndex2);
                if (unsignedInt > unsignedInt2) {
                    return 1;
                }
                if (unsignedInt < unsignedInt2) {
                    return -1;
                }
                readerIndex += 4;
                readerIndex2 += 4;
            }
        }
        else {
            for (int j = n; j > 0; --j) {
                final long unsignedInt3 = byteBuf.getUnsignedInt(readerIndex);
                final long n3 = (long)swapInt(byteBuf2.getInt(readerIndex2)) & 0xFFFFFFFFL;
                if (unsignedInt3 > n3) {
                    return 1;
                }
                if (unsignedInt3 < n3) {
                    return -1;
                }
                readerIndex += 4;
                readerIndex2 += 4;
            }
        }
        for (int k = n2; k > 0; --k) {
            final short unsignedByte = byteBuf.getUnsignedByte(readerIndex);
            final short unsignedByte2 = byteBuf2.getUnsignedByte(readerIndex2);
            if (unsignedByte > unsignedByte2) {
                return 1;
            }
            if (unsignedByte < unsignedByte2) {
                return -1;
            }
            ++readerIndex;
            ++readerIndex2;
        }
        return readableBytes - readableBytes2;
    }
    
    public static int indexOf(final ByteBuf byteBuf, final int n, final int n2, final byte b) {
        if (n <= n2) {
            return firstIndexOf(byteBuf, n, n2, b);
        }
        return lastIndexOf(byteBuf, n, n2, b);
    }
    
    public static short swapShort(final short n) {
        return Short.reverseBytes(n);
    }
    
    public static int swapMedium(final int n) {
        int n2 = (n << 16 & 0xFF0000) | (n & 0xFF00) | (n >>> 16 & 0xFF);
        if ((n2 & 0x800000) != 0x0) {
            n2 |= 0xFF000000;
        }
        return n2;
    }
    
    public static int swapInt(final int n) {
        return Integer.reverseBytes(n);
    }
    
    public static long swapLong(final long n) {
        return Long.reverseBytes(n);
    }
    
    public static ByteBuf readBytes(final ByteBufAllocator byteBufAllocator, final ByteBuf byteBuf, final int n) {
        final ByteBuf buffer = byteBufAllocator.buffer(n);
        byteBuf.readBytes(buffer);
        final ByteBuf byteBuf2 = buffer;
        if (false) {
            buffer.release();
        }
        return byteBuf2;
    }
    
    private static int firstIndexOf(final ByteBuf byteBuf, int max, final int n, final byte b) {
        max = Math.max(max, 0);
        if (max >= n || byteBuf.capacity() == 0) {
            return -1;
        }
        for (int i = max; i < n; ++i) {
            if (byteBuf.getByte(i) == b) {
                return i;
            }
        }
        return -1;
    }
    
    private static int lastIndexOf(final ByteBuf byteBuf, int min, final int n, final byte b) {
        min = Math.min(min, byteBuf.capacity());
        if (min < 0 || byteBuf.capacity() == 0) {
            return -1;
        }
        for (int i = min - 1; i >= n; --i) {
            if (byteBuf.getByte(i) == b) {
                return i;
            }
        }
        return -1;
    }
    
    public static ByteBuf encodeString(final ByteBufAllocator byteBufAllocator, final CharBuffer charBuffer, final Charset charset) {
        return encodeString0(byteBufAllocator, false, charBuffer, charset);
    }
    
    static ByteBuf encodeString0(final ByteBufAllocator byteBufAllocator, final boolean b, final CharBuffer charBuffer, final Charset charset) {
        final CharsetEncoder encoder = CharsetUtil.getEncoder(charset);
        final int n = (int)(charBuffer.remaining() * (double)encoder.maxBytesPerChar());
        ByteBuf byteBuf;
        if (b) {
            byteBuf = byteBufAllocator.heapBuffer(n);
        }
        else {
            byteBuf = byteBufAllocator.buffer(n);
        }
        final ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(0, n);
        final int position = internalNioBuffer.position();
        final CoderResult encode = encoder.encode(charBuffer, internalNioBuffer, true);
        if (!encode.isUnderflow()) {
            encode.throwException();
        }
        final CoderResult flush = encoder.flush(internalNioBuffer);
        if (!flush.isUnderflow()) {
            flush.throwException();
        }
        byteBuf.writerIndex(byteBuf.writerIndex() + internalNioBuffer.position() - position);
        final ByteBuf byteBuf2 = byteBuf;
        if (false) {
            byteBuf.release();
        }
        return byteBuf2;
    }
    
    static String decodeString(final ByteBuffer byteBuffer, final Charset charset) {
        final CharsetDecoder decoder = CharsetUtil.getDecoder(charset);
        final CharBuffer allocate = CharBuffer.allocate((int)(byteBuffer.remaining() * (double)decoder.maxCharsPerByte()));
        final CoderResult decode = decoder.decode(byteBuffer, allocate, true);
        if (!decode.isUnderflow()) {
            decode.throwException();
        }
        final CoderResult flush = decoder.flush(allocate);
        if (!flush.isUnderflow()) {
            flush.throwException();
        }
        return allocate.flip().toString();
    }
    
    public static ByteBuf threadLocalDirectBuffer() {
        if (ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE <= 0) {
            return null;
        }
        if (PlatformDependent.hasUnsafe()) {
            return ThreadLocalUnsafeDirectByteBuf.newInstance();
        }
        return ThreadLocalDirectByteBuf.newInstance();
    }
    
    private ByteBufUtil() {
    }
    
    static int access$100() {
        return ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);
        HEXDUMP_TABLE = new char[1024];
        final char[] charArray = "0123456789abcdef".toCharArray();
        while (0 < 256) {
            ByteBufUtil.HEXDUMP_TABLE[0] = charArray[0];
            ByteBufUtil.HEXDUMP_TABLE[1] = charArray[0];
            int n = 0;
            ++n;
        }
        final String trim = SystemPropertyUtil.get("io.netty.allocator.type", "unpooled").toLowerCase(Locale.US).trim();
        AbstractByteBufAllocator default_ALLOCATOR;
        if ("unpooled".equals(trim)) {
            default_ALLOCATOR = UnpooledByteBufAllocator.DEFAULT;
            ByteBufUtil.logger.debug("-Dio.netty.allocator.type: {}", trim);
        }
        else if ("pooled".equals(trim)) {
            default_ALLOCATOR = PooledByteBufAllocator.DEFAULT;
            ByteBufUtil.logger.debug("-Dio.netty.allocator.type: {}", trim);
        }
        else {
            default_ALLOCATOR = UnpooledByteBufAllocator.DEFAULT;
            ByteBufUtil.logger.debug("-Dio.netty.allocator.type: unpooled (unknown: {})", trim);
        }
        DEFAULT_ALLOCATOR = default_ALLOCATOR;
        THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536);
        ByteBufUtil.logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", (Object)ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE);
    }
    
    static final class ThreadLocalDirectByteBuf extends UnpooledDirectByteBuf
    {
        private static final Recycler RECYCLER;
        private final Recycler.Handle handle;
        
        static ThreadLocalDirectByteBuf newInstance() {
            final ThreadLocalDirectByteBuf threadLocalDirectByteBuf = (ThreadLocalDirectByteBuf)ThreadLocalDirectByteBuf.RECYCLER.get();
            threadLocalDirectByteBuf.setRefCnt(1);
            return threadLocalDirectByteBuf;
        }
        
        private ThreadLocalDirectByteBuf(final Recycler.Handle handle) {
            super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle;
        }
        
        @Override
        protected void deallocate() {
            if (this.capacity() > ByteBufUtil.access$100()) {
                super.deallocate();
            }
            else {
                this.clear();
                ThreadLocalDirectByteBuf.RECYCLER.recycle(this, this.handle);
            }
        }
        
        ThreadLocalDirectByteBuf(final Recycler.Handle handle, final ByteBufUtil$1 object) {
            this(handle);
        }
        
        static {
            RECYCLER = new Recycler() {
                @Override
                protected ThreadLocalDirectByteBuf newObject(final Handle handle) {
                    return new ThreadLocalDirectByteBuf(handle, null);
                }
                
                @Override
                protected Object newObject(final Handle handle) {
                    return this.newObject(handle);
                }
            };
        }
    }
    
    static final class ThreadLocalUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf
    {
        private static final Recycler RECYCLER;
        private final Recycler.Handle handle;
        
        static ThreadLocalUnsafeDirectByteBuf newInstance() {
            final ThreadLocalUnsafeDirectByteBuf threadLocalUnsafeDirectByteBuf = (ThreadLocalUnsafeDirectByteBuf)ThreadLocalUnsafeDirectByteBuf.RECYCLER.get();
            threadLocalUnsafeDirectByteBuf.setRefCnt(1);
            return threadLocalUnsafeDirectByteBuf;
        }
        
        private ThreadLocalUnsafeDirectByteBuf(final Recycler.Handle handle) {
            super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle;
        }
        
        @Override
        protected void deallocate() {
            if (this.capacity() > ByteBufUtil.access$100()) {
                super.deallocate();
            }
            else {
                this.clear();
                ThreadLocalUnsafeDirectByteBuf.RECYCLER.recycle(this, this.handle);
            }
        }
        
        ThreadLocalUnsafeDirectByteBuf(final Recycler.Handle handle, final ByteBufUtil$1 object) {
            this(handle);
        }
        
        static {
            RECYCLER = new Recycler() {
                @Override
                protected ThreadLocalUnsafeDirectByteBuf newObject(final Handle handle) {
                    return new ThreadLocalUnsafeDirectByteBuf(handle, null);
                }
                
                @Override
                protected Object newObject(final Handle handle) {
                    return this.newObject(handle);
                }
            };
        }
    }
}
