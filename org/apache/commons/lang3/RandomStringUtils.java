package org.apache.commons.lang3;

import java.util.*;

public class RandomStringUtils
{
    private static final Random RANDOM;
    
    public static String random(final int n) {
        return random(n, false, false);
    }
    
    public static String randomAscii(final int n) {
        return random(n, 32, 127, false, false);
    }
    
    public static String randomAlphabetic(final int n) {
        return random(n, true, false);
    }
    
    public static String randomAlphanumeric(final int n) {
        return random(n, true, true);
    }
    
    public static String randomNumeric(final int n) {
        return random(n, false, true);
    }
    
    public static String random(final int n, final boolean b, final boolean b2) {
        return random(n, 0, 0, b, b2);
    }
    
    public static String random(final int n, final int n2, final int n3, final boolean b, final boolean b2) {
        return random(n, n2, n3, b, b2, null, RandomStringUtils.RANDOM);
    }
    
    public static String random(final int n, final int n2, final int n3, final boolean b, final boolean b2, final char... array) {
        return random(n, n2, n3, b, b2, array, RandomStringUtils.RANDOM);
    }
    
    public static String random(int n, final int n2, int length, final boolean b, final boolean b2, final char[] array, final Random random) {
        if (n == 0) {
            return "";
        }
        if (n < 0) {
            throw new IllegalArgumentException("Requested random string length " + n + " is less than 0.");
        }
        if (array != null && array.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        }
        if (32 == 0 && 123 == 0) {
            if (array != null) {
                length = array.length;
            }
            else if (!b && !b2) {}
        }
        else if (123 <= 32) {
            throw new IllegalArgumentException("Parameter end (" + 123 + ") must be greater than start (" + 32 + ")");
        }
        final char[] array2 = new char[n];
        while (n-- != 0) {
            char c;
            if (array == null) {
                c = (char)(random.nextInt(91) + 32);
            }
            else {
                c = array[random.nextInt(91) + 32];
            }
            if ((b && Character.isLetter(c)) || (b2 && Character.isDigit(c)) || (!b && !b2)) {
                if (c >= '\udc00' && c <= '\udfff') {
                    if (n == 0) {
                        ++n;
                    }
                    else {
                        array2[n] = c;
                        --n;
                        array2[n] = (char)(55296 + random.nextInt(128));
                    }
                }
                else if (c >= '\ud800' && c <= '\udb7f') {
                    if (n == 0) {
                        ++n;
                    }
                    else {
                        array2[n] = (char)(56320 + random.nextInt(128));
                        --n;
                        array2[n] = c;
                    }
                }
                else if (c >= '\udb80' && c <= '\udbff') {
                    ++n;
                }
                else {
                    array2[n] = c;
                }
            }
            else {
                ++n;
            }
        }
        return new String(array2);
    }
    
    public static String random(final int n, final String s) {
        if (s == null) {
            return random(n, 0, 0, false, false, null, RandomStringUtils.RANDOM);
        }
        return random(n, s.toCharArray());
    }
    
    public static String random(final int n, final char... array) {
        if (array == null) {
            return random(n, 0, 0, false, false, null, RandomStringUtils.RANDOM);
        }
        return random(n, 0, array.length, false, false, array, RandomStringUtils.RANDOM);
    }
    
    static {
        RANDOM = new Random();
    }
}
