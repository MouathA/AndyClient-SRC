package com.ibm.icu.impl;

import java.util.*;

public class LocaleUtility
{
    public static Locale getLocaleFromName(final String s) {
        String s2 = "";
        String substring = "";
        int index = s.indexOf(95);
        String substring2;
        if (index < 0) {
            substring2 = s;
        }
        else {
            substring2 = s.substring(0, index);
            ++index;
            final int index2 = s.indexOf(95, index);
            if (index2 < 0) {
                s2 = s.substring(index);
            }
            else {
                s2 = s.substring(index, index2);
                substring = s.substring(index2 + 1);
            }
        }
        return new Locale(substring2, s2, substring);
    }
    
    public static boolean isFallbackOf(final String s, final String s2) {
        if (!s2.startsWith(s)) {
            return false;
        }
        final int length = s.length();
        return length == s2.length() || s2.charAt(length) == '_';
    }
    
    public static boolean isFallbackOf(final Locale locale, final Locale locale2) {
        return isFallbackOf(locale.toString(), locale2.toString());
    }
    
    public static Locale fallback(final Locale locale) {
        final String[] array = { locale.getLanguage(), locale.getCountry(), locale.getVariant() };
        while (2 >= 0) {
            if (array[2].length() != 0) {
                array[2] = "";
                break;
            }
            int n = 0;
            --n;
        }
        if (2 < 0) {
            return null;
        }
        return new Locale(array[0], array[1], array[2]);
    }
}
