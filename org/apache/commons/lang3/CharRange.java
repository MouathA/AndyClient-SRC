package org.apache.commons.lang3;

import java.io.*;
import java.util.*;

final class CharRange implements Iterable, Serializable
{
    private static final long serialVersionUID = 8270183163158333422L;
    private final char start;
    private final char end;
    private final boolean negated;
    private transient String iToString;
    
    private CharRange(char start, char end, final boolean negated) {
        if (start > end) {
            final char c = start;
            start = end;
            end = c;
        }
        this.start = start;
        this.end = end;
        this.negated = negated;
    }
    
    public static CharRange is(final char c) {
        return new CharRange(c, c, false);
    }
    
    public static CharRange isNot(final char c) {
        return new CharRange(c, c, true);
    }
    
    public static CharRange isIn(final char c, final char c2) {
        return new CharRange(c, c2, false);
    }
    
    public static CharRange isNotIn(final char c, final char c2) {
        return new CharRange(c, c2, true);
    }
    
    public char getStart() {
        return this.start;
    }
    
    public char getEnd() {
        return this.end;
    }
    
    public boolean isNegated() {
        return this.negated;
    }
    
    public boolean contains(final char c) {
        return (c >= this.start && c <= this.end) != this.negated;
    }
    
    public boolean contains(final CharRange charRange) {
        if (charRange == null) {
            throw new IllegalArgumentException("The Range must not be null");
        }
        if (this.negated) {
            if (charRange.negated) {
                return this.start >= charRange.start && this.end <= charRange.end;
            }
            return charRange.end < this.start || charRange.start > this.end;
        }
        else {
            if (charRange.negated) {
                return this.start == '\0' && this.end == '\uffff';
            }
            return this.start <= charRange.start && this.end >= charRange.end;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CharRange)) {
            return false;
        }
        final CharRange charRange = (CharRange)o;
        return this.start == charRange.start && this.end == charRange.end && this.negated == charRange.negated;
    }
    
    @Override
    public int hashCode() {
        return 'S' + this.start + '\u0007' * this.end + (this.negated ? 1 : 0);
    }
    
    @Override
    public String toString() {
        if (this.iToString == null) {
            final StringBuilder sb = new StringBuilder(4);
            if (this.isNegated()) {
                sb.append('^');
            }
            sb.append(this.start);
            if (this.start != this.end) {
                sb.append('-');
                sb.append(this.end);
            }
            this.iToString = sb.toString();
        }
        return this.iToString;
    }
    
    @Override
    public Iterator iterator() {
        return new CharacterIterator(this, null);
    }
    
    static boolean access$100(final CharRange charRange) {
        return charRange.negated;
    }
    
    static char access$200(final CharRange charRange) {
        return charRange.start;
    }
    
    static char access$300(final CharRange charRange) {
        return charRange.end;
    }
    
    private static class CharacterIterator implements Iterator
    {
        private char current;
        private final CharRange range;
        private boolean hasNext;
        
        private CharacterIterator(final CharRange range) {
            this.range = range;
            this.hasNext = true;
            if (CharRange.access$100(this.range)) {
                if (CharRange.access$200(this.range) == '\0') {
                    if (CharRange.access$300(this.range) == '\uffff') {
                        this.hasNext = false;
                    }
                    else {
                        this.current = (char)(CharRange.access$300(this.range) + '\u0001');
                    }
                }
                else {
                    this.current = '\0';
                }
            }
            else {
                this.current = CharRange.access$200(this.range);
            }
        }
        
        private void prepareNext() {
            if (CharRange.access$100(this.range)) {
                if (this.current == '\uffff') {
                    this.hasNext = false;
                }
                else if (this.current + '\u0001' == CharRange.access$200(this.range)) {
                    if (CharRange.access$300(this.range) == '\uffff') {
                        this.hasNext = false;
                    }
                    else {
                        this.current = (char)(CharRange.access$300(this.range) + '\u0001');
                    }
                }
                else {
                    ++this.current;
                }
            }
            else if (this.current < CharRange.access$300(this.range)) {
                ++this.current;
            }
            else {
                this.hasNext = false;
            }
        }
        
        @Override
        public boolean hasNext() {
            return this.hasNext;
        }
        
        @Override
        public Character next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            final char current = this.current;
            this.prepareNext();
            return current;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        CharacterIterator(final CharRange charRange, final CharRange$1 object) {
            this(charRange);
        }
    }
}
