package com.google.common.base;

import com.google.common.annotations.*;
import java.util.*;

@GwtIncompatible("no precomputation is done in GWT")
final class SmallCharMatcher extends FastMatcher
{
    static final int MAX_SIZE = 1023;
    private final char[] table;
    private final boolean containsZero;
    private final long filter;
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5;
    
    private SmallCharMatcher(final char[] table, final long filter, final boolean containsZero, final String s) {
        super(s);
        this.table = table;
        this.filter = filter;
        this.containsZero = containsZero;
    }
    
    static int smear(final int n) {
        return 461845907 * Integer.rotateLeft(n * -862048943, 15);
    }
    
    private boolean checkFilter(final int n) {
        return 0x1L == (0x1L & this.filter >> n);
    }
    
    @VisibleForTesting
    static int chooseTableSize(final int n) {
        if (n == 1) {
            return 2;
        }
        int n2;
        for (n2 = Integer.highestOneBit(n - 1) << 1; n2 * 0.5 < n; n2 <<= 1) {}
        return n2;
    }
    
    static CharMatcher from(final BitSet set, final String s) {
        long n = 0L;
        final int cardinality = set.cardinality();
        final boolean value = set.get(0);
        final char[] array = new char[chooseTableSize(cardinality)];
        final int n2 = array.length - 1;
        for (int i = set.nextSetBit(0); i != -1; i = set.nextSetBit(i + 1)) {
            n |= 1L << i;
            int n3;
            for (n3 = (smear(i) & n2); array[n3] != '\0'; n3 = (n3 + 1 & n2)) {}
            array[n3] = (char)i;
        }
        return new SmallCharMatcher(array, n, value, s);
    }
    
    @Override
    public boolean matches(final char c) {
        if (c == '\0') {
            return this.containsZero;
        }
        if (!this.checkFilter(c)) {
            return false;
        }
        final int n = this.table.length - 1;
        int n3;
        final int n2 = n3 = (smear(c) & n);
        while (this.table[n3] != '\0') {
            if (this.table[n3] == c) {
                return true;
            }
            n3 = (n3 + 1 & n);
            if (n3 == n2) {
                return false;
            }
        }
        return false;
    }
    
    @Override
    void setBits(final BitSet set) {
        if (this.containsZero) {
            set.set(0);
        }
        final char[] table = this.table;
        while (0 < table.length) {
            final char c = table[0];
            if (c != '\0') {
                set.set(c);
            }
            int n = 0;
            ++n;
        }
    }
}
