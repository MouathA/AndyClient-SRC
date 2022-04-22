package com.sun.jna;

import java.nio.*;
import java.util.*;

public class Memory extends Pointer
{
    private static final Map buffers;
    protected long size;
    
    public static void purge() {
        Memory.buffers.size();
    }
    
    public Memory(final long size) {
        this.size = size;
        if (size <= 0L) {
            throw new IllegalArgumentException("Allocation size must be greater than zero");
        }
        this.peer = malloc(size);
        if (this.peer == 0L) {
            throw new OutOfMemoryError("Cannot allocate " + size + " bytes");
        }
    }
    
    protected Memory() {
    }
    
    public Pointer share(final long n) {
        return this.share(n, this.getSize() - n);
    }
    
    public Pointer share(final long n, final long n2) {
        if (n == 0L && n2 == this.getSize()) {
            return this;
        }
        this.boundsCheck(n, n2);
        return new SharedMemory(n);
    }
    
    public Memory align(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Byte boundary must be positive: " + n);
        }
        while (0 < 32) {
            if (n == 1) {
                final long n2 = ~(n - 1L);
                if ((this.peer & n2) == this.peer) {
                    return this;
                }
                final long n3 = this.peer + n - 1L & n2;
                final long n4 = this.peer + this.size - n3;
                if (n4 <= 0L) {
                    throw new IllegalArgumentException("Insufficient memory to align to the requested boundary");
                }
                return (Memory)this.share(n3 - this.peer, n4);
            }
            else {
                int n5 = 0;
                ++n5;
            }
        }
        throw new IllegalArgumentException("Byte boundary must be a power of two");
    }
    
    protected void finalize() {
        this.dispose();
    }
    
    protected synchronized void dispose() {
        free(this.peer);
        this.peer = 0L;
    }
    
    public void clear() {
        this.clear(this.size);
    }
    
    public boolean isValid() {
        return this.valid();
    }
    
    public boolean valid() {
        return this.peer != 0L;
    }
    
    public long size() {
        return this.size;
    }
    
    public long getSize() {
        return this.size();
    }
    
    protected void boundsCheck(final long n, final long n2) {
        if (n < 0L) {
            throw new IndexOutOfBoundsException("Invalid offset: " + n);
        }
        if (n + n2 > this.size) {
            throw new IndexOutOfBoundsException("Bounds exceeds available space : size=" + this.size + ", offset=" + (n + n2));
        }
    }
    
    public void read(final long n, final byte[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 1L);
        super.read(n, array, n2, n3);
    }
    
    public void read(final long n, final short[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 2L);
        super.read(n, array, n2, n3);
    }
    
    public void read(final long n, final char[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 2L);
        super.read(n, array, n2, n3);
    }
    
    public void read(final long n, final int[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 4L);
        super.read(n, array, n2, n3);
    }
    
    public void read(final long n, final long[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 8L);
        super.read(n, array, n2, n3);
    }
    
    public void read(final long n, final float[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 4L);
        super.read(n, array, n2, n3);
    }
    
    public void read(final long n, final double[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 8L);
        super.read(n, array, n2, n3);
    }
    
    public void write(final long n, final byte[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 1L);
        super.write(n, array, n2, n3);
    }
    
    public void write(final long n, final short[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 2L);
        super.write(n, array, n2, n3);
    }
    
    public void write(final long n, final char[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 2L);
        super.write(n, array, n2, n3);
    }
    
    public void write(final long n, final int[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 4L);
        super.write(n, array, n2, n3);
    }
    
    public void write(final long n, final long[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 8L);
        super.write(n, array, n2, n3);
    }
    
    public void write(final long n, final float[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 4L);
        super.write(n, array, n2, n3);
    }
    
    public void write(final long n, final double[] array, final int n2, final int n3) {
        this.boundsCheck(n, n3 * 8L);
        super.write(n, array, n2, n3);
    }
    
    public byte getByte(final long n) {
        this.boundsCheck(n, 1L);
        return super.getByte(n);
    }
    
    public char getChar(final long n) {
        this.boundsCheck(n, 1L);
        return super.getChar(n);
    }
    
    public short getShort(final long n) {
        this.boundsCheck(n, 2L);
        return super.getShort(n);
    }
    
    public int getInt(final long n) {
        this.boundsCheck(n, 4L);
        return super.getInt(n);
    }
    
    public long getLong(final long n) {
        this.boundsCheck(n, 8L);
        return super.getLong(n);
    }
    
    public float getFloat(final long n) {
        this.boundsCheck(n, 4L);
        return super.getFloat(n);
    }
    
    public double getDouble(final long n) {
        this.boundsCheck(n, 8L);
        return super.getDouble(n);
    }
    
    public Pointer getPointer(final long n) {
        this.boundsCheck(n, Pointer.SIZE);
        return super.getPointer(n);
    }
    
    public ByteBuffer getByteBuffer(final long n, final long n2) {
        this.boundsCheck(n, n2);
        final ByteBuffer byteBuffer = super.getByteBuffer(n, n2);
        Memory.buffers.put(byteBuffer, this);
        return byteBuffer;
    }
    
    public String getString(final long n, final boolean b) {
        this.boundsCheck(n, 0L);
        return super.getString(n, b);
    }
    
    public void setByte(final long n, final byte b) {
        this.boundsCheck(n, 1L);
        super.setByte(n, b);
    }
    
    public void setChar(final long n, final char c) {
        this.boundsCheck(n, Native.WCHAR_SIZE);
        super.setChar(n, c);
    }
    
    public void setShort(final long n, final short n2) {
        this.boundsCheck(n, 2L);
        super.setShort(n, n2);
    }
    
    public void setInt(final long n, final int n2) {
        this.boundsCheck(n, 4L);
        super.setInt(n, n2);
    }
    
    public void setLong(final long n, final long n2) {
        this.boundsCheck(n, 8L);
        super.setLong(n, n2);
    }
    
    public void setFloat(final long n, final float n2) {
        this.boundsCheck(n, 4L);
        super.setFloat(n, n2);
    }
    
    public void setDouble(final long n, final double n2) {
        this.boundsCheck(n, 8L);
        super.setDouble(n, n2);
    }
    
    public void setPointer(final long n, final Pointer pointer) {
        this.boundsCheck(n, Pointer.SIZE);
        super.setPointer(n, pointer);
    }
    
    public void setString(final long n, final String s, final boolean b) {
        if (b) {
            this.boundsCheck(n, (s.length() + 1L) * Native.WCHAR_SIZE);
        }
        else {
            this.boundsCheck(n, s.getBytes().length + 1L);
        }
        super.setString(n, s, b);
    }
    
    public String toString() {
        return "allocated@0x" + Long.toHexString(this.peer) + " (" + this.size + " bytes)";
    }
    
    protected static void free(final long n) {
        Native.free(n);
    }
    
    protected static long malloc(final long n) {
        return Native.malloc(n);
    }
    
    static {
        buffers = Collections.synchronizedMap((Map<Object, Object>)(Platform.HAS_BUFFERS ? new WeakIdentityHashMap() : new HashMap<Object, Object>()));
    }
    
    private class SharedMemory extends Memory
    {
        private final Memory this$0;
        
        public SharedMemory(final Memory this$0, final long n) {
            this.this$0 = this$0;
            this.size = this$0.size - n;
            this.peer = this$0.peer + n;
        }
        
        protected void finalize() {
        }
        
        protected void boundsCheck(final long n, final long n2) {
            this.this$0.boundsCheck(this.peer - this.this$0.peer + n, n2);
        }
        
        public String toString() {
            return super.toString() + " (shared from " + this.this$0.toString() + ")";
        }
    }
}
