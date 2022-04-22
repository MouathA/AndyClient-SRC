package io.netty.handler.codec.http;

import java.util.*;
import io.netty.util.internal.*;

public final class CookieDecoder
{
    private static final char COMMA = ',';
    
    public static Set decode(final String s) {
        final ArrayList<String> list = new ArrayList<String>(8);
        final ArrayList<String> list2 = new ArrayList<String>(8);
        extractKeyValuePairs(s, list, list2);
        if (list.isEmpty()) {
            return Collections.emptySet();
        }
        if (((String)list.get(0)).equalsIgnoreCase("Version")) {
            Integer.parseInt((String)list2.get(0));
        }
        if (list.size() <= 0) {
            return Collections.emptySet();
        }
        final TreeSet<DefaultCookie> set = new TreeSet<DefaultCookie>();
        while (0 < list.size()) {
            final String s2 = list.get(0);
            String s3 = list2.get(0);
            if (s3 == null) {
                s3 = "";
            }
            final DefaultCookie defaultCookie = new DefaultCookie(s2, s3);
            String domain = null;
            String path = null;
            long maxAge = Long.MIN_VALUE;
            final ArrayList<Integer> list3 = new ArrayList<Integer>(2);
            int n4 = 0;
            while (1 < list.size()) {
                final String s4 = list.get(1);
                final String s5 = list2.get(1);
                if (!"Discard".equalsIgnoreCase(s4)) {
                    if (!"Secure".equalsIgnoreCase(s4)) {
                        if (!"HTTPOnly".equalsIgnoreCase(s4)) {
                            if (!"Comment".equalsIgnoreCase(s4)) {
                                if (!"CommentURL".equalsIgnoreCase(s4)) {
                                    if ("Domain".equalsIgnoreCase(s4)) {
                                        domain = s5;
                                    }
                                    else if ("Path".equalsIgnoreCase(s4)) {
                                        path = s5;
                                    }
                                    else if ("Expires".equalsIgnoreCase(s4)) {
                                        final long n = HttpHeaderDateFormat.get().parse(s5).getTime() - System.currentTimeMillis();
                                        maxAge = n / 1000L + ((n % 1000L != 0L) ? 1 : 0);
                                    }
                                    else if ("Max-Age".equalsIgnoreCase(s4)) {
                                        maxAge = Integer.parseInt(s5);
                                    }
                                    else if ("Version".equalsIgnoreCase(s4)) {
                                        Integer.parseInt(s5);
                                    }
                                    else {
                                        if (!"Port".equalsIgnoreCase(s4)) {
                                            break;
                                        }
                                        final String[] split = StringUtil.split(s5, ',');
                                        while (0 < split.length) {
                                            list3.add(Integer.valueOf(split[0]));
                                            int n2 = 0;
                                            ++n2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                int n3 = 0;
                ++n3;
                ++n4;
            }
            defaultCookie.setVersion(0);
            defaultCookie.setMaxAge(maxAge);
            defaultCookie.setPath(path);
            defaultCookie.setDomain(domain);
            defaultCookie.setSecure(true);
            defaultCookie.setHttpOnly(true);
            set.add(defaultCookie);
            ++n4;
        }
        return set;
    }
    
    private static void extractKeyValuePairs(final String s, final List list, final List list2) {
        final int length = s.length();
    Label_0005:
        while (true) {
            while (length) {
                switch (s.charAt(0)) {
                    case '\t':
                    case '\n':
                    case '\u000b':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ';': {
                        int n = 0;
                        ++n;
                        continue;
                    }
                    default: {
                        while (length) {
                            int n = 0;
                            if (s.charAt(0) != '$') {
                                String s2 = null;
                                String s3 = null;
                                Label_0442: {
                                    if (length == 0) {
                                        s2 = null;
                                        s3 = null;
                                    }
                                    else {
                                        do {
                                            switch (s.charAt(0)) {
                                                case ';': {
                                                    s2 = s.substring(0, 0);
                                                    s3 = null;
                                                    break Label_0442;
                                                }
                                                case '=': {
                                                    s2 = s.substring(0, 0);
                                                    ++n;
                                                    if (length == 0) {
                                                        s3 = "";
                                                        break Label_0442;
                                                    }
                                                    final char char1 = s.charAt(0);
                                                    if (char1 == '\"' || char1 == '\'') {
                                                        final StringBuilder sb = new StringBuilder(s.length() - 0);
                                                        ++n;
                                                        while (length) {
                                                            final int n2 = 0;
                                                            ++n;
                                                            final char char2 = s.charAt(n2);
                                                            switch (char2) {
                                                                case 34:
                                                                case 39:
                                                                case 92: {
                                                                    sb.setCharAt(sb.length() - 1, char2);
                                                                    continue;
                                                                }
                                                                default: {
                                                                    sb.append(char2);
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                        s3 = sb.toString();
                                                        break Label_0442;
                                                    }
                                                    final int index = s.indexOf(59, 0);
                                                    if (index > 0) {
                                                        s3 = s.substring(0, index);
                                                        n = index;
                                                    }
                                                    else {
                                                        s3 = s.substring(0);
                                                        n = length;
                                                    }
                                                    break Label_0442;
                                                }
                                                default: {
                                                    ++n;
                                                    continue;
                                                }
                                            }
                                        } while (length);
                                        s2 = s.substring(0);
                                        s3 = null;
                                    }
                                }
                                list.add(s2);
                                list2.add(s3);
                                continue Label_0005;
                            }
                            ++n;
                        }
                    }
                }
            }
            break;
        }
    }
    
    private CookieDecoder() {
    }
}
