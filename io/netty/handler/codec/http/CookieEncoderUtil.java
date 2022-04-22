package io.netty.handler.codec.http;

import io.netty.util.internal.*;

final class CookieEncoderUtil
{
    static StringBuilder stringBuilder() {
        return InternalThreadLocalMap.get().stringBuilder();
    }
    
    static String stripTrailingSeparator(final StringBuilder sb) {
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
    
    static void add(final StringBuilder sb, final String s, final String s2) {
        if (s2 == null) {
            addQuoted(sb, s, "");
            return;
        }
        while (0 < s2.length()) {
            switch (s2.charAt(0)) {
                case '\t':
                case ' ':
                case '\"':
                case '(':
                case ')':
                case ',':
                case '/':
                case ':':
                case ';':
                case '<':
                case '=':
                case '>':
                case '?':
                case '@':
                case '[':
                case '\\':
                case ']':
                case '{':
                case '}': {
                    addQuoted(sb, s, s2);
                    return;
                }
                default: {
                    int n = 0;
                    ++n;
                    continue;
                }
            }
        }
        addUnquoted(sb, s, s2);
    }
    
    static void addUnquoted(final StringBuilder sb, final String s, final String s2) {
        sb.append(s);
        sb.append('=');
        sb.append(s2);
        sb.append(';');
        sb.append(' ');
    }
    
    static void addQuoted(final StringBuilder sb, final String s, String s2) {
        if (s2 == null) {
            s2 = "";
        }
        sb.append(s);
        sb.append('=');
        sb.append('\"');
        sb.append(s2.replace("\\", "\\\\").replace("\"", "\\\""));
        sb.append('\"');
        sb.append(';');
        sb.append(' ');
    }
    
    static void add(final StringBuilder sb, final String s, final long n) {
        sb.append(s);
        sb.append('=');
        sb.append(n);
        sb.append(';');
        sb.append(' ');
    }
    
    private CookieEncoderUtil() {
    }
}
