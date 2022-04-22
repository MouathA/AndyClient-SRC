package org.apache.http.util;

import java.io.*;
import org.apache.http.annotation.*;

@NotThreadSafe
public final class ByteArrayBuffer implements Serializable
{
    private static final long serialVersionUID = 4359112959524048036L;
    private byte[] buffer;
    private int len;
    
    public ByteArrayBuffer(final int n) {
        Args.notNegative(n, "Buffer capacity");
        this.buffer = new byte[n];
    }
    
    private void expand(final int n) {
        final byte[] buffer = new byte[Math.max(this.buffer.length << 1, n)];
        System.arraycopy(this.buffer, 0, buffer, 0, this.len);
        this.buffer = buffer;
    }
    
    public void append(final byte[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (n < 0 || n > array.length || n2 < 0 || n + n2 < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("off: " + n + " len: " + n2 + " b.length: " + array.length);
        }
        if (n2 == 0) {
            return;
        }
        final int len = this.len + n2;
        if (len > this.buffer.length) {
            this.expand(len);
        }
        System.arraycopy(array, n, this.buffer, this.len, n2);
        this.len = len;
    }
    
    public void append(final int n) {
        final int len = this.len + 1;
        if (len > this.buffer.length) {
            this.expand(len);
        }
        this.buffer[this.len] = (byte)n;
        this.len = len;
    }
    
    public void append(final char[] array, final int n, final int n2) {
        if (array == null) {
            return;
        }
        if (n < 0 || n > array.length || n2 < 0 || n + n2 < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("off: " + n + " len: " + n2 + " b.length: " + array.length);
        }
        if (n2 == 0) {
            return;
        }
        final int len = this.len;
        final int len2 = len + n2;
        if (len2 > this.buffer.length) {
            this.expand(len2);
        }
        int n3 = n;
        for (int i = len; i < len2; ++i) {
            this.buffer[i] = (byte)array[n3];
            ++n3;
        }
        this.len = len2;
    }
    
    public void append(final CharArrayBuffer charArrayBuffer, final int n, final int n2) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer(), n, n2);
    }
    
    public void clear() {
        this.len = 0;
    }
    
    public byte[] toByteArray() {
        final byte[] array = new byte[this.len];
        if (this.len > 0) {
            System.arraycopy(this.buffer, 0, array, 0, this.len);
        }
        return array;
    }
    
    public int byteAt(final int n) {
        return this.buffer[n];
    }
    
    public int capacity() {
        return this.buffer.length;
    }
    
    public int length() {
        return this.len;
    }
    
    public void ensureCapacity(final int n) {
        if (n <= 0) {
            return;
        }
        if (n > this.buffer.length - this.len) {
            this.expand(this.len + n);
        }
    }
    
    public byte[] buffer() {
        return this.buffer;
    }
    
    public void setLength(final int len) {
        if (len < 0 || len > this.buffer.length) {
            throw new IndexOutOfBoundsException("len: " + len + " < 0 or > buffer len: " + this.buffer.length);
        }
        this.len = len;
    }
    
    public boolean isEmpty() {
        return this.len == 0;
    }
    
    public boolean isFull() {
        return this.len == this.buffer.length;
    }
    
    public int indexOf(final byte b, final int n, final int n2) {
        if (0 < 0) {}
        int len = n2;
        if (len > this.len) {
            len = this.len;
        }
        if (0 > len) {
            return -1;
        }
        while (0 < len) {
            if (this.buffer[0] == b) {
                return 0;
            }
            int n3 = 0;
            ++n3;
        }
        return -1;
    }
    
    public int indexOf(final byte b) {
        return this.indexOf(b, 0, this.len);
    }
}
