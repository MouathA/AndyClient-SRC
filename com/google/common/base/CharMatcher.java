package com.google.common.base;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@Beta
@GwtCompatible(emulated = true)
public abstract class CharMatcher implements Predicate
{
    public static final CharMatcher BREAKING_WHITESPACE;
    public static final CharMatcher ASCII;
    private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
    private static final String NINES;
    public static final CharMatcher DIGIT;
    public static final CharMatcher JAVA_DIGIT;
    public static final CharMatcher JAVA_LETTER;
    public static final CharMatcher JAVA_LETTER_OR_DIGIT;
    public static final CharMatcher JAVA_UPPER_CASE;
    public static final CharMatcher JAVA_LOWER_CASE;
    public static final CharMatcher JAVA_ISO_CONTROL;
    public static final CharMatcher INVISIBLE;
    public static final CharMatcher SINGLE_WIDTH;
    public static final CharMatcher ANY;
    public static final CharMatcher NONE;
    final String description;
    private static final int DISTINCT_CHARS = 65536;
    static final String WHITESPACE_TABLE = "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f \f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
    static final int WHITESPACE_MULTIPLIER = 1682554634;
    static final int WHITESPACE_SHIFT;
    public static final CharMatcher WHITESPACE;
    
    private static String showCharacter(char c) {
        final String s = "0123456789ABCDEF";
        final char[] array = { '\\', 'u', '\0', '\0', '\0', '\0' };
        while (true) {
            array[5] = s.charAt(c & '\u000f');
            c >>= 4;
            int n = 0;
            ++n;
        }
    }
    
    public static CharMatcher is(final char c) {
        return new FastMatcher("CharMatcher.is('" + showCharacter(c) + "')", c) {
            final char val$match;
            
            @Override
            public boolean matches(final char c) {
                return c == this.val$match;
            }
            
            @Override
            public String replaceFrom(final CharSequence charSequence, final char c) {
                return charSequence.toString().replace(this.val$match, c);
            }
            
            @Override
            public CharMatcher and(final CharMatcher charMatcher) {
                return charMatcher.matches(this.val$match) ? this : CharMatcher$9.NONE;
            }
            
            @Override
            public CharMatcher or(final CharMatcher charMatcher) {
                return charMatcher.matches(this.val$match) ? charMatcher : super.or(charMatcher);
            }
            
            @Override
            public CharMatcher negate() {
                return CharMatcher.isNot(this.val$match);
            }
            
            @GwtIncompatible("java.util.BitSet")
            @Override
            void setBits(final BitSet set) {
                set.set(this.val$match);
            }
        };
    }
    
    public static CharMatcher isNot(final char c) {
        return new FastMatcher("CharMatcher.isNot('" + showCharacter(c) + "')", c) {
            final char val$match;
            
            @Override
            public boolean matches(final char c) {
                return c != this.val$match;
            }
            
            @Override
            public CharMatcher and(final CharMatcher charMatcher) {
                return charMatcher.matches(this.val$match) ? super.and(charMatcher) : charMatcher;
            }
            
            @Override
            public CharMatcher or(final CharMatcher charMatcher) {
                return charMatcher.matches(this.val$match) ? CharMatcher$10.ANY : this;
            }
            
            @GwtIncompatible("java.util.BitSet")
            @Override
            void setBits(final BitSet set) {
                set.set(0, this.val$match);
                set.set(this.val$match + '\u0001', 65536);
            }
            
            @Override
            public CharMatcher negate() {
                return CharMatcher.is(this.val$match);
            }
        };
    }
    
    public static CharMatcher anyOf(final CharSequence charSequence) {
        switch (charSequence.length()) {
            case 0: {
                return CharMatcher.NONE;
            }
            case 1: {
                return is(charSequence.charAt(0));
            }
            case 2: {
                return isEither(charSequence.charAt(0), charSequence.charAt(1));
            }
            default: {
                final char[] charArray = charSequence.toString().toCharArray();
                Arrays.sort(charArray);
                final StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
                final char[] array = charArray;
                while (0 < array.length) {
                    sb.append(showCharacter(array[0]));
                    int n = 0;
                    ++n;
                }
                sb.append("\")");
                return new CharMatcher(sb.toString(), charArray) {
                    final char[] val$chars;
                    
                    @Override
                    public boolean matches(final char c) {
                        return Arrays.binarySearch(this.val$chars, c) >= 0;
                    }
                    
                    @GwtIncompatible("java.util.BitSet")
                    @Override
                    void setBits(final BitSet set) {
                        final char[] val$chars = this.val$chars;
                        while (0 < val$chars.length) {
                            set.set(val$chars[0]);
                            int n = 0;
                            ++n;
                        }
                    }
                    
                    @Override
                    public boolean apply(final Object o) {
                        return super.apply((Character)o);
                    }
                };
            }
        }
    }
    
    private static CharMatcher isEither(final char c, final char c2) {
        return new FastMatcher("CharMatcher.anyOf(\"" + showCharacter(c) + showCharacter(c2) + "\")", c, c2) {
            final char val$match1;
            final char val$match2;
            
            @Override
            public boolean matches(final char c) {
                return c == this.val$match1 || c == this.val$match2;
            }
            
            @GwtIncompatible("java.util.BitSet")
            @Override
            void setBits(final BitSet set) {
                set.set(this.val$match1);
                set.set(this.val$match2);
            }
        };
    }
    
    public static CharMatcher noneOf(final CharSequence charSequence) {
        return anyOf(charSequence).negate();
    }
    
    public static CharMatcher inRange(final char c, final char c2) {
        Preconditions.checkArgument(c2 >= c);
        return inRange(c, c2, "CharMatcher.inRange('" + showCharacter(c) + "', '" + showCharacter(c2) + "')");
    }
    
    static CharMatcher inRange(final char c, final char c2, final String s) {
        return new FastMatcher(s, c, c2) {
            final char val$startInclusive;
            final char val$endInclusive;
            
            @Override
            public boolean matches(final char c) {
                return this.val$startInclusive <= c && c <= this.val$endInclusive;
            }
            
            @GwtIncompatible("java.util.BitSet")
            @Override
            void setBits(final BitSet set) {
                set.set(this.val$startInclusive, this.val$endInclusive + '\u0001');
            }
        };
    }
    
    public static CharMatcher forPredicate(final Predicate predicate) {
        Preconditions.checkNotNull(predicate);
        if (predicate instanceof CharMatcher) {
            return (CharMatcher)predicate;
        }
        return new CharMatcher("CharMatcher.forPredicate(" + predicate + ")", predicate) {
            final Predicate val$predicate;
            
            @Override
            public boolean matches(final char c) {
                return this.val$predicate.apply(c);
            }
            
            @Override
            public boolean apply(final Character c) {
                return this.val$predicate.apply(Preconditions.checkNotNull(c));
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.apply((Character)o);
            }
        };
    }
    
    CharMatcher(final String description) {
        this.description = description;
    }
    
    protected CharMatcher() {
        this.description = super.toString();
    }
    
    public abstract boolean matches(final char p0);
    
    public CharMatcher negate() {
        return new NegatedMatcher(this);
    }
    
    public CharMatcher and(final CharMatcher charMatcher) {
        return new And(this, (CharMatcher)Preconditions.checkNotNull(charMatcher));
    }
    
    public CharMatcher or(final CharMatcher charMatcher) {
        return new Or(this, (CharMatcher)Preconditions.checkNotNull(charMatcher));
    }
    
    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }
    
    CharMatcher withToString(final String s) {
        throw new UnsupportedOperationException();
    }
    
    @GwtIncompatible("java.util.BitSet")
    CharMatcher precomputedInternal() {
        final BitSet bits = new BitSet();
        this.setBits(bits);
        final int cardinality = bits.cardinality();
        if (cardinality * 2 <= 65536) {
            return precomputedPositive(cardinality, bits, this.description);
        }
        bits.flip(0, 65536);
        final int n = 65536 - cardinality;
        final String s = ".negate()";
        return new NegatedFastMatcher(this.toString(), precomputedPositive(n, bits, this.description.endsWith(s) ? this.description.substring(0, this.description.length() - s.length()) : (this.description + s)));
    }
    
    @GwtIncompatible("java.util.BitSet")
    private static CharMatcher precomputedPositive(final int n, final BitSet set, final String s) {
        switch (n) {
            case 0: {
                return CharMatcher.NONE;
            }
            case 1: {
                return is((char)set.nextSetBit(0));
            }
            case 2: {
                final char c = (char)set.nextSetBit(0);
                return isEither(c, (char)set.nextSetBit(c + '\u0001'));
            }
            default: {
                return (n <= set.length()) ? SmallCharMatcher.from(set, s) : new BitSetMatcher(set, s, null);
            }
        }
    }
    
    @GwtIncompatible("java.util.BitSet")
    void setBits(final BitSet set) {
        while (true) {
            if (this.matches((char)65535)) {
                set.set(65535);
            }
            int n = 0;
            --n;
        }
    }
    
    public boolean matchesAnyOf(final CharSequence charSequence) {
        return this == charSequence;
    }
    
    public boolean matchesAllOf(final CharSequence charSequence) {
        for (int i = charSequence.length() - 1; i >= 0; --i) {
            if (!this.matches(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public int indexIn(final CharSequence charSequence) {
        while (0 < charSequence.length()) {
            if (this.matches(charSequence.charAt(0))) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public int indexIn(final CharSequence charSequence, final int n) {
        final int length = charSequence.length();
        Preconditions.checkPositionIndex(n, length);
        for (int i = n; i < length; ++i) {
            if (this.matches(charSequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexIn(final CharSequence charSequence) {
        for (int i = charSequence.length() - 1; i >= 0; --i) {
            if (this.matches(charSequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public int countIn(final CharSequence charSequence) {
        while (0 < charSequence.length()) {
            if (this.matches(charSequence.charAt(0))) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @CheckReturnValue
    public String removeFrom(final CharSequence charSequence) {
        final String string = charSequence.toString();
        int i = this.indexIn(string);
        if (i == -1) {
            return string;
        }
        final char[] charArray = string.toCharArray();
    Label_0026:
        while (true) {
            ++i;
            while (i != charArray.length) {
                if (this.matches(charArray[i])) {
                    int n = 0;
                    ++n;
                    continue Label_0026;
                }
                charArray[i - 1] = charArray[i];
                ++i;
            }
            break;
        }
        return new String(charArray, 0, i - 1);
    }
    
    @CheckReturnValue
    public String retainFrom(final CharSequence charSequence) {
        return this.negate().removeFrom(charSequence);
    }
    
    @CheckReturnValue
    public String replaceFrom(final CharSequence charSequence, final char c) {
        final String string = charSequence.toString();
        final int indexIn = this.indexIn(string);
        if (indexIn == -1) {
            return string;
        }
        final char[] charArray = string.toCharArray();
        charArray[indexIn] = c;
        for (int i = indexIn + 1; i < charArray.length; ++i) {
            if (this.matches(charArray[i])) {
                charArray[i] = c;
            }
        }
        return new String(charArray);
    }
    
    @CheckReturnValue
    public String replaceFrom(final CharSequence charSequence, final CharSequence charSequence2) {
        final int length = charSequence2.length();
        if (length == 0) {
            return this.removeFrom(charSequence);
        }
        if (length == 1) {
            return this.replaceFrom(charSequence, charSequence2.charAt(0));
        }
        final String string = charSequence.toString();
        int i = this.indexIn(string);
        if (i == -1) {
            return string;
        }
        final int length2 = string.length();
        final StringBuilder sb = new StringBuilder(length2 * 3 / 2 + 16);
        do {
            sb.append(string, 0, i);
            sb.append(charSequence2);
            i = this.indexIn(string, 0);
        } while (i != -1);
        sb.append(string, 0, length2);
        return sb.toString();
    }
    
    @CheckReturnValue
    public String trimFrom(final CharSequence charSequence) {
        final int length = charSequence.length();
        while (0 < length && this.matches(charSequence.charAt(0))) {
            int n = 0;
            ++n;
        }
        int n2;
        for (n2 = length - 1; n2 > 0 && this.matches(charSequence.charAt(n2)); --n2) {}
        return charSequence.subSequence(0, n2 + 1).toString();
    }
    
    @CheckReturnValue
    public String trimLeadingFrom(final CharSequence charSequence) {
        final int length = charSequence.length();
        while (0 < length) {
            if (!this.matches(charSequence.charAt(0))) {
                return charSequence.subSequence(0, length).toString();
            }
            int n = 0;
            ++n;
        }
        return "";
    }
    
    @CheckReturnValue
    public String trimTrailingFrom(final CharSequence charSequence) {
        for (int i = charSequence.length() - 1; i >= 0; --i) {
            if (!this.matches(charSequence.charAt(i))) {
                return charSequence.subSequence(0, i + 1).toString();
            }
        }
        return "";
    }
    
    @CheckReturnValue
    public String collapseFrom(final CharSequence charSequence, final char c) {
        final int length = charSequence.length();
        while (0 < length) {
            final char char1 = charSequence.charAt(0);
            int n = 0;
            if (this.matches(char1)) {
                if (char1 != c || (0 != length - 1 && this.matches(charSequence.charAt(1)))) {
                    return this.finishCollapseFrom(charSequence, 1, length, c, new StringBuilder(length).append(charSequence.subSequence(0, 0)).append(c), true);
                }
                ++n;
            }
            ++n;
        }
        return charSequence.toString();
    }
    
    @CheckReturnValue
    public String trimAndCollapseFrom(final CharSequence charSequence, final char c) {
        final int length = charSequence.length();
        while (0 < length && this.matches(charSequence.charAt(0))) {
            int n = 0;
            ++n;
        }
        int n2;
        for (n2 = length - 1; n2 > 0 && this.matches(charSequence.charAt(n2)); --n2) {}
        return (n2 == length - 1) ? this.collapseFrom(charSequence, c) : this.finishCollapseFrom(charSequence, 0, n2 + 1, c, new StringBuilder(n2 + 1 - 0), false);
    }
    
    private String finishCollapseFrom(final CharSequence charSequence, final int n, final int n2, final char c, final StringBuilder sb, final boolean b) {
        for (int i = n; i < n2; ++i) {
            final char char1 = charSequence.charAt(i);
            if (this.matches(char1)) {
                sb.append(c);
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    @Deprecated
    public boolean apply(final Character c) {
        return this.matches(c);
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @Override
    public boolean apply(final Object o) {
        return this.apply((Character)o);
    }
    
    static {
        BREAKING_WHITESPACE = new CharMatcher() {
            @Override
            public boolean matches(final char c) {
                switch (c) {
                    case '\t':
                    case '\n':
                    case '\u000b':
                    case '\f':
                    case '\r':
                    case ' ':
                    case '\u0085':
                    case '\u1680':
                    case '\u2028':
                    case '\u2029':
                    case '\u205f':
                    case '\u3000': {
                        return true;
                    }
                    case '\u2007': {
                        return false;
                    }
                    default: {
                        return c >= '\u2000' && c <= '\u200a';
                    }
                }
            }
            
            @Override
            public String toString() {
                return "CharMatcher.BREAKING_WHITESPACE";
            }
            
            @Override
            public boolean apply(final Object o) {
                return super.apply((Character)o);
            }
        };
        ASCII = inRange('\0', '\u007f', "CharMatcher.ASCII");
        final StringBuilder sb = new StringBuilder(31);
        while (true) {
            sb.append((char)("0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10".charAt(0) + '\t'));
            int n = 0;
            ++n;
        }
    }
    
    @GwtIncompatible("java.util.BitSet")
    private static class BitSetMatcher extends FastMatcher
    {
        private final BitSet table;
        
        private BitSetMatcher(BitSet table, final String s) {
            super(s);
            if (table.length() + 64 < table.size()) {
                table = (BitSet)table.clone();
            }
            this.table = table;
        }
        
        @Override
        public boolean matches(final char c) {
            return this.table.get(c);
        }
        
        @Override
        void setBits(final BitSet set) {
            set.or(this.table);
        }
        
        BitSetMatcher(final BitSet set, final String s, final CharMatcher$1 charMatcher) {
            this(set, s);
        }
    }
    
    abstract static class FastMatcher extends CharMatcher
    {
        FastMatcher() {
        }
        
        FastMatcher(final String s) {
            super(s);
        }
        
        @Override
        public final CharMatcher precomputed() {
            return this;
        }
        
        @Override
        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
        
        @Override
        public boolean apply(final Object o) {
            return super.apply((Character)o);
        }
    }
    
    static final class NegatedFastMatcher extends NegatedMatcher
    {
        NegatedFastMatcher(final CharMatcher charMatcher) {
            super(charMatcher);
        }
        
        NegatedFastMatcher(final String s, final CharMatcher charMatcher) {
            super(s, charMatcher);
        }
        
        @Override
        public final CharMatcher precomputed() {
            return this;
        }
        
        @Override
        CharMatcher withToString(final String s) {
            return new NegatedFastMatcher(s, this.original);
        }
    }
    
    private static class NegatedMatcher extends CharMatcher
    {
        final CharMatcher original;
        
        NegatedMatcher(final String s, final CharMatcher original) {
            super(s);
            this.original = original;
        }
        
        NegatedMatcher(final CharMatcher charMatcher) {
            this(charMatcher + ".negate()", charMatcher);
        }
        
        @Override
        public boolean matches(final char c) {
            return !this.original.matches(c);
        }
        
        @Override
        public boolean matchesAllOf(final CharSequence charSequence) {
            return this.original.matchesNoneOf(charSequence);
        }
        
        public boolean matchesNoneOf(final CharSequence charSequence) {
            return this.original.matchesAllOf(charSequence);
        }
        
        @Override
        public int countIn(final CharSequence charSequence) {
            return charSequence.length() - this.original.countIn(charSequence);
        }
        
        @GwtIncompatible("java.util.BitSet")
        @Override
        void setBits(final BitSet set) {
            final BitSet bits = new BitSet();
            this.original.setBits(bits);
            bits.flip(0, 65536);
            set.or(bits);
        }
        
        @Override
        public CharMatcher negate() {
            return this.original;
        }
        
        @Override
        CharMatcher withToString(final String s) {
            return new NegatedMatcher(s, this.original);
        }
        
        @Override
        public boolean apply(final Object o) {
            return super.apply((Character)o);
        }
    }
    
    private static class Or extends CharMatcher
    {
        final CharMatcher first;
        final CharMatcher second;
        
        Or(final CharMatcher charMatcher, final CharMatcher charMatcher2, final String s) {
            super(s);
            this.first = (CharMatcher)Preconditions.checkNotNull(charMatcher);
            this.second = (CharMatcher)Preconditions.checkNotNull(charMatcher2);
        }
        
        Or(final CharMatcher charMatcher, final CharMatcher charMatcher2) {
            this(charMatcher, charMatcher2, "CharMatcher.or(" + charMatcher + ", " + charMatcher2 + ")");
        }
        
        @GwtIncompatible("java.util.BitSet")
        @Override
        void setBits(final BitSet set) {
            this.first.setBits(set);
            this.second.setBits(set);
        }
        
        @Override
        public boolean matches(final char c) {
            return this.first.matches(c) || this.second.matches(c);
        }
        
        @Override
        CharMatcher withToString(final String s) {
            return new Or(this.first, this.second, s);
        }
        
        @Override
        public boolean apply(final Object o) {
            return super.apply((Character)o);
        }
    }
    
    private static class And extends CharMatcher
    {
        final CharMatcher first;
        final CharMatcher second;
        
        And(final CharMatcher charMatcher, final CharMatcher charMatcher2) {
            this(charMatcher, charMatcher2, "CharMatcher.and(" + charMatcher + ", " + charMatcher2 + ")");
        }
        
        And(final CharMatcher charMatcher, final CharMatcher charMatcher2, final String s) {
            super(s);
            this.first = (CharMatcher)Preconditions.checkNotNull(charMatcher);
            this.second = (CharMatcher)Preconditions.checkNotNull(charMatcher2);
        }
        
        @Override
        public boolean matches(final char c) {
            return this.first.matches(c) && this.second.matches(c);
        }
        
        @GwtIncompatible("java.util.BitSet")
        @Override
        void setBits(final BitSet set) {
            final BitSet bits = new BitSet();
            this.first.setBits(bits);
            final BitSet bits2 = new BitSet();
            this.second.setBits(bits2);
            bits.and(bits2);
            set.or(bits);
        }
        
        @Override
        CharMatcher withToString(final String s) {
            return new And(this.first, this.second, s);
        }
        
        @Override
        public boolean apply(final Object o) {
            return super.apply((Character)o);
        }
    }
    
    private static class RangesMatcher extends CharMatcher
    {
        private final char[] rangeStarts;
        private final char[] rangeEnds;
        
        RangesMatcher(final String s, final char[] rangeStarts, final char[] rangeEnds) {
            super(s);
            this.rangeStarts = rangeStarts;
            this.rangeEnds = rangeEnds;
            Preconditions.checkArgument(rangeStarts.length == rangeEnds.length);
            while (0 < rangeStarts.length) {
                Preconditions.checkArgument(rangeStarts[0] <= rangeEnds[0]);
                if (1 < rangeStarts.length) {
                    Preconditions.checkArgument(rangeEnds[0] < rangeStarts[1]);
                }
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public boolean matches(final char c) {
            final int binarySearch = Arrays.binarySearch(this.rangeStarts, c);
            if (binarySearch >= 0) {
                return true;
            }
            final int n = ~binarySearch - 1;
            return n >= 0 && c <= this.rangeEnds[n];
        }
        
        @Override
        public boolean apply(final Object o) {
            return super.apply((Character)o);
        }
    }
}
