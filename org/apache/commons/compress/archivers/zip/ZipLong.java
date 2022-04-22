package org.apache.commons.compress.archivers.zip;

import java.io.*;

public final class ZipLong implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final int BYTE_1 = 1;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final int BYTE_2 = 2;
    private static final int BYTE_2_MASK = 16711680;
    private static final int BYTE_2_SHIFT = 16;
    private static final int BYTE_3 = 3;
    private static final long BYTE_3_MASK = 4278190080L;
    private static final int BYTE_3_SHIFT = 24;
    private final long value;
    public static final ZipLong CFH_SIG;
    public static final ZipLong LFH_SIG;
    public static final ZipLong DD_SIG;
    static final ZipLong ZIP64_MAGIC;
    public static final ZipLong SINGLE_SEGMENT_SPLIT_MARKER;
    public static final ZipLong AED_SIG;
    
    public ZipLong(final long value) {
        this.value = value;
    }
    
    public ZipLong(final byte[] array) {
        this(array, 0);
    }
    
    public ZipLong(final byte[] array, final int n) {
        this.value = getValue(array, n);
    }
    
    public byte[] getBytes() {
        return getBytes(this.value);
    }
    
    public long getValue() {
        return this.value;
    }
    
    public static byte[] getBytes(final long n) {
        return new byte[] { (byte)(n & 0xFFL), (byte)((n & 0xFF00L) >> 8), (byte)((n & 0xFF0000L) >> 16), (byte)((n & 0xFF000000L) >> 24) };
    }
    
    public static long getValue(final byte[] array, final int n) {
        return ((long)(array[n + 3] << 24) & 0xFF000000L) + (array[n + 2] << 16 & 0xFF0000) + (array[n + 1] << 8 & 0xFF00) + (array[n] & 0xFF);
    }
    
    public static long getValue(final byte[] array) {
        return getValue(array, 0);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof ZipLong && this.value == ((ZipLong)o).getValue();
    }
    
    @Override
    public int hashCode() {
        return (int)this.value;
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public String toString() {
        return "ZipLong value: " + this.value;
    }
    
    static {
        CFH_SIG = new ZipLong(33639248L);
        LFH_SIG = new ZipLong(67324752L);
        DD_SIG = new ZipLong(134695760L);
        ZIP64_MAGIC = new ZipLong(4294967295L);
        SINGLE_SEGMENT_SPLIT_MARKER = new ZipLong(808471376L);
        AED_SIG = new ZipLong(134630224L);
    }
}
