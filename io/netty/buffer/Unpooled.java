package io.netty.buffer;

import io.netty.util.internal.*;
import java.util.*;
import java.nio.charset.*;
import java.nio.*;

public final class Unpooled
{
    private static final ByteBufAllocator ALLOC;
    public static final ByteOrder BIG_ENDIAN;
    public static final ByteOrder LITTLE_ENDIAN;
    public static final ByteBuf EMPTY_BUFFER;
    
    public static ByteBuf buffer() {
        return Unpooled.ALLOC.heapBuffer();
    }
    
    public static ByteBuf directBuffer() {
        return Unpooled.ALLOC.directBuffer();
    }
    
    public static ByteBuf buffer(final int n) {
        return Unpooled.ALLOC.heapBuffer(n);
    }
    
    public static ByteBuf directBuffer(final int n) {
        return Unpooled.ALLOC.directBuffer(n);
    }
    
    public static ByteBuf buffer(final int n, final int n2) {
        return Unpooled.ALLOC.heapBuffer(n, n2);
    }
    
    public static ByteBuf directBuffer(final int n, final int n2) {
        return Unpooled.ALLOC.directBuffer(n, n2);
    }
    
    public static ByteBuf wrappedBuffer(final byte[] array) {
        if (array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return new UnpooledHeapByteBuf(Unpooled.ALLOC, array, array.length);
    }
    
    public static ByteBuf wrappedBuffer(final byte[] array, final int n, final int n2) {
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (n == 0 && n2 == array.length) {
            return wrappedBuffer(array);
        }
        return wrappedBuffer(array).slice(n, n2);
    }
    
    public static ByteBuf wrappedBuffer(final ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (byteBuffer.hasArray()) {
            return wrappedBuffer(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()).order(byteBuffer.order());
        }
        if (PlatformDependent.hasUnsafe()) {
            if (!byteBuffer.isReadOnly()) {
                return new UnpooledUnsafeDirectByteBuf(Unpooled.ALLOC, byteBuffer, byteBuffer.remaining());
            }
            if (byteBuffer.isDirect()) {
                return new ReadOnlyUnsafeDirectByteBuf(Unpooled.ALLOC, byteBuffer);
            }
            return new ReadOnlyByteBufferBuf(Unpooled.ALLOC, byteBuffer);
        }
        else {
            if (byteBuffer.isReadOnly()) {
                return new ReadOnlyByteBufferBuf(Unpooled.ALLOC, byteBuffer);
            }
            return new UnpooledDirectByteBuf(Unpooled.ALLOC, byteBuffer, byteBuffer.remaining());
        }
    }
    
    public static ByteBuf wrappedBuffer(final ByteBuf byteBuf) {
        if (byteBuf.isReadable()) {
            return byteBuf.slice();
        }
        return Unpooled.EMPTY_BUFFER;
    }
    
    public static ByteBuf wrappedBuffer(final byte[]... array) {
        return wrappedBuffer(16, array);
    }
    
    public static ByteBuf wrappedBuffer(final ByteBuf... array) {
        return wrappedBuffer(16, array);
    }
    
    public static ByteBuf wrappedBuffer(final ByteBuffer... array) {
        return wrappedBuffer(16, array);
    }
    
    public static ByteBuf wrappedBuffer(final int n, final byte[]... array) {
        switch (array.length) {
            case 0: {
                break;
            }
            case 1: {
                if (array[0].length != 0) {
                    return wrappedBuffer(array[0]);
                }
                break;
            }
            default: {
                final ArrayList<ByteBuf> list = new ArrayList<ByteBuf>(array.length);
                while (0 < array.length) {
                    final byte[] array2 = array[0];
                    if (array2 == null) {
                        break;
                    }
                    if (array2.length > 0) {
                        list.add(wrappedBuffer(array2));
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (!list.isEmpty()) {
                    return new CompositeByteBuf(Unpooled.ALLOC, false, n, list);
                }
                break;
            }
        }
        return Unpooled.EMPTY_BUFFER;
    }
    
    public static ByteBuf wrappedBuffer(final int n, final ByteBuf... array) {
        switch (array.length) {
            case 0: {
                break;
            }
            case 1: {
                if (array[0].isReadable()) {
                    return wrappedBuffer(array[0].order(Unpooled.BIG_ENDIAN));
                }
                break;
            }
            default: {
                while (0 < array.length) {
                    if (array[0].isReadable()) {
                        return new CompositeByteBuf(Unpooled.ALLOC, false, n, array);
                    }
                    int n2 = 0;
                    ++n2;
                }
                break;
            }
        }
        return Unpooled.EMPTY_BUFFER;
    }
    
    public static ByteBuf wrappedBuffer(final int n, final ByteBuffer... array) {
        switch (array.length) {
            case 0: {
                break;
            }
            case 1: {
                if (array[0].hasRemaining()) {
                    return wrappedBuffer(array[0].order(Unpooled.BIG_ENDIAN));
                }
                break;
            }
            default: {
                final ArrayList<ByteBuf> list = new ArrayList<ByteBuf>(array.length);
                while (0 < array.length) {
                    final ByteBuffer byteBuffer = array[0];
                    if (byteBuffer == null) {
                        break;
                    }
                    if (byteBuffer.remaining() > 0) {
                        list.add(wrappedBuffer(byteBuffer.order(Unpooled.BIG_ENDIAN)));
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (!list.isEmpty()) {
                    return new CompositeByteBuf(Unpooled.ALLOC, false, n, list);
                }
                break;
            }
        }
        return Unpooled.EMPTY_BUFFER;
    }
    
    public static CompositeByteBuf compositeBuffer() {
        return compositeBuffer(16);
    }
    
    public static CompositeByteBuf compositeBuffer(final int n) {
        return new CompositeByteBuf(Unpooled.ALLOC, false, n);
    }
    
    public static ByteBuf copiedBuffer(final byte[] array) {
        if (array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return wrappedBuffer(array.clone());
    }
    
    public static ByteBuf copiedBuffer(final byte[] array, final int n, final int n2) {
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final byte[] array2 = new byte[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return wrappedBuffer(array2);
    }
    
    public static ByteBuf copiedBuffer(final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        if (remaining == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final byte[] array = new byte[remaining];
        final int position = byteBuffer.position();
        byteBuffer.get(array);
        byteBuffer.position(position);
        return wrappedBuffer(array).order(byteBuffer.order());
    }
    
    public static ByteBuf copiedBuffer(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes > 0) {
            final ByteBuf buffer = buffer(readableBytes);
            buffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            return buffer;
        }
        return Unpooled.EMPTY_BUFFER;
    }
    
    public static ByteBuf copiedBuffer(final byte[]... array) {
        switch (array.length) {
            case 0: {
                return Unpooled.EMPTY_BUFFER;
            }
            case 1: {
                if (array[0].length == 0) {
                    return Unpooled.EMPTY_BUFFER;
                }
                return copiedBuffer(array[0]);
            }
            default: {
                final int length = array.length;
                return Unpooled.EMPTY_BUFFER;
            }
        }
    }
    
    public static ByteBuf copiedBuffer(final ByteBuf... array) {
        switch (array.length) {
            case 0: {
                return Unpooled.EMPTY_BUFFER;
            }
            case 1: {
                return copiedBuffer(array[0]);
            }
            default: {
                final int length = array.length;
                return Unpooled.EMPTY_BUFFER;
            }
        }
    }
    
    public static ByteBuf copiedBuffer(final ByteBuffer... array) {
        switch (array.length) {
            case 0: {
                return Unpooled.EMPTY_BUFFER;
            }
            case 1: {
                return copiedBuffer(array[0]);
            }
            default: {
                final int length = array.length;
                return Unpooled.EMPTY_BUFFER;
            }
        }
    }
    
    public static ByteBuf copiedBuffer(final CharSequence charSequence, final Charset charset) {
        if (charSequence == null) {
            throw new NullPointerException("string");
        }
        if (charSequence instanceof CharBuffer) {
            return copiedBuffer((CharBuffer)charSequence, charset);
        }
        return copiedBuffer(CharBuffer.wrap(charSequence), charset);
    }
    
    public static ByteBuf copiedBuffer(final CharSequence charSequence, final int n, final int n2, final Charset charset) {
        if (charSequence == null) {
            throw new NullPointerException("string");
        }
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (!(charSequence instanceof CharBuffer)) {
            return copiedBuffer(CharBuffer.wrap(charSequence, n, n + n2), charset);
        }
        final CharBuffer charBuffer = (CharBuffer)charSequence;
        if (charBuffer.hasArray()) {
            return copiedBuffer(charBuffer.array(), charBuffer.arrayOffset() + charBuffer.position() + n, n2, charset);
        }
        final CharBuffer slice = charBuffer.slice();
        slice.limit(n2);
        slice.position(n);
        return copiedBuffer(slice, charset);
    }
    
    public static ByteBuf copiedBuffer(final char[] array, final Charset charset) {
        if (array == null) {
            throw new NullPointerException("array");
        }
        return copiedBuffer(array, 0, array.length, charset);
    }
    
    public static ByteBuf copiedBuffer(final char[] array, final int n, final int n2, final Charset charset) {
        if (array == null) {
            throw new NullPointerException("array");
        }
        if (n2 == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return copiedBuffer(CharBuffer.wrap(array, n, n2), charset);
    }
    
    private static ByteBuf copiedBuffer(final CharBuffer charBuffer, final Charset charset) {
        return ByteBufUtil.encodeString0(Unpooled.ALLOC, true, charBuffer, charset);
    }
    
    public static ByteBuf unmodifiableBuffer(final ByteBuf byteBuf) {
        if (byteBuf.order() == Unpooled.BIG_ENDIAN) {
            return new ReadOnlyByteBuf(byteBuf);
        }
        return new ReadOnlyByteBuf(byteBuf.order(Unpooled.BIG_ENDIAN)).order(Unpooled.LITTLE_ENDIAN);
    }
    
    public static ByteBuf copyInt(final int n) {
        final ByteBuf buffer = buffer(4);
        buffer.writeInt(n);
        return buffer;
    }
    
    public static ByteBuf copyInt(final int... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 4);
        while (0 < array.length) {
            buffer.writeInt(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyShort(final int n) {
        final ByteBuf buffer = buffer(2);
        buffer.writeShort(n);
        return buffer;
    }
    
    public static ByteBuf copyShort(final short... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 2);
        while (0 < array.length) {
            buffer.writeShort(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyShort(final int... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 2);
        while (0 < array.length) {
            buffer.writeShort(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyMedium(final int n) {
        final ByteBuf buffer = buffer(3);
        buffer.writeMedium(n);
        return buffer;
    }
    
    public static ByteBuf copyMedium(final int... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 3);
        while (0 < array.length) {
            buffer.writeMedium(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyLong(final long n) {
        final ByteBuf buffer = buffer(8);
        buffer.writeLong(n);
        return buffer;
    }
    
    public static ByteBuf copyLong(final long... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 8);
        while (0 < array.length) {
            buffer.writeLong(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyBoolean(final boolean b) {
        final ByteBuf buffer = buffer(1);
        buffer.writeBoolean(b);
        return buffer;
    }
    
    public static ByteBuf copyBoolean(final boolean... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length);
        while (0 < array.length) {
            buffer.writeBoolean(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyFloat(final float n) {
        final ByteBuf buffer = buffer(4);
        buffer.writeFloat(n);
        return buffer;
    }
    
    public static ByteBuf copyFloat(final float... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 4);
        while (0 < array.length) {
            buffer.writeFloat(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf copyDouble(final double n) {
        final ByteBuf buffer = buffer(8);
        buffer.writeDouble(n);
        return buffer;
    }
    
    public static ByteBuf copyDouble(final double... array) {
        if (array == null || array.length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf buffer = buffer(array.length * 8);
        while (0 < array.length) {
            buffer.writeDouble(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
    
    public static ByteBuf unreleasableBuffer(final ByteBuf byteBuf) {
        return new UnreleasableByteBuf(byteBuf);
    }
    
    private Unpooled() {
    }
    
    static {
        ALLOC = UnpooledByteBufAllocator.DEFAULT;
        BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
        LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        EMPTY_BUFFER = Unpooled.ALLOC.buffer(0, 0);
    }
}
