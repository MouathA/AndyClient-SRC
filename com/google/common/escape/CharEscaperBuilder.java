package com.google.common.escape;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtCompatible
public final class CharEscaperBuilder
{
    private final Map map;
    private int max;
    
    public CharEscaperBuilder() {
        this.max = -1;
        this.map = new HashMap();
    }
    
    public CharEscaperBuilder addEscape(final char max, final String s) {
        this.map.put(max, Preconditions.checkNotNull(s));
        if (max > this.max) {
            this.max = max;
        }
        return this;
    }
    
    public CharEscaperBuilder addEscapes(final char[] array, final String s) {
        Preconditions.checkNotNull(s);
        while (0 < array.length) {
            this.addEscape(array[0], s);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public char[][] toArray() {
        final char[][] array = new char[this.max + 1][];
        for (final Map.Entry<Character, V> entry : this.map.entrySet()) {
            array[(char)entry.getKey()] = ((String)entry.getValue()).toCharArray();
        }
        return array;
    }
    
    public Escaper toEscaper() {
        return new CharArrayDecorator(this.toArray());
    }
    
    private static class CharArrayDecorator extends CharEscaper
    {
        private final char[][] replacements;
        private final int replaceLength;
        
        CharArrayDecorator(final char[][] replacements) {
            this.replacements = replacements;
            this.replaceLength = replacements.length;
        }
        
        @Override
        public String escape(final String s) {
            while (0 < s.length()) {
                final char char1 = s.charAt(0);
                if (char1 < this.replacements.length && this.replacements[char1] != null) {
                    return this.escapeSlow(s, 0);
                }
                int n = 0;
                ++n;
            }
            return s;
        }
        
        @Override
        protected char[] escape(final char c) {
            return (char[])((c < this.replaceLength) ? this.replacements[c] : null);
        }
    }
}
