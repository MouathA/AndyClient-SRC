package io.netty.util.internal;

import java.util.*;
import java.io.*;

public final class AppendableCharSequence implements CharSequence, Appendable
{
    private char[] chars;
    private int pos;
    
    public AppendableCharSequence(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("length: " + n + " (length: >= 1)");
        }
        this.chars = new char[n];
    }
    
    private AppendableCharSequence(final char[] chars) {
        this.chars = chars;
        this.pos = chars.length;
    }
    
    @Override
    public int length() {
        return this.pos;
    }
    
    @Override
    public char charAt(final int n) {
        if (n > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return this.chars[n];
    }
    
    @Override
    public AppendableCharSequence subSequence(final int n, final int n2) {
        return new AppendableCharSequence(Arrays.copyOfRange(this.chars, n, n2));
    }
    
    @Override
    public AppendableCharSequence append(final char c) {
        if (this.pos == this.chars.length) {
            final char[] chars = this.chars;
            final int n = chars.length << 1;
            if (n < 0) {
                throw new IllegalStateException();
            }
            System.arraycopy(chars, 0, this.chars = new char[n], 0, chars.length);
        }
        this.chars[this.pos++] = c;
        return this;
    }
    
    @Override
    public AppendableCharSequence append(final CharSequence charSequence) {
        return this.append(charSequence, 0, charSequence.length());
    }
    
    @Override
    public AppendableCharSequence append(final CharSequence charSequence, final int n, final int n2) {
        if (charSequence.length() < n2) {
            throw new IndexOutOfBoundsException();
        }
        final int n3 = n2 - n;
        if (n3 > this.chars.length - this.pos) {
            this.chars = expand(this.chars, this.pos + n3, this.pos);
        }
        if (charSequence instanceof AppendableCharSequence) {
            System.arraycopy(((AppendableCharSequence)charSequence).chars, n, this.chars, this.pos, n3);
            this.pos += n3;
            return this;
        }
        for (int i = n; i < n2; ++i) {
            this.chars[this.pos++] = charSequence.charAt(i);
        }
        return this;
    }
    
    public void reset() {
        this.pos = 0;
    }
    
    @Override
    public String toString() {
        return new String(this.chars, 0, this.pos);
    }
    
    public String substring(final int n, final int n2) {
        final int n3 = n2 - n;
        if (n > this.pos || n3 > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.chars, n, n3);
    }
    
    private static char[] expand(final char[] array, final int i, final int n) {
        int length = array.length;
        do {
            length <<= 1;
            if (length < 0) {
                throw new IllegalStateException();
            }
        } while (i > length);
        final char[] array2 = new char[length];
        System.arraycopy(array, 0, array2, 0, n);
        return array2;
    }
    
    @Override
    public CharSequence subSequence(final int n, final int n2) {
        return this.subSequence(n, n2);
    }
    
    @Override
    public Appendable append(final char c) throws IOException {
        return this.append(c);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}
