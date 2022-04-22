package com.google.common.escape;

import com.google.common.annotations.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public abstract class UnicodeEscaper extends Escaper
{
    private static final int DEST_PAD = 32;
    
    protected UnicodeEscaper() {
    }
    
    protected abstract char[] escape(final int p0);
    
    protected int nextEscapeIndex(final CharSequence charSequence, final int n, final int n2) {
        int i;
        int codePoint;
        for (i = n; i < n2; i += (Character.isSupplementaryCodePoint(codePoint) ? 2 : 1)) {
            codePoint = codePointAt(charSequence, i, n2);
            if (codePoint < 0) {
                break;
            }
            if (this.escape(codePoint) != null) {
                break;
            }
        }
        return i;
    }
    
    @Override
    public String escape(final String s) {
        Preconditions.checkNotNull(s);
        final int length = s.length();
        final int nextEscapeIndex = this.nextEscapeIndex(s, 0, length);
        return (nextEscapeIndex == length) ? s : this.escapeSlow(s, nextEscapeIndex);
    }
    
    protected final String escapeSlow(final String s, int i) {
        final int length = s.length();
        char[] array = Platform.charBufferFromThreadLocal();
        while (i < length) {
            final int codePoint = codePointAt(s, i, length);
            if (codePoint < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            final char[] escape = this.escape(codePoint);
            final int n = i + (Character.isSupplementaryCodePoint(codePoint) ? 2 : 1);
            if (escape != null) {
                final int n2 = i - 0;
                final int n3 = 0 + n2 + escape.length;
                if (array.length < n3) {
                    array = growBuffer(array, 0, n3 + (length - i) + 32);
                }
                if (n2 > 0) {
                    s.getChars(0, i, array, 0);
                }
                if (escape.length > 0) {
                    System.arraycopy(escape, 0, array, 0, escape.length);
                    final int n4 = 0 + escape.length;
                }
            }
            i = this.nextEscapeIndex(s, n, length);
        }
        final int n5 = length - 0;
        if (n5 > 0) {
            final int n6 = 0 + n5;
            if (array.length < n6) {
                array = growBuffer(array, 0, n6);
            }
            s.getChars(0, length, array, 0);
        }
        return new String(array, 0, 0);
    }
    
    protected static int codePointAt(final CharSequence charSequence, int n, final int n2) {
        Preconditions.checkNotNull(charSequence);
        if (n >= n2) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        final char char1 = charSequence.charAt(n++);
        if (char1 < '\ud800' || char1 > '\udfff') {
            return char1;
        }
        if (char1 > '\udbff') {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + char1 + "' with value " + (int)char1 + " at index " + (n - 1) + " in '" + (Object)charSequence + "'");
        }
        if (n == n2) {
            return -char1;
        }
        final char char2 = charSequence.charAt(n);
        if (Character.isLowSurrogate(char2)) {
            return Character.toCodePoint(char1, char2);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + char2 + "' with value " + (int)char2 + " at index " + n + " in '" + (Object)charSequence + "'");
    }
    
    private static char[] growBuffer(final char[] array, final int n, final int n2) {
        final char[] array2 = new char[n2];
        if (n > 0) {
            System.arraycopy(array, 0, array2, 0, n);
        }
        return array2;
    }
}
