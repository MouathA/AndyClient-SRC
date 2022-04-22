package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;

public abstract class UCharacterIterator implements Cloneable, UForwardCharacterIterator
{
    protected UCharacterIterator() {
    }
    
    public static final UCharacterIterator getInstance(final Replaceable replaceable) {
        return new ReplaceableUCharacterIterator(replaceable);
    }
    
    public static final UCharacterIterator getInstance(final String s) {
        return new ReplaceableUCharacterIterator(s);
    }
    
    public static final UCharacterIterator getInstance(final char[] array) {
        return getInstance(array, 0, array.length);
    }
    
    public static final UCharacterIterator getInstance(final char[] array, final int n, final int n2) {
        return new UCharArrayIterator(array, n, n2);
    }
    
    public static final UCharacterIterator getInstance(final StringBuffer sb) {
        return new ReplaceableUCharacterIterator(sb);
    }
    
    public static final UCharacterIterator getInstance(final CharacterIterator characterIterator) {
        return new CharacterIteratorWrapper(characterIterator);
    }
    
    public CharacterIterator getCharacterIterator() {
        return new UCharacterIteratorWrapper(this);
    }
    
    public abstract int current();
    
    public int currentCodePoint() {
        final int current = this.current();
        if (UTF16.isLeadSurrogate((char)current)) {
            this.next();
            final int current2 = this.current();
            this.previous();
            if (UTF16.isTrailSurrogate((char)current2)) {
                return UCharacterProperty.getRawSupplementary((char)current, (char)current2);
            }
        }
        return current;
    }
    
    public abstract int getLength();
    
    public abstract int getIndex();
    
    public abstract int next();
    
    public int nextCodePoint() {
        final int next = this.next();
        if (UTF16.isLeadSurrogate((char)next)) {
            final int next2 = this.next();
            if (UTF16.isTrailSurrogate((char)next2)) {
                return UCharacterProperty.getRawSupplementary((char)next, (char)next2);
            }
            if (next2 != -1) {
                this.previous();
            }
        }
        return next;
    }
    
    public abstract int previous();
    
    public int previousCodePoint() {
        final int previous = this.previous();
        if (UTF16.isTrailSurrogate((char)previous)) {
            final int previous2 = this.previous();
            if (UTF16.isLeadSurrogate((char)previous2)) {
                return UCharacterProperty.getRawSupplementary((char)previous2, (char)previous);
            }
            if (previous2 != -1) {
                this.next();
            }
        }
        return previous;
    }
    
    public abstract void setIndex(final int p0);
    
    public void setToLimit() {
        this.setIndex(this.getLength());
    }
    
    public void setToStart() {
        this.setIndex(0);
    }
    
    public abstract int getText(final char[] p0, final int p1);
    
    public final int getText(final char[] array) {
        return this.getText(array, 0);
    }
    
    public String getText() {
        final char[] array = new char[this.getLength()];
        this.getText(array);
        return new String(array);
    }
    
    public int moveIndex(final int n) {
        final int max = Math.max(0, Math.min(this.getIndex() + n, this.getLength()));
        this.setIndex(max);
        return max;
    }
    
    public int moveCodePointIndex(int n) {
        if (n > 0) {
            while (n > 0 && this.nextCodePoint() != -1) {
                --n;
            }
        }
        else {
            while (n < 0 && this.previousCodePoint() != -1) {
                ++n;
            }
        }
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.getIndex();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
