package org.apache.commons.lang3.time;

import org.apache.commons.lang3.*;
import java.util.*;

public class DurationFormatUtils
{
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
    static final Object y;
    static final Object M;
    static final Object d;
    static final Object H;
    static final Object m;
    static final Object s;
    static final Object S;
    
    public static String formatDurationHMS(final long n) {
        return formatDuration(n, "H:mm:ss.SSS");
    }
    
    public static String formatDurationISO(final long n) {
        return formatDuration(n, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
    }
    
    public static String formatDuration(final long n, final String s) {
        return formatDuration(n, s, true);
    }
    
    public static String formatDuration(final long n, final String s, final boolean b) {
        final Token[] lexx = lexx(s);
        long n2 = 0L;
        long n3 = 0L;
        long n4 = 0L;
        long n5 = 0L;
        long n6 = n;
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.d)) {
            n2 = n6 / 86400000L;
            n6 -= n2 * 86400000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.H)) {
            n3 = n6 / 3600000L;
            n6 -= n3 * 3600000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.m)) {
            n4 = n6 / 60000L;
            n6 -= n4 * 60000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.s)) {
            n5 = n6 / 1000L;
            n6 -= n5 * 1000L;
        }
        return format(lexx, 0L, 0L, n2, n3, n4, n5, n6, b);
    }
    
    public static String formatDurationWords(final long n, final boolean b, final boolean b2) {
        String s = formatDuration(n, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (b) {
            s = " " + s;
            final String replaceOnce = StringUtils.replaceOnce(s, " 0 days", "");
            if (replaceOnce.length() != s.length()) {
                s = replaceOnce;
                final String replaceOnce2 = StringUtils.replaceOnce(s, " 0 hours", "");
                if (replaceOnce2.length() != s.length()) {
                    final String s2 = s = StringUtils.replaceOnce(replaceOnce2, " 0 minutes", "");
                    if (s2.length() != s.length()) {
                        s = StringUtils.replaceOnce(s2, " 0 seconds", "");
                    }
                }
            }
            if (s.length() != 0) {
                s = s.substring(1);
            }
        }
        if (b2) {
            final String replaceOnce3 = StringUtils.replaceOnce(s, " 0 seconds", "");
            if (replaceOnce3.length() != s.length()) {
                s = replaceOnce3;
                final String replaceOnce4 = StringUtils.replaceOnce(s, " 0 minutes", "");
                if (replaceOnce4.length() != s.length()) {
                    s = replaceOnce4;
                    final String replaceOnce5 = StringUtils.replaceOnce(s, " 0 hours", "");
                    if (replaceOnce5.length() != s.length()) {
                        s = StringUtils.replaceOnce(replaceOnce5, " 0 days", "");
                    }
                }
            }
        }
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(" " + s, " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }
    
    public static String formatPeriodISO(final long n, final long n2) {
        return formatPeriod(n, n2, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, TimeZone.getDefault());
    }
    
    public static String formatPeriod(final long n, final long n2, final String s) {
        return formatPeriod(n, n2, s, true, TimeZone.getDefault());
    }
    
    public static String formatPeriod(final long n, final long n2, final String s, final boolean b, final TimeZone timeZone) {
        final Token[] lexx = lexx(s);
        final Calendar instance = Calendar.getInstance(timeZone);
        instance.setTime(new Date(n));
        final Calendar instance2 = Calendar.getInstance(timeZone);
        instance2.setTime(new Date(n2));
        int i = instance2.get(14) - instance.get(14);
        int n3 = instance2.get(13) - instance.get(13);
        final int n4 = instance2.get(12) - instance.get(12);
        final int n5 = instance2.get(11) - instance.get(11);
        final int n6 = instance2.get(5) - instance.get(5);
        final int n7 = instance2.get(2) - instance.get(2);
        final int n8 = instance2.get(1) - instance.get(1);
        while (i < 0) {
            i += 1000;
            --n3;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.M)) {
            if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.y)) {}
        }
        else {
            if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.y)) {
                while (instance.get(1) != instance2.get(1)) {
                    int n9 = 0 + (instance.getActualMaximum(6) - instance.get(6));
                    if (instance instanceof GregorianCalendar && instance.get(2) == 1 && instance.get(5) == 29) {
                        ++n9;
                    }
                    instance.add(1, 1);
                    final int n10 = 0 + instance.get(6);
                }
            }
            while (instance.get(2) != instance2.get(2)) {
                final int n11 = 0 + instance.getActualMaximum(5);
                instance.add(2, 1);
            }
        }
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.d)) {}
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.H)) {}
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.m)) {}
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.s)) {
            i += 0;
        }
        return format(lexx, 0, 0, 0, 0, 0, 0, i, b);
    }
    
    static String format(final Token[] array, final long n, final long n2, final long n3, final long n4, final long n5, final long n6, final long n7, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        while (0 < array.length) {
            final Token token = array[0];
            final Object value = token.getValue();
            final int count = token.getCount();
            if (value instanceof StringBuilder) {
                sb.append(value.toString());
            }
            else if (value == DurationFormatUtils.y) {
                sb.append(paddedValue(n, b, count));
            }
            else if (value == DurationFormatUtils.M) {
                sb.append(paddedValue(n2, b, count));
            }
            else if (value == DurationFormatUtils.d) {
                sb.append(paddedValue(n3, b, count));
            }
            else if (value == DurationFormatUtils.H) {
                sb.append(paddedValue(n4, b, count));
            }
            else if (value == DurationFormatUtils.m) {
                sb.append(paddedValue(n5, b, count));
            }
            else if (value == DurationFormatUtils.s) {
                sb.append(paddedValue(n6, b, count));
            }
            else if (value == DurationFormatUtils.S) {
                sb.append(paddedValue(n7, b, count));
            }
            int n8 = 0;
            ++n8;
        }
        return sb.toString();
    }
    
    private static String paddedValue(final long n, final boolean b, final int n2) {
        final String string = Long.toString(n);
        return b ? StringUtils.leftPad(string, n2, '0') : string;
    }
    
    static Token[] lexx(final String s) {
        final ArrayList<Token> list = new ArrayList<Token>(s.length());
        StringBuilder sb = null;
        Token token = null;
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 != '\'') {
                sb.append(char1);
            }
            else {
                Object o = null;
                switch (char1) {
                    case '\'': {
                        sb = null;
                        break;
                    }
                    case 'y': {
                        o = DurationFormatUtils.y;
                        break;
                    }
                    case 'M': {
                        o = DurationFormatUtils.M;
                        break;
                    }
                    case 'd': {
                        o = DurationFormatUtils.d;
                        break;
                    }
                    case 'H': {
                        o = DurationFormatUtils.H;
                        break;
                    }
                    case 'm': {
                        o = DurationFormatUtils.m;
                        break;
                    }
                    case 's': {
                        o = DurationFormatUtils.s;
                        break;
                    }
                    case 'S': {
                        o = DurationFormatUtils.S;
                        break;
                    }
                    default: {
                        if (sb == null) {
                            sb = new StringBuilder();
                            list.add(new Token(sb));
                        }
                        sb.append(char1);
                        break;
                    }
                }
                if (o != null) {
                    if (token != null && token.getValue() == o) {
                        token.increment();
                    }
                    else {
                        final Token token2 = new Token(o);
                        list.add(token2);
                        token = token2;
                    }
                    sb = null;
                }
            }
            int n = 0;
            ++n;
        }
        throw new IllegalArgumentException("Unmatched quote in format: " + s);
    }
    
    static {
        y = "y";
        M = "M";
        d = "d";
        H = "H";
        m = "m";
        s = "s";
        S = "S";
    }
    
    static class Token
    {
        private final Object value;
        private int count;
        
        static boolean containsTokenWithValue(final Token[] array, final Object o) {
            while (0 < array.length) {
                if (array[0].getValue() == o) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        Token(final Object value) {
            this.value = value;
            this.count = 1;
        }
        
        Token(final Object value, final int count) {
            this.value = value;
            this.count = count;
        }
        
        void increment() {
            ++this.count;
        }
        
        int getCount() {
            return this.count;
        }
        
        Object getValue() {
            return this.value;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Token)) {
                return false;
            }
            final Token token = (Token)o;
            if (this.value.getClass() != token.value.getClass()) {
                return false;
            }
            if (this.count != token.count) {
                return false;
            }
            if (this.value instanceof StringBuilder) {
                return this.value.toString().equals(token.value.toString());
            }
            if (this.value instanceof Number) {
                return this.value.equals(token.value);
            }
            return this.value == token.value;
        }
        
        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
        
        @Override
        public String toString() {
            return StringUtils.repeat(this.value.toString(), this.count);
        }
    }
}
