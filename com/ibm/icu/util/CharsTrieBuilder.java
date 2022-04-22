package com.ibm.icu.util;

import java.nio.*;

public final class CharsTrieBuilder extends StringTrieBuilder
{
    private final char[] intUnits;
    private char[] chars;
    private int charsLength;
    static final boolean $assertionsDisabled;
    
    public CharsTrieBuilder() {
        this.intUnits = new char[3];
    }
    
    public CharsTrieBuilder add(final CharSequence charSequence, final int n) {
        this.addImpl(charSequence, n);
        return this;
    }
    
    public CharsTrie build(final Option option) {
        return new CharsTrie(this.buildCharSequence(option), 0);
    }
    
    public CharSequence buildCharSequence(final Option option) {
        this.buildChars(option);
        return CharBuffer.wrap(this.chars, this.chars.length - this.charsLength, this.charsLength);
    }
    
    private void buildChars(final Option option) {
        if (this.chars == null) {
            this.chars = new char[1024];
        }
        this.buildImpl(option);
    }
    
    public CharsTrieBuilder clear() {
        this.clearImpl();
        this.chars = null;
        this.charsLength = 0;
        return this;
    }
    
    @Override
    @Deprecated
    protected boolean matchNodesCanHaveValues() {
        return true;
    }
    
    @Override
    @Deprecated
    protected int getMaxBranchLinearSubNodeLength() {
        return 5;
    }
    
    @Override
    @Deprecated
    protected int getMinLinearMatch() {
        return 48;
    }
    
    @Override
    @Deprecated
    protected int getMaxLinearMatchLength() {
        return 16;
    }
    
    private void ensureCapacity(final int n) {
        if (n > this.chars.length) {
            int i = this.chars.length;
            do {
                i *= 2;
            } while (i <= n);
            final char[] chars = new char[i];
            System.arraycopy(this.chars, this.chars.length - this.charsLength, chars, chars.length - this.charsLength, this.charsLength);
            this.chars = chars;
        }
    }
    
    @Override
    @Deprecated
    protected int write(final int n) {
        final int charsLength = this.charsLength + 1;
        this.ensureCapacity(charsLength);
        this.charsLength = charsLength;
        this.chars[this.chars.length - this.charsLength] = (char)n;
        return this.charsLength;
    }
    
    @Override
    @Deprecated
    protected int write(int n, int i) {
        final int charsLength = this.charsLength + i;
        this.ensureCapacity(charsLength);
        this.charsLength = charsLength;
        int n2 = this.chars.length - this.charsLength;
        while (i > 0) {
            this.chars[n2++] = this.strings.charAt(n++);
            --i;
        }
        return this.charsLength;
    }
    
    private int write(final char[] array, final int n) {
        final int charsLength = this.charsLength + n;
        this.ensureCapacity(charsLength);
        this.charsLength = charsLength;
        System.arraycopy(array, 0, this.chars, this.chars.length - this.charsLength, n);
        return this.charsLength;
    }
    
    @Override
    @Deprecated
    protected int writeValueAndFinal(final int n, final boolean b) {
        if (0 <= n && n <= 16383) {
            return this.write(n | (b ? 32768 : 0));
        }
        if (n < 0 || n > 1073676287) {
            this.intUnits[0] = '\u7fff';
            this.intUnits[1] = (char)(n >> 16);
            this.intUnits[2] = (char)n;
        }
        else {
            this.intUnits[0] = (char)(16384 + (n >> 16));
            this.intUnits[1] = (char)n;
        }
        this.intUnits[0] |= (b ? '\u8000' : '\0');
        return this.write(this.intUnits, 2);
    }
    
    @Override
    @Deprecated
    protected int writeValueAndType(final boolean b, final int n, final int n2) {
        if (!b) {
            return this.write(n2);
        }
        if (n < 0 || n > 16646143) {
            this.intUnits[0] = '\u7fc0';
            this.intUnits[1] = (char)(n >> 16);
            this.intUnits[2] = (char)n;
        }
        else if (n <= 255) {
            this.intUnits[0] = (char)(n + 1 << 6);
        }
        else {
            this.intUnits[0] = (char)(16448 + (n >> 10 & 0x7FC0));
            this.intUnits[1] = (char)n;
        }
        final char[] intUnits = this.intUnits;
        final int n3 = 0;
        intUnits[n3] |= (char)n2;
        return this.write(this.intUnits, 2);
    }
    
    @Override
    @Deprecated
    protected int writeDeltaTo(final int n) {
        final int n2 = this.charsLength - n;
        assert n2 >= 0;
        if (n2 <= 64511) {
            return this.write(n2);
        }
        if (n2 <= 67043327) {
            this.intUnits[0] = (char)(64512 + (n2 >> 16));
        }
        else {
            this.intUnits[0] = '\uffff';
            this.intUnits[1] = (char)(n2 >> 16);
        }
        final char[] intUnits = this.intUnits;
        final int n3 = 2;
        int n4 = 0;
        ++n4;
        intUnits[n3] = (char)n2;
        return this.write(this.intUnits, 2);
    }
    
    static {
        $assertionsDisabled = !CharsTrieBuilder.class.desiredAssertionStatus();
    }
}
