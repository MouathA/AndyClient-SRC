package org.lwjgl;

import java.nio.*;

public class PointerBuffer implements Comparable
{
    private static final boolean is64Bit;
    protected final ByteBuffer pointers;
    protected final Buffer view;
    protected final IntBuffer view32;
    protected final LongBuffer view64;
    
    public PointerBuffer(final int n) {
        this(BufferUtils.createByteBuffer(n * getPointerSize()));
    }
    
    public PointerBuffer(final ByteBuffer byteBuffer) {
        if (LWJGLUtil.CHECKS) {
            checkSource(byteBuffer);
        }
        this.pointers = byteBuffer.slice().order(byteBuffer.order());
        if (PointerBuffer.is64Bit) {
            this.view32 = null;
            final LongBuffer longBuffer = this.pointers.asLongBuffer();
            this.view64 = longBuffer;
            this.view = longBuffer;
        }
        else {
            final IntBuffer intBuffer = this.pointers.asIntBuffer();
            this.view32 = intBuffer;
            this.view = intBuffer;
            this.view64 = null;
        }
    }
    
    private static void checkSource(final ByteBuffer byteBuffer) {
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("The source buffer is not direct.");
        }
        final int n = PointerBuffer.is64Bit ? 8 : 4;
        if ((MemoryUtil.getAddress0(byteBuffer) + byteBuffer.position()) % n != 0L || byteBuffer.remaining() % n != 0) {
            throw new IllegalArgumentException("The source buffer is not aligned to " + n + " bytes.");
        }
    }
    
    public ByteBuffer getBuffer() {
        return this.pointers;
    }
    
    public static boolean is64Bit() {
        return PointerBuffer.is64Bit;
    }
    
    public static int getPointerSize() {
        return PointerBuffer.is64Bit ? 8 : 4;
    }
    
    public final int capacity() {
        return this.view.capacity();
    }
    
    public final int position() {
        return this.view.position();
    }
    
    public final int positionByte() {
        return this.position() * getPointerSize();
    }
    
    public final PointerBuffer position(final int n) {
        this.view.position(n);
        return this;
    }
    
    public final int limit() {
        return this.view.limit();
    }
    
    public final PointerBuffer limit(final int n) {
        this.view.limit(n);
        return this;
    }
    
    public final PointerBuffer mark() {
        this.view.mark();
        return this;
    }
    
    public final PointerBuffer reset() {
        this.view.reset();
        return this;
    }
    
    public final PointerBuffer clear() {
        this.view.clear();
        return this;
    }
    
    public final PointerBuffer flip() {
        this.view.flip();
        return this;
    }
    
    public final PointerBuffer rewind() {
        this.view.rewind();
        return this;
    }
    
    public final int remaining() {
        return this.view.remaining();
    }
    
    public final int remainingByte() {
        return this.remaining() * getPointerSize();
    }
    
    public final boolean hasRemaining() {
        return this.view.hasRemaining();
    }
    
    public static PointerBuffer allocateDirect(final int n) {
        return new PointerBuffer(n);
    }
    
    protected PointerBuffer newInstance(final ByteBuffer byteBuffer) {
        return new PointerBuffer(byteBuffer);
    }
    
    public PointerBuffer slice() {
        final int pointerSize = getPointerSize();
        this.pointers.position(this.view.position() * pointerSize);
        this.pointers.limit(this.view.limit() * pointerSize);
        final PointerBuffer instance = this.newInstance(this.pointers);
        this.pointers.clear();
        return instance;
    }
    
    public PointerBuffer duplicate() {
        final PointerBuffer instance = this.newInstance(this.pointers);
        instance.position(this.view.position());
        instance.limit(this.view.limit());
        return instance;
    }
    
    public PointerBuffer asReadOnlyBuffer() {
        final PointerBufferR pointerBufferR = new PointerBufferR(this.pointers);
        pointerBufferR.position(this.view.position());
        pointerBufferR.limit(this.view.limit());
        return pointerBufferR;
    }
    
    public boolean isReadOnly() {
        return false;
    }
    
    public long get() {
        if (PointerBuffer.is64Bit) {
            return this.view64.get();
        }
        return (long)this.view32.get() & 0xFFFFFFFFL;
    }
    
    public PointerBuffer put(final long n) {
        if (PointerBuffer.is64Bit) {
            this.view64.put(n);
        }
        else {
            this.view32.put((int)n);
        }
        return this;
    }
    
    public PointerBuffer put(final PointerWrapper pointerWrapper) {
        return this.put(pointerWrapper.getPointer());
    }
    
    public static void put(final ByteBuffer byteBuffer, final long n) {
        if (PointerBuffer.is64Bit) {
            byteBuffer.putLong(n);
        }
        else {
            byteBuffer.putInt((int)n);
        }
    }
    
    public long get(final int n) {
        if (PointerBuffer.is64Bit) {
            return this.view64.get(n);
        }
        return (long)this.view32.get(n) & 0xFFFFFFFFL;
    }
    
    public PointerBuffer put(final int n, final long n2) {
        if (PointerBuffer.is64Bit) {
            this.view64.put(n, n2);
        }
        else {
            this.view32.put(n, (int)n2);
        }
        return this;
    }
    
    public PointerBuffer put(final int n, final PointerWrapper pointerWrapper) {
        return this.put(n, pointerWrapper.getPointer());
    }
    
    public static void put(final ByteBuffer byteBuffer, final int n, final long n2) {
        if (PointerBuffer.is64Bit) {
            byteBuffer.putLong(n, n2);
        }
        else {
            byteBuffer.putInt(n, (int)n2);
        }
    }
    
    public PointerBuffer get(final long[] array, final int n, final int n2) {
        if (PointerBuffer.is64Bit) {
            this.view64.get(array, n, n2);
        }
        else {
            checkBounds(n, n2, array.length);
            if (n2 > this.view32.remaining()) {
                throw new BufferUnderflowException();
            }
            for (int n3 = n + n2, i = n; i < n3; ++i) {
                array[i] = ((long)this.view32.get() & 0xFFFFFFFFL);
            }
        }
        return this;
    }
    
    public PointerBuffer get(final long[] array) {
        return this.get(array, 0, array.length);
    }
    
    public PointerBuffer put(final PointerBuffer pointerBuffer) {
        if (PointerBuffer.is64Bit) {
            this.view64.put(pointerBuffer.view64);
        }
        else {
            this.view32.put(pointerBuffer.view32);
        }
        return this;
    }
    
    public PointerBuffer put(final long[] array, final int n, final int n2) {
        if (PointerBuffer.is64Bit) {
            this.view64.put(array, n, n2);
        }
        else {
            checkBounds(n, n2, array.length);
            if (n2 > this.view32.remaining()) {
                throw new BufferOverflowException();
            }
            for (int n3 = n + n2, i = n; i < n3; ++i) {
                this.view32.put((int)array[i]);
            }
        }
        return this;
    }
    
    public final PointerBuffer put(final long[] array) {
        return this.put(array, 0, array.length);
    }
    
    public PointerBuffer compact() {
        if (PointerBuffer.is64Bit) {
            this.view64.compact();
        }
        else {
            this.view32.compact();
        }
        return this;
    }
    
    public ByteOrder order() {
        if (PointerBuffer.is64Bit) {
            return this.view64.order();
        }
        return this.view32.order();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(48);
        sb.append(this.getClass().getName());
        sb.append("[pos=");
        sb.append(this.position());
        sb.append(" lim=");
        sb.append(this.limit());
        sb.append(" cap=");
        sb.append(this.capacity());
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        for (int position = this.position(), i = this.limit() - 1; i >= position; --i) {
            final int n = 31 + (int)this.get(i);
        }
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PointerBuffer)) {
            return false;
        }
        final PointerBuffer pointerBuffer = (PointerBuffer)o;
        if (this.remaining() != pointerBuffer.remaining()) {
            return false;
        }
        for (int position = this.position(), i = this.limit() - 1, n = pointerBuffer.limit() - 1; i >= position; --i, --n) {
            if (this.get(i) != pointerBuffer.get(n)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int compareTo(final Object o) {
        final PointerBuffer pointerBuffer = (PointerBuffer)o;
        final int n = this.position() + Math.min(this.remaining(), pointerBuffer.remaining());
        int i = this.position();
        int position = pointerBuffer.position();
        while (i < n) {
            final long value = this.get(i);
            final long value2 = pointerBuffer.get(position);
            if (value == value2) {
                ++i;
                ++position;
            }
            else {
                if (value < value2) {
                    return -1;
                }
                return 1;
            }
        }
        return this.remaining() - pointerBuffer.remaining();
    }
    
    private static void checkBounds(final int n, final int n2, final int n3) {
        if ((n | n2 | n + n2 | n3 - (n + n2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    static {
        (boolean)Class.forName("org.lwjgl.Sys").getDeclaredMethod("is64Bit", (Class<?>[])null).invoke(null, (Object[])null);
        is64Bit = false;
    }
    
    private static final class PointerBufferR extends PointerBuffer
    {
        PointerBufferR(final ByteBuffer byteBuffer) {
            super(byteBuffer);
        }
        
        @Override
        public boolean isReadOnly() {
            return true;
        }
        
        @Override
        protected PointerBuffer newInstance(final ByteBuffer byteBuffer) {
            return new PointerBufferR(byteBuffer);
        }
        
        @Override
        public PointerBuffer asReadOnlyBuffer() {
            return this.duplicate();
        }
        
        @Override
        public PointerBuffer put(final long n) {
            throw new ReadOnlyBufferException();
        }
        
        @Override
        public PointerBuffer put(final int n, final long n2) {
            throw new ReadOnlyBufferException();
        }
        
        @Override
        public PointerBuffer put(final PointerBuffer pointerBuffer) {
            throw new ReadOnlyBufferException();
        }
        
        @Override
        public PointerBuffer put(final long[] array, final int n, final int n2) {
            throw new ReadOnlyBufferException();
        }
        
        @Override
        public PointerBuffer compact() {
            throw new ReadOnlyBufferException();
        }
    }
}
