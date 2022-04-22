package org.apache.logging.log4j.core.helpers;

public class Strings
{
    public static boolean isEmpty(final CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }
    
    public static boolean isNotEmpty(final CharSequence charSequence) {
        return !isEmpty(charSequence);
    }
}
