package com.google.common.primitives;

import com.google.common.annotations.*;

@GwtCompatible
final class ParseRequest
{
    final String rawValue;
    final int radix;
    
    private ParseRequest(final String rawValue, final int radix) {
        this.rawValue = rawValue;
        this.radix = radix;
    }
    
    static ParseRequest fromString(final String s) {
        if (s.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        final char char1 = s.charAt(0);
        String s2;
        if (s.startsWith("0x") || s.startsWith("0X")) {
            s2 = s.substring(2);
        }
        else if (char1 == '#') {
            s2 = s.substring(1);
        }
        else if (char1 == '0' && s.length() > 1) {
            s2 = s.substring(1);
        }
        else {
            s2 = s;
        }
        return new ParseRequest(s2, 10);
    }
}
