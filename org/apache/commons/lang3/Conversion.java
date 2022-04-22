package org.apache.commons.lang3;

import java.util.*;

public class Conversion
{
    static final boolean $assertionsDisabled;
    
    public static int hexDigitToInt(final char c) {
        final int digit = Character.digit(c, 16);
        if (digit < 0) {
            throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
        }
        return digit;
    }
    
    public static int hexDigitMsb0ToInt(final char c) {
        switch (c) {
            case '0': {
                return 0;
            }
            case '1': {
                return 8;
            }
            case '2': {
                return 4;
            }
            case '3': {
                return 12;
            }
            case '4': {
                return 2;
            }
            case '5': {
                return 10;
            }
            case '6': {
                return 6;
            }
            case '7': {
                return 14;
            }
            case '8': {
                return 1;
            }
            case '9': {
                return 9;
            }
            case 'A':
            case 'a': {
                return 5;
            }
            case 'B':
            case 'b': {
                return 13;
            }
            case 'C':
            case 'c': {
                return 3;
            }
            case 'D':
            case 'd': {
                return 11;
            }
            case 'E':
            case 'e': {
                return 7;
            }
            case 'F':
            case 'f': {
                return 15;
            }
            default: {
                throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
            }
        }
    }
    
    public static boolean[] hexDigitToBinary(final char c) {
        switch (c) {
            case '0': {
                return new boolean[] { false, false, false, false };
            }
            case '1': {
                return new boolean[] { true, false, false, false };
            }
            case '2': {
                return new boolean[] { false, true, false, false };
            }
            case '3': {
                return new boolean[] { true, true, false, false };
            }
            case '4': {
                return new boolean[] { false, false, true, false };
            }
            case '5': {
                return new boolean[] { true, false, true, false };
            }
            case '6': {
                return new boolean[] { false, true, true, false };
            }
            case '7': {
                return new boolean[] { true, true, true, false };
            }
            case '8': {
                return new boolean[] { false, false, false, true };
            }
            case '9': {
                return new boolean[] { true, false, false, true };
            }
            case 'A':
            case 'a': {
                return new boolean[] { false, true, false, true };
            }
            case 'B':
            case 'b': {
                return new boolean[] { true, true, false, true };
            }
            case 'C':
            case 'c': {
                return new boolean[] { false, false, true, true };
            }
            case 'D':
            case 'd': {
                return new boolean[] { true, false, true, true };
            }
            case 'E':
            case 'e': {
                return new boolean[] { false, true, true, true };
            }
            case 'F':
            case 'f': {
                return new boolean[] { true, true, true, true };
            }
            default: {
                throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
            }
        }
    }
    
    public static boolean[] hexDigitMsb0ToBinary(final char c) {
        switch (c) {
            case '0': {
                return new boolean[] { false, false, false, false };
            }
            case '1': {
                return new boolean[] { false, false, false, true };
            }
            case '2': {
                return new boolean[] { false, false, true, false };
            }
            case '3': {
                return new boolean[] { false, false, true, true };
            }
            case '4': {
                return new boolean[] { false, true, false, false };
            }
            case '5': {
                return new boolean[] { false, true, false, true };
            }
            case '6': {
                return new boolean[] { false, true, true, false };
            }
            case '7': {
                return new boolean[] { false, true, true, true };
            }
            case '8': {
                return new boolean[] { true, false, false, false };
            }
            case '9': {
                return new boolean[] { true, false, false, true };
            }
            case 'A':
            case 'a': {
                return new boolean[] { true, false, true, false };
            }
            case 'B':
            case 'b': {
                return new boolean[] { true, false, true, true };
            }
            case 'C':
            case 'c': {
                return new boolean[] { true, true, false, false };
            }
            case 'D':
            case 'd': {
                return new boolean[] { true, true, false, true };
            }
            case 'E':
            case 'e': {
                return new boolean[] { true, true, true, false };
            }
            case 'F':
            case 'f': {
                return new boolean[] { true, true, true, true };
            }
            default: {
                throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
            }
        }
    }
    
    public static char binaryToHexDigit(final boolean[] array) {
        return binaryToHexDigit(array, 0);
    }
    
    public static char binaryToHexDigit(final boolean[] array, final int n) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Cannot convert an empty array.");
        }
        if (array.length > n + 3 && array[n + 3]) {
            if (array.length > n + 2 && array[n + 2]) {
                if (array.length > n + 1 && array[n + 1]) {
                    if (array[n]) {
                        return 'f';
                    }
                    return 'e';
                }
                else {
                    if (array[n]) {
                        return 'd';
                    }
                    return 'c';
                }
            }
            else if (array.length > n + 1 && array[n + 1]) {
                if (array[n]) {
                    return 'b';
                }
                return 'a';
            }
            else {
                if (array[n]) {
                    return '9';
                }
                return '8';
            }
        }
        else if (array.length > n + 2 && array[n + 2]) {
            if (array.length > n + 1 && array[n + 1]) {
                if (array[n]) {
                    return '7';
                }
                return '6';
            }
            else {
                if (array[n]) {
                    return '5';
                }
                return '4';
            }
        }
        else if (array.length > n + 1 && array[n + 1]) {
            if (array[n]) {
                return '3';
            }
            return '2';
        }
        else {
            if (array[n]) {
                return '1';
            }
            return '0';
        }
    }
    
    public static char binaryToHexDigitMsb0_4bits(final boolean[] array) {
        return binaryToHexDigitMsb0_4bits(array, 0);
    }
    
    public static char binaryToHexDigitMsb0_4bits(final boolean[] array, final int n) {
        if (array.length > 8) {
            throw new IllegalArgumentException("src.length>8: src.length=" + array.length);
        }
        if (array.length - n < 4) {
            throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + array.length + ", srcPos=" + n);
        }
        if (array[n + 3]) {
            if (array[n + 2]) {
                if (array[n + 1]) {
                    if (array[n]) {
                        return 'f';
                    }
                    return '7';
                }
                else {
                    if (array[n]) {
                        return 'b';
                    }
                    return '3';
                }
            }
            else if (array[n + 1]) {
                if (array[n]) {
                    return 'd';
                }
                return '5';
            }
            else {
                if (array[n]) {
                    return '9';
                }
                return '1';
            }
        }
        else if (array[n + 2]) {
            if (array[n + 1]) {
                if (array[n]) {
                    return 'e';
                }
                return '6';
            }
            else {
                if (array[n]) {
                    return 'a';
                }
                return '2';
            }
        }
        else if (array[n + 1]) {
            if (array[n]) {
                return 'c';
            }
            return '4';
        }
        else {
            if (array[n]) {
                return '8';
            }
            return '0';
        }
    }
    
    public static char binaryBeMsb0ToHexDigit(final boolean[] array) {
        return binaryBeMsb0ToHexDigit(array, 0);
    }
    
    public static char binaryBeMsb0ToHexDigit(boolean[] array, final int n) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Cannot convert an empty array.");
        }
        final int n2 = array.length - 1 - 0;
        final int min = Math.min(4, n2 + 1);
        final boolean[] array2 = new boolean[4];
        System.arraycopy(array, n2 + 1 - min, array2, 4 - min, min);
        array = array2;
        if (array[0]) {
            if (array.length > 1 && array[1]) {
                if (array.length > 2 && array[2]) {
                    if (array.length > 3 && array[3]) {
                        return 'f';
                    }
                    return 'e';
                }
                else {
                    if (array.length > 3 && array[3]) {
                        return 'd';
                    }
                    return 'c';
                }
            }
            else if (array.length > 2 && array[2]) {
                if (array.length > 3 && array[3]) {
                    return 'b';
                }
                return 'a';
            }
            else {
                if (array.length > 3 && array[3]) {
                    return '9';
                }
                return '8';
            }
        }
        else if (array.length > 1 && array[1]) {
            if (array.length > 2 && array[2]) {
                if (array.length > 3 && array[3]) {
                    return '7';
                }
                return '6';
            }
            else {
                if (array.length > 3 && array[3]) {
                    return '5';
                }
                return '4';
            }
        }
        else if (array.length > 2 && array[2]) {
            if (array.length > 3 && array[3]) {
                return '3';
            }
            return '2';
        }
        else {
            if (array.length > 3 && array[3]) {
                return '1';
            }
            return '0';
        }
    }
    
    public static char intToHexDigit(final int n) {
        final char forDigit = Character.forDigit(n, 16);
        if (forDigit == '\0') {
            throw new IllegalArgumentException("nibble value not between 0 and 15: " + n);
        }
        return forDigit;
    }
    
    public static char intToHexDigitMsb0(final int n) {
        switch (n) {
            case 0: {
                return '0';
            }
            case 1: {
                return '8';
            }
            case 2: {
                return '4';
            }
            case 3: {
                return 'c';
            }
            case 4: {
                return '2';
            }
            case 5: {
                return 'a';
            }
            case 6: {
                return '6';
            }
            case 7: {
                return 'e';
            }
            case 8: {
                return '1';
            }
            case 9: {
                return '9';
            }
            case 10: {
                return '5';
            }
            case 11: {
                return 'd';
            }
            case 12: {
                return '3';
            }
            case 13: {
                return 'b';
            }
            case 14: {
                return '7';
            }
            case 15: {
                return 'f';
            }
            default: {
                throw new IllegalArgumentException("nibble value not between 0 and 15: " + n);
            }
        }
    }
    
    public static long intArrayToLong(final int[] array, final int n, final long n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 32 + n3 >= 64) {
            throw new IllegalArgumentException("(nInts-1)*32+dstPos is greather or equal to than 64");
        }
        long n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & ~0xFFFFFFFFL) | (0xFFFFFFFFL & (long)array[0 + n]) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static long shortArrayToLong(final short[] array, final int n, final long n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 16 + n3 >= 64) {
            throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 64");
        }
        long n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & ~0xFFFFL) | (0xFFFFL & (long)array[0 + n]) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static int shortArrayToInt(final short[] array, final int n, final int n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 16 + n3 >= 32) {
            throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & 0xFFFF0000) | (0xFFFF & array[0 + n]) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static long byteArrayToLong(final byte[] array, final int n, final long n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 8 + n3 >= 64) {
            throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 64");
        }
        long n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & ~0xFFL) | (0xFFL & (long)array[0 + n]) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static int byteArrayToInt(final byte[] array, final int n, final int n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 8 + n3 >= 32) {
            throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & 0xFFFFFF00) | (0xFF & array[0 + n]) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static short byteArrayToShort(final byte[] array, final int n, final short n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 8 + n3 >= 16) {
            throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 16");
        }
        short n5 = n2;
        while (0 < n4) {
            n5 = (short)((n5 & 0xFFFFFF00) | (0xFF & array[0 + n]) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static long hexToLong(final String s, final int n, final long n2, final int n3, final int n4) {
        if (0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 4 + n3 >= 64) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 64");
        }
        long n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & ~0xFL) | (0xFL & (long)hexDigitToInt(s.charAt(0 + n))) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static int hexToInt(final String s, final int n, final int n2, final int n3, final int n4) {
        if (0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 4 + n3 >= 32) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & 0xFFFFFFF0) | (0xF & hexDigitToInt(s.charAt(0 + n))) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static short hexToShort(final String s, final int n, final short n2, final int n3, final int n4) {
        if (0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 4 + n3 >= 16) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 16");
        }
        short n5 = n2;
        while (0 < n4) {
            n5 = (short)((n5 & 0xFFFFFFF0) | (0xF & hexDigitToInt(s.charAt(0 + n))) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static byte hexToByte(final String s, final int n, final byte b, final int n2, final int n3) {
        if (0 == n3) {
            return b;
        }
        if ((n3 - 1) * 4 + n2 >= 8) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 8");
        }
        byte b2 = b;
        while (0 < n3) {
            b2 = (byte)((b2 & 0xFFFFFFF0) | (0xF & hexDigitToInt(s.charAt(0 + n))) << 0);
            int n4 = 0;
            ++n4;
        }
        return b2;
    }
    
    public static long binaryToLong(final boolean[] array, final int n, final long n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if (n4 - 1 + n3 >= 64) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 64");
        }
        long n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & ~0x1L) | (array[0 + n] ? 1 : 0) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static int binaryToInt(final boolean[] array, final int n, final int n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if (n4 - 1 + n3 >= 32) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        while (0 < n4) {
            n5 = ((n5 & 0xFFFFFFFE) | (array[0 + n] ? 1 : 0) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static short binaryToShort(final boolean[] array, final int n, final short n2, final int n3, final int n4) {
        if ((array.length == 0 && n == 0) || 0 == n4) {
            return n2;
        }
        if (n4 - 1 + n3 >= 16) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 16");
        }
        short n5 = n2;
        while (0 < n4) {
            n5 = (short)((n5 & 0xFFFFFFFE) | (array[0 + n] ? 1 : 0) << 0);
            int n6 = 0;
            ++n6;
        }
        return n5;
    }
    
    public static byte binaryToByte(final boolean[] array, final int n, final byte b, final int n2, final int n3) {
        if ((array.length == 0 && n == 0) || 0 == n3) {
            return b;
        }
        if (n3 - 1 + n2 >= 8) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 8");
        }
        byte b2 = b;
        while (0 < n3) {
            b2 = (byte)((b2 & 0xFFFFFFFE) | (array[0 + n] ? 1 : 0) << 0);
            int n4 = 0;
            ++n4;
        }
        return b2;
    }
    
    public static int[] longToIntArray(final long n, final int n2, final int[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if ((n4 - 1) * 32 + n2 >= 64) {
            throw new IllegalArgumentException("(nInts-1)*32+srcPos is greather or equal to than 64");
        }
        while (0 < n4) {
            array[n3 + 0] = (int)(-1L & n >> 0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static short[] longToShortArray(final long n, final int n2, final short[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if ((n4 - 1) * 16 + n2 >= 64) {
            throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 64");
        }
        while (0 < n4) {
            array[n3 + 0] = (short)(0xFFFFL & n >> 0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static short[] intToShortArray(final int n, final int n2, final short[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if ((n4 - 1) * 16 + n2 >= 32) {
            throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 32");
        }
        while (0 < n4) {
            array[n3 + 0] = (short)(0xFFFF & n >> 0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static byte[] longToByteArray(final long n, final int n2, final byte[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if ((n4 - 1) * 8 + n2 >= 64) {
            throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 64");
        }
        while (0 < n4) {
            array[n3 + 0] = (byte)(0xFFL & n >> 0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static byte[] intToByteArray(final int n, final int n2, final byte[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if ((n4 - 1) * 8 + n2 >= 32) {
            throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 32");
        }
        while (0 < n4) {
            array[n3 + 0] = (byte)(0xFF & n >> 0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static byte[] shortToByteArray(final short n, final int n2, final byte[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if ((n4 - 1) * 8 + n2 >= 16) {
            throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 16");
        }
        while (0 < n4) {
            array[n3 + 0] = (byte)(0xFF & n >> 0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static String longToHex(final long n, final int n2, final String s, final int n3, final int n4) {
        if (0 == n4) {
            return s;
        }
        if ((n4 - 1) * 4 + n2 >= 64) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 64");
        }
        final StringBuilder sb = new StringBuilder(s);
        int length = sb.length();
        while (0 < n4) {
            final int n5 = (int)(0xFL & n >> 0);
            if (n3 + 0 == length) {
                ++length;
                sb.append(intToHexDigit(n5));
            }
            else {
                sb.setCharAt(n3 + 0, intToHexDigit(n5));
            }
            int n6 = 0;
            ++n6;
        }
        return sb.toString();
    }
    
    public static String intToHex(final int n, final int n2, final String s, final int n3, final int n4) {
        if (0 == n4) {
            return s;
        }
        if ((n4 - 1) * 4 + n2 >= 32) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 32");
        }
        final StringBuilder sb = new StringBuilder(s);
        int length = sb.length();
        while (0 < n4) {
            final int n5 = 0xF & n >> 0;
            if (n3 + 0 == length) {
                ++length;
                sb.append(intToHexDigit(n5));
            }
            else {
                sb.setCharAt(n3 + 0, intToHexDigit(n5));
            }
            int n6 = 0;
            ++n6;
        }
        return sb.toString();
    }
    
    public static String shortToHex(final short n, final int n2, final String s, final int n3, final int n4) {
        if (0 == n4) {
            return s;
        }
        if ((n4 - 1) * 4 + n2 >= 16) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 16");
        }
        final StringBuilder sb = new StringBuilder(s);
        int length = sb.length();
        while (0 < n4) {
            final int n5 = 0xF & n >> 0;
            if (n3 + 0 == length) {
                ++length;
                sb.append(intToHexDigit(n5));
            }
            else {
                sb.setCharAt(n3 + 0, intToHexDigit(n5));
            }
            int n6 = 0;
            ++n6;
        }
        return sb.toString();
    }
    
    public static String byteToHex(final byte b, final int n, final String s, final int n2, final int n3) {
        if (0 == n3) {
            return s;
        }
        if ((n3 - 1) * 4 + n >= 8) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 8");
        }
        final StringBuilder sb = new StringBuilder(s);
        int length = sb.length();
        while (0 < n3) {
            final int n4 = 0xF & b >> 0;
            if (n2 + 0 == length) {
                ++length;
                sb.append(intToHexDigit(n4));
            }
            else {
                sb.setCharAt(n2 + 0, intToHexDigit(n4));
            }
            int n5 = 0;
            ++n5;
        }
        return sb.toString();
    }
    
    public static boolean[] longToBinary(final long n, final int n2, final boolean[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if (n4 - 1 + n2 >= 64) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 64");
        }
        while (0 < n4) {
            array[n3 + 0] = ((0x1L & n >> 0) != 0x0L);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static boolean[] intToBinary(final int n, final int n2, final boolean[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if (n4 - 1 + n2 >= 32) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 32");
        }
        while (0 < n4) {
            array[n3 + 0] = ((0x1 & n >> 0) != 0x0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static boolean[] shortToBinary(final short n, final int n2, final boolean[] array, final int n3, final int n4) {
        if (0 == n4) {
            return array;
        }
        if (n4 - 1 + n2 >= 16) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 16");
        }
        assert (n4 - 1) * 1 < 16 - n2;
        while (0 < n4) {
            array[n3 + 0] = ((0x1 & n >> 0) != 0x0);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static boolean[] byteToBinary(final byte b, final int n, final boolean[] array, final int n2, final int n3) {
        if (0 == n3) {
            return array;
        }
        if (n3 - 1 + n >= 8) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 8");
        }
        while (0 < n3) {
            array[n2 + 0] = ((0x1 & b >> 0) != 0x0);
            int n4 = 0;
            ++n4;
        }
        return array;
    }
    
    public static byte[] uuidToByteArray(final UUID uuid, final byte[] array, final int n, final int n2) {
        if (0 == n2) {
            return array;
        }
        if (n2 > 16) {
            throw new IllegalArgumentException("nBytes is greather than 16");
        }
        longToByteArray(uuid.getMostSignificantBits(), 0, array, n, (n2 > 8) ? 8 : n2);
        if (n2 >= 8) {
            longToByteArray(uuid.getLeastSignificantBits(), 0, array, n + 8, n2 - 8);
        }
        return array;
    }
    
    public static UUID byteArrayToUuid(final byte[] array, final int n) {
        if (array.length - n < 16) {
            throw new IllegalArgumentException("Need at least 16 bytes for UUID");
        }
        return new UUID(byteArrayToLong(array, n, 0L, 0, 8), byteArrayToLong(array, n + 8, 0L, 0, 8));
    }
    
    static {
        $assertionsDisabled = !Conversion.class.desiredAssertionStatus();
    }
}
