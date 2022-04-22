package io.netty.handler.codec.http;

import io.netty.util.*;
import io.netty.buffer.*;

final class HttpHeaderEntity implements CharSequence
{
    private final String name;
    private final int hash;
    private final byte[] bytes;
    private final int separatorLen;
    
    public HttpHeaderEntity(final String s) {
        this(s, null);
    }
    
    public HttpHeaderEntity(final String name, final byte[] array) {
        this.name = name;
        this.hash = HttpHeaders.hash(name);
        final byte[] bytes = name.getBytes(CharsetUtil.US_ASCII);
        if (array == null) {
            this.bytes = bytes;
            this.separatorLen = 0;
        }
        else {
            this.separatorLen = array.length;
            System.arraycopy(bytes, 0, this.bytes = new byte[bytes.length + array.length], 0, bytes.length);
            System.arraycopy(array, 0, this.bytes, bytes.length, array.length);
        }
    }
    
    int hash() {
        return this.hash;
    }
    
    @Override
    public int length() {
        return this.bytes.length - this.separatorLen;
    }
    
    @Override
    public char charAt(final int n) {
        if (this.bytes.length - this.separatorLen <= n) {
            throw new IndexOutOfBoundsException();
        }
        return (char)this.bytes[n];
    }
    
    @Override
    public CharSequence subSequence(final int n, final int n2) {
        return new HttpHeaderEntity(this.name.substring(n, n2));
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    boolean encode(final ByteBuf byteBuf) {
        byteBuf.writeBytes(this.bytes);
        return this.separatorLen > 0;
    }
}
