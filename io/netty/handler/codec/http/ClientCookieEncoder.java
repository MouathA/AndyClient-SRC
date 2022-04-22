package io.netty.handler.codec.http;

import java.util.*;

public final class ClientCookieEncoder
{
    public static String encode(final String s, final String s2) {
        return encode(new DefaultCookie(s, s2));
    }
    
    public static String encode(final Cookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie");
        }
        final StringBuilder stringBuilder = CookieEncoderUtil.stringBuilder();
        encode(stringBuilder, cookie);
        return CookieEncoderUtil.stripTrailingSeparator(stringBuilder);
    }
    
    public static String encode(final Cookie... array) {
        if (array == null) {
            throw new NullPointerException("cookies");
        }
        final StringBuilder stringBuilder = CookieEncoderUtil.stringBuilder();
        while (0 < array.length) {
            final Cookie cookie = array[0];
            if (cookie == null) {
                break;
            }
            encode(stringBuilder, cookie);
            int n = 0;
            ++n;
        }
        return CookieEncoderUtil.stripTrailingSeparator(stringBuilder);
    }
    
    public static String encode(final Iterable iterable) {
        if (iterable == null) {
            throw new NullPointerException("cookies");
        }
        final StringBuilder stringBuilder = CookieEncoderUtil.stringBuilder();
        for (final Cookie cookie : iterable) {
            if (cookie == null) {
                break;
            }
            encode(stringBuilder, cookie);
        }
        return CookieEncoderUtil.stripTrailingSeparator(stringBuilder);
    }
    
    private static void encode(final StringBuilder sb, final Cookie cookie) {
        if (cookie.getVersion() >= 1) {
            CookieEncoderUtil.add(sb, "$Version", 1L);
        }
        CookieEncoderUtil.add(sb, cookie.getName(), cookie.getValue());
        if (cookie.getPath() != null) {
            CookieEncoderUtil.add(sb, "$Path", cookie.getPath());
        }
        if (cookie.getDomain() != null) {
            CookieEncoderUtil.add(sb, "$Domain", cookie.getDomain());
        }
        if (cookie.getVersion() >= 1 && !cookie.getPorts().isEmpty()) {
            sb.append('$');
            sb.append("Port");
            sb.append('=');
            sb.append('\"');
            final Iterator<Integer> iterator = cookie.getPorts().iterator();
            while (iterator.hasNext()) {
                sb.append((int)iterator.next());
                sb.append(',');
            }
            sb.setCharAt(sb.length() - 1, '\"');
            sb.append(';');
            sb.append(' ');
        }
    }
    
    private ClientCookieEncoder() {
    }
}
