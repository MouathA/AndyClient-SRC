package org.apache.commons.lang3.math;

import org.apache.commons.lang3.*;
import java.math.*;
import java.lang.reflect.*;

public class NumberUtils
{
    public static final Long LONG_ZERO;
    public static final Long LONG_ONE;
    public static final Long LONG_MINUS_ONE;
    public static final Integer INTEGER_ZERO;
    public static final Integer INTEGER_ONE;
    public static final Integer INTEGER_MINUS_ONE;
    public static final Short SHORT_ZERO;
    public static final Short SHORT_ONE;
    public static final Short SHORT_MINUS_ONE;
    public static final Byte BYTE_ZERO;
    public static final Byte BYTE_ONE;
    public static final Byte BYTE_MINUS_ONE;
    public static final Double DOUBLE_ZERO;
    public static final Double DOUBLE_ONE;
    public static final Double DOUBLE_MINUS_ONE;
    public static final Float FLOAT_ZERO;
    public static final Float FLOAT_ONE;
    public static final Float FLOAT_MINUS_ONE;
    
    public static int toInt(final String s) {
        return toInt(s, 0);
    }
    
    public static int toInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        return Integer.parseInt(s);
    }
    
    public static long toLong(final String s) {
        return toLong(s, 0L);
    }
    
    public static long toLong(final String s, final long n) {
        if (s == null) {
            return n;
        }
        return Long.parseLong(s);
    }
    
    public static float toFloat(final String s) {
        return toFloat(s, 0.0f);
    }
    
    public static float toFloat(final String s, final float n) {
        if (s == null) {
            return n;
        }
        return Float.parseFloat(s);
    }
    
    public static double toDouble(final String s) {
        return toDouble(s, 0.0);
    }
    
    public static double toDouble(final String s, final double n) {
        if (s == null) {
            return n;
        }
        return Double.parseDouble(s);
    }
    
    public static byte toByte(final String s) {
        return toByte(s, (byte)0);
    }
    
    public static byte toByte(final String s, final byte b) {
        if (s == null) {
            return b;
        }
        return Byte.parseByte(s);
    }
    
    public static short toShort(final String s) {
        return toShort(s, (short)0);
    }
    
    public static short toShort(final String s, final short n) {
        if (s == null) {
            return n;
        }
        return Short.parseShort(s);
    }
    
    public static Number createNumber(final String s) throws NumberFormatException {
        if (s == null) {
            return null;
        }
        if (StringUtils.isBlank(s)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        final String[] array = { "0x", "0X", "-0x", "-0X", "#", "-#" };
        int length = array.length;
        int n = 0;
        while (0 < 0) {
            final String s2 = array[0];
            if (s.startsWith(s2)) {
                n = 0 + s2.length();
                break;
            }
            int n2 = 0;
            ++n2;
        }
        if (0 > 0) {
            while (0 < s.length()) {
                s.charAt(0);
                if (0 != 48) {
                    break;
                }
                ++n;
                ++length;
            }
            final int n3 = s.length() - 0;
            if (0 > 16 || (0 == 16 && 0 > 55)) {
                return createBigInteger(s);
            }
            if (0 > 8 || (0 == 8 && 0 > 55)) {
                return createLong(s);
            }
            return createInteger(s);
        }
        else {
            s.charAt(s.length() - 1);
            final int index = s.indexOf(46);
            final int n4 = s.indexOf(101) + s.indexOf(69) + 1;
            String s3;
            String s4;
            if (index > -1) {
                if (n4 > -1) {
                    if (n4 < index || n4 > s.length()) {
                        throw new NumberFormatException(s + " is not a valid number.");
                    }
                    s3 = s.substring(index + 1, n4);
                }
                else {
                    s3 = s.substring(index + 1);
                }
                s4 = s.substring(0, index);
                s3.length();
            }
            else {
                if (n4 > -1) {
                    if (n4 > s.length()) {
                        throw new NumberFormatException(s + " is not a valid number.");
                    }
                    s4 = s.substring(0, n4);
                }
                else {
                    s4 = s;
                }
                s3 = null;
            }
            if (!Character.isDigit('\0') && 0 != 46) {
                String substring;
                if (n4 > -1 && n4 < s.length() - 1) {
                    substring = s.substring(n4 + 1, s.length() - 1);
                }
                else {
                    substring = null;
                }
                final String substring2 = s.substring(0, s.length() - 1);
                final boolean b = isAllZeros(s4) && isAllZeros(substring);
                switch (false) {
                    case 76:
                    case 108: {
                        if (s3 == null && substring == null && ((substring2.charAt(0) == '-' && isDigits(substring2.substring(1))) || isDigits(substring2))) {
                            return createLong(substring2);
                        }
                        throw new NumberFormatException(s + " is not a valid number.");
                    }
                    case 70:
                    case 102: {
                        final Float float1 = createFloat(substring2);
                        if (!float1.isInfinite() && (float1 != 0.0f || b)) {
                            return float1;
                        }
                    }
                    case 68:
                    case 100: {
                        final Double double1 = createDouble(substring2);
                        if (!double1.isInfinite() && (double1.floatValue() != 0.0 || b)) {
                            return double1;
                        }
                        return createBigDecimal(substring2);
                    }
                    default: {
                        throw new NumberFormatException(s + " is not a valid number.");
                    }
                }
            }
            else {
                String substring3;
                if (n4 > -1 && n4 < s.length() - 1) {
                    substring3 = s.substring(n4 + 1, s.length());
                }
                else {
                    substring3 = null;
                }
                if (s3 == null && substring3 == null) {
                    return createInteger(s);
                }
                final boolean b2 = isAllZeros(s4) && isAllZeros(substring3);
                if (0 <= 7) {
                    final Float float2 = createFloat(s);
                    if (!float2.isInfinite() && (float2 != 0.0f || b2)) {
                        return float2;
                    }
                }
                if (0 <= 16) {
                    final Double double2 = createDouble(s);
                    if (!double2.isInfinite() && (double2 != 0.0 || b2)) {
                        return double2;
                    }
                }
                return createBigDecimal(s);
            }
        }
    }
    
    private static boolean isAllZeros(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = s.length() - 1; i >= 0; --i) {
            if (s.charAt(i) != '0') {
                return false;
            }
        }
        return s.length() > 0;
    }
    
    public static Float createFloat(final String s) {
        if (s == null) {
            return null;
        }
        return Float.valueOf(s);
    }
    
    public static Double createDouble(final String s) {
        if (s == null) {
            return null;
        }
        return Double.valueOf(s);
    }
    
    public static Integer createInteger(final String s) {
        if (s == null) {
            return null;
        }
        return Integer.decode(s);
    }
    
    public static Long createLong(final String s) {
        if (s == null) {
            return null;
        }
        return Long.decode(s);
    }
    
    public static BigInteger createBigInteger(final String s) {
        if (s == null) {
            return null;
        }
        if (s.startsWith("-")) {}
        if (s.startsWith("0x", 1) || s.startsWith("0x", 1)) {
            final int n;
            n += 2;
        }
        else if (s.startsWith("#", 1)) {
            int n = 0;
            ++n;
        }
        else if (s.startsWith("0", 1) && s.length() > 2) {
            int n = 0;
            ++n;
        }
        final BigInteger bigInteger = new BigInteger(s.substring(1), 8);
        return true ? bigInteger.negate() : bigInteger;
    }
    
    public static BigDecimal createBigDecimal(final String s) {
        if (s == null) {
            return null;
        }
        if (StringUtils.isBlank(s)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        if (s.trim().startsWith("--")) {
            throw new NumberFormatException(s + " is not a valid number.");
        }
        return new BigDecimal(s);
    }
    
    public static long min(final long[] array) {
        validateArray(array);
        long n = array[0];
        while (1 < array.length) {
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static int min(final int[] array) {
        validateArray(array);
        int n = array[0];
        while (1 < array.length) {
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static short min(final short[] array) {
        validateArray(array);
        short n = array[0];
        while (1 < array.length) {
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static byte min(final byte[] array) {
        validateArray(array);
        byte b = array[0];
        while (1 < array.length) {
            if (array[1] < b) {
                b = array[1];
            }
            int n = 0;
            ++n;
        }
        return b;
    }
    
    public static double min(final double[] array) {
        validateArray(array);
        double n = array[0];
        while (1 < array.length) {
            if (Double.isNaN(array[1])) {
                return Double.NaN;
            }
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static float min(final float[] array) {
        validateArray(array);
        float n = array[0];
        while (1 < array.length) {
            if (Float.isNaN(array[1])) {
                return Float.NaN;
            }
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static long max(final long[] array) {
        validateArray(array);
        long n = array[0];
        while (1 < array.length) {
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static int max(final int[] array) {
        validateArray(array);
        int n = array[0];
        while (1 < array.length) {
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static short max(final short[] array) {
        validateArray(array);
        short n = array[0];
        while (1 < array.length) {
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static byte max(final byte[] array) {
        validateArray(array);
        byte b = array[0];
        while (1 < array.length) {
            if (array[1] > b) {
                b = array[1];
            }
            int n = 0;
            ++n;
        }
        return b;
    }
    
    public static double max(final double[] array) {
        validateArray(array);
        double n = array[0];
        while (1 < array.length) {
            if (Double.isNaN(array[1])) {
                return Double.NaN;
            }
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static float max(final float[] array) {
        validateArray(array);
        float n = array[0];
        while (1 < array.length) {
            if (Float.isNaN(array[1])) {
                return Float.NaN;
            }
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    private static void validateArray(final Object o) {
        if (o == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (Array.getLength(o) == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }
    
    public static long min(long n, final long n2, final long n3) {
        if (n2 < n) {
            n = n2;
        }
        if (n3 < n) {
            n = n3;
        }
        return n;
    }
    
    public static int min(int n, final int n2, final int n3) {
        if (n2 < n) {
            n = n2;
        }
        if (n3 < n) {
            n = n3;
        }
        return n;
    }
    
    public static short min(short n, final short n2, final short n3) {
        if (n2 < n) {
            n = n2;
        }
        if (n3 < n) {
            n = n3;
        }
        return n;
    }
    
    public static byte min(byte b, final byte b2, final byte b3) {
        if (b2 < b) {
            b = b2;
        }
        if (b3 < b) {
            b = b3;
        }
        return b;
    }
    
    public static double min(final double n, final double n2, final double n3) {
        return Math.min(Math.min(n, n2), n3);
    }
    
    public static float min(final float n, final float n2, final float n3) {
        return Math.min(Math.min(n, n2), n3);
    }
    
    public static long max(long n, final long n2, final long n3) {
        if (n2 > n) {
            n = n2;
        }
        if (n3 > n) {
            n = n3;
        }
        return n;
    }
    
    public static int max(int n, final int n2, final int n3) {
        if (n2 > n) {
            n = n2;
        }
        if (n3 > n) {
            n = n3;
        }
        return n;
    }
    
    public static short max(short n, final short n2, final short n3) {
        if (n2 > n) {
            n = n2;
        }
        if (n3 > n) {
            n = n3;
        }
        return n;
    }
    
    public static byte max(byte b, final byte b2, final byte b3) {
        if (b2 > b) {
            b = b2;
        }
        if (b3 > b) {
            b = b3;
        }
        return b;
    }
    
    public static double max(final double n, final double n2, final double n3) {
        return Math.max(Math.max(n, n2), n3);
    }
    
    public static float max(final float n, final float n2, final float n3) {
        return Math.max(Math.max(n, n2), n3);
    }
    
    public static boolean isDigits(final String s) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        while (0 < s.length()) {
            if (!Character.isDigit(s.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isNumber(final String s) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        final char[] charArray = s.toCharArray();
        int length = charArray.length;
        final int n = (charArray[0] == '-') ? 1 : 0;
        if (length > n + 1 && charArray[n] == '0') {
            if (charArray[n + 1] == 'x' || charArray[n + 1] == 'X') {
                int i = n + 2;
                if (i == length) {
                    return false;
                }
                while (i < charArray.length) {
                    if ((charArray[i] < '0' || charArray[i] > '9') && (charArray[i] < 'a' || charArray[i] > 'f') && (charArray[i] < 'A' || charArray[i] > 'F')) {
                        return false;
                    }
                    ++i;
                }
                return true;
            }
            else if (Character.isDigit(charArray[n + 1])) {
                for (int j = n + 1; j < charArray.length; ++j) {
                    if (charArray[j] < '0' || charArray[j] > '7') {
                        return false;
                    }
                }
                return true;
            }
        }
        --length;
        int n2;
        for (n2 = n; n2 < length || (n2 < length + 1 && false && !false); ++n2) {
            if (charArray[n2] < '0' || charArray[n2] > '9') {
                if (charArray[n2] == '.') {
                    if (true || true) {
                        return false;
                    }
                }
                else if (charArray[n2] == 'e' || charArray[n2] == 'E') {
                    if (true) {
                        return false;
                    }
                    if (!false) {
                        return false;
                    }
                }
                else {
                    if (charArray[n2] != '+' && charArray[n2] != '-') {
                        return false;
                    }
                    if (!false) {
                        return false;
                    }
                }
            }
        }
        if (n2 >= charArray.length) {
            return !false && false;
        }
        if (charArray[n2] >= '0' && charArray[n2] <= '9') {
            return true;
        }
        if (charArray[n2] == 'e' || charArray[n2] == 'E') {
            return false;
        }
        if (charArray[n2] == '.') {
            return (true || true) && false;
        }
        return (false || (charArray[n2] != 'd' && charArray[n2] != 'D' && charArray[n2] != 'f' && charArray[n2] != 'F')) && (charArray[n2] == 'l' || charArray[n2] == 'L') && false && !true && !true;
    }
    
    static {
        LONG_ZERO = 0L;
        LONG_ONE = 1L;
        LONG_MINUS_ONE = -1L;
        INTEGER_ZERO = 0;
        INTEGER_ONE = 1;
        INTEGER_MINUS_ONE = -1;
        SHORT_ZERO = 0;
        SHORT_ONE = 1;
        SHORT_MINUS_ONE = -1;
        BYTE_ZERO = 0;
        BYTE_ONE = 1;
        BYTE_MINUS_ONE = -1;
        DOUBLE_ZERO = 0.0;
        DOUBLE_ONE = 1.0;
        DOUBLE_MINUS_ONE = -1.0;
        FLOAT_ZERO = 0.0f;
        FLOAT_ONE = 1.0f;
        FLOAT_MINUS_ONE = -1.0f;
    }
}
