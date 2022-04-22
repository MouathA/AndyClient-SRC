package org.apache.http.util;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.protocol.*;

@NotThreadSafe
public final class CharArrayBuffer implements Serializable
{
    private static final long serialVersionUID = -6208952725094867135L;
    private char[] buffer;
    private int len;
    
    public CharArrayBuffer(final int n) {
        Args.notNegative(n, "Buffer capacity");
        this.buffer = new char[n];
    }
    
    private void expand(final int n) {
        final char[] buffer = new char[Math.max(this.buffer.length << 1, n)];
        System.arraycopy(this.buffer, 0, buffer, 0, this.len);
        this.buffer = buffer;
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
        final int len = this.len + n2;
        if (len > this.buffer.length) {
            this.expand(len);
        }
        System.arraycopy(array, n, this.buffer, this.len, n2);
        this.len = len;
    }
    
    public void append(final String s) {
        final String s2 = (s != null) ? s : "null";
        final int length = s2.length();
        final int len = this.len + length;
        if (len > this.buffer.length) {
            this.expand(len);
        }
        s2.getChars(0, length, this.buffer, this.len);
        this.len = len;
    }
    
    public void append(final CharArrayBuffer charArrayBuffer, final int n, final int n2) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer, n, n2);
    }
    
    public void append(final CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer, 0, charArrayBuffer.len);
    }
    
    public void append(final char c) {
        final int len = this.len + 1;
        if (len > this.buffer.length) {
            this.expand(len);
        }
        this.buffer[this.len] = c;
        this.len = len;
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
        final int len = this.len;
        final int len2 = len + n2;
        if (len2 > this.buffer.length) {
            this.expand(len2);
        }
        int n3 = n;
        for (int i = len; i < len2; ++i) {
            this.buffer[i] = (char)(array[n3] & 0xFF);
            ++n3;
        }
        this.len = len2;
    }
    
    public void append(final ByteArrayBuffer byteArrayBuffer, final int n, final int n2) {
        if (byteArrayBuffer == null) {
            return;
        }
        this.append(byteArrayBuffer.buffer(), n, n2);
    }
    
    public void append(final Object o) {
        this.append(String.valueOf(o));
    }
    
    public void clear() {
        this.len = 0;
    }
    
    public char[] toCharArray() {
        final char[] array = new char[this.len];
        if (this.len > 0) {
            System.arraycopy(this.buffer, 0, array, 0, this.len);
        }
        return array;
    }
    
    public char charAt(final int n) {
        return this.buffer[n];
    }
    
    public char[] buffer() {
        return this.buffer;
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
    
    public int indexOf(final int n, final int n2, final int n3) {
        int len = n3;
        if (len > this.len) {
            len = this.len;
        }
        if (0 > len) {
            return -1;
        }
        while (0 < len) {
            if (this.buffer[0] == n) {
                return 0;
            }
            int n4 = 0;
            ++n4;
        }
        return -1;
    }
    
    public int indexOf(final int n) {
        return this.indexOf(n, 0, this.len);
    }
    
    public String substring(final int n, final int n2) {
        return new String(this.buffer, n, n2 - n);
    }
    
    public String substringTrimmed(final int n, final int n2) {
        int n3 = n;
        int n4 = n2;
        if (n3 < 0) {
            throw new IndexOutOfBoundsException("Negative beginIndex: " + n3);
        }
        if (n4 > this.len) {
            throw new IndexOutOfBoundsException("endIndex: " + n4 + " > length: " + this.len);
        }
        if (n3 > n4) {
            throw new IndexOutOfBoundsException("beginIndex: " + n3 + " > endIndex: " + n4);
        }
        while (n3 < n4 && HTTP.isWhitespace(this.buffer[n3])) {
            ++n3;
        }
        while (n4 > n3 && HTTP.isWhitespace(this.buffer[n4 - 1])) {
            --n4;
        }
        return new String(this.buffer, n3, n4 - n3);
    }
    
    @Override
    public String toString() {
        return new String(this.buffer, 0, this.len);
    }
}
