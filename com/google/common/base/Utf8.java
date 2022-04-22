package com.google.common.base;

import com.google.common.annotations.*;

@Beta
@GwtCompatible
public final class Utf8
{
    public static int encodedLength(final CharSequence charSequence) {
        int length;
        final int n = length = charSequence.length();
        int n2 = 0;
        while (0 < n && charSequence.charAt(0) < '\u0080') {
            ++n2;
        }
        while (0 < n) {
            final char char1 = charSequence.charAt(0);
            if (char1 >= '\u0800') {
                length += encodedLengthGeneral(charSequence, 0);
                break;
            }
            length += '\u007f' - char1 >>> 31;
            ++n2;
        }
        if (length < n) {
            throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (length + 4294967296L));
        }
        return length;
    }
    
    private static int encodedLengthGeneral(final CharSequence charSequence, final int n) {
        for (int length = charSequence.length(), i = n; i < length; ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 < '\u0800') {
                final int n2 = 0 + ('\u007f' - char1 >>> 31);
            }
            else {
                final int n2;
                n2 += 2;
                if ('\ud800' <= char1 && char1 <= '\udfff') {
                    if (Character.codePointAt(charSequence, i) < 65536) {
                        throw new IllegalArgumentException("Unpaired surrogate at index " + i);
                    }
                    ++i;
                }
            }
        }
        return 0;
    }
    
    public static boolean isWellFormed(final byte[] array) {
        return isWellFormed(array, 0, array.length);
    }
    
    public static boolean isWellFormed(final byte[] array, final int n, final int n2) {
        final int n3 = n + n2;
        Preconditions.checkPositionIndexes(n, n3, array.length);
        for (int i = n; i < n3; ++i) {
            if (array[i] < 0) {
                return isWellFormedSlowPath(array, i, n3);
            }
        }
        return true;
    }
    
    private static boolean isWellFormedSlowPath(final byte[] array, final int n, final int n2) {
        int i = n;
        while (i < n2) {
            final byte b;
            if ((b = array[i++]) < 0) {
                if (b < -32) {
                    if (i == n2) {
                        return false;
                    }
                    if (b < -62 || array[i++] > -65) {
                        return false;
                    }
                    continue;
                }
                else if (b < -16) {
                    if (i + 1 >= n2) {
                        return false;
                    }
                    final byte b2 = array[i++];
                    if (b2 > -65 || (b == -32 && b2 < -96) || (b == -19 && -96 <= b2) || array[i++] > -65) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (i + 2 >= n2) {
                        return false;
                    }
                    final byte b3 = array[i++];
                    if (b3 > -65 || (b << 28) + (b3 + 112) >> 30 != 0 || array[i++] > -65 || array[i++] > -65) {
                        return false;
                    }
                    continue;
                }
            }
        }
        return true;
    }
    
    private Utf8() {
    }
}
