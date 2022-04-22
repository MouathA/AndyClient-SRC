package io.netty.handler.codec.http.multipart;

import java.util.*;
import java.io.*;

final class CaseIgnoringComparator implements Comparator, Serializable
{
    private static final long serialVersionUID = 4582133183775373862L;
    static final CaseIgnoringComparator INSTANCE;
    
    private CaseIgnoringComparator() {
    }
    
    public int compare(final CharSequence charSequence, final CharSequence charSequence2) {
        final int length = charSequence.length();
        final int length2 = charSequence2.length();
        while (0 < Math.min(length, length2)) {
            final char char1 = charSequence.charAt(0);
            final char char2 = charSequence2.charAt(0);
            if (char1 != char2) {
                final char upperCase = Character.toUpperCase(char1);
                final char upperCase2 = Character.toUpperCase(char2);
                if (upperCase != upperCase2) {
                    final char lowerCase = Character.toLowerCase(upperCase);
                    final char lowerCase2 = Character.toLowerCase(upperCase2);
                    if (lowerCase != lowerCase2) {
                        return lowerCase - lowerCase2;
                    }
                }
            }
            int n = 0;
            ++n;
        }
        return length - length2;
    }
    
    private Object readResolve() {
        return CaseIgnoringComparator.INSTANCE;
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.compare((CharSequence)o, (CharSequence)o2);
    }
    
    static {
        INSTANCE = new CaseIgnoringComparator();
    }
}
