package org.apache.commons.io;

import java.io.*;

public class ByteOrderMark implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final ByteOrderMark UTF_8;
    public static final ByteOrderMark UTF_16BE;
    public static final ByteOrderMark UTF_16LE;
    public static final ByteOrderMark UTF_32BE;
    public static final ByteOrderMark UTF_32LE;
    private final String charsetName;
    private final int[] bytes;
    
    public ByteOrderMark(final String charsetName, final int... array) {
        if (charsetName == null || charsetName.length() == 0) {
            throw new IllegalArgumentException("No charsetName specified");
        }
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("No bytes specified");
        }
        this.charsetName = charsetName;
        System.arraycopy(array, 0, this.bytes = new int[array.length], 0, array.length);
    }
    
    public String getCharsetName() {
        return this.charsetName;
    }
    
    public int length() {
        return this.bytes.length;
    }
    
    public int get(final int n) {
        return this.bytes[n];
    }
    
    public byte[] getBytes() {
        final byte[] array = new byte[this.bytes.length];
        while (0 < this.bytes.length) {
            array[0] = (byte)this.bytes[0];
            int n = 0;
            ++n;
        }
        return array;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ByteOrderMark)) {
            return false;
        }
        final ByteOrderMark byteOrderMark = (ByteOrderMark)o;
        if (this.bytes.length != byteOrderMark.length()) {
            return false;
        }
        while (0 < this.bytes.length) {
            if (this.bytes[0] != byteOrderMark.get(0)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = this.getClass().hashCode();
        final int[] bytes = this.bytes;
        while (0 < bytes.length) {
            hashCode += bytes[0];
            int n = 0;
            ++n;
        }
        return hashCode;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append('[');
        sb.append(this.charsetName);
        sb.append(": ");
        while (0 < this.bytes.length) {
            if (0 > 0) {
                sb.append(",");
            }
            sb.append("0x");
            sb.append(Integer.toHexString(0xFF & this.bytes[0]).toUpperCase());
            int n = 0;
            ++n;
        }
        sb.append(']');
        return sb.toString();
    }
    
    static {
        UTF_8 = new ByteOrderMark("UTF-8", new int[] { 239, 187, 191 });
        UTF_16BE = new ByteOrderMark("UTF-16BE", new int[] { 254, 255 });
        UTF_16LE = new ByteOrderMark("UTF-16LE", new int[] { 255, 254 });
        UTF_32BE = new ByteOrderMark("UTF-32BE", new int[] { 0, 0, 254, 255 });
        UTF_32LE = new ByteOrderMark("UTF-32LE", new int[] { 255, 254, 0, 0 });
    }
}
