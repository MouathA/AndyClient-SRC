package org.apache.commons.codec.language;

import java.util.*;
import org.apache.commons.codec.*;

final class SoundexUtils
{
    static String clean(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        final int length = s.length();
        final char[] array = new char[length];
        while (0 < length) {
            if (Character.isLetter(s.charAt(0))) {
                final char[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = s.charAt(0);
            }
            int n3 = 0;
            ++n3;
        }
        if (length == 0) {
            return s.toUpperCase(Locale.ENGLISH);
        }
        return new String(array, 0, 0).toUpperCase(Locale.ENGLISH);
    }
    
    static int difference(final StringEncoder stringEncoder, final String s, final String s2) throws EncoderException {
        return differenceEncoded(stringEncoder.encode(s), stringEncoder.encode(s2));
    }
    
    static int differenceEncoded(final String s, final String s2) {
        if (s == null || s2 == null) {
            return 0;
        }
        while (0 < Math.min(s.length(), s2.length())) {
            if (s.charAt(0) == s2.charAt(0)) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
}
