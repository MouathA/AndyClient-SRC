package org.apache.http.util;

public final class TextUtils
{
    public static boolean isEmpty(final CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }
    
    public static boolean isBlank(final CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        while (0 < charSequence.length()) {
            if (!Character.isWhitespace(charSequence.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
}
