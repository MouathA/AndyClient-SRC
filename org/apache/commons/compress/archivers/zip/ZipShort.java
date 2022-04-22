package org.apache.commons.compress.archivers.zip;

import java.io.*;

public final class ZipShort implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private final int value;
    
    public ZipShort(final int value) {
        this.value = value;
    }
    
    public ZipShort(final byte[] array) {
        this(array, 0);
    }
    
    public ZipShort(final byte[] array, final int n) {
        this.value = getValue(array, n);
    }
    
    public byte[] getBytes() {
        return new byte[] { (byte)(this.value & 0xFF), (byte)((this.value & 0xFF00) >> 8) };
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static byte[] getBytes(final int n) {
        return new byte[] { (byte)(n & 0xFF), (byte)((n & 0xFF00) >> 8) };
    }
    
    public static int getValue(final byte[] array, final int n) {
        return (array[n + 1] << 8 & 0xFF00) + (array[n] & 0xFF);
    }
    
    public static int getValue(final byte[] array) {
        return getValue(array, 0);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof ZipShort && this.value == ((ZipShort)o).getValue();
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public String toString() {
        return "ZipShort value: " + this.value;
    }
}
