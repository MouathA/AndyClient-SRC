package com.ibm.icu.util;

import java.nio.*;
import com.ibm.icu.impl.*;

public class ByteArrayWrapper implements Comparable
{
    public byte[] bytes;
    public int size;
    
    public ByteArrayWrapper() {
    }
    
    public ByteArrayWrapper(final byte[] bytes, final int size) {
        if ((bytes == null && size != 0) || size < 0 || size > bytes.length) {
            throw new IndexOutOfBoundsException("illegal size: " + size);
        }
        this.bytes = bytes;
        this.size = size;
    }
    
    public ByteArrayWrapper(final ByteBuffer byteBuffer) {
        this.size = byteBuffer.limit();
        byteBuffer.get(this.bytes = new byte[this.size], 0, this.size);
    }
    
    public ByteArrayWrapper ensureCapacity(final int n) {
        if (this.bytes == null || this.bytes.length < n) {
            final byte[] bytes = new byte[n];
            copyBytes(this.bytes, 0, bytes, 0, this.size);
            this.bytes = bytes;
        }
        return this;
    }
    
    public final ByteArrayWrapper set(final byte[] array, final int n, final int n2) {
        this.size = 0;
        this.append(array, n, n2);
        return this;
    }
    
    public final ByteArrayWrapper append(final byte[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        this.ensureCapacity(this.size + n3);
        copyBytes(array, n, this.bytes, this.size, n3);
        this.size += n3;
        return this;
    }
    
    public final byte[] releaseBytes() {
        final byte[] bytes = this.bytes;
        this.bytes = null;
        this.size = 0;
        return bytes;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        while (0 < this.size) {
            if (false) {
                sb.append(" ");
            }
            sb.append(Utility.hex(this.bytes[0] & 0xFF, 2));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        final ByteArrayWrapper byteArrayWrapper = (ByteArrayWrapper)o;
        if (this.size != byteArrayWrapper.size) {
            return false;
        }
        while (0 < this.size) {
            if (this.bytes[0] != byteArrayWrapper.bytes[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int length = this.bytes.length;
        while (0 < this.size) {
            length = 37 * length + this.bytes[0];
            int n = 0;
            ++n;
        }
        return length;
    }
    
    public int compareTo(final ByteArrayWrapper byteArrayWrapper) {
        if (this == byteArrayWrapper) {
            return 0;
        }
        while (0 < ((this.size < byteArrayWrapper.size) ? this.size : byteArrayWrapper.size)) {
            if (this.bytes[0] != byteArrayWrapper.bytes[0]) {
                return (this.bytes[0] & 0xFF) - (byteArrayWrapper.bytes[0] & 0xFF);
            }
            int n = 0;
            ++n;
        }
        return this.size - byteArrayWrapper.size;
    }
    
    private static final void copyBytes(final byte[] array, final int n, final byte[] array2, final int n2, int n3) {
        if (n3 < 64) {
            int n4 = n;
            int n5 = n2;
            while (--n3 >= 0) {
                array2[n5] = array[n4];
                ++n4;
                ++n5;
            }
        }
        else {
            System.arraycopy(array, n, array2, n2, n3);
        }
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((ByteArrayWrapper)o);
    }
}
