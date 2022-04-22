package org.lwjgl;

import java.nio.*;
import java.nio.charset.*;
import java.lang.reflect.*;

public final class MemoryUtil
{
    private static final Charset ascii;
    private static final Charset utf8;
    private static final Charset utf16;
    private static final Accessor memUtil;
    
    private MemoryUtil() {
    }
    
    public static long getAddress0(final Buffer buffer) {
        return MemoryUtil.memUtil.getAddress(buffer);
    }
    
    public static long getAddress0Safe(final Buffer buffer) {
        return (buffer == null) ? 0L : MemoryUtil.memUtil.getAddress(buffer);
    }
    
    public static long getAddress0(final PointerBuffer pointerBuffer) {
        return MemoryUtil.memUtil.getAddress(pointerBuffer.getBuffer());
    }
    
    public static long getAddress0Safe(final PointerBuffer pointerBuffer) {
        return (pointerBuffer == null) ? 0L : MemoryUtil.memUtil.getAddress(pointerBuffer.getBuffer());
    }
    
    public static long getAddress(final ByteBuffer byteBuffer) {
        return getAddress(byteBuffer, byteBuffer.position());
    }
    
    public static long getAddress(final ByteBuffer byteBuffer, final int n) {
        return getAddress0(byteBuffer) + n;
    }
    
    public static long getAddress(final ShortBuffer shortBuffer) {
        return getAddress(shortBuffer, shortBuffer.position());
    }
    
    public static long getAddress(final ShortBuffer shortBuffer, final int n) {
        return getAddress0(shortBuffer) + (n << 1);
    }
    
    public static long getAddress(final CharBuffer charBuffer) {
        return getAddress(charBuffer, charBuffer.position());
    }
    
    public static long getAddress(final CharBuffer charBuffer, final int n) {
        return getAddress0(charBuffer) + (n << 1);
    }
    
    public static long getAddress(final IntBuffer intBuffer) {
        return getAddress(intBuffer, intBuffer.position());
    }
    
    public static long getAddress(final IntBuffer intBuffer, final int n) {
        return getAddress0(intBuffer) + (n << 2);
    }
    
    public static long getAddress(final FloatBuffer floatBuffer) {
        return getAddress(floatBuffer, floatBuffer.position());
    }
    
    public static long getAddress(final FloatBuffer floatBuffer, final int n) {
        return getAddress0(floatBuffer) + (n << 2);
    }
    
    public static long getAddress(final LongBuffer longBuffer) {
        return getAddress(longBuffer, longBuffer.position());
    }
    
    public static long getAddress(final LongBuffer longBuffer, final int n) {
        return getAddress0(longBuffer) + (n << 3);
    }
    
    public static long getAddress(final DoubleBuffer doubleBuffer) {
        return getAddress(doubleBuffer, doubleBuffer.position());
    }
    
    public static long getAddress(final DoubleBuffer doubleBuffer, final int n) {
        return getAddress0(doubleBuffer) + (n << 3);
    }
    
    public static long getAddress(final PointerBuffer pointerBuffer) {
        return getAddress(pointerBuffer, pointerBuffer.position());
    }
    
    public static long getAddress(final PointerBuffer pointerBuffer, final int n) {
        return getAddress0(pointerBuffer) + n * PointerBuffer.getPointerSize();
    }
    
    public static long getAddressSafe(final ByteBuffer byteBuffer) {
        return (byteBuffer == null) ? 0L : getAddress(byteBuffer);
    }
    
    public static long getAddressSafe(final ByteBuffer byteBuffer, final int n) {
        return (byteBuffer == null) ? 0L : getAddress(byteBuffer, n);
    }
    
    public static long getAddressSafe(final ShortBuffer shortBuffer) {
        return (shortBuffer == null) ? 0L : getAddress(shortBuffer);
    }
    
    public static long getAddressSafe(final ShortBuffer shortBuffer, final int n) {
        return (shortBuffer == null) ? 0L : getAddress(shortBuffer, n);
    }
    
    public static long getAddressSafe(final CharBuffer charBuffer) {
        return (charBuffer == null) ? 0L : getAddress(charBuffer);
    }
    
    public static long getAddressSafe(final CharBuffer charBuffer, final int n) {
        return (charBuffer == null) ? 0L : getAddress(charBuffer, n);
    }
    
    public static long getAddressSafe(final IntBuffer intBuffer) {
        return (intBuffer == null) ? 0L : getAddress(intBuffer);
    }
    
    public static long getAddressSafe(final IntBuffer intBuffer, final int n) {
        return (intBuffer == null) ? 0L : getAddress(intBuffer, n);
    }
    
    public static long getAddressSafe(final FloatBuffer floatBuffer) {
        return (floatBuffer == null) ? 0L : getAddress(floatBuffer);
    }
    
    public static long getAddressSafe(final FloatBuffer floatBuffer, final int n) {
        return (floatBuffer == null) ? 0L : getAddress(floatBuffer, n);
    }
    
    public static long getAddressSafe(final LongBuffer longBuffer) {
        return (longBuffer == null) ? 0L : getAddress(longBuffer);
    }
    
    public static long getAddressSafe(final LongBuffer longBuffer, final int n) {
        return (longBuffer == null) ? 0L : getAddress(longBuffer, n);
    }
    
    public static long getAddressSafe(final DoubleBuffer doubleBuffer) {
        return (doubleBuffer == null) ? 0L : getAddress(doubleBuffer);
    }
    
    public static long getAddressSafe(final DoubleBuffer doubleBuffer, final int n) {
        return (doubleBuffer == null) ? 0L : getAddress(doubleBuffer, n);
    }
    
    public static long getAddressSafe(final PointerBuffer pointerBuffer) {
        return (pointerBuffer == null) ? 0L : getAddress(pointerBuffer);
    }
    
    public static long getAddressSafe(final PointerBuffer pointerBuffer, final int n) {
        return (pointerBuffer == null) ? 0L : getAddress(pointerBuffer, n);
    }
    
    public static ByteBuffer encodeASCII(final CharSequence charSequence) {
        return encode(charSequence, MemoryUtil.ascii);
    }
    
    public static ByteBuffer encodeUTF8(final CharSequence charSequence) {
        return encode(charSequence, MemoryUtil.utf8);
    }
    
    public static ByteBuffer encodeUTF16(final CharSequence charSequence) {
        return encode(charSequence, MemoryUtil.utf16);
    }
    
    private static ByteBuffer encode(final CharSequence charSequence, final Charset charset) {
        if (charSequence == null) {
            return null;
        }
        return encode(CharBuffer.wrap(new CharSequenceNT(charSequence)), charset);
    }
    
    private static ByteBuffer encode(final CharBuffer charBuffer, final Charset charset) {
        final CharsetEncoder encoder = charset.newEncoder();
        int n = (int)(charBuffer.remaining() * encoder.averageBytesPerChar());
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(n);
        if (n == 0 && charBuffer.remaining() == 0) {
            return byteBuffer;
        }
        encoder.reset();
        while (true) {
            CoderResult flush = charBuffer.hasRemaining() ? encoder.encode(charBuffer, byteBuffer, true) : CoderResult.UNDERFLOW;
            if (flush.isUnderflow()) {
                flush = encoder.flush(byteBuffer);
            }
            if (flush.isUnderflow()) {
                break;
            }
            if (flush.isOverflow()) {
                n = 2 * n + 1;
                final ByteBuffer byteBuffer2 = BufferUtils.createByteBuffer(n);
                byteBuffer.flip();
                byteBuffer2.put(byteBuffer);
                byteBuffer = byteBuffer2;
            }
            else {
                flush.throwException();
            }
        }
        byteBuffer.flip();
        return byteBuffer;
    }
    
    public static String decodeASCII(final ByteBuffer byteBuffer) {
        return decode(byteBuffer, MemoryUtil.ascii);
    }
    
    public static String decodeUTF8(final ByteBuffer byteBuffer) {
        return decode(byteBuffer, MemoryUtil.utf8);
    }
    
    public static String decodeUTF16(final ByteBuffer byteBuffer) {
        return decode(byteBuffer, MemoryUtil.utf16);
    }
    
    private static String decode(final ByteBuffer byteBuffer, final Charset charset) {
        if (byteBuffer == null) {
            return null;
        }
        return decodeImpl(byteBuffer, charset);
    }
    
    private static String decodeImpl(final ByteBuffer byteBuffer, final Charset charset) {
        final CharsetDecoder decoder = charset.newDecoder();
        int n = (int)(byteBuffer.remaining() * decoder.averageCharsPerByte());
        CharBuffer charBuffer = BufferUtils.createCharBuffer(n);
        if (n == 0 && byteBuffer.remaining() == 0) {
            return "";
        }
        decoder.reset();
        while (true) {
            CoderResult flush = byteBuffer.hasRemaining() ? decoder.decode(byteBuffer, charBuffer, true) : CoderResult.UNDERFLOW;
            if (flush.isUnderflow()) {
                flush = decoder.flush(charBuffer);
            }
            if (flush.isUnderflow()) {
                break;
            }
            if (flush.isOverflow()) {
                n = 2 * n + 1;
                final CharBuffer charBuffer2 = BufferUtils.createCharBuffer(n);
                charBuffer.flip();
                charBuffer2.put(charBuffer);
                charBuffer = charBuffer2;
            }
            else {
                flush.throwException();
            }
        }
        charBuffer.flip();
        return charBuffer.toString();
    }
    
    private static Accessor loadAccessor(final String s) throws Exception {
        return (Accessor)Class.forName(s).newInstance();
    }
    
    static Field getAddressField() throws NoSuchFieldException {
        return getDeclaredFieldRecursive(ByteBuffer.class, "address");
    }
    
    private static Field getDeclaredFieldRecursive(final Class clazz, final String s) throws NoSuchFieldException {
        return clazz.getDeclaredField(s);
    }
    
    static {
        ascii = Charset.forName("ISO-8859-1");
        utf8 = Charset.forName("UTF-8");
        utf16 = Charset.forName("UTF-16LE");
        final Accessor loadAccessor = loadAccessor("org.lwjgl.MemoryUtilSun$AccessorUnsafe");
        LWJGLUtil.log("MemoryUtil Accessor: " + loadAccessor.getClass().getSimpleName());
        memUtil = loadAccessor;
    }
    
    private static class AccessorReflect implements Accessor
    {
        private final Field address;
        
        AccessorReflect() {
            (this.address = MemoryUtil.getAddressField()).setAccessible(true);
        }
        
        @Override
        public long getAddress(final Buffer buffer) {
            return this.address.getLong(buffer);
        }
    }
    
    interface Accessor
    {
        long getAddress(final Buffer p0);
    }
    
    private static class AccessorJNI implements Accessor
    {
        private AccessorJNI() {
        }
        
        @Override
        public long getAddress(final Buffer buffer) {
            return BufferUtils.getBufferAddress(buffer);
        }
        
        AccessorJNI(final MemoryUtil$1 object) {
            this();
        }
    }
    
    private static class CharSequenceNT implements CharSequence
    {
        final CharSequence source;
        
        CharSequenceNT(final CharSequence source) {
            this.source = source;
        }
        
        @Override
        public int length() {
            return this.source.length() + 1;
        }
        
        @Override
        public char charAt(final int n) {
            return (n == this.source.length()) ? '\0' : this.source.charAt(n);
        }
        
        @Override
        public CharSequence subSequence(final int n, final int n2) {
            return new CharSequenceNT(this.source.subSequence(n, Math.min(n2, this.source.length())));
        }
    }
}
