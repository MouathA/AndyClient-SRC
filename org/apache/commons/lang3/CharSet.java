package org.apache.commons.lang3;

import java.io.*;
import java.util.*;

public class CharSet implements Serializable
{
    private static final long serialVersionUID = 5947847346149275958L;
    public static final CharSet EMPTY;
    public static final CharSet ASCII_ALPHA;
    public static final CharSet ASCII_ALPHA_LOWER;
    public static final CharSet ASCII_ALPHA_UPPER;
    public static final CharSet ASCII_NUMERIC;
    protected static final Map COMMON;
    private final Set set;
    
    public static CharSet getInstance(final String... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 1) {
            final CharSet set = CharSet.COMMON.get(array[0]);
            if (set != null) {
                return set;
            }
        }
        return new CharSet(array);
    }
    
    protected CharSet(final String... array) {
        this.set = Collections.synchronizedSet(new HashSet<Object>());
        while (0 < array.length) {
            this.add(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    protected void add(final String s) {
        if (s == null) {
            return;
        }
        final int length = s.length();
        while (0 < length) {
            final int n = length - 0;
            if (n >= 4 && s.charAt(0) == '^' && s.charAt(2) == '-') {
                this.set.add(CharRange.isNotIn(s.charAt(1), s.charAt(3)));
                final int n2;
                n2 += 4;
            }
            else if (n >= 3 && s.charAt(1) == '-') {
                this.set.add(CharRange.isIn(s.charAt(0), s.charAt(2)));
                final int n2;
                n2 += 3;
            }
            else if (n >= 2 && s.charAt(0) == '^') {
                this.set.add(CharRange.isNot(s.charAt(1)));
                final int n2;
                n2 += 2;
            }
            else {
                this.set.add(CharRange.is(s.charAt(0)));
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    CharRange[] getCharRanges() {
        return this.set.toArray(new CharRange[this.set.size()]);
    }
    
    public boolean contains(final char c) {
        final Iterator<CharRange> iterator = this.set.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof CharSet && this.set.equals(((CharSet)o).set));
    }
    
    @Override
    public int hashCode() {
        return 89 + this.set.hashCode();
    }
    
    @Override
    public String toString() {
        return this.set.toString();
    }
    
    static {
        EMPTY = new CharSet(new String[] { null });
        ASCII_ALPHA = new CharSet(new String[] { "a-zA-Z" });
        ASCII_ALPHA_LOWER = new CharSet(new String[] { "a-z" });
        ASCII_ALPHA_UPPER = new CharSet(new String[] { "A-Z" });
        ASCII_NUMERIC = new CharSet(new String[] { "0-9" });
        (COMMON = Collections.synchronizedMap(new HashMap<Object, Object>())).put(null, CharSet.EMPTY);
        CharSet.COMMON.put("", CharSet.EMPTY);
        CharSet.COMMON.put("a-zA-Z", CharSet.ASCII_ALPHA);
        CharSet.COMMON.put("A-Za-z", CharSet.ASCII_ALPHA);
        CharSet.COMMON.put("a-z", CharSet.ASCII_ALPHA_LOWER);
        CharSet.COMMON.put("A-Z", CharSet.ASCII_ALPHA_UPPER);
        CharSet.COMMON.put("0-9", CharSet.ASCII_NUMERIC);
    }
}
