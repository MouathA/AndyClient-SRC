package org.apache.commons.lang3.text;

import org.apache.commons.lang3.*;
import java.util.*;

public abstract class StrMatcher
{
    private static final StrMatcher COMMA_MATCHER;
    private static final StrMatcher TAB_MATCHER;
    private static final StrMatcher SPACE_MATCHER;
    private static final StrMatcher SPLIT_MATCHER;
    private static final StrMatcher TRIM_MATCHER;
    private static final StrMatcher SINGLE_QUOTE_MATCHER;
    private static final StrMatcher DOUBLE_QUOTE_MATCHER;
    private static final StrMatcher QUOTE_MATCHER;
    private static final StrMatcher NONE_MATCHER;
    
    public static StrMatcher commaMatcher() {
        return StrMatcher.COMMA_MATCHER;
    }
    
    public static StrMatcher tabMatcher() {
        return StrMatcher.TAB_MATCHER;
    }
    
    public static StrMatcher spaceMatcher() {
        return StrMatcher.SPACE_MATCHER;
    }
    
    public static StrMatcher splitMatcher() {
        return StrMatcher.SPLIT_MATCHER;
    }
    
    public static StrMatcher trimMatcher() {
        return StrMatcher.TRIM_MATCHER;
    }
    
    public static StrMatcher singleQuoteMatcher() {
        return StrMatcher.SINGLE_QUOTE_MATCHER;
    }
    
    public static StrMatcher doubleQuoteMatcher() {
        return StrMatcher.DOUBLE_QUOTE_MATCHER;
    }
    
    public static StrMatcher quoteMatcher() {
        return StrMatcher.QUOTE_MATCHER;
    }
    
    public static StrMatcher noneMatcher() {
        return StrMatcher.NONE_MATCHER;
    }
    
    public static StrMatcher charMatcher(final char c) {
        return new CharMatcher(c);
    }
    
    public static StrMatcher charSetMatcher(final char... array) {
        if (array == null || array.length == 0) {
            return StrMatcher.NONE_MATCHER;
        }
        if (array.length == 1) {
            return new CharMatcher(array[0]);
        }
        return new CharSetMatcher(array);
    }
    
    public static StrMatcher charSetMatcher(final String s) {
        if (StringUtils.isEmpty(s)) {
            return StrMatcher.NONE_MATCHER;
        }
        if (s.length() == 1) {
            return new CharMatcher(s.charAt(0));
        }
        return new CharSetMatcher(s.toCharArray());
    }
    
    public static StrMatcher stringMatcher(final String s) {
        if (StringUtils.isEmpty(s)) {
            return StrMatcher.NONE_MATCHER;
        }
        return new StringMatcher(s);
    }
    
    protected StrMatcher() {
    }
    
    public abstract int isMatch(final char[] p0, final int p1, final int p2, final int p3);
    
    public int isMatch(final char[] array, final int n) {
        return this.isMatch(array, n, 0, array.length);
    }
    
    static {
        COMMA_MATCHER = new CharMatcher(',');
        TAB_MATCHER = new CharMatcher('\t');
        SPACE_MATCHER = new CharMatcher(' ');
        SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
        TRIM_MATCHER = new TrimMatcher();
        SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
        DOUBLE_QUOTE_MATCHER = new CharMatcher('\"');
        QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
        NONE_MATCHER = new NoMatcher();
    }
    
    static final class TrimMatcher extends StrMatcher
    {
        @Override
        public int isMatch(final char[] array, final int n, final int n2, final int n3) {
            return (array[n] <= ' ') ? 1 : 0;
        }
    }
    
    static final class NoMatcher extends StrMatcher
    {
        @Override
        public int isMatch(final char[] array, final int n, final int n2, final int n3) {
            return 0;
        }
    }
    
    static final class StringMatcher extends StrMatcher
    {
        private final char[] chars;
        
        StringMatcher(final String s) {
            this.chars = s.toCharArray();
        }
        
        @Override
        public int isMatch(final char[] array, int n, final int n2, final int n3) {
            final int length = this.chars.length;
            if (n + length > n3) {
                return 0;
            }
            while (0 < this.chars.length) {
                if (this.chars[0] != array[n]) {
                    return 0;
                }
                int n4 = 0;
                ++n4;
                ++n;
            }
            return length;
        }
    }
    
    static final class CharMatcher extends StrMatcher
    {
        private final char ch;
        
        CharMatcher(final char ch) {
            this.ch = ch;
        }
        
        @Override
        public int isMatch(final char[] array, final int n, final int n2, final int n3) {
            return (this.ch == array[n]) ? 1 : 0;
        }
    }
    
    static final class CharSetMatcher extends StrMatcher
    {
        private final char[] chars;
        
        CharSetMatcher(final char[] array) {
            Arrays.sort(this.chars = array.clone());
        }
        
        @Override
        public int isMatch(final char[] array, final int n, final int n2, final int n3) {
            return (Arrays.binarySearch(this.chars, array[n]) >= 0) ? 1 : 0;
        }
    }
}
