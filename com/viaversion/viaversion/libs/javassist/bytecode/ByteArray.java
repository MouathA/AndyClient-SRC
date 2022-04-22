package com.viaversion.viaversion.libs.javassist.bytecode;

public class ByteArray
{
    public static int readU16bit(final byte[] array, final int n) {
        return (array[n] & 0xFF) << 8 | (array[n + 1] & 0xFF);
    }
    
    public static int readS16bit(final byte[] array, final int n) {
        return array[n] << 8 | (array[n + 1] & 0xFF);
    }
    
    public static void write16bit(final int n, final byte[] array, final int n2) {
        array[n2] = (byte)(n >>> 8);
        array[n2 + 1] = (byte)n;
    }
    
    public static int read32bit(final byte[] array, final int n) {
        return array[n] << 24 | (array[n + 1] & 0xFF) << 16 | (array[n + 2] & 0xFF) << 8 | (array[n + 3] & 0xFF);
    }
    
    public static void write32bit(final int n, final byte[] array, final int n2) {
        array[n2] = (byte)(n >>> 24);
        array[n2 + 1] = (byte)(n >>> 16);
        array[n2 + 2] = (byte)(n >>> 8);
        array[n2 + 3] = (byte)n;
    }
    
    static void copy32bit(final byte[] array, final int n, final byte[] array2, final int n2) {
        array2[n2] = array[n];
        array2[n2 + 1] = array[n + 1];
        array2[n2 + 2] = array[n + 2];
        array2[n2 + 3] = array[n + 3];
    }
}
