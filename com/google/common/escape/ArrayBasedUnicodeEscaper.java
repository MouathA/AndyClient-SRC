package com.google.common.escape;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper
{
    private final char[][] replacements;
    private final int replacementsLength;
    private final int safeMin;
    private final int safeMax;
    private final char safeMinChar;
    private final char safeMaxChar;
    
    protected ArrayBasedUnicodeEscaper(final Map map, final int n, final int n2, @Nullable final String s) {
        this(ArrayBasedEscaperMap.create(map), n, n2, s);
    }
    
    protected ArrayBasedUnicodeEscaper(final ArrayBasedEscaperMap arrayBasedEscaperMap, final int n, final int n2, @Nullable final String s) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.replacements = arrayBasedEscaperMap.getReplacementArray();
        this.replacementsLength = this.replacements.length;
        if (-1 < Integer.MAX_VALUE) {}
        this.safeMin = Integer.MAX_VALUE;
        this.safeMax = -1;
        if (Integer.MAX_VALUE >= 55296) {
            this.safeMinChar = '\uffff';
            this.safeMaxChar = '\0';
        }
        else {
            this.safeMinChar = (char)Integer.MAX_VALUE;
            this.safeMaxChar = (char)Math.min(-1, 55295);
        }
    }
    
    @Override
    public final String escape(final String s) {
        Preconditions.checkNotNull(s);
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if ((char1 < this.replacementsLength && this.replacements[char1] != null) || char1 > this.safeMaxChar || char1 < this.safeMinChar) {
                return this.escapeSlow(s, 0);
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    @Override
    protected final int nextEscapeIndex(final CharSequence charSequence, int i, final int n) {
        while (i < n) {
            final char char1 = charSequence.charAt(i);
            if ((char1 < this.replacementsLength && this.replacements[char1] != null) || char1 > this.safeMaxChar) {
                break;
            }
            if (char1 < this.safeMinChar) {
                break;
            }
            ++i;
        }
        return i;
    }
    
    @Override
    protected final char[] escape(final int n) {
        if (n < this.replacementsLength) {
            final char[] array = this.replacements[n];
            if (array != null) {
                return array;
            }
        }
        if (n >= this.safeMin && n <= this.safeMax) {
            return null;
        }
        return this.escapeUnsafe(n);
    }
    
    protected abstract char[] escapeUnsafe(final int p0);
}
