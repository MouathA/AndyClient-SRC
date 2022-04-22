package com.ibm.icu.text;

public abstract class UnicodeFilter implements UnicodeMatcher
{
    public abstract boolean contains(final int p0);
    
    public int matches(final Replaceable replaceable, final int[] array, final int n, final boolean b) {
        final int char32At;
        if (array[0] < n && this.contains(char32At = replaceable.char32At(array[0]))) {
            final int n2 = 0;
            array[n2] += UTF16.getCharCount(char32At);
            return 2;
        }
        if (array[0] > n && this.contains(replaceable.char32At(array[0]))) {
            final int n3 = 0;
            --array[n3];
            if (array[0] >= 0) {
                final int n4 = 0;
                array[n4] -= UTF16.getCharCount(replaceable.char32At(array[0])) - 1;
            }
            return 2;
        }
        if (b && array[0] == n) {
            return 1;
        }
        return 0;
    }
    
    @Deprecated
    protected UnicodeFilter() {
    }
}
