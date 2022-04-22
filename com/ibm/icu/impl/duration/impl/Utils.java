package com.ibm.icu.impl.duration.impl;

import java.util.*;

public class Utils
{
    public static final Locale localeFromString(final String s) {
        String substring = s;
        String s2 = "";
        String substring2 = "";
        final int index = substring.indexOf("_");
        if (index != -1) {
            s2 = substring.substring(index + 1);
            substring = substring.substring(0, index);
        }
        final int index2 = s2.indexOf("_");
        if (index2 != -1) {
            substring2 = s2.substring(index2 + 1);
            s2 = s2.substring(0, index2);
        }
        return new Locale(substring, s2, substring2);
    }
    
    public static String chineseNumber(long n, final ChineseDigits chineseDigits) {
        if (n < 0L) {
            n = -n;
        }
        if (n <= 10L) {
            if (n == 2L) {
                return String.valueOf(chineseDigits.liang);
            }
            return String.valueOf(chineseDigits.digits[(int)n]);
        }
        else {
            final char[] array = new char[40];
            final char[] charArray = String.valueOf(n).toCharArray();
            int length = array.length;
            int length2 = charArray.length;
            while (true) {
                --length2;
                array[--length] = chineseDigits.levels[0];
                int n2 = 0;
                ++n2;
                final int n3 = charArray[1] - '0';
                array[--length] = chineseDigits.digits[-2];
            }
        }
    }
    
    public static class ChineseDigits
    {
        final char[] digits;
        final char[] units;
        final char[] levels;
        final char liang;
        final boolean ko;
        public static final ChineseDigits DEBUG;
        public static final ChineseDigits TRADITIONAL;
        public static final ChineseDigits SIMPLIFIED;
        public static final ChineseDigits KOREAN;
        
        ChineseDigits(final String s, final String s2, final String s3, final char liang, final boolean ko) {
            this.digits = s.toCharArray();
            this.units = s2.toCharArray();
            this.levels = s3.toCharArray();
            this.liang = liang;
            this.ko = ko;
        }
        
        static {
            DEBUG = new ChineseDigits("0123456789s", "sbq", "WYZ", 'L', false);
            TRADITIONAL = new ChineseDigits("\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341", "\u5341\u767e\u5343", "\u842c\u5104\u5146", '\u5169', false);
            SIMPLIFIED = new ChineseDigits("\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341", "\u5341\u767e\u5343", "\u4e07\u4ebf\u5146", '\u4e24', false);
            KOREAN = new ChineseDigits("\uc601\uc77c\uc774\uc0bc\uc0ac\uc624\uc721\uce60\ud314\uad6c\uc2ed", "\uc2ed\ubc31\ucc9c", "\ub9cc\uc5b5?", '\uc774', true);
        }
    }
}
