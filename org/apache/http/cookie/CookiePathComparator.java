package org.apache.http.cookie;

import java.io.*;
import java.util.*;
import org.apache.http.annotation.*;

@Immutable
public class CookiePathComparator implements Serializable, Comparator
{
    private static final long serialVersionUID = 7523645369616405818L;
    
    private String normalizePath(final Cookie cookie) {
        String s = cookie.getPath();
        if (s == null) {
            s = "/";
        }
        if (!s.endsWith("/")) {
            s += '/';
        }
        return s;
    }
    
    public int compare(final Cookie cookie, final Cookie cookie2) {
        final String normalizePath = this.normalizePath(cookie);
        final String normalizePath2 = this.normalizePath(cookie2);
        if (normalizePath.equals(normalizePath2)) {
            return 0;
        }
        if (normalizePath.startsWith(normalizePath2)) {
            return -1;
        }
        if (normalizePath2.startsWith(normalizePath)) {
            return 1;
        }
        return 0;
    }
    
    public int compare(final Object o, final Object o2) {
        return this.compare((Cookie)o, (Cookie)o2);
    }
}
