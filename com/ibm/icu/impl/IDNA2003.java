package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public final class IDNA2003
{
    private static char[] ACE_PREFIX;
    private static final int MAX_LABEL_LENGTH = 63;
    private static final int HYPHEN = 45;
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    private static final int LOWER_CASE_DELTA = 32;
    private static final int FULL_STOP = 46;
    private static final int MAX_DOMAIN_NAME_LENGTH = 255;
    private static final StringPrep namePrep;
    
    private static boolean startsWithPrefix(final StringBuffer sb) {
        if (sb.length() < IDNA2003.ACE_PREFIX.length) {
            return false;
        }
        while (0 < IDNA2003.ACE_PREFIX.length) {
            if (toASCIILower(sb.charAt(0)) != IDNA2003.ACE_PREFIX[0]) {}
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private static char toASCIILower(final char c) {
        if ('A' <= c && c <= 'Z') {
            return (char)(c + ' ');
        }
        return c;
    }
    
    private static StringBuffer toASCIILower(final CharSequence charSequence) {
        final StringBuffer sb = new StringBuffer();
        while (0 < charSequence.length()) {
            sb.append(toASCIILower(charSequence.charAt(0)));
            int n = 0;
            ++n;
        }
        return sb;
    }
    
    private static int compareCaseInsensitiveASCII(final StringBuffer sb, final StringBuffer sb2) {
        while (0 != sb.length()) {
            final char char1 = sb.charAt(0);
            final char char2 = sb2.charAt(0);
            if (char1 != char2) {
                final int n = toASCIILower(char1) - toASCIILower(char2);
                if (n != 0) {
                    return n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static int getSeparatorIndex(final char[] array, int i, final int n) {
        while (i < n) {
            if (isLabelSeparator(array[i])) {
                return i;
            }
            ++i;
        }
        return i;
    }
    
    private static boolean isLDHChar(final int n) {
        return n <= 122 && (n == 45 || (48 <= n && n <= 57) || (65 <= n && n <= 90) || (97 <= n && n <= 122));
    }
    
    private static boolean isLabelSeparator(final int n) {
        switch (n) {
            case 46:
            case 12290:
            case 65294:
            case 65377: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static StringBuffer convertToASCII(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        final boolean b = (n & 0x2) != 0x0;
        int next;
        while ((next = uCharacterIterator.next()) != -1) {
            if (next > 127) {
                continue;
            }
        }
        uCharacterIterator.setToStart();
        StringBuffer prepare;
        if (!false) {
            prepare = IDNA2003.namePrep.prepare(uCharacterIterator, n);
        }
        else {
            prepare = new StringBuffer(uCharacterIterator.getText());
        }
        final int length = prepare.length();
        if (length == 0) {
            throw new StringPrepParseException("Found zero length lable after NamePrep.", 10);
        }
        StringBuffer sb = new StringBuffer();
        while (0 < length) {
            final char char1 = prepare.charAt(0);
            if (char1 <= '\u007f') {
                if (!isLDHChar(char1)) {}
            }
            int n2 = 0;
            ++n2;
        }
        if (b && (!false || prepare.charAt(0) == '-' || prepare.charAt(prepare.length() - 1) == '-')) {
            if (!false) {
                throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, prepare.toString(), (-1 > 0) ? -2 : -1);
            }
            if (prepare.charAt(0) == '-') {
                throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, prepare.toString(), 0);
            }
            throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, prepare.toString(), (length > 0) ? (length - 1) : length);
        }
        else {
            if (false) {
                sb = prepare;
            }
            else {
                if (startsWithPrefix(prepare)) {
                    throw new StringPrepParseException("The input does not start with the ACE Prefix.", 6, prepare.toString(), 0);
                }
                final StringBuffer asciiLower = toASCIILower(Punycode.encode(prepare, new boolean[length]));
                sb.append(IDNA2003.ACE_PREFIX, 0, IDNA2003.ACE_PREFIX.length);
                sb.append(asciiLower);
            }
            if (sb.length() > 63) {
                throw new StringPrepParseException("The labels in the input are too long. Length > 63.", 8, sb.toString(), 0);
            }
            return sb;
        }
    }
    
    public static StringBuffer convertIDNToASCII(final String s, final int n) throws StringPrepParseException {
        final char[] charArray = s.toCharArray();
        final StringBuffer sb = new StringBuffer();
        while (true) {
            int separatorIndex = getSeparatorIndex(charArray, 0, charArray.length);
            final String s2 = new String(charArray, 0, 0);
            if (s2.length() != 0 || 0 != charArray.length) {
                sb.append(convertToASCII(UCharacterIterator.getInstance(s2), n));
            }
            if (0 == charArray.length) {
                break;
            }
            ++separatorIndex;
            sb.append('.');
        }
        if (sb.length() > 255) {
            throw new StringPrepParseException("The output exceed the max allowed length.", 11);
        }
        return sb;
    }
    
    public static StringBuffer convertToUnicode(final UCharacterIterator uCharacterIterator, final int n) throws StringPrepParseException {
        final boolean[] array = null;
        final int index = uCharacterIterator.getIndex();
        int next;
        while ((next = uCharacterIterator.next()) != -1) {
            if (next > 127) {
                continue;
            }
        }
        StringBuffer prepare;
        if (!false) {
            uCharacterIterator.setIndex(index);
            prepare = IDNA2003.namePrep.prepare(uCharacterIterator, n);
        }
        else {
            prepare = new StringBuffer(uCharacterIterator.getText());
        }
        if (startsWithPrefix(prepare)) {
            StringBuffer sb = new StringBuffer(Punycode.decode(prepare.substring(IDNA2003.ACE_PREFIX.length, prepare.length()), array));
            if (sb != null && compareCaseInsensitiveASCII(prepare, convertToASCII(UCharacterIterator.getInstance(sb), n)) != 0) {
                sb = null;
            }
            if (sb != null) {
                return sb;
            }
        }
        return new StringBuffer(uCharacterIterator.getText());
    }
    
    public static StringBuffer convertIDNToUnicode(final String s, final int n) throws StringPrepParseException {
        final char[] charArray = s.toCharArray();
        final StringBuffer sb = new StringBuffer();
        while (true) {
            int separatorIndex = getSeparatorIndex(charArray, 0, charArray.length);
            final String s2 = new String(charArray, 0, 0);
            if (s2.length() == 0 && 0 != charArray.length) {
                throw new StringPrepParseException("Found zero length lable after NamePrep.", 10);
            }
            sb.append(convertToUnicode(UCharacterIterator.getInstance(s2), n));
            if (0 == charArray.length) {
                if (sb.length() > 255) {
                    throw new StringPrepParseException("The output exceed the max allowed length.", 11);
                }
                return sb;
            }
            else {
                sb.append(charArray[0]);
                ++separatorIndex;
            }
        }
    }
    
    public static int compare(final String s, final String s2, final int n) throws StringPrepParseException {
        return compareCaseInsensitiveASCII(convertIDNToASCII(s, n), convertIDNToASCII(s2, n));
    }
    
    static {
        IDNA2003.ACE_PREFIX = new char[] { 'x', 'n', '-', '-' };
        namePrep = StringPrep.getInstance(0);
    }
}
