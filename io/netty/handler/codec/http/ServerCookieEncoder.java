package io.netty.handler.codec.http;

import java.util.*;

public final class ServerCookieEncoder
{
    public static String encode(final String s, final String s2) {
        return encode(new DefaultCookie(s, s2));
    }
    
    public static String encode(final Cookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie");
        }
        final StringBuilder stringBuilder = CookieEncoderUtil.stringBuilder();
        CookieEncoderUtil.add(stringBuilder, cookie.getName(), cookie.getValue());
        if (cookie.getMaxAge() != Long.MIN_VALUE) {
            if (cookie.getVersion() == 0) {
                CookieEncoderUtil.addUnquoted(stringBuilder, "Expires", HttpHeaderDateFormat.get().format(new Date(System.currentTimeMillis() + cookie.getMaxAge() * 1000L)));
            }
            else {
                CookieEncoderUtil.add(stringBuilder, "Max-Age", cookie.getMaxAge());
            }
        }
        if (cookie.getPath() != null) {
            if (cookie.getVersion() > 0) {
                CookieEncoderUtil.add(stringBuilder, "Path", cookie.getPath());
            }
            else {
                CookieEncoderUtil.addUnquoted(stringBuilder, "Path", cookie.getPath());
            }
        }
        if (cookie.getDomain() != null) {
            if (cookie.getVersion() > 0) {
                CookieEncoderUtil.add(stringBuilder, "Domain", cookie.getDomain());
            }
            else {
                CookieEncoderUtil.addUnquoted(stringBuilder, "Domain", cookie.getDomain());
            }
        }
        if (cookie.isSecure()) {
            stringBuilder.append("Secure");
            stringBuilder.append(';');
            stringBuilder.append(' ');
        }
        if (cookie.isHttpOnly()) {
            stringBuilder.append("HTTPOnly");
            stringBuilder.append(';');
            stringBuilder.append(' ');
        }
        if (cookie.getVersion() >= 1) {
            if (cookie.getComment() != null) {
                CookieEncoderUtil.add(stringBuilder, "Comment", cookie.getComment());
            }
            CookieEncoderUtil.add(stringBuilder, "Version", 1L);
            if (cookie.getCommentUrl() != null) {
                CookieEncoderUtil.addQuoted(stringBuilder, "CommentURL", cookie.getCommentUrl());
            }
            if (!cookie.getPorts().isEmpty()) {
                stringBuilder.append("Port");
                stringBuilder.append('=');
                stringBuilder.append('\"');
                final Iterator<Integer> iterator = cookie.getPorts().iterator();
                while (iterator.hasNext()) {
                    stringBuilder.append((int)iterator.next());
                    stringBuilder.append(',');
                }
                stringBuilder.setCharAt(stringBuilder.length() - 1, '\"');
                stringBuilder.append(';');
                stringBuilder.append(' ');
            }
            if (cookie.isDiscard()) {
                stringBuilder.append("Discard");
                stringBuilder.append(';');
                stringBuilder.append(' ');
            }
        }
        return CookieEncoderUtil.stripTrailingSeparator(stringBuilder);
    }
    
    public static List encode(final Cookie... array) {
        if (array == null) {
            throw new NullPointerException("cookies");
        }
        final ArrayList<String> list = new ArrayList<String>(array.length);
        while (0 < array.length) {
            final Cookie cookie = array[0];
            if (cookie == null) {
                break;
            }
            list.add(encode(cookie));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public static List encode(final Collection collection) {
        if (collection == null) {
            throw new NullPointerException("cookies");
        }
        final ArrayList<String> list = new ArrayList<String>(collection.size());
        for (final Cookie cookie : collection) {
            if (cookie == null) {
                break;
            }
            list.add(encode(cookie));
        }
        return list;
    }
    
    public static List encode(final Iterable iterable) {
        if (iterable == null) {
            throw new NullPointerException("cookies");
        }
        final ArrayList<String> list = new ArrayList<String>();
        for (final Cookie cookie : iterable) {
            if (cookie == null) {
                break;
            }
            list.add(encode(cookie));
        }
        return list;
    }
    
    private ServerCookieEncoder() {
    }
}
