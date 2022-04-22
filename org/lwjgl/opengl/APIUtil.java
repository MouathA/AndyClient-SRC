package org.lwjgl.opengl;

import org.lwjgl.*;
import java.nio.*;

final class APIUtil
{
    private static final int INITIAL_BUFFER_SIZE = 256;
    private static final int INITIAL_LENGTHS_SIZE = 4;
    private static final int BUFFERS_SIZE = 32;
    private char[] array;
    private ByteBuffer buffer;
    private IntBuffer lengths;
    private final IntBuffer ints;
    private final LongBuffer longs;
    private final FloatBuffer floats;
    private final DoubleBuffer doubles;
    
    APIUtil() {
        this.array = new char[256];
        this.buffer = BufferUtils.createByteBuffer(256);
        this.lengths = BufferUtils.createIntBuffer(4);
        this.ints = BufferUtils.createIntBuffer(32);
        this.longs = BufferUtils.createLongBuffer(32);
        this.floats = BufferUtils.createFloatBuffer(32);
        this.doubles = BufferUtils.createDoubleBuffer(32);
    }
    
    private static char[] getArray(final ContextCapabilities contextCapabilities, final int n) {
        char[] array = contextCapabilities.util.array;
        if (array.length < n) {
            for (int i = array.length << 1; i < n; i <<= 1) {}
            array = new char[n];
            contextCapabilities.util.array = array;
        }
        return array;
    }
    
    static ByteBuffer getBufferByte(final ContextCapabilities contextCapabilities, final int n) {
        ByteBuffer buffer = contextCapabilities.util.buffer;
        if (buffer.capacity() < n) {
            for (int i = buffer.capacity() << 1; i < n; i <<= 1) {}
            buffer = BufferUtils.createByteBuffer(n);
            contextCapabilities.util.buffer = buffer;
        }
        else {
            buffer.clear();
        }
        return buffer;
    }
    
    private static ByteBuffer getBufferByteOffset(final ContextCapabilities contextCapabilities, final int n) {
        ByteBuffer buffer = contextCapabilities.util.buffer;
        if (buffer.capacity() < n) {
            for (int i = buffer.capacity() << 1; i < n; i <<= 1) {}
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(n);
            byteBuffer.put(buffer);
            buffer = (contextCapabilities.util.buffer = byteBuffer);
        }
        else {
            buffer.position(buffer.limit());
            buffer.limit(buffer.capacity());
        }
        return buffer;
    }
    
    static IntBuffer getBufferInt(final ContextCapabilities contextCapabilities) {
        return contextCapabilities.util.ints;
    }
    
    static LongBuffer getBufferLong(final ContextCapabilities contextCapabilities) {
        return contextCapabilities.util.longs;
    }
    
    static FloatBuffer getBufferFloat(final ContextCapabilities contextCapabilities) {
        return contextCapabilities.util.floats;
    }
    
    static DoubleBuffer getBufferDouble(final ContextCapabilities contextCapabilities) {
        return contextCapabilities.util.doubles;
    }
    
    static IntBuffer getLengths(final ContextCapabilities contextCapabilities) {
        return getLengths(contextCapabilities, 1);
    }
    
    static IntBuffer getLengths(final ContextCapabilities contextCapabilities, final int n) {
        IntBuffer lengths = contextCapabilities.util.lengths;
        if (lengths.capacity() < n) {
            for (int i = lengths.capacity(); i < n; i <<= 1) {}
            lengths = BufferUtils.createIntBuffer(n);
            contextCapabilities.util.lengths = lengths;
        }
        else {
            lengths.clear();
        }
        return lengths;
    }
    
    private static ByteBuffer encode(final ByteBuffer byteBuffer, final CharSequence charSequence) {
        while (0 < charSequence.length()) {
            final char char1 = charSequence.charAt(0);
            if (LWJGLUtil.DEBUG && '\u0080' <= char1) {
                byteBuffer.put((byte)26);
            }
            else {
                byteBuffer.put((byte)char1);
            }
            int n = 0;
            ++n;
        }
        return byteBuffer;
    }
    
    static String getString(final ContextCapabilities contextCapabilities, final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        final char[] array = getArray(contextCapabilities, remaining);
        for (int i = byteBuffer.position(); i < byteBuffer.limit(); ++i) {
            array[i - byteBuffer.position()] = (char)byteBuffer.get(i);
        }
        return new String(array, 0, remaining);
    }
    
    static long getBuffer(final ContextCapabilities contextCapabilities, final CharSequence charSequence) {
        final ByteBuffer encode = encode(getBufferByte(contextCapabilities, charSequence.length()), charSequence);
        encode.flip();
        return MemoryUtil.getAddress0(encode);
    }
    
    static long getBuffer(final ContextCapabilities contextCapabilities, final CharSequence charSequence, final int n) {
        final ByteBuffer encode = encode(getBufferByteOffset(contextCapabilities, n + charSequence.length()), charSequence);
        encode.flip();
        return MemoryUtil.getAddress(encode);
    }
    
    static long getBufferNT(final ContextCapabilities contextCapabilities, final CharSequence charSequence) {
        final ByteBuffer encode = encode(getBufferByte(contextCapabilities, charSequence.length() + 1), charSequence);
        encode.put((byte)0);
        encode.flip();
        return MemoryUtil.getAddress0(encode);
    }
    
    static int getTotalLength(final CharSequence[] array) {
        while (0 < array.length) {
            final int n = 0 + array[0].length();
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    static long getBuffer(final ContextCapabilities contextCapabilities, final CharSequence[] array) {
        final ByteBuffer bufferByte = getBufferByte(contextCapabilities, getTotalLength(array));
        while (0 < array.length) {
            encode(bufferByte, array[0]);
            int n = 0;
            ++n;
        }
        bufferByte.flip();
        return MemoryUtil.getAddress0(bufferByte);
    }
    
    static long getBufferNT(final ContextCapabilities contextCapabilities, final CharSequence[] array) {
        final ByteBuffer bufferByte = getBufferByte(contextCapabilities, getTotalLength(array) + array.length);
        while (0 < array.length) {
            encode(bufferByte, array[0]);
            bufferByte.put((byte)0);
            int n = 0;
            ++n;
        }
        bufferByte.flip();
        return MemoryUtil.getAddress0(bufferByte);
    }
    
    static long getLengths(final ContextCapabilities contextCapabilities, final CharSequence[] array) {
        final IntBuffer lengths = getLengths(contextCapabilities, array.length);
        while (0 < array.length) {
            lengths.put(array[0].length());
            int n = 0;
            ++n;
        }
        lengths.flip();
        return MemoryUtil.getAddress0(lengths);
    }
    
    static long getInt(final ContextCapabilities contextCapabilities, final int n) {
        return MemoryUtil.getAddress0(getBufferInt(contextCapabilities).put(0, n));
    }
    
    static long getBufferByte0(final ContextCapabilities contextCapabilities) {
        return MemoryUtil.getAddress0(getBufferByte(contextCapabilities, 0));
    }
}
