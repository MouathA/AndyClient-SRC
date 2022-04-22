package org.apache.commons.compress.archivers.zip;

import java.io.*;
import java.math.*;

public final class ZipEightByteInteger implements Serializable
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
    private static final int BYTE_4 = 4;
    private static final long BYTE_4_MASK = 1095216660480L;
    private static final int BYTE_4_SHIFT = 32;
    private static final int BYTE_5 = 5;
    private static final long BYTE_5_MASK = 280375465082880L;
    private static final int BYTE_5_SHIFT = 40;
    private static final int BYTE_6 = 6;
    private static final long BYTE_6_MASK = 71776119061217280L;
    private static final int BYTE_6_SHIFT = 48;
    private static final int BYTE_7 = 7;
    private static final long BYTE_7_MASK = 9151314442816847872L;
    private static final int BYTE_7_SHIFT = 56;
    private static final int LEFTMOST_BIT_SHIFT = 63;
    private static final byte LEFTMOST_BIT = Byte.MIN_VALUE;
    private final BigInteger value;
    public static final ZipEightByteInteger ZERO;
    
    public ZipEightByteInteger(final long n) {
        this(BigInteger.valueOf(n));
    }
    
    public ZipEightByteInteger(final BigInteger value) {
        this.value = value;
    }
    
    public ZipEightByteInteger(final byte[] array) {
        this(array, 0);
    }
    
    public ZipEightByteInteger(final byte[] array, final int n) {
        this.value = getValue(array, n);
    }
    
    public byte[] getBytes() {
        return getBytes(this.value);
    }
    
    public long getLongValue() {
        return this.value.longValue();
    }
    
    public BigInteger getValue() {
        return this.value;
    }
    
    public static byte[] getBytes(final long n) {
        return getBytes(BigInteger.valueOf(n));
    }
    
    public static byte[] getBytes(final BigInteger bigInteger) {
        final byte[] array = new byte[8];
        final long longValue = bigInteger.longValue();
        array[0] = (byte)(longValue & 0xFFL);
        array[1] = (byte)((longValue & 0xFF00L) >> 8);
        array[2] = (byte)((longValue & 0xFF0000L) >> 16);
        array[3] = (byte)((longValue & 0xFF000000L) >> 24);
        array[4] = (byte)((longValue & 0xFF00000000L) >> 32);
        array[5] = (byte)((longValue & 0xFF0000000000L) >> 40);
        array[6] = (byte)((longValue & 0xFF000000000000L) >> 48);
        array[7] = (byte)((longValue & 0x7F00000000000000L) >> 56);
        if (bigInteger.testBit(63)) {
            final byte[] array2 = array;
            final int n = 7;
            array2[n] |= 0xFFFFFF80;
        }
        return array;
    }
    
    public static long getLongValue(final byte[] array, final int n) {
        return getValue(array, n).longValue();
    }
    
    public static BigInteger getValue(final byte[] array, final int n) {
        final BigInteger value = BigInteger.valueOf(((long)array[n + 7] << 56 & 0x7F00000000000000L) + ((long)array[n + 6] << 48 & 0xFF000000000000L) + ((long)array[n + 5] << 40 & 0xFF0000000000L) + ((long)array[n + 4] << 32 & 0xFF00000000L) + ((long)array[n + 3] << 24 & 0xFF000000L) + ((long)array[n + 2] << 16 & 0xFF0000L) + ((long)array[n + 1] << 8 & 0xFF00L) + ((long)array[n] & 0xFFL));
        return ((array[n + 7] & 0xFFFFFF80) == 0xFFFFFF80) ? value.setBit(63) : value;
    }
    
    public static long getLongValue(final byte[] array) {
        return getLongValue(array, 0);
    }
    
    public static BigInteger getValue(final byte[] array) {
        return getValue(array, 0);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof ZipEightByteInteger && this.value.equals(((ZipEightByteInteger)o).getValue());
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return "ZipEightByteInteger value: " + this.value;
    }
    
    static {
        ZERO = new ZipEightByteInteger(0L);
    }
}
