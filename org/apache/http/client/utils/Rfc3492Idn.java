package org.apache.http.client.utils;

import org.apache.http.annotation.*;
import java.util.*;

@Immutable
public class Rfc3492Idn implements Idn
{
    private static final int base = 36;
    private static final int tmin = 1;
    private static final int tmax = 26;
    private static final int skew = 38;
    private static final int damp = 700;
    private static final int initial_bias = 72;
    private static final int initial_n = 128;
    private static final char delimiter = '-';
    private static final String ACE_PREFIX = "xn--";
    
    private int adapt(final int n, final int n2, final boolean b) {
        int n3;
        if (b) {
            n3 = n / 700;
        }
        else {
            n3 = n / 2;
        }
        int i;
        for (i = n3 + n3 / n2; i > 455; i /= 35) {}
        return 0 + 36 * i / (i + 38);
    }
    
    private int digit(final char c) {
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        }
        if (c >= '0' && c <= '9') {
            return c - '0' + 26;
        }
        throw new IllegalArgumentException("illegal digit: " + c);
    }
    
    public String toUnicode(final String s) {
        final StringBuilder sb = new StringBuilder(s.length());
        final StringTokenizer stringTokenizer = new StringTokenizer(s, ".");
        while (stringTokenizer.hasMoreTokens()) {
            String s2 = stringTokenizer.nextToken();
            if (sb.length() > 0) {
                sb.append('.');
            }
            if (s2.startsWith("xn--")) {
                s2 = this.decode(s2.substring(4));
            }
            sb.append(s2);
        }
        return sb.toString();
    }
    
    protected String decode(final String s) {
        String s2 = s;
        final StringBuilder sb = new StringBuilder(s2.length());
        final int lastIndex = s2.lastIndexOf(45);
        if (lastIndex != -1) {
            sb.append(s2.subSequence(0, lastIndex));
            s2 = s2.substring(lastIndex + 1);
        }
    Label_0051:
        while (s2.length() > 0) {
            while (true) {
                while (s2.length() != 0) {
                    final char char1 = s2.charAt(0);
                    s2 = s2.substring(1);
                    if (this.digit(char1) < 26) {
                        this.adapt(0, sb.length() + 1, true);
                        final int n = 128 + 0 / (sb.length() + 1);
                        int n2 = 0 % (sb.length() + 1);
                        sb.insert(0, (char)128);
                        ++n2;
                        continue Label_0051;
                    }
                    final int n3;
                    n3 += 36;
                }
                continue;
            }
        }
        return sb.toString();
    }
}
