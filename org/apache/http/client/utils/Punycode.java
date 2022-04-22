package org.apache.http.client.utils;

import org.apache.http.annotation.*;

@Immutable
public class Punycode
{
    private static final Idn impl;
    
    public static String toUnicode(final String s) {
        return Punycode.impl.toUnicode(s);
    }
    
    static {
        impl = new JdkIdn();
    }
}
