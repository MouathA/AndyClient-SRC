package com.ibm.icu.lang;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.lang.ref.*;
import java.util.*;

public final class UCharacter implements UCharacterEnums.ECharacterCategory, UCharacterEnums.ECharacterDirection
{
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 1114111;
    public static final int SUPPLEMENTARY_MIN_VALUE = 65536;
    public static final int REPLACEMENT_CHAR = 65533;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8;
    public static final int MIN_RADIX = 2;
    public static final int MAX_RADIX = 36;
    public static final int TITLECASE_NO_LOWERCASE = 256;
    public static final int TITLECASE_NO_BREAK_ADJUSTMENT = 512;
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
    public static final char MIN_HIGH_SURROGATE = '\ud800';
    public static final char MAX_HIGH_SURROGATE = '\udbff';
    public static final char MIN_LOW_SURROGATE = '\udc00';
    public static final char MAX_LOW_SURROGATE = '\udfff';
    public static final char MIN_SURROGATE = '\ud800';
    public static final char MAX_SURROGATE = '\udfff';
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 65536;
    public static final int MAX_CODE_POINT = 1114111;
    public static final int MIN_CODE_POINT = 0;
    private static final int LAST_CHAR_MASK_ = 65535;
    private static final int NO_BREAK_SPACE_ = 160;
    private static final int FIGURE_SPACE_ = 8199;
    private static final int NARROW_NO_BREAK_SPACE_ = 8239;
    private static final int IDEOGRAPHIC_NUMBER_ZERO_ = 12295;
    private static final int CJK_IDEOGRAPH_FIRST_ = 19968;
    private static final int CJK_IDEOGRAPH_SECOND_ = 20108;
    private static final int CJK_IDEOGRAPH_THIRD_ = 19977;
    private static final int CJK_IDEOGRAPH_FOURTH_ = 22235;
    private static final int CJK_IDEOGRAPH_FIFTH_ = 20116;
    private static final int CJK_IDEOGRAPH_SIXTH_ = 20845;
    private static final int CJK_IDEOGRAPH_SEVENTH_ = 19971;
    private static final int CJK_IDEOGRAPH_EIGHTH_ = 20843;
    private static final int CJK_IDEOGRAPH_NINETH_ = 20061;
    private static final int APPLICATION_PROGRAM_COMMAND_ = 159;
    private static final int UNIT_SEPARATOR_ = 31;
    private static final int DELETE_ = 127;
    private static final int CJK_IDEOGRAPH_COMPLEX_ZERO_ = 38646;
    private static final int CJK_IDEOGRAPH_COMPLEX_ONE_ = 22777;
    private static final int CJK_IDEOGRAPH_COMPLEX_TWO_ = 36019;
    private static final int CJK_IDEOGRAPH_COMPLEX_THREE_ = 21443;
    private static final int CJK_IDEOGRAPH_COMPLEX_FOUR_ = 32902;
    private static final int CJK_IDEOGRAPH_COMPLEX_FIVE_ = 20237;
    private static final int CJK_IDEOGRAPH_COMPLEX_SIX_ = 38520;
    private static final int CJK_IDEOGRAPH_COMPLEX_SEVEN_ = 26578;
    private static final int CJK_IDEOGRAPH_COMPLEX_EIGHT_ = 25420;
    private static final int CJK_IDEOGRAPH_COMPLEX_NINE_ = 29590;
    private static final int CJK_IDEOGRAPH_TEN_ = 21313;
    private static final int CJK_IDEOGRAPH_COMPLEX_TEN_ = 25342;
    private static final int CJK_IDEOGRAPH_HUNDRED_ = 30334;
    private static final int CJK_IDEOGRAPH_COMPLEX_HUNDRED_ = 20336;
    private static final int CJK_IDEOGRAPH_THOUSAND_ = 21315;
    private static final int CJK_IDEOGRAPH_COMPLEX_THOUSAND_ = 20191;
    private static final int CJK_IDEOGRAPH_TEN_THOUSAND_ = 33356;
    private static final int CJK_IDEOGRAPH_HUNDRED_MILLION_ = 20740;
    
    public static int digit(final int n, final int n2) {
        if (2 <= n2 && n2 <= 36) {
            int n3 = digit(n);
            if (n3 < 0) {
                n3 = UCharacterProperty.getEuropeanDigit(n);
            }
            return (n3 < n2) ? n3 : -1;
        }
        return -1;
    }
    
    public static int digit(final int n) {
        return UCharacterProperty.INSTANCE.digit(n);
    }
    
    public static int getNumericValue(final int n) {
        return UCharacterProperty.INSTANCE.getNumericValue(n);
    }
    
    public static double getUnicodeNumericValue(final int n) {
        return UCharacterProperty.INSTANCE.getUnicodeNumericValue(n);
    }
    
    @Deprecated
    public static boolean isSpace(final int n) {
        return n <= 32 && (n == 32 || n == 9 || n == 10 || n == 12 || n == 13);
    }
    
    public static int getType(final int n) {
        return UCharacterProperty.INSTANCE.getType(n);
    }
    
    public static boolean isDefined(final int n) {
        return getType(n) != 0;
    }
    
    public static boolean isDigit(final int n) {
        return getType(n) == 9;
    }
    
    public static boolean isISOControl(final int n) {
        return n >= 0 && n <= 159 && (n <= 31 || n >= 127);
    }
    
    public static boolean isLetter(final int n) {
        return (1 << getType(n) & 0x3E) != 0x0;
    }
    
    public static boolean isLetterOrDigit(final int n) {
        return (1 << getType(n) & 0x23E) != 0x0;
    }
    
    @Deprecated
    public static boolean isJavaLetter(final int n) {
        return isJavaIdentifierStart(n);
    }
    
    @Deprecated
    public static boolean isJavaLetterOrDigit(final int n) {
        return isJavaIdentifierPart(n);
    }
    
    public static boolean isJavaIdentifierStart(final int n) {
        return Character.isJavaIdentifierStart((char)n);
    }
    
    public static boolean isJavaIdentifierPart(final int n) {
        return Character.isJavaIdentifierPart((char)n);
    }
    
    public static boolean isLowerCase(final int n) {
        return getType(n) == 2;
    }
    
    public static boolean isWhitespace(final int n) {
        return ((1 << getType(n) & 0x7000) != 0x0 && n != 160 && n != 8199 && n != 8239) || (n >= 9 && n <= 13) || (n >= 28 && n <= 31);
    }
    
    public static boolean isSpaceChar(final int n) {
        return (1 << getType(n) & 0x7000) != 0x0;
    }
    
    public static boolean isTitleCase(final int n) {
        return getType(n) == 3;
    }
    
    public static boolean isUnicodeIdentifierPart(final int n) {
        return (1 << getType(n) & 0x40077E) != 0x0 || isIdentifierIgnorable(n);
    }
    
    public static boolean isUnicodeIdentifierStart(final int n) {
        return (1 << getType(n) & 0x43E) != 0x0;
    }
    
    public static boolean isIdentifierIgnorable(final int n) {
        if (n <= 159) {
            return isISOControl(n) && (n < 9 || n > 13) && (n < 28 || n > 31);
        }
        return getType(n) == 16;
    }
    
    public static boolean isUpperCase(final int n) {
        return getType(n) == 1;
    }
    
    public static int toLowerCase(final int n) {
        return UCaseProps.INSTANCE.tolower(n);
    }
    
    public static String toString(final int n) {
        if (n < 0 || n > 1114111) {
            return null;
        }
        if (n < 65536) {
            return String.valueOf((char)n);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(UTF16.getLeadSurrogate(n));
        sb.append(UTF16.getTrailSurrogate(n));
        return sb.toString();
    }
    
    public static int toTitleCase(final int n) {
        return UCaseProps.INSTANCE.totitle(n);
    }
    
    public static int toUpperCase(final int n) {
        return UCaseProps.INSTANCE.toupper(n);
    }
    
    public static boolean isSupplementary(final int n) {
        return n >= 65536 && n <= 1114111;
    }
    
    public static boolean isBMP(final int n) {
        return n >= 0 && n <= 65535;
    }
    
    public static boolean isPrintable(final int n) {
        final int type = getType(n);
        return type != 0 && type != 15 && type != 16 && type != 17 && type != 18 && type != 0;
    }
    
    public static boolean isBaseForm(final int n) {
        final int type = getType(n);
        return type == 9 || type == 11 || type == 10 || type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 6 || type == 7 || type == 8;
    }
    
    public static int getDirection(final int n) {
        return UBiDiProps.INSTANCE.getClass(n);
    }
    
    public static boolean isMirrored(final int n) {
        return UBiDiProps.INSTANCE.isMirrored(n);
    }
    
    public static int getMirror(final int n) {
        return UBiDiProps.INSTANCE.getMirror(n);
    }
    
    public static int getCombiningClass(final int n) {
        return Norm2AllModes.getNFCInstance().decomp.getCombiningClass(n);
    }
    
    public static boolean isLegal(final int n) {
        return n >= 0 && (n < 55296 || (n > 57343 && !UCharacterUtility.isNonCharacter(n) && n <= 1114111));
    }
    
    public static boolean isLegal(final String s) {
        while (0 < s.length()) {
            final int char1 = UTF16.charAt(s, 0);
            if (!isLegal(char1)) {
                return false;
            }
            int n = 0;
            if (isSupplementary(char1)) {
                ++n;
            }
            ++n;
        }
        return true;
    }
    
    public static VersionInfo getUnicodeVersion() {
        return UCharacterProperty.INSTANCE.m_unicodeVersion_;
    }
    
    public static String getName(final int n) {
        return UCharacterName.INSTANCE.getName(n, 0);
    }
    
    public static String getName(final String s, final String s2) {
        if (s.length() == 1) {
            return getName(s.charAt(0));
        }
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final int char1 = UTF16.charAt(s, 0);
            if (false) {
                sb.append(s2);
            }
            sb.append(getName(char1));
            final int n = 0 + UTF16.getCharCount(char1);
        }
        return sb.toString();
    }
    
    @Deprecated
    public static String getName1_0(final int n) {
        return null;
    }
    
    public static String getExtendedName(final int n) {
        return UCharacterName.INSTANCE.getName(n, 2);
    }
    
    public static String getNameAlias(final int n) {
        return UCharacterName.INSTANCE.getName(n, 3);
    }
    
    @Deprecated
    public static String getISOComment(final int n) {
        return null;
    }
    
    public static int getCharFromName(final String s) {
        return UCharacterName.INSTANCE.getCharFromName(0, s);
    }
    
    @Deprecated
    public static int getCharFromName1_0(final String s) {
        return -1;
    }
    
    public static int getCharFromExtendedName(final String s) {
        return UCharacterName.INSTANCE.getCharFromName(2, s);
    }
    
    public static int getCharFromNameAlias(final String s) {
        return UCharacterName.INSTANCE.getCharFromName(3, s);
    }
    
    public static String getPropertyName(final int n, final int n2) {
        return UPropertyAliases.INSTANCE.getPropertyName(n, n2);
    }
    
    public static int getPropertyEnum(final CharSequence charSequence) {
        final int propertyEnum = UPropertyAliases.INSTANCE.getPropertyEnum(charSequence);
        if (propertyEnum == -1) {
            throw new IllegalIcuArgumentException("Invalid name: " + (Object)charSequence);
        }
        return propertyEnum;
    }
    
    public static String getPropertyValueName(final int n, final int n2, final int n3) {
        if ((n == 4098 || n == 4112 || n == 4113) && n2 >= getIntPropertyMinValue(4098) && n2 <= getIntPropertyMaxValue(4098) && n3 >= 0 && n3 < 2) {
            return UPropertyAliases.INSTANCE.getPropertyValueName(n, n2, n3);
        }
        return UPropertyAliases.INSTANCE.getPropertyValueName(n, n2, n3);
    }
    
    public static int getPropertyValueEnum(final int n, final CharSequence charSequence) {
        final int propertyValueEnum = UPropertyAliases.INSTANCE.getPropertyValueEnum(n, charSequence);
        if (propertyValueEnum == -1) {
            throw new IllegalIcuArgumentException("Invalid name: " + (Object)charSequence);
        }
        return propertyValueEnum;
    }
    
    public static int getCodePoint(final char c, final char c2) {
        if (UTF16.isLeadSurrogate(c) && UTF16.isTrailSurrogate(c2)) {
            return UCharacterProperty.getRawSupplementary(c, c2);
        }
        throw new IllegalArgumentException("Illegal surrogate characters");
    }
    
    public static int getCodePoint(final char c) {
        if (isLegal(c)) {
            return c;
        }
        throw new IllegalArgumentException("Illegal codepoint");
    }
    
    public static String toUpperCase(final String s) {
        return toUpperCase(ULocale.getDefault(), s);
    }
    
    public static String toLowerCase(final String s) {
        return toLowerCase(ULocale.getDefault(), s);
    }
    
    public static String toTitleCase(final String s, final BreakIterator breakIterator) {
        return toTitleCase(ULocale.getDefault(), s, breakIterator);
    }
    
    public static String toUpperCase(final Locale locale, final String s) {
        return toUpperCase(ULocale.forLocale(locale), s);
    }
    
    public static String toUpperCase(ULocale default1, final String s) {
        final StringContextIterator stringContextIterator = new StringContextIterator(s);
        final StringBuilder sb = new StringBuilder(s.length());
        final int[] array = { 0 };
        if (default1 == null) {
            default1 = ULocale.getDefault();
        }
        array[0] = 0;
        int nextCaseMapCP;
        while ((nextCaseMapCP = stringContextIterator.nextCaseMapCP()) >= 0) {
            int fullUpper = UCaseProps.INSTANCE.toFullUpper(nextCaseMapCP, stringContextIterator, sb, default1, array);
            if (fullUpper < 0) {
                fullUpper ^= -1;
            }
            else if (fullUpper <= 31) {
                continue;
            }
            sb.appendCodePoint(fullUpper);
        }
        return sb.toString();
    }
    
    public static String toLowerCase(final Locale locale, final String s) {
        return toLowerCase(ULocale.forLocale(locale), s);
    }
    
    public static String toLowerCase(ULocale default1, final String s) {
        final StringContextIterator stringContextIterator = new StringContextIterator(s);
        final StringBuilder sb = new StringBuilder(s.length());
        final int[] array = { 0 };
        if (default1 == null) {
            default1 = ULocale.getDefault();
        }
        array[0] = 0;
        int nextCaseMapCP;
        while ((nextCaseMapCP = stringContextIterator.nextCaseMapCP()) >= 0) {
            int fullLower = UCaseProps.INSTANCE.toFullLower(nextCaseMapCP, stringContextIterator, sb, default1, array);
            if (fullLower < 0) {
                fullLower ^= -1;
            }
            else if (fullLower <= 31) {
                continue;
            }
            sb.appendCodePoint(fullLower);
        }
        return sb.toString();
    }
    
    public static String toTitleCase(final Locale locale, final String s, final BreakIterator breakIterator) {
        return toTitleCase(ULocale.forLocale(locale), s, breakIterator);
    }
    
    public static String toTitleCase(final ULocale uLocale, final String s, final BreakIterator breakIterator) {
        return toTitleCase(uLocale, s, breakIterator, 0);
    }
    
    public static String toTitleCase(ULocale default1, final String text, BreakIterator wordInstance, final int n) {
        final StringContextIterator stringContextIterator = new StringContextIterator(text);
        final StringBuilder sb = new StringBuilder(text.length());
        final int[] array = { 0 };
        final int length = text.length();
        if (default1 == null) {
            default1 = ULocale.getDefault();
        }
        array[0] = 0;
        if (wordInstance == null) {
            wordInstance = BreakIterator.getWordInstance(default1);
        }
        wordInstance.setText(text);
        final boolean equals = default1.getLanguage().equals("nl");
        while (0 < length) {
            int limit;
            if (false) {
                limit = wordInstance.first();
            }
            else {
                limit = wordInstance.next();
            }
            if (limit == -1 || limit > length) {
                limit = length;
            }
            if (0 < limit) {
                stringContextIterator.setLimit(limit);
                stringContextIterator.nextCaseMapCP();
                if ((n & 0x200) == 0x0 && 0 == UCaseProps.INSTANCE.getType(74)) {
                    while (stringContextIterator.nextCaseMapCP() >= 0 && 0 == UCaseProps.INSTANCE.getType(74)) {}
                    stringContextIterator.getCPStart();
                    if (0 < 0) {
                        sb.append(text, 0, 0);
                    }
                }
                if (0 >= limit) {
                    continue;
                }
                UCaseProps.INSTANCE.toFullTitle(74, stringContextIterator, sb, default1, array);
                while (true) {
                    if (74 < 0) {
                        sb.appendCodePoint(74);
                    }
                    else if (74 > 31) {
                        sb.appendCodePoint(74);
                    }
                    if ((n & 0x100) != 0x0) {
                        final int cpLimit = stringContextIterator.getCPLimit();
                        if (cpLimit < limit) {
                            String s = text.substring(cpLimit, limit);
                            if (equals && 74 == 73 && s.startsWith("j")) {
                                s = "J" + s.substring(1);
                            }
                            sb.append(s);
                        }
                        stringContextIterator.moveToLimit();
                        break;
                    }
                    final int nextCaseMapCP;
                    if ((nextCaseMapCP = stringContextIterator.nextCaseMapCP()) < 0) {
                        break;
                    }
                    if (equals && (nextCaseMapCP == 74 || nextCaseMapCP == 106) && 74 == 73 && false == true) {
                        continue;
                    }
                    UCaseProps.INSTANCE.toFullLower(nextCaseMapCP, stringContextIterator, sb, default1, array);
                }
            }
        }
        return sb.toString();
    }
    
    public static int foldCase(final int n, final boolean b) {
        return foldCase(n, b ? 0 : 1);
    }
    
    public static String foldCase(final String s, final boolean b) {
        return foldCase(s, b ? 0 : 1);
    }
    
    public static int foldCase(final int n, final int n2) {
        return UCaseProps.INSTANCE.fold(n, n2);
    }
    
    public static final String foldCase(final String s, final int n) {
        final StringBuilder sb = new StringBuilder(s.length());
        while (0 < s.length()) {
            final int char1 = UTF16.charAt(s, 0);
            final int n2 = 0 + UTF16.getCharCount(char1);
            int fullFolding = UCaseProps.INSTANCE.toFullFolding(char1, sb, n);
            if (fullFolding < 0) {
                fullFolding ^= -1;
            }
            else if (fullFolding <= 31) {
                continue;
            }
            sb.appendCodePoint(fullFolding);
        }
        return sb.toString();
    }
    
    public static int getHanNumericValue(final int n) {
        switch (n) {
            case 12295:
            case 38646: {
                return 0;
            }
            case 19968:
            case 22777: {
                return 1;
            }
            case 20108:
            case 36019: {
                return 2;
            }
            case 19977:
            case 21443: {
                return 3;
            }
            case 22235:
            case 32902: {
                return 4;
            }
            case 20116:
            case 20237: {
                return 5;
            }
            case 20845:
            case 38520: {
                return 6;
            }
            case 19971:
            case 26578: {
                return 7;
            }
            case 20843:
            case 25420: {
                return 8;
            }
            case 20061:
            case 29590: {
                return 9;
            }
            case 21313:
            case 25342: {
                return 10;
            }
            case 20336:
            case 30334: {
                return 100;
            }
            case 20191:
            case 21315: {
                return 1000;
            }
            case 33356: {
                return 10000;
            }
            case 20740: {
                return 100000000;
            }
            default: {
                return -1;
            }
        }
    }
    
    public static RangeValueIterator getTypeIterator() {
        return new UCharacterTypeIterator();
    }
    
    public static ValueIterator getNameIterator() {
        return new UCharacterNameIterator(UCharacterName.INSTANCE, 0);
    }
    
    @Deprecated
    public static ValueIterator getName1_0Iterator() {
        return new DummyValueIterator(null);
    }
    
    public static ValueIterator getExtendedNameIterator() {
        return new UCharacterNameIterator(UCharacterName.INSTANCE, 2);
    }
    
    public static VersionInfo getAge(final int n) {
        if (n < 0 || n > 1114111) {
            throw new IllegalArgumentException("Codepoint out of bounds");
        }
        return UCharacterProperty.INSTANCE.getAge(n);
    }
    
    public static boolean hasBinaryProperty(final int n, final int n2) {
        return UCharacterProperty.INSTANCE.hasBinaryProperty(n, n2);
    }
    
    public static boolean isUAlphabetic(final int n) {
        return hasBinaryProperty(n, 0);
    }
    
    public static boolean isULowercase(final int n) {
        return hasBinaryProperty(n, 22);
    }
    
    public static boolean isUUppercase(final int n) {
        return hasBinaryProperty(n, 30);
    }
    
    public static boolean isUWhiteSpace(final int n) {
        return hasBinaryProperty(n, 31);
    }
    
    public static int getIntPropertyValue(final int n, final int n2) {
        return UCharacterProperty.INSTANCE.getIntPropertyValue(n, n2);
    }
    
    @Deprecated
    public static String getStringPropertyValue(final int n, final int n2, final int n3) {
        if ((n >= 0 && n < 57) || (n >= 4096 && n < 4117)) {
            return getPropertyValueName(n, getIntPropertyValue(n2, n), n3);
        }
        if (n == 12288) {
            return String.valueOf(getUnicodeNumericValue(n2));
        }
        switch (n) {
            case 16384: {
                return getAge(n2).toString();
            }
            case 16387: {
                return getISOComment(n2);
            }
            case 16385: {
                return UTF16.valueOf(getMirror(n2));
            }
            case 16386: {
                return foldCase(UTF16.valueOf(n2), true);
            }
            case 16388: {
                return toLowerCase(UTF16.valueOf(n2));
            }
            case 16389: {
                return getName(n2);
            }
            case 16390: {
                return UTF16.valueOf(foldCase(n2, true));
            }
            case 16391: {
                return UTF16.valueOf(toLowerCase(n2));
            }
            case 16392: {
                return UTF16.valueOf(toTitleCase(n2));
            }
            case 16393: {
                return UTF16.valueOf(toUpperCase(n2));
            }
            case 16394: {
                return toTitleCase(UTF16.valueOf(n2), null);
            }
            case 16395: {
                return getName1_0(n2);
            }
            case 16396: {
                return toUpperCase(UTF16.valueOf(n2));
            }
            default: {
                throw new IllegalArgumentException("Illegal Property Enum");
            }
        }
    }
    
    public static int getIntPropertyMinValue(final int n) {
        return 0;
    }
    
    public static int getIntPropertyMaxValue(final int n) {
        return UCharacterProperty.INSTANCE.getIntPropertyMaxValue(n);
    }
    
    public static char forDigit(final int n, final int n2) {
        return Character.forDigit(n, n2);
    }
    
    public static final boolean isValidCodePoint(final int n) {
        return n >= 0 && n <= 1114111;
    }
    
    public static final boolean isSupplementaryCodePoint(final int n) {
        return n >= 65536 && n <= 1114111;
    }
    
    public static boolean isHighSurrogate(final char c) {
        return c >= '\ud800' && c <= '\udbff';
    }
    
    public static boolean isLowSurrogate(final char c) {
        return c >= '\udc00' && c <= '\udfff';
    }
    
    public static final boolean isSurrogatePair(final char c, final char c2) {
        return isHighSurrogate(c) && isLowSurrogate(c2);
    }
    
    public static int charCount(final int n) {
        return UTF16.getCharCount(n);
    }
    
    public static final int toCodePoint(final char c, final char c2) {
        return UCharacterProperty.getRawSupplementary(c, c2);
    }
    
    public static final int codePointAt(final CharSequence charSequence, int n) {
        final char char1 = charSequence.charAt(n++);
        if (isHighSurrogate(char1) && n < charSequence.length()) {
            final char char2 = charSequence.charAt(n);
            if (isLowSurrogate(char2)) {
                return toCodePoint(char1, char2);
            }
        }
        return char1;
    }
    
    public static final int codePointAt(final char[] array, int n) {
        final char c = array[n++];
        if (isHighSurrogate(c) && n < array.length) {
            final char c2 = array[n];
            if (isLowSurrogate(c2)) {
                return toCodePoint(c, c2);
            }
        }
        return c;
    }
    
    public static final int codePointAt(final char[] array, int n, final int n2) {
        if (n >= n2 || n2 > array.length) {
            throw new IndexOutOfBoundsException();
        }
        final char c = array[n++];
        if (isHighSurrogate(c) && n < n2) {
            final char c2 = array[n];
            if (isLowSurrogate(c2)) {
                return toCodePoint(c, c2);
            }
        }
        return c;
    }
    
    public static final int codePointBefore(final CharSequence charSequence, int n) {
        final char char1 = charSequence.charAt(--n);
        if (isLowSurrogate(char1) && n > 0) {
            final char char2 = charSequence.charAt(--n);
            if (isHighSurrogate(char2)) {
                return toCodePoint(char2, char1);
            }
        }
        return char1;
    }
    
    public static final int codePointBefore(final char[] array, int n) {
        final char c = array[--n];
        if (isLowSurrogate(c) && n > 0) {
            final char c2 = array[--n];
            if (isHighSurrogate(c2)) {
                return toCodePoint(c2, c);
            }
        }
        return c;
    }
    
    public static final int codePointBefore(final char[] array, int n, final int n2) {
        if (n <= n2 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        final char c = array[--n];
        if (isLowSurrogate(c) && n > n2) {
            final char c2 = array[--n];
            if (isHighSurrogate(c2)) {
                return toCodePoint(c2, c);
            }
        }
        return c;
    }
    
    public static final int toChars(final int n, final char[] array, final int n2) {
        if (n >= 0) {
            if (n < 65536) {
                array[n2] = (char)n;
                return 1;
            }
            if (n <= 1114111) {
                array[n2] = UTF16.getLeadSurrogate(n);
                array[n2 + 1] = UTF16.getTrailSurrogate(n);
                return 2;
            }
        }
        throw new IllegalArgumentException();
    }
    
    public static final char[] toChars(final int n) {
        if (n >= 0) {
            if (n < 65536) {
                return new char[] { (char)n };
            }
            if (n <= 1114111) {
                return new char[] { UTF16.getLeadSurrogate(n), UTF16.getTrailSurrogate(n) };
            }
        }
        throw new IllegalArgumentException();
    }
    
    public static byte getDirectionality(final int n) {
        return (byte)getDirection(n);
    }
    
    public static int codePointCount(final CharSequence charSequence, final int n, int i) {
        if (n < 0 || i < n || i > charSequence.length()) {
            throw new IndexOutOfBoundsException("start (" + n + ") or limit (" + i + ") invalid or out of range 0, " + charSequence.length());
        }
        int n2 = i - n;
        while (i > n) {
            char c = charSequence.charAt(--i);
            while (c >= '\udc00' && c <= '\udfff' && i > n) {
                c = charSequence.charAt(--i);
                if (c >= '\ud800' && c <= '\udbff') {
                    --n2;
                    break;
                }
            }
        }
        return n2;
    }
    
    public static int codePointCount(final char[] array, final int n, int i) {
        if (n < 0 || i < n || i > array.length) {
            throw new IndexOutOfBoundsException("start (" + n + ") or limit (" + i + ") invalid or out of range 0, " + array.length);
        }
        int n2 = i - n;
        while (i > n) {
            char c = array[--i];
            while (c >= '\udc00' && c <= '\udfff' && i > n) {
                c = array[--i];
                if (c >= '\ud800' && c <= '\udbff') {
                    --n2;
                    break;
                }
            }
        }
        return n2;
    }
    
    public static int offsetByCodePoints(final CharSequence charSequence, int n, int n2) {
        if (n < 0 || n > charSequence.length()) {
            throw new IndexOutOfBoundsException("index ( " + n + ") out of range 0, " + charSequence.length());
        }
        if (n2 < 0) {
            while (++n2 <= 0) {
                char c = charSequence.charAt(--n);
                while (c >= '\udc00' && c <= '\udfff' && n > 0) {
                    c = charSequence.charAt(--n);
                    if ((c < '\ud800' || c > '\udbff') && ++n2 > 0) {
                        return n + 1;
                    }
                }
            }
        }
        else {
            final int length = charSequence.length();
            while (--n2 >= 0) {
                char c2 = charSequence.charAt(n++);
                while (c2 >= '\ud800' && c2 <= '\udbff' && n < length) {
                    c2 = charSequence.charAt(n++);
                    if ((c2 < '\udc00' || c2 > '\udfff') && --n2 < 0) {
                        return n - 1;
                    }
                }
            }
        }
        return n;
    }
    
    public static int offsetByCodePoints(final char[] array, final int n, final int n2, int n3, int n4) {
        final int n5 = n + n2;
        if (n < 0 || n5 < n || n5 > array.length || n3 < n || n3 > n5) {
            throw new IndexOutOfBoundsException("index ( " + n3 + ") out of range " + n + ", " + n5 + " in array 0, " + array.length);
        }
        if (n4 < 0) {
            while (++n4 <= 0) {
                char c = array[--n3];
                if (n3 < n) {
                    throw new IndexOutOfBoundsException("index ( " + n3 + ") < start (" + n + ")");
                }
                while (c >= '\udc00' && c <= '\udfff' && n3 > n) {
                    c = array[--n3];
                    if ((c < '\ud800' || c > '\udbff') && ++n4 > 0) {
                        return n3 + 1;
                    }
                }
            }
        }
        else {
            while (--n4 >= 0) {
                char c2 = array[n3++];
                if (n3 > n5) {
                    throw new IndexOutOfBoundsException("index ( " + n3 + ") > limit (" + n5 + ")");
                }
                while (c2 >= '\ud800' && c2 <= '\udbff' && n3 < n5) {
                    c2 = array[n3++];
                    if ((c2 < '\udc00' || c2 > '\udfff') && --n4 < 0) {
                        return n3 - 1;
                    }
                }
            }
        }
        return n3;
    }
    
    private UCharacter() {
    }
    
    private static final class DummyValueIterator implements ValueIterator
    {
        private DummyValueIterator() {
        }
        
        public boolean next(final Element element) {
            return false;
        }
        
        public void reset() {
        }
        
        public void setRange(final int n, final int n2) {
        }
        
        DummyValueIterator(final UCharacter$1 object) {
            this();
        }
    }
    
    private static final class UCharacterTypeIterator implements RangeValueIterator
    {
        private Iterator trieIterator;
        private Trie2.Range range;
        private static final MaskType MASK_TYPE;
        
        UCharacterTypeIterator() {
            this.reset();
        }
        
        public boolean next(final Element element) {
            if (this.trieIterator.hasNext()) {
                final Trie2.Range range = this.trieIterator.next();
                this.range = range;
                if (!range.leadSurrogate) {
                    element.start = this.range.startCodePoint;
                    element.limit = this.range.endCodePoint + 1;
                    element.value = this.range.value;
                    return true;
                }
            }
            return false;
        }
        
        public void reset() {
            this.trieIterator = UCharacterProperty.INSTANCE.m_trie_.iterator(UCharacterTypeIterator.MASK_TYPE);
        }
        
        static {
            MASK_TYPE = new MaskType(null);
        }
        
        private static final class MaskType implements Trie2.ValueMapper
        {
            private MaskType() {
            }
            
            public int map(final int n) {
                return n & 0x1F;
            }
            
            MaskType(final UCharacter$1 object) {
                this();
            }
        }
    }
    
    private static class StringContextIterator implements UCaseProps.ContextIterator
    {
        protected String s;
        protected int index;
        protected int limit;
        protected int cpStart;
        protected int cpLimit;
        protected int dir;
        
        StringContextIterator(final String s) {
            this.s = s;
            this.limit = s.length();
            final int cpStart = 0;
            this.index = cpStart;
            this.cpLimit = cpStart;
            this.cpStart = cpStart;
            this.dir = 0;
        }
        
        public void setLimit(final int limit) {
            if (0 <= limit && limit <= this.s.length()) {
                this.limit = limit;
            }
            else {
                this.limit = this.s.length();
            }
        }
        
        public void moveToLimit() {
            final int limit = this.limit;
            this.cpLimit = limit;
            this.cpStart = limit;
        }
        
        public int nextCaseMapCP() {
            this.cpStart = this.cpLimit;
            if (this.cpLimit < this.limit) {
                int n = this.s.charAt(this.cpLimit++);
                final char char1;
                if ((55296 <= n || n <= 57343) && n <= 56319 && this.cpLimit < this.limit && '\udc00' <= (char1 = this.s.charAt(this.cpLimit)) && char1 <= '\udfff') {
                    ++this.cpLimit;
                    n = UCharacterProperty.getRawSupplementary((char)n, char1);
                }
                return n;
            }
            return -1;
        }
        
        public int getCPStart() {
            return this.cpStart;
        }
        
        public int getCPLimit() {
            return this.cpLimit;
        }
        
        public void reset(final int n) {
            if (n > 0) {
                this.dir = 1;
                this.index = this.cpLimit;
            }
            else if (n < 0) {
                this.dir = -1;
                this.index = this.cpStart;
            }
            else {
                this.dir = 0;
                this.index = 0;
            }
        }
        
        public int next() {
            if (this.dir > 0 && this.index < this.s.length()) {
                final int char1 = UTF16.charAt(this.s, this.index);
                this.index += UTF16.getCharCount(char1);
                return char1;
            }
            if (this.dir < 0 && this.index > 0) {
                final int char2 = UTF16.charAt(this.s, this.index - 1);
                this.index -= UTF16.getCharCount(char2);
                return char2;
            }
            return -1;
        }
    }
    
    public interface HangulSyllableType
    {
        public static final int NOT_APPLICABLE = 0;
        public static final int LEADING_JAMO = 1;
        public static final int VOWEL_JAMO = 2;
        public static final int TRAILING_JAMO = 3;
        public static final int LV_SYLLABLE = 4;
        public static final int LVT_SYLLABLE = 5;
        public static final int COUNT = 6;
    }
    
    public interface NumericType
    {
        public static final int NONE = 0;
        public static final int DECIMAL = 1;
        public static final int DIGIT = 2;
        public static final int NUMERIC = 3;
        public static final int COUNT = 4;
    }
    
    public interface LineBreak
    {
        public static final int UNKNOWN = 0;
        public static final int AMBIGUOUS = 1;
        public static final int ALPHABETIC = 2;
        public static final int BREAK_BOTH = 3;
        public static final int BREAK_AFTER = 4;
        public static final int BREAK_BEFORE = 5;
        public static final int MANDATORY_BREAK = 6;
        public static final int CONTINGENT_BREAK = 7;
        public static final int CLOSE_PUNCTUATION = 8;
        public static final int COMBINING_MARK = 9;
        public static final int CARRIAGE_RETURN = 10;
        public static final int EXCLAMATION = 11;
        public static final int GLUE = 12;
        public static final int HYPHEN = 13;
        public static final int IDEOGRAPHIC = 14;
        public static final int INSEPERABLE = 15;
        public static final int INSEPARABLE = 15;
        public static final int INFIX_NUMERIC = 16;
        public static final int LINE_FEED = 17;
        public static final int NONSTARTER = 18;
        public static final int NUMERIC = 19;
        public static final int OPEN_PUNCTUATION = 20;
        public static final int POSTFIX_NUMERIC = 21;
        public static final int PREFIX_NUMERIC = 22;
        public static final int QUOTATION = 23;
        public static final int COMPLEX_CONTEXT = 24;
        public static final int SURROGATE = 25;
        public static final int SPACE = 26;
        public static final int BREAK_SYMBOLS = 27;
        public static final int ZWSPACE = 28;
        public static final int NEXT_LINE = 29;
        public static final int WORD_JOINER = 30;
        public static final int H2 = 31;
        public static final int H3 = 32;
        public static final int JL = 33;
        public static final int JT = 34;
        public static final int JV = 35;
        public static final int CLOSE_PARENTHESIS = 36;
        public static final int CONDITIONAL_JAPANESE_STARTER = 37;
        public static final int HEBREW_LETTER = 38;
        public static final int REGIONAL_INDICATOR = 39;
        public static final int COUNT = 40;
    }
    
    public interface SentenceBreak
    {
        public static final int OTHER = 0;
        public static final int ATERM = 1;
        public static final int CLOSE = 2;
        public static final int FORMAT = 3;
        public static final int LOWER = 4;
        public static final int NUMERIC = 5;
        public static final int OLETTER = 6;
        public static final int SEP = 7;
        public static final int SP = 8;
        public static final int STERM = 9;
        public static final int UPPER = 10;
        public static final int CR = 11;
        public static final int EXTEND = 12;
        public static final int LF = 13;
        public static final int SCONTINUE = 14;
        public static final int COUNT = 15;
    }
    
    public interface WordBreak
    {
        public static final int OTHER = 0;
        public static final int ALETTER = 1;
        public static final int FORMAT = 2;
        public static final int KATAKANA = 3;
        public static final int MIDLETTER = 4;
        public static final int MIDNUM = 5;
        public static final int NUMERIC = 6;
        public static final int EXTENDNUMLET = 7;
        public static final int CR = 8;
        public static final int EXTEND = 9;
        public static final int LF = 10;
        public static final int MIDNUMLET = 11;
        public static final int NEWLINE = 12;
        public static final int REGIONAL_INDICATOR = 13;
        public static final int COUNT = 14;
    }
    
    public interface GraphemeClusterBreak
    {
        public static final int OTHER = 0;
        public static final int CONTROL = 1;
        public static final int CR = 2;
        public static final int EXTEND = 3;
        public static final int L = 4;
        public static final int LF = 5;
        public static final int LV = 6;
        public static final int LVT = 7;
        public static final int T = 8;
        public static final int V = 9;
        public static final int SPACING_MARK = 10;
        public static final int PREPEND = 11;
        public static final int REGIONAL_INDICATOR = 12;
        public static final int COUNT = 13;
    }
    
    public interface JoiningGroup
    {
        public static final int NO_JOINING_GROUP = 0;
        public static final int AIN = 1;
        public static final int ALAPH = 2;
        public static final int ALEF = 3;
        public static final int BEH = 4;
        public static final int BETH = 5;
        public static final int DAL = 6;
        public static final int DALATH_RISH = 7;
        public static final int E = 8;
        public static final int FEH = 9;
        public static final int FINAL_SEMKATH = 10;
        public static final int GAF = 11;
        public static final int GAMAL = 12;
        public static final int HAH = 13;
        public static final int TEH_MARBUTA_GOAL = 14;
        public static final int HAMZA_ON_HEH_GOAL = 14;
        public static final int HE = 15;
        public static final int HEH = 16;
        public static final int HEH_GOAL = 17;
        public static final int HETH = 18;
        public static final int KAF = 19;
        public static final int KAPH = 20;
        public static final int KNOTTED_HEH = 21;
        public static final int LAM = 22;
        public static final int LAMADH = 23;
        public static final int MEEM = 24;
        public static final int MIM = 25;
        public static final int NOON = 26;
        public static final int NUN = 27;
        public static final int PE = 28;
        public static final int QAF = 29;
        public static final int QAPH = 30;
        public static final int REH = 31;
        public static final int REVERSED_PE = 32;
        public static final int SAD = 33;
        public static final int SADHE = 34;
        public static final int SEEN = 35;
        public static final int SEMKATH = 36;
        public static final int SHIN = 37;
        public static final int SWASH_KAF = 38;
        public static final int SYRIAC_WAW = 39;
        public static final int TAH = 40;
        public static final int TAW = 41;
        public static final int TEH_MARBUTA = 42;
        public static final int TETH = 43;
        public static final int WAW = 44;
        public static final int YEH = 45;
        public static final int YEH_BARREE = 46;
        public static final int YEH_WITH_TAIL = 47;
        public static final int YUDH = 48;
        public static final int YUDH_HE = 49;
        public static final int ZAIN = 50;
        public static final int FE = 51;
        public static final int KHAPH = 52;
        public static final int ZHAIN = 53;
        public static final int BURUSHASKI_YEH_BARREE = 54;
        public static final int FARSI_YEH = 55;
        public static final int NYA = 56;
        public static final int ROHINGYA_YEH = 57;
        public static final int COUNT = 58;
    }
    
    public interface JoiningType
    {
        public static final int NON_JOINING = 0;
        public static final int JOIN_CAUSING = 1;
        public static final int DUAL_JOINING = 2;
        public static final int LEFT_JOINING = 3;
        public static final int RIGHT_JOINING = 4;
        public static final int TRANSPARENT = 5;
        public static final int COUNT = 6;
    }
    
    public interface DecompositionType
    {
        public static final int NONE = 0;
        public static final int CANONICAL = 1;
        public static final int COMPAT = 2;
        public static final int CIRCLE = 3;
        public static final int FINAL = 4;
        public static final int FONT = 5;
        public static final int FRACTION = 6;
        public static final int INITIAL = 7;
        public static final int ISOLATED = 8;
        public static final int MEDIAL = 9;
        public static final int NARROW = 10;
        public static final int NOBREAK = 11;
        public static final int SMALL = 12;
        public static final int SQUARE = 13;
        public static final int SUB = 14;
        public static final int SUPER = 15;
        public static final int VERTICAL = 16;
        public static final int WIDE = 17;
        public static final int COUNT = 18;
    }
    
    public interface EastAsianWidth
    {
        public static final int NEUTRAL = 0;
        public static final int AMBIGUOUS = 1;
        public static final int HALFWIDTH = 2;
        public static final int FULLWIDTH = 3;
        public static final int NARROW = 4;
        public static final int WIDE = 5;
        public static final int COUNT = 6;
    }
    
    public static final class UnicodeBlock extends Character.Subset
    {
        public static final int INVALID_CODE_ID = -1;
        public static final int BASIC_LATIN_ID = 1;
        public static final int LATIN_1_SUPPLEMENT_ID = 2;
        public static final int LATIN_EXTENDED_A_ID = 3;
        public static final int LATIN_EXTENDED_B_ID = 4;
        public static final int IPA_EXTENSIONS_ID = 5;
        public static final int SPACING_MODIFIER_LETTERS_ID = 6;
        public static final int COMBINING_DIACRITICAL_MARKS_ID = 7;
        public static final int GREEK_ID = 8;
        public static final int CYRILLIC_ID = 9;
        public static final int ARMENIAN_ID = 10;
        public static final int HEBREW_ID = 11;
        public static final int ARABIC_ID = 12;
        public static final int SYRIAC_ID = 13;
        public static final int THAANA_ID = 14;
        public static final int DEVANAGARI_ID = 15;
        public static final int BENGALI_ID = 16;
        public static final int GURMUKHI_ID = 17;
        public static final int GUJARATI_ID = 18;
        public static final int ORIYA_ID = 19;
        public static final int TAMIL_ID = 20;
        public static final int TELUGU_ID = 21;
        public static final int KANNADA_ID = 22;
        public static final int MALAYALAM_ID = 23;
        public static final int SINHALA_ID = 24;
        public static final int THAI_ID = 25;
        public static final int LAO_ID = 26;
        public static final int TIBETAN_ID = 27;
        public static final int MYANMAR_ID = 28;
        public static final int GEORGIAN_ID = 29;
        public static final int HANGUL_JAMO_ID = 30;
        public static final int ETHIOPIC_ID = 31;
        public static final int CHEROKEE_ID = 32;
        public static final int UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_ID = 33;
        public static final int OGHAM_ID = 34;
        public static final int RUNIC_ID = 35;
        public static final int KHMER_ID = 36;
        public static final int MONGOLIAN_ID = 37;
        public static final int LATIN_EXTENDED_ADDITIONAL_ID = 38;
        public static final int GREEK_EXTENDED_ID = 39;
        public static final int GENERAL_PUNCTUATION_ID = 40;
        public static final int SUPERSCRIPTS_AND_SUBSCRIPTS_ID = 41;
        public static final int CURRENCY_SYMBOLS_ID = 42;
        public static final int COMBINING_MARKS_FOR_SYMBOLS_ID = 43;
        public static final int LETTERLIKE_SYMBOLS_ID = 44;
        public static final int NUMBER_FORMS_ID = 45;
        public static final int ARROWS_ID = 46;
        public static final int MATHEMATICAL_OPERATORS_ID = 47;
        public static final int MISCELLANEOUS_TECHNICAL_ID = 48;
        public static final int CONTROL_PICTURES_ID = 49;
        public static final int OPTICAL_CHARACTER_RECOGNITION_ID = 50;
        public static final int ENCLOSED_ALPHANUMERICS_ID = 51;
        public static final int BOX_DRAWING_ID = 52;
        public static final int BLOCK_ELEMENTS_ID = 53;
        public static final int GEOMETRIC_SHAPES_ID = 54;
        public static final int MISCELLANEOUS_SYMBOLS_ID = 55;
        public static final int DINGBATS_ID = 56;
        public static final int BRAILLE_PATTERNS_ID = 57;
        public static final int CJK_RADICALS_SUPPLEMENT_ID = 58;
        public static final int KANGXI_RADICALS_ID = 59;
        public static final int IDEOGRAPHIC_DESCRIPTION_CHARACTERS_ID = 60;
        public static final int CJK_SYMBOLS_AND_PUNCTUATION_ID = 61;
        public static final int HIRAGANA_ID = 62;
        public static final int KATAKANA_ID = 63;
        public static final int BOPOMOFO_ID = 64;
        public static final int HANGUL_COMPATIBILITY_JAMO_ID = 65;
        public static final int KANBUN_ID = 66;
        public static final int BOPOMOFO_EXTENDED_ID = 67;
        public static final int ENCLOSED_CJK_LETTERS_AND_MONTHS_ID = 68;
        public static final int CJK_COMPATIBILITY_ID = 69;
        public static final int CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A_ID = 70;
        public static final int CJK_UNIFIED_IDEOGRAPHS_ID = 71;
        public static final int YI_SYLLABLES_ID = 72;
        public static final int YI_RADICALS_ID = 73;
        public static final int HANGUL_SYLLABLES_ID = 74;
        public static final int HIGH_SURROGATES_ID = 75;
        public static final int HIGH_PRIVATE_USE_SURROGATES_ID = 76;
        public static final int LOW_SURROGATES_ID = 77;
        public static final int PRIVATE_USE_AREA_ID = 78;
        public static final int PRIVATE_USE_ID = 78;
        public static final int CJK_COMPATIBILITY_IDEOGRAPHS_ID = 79;
        public static final int ALPHABETIC_PRESENTATION_FORMS_ID = 80;
        public static final int ARABIC_PRESENTATION_FORMS_A_ID = 81;
        public static final int COMBINING_HALF_MARKS_ID = 82;
        public static final int CJK_COMPATIBILITY_FORMS_ID = 83;
        public static final int SMALL_FORM_VARIANTS_ID = 84;
        public static final int ARABIC_PRESENTATION_FORMS_B_ID = 85;
        public static final int SPECIALS_ID = 86;
        public static final int HALFWIDTH_AND_FULLWIDTH_FORMS_ID = 87;
        public static final int OLD_ITALIC_ID = 88;
        public static final int GOTHIC_ID = 89;
        public static final int DESERET_ID = 90;
        public static final int BYZANTINE_MUSICAL_SYMBOLS_ID = 91;
        public static final int MUSICAL_SYMBOLS_ID = 92;
        public static final int MATHEMATICAL_ALPHANUMERIC_SYMBOLS_ID = 93;
        public static final int CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B_ID = 94;
        public static final int CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT_ID = 95;
        public static final int TAGS_ID = 96;
        public static final int CYRILLIC_SUPPLEMENTARY_ID = 97;
        public static final int CYRILLIC_SUPPLEMENT_ID = 97;
        public static final int TAGALOG_ID = 98;
        public static final int HANUNOO_ID = 99;
        public static final int BUHID_ID = 100;
        public static final int TAGBANWA_ID = 101;
        public static final int MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A_ID = 102;
        public static final int SUPPLEMENTAL_ARROWS_A_ID = 103;
        public static final int SUPPLEMENTAL_ARROWS_B_ID = 104;
        public static final int MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B_ID = 105;
        public static final int SUPPLEMENTAL_MATHEMATICAL_OPERATORS_ID = 106;
        public static final int KATAKANA_PHONETIC_EXTENSIONS_ID = 107;
        public static final int VARIATION_SELECTORS_ID = 108;
        public static final int SUPPLEMENTARY_PRIVATE_USE_AREA_A_ID = 109;
        public static final int SUPPLEMENTARY_PRIVATE_USE_AREA_B_ID = 110;
        public static final int LIMBU_ID = 111;
        public static final int TAI_LE_ID = 112;
        public static final int KHMER_SYMBOLS_ID = 113;
        public static final int PHONETIC_EXTENSIONS_ID = 114;
        public static final int MISCELLANEOUS_SYMBOLS_AND_ARROWS_ID = 115;
        public static final int YIJING_HEXAGRAM_SYMBOLS_ID = 116;
        public static final int LINEAR_B_SYLLABARY_ID = 117;
        public static final int LINEAR_B_IDEOGRAMS_ID = 118;
        public static final int AEGEAN_NUMBERS_ID = 119;
        public static final int UGARITIC_ID = 120;
        public static final int SHAVIAN_ID = 121;
        public static final int OSMANYA_ID = 122;
        public static final int CYPRIOT_SYLLABARY_ID = 123;
        public static final int TAI_XUAN_JING_SYMBOLS_ID = 124;
        public static final int VARIATION_SELECTORS_SUPPLEMENT_ID = 125;
        public static final int ANCIENT_GREEK_MUSICAL_NOTATION_ID = 126;
        public static final int ANCIENT_GREEK_NUMBERS_ID = 127;
        public static final int ARABIC_SUPPLEMENT_ID = 128;
        public static final int BUGINESE_ID = 129;
        public static final int CJK_STROKES_ID = 130;
        public static final int COMBINING_DIACRITICAL_MARKS_SUPPLEMENT_ID = 131;
        public static final int COPTIC_ID = 132;
        public static final int ETHIOPIC_EXTENDED_ID = 133;
        public static final int ETHIOPIC_SUPPLEMENT_ID = 134;
        public static final int GEORGIAN_SUPPLEMENT_ID = 135;
        public static final int GLAGOLITIC_ID = 136;
        public static final int KHAROSHTHI_ID = 137;
        public static final int MODIFIER_TONE_LETTERS_ID = 138;
        public static final int NEW_TAI_LUE_ID = 139;
        public static final int OLD_PERSIAN_ID = 140;
        public static final int PHONETIC_EXTENSIONS_SUPPLEMENT_ID = 141;
        public static final int SUPPLEMENTAL_PUNCTUATION_ID = 142;
        public static final int SYLOTI_NAGRI_ID = 143;
        public static final int TIFINAGH_ID = 144;
        public static final int VERTICAL_FORMS_ID = 145;
        public static final int NKO_ID = 146;
        public static final int BALINESE_ID = 147;
        public static final int LATIN_EXTENDED_C_ID = 148;
        public static final int LATIN_EXTENDED_D_ID = 149;
        public static final int PHAGS_PA_ID = 150;
        public static final int PHOENICIAN_ID = 151;
        public static final int CUNEIFORM_ID = 152;
        public static final int CUNEIFORM_NUMBERS_AND_PUNCTUATION_ID = 153;
        public static final int COUNTING_ROD_NUMERALS_ID = 154;
        public static final int SUNDANESE_ID = 155;
        public static final int LEPCHA_ID = 156;
        public static final int OL_CHIKI_ID = 157;
        public static final int CYRILLIC_EXTENDED_A_ID = 158;
        public static final int VAI_ID = 159;
        public static final int CYRILLIC_EXTENDED_B_ID = 160;
        public static final int SAURASHTRA_ID = 161;
        public static final int KAYAH_LI_ID = 162;
        public static final int REJANG_ID = 163;
        public static final int CHAM_ID = 164;
        public static final int ANCIENT_SYMBOLS_ID = 165;
        public static final int PHAISTOS_DISC_ID = 166;
        public static final int LYCIAN_ID = 167;
        public static final int CARIAN_ID = 168;
        public static final int LYDIAN_ID = 169;
        public static final int MAHJONG_TILES_ID = 170;
        public static final int DOMINO_TILES_ID = 171;
        public static final int SAMARITAN_ID = 172;
        public static final int UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED_ID = 173;
        public static final int TAI_THAM_ID = 174;
        public static final int VEDIC_EXTENSIONS_ID = 175;
        public static final int LISU_ID = 176;
        public static final int BAMUM_ID = 177;
        public static final int COMMON_INDIC_NUMBER_FORMS_ID = 178;
        public static final int DEVANAGARI_EXTENDED_ID = 179;
        public static final int HANGUL_JAMO_EXTENDED_A_ID = 180;
        public static final int JAVANESE_ID = 181;
        public static final int MYANMAR_EXTENDED_A_ID = 182;
        public static final int TAI_VIET_ID = 183;
        public static final int MEETEI_MAYEK_ID = 184;
        public static final int HANGUL_JAMO_EXTENDED_B_ID = 185;
        public static final int IMPERIAL_ARAMAIC_ID = 186;
        public static final int OLD_SOUTH_ARABIAN_ID = 187;
        public static final int AVESTAN_ID = 188;
        public static final int INSCRIPTIONAL_PARTHIAN_ID = 189;
        public static final int INSCRIPTIONAL_PAHLAVI_ID = 190;
        public static final int OLD_TURKIC_ID = 191;
        public static final int RUMI_NUMERAL_SYMBOLS_ID = 192;
        public static final int KAITHI_ID = 193;
        public static final int EGYPTIAN_HIEROGLYPHS_ID = 194;
        public static final int ENCLOSED_ALPHANUMERIC_SUPPLEMENT_ID = 195;
        public static final int ENCLOSED_IDEOGRAPHIC_SUPPLEMENT_ID = 196;
        public static final int CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C_ID = 197;
        public static final int MANDAIC_ID = 198;
        public static final int BATAK_ID = 199;
        public static final int ETHIOPIC_EXTENDED_A_ID = 200;
        public static final int BRAHMI_ID = 201;
        public static final int BAMUM_SUPPLEMENT_ID = 202;
        public static final int KANA_SUPPLEMENT_ID = 203;
        public static final int PLAYING_CARDS_ID = 204;
        public static final int MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS_ID = 205;
        public static final int EMOTICONS_ID = 206;
        public static final int TRANSPORT_AND_MAP_SYMBOLS_ID = 207;
        public static final int ALCHEMICAL_SYMBOLS_ID = 208;
        public static final int CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D_ID = 209;
        public static final int ARABIC_EXTENDED_A_ID = 210;
        public static final int ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS_ID = 211;
        public static final int CHAKMA_ID = 212;
        public static final int MEETEI_MAYEK_EXTENSIONS_ID = 213;
        public static final int MEROITIC_CURSIVE_ID = 214;
        public static final int MEROITIC_HIEROGLYPHS_ID = 215;
        public static final int MIAO_ID = 216;
        public static final int SHARADA_ID = 217;
        public static final int SORA_SOMPENG_ID = 218;
        public static final int SUNDANESE_SUPPLEMENT_ID = 219;
        public static final int TAKRI_ID = 220;
        public static final int COUNT = 221;
        private static final UnicodeBlock[] BLOCKS_;
        public static final UnicodeBlock NO_BLOCK;
        public static final UnicodeBlock BASIC_LATIN;
        public static final UnicodeBlock LATIN_1_SUPPLEMENT;
        public static final UnicodeBlock LATIN_EXTENDED_A;
        public static final UnicodeBlock LATIN_EXTENDED_B;
        public static final UnicodeBlock IPA_EXTENSIONS;
        public static final UnicodeBlock SPACING_MODIFIER_LETTERS;
        public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS;
        public static final UnicodeBlock GREEK;
        public static final UnicodeBlock CYRILLIC;
        public static final UnicodeBlock ARMENIAN;
        public static final UnicodeBlock HEBREW;
        public static final UnicodeBlock ARABIC;
        public static final UnicodeBlock SYRIAC;
        public static final UnicodeBlock THAANA;
        public static final UnicodeBlock DEVANAGARI;
        public static final UnicodeBlock BENGALI;
        public static final UnicodeBlock GURMUKHI;
        public static final UnicodeBlock GUJARATI;
        public static final UnicodeBlock ORIYA;
        public static final UnicodeBlock TAMIL;
        public static final UnicodeBlock TELUGU;
        public static final UnicodeBlock KANNADA;
        public static final UnicodeBlock MALAYALAM;
        public static final UnicodeBlock SINHALA;
        public static final UnicodeBlock THAI;
        public static final UnicodeBlock LAO;
        public static final UnicodeBlock TIBETAN;
        public static final UnicodeBlock MYANMAR;
        public static final UnicodeBlock GEORGIAN;
        public static final UnicodeBlock HANGUL_JAMO;
        public static final UnicodeBlock ETHIOPIC;
        public static final UnicodeBlock CHEROKEE;
        public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS;
        public static final UnicodeBlock OGHAM;
        public static final UnicodeBlock RUNIC;
        public static final UnicodeBlock KHMER;
        public static final UnicodeBlock MONGOLIAN;
        public static final UnicodeBlock LATIN_EXTENDED_ADDITIONAL;
        public static final UnicodeBlock GREEK_EXTENDED;
        public static final UnicodeBlock GENERAL_PUNCTUATION;
        public static final UnicodeBlock SUPERSCRIPTS_AND_SUBSCRIPTS;
        public static final UnicodeBlock CURRENCY_SYMBOLS;
        public static final UnicodeBlock COMBINING_MARKS_FOR_SYMBOLS;
        public static final UnicodeBlock LETTERLIKE_SYMBOLS;
        public static final UnicodeBlock NUMBER_FORMS;
        public static final UnicodeBlock ARROWS;
        public static final UnicodeBlock MATHEMATICAL_OPERATORS;
        public static final UnicodeBlock MISCELLANEOUS_TECHNICAL;
        public static final UnicodeBlock CONTROL_PICTURES;
        public static final UnicodeBlock OPTICAL_CHARACTER_RECOGNITION;
        public static final UnicodeBlock ENCLOSED_ALPHANUMERICS;
        public static final UnicodeBlock BOX_DRAWING;
        public static final UnicodeBlock BLOCK_ELEMENTS;
        public static final UnicodeBlock GEOMETRIC_SHAPES;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS;
        public static final UnicodeBlock DINGBATS;
        public static final UnicodeBlock BRAILLE_PATTERNS;
        public static final UnicodeBlock CJK_RADICALS_SUPPLEMENT;
        public static final UnicodeBlock KANGXI_RADICALS;
        public static final UnicodeBlock IDEOGRAPHIC_DESCRIPTION_CHARACTERS;
        public static final UnicodeBlock CJK_SYMBOLS_AND_PUNCTUATION;
        public static final UnicodeBlock HIRAGANA;
        public static final UnicodeBlock KATAKANA;
        public static final UnicodeBlock BOPOMOFO;
        public static final UnicodeBlock HANGUL_COMPATIBILITY_JAMO;
        public static final UnicodeBlock KANBUN;
        public static final UnicodeBlock BOPOMOFO_EXTENDED;
        public static final UnicodeBlock ENCLOSED_CJK_LETTERS_AND_MONTHS;
        public static final UnicodeBlock CJK_COMPATIBILITY;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS;
        public static final UnicodeBlock YI_SYLLABLES;
        public static final UnicodeBlock YI_RADICALS;
        public static final UnicodeBlock HANGUL_SYLLABLES;
        public static final UnicodeBlock HIGH_SURROGATES;
        public static final UnicodeBlock HIGH_PRIVATE_USE_SURROGATES;
        public static final UnicodeBlock LOW_SURROGATES;
        public static final UnicodeBlock PRIVATE_USE_AREA;
        public static final UnicodeBlock PRIVATE_USE;
        public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS;
        public static final UnicodeBlock ALPHABETIC_PRESENTATION_FORMS;
        public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_A;
        public static final UnicodeBlock COMBINING_HALF_MARKS;
        public static final UnicodeBlock CJK_COMPATIBILITY_FORMS;
        public static final UnicodeBlock SMALL_FORM_VARIANTS;
        public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_B;
        public static final UnicodeBlock SPECIALS;
        public static final UnicodeBlock HALFWIDTH_AND_FULLWIDTH_FORMS;
        public static final UnicodeBlock OLD_ITALIC;
        public static final UnicodeBlock GOTHIC;
        public static final UnicodeBlock DESERET;
        public static final UnicodeBlock BYZANTINE_MUSICAL_SYMBOLS;
        public static final UnicodeBlock MUSICAL_SYMBOLS;
        public static final UnicodeBlock MATHEMATICAL_ALPHANUMERIC_SYMBOLS;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;
        public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
        public static final UnicodeBlock TAGS;
        public static final UnicodeBlock CYRILLIC_SUPPLEMENTARY;
        public static final UnicodeBlock CYRILLIC_SUPPLEMENT;
        public static final UnicodeBlock TAGALOG;
        public static final UnicodeBlock HANUNOO;
        public static final UnicodeBlock BUHID;
        public static final UnicodeBlock TAGBANWA;
        public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A;
        public static final UnicodeBlock SUPPLEMENTAL_ARROWS_A;
        public static final UnicodeBlock SUPPLEMENTAL_ARROWS_B;
        public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B;
        public static final UnicodeBlock SUPPLEMENTAL_MATHEMATICAL_OPERATORS;
        public static final UnicodeBlock KATAKANA_PHONETIC_EXTENSIONS;
        public static final UnicodeBlock VARIATION_SELECTORS;
        public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_A;
        public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_B;
        public static final UnicodeBlock LIMBU;
        public static final UnicodeBlock TAI_LE;
        public static final UnicodeBlock KHMER_SYMBOLS;
        public static final UnicodeBlock PHONETIC_EXTENSIONS;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_ARROWS;
        public static final UnicodeBlock YIJING_HEXAGRAM_SYMBOLS;
        public static final UnicodeBlock LINEAR_B_SYLLABARY;
        public static final UnicodeBlock LINEAR_B_IDEOGRAMS;
        public static final UnicodeBlock AEGEAN_NUMBERS;
        public static final UnicodeBlock UGARITIC;
        public static final UnicodeBlock SHAVIAN;
        public static final UnicodeBlock OSMANYA;
        public static final UnicodeBlock CYPRIOT_SYLLABARY;
        public static final UnicodeBlock TAI_XUAN_JING_SYMBOLS;
        public static final UnicodeBlock VARIATION_SELECTORS_SUPPLEMENT;
        public static final UnicodeBlock ANCIENT_GREEK_MUSICAL_NOTATION;
        public static final UnicodeBlock ANCIENT_GREEK_NUMBERS;
        public static final UnicodeBlock ARABIC_SUPPLEMENT;
        public static final UnicodeBlock BUGINESE;
        public static final UnicodeBlock CJK_STROKES;
        public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS_SUPPLEMENT;
        public static final UnicodeBlock COPTIC;
        public static final UnicodeBlock ETHIOPIC_EXTENDED;
        public static final UnicodeBlock ETHIOPIC_SUPPLEMENT;
        public static final UnicodeBlock GEORGIAN_SUPPLEMENT;
        public static final UnicodeBlock GLAGOLITIC;
        public static final UnicodeBlock KHAROSHTHI;
        public static final UnicodeBlock MODIFIER_TONE_LETTERS;
        public static final UnicodeBlock NEW_TAI_LUE;
        public static final UnicodeBlock OLD_PERSIAN;
        public static final UnicodeBlock PHONETIC_EXTENSIONS_SUPPLEMENT;
        public static final UnicodeBlock SUPPLEMENTAL_PUNCTUATION;
        public static final UnicodeBlock SYLOTI_NAGRI;
        public static final UnicodeBlock TIFINAGH;
        public static final UnicodeBlock VERTICAL_FORMS;
        public static final UnicodeBlock NKO;
        public static final UnicodeBlock BALINESE;
        public static final UnicodeBlock LATIN_EXTENDED_C;
        public static final UnicodeBlock LATIN_EXTENDED_D;
        public static final UnicodeBlock PHAGS_PA;
        public static final UnicodeBlock PHOENICIAN;
        public static final UnicodeBlock CUNEIFORM;
        public static final UnicodeBlock CUNEIFORM_NUMBERS_AND_PUNCTUATION;
        public static final UnicodeBlock COUNTING_ROD_NUMERALS;
        public static final UnicodeBlock SUNDANESE;
        public static final UnicodeBlock LEPCHA;
        public static final UnicodeBlock OL_CHIKI;
        public static final UnicodeBlock CYRILLIC_EXTENDED_A;
        public static final UnicodeBlock VAI;
        public static final UnicodeBlock CYRILLIC_EXTENDED_B;
        public static final UnicodeBlock SAURASHTRA;
        public static final UnicodeBlock KAYAH_LI;
        public static final UnicodeBlock REJANG;
        public static final UnicodeBlock CHAM;
        public static final UnicodeBlock ANCIENT_SYMBOLS;
        public static final UnicodeBlock PHAISTOS_DISC;
        public static final UnicodeBlock LYCIAN;
        public static final UnicodeBlock CARIAN;
        public static final UnicodeBlock LYDIAN;
        public static final UnicodeBlock MAHJONG_TILES;
        public static final UnicodeBlock DOMINO_TILES;
        public static final UnicodeBlock SAMARITAN;
        public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED;
        public static final UnicodeBlock TAI_THAM;
        public static final UnicodeBlock VEDIC_EXTENSIONS;
        public static final UnicodeBlock LISU;
        public static final UnicodeBlock BAMUM;
        public static final UnicodeBlock COMMON_INDIC_NUMBER_FORMS;
        public static final UnicodeBlock DEVANAGARI_EXTENDED;
        public static final UnicodeBlock HANGUL_JAMO_EXTENDED_A;
        public static final UnicodeBlock JAVANESE;
        public static final UnicodeBlock MYANMAR_EXTENDED_A;
        public static final UnicodeBlock TAI_VIET;
        public static final UnicodeBlock MEETEI_MAYEK;
        public static final UnicodeBlock HANGUL_JAMO_EXTENDED_B;
        public static final UnicodeBlock IMPERIAL_ARAMAIC;
        public static final UnicodeBlock OLD_SOUTH_ARABIAN;
        public static final UnicodeBlock AVESTAN;
        public static final UnicodeBlock INSCRIPTIONAL_PARTHIAN;
        public static final UnicodeBlock INSCRIPTIONAL_PAHLAVI;
        public static final UnicodeBlock OLD_TURKIC;
        public static final UnicodeBlock RUMI_NUMERAL_SYMBOLS;
        public static final UnicodeBlock KAITHI;
        public static final UnicodeBlock EGYPTIAN_HIEROGLYPHS;
        public static final UnicodeBlock ENCLOSED_ALPHANUMERIC_SUPPLEMENT;
        public static final UnicodeBlock ENCLOSED_IDEOGRAPHIC_SUPPLEMENT;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C;
        public static final UnicodeBlock MANDAIC;
        public static final UnicodeBlock BATAK;
        public static final UnicodeBlock ETHIOPIC_EXTENDED_A;
        public static final UnicodeBlock BRAHMI;
        public static final UnicodeBlock BAMUM_SUPPLEMENT;
        public static final UnicodeBlock KANA_SUPPLEMENT;
        public static final UnicodeBlock PLAYING_CARDS;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS;
        public static final UnicodeBlock EMOTICONS;
        public static final UnicodeBlock TRANSPORT_AND_MAP_SYMBOLS;
        public static final UnicodeBlock ALCHEMICAL_SYMBOLS;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D;
        public static final UnicodeBlock ARABIC_EXTENDED_A;
        public static final UnicodeBlock ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS;
        public static final UnicodeBlock CHAKMA;
        public static final UnicodeBlock MEETEI_MAYEK_EXTENSIONS;
        public static final UnicodeBlock MEROITIC_CURSIVE;
        public static final UnicodeBlock MEROITIC_HIEROGLYPHS;
        public static final UnicodeBlock MIAO;
        public static final UnicodeBlock SHARADA;
        public static final UnicodeBlock SORA_SOMPENG;
        public static final UnicodeBlock SUNDANESE_SUPPLEMENT;
        public static final UnicodeBlock TAKRI;
        public static final UnicodeBlock INVALID_CODE;
        private static SoftReference mref;
        private int m_id_;
        
        public static UnicodeBlock getInstance(final int n) {
            if (n >= 0 && n < UnicodeBlock.BLOCKS_.length) {
                return UnicodeBlock.BLOCKS_[n];
            }
            return UnicodeBlock.INVALID_CODE;
        }
        
        public static UnicodeBlock of(final int n) {
            if (n > 1114111) {
                return UnicodeBlock.INVALID_CODE;
            }
            return getInstance(UCharacterProperty.INSTANCE.getIntPropertyValue(n, 4097));
        }
        
        public static final UnicodeBlock forName(final String s) {
            Map<?, ?> map = null;
            if (UnicodeBlock.mref != null) {
                map = UnicodeBlock.mref.get();
            }
            if (map == null) {
                map = new HashMap<Object, Object>(UnicodeBlock.BLOCKS_.length);
                while (0 < UnicodeBlock.BLOCKS_.length) {
                    final UnicodeBlock unicodeBlock = UnicodeBlock.BLOCKS_[0];
                    map.put(trimBlockName(UCharacter.getPropertyValueName(4097, unicodeBlock.getID(), 1)), unicodeBlock);
                    int n = 0;
                    ++n;
                }
                UnicodeBlock.mref = new SoftReference(map);
            }
            final UnicodeBlock unicodeBlock2 = (UnicodeBlock)map.get(trimBlockName(s));
            if (unicodeBlock2 == null) {
                throw new IllegalArgumentException();
            }
            return unicodeBlock2;
        }
        
        private static String trimBlockName(final String s) {
            final String upperCase = s.toUpperCase(Locale.ENGLISH);
            final StringBuilder sb = new StringBuilder(upperCase.length());
            while (0 < upperCase.length()) {
                final char char1 = upperCase.charAt(0);
                if (char1 != ' ' && char1 != '_' && char1 != '-') {
                    sb.append(char1);
                }
                int n = 0;
                ++n;
            }
            return sb.toString();
        }
        
        public int getID() {
            return this.m_id_;
        }
        
        private UnicodeBlock(final String s, final int id_) {
            super(s);
            this.m_id_ = id_;
            if (id_ >= 0) {
                UnicodeBlock.BLOCKS_[id_] = this;
            }
        }
        
        static {
            BLOCKS_ = new UnicodeBlock[221];
            NO_BLOCK = new UnicodeBlock("NO_BLOCK", 0);
            BASIC_LATIN = new UnicodeBlock("BASIC_LATIN", 1);
            LATIN_1_SUPPLEMENT = new UnicodeBlock("LATIN_1_SUPPLEMENT", 2);
            LATIN_EXTENDED_A = new UnicodeBlock("LATIN_EXTENDED_A", 3);
            LATIN_EXTENDED_B = new UnicodeBlock("LATIN_EXTENDED_B", 4);
            IPA_EXTENSIONS = new UnicodeBlock("IPA_EXTENSIONS", 5);
            SPACING_MODIFIER_LETTERS = new UnicodeBlock("SPACING_MODIFIER_LETTERS", 6);
            COMBINING_DIACRITICAL_MARKS = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS", 7);
            GREEK = new UnicodeBlock("GREEK", 8);
            CYRILLIC = new UnicodeBlock("CYRILLIC", 9);
            ARMENIAN = new UnicodeBlock("ARMENIAN", 10);
            HEBREW = new UnicodeBlock("HEBREW", 11);
            ARABIC = new UnicodeBlock("ARABIC", 12);
            SYRIAC = new UnicodeBlock("SYRIAC", 13);
            THAANA = new UnicodeBlock("THAANA", 14);
            DEVANAGARI = new UnicodeBlock("DEVANAGARI", 15);
            BENGALI = new UnicodeBlock("BENGALI", 16);
            GURMUKHI = new UnicodeBlock("GURMUKHI", 17);
            GUJARATI = new UnicodeBlock("GUJARATI", 18);
            ORIYA = new UnicodeBlock("ORIYA", 19);
            TAMIL = new UnicodeBlock("TAMIL", 20);
            TELUGU = new UnicodeBlock("TELUGU", 21);
            KANNADA = new UnicodeBlock("KANNADA", 22);
            MALAYALAM = new UnicodeBlock("MALAYALAM", 23);
            SINHALA = new UnicodeBlock("SINHALA", 24);
            THAI = new UnicodeBlock("THAI", 25);
            LAO = new UnicodeBlock("LAO", 26);
            TIBETAN = new UnicodeBlock("TIBETAN", 27);
            MYANMAR = new UnicodeBlock("MYANMAR", 28);
            GEORGIAN = new UnicodeBlock("GEORGIAN", 29);
            HANGUL_JAMO = new UnicodeBlock("HANGUL_JAMO", 30);
            ETHIOPIC = new UnicodeBlock("ETHIOPIC", 31);
            CHEROKEE = new UnicodeBlock("CHEROKEE", 32);
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS", 33);
            OGHAM = new UnicodeBlock("OGHAM", 34);
            RUNIC = new UnicodeBlock("RUNIC", 35);
            KHMER = new UnicodeBlock("KHMER", 36);
            MONGOLIAN = new UnicodeBlock("MONGOLIAN", 37);
            LATIN_EXTENDED_ADDITIONAL = new UnicodeBlock("LATIN_EXTENDED_ADDITIONAL", 38);
            GREEK_EXTENDED = new UnicodeBlock("GREEK_EXTENDED", 39);
            GENERAL_PUNCTUATION = new UnicodeBlock("GENERAL_PUNCTUATION", 40);
            SUPERSCRIPTS_AND_SUBSCRIPTS = new UnicodeBlock("SUPERSCRIPTS_AND_SUBSCRIPTS", 41);
            CURRENCY_SYMBOLS = new UnicodeBlock("CURRENCY_SYMBOLS", 42);
            COMBINING_MARKS_FOR_SYMBOLS = new UnicodeBlock("COMBINING_MARKS_FOR_SYMBOLS", 43);
            LETTERLIKE_SYMBOLS = new UnicodeBlock("LETTERLIKE_SYMBOLS", 44);
            NUMBER_FORMS = new UnicodeBlock("NUMBER_FORMS", 45);
            ARROWS = new UnicodeBlock("ARROWS", 46);
            MATHEMATICAL_OPERATORS = new UnicodeBlock("MATHEMATICAL_OPERATORS", 47);
            MISCELLANEOUS_TECHNICAL = new UnicodeBlock("MISCELLANEOUS_TECHNICAL", 48);
            CONTROL_PICTURES = new UnicodeBlock("CONTROL_PICTURES", 49);
            OPTICAL_CHARACTER_RECOGNITION = new UnicodeBlock("OPTICAL_CHARACTER_RECOGNITION", 50);
            ENCLOSED_ALPHANUMERICS = new UnicodeBlock("ENCLOSED_ALPHANUMERICS", 51);
            BOX_DRAWING = new UnicodeBlock("BOX_DRAWING", 52);
            BLOCK_ELEMENTS = new UnicodeBlock("BLOCK_ELEMENTS", 53);
            GEOMETRIC_SHAPES = new UnicodeBlock("GEOMETRIC_SHAPES", 54);
            MISCELLANEOUS_SYMBOLS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS", 55);
            DINGBATS = new UnicodeBlock("DINGBATS", 56);
            BRAILLE_PATTERNS = new UnicodeBlock("BRAILLE_PATTERNS", 57);
            CJK_RADICALS_SUPPLEMENT = new UnicodeBlock("CJK_RADICALS_SUPPLEMENT", 58);
            KANGXI_RADICALS = new UnicodeBlock("KANGXI_RADICALS", 59);
            IDEOGRAPHIC_DESCRIPTION_CHARACTERS = new UnicodeBlock("IDEOGRAPHIC_DESCRIPTION_CHARACTERS", 60);
            CJK_SYMBOLS_AND_PUNCTUATION = new UnicodeBlock("CJK_SYMBOLS_AND_PUNCTUATION", 61);
            HIRAGANA = new UnicodeBlock("HIRAGANA", 62);
            KATAKANA = new UnicodeBlock("KATAKANA", 63);
            BOPOMOFO = new UnicodeBlock("BOPOMOFO", 64);
            HANGUL_COMPATIBILITY_JAMO = new UnicodeBlock("HANGUL_COMPATIBILITY_JAMO", 65);
            KANBUN = new UnicodeBlock("KANBUN", 66);
            BOPOMOFO_EXTENDED = new UnicodeBlock("BOPOMOFO_EXTENDED", 67);
            ENCLOSED_CJK_LETTERS_AND_MONTHS = new UnicodeBlock("ENCLOSED_CJK_LETTERS_AND_MONTHS", 68);
            CJK_COMPATIBILITY = new UnicodeBlock("CJK_COMPATIBILITY", 69);
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A", 70);
            CJK_UNIFIED_IDEOGRAPHS = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS", 71);
            YI_SYLLABLES = new UnicodeBlock("YI_SYLLABLES", 72);
            YI_RADICALS = new UnicodeBlock("YI_RADICALS", 73);
            HANGUL_SYLLABLES = new UnicodeBlock("HANGUL_SYLLABLES", 74);
            HIGH_SURROGATES = new UnicodeBlock("HIGH_SURROGATES", 75);
            HIGH_PRIVATE_USE_SURROGATES = new UnicodeBlock("HIGH_PRIVATE_USE_SURROGATES", 76);
            LOW_SURROGATES = new UnicodeBlock("LOW_SURROGATES", 77);
            PRIVATE_USE_AREA = new UnicodeBlock("PRIVATE_USE_AREA", 78);
            PRIVATE_USE = UnicodeBlock.PRIVATE_USE_AREA;
            CJK_COMPATIBILITY_IDEOGRAPHS = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS", 79);
            ALPHABETIC_PRESENTATION_FORMS = new UnicodeBlock("ALPHABETIC_PRESENTATION_FORMS", 80);
            ARABIC_PRESENTATION_FORMS_A = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_A", 81);
            COMBINING_HALF_MARKS = new UnicodeBlock("COMBINING_HALF_MARKS", 82);
            CJK_COMPATIBILITY_FORMS = new UnicodeBlock("CJK_COMPATIBILITY_FORMS", 83);
            SMALL_FORM_VARIANTS = new UnicodeBlock("SMALL_FORM_VARIANTS", 84);
            ARABIC_PRESENTATION_FORMS_B = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_B", 85);
            SPECIALS = new UnicodeBlock("SPECIALS", 86);
            HALFWIDTH_AND_FULLWIDTH_FORMS = new UnicodeBlock("HALFWIDTH_AND_FULLWIDTH_FORMS", 87);
            OLD_ITALIC = new UnicodeBlock("OLD_ITALIC", 88);
            GOTHIC = new UnicodeBlock("GOTHIC", 89);
            DESERET = new UnicodeBlock("DESERET", 90);
            BYZANTINE_MUSICAL_SYMBOLS = new UnicodeBlock("BYZANTINE_MUSICAL_SYMBOLS", 91);
            MUSICAL_SYMBOLS = new UnicodeBlock("MUSICAL_SYMBOLS", 92);
            MATHEMATICAL_ALPHANUMERIC_SYMBOLS = new UnicodeBlock("MATHEMATICAL_ALPHANUMERIC_SYMBOLS", 93);
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B", 94);
            CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT", 95);
            TAGS = new UnicodeBlock("TAGS", 96);
            CYRILLIC_SUPPLEMENTARY = new UnicodeBlock("CYRILLIC_SUPPLEMENTARY", 97);
            CYRILLIC_SUPPLEMENT = new UnicodeBlock("CYRILLIC_SUPPLEMENT", 97);
            TAGALOG = new UnicodeBlock("TAGALOG", 98);
            HANUNOO = new UnicodeBlock("HANUNOO", 99);
            BUHID = new UnicodeBlock("BUHID", 100);
            TAGBANWA = new UnicodeBlock("TAGBANWA", 101);
            MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A", 102);
            SUPPLEMENTAL_ARROWS_A = new UnicodeBlock("SUPPLEMENTAL_ARROWS_A", 103);
            SUPPLEMENTAL_ARROWS_B = new UnicodeBlock("SUPPLEMENTAL_ARROWS_B", 104);
            MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B", 105);
            SUPPLEMENTAL_MATHEMATICAL_OPERATORS = new UnicodeBlock("SUPPLEMENTAL_MATHEMATICAL_OPERATORS", 106);
            KATAKANA_PHONETIC_EXTENSIONS = new UnicodeBlock("KATAKANA_PHONETIC_EXTENSIONS", 107);
            VARIATION_SELECTORS = new UnicodeBlock("VARIATION_SELECTORS", 108);
            SUPPLEMENTARY_PRIVATE_USE_AREA_A = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_A", 109);
            SUPPLEMENTARY_PRIVATE_USE_AREA_B = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_B", 110);
            LIMBU = new UnicodeBlock("LIMBU", 111);
            TAI_LE = new UnicodeBlock("TAI_LE", 112);
            KHMER_SYMBOLS = new UnicodeBlock("KHMER_SYMBOLS", 113);
            PHONETIC_EXTENSIONS = new UnicodeBlock("PHONETIC_EXTENSIONS", 114);
            MISCELLANEOUS_SYMBOLS_AND_ARROWS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_ARROWS", 115);
            YIJING_HEXAGRAM_SYMBOLS = new UnicodeBlock("YIJING_HEXAGRAM_SYMBOLS", 116);
            LINEAR_B_SYLLABARY = new UnicodeBlock("LINEAR_B_SYLLABARY", 117);
            LINEAR_B_IDEOGRAMS = new UnicodeBlock("LINEAR_B_IDEOGRAMS", 118);
            AEGEAN_NUMBERS = new UnicodeBlock("AEGEAN_NUMBERS", 119);
            UGARITIC = new UnicodeBlock("UGARITIC", 120);
            SHAVIAN = new UnicodeBlock("SHAVIAN", 121);
            OSMANYA = new UnicodeBlock("OSMANYA", 122);
            CYPRIOT_SYLLABARY = new UnicodeBlock("CYPRIOT_SYLLABARY", 123);
            TAI_XUAN_JING_SYMBOLS = new UnicodeBlock("TAI_XUAN_JING_SYMBOLS", 124);
            VARIATION_SELECTORS_SUPPLEMENT = new UnicodeBlock("VARIATION_SELECTORS_SUPPLEMENT", 125);
            ANCIENT_GREEK_MUSICAL_NOTATION = new UnicodeBlock("ANCIENT_GREEK_MUSICAL_NOTATION", 126);
            ANCIENT_GREEK_NUMBERS = new UnicodeBlock("ANCIENT_GREEK_NUMBERS", 127);
            ARABIC_SUPPLEMENT = new UnicodeBlock("ARABIC_SUPPLEMENT", 128);
            BUGINESE = new UnicodeBlock("BUGINESE", 129);
            CJK_STROKES = new UnicodeBlock("CJK_STROKES", 130);
            COMBINING_DIACRITICAL_MARKS_SUPPLEMENT = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS_SUPPLEMENT", 131);
            COPTIC = new UnicodeBlock("COPTIC", 132);
            ETHIOPIC_EXTENDED = new UnicodeBlock("ETHIOPIC_EXTENDED", 133);
            ETHIOPIC_SUPPLEMENT = new UnicodeBlock("ETHIOPIC_SUPPLEMENT", 134);
            GEORGIAN_SUPPLEMENT = new UnicodeBlock("GEORGIAN_SUPPLEMENT", 135);
            GLAGOLITIC = new UnicodeBlock("GLAGOLITIC", 136);
            KHAROSHTHI = new UnicodeBlock("KHAROSHTHI", 137);
            MODIFIER_TONE_LETTERS = new UnicodeBlock("MODIFIER_TONE_LETTERS", 138);
            NEW_TAI_LUE = new UnicodeBlock("NEW_TAI_LUE", 139);
            OLD_PERSIAN = new UnicodeBlock("OLD_PERSIAN", 140);
            PHONETIC_EXTENSIONS_SUPPLEMENT = new UnicodeBlock("PHONETIC_EXTENSIONS_SUPPLEMENT", 141);
            SUPPLEMENTAL_PUNCTUATION = new UnicodeBlock("SUPPLEMENTAL_PUNCTUATION", 142);
            SYLOTI_NAGRI = new UnicodeBlock("SYLOTI_NAGRI", 143);
            TIFINAGH = new UnicodeBlock("TIFINAGH", 144);
            VERTICAL_FORMS = new UnicodeBlock("VERTICAL_FORMS", 145);
            NKO = new UnicodeBlock("NKO", 146);
            BALINESE = new UnicodeBlock("BALINESE", 147);
            LATIN_EXTENDED_C = new UnicodeBlock("LATIN_EXTENDED_C", 148);
            LATIN_EXTENDED_D = new UnicodeBlock("LATIN_EXTENDED_D", 149);
            PHAGS_PA = new UnicodeBlock("PHAGS_PA", 150);
            PHOENICIAN = new UnicodeBlock("PHOENICIAN", 151);
            CUNEIFORM = new UnicodeBlock("CUNEIFORM", 152);
            CUNEIFORM_NUMBERS_AND_PUNCTUATION = new UnicodeBlock("CUNEIFORM_NUMBERS_AND_PUNCTUATION", 153);
            COUNTING_ROD_NUMERALS = new UnicodeBlock("COUNTING_ROD_NUMERALS", 154);
            SUNDANESE = new UnicodeBlock("SUNDANESE", 155);
            LEPCHA = new UnicodeBlock("LEPCHA", 156);
            OL_CHIKI = new UnicodeBlock("OL_CHIKI", 157);
            CYRILLIC_EXTENDED_A = new UnicodeBlock("CYRILLIC_EXTENDED_A", 158);
            VAI = new UnicodeBlock("VAI", 159);
            CYRILLIC_EXTENDED_B = new UnicodeBlock("CYRILLIC_EXTENDED_B", 160);
            SAURASHTRA = new UnicodeBlock("SAURASHTRA", 161);
            KAYAH_LI = new UnicodeBlock("KAYAH_LI", 162);
            REJANG = new UnicodeBlock("REJANG", 163);
            CHAM = new UnicodeBlock("CHAM", 164);
            ANCIENT_SYMBOLS = new UnicodeBlock("ANCIENT_SYMBOLS", 165);
            PHAISTOS_DISC = new UnicodeBlock("PHAISTOS_DISC", 166);
            LYCIAN = new UnicodeBlock("LYCIAN", 167);
            CARIAN = new UnicodeBlock("CARIAN", 168);
            LYDIAN = new UnicodeBlock("LYDIAN", 169);
            MAHJONG_TILES = new UnicodeBlock("MAHJONG_TILES", 170);
            DOMINO_TILES = new UnicodeBlock("DOMINO_TILES", 171);
            SAMARITAN = new UnicodeBlock("SAMARITAN", 172);
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED", 173);
            TAI_THAM = new UnicodeBlock("TAI_THAM", 174);
            VEDIC_EXTENSIONS = new UnicodeBlock("VEDIC_EXTENSIONS", 175);
            LISU = new UnicodeBlock("LISU", 176);
            BAMUM = new UnicodeBlock("BAMUM", 177);
            COMMON_INDIC_NUMBER_FORMS = new UnicodeBlock("COMMON_INDIC_NUMBER_FORMS", 178);
            DEVANAGARI_EXTENDED = new UnicodeBlock("DEVANAGARI_EXTENDED", 179);
            HANGUL_JAMO_EXTENDED_A = new UnicodeBlock("HANGUL_JAMO_EXTENDED_A", 180);
            JAVANESE = new UnicodeBlock("JAVANESE", 181);
            MYANMAR_EXTENDED_A = new UnicodeBlock("MYANMAR_EXTENDED_A", 182);
            TAI_VIET = new UnicodeBlock("TAI_VIET", 183);
            MEETEI_MAYEK = new UnicodeBlock("MEETEI_MAYEK", 184);
            HANGUL_JAMO_EXTENDED_B = new UnicodeBlock("HANGUL_JAMO_EXTENDED_B", 185);
            IMPERIAL_ARAMAIC = new UnicodeBlock("IMPERIAL_ARAMAIC", 186);
            OLD_SOUTH_ARABIAN = new UnicodeBlock("OLD_SOUTH_ARABIAN", 187);
            AVESTAN = new UnicodeBlock("AVESTAN", 188);
            INSCRIPTIONAL_PARTHIAN = new UnicodeBlock("INSCRIPTIONAL_PARTHIAN", 189);
            INSCRIPTIONAL_PAHLAVI = new UnicodeBlock("INSCRIPTIONAL_PAHLAVI", 190);
            OLD_TURKIC = new UnicodeBlock("OLD_TURKIC", 191);
            RUMI_NUMERAL_SYMBOLS = new UnicodeBlock("RUMI_NUMERAL_SYMBOLS", 192);
            KAITHI = new UnicodeBlock("KAITHI", 193);
            EGYPTIAN_HIEROGLYPHS = new UnicodeBlock("EGYPTIAN_HIEROGLYPHS", 194);
            ENCLOSED_ALPHANUMERIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_ALPHANUMERIC_SUPPLEMENT", 195);
            ENCLOSED_IDEOGRAPHIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_IDEOGRAPHIC_SUPPLEMENT", 196);
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C", 197);
            MANDAIC = new UnicodeBlock("MANDAIC", 198);
            BATAK = new UnicodeBlock("BATAK", 199);
            ETHIOPIC_EXTENDED_A = new UnicodeBlock("ETHIOPIC_EXTENDED_A", 200);
            BRAHMI = new UnicodeBlock("BRAHMI", 201);
            BAMUM_SUPPLEMENT = new UnicodeBlock("BAMUM_SUPPLEMENT", 202);
            KANA_SUPPLEMENT = new UnicodeBlock("KANA_SUPPLEMENT", 203);
            PLAYING_CARDS = new UnicodeBlock("PLAYING_CARDS", 204);
            MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS", 205);
            EMOTICONS = new UnicodeBlock("EMOTICONS", 206);
            TRANSPORT_AND_MAP_SYMBOLS = new UnicodeBlock("TRANSPORT_AND_MAP_SYMBOLS", 207);
            ALCHEMICAL_SYMBOLS = new UnicodeBlock("ALCHEMICAL_SYMBOLS", 208);
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D", 209);
            ARABIC_EXTENDED_A = new UnicodeBlock("ARABIC_EXTENDED_A", 210);
            ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS = new UnicodeBlock("ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS", 211);
            CHAKMA = new UnicodeBlock("CHAKMA", 212);
            MEETEI_MAYEK_EXTENSIONS = new UnicodeBlock("MEETEI_MAYEK_EXTENSIONS", 213);
            MEROITIC_CURSIVE = new UnicodeBlock("MEROITIC_CURSIVE", 214);
            MEROITIC_HIEROGLYPHS = new UnicodeBlock("MEROITIC_HIEROGLYPHS", 215);
            MIAO = new UnicodeBlock("MIAO", 216);
            SHARADA = new UnicodeBlock("SHARADA", 217);
            SORA_SOMPENG = new UnicodeBlock("SORA_SOMPENG", 218);
            SUNDANESE_SUPPLEMENT = new UnicodeBlock("SUNDANESE_SUPPLEMENT", 219);
            TAKRI = new UnicodeBlock("TAKRI", 220);
            INVALID_CODE = new UnicodeBlock("INVALID_CODE", -1);
            while (UnicodeBlock.BLOCKS_[0] != null) {
                int n = 0;
                ++n;
            }
            throw new IllegalStateException("UnicodeBlock.BLOCKS_[" + 0 + "] not initialized");
        }
    }
}
