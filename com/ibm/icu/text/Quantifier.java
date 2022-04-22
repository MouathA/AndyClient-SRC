package com.ibm.icu.text;

import com.ibm.icu.impl.*;

class Quantifier implements UnicodeMatcher
{
    private UnicodeMatcher matcher;
    private int minCount;
    private int maxCount;
    public static final int MAX = Integer.MAX_VALUE;
    
    public Quantifier(final UnicodeMatcher matcher, final int minCount, final int maxCount) {
        if (matcher == null || minCount < 0 || maxCount < 0 || minCount > maxCount) {
            throw new IllegalArgumentException();
        }
        this.matcher = matcher;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }
    
    public int matches(final Replaceable replaceable, final int[] array, final int n, final boolean b) {
        final int n2 = array[0];
        while (0 < this.maxCount) {
            final int n3 = array[0];
            final int matches = this.matcher.matches(replaceable, array, n, b);
            if (matches == 2) {
                int n4 = 0;
                ++n4;
                if (n3 == array[0]) {
                    break;
                }
                continue;
            }
            else {
                if (b && matches == 1) {
                    return 1;
                }
                break;
            }
        }
        if (b && array[0] == n) {
            return 1;
        }
        if (0 >= this.minCount) {
            return 2;
        }
        array[0] = n2;
        return 0;
    }
    
    public String toPattern(final boolean b) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.matcher.toPattern(b));
        if (this.minCount == 0) {
            if (this.maxCount == 1) {
                return sb.append('?').toString();
            }
            if (this.maxCount == Integer.MAX_VALUE) {
                return sb.append('*').toString();
            }
        }
        else if (this.minCount == 1 && this.maxCount == Integer.MAX_VALUE) {
            return sb.append('+').toString();
        }
        sb.append('{');
        sb.append(Utility.hex(this.minCount, 1));
        sb.append(',');
        if (this.maxCount != Integer.MAX_VALUE) {
            sb.append(Utility.hex(this.maxCount, 1));
        }
        sb.append('}');
        return sb.toString();
    }
    
    public boolean matchesIndexValue(final int n) {
        return this.minCount == 0 || this.matcher.matchesIndexValue(n);
    }
    
    public void addMatchSetTo(final UnicodeSet set) {
        if (this.maxCount > 0) {
            this.matcher.addMatchSetTo(set);
        }
    }
}
