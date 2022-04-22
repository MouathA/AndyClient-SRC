package com.google.common.escape;

import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public abstract class ArrayBasedCharEscaper extends CharEscaper
{
    private final char[][] replacements;
    private final int replacementsLength;
    private final char safeMin;
    private final char safeMax;
    
    protected ArrayBasedCharEscaper(final Map map, final char c, final char c2) {
        this(ArrayBasedEscaperMap.create(map), c, c2);
    }
    
    protected ArrayBasedCharEscaper(final ArrayBasedEscaperMap arrayBasedEscaperMap, final char c, final char c2) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.replacements = arrayBasedEscaperMap.getReplacementArray();
        this.replacementsLength = this.replacements.length;
        this.safeMin = '\uffff';
        this.safeMax = '\0';
    }
    
    @Override
    public final String escape(final String s) {
        Preconditions.checkNotNull(s);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if ((char1 < this.replacementsLength && this.replacements[char1] != null) || char1 > this.safeMax || char1 < this.safeMin) {
                return this.escapeSlow(s, 0);
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    @Override
    protected final char[] escape(final char c) {
        if (c < this.replacementsLength) {
            final char[] array = this.replacements[c];
            if (array != null) {
                return array;
            }
        }
        if (c >= this.safeMin && c <= this.safeMax) {
            return null;
        }
        return this.escapeUnsafe(c);
    }
    
    protected abstract char[] escapeUnsafe(final char p0);
}
