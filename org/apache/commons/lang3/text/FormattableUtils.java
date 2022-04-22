package org.apache.commons.lang3.text;

import java.util.*;
import org.apache.commons.lang3.*;

public class FormattableUtils
{
    private static final String SIMPLEST_FORMAT = "%s";
    
    public static String toString(final Formattable formattable) {
        return String.format("%s", formattable);
    }
    
    public static Formatter append(final CharSequence charSequence, final Formatter formatter, final int n, final int n2, final int n3) {
        return append(charSequence, formatter, n, n2, n3, ' ', null);
    }
    
    public static Formatter append(final CharSequence charSequence, final Formatter formatter, final int n, final int n2, final int n3, final char c) {
        return append(charSequence, formatter, n, n2, n3, c, null);
    }
    
    public static Formatter append(final CharSequence charSequence, final Formatter formatter, final int n, final int n2, final int n3, final CharSequence charSequence2) {
        return append(charSequence, formatter, n, n2, n3, ' ', charSequence2);
    }
    
    public static Formatter append(final CharSequence charSequence, final Formatter formatter, final int n, final int n2, final int n3, final char c, final CharSequence charSequence2) {
        Validate.isTrue(charSequence2 == null || n3 < 0 || charSequence2.length() <= n3, "Specified ellipsis '%1$s' exceeds precision of %2$s", charSequence2, n3);
        final StringBuilder sb = new StringBuilder(charSequence);
        if (n3 >= 0 && n3 < charSequence.length()) {
            final CharSequence charSequence3 = (CharSequence)ObjectUtils.defaultIfNull(charSequence2, "");
            sb.replace(n3 - charSequence3.length(), charSequence.length(), charSequence3.toString());
        }
        final boolean b = (n & 0x1) == 0x1;
        for (int i = sb.length(); i < n2; ++i) {
            sb.insert(b ? i : false, c);
        }
        formatter.format(sb.toString(), new Object[0]);
        return formatter;
    }
}
