package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.*;

public abstract class UnicodeEscaper implements Escaper
{
    private static final int DEST_PAD = 32;
    private static final ThreadLocal DEST_TL;
    static final boolean $assertionsDisabled;
    
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
        final int length = s.length();
        final int nextEscapeIndex = this.nextEscapeIndex(s, 0, length);
        return (nextEscapeIndex == length) ? s : this.escapeSlow(s, nextEscapeIndex);
    }
    
    protected final String escapeSlow(final String s, int i) {
        final int length = s.length();
        char[] array = UnicodeEscaper.DEST_TL.get();
        while (i < length) {
            final int codePoint = codePointAt(s, i, length);
            if (codePoint < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            final char[] escape = this.escape(codePoint);
            if (escape != null) {
                final int n = i - 0;
                final int n2 = 0 + n + escape.length;
                if (array.length < n2) {
                    array = growBuffer(array, 0, n2 + (length - i) + 32);
                }
                if (n > 0) {
                    s.getChars(0, i, array, 0);
                }
                if (escape.length > 0) {
                    System.arraycopy(escape, 0, array, 0, escape.length);
                    final int n3 = 0 + escape.length;
                }
            }
            final int n4 = i + (Character.isSupplementaryCodePoint(codePoint) ? 2 : 1);
            i = this.nextEscapeIndex(s, 0, length);
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
    
    @Override
    public Appendable escape(final Appendable appendable) {
        assert appendable != null;
        return new Appendable(appendable) {
            int pendingHighSurrogate = -1;
            char[] decodedChars = new char[2];
            final Appendable val$out;
            final UnicodeEscaper this$0;
            
            @Override
            public Appendable append(final CharSequence charSequence) throws IOException {
                return this.append(charSequence, 0, charSequence.length());
            }
            
            @Override
            public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
                int n3 = n;
                if (n3 < n2) {
                    int n4 = n3;
                    if (this.pendingHighSurrogate != -1) {
                        final char char1 = charSequence.charAt(n3++);
                        if (!Character.isLowSurrogate(char1)) {
                            throw new IllegalArgumentException("Expected low surrogate character but got " + char1);
                        }
                        final char[] escape = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, char1));
                        if (escape != null) {
                            this.outputChars(escape, escape.length);
                            ++n4;
                        }
                        else {
                            this.val$out.append((char)this.pendingHighSurrogate);
                        }
                        this.pendingHighSurrogate = -1;
                    }
                    while (true) {
                        final int nextEscapeIndex = this.this$0.nextEscapeIndex(charSequence, n3, n2);
                        if (nextEscapeIndex > n4) {
                            this.val$out.append(charSequence, n4, nextEscapeIndex);
                        }
                        if (nextEscapeIndex == n2) {
                            break;
                        }
                        final int codePoint = UnicodeEscaper.codePointAt(charSequence, nextEscapeIndex, n2);
                        if (codePoint < 0) {
                            this.pendingHighSurrogate = -codePoint;
                            break;
                        }
                        final char[] escape2 = this.this$0.escape(codePoint);
                        if (escape2 != null) {
                            this.outputChars(escape2, escape2.length);
                        }
                        else {
                            this.outputChars(this.decodedChars, Character.toChars(codePoint, this.decodedChars, 0));
                        }
                        n3 = (n4 = nextEscapeIndex + (Character.isSupplementaryCodePoint(codePoint) ? 2 : 1));
                    }
                }
                return this;
            }
            
            @Override
            public Appendable append(final char pendingHighSurrogate) throws IOException {
                if (this.pendingHighSurrogate != -1) {
                    if (!Character.isLowSurrogate(pendingHighSurrogate)) {
                        throw new IllegalArgumentException("Expected low surrogate character but got '" + pendingHighSurrogate + "' with value " + (int)pendingHighSurrogate);
                    }
                    final char[] escape = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, pendingHighSurrogate));
                    if (escape != null) {
                        this.outputChars(escape, escape.length);
                    }
                    else {
                        this.val$out.append((char)this.pendingHighSurrogate);
                        this.val$out.append(pendingHighSurrogate);
                    }
                    this.pendingHighSurrogate = -1;
                }
                else if (Character.isHighSurrogate(pendingHighSurrogate)) {
                    this.pendingHighSurrogate = pendingHighSurrogate;
                }
                else {
                    if (Character.isLowSurrogate(pendingHighSurrogate)) {
                        throw new IllegalArgumentException("Unexpected low surrogate character '" + pendingHighSurrogate + "' with value " + (int)pendingHighSurrogate);
                    }
                    final char[] escape2 = this.this$0.escape(pendingHighSurrogate);
                    if (escape2 != null) {
                        this.outputChars(escape2, escape2.length);
                    }
                    else {
                        this.val$out.append(pendingHighSurrogate);
                    }
                }
                return this;
            }
            
            private void outputChars(final char[] array, final int n) throws IOException {
                while (0 < n) {
                    this.val$out.append(array[0]);
                    int n2 = 0;
                    ++n2;
                }
            }
        };
    }
    
    protected static final int codePointAt(final CharSequence charSequence, int n, final int n2) {
        if (n >= n2) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        final char char1 = charSequence.charAt(n++);
        if (char1 < '\ud800' || char1 > '\udfff') {
            return char1;
        }
        if (char1 > '\udbff') {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + char1 + "' with value " + (int)char1 + " at index " + (n - 1));
        }
        if (n == n2) {
            return -char1;
        }
        final char char2 = charSequence.charAt(n);
        if (Character.isLowSurrogate(char2)) {
            return Character.toCodePoint(char1, char2);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + char2 + "' with value " + (int)char2 + " at index " + n);
    }
    
    private static final char[] growBuffer(final char[] array, final int n, final int n2) {
        final char[] array2 = new char[n2];
        if (n > 0) {
            System.arraycopy(array, 0, array2, 0, n);
        }
        return array2;
    }
    
    static {
        $assertionsDisabled = !UnicodeEscaper.class.desiredAssertionStatus();
        DEST_TL = new ThreadLocal() {
            @Override
            protected char[] initialValue() {
                return new char[1024];
            }
            
            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
    }
}
