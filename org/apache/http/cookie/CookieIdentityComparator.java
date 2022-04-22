package org.apache.http.cookie;

import java.io.*;
import java.util.*;
import org.apache.http.annotation.*;

@Immutable
public class CookieIdentityComparator implements Serializable, Comparator
{
    private static final long serialVersionUID = 4466565437490631532L;
    
    public int compare(final Cookie cookie, final Cookie cookie2) {
        int n = cookie.getName().compareTo(cookie2.getName());
        if (n == 0) {
            String s = cookie.getDomain();
            if (s == null) {
                s = "";
            }
            else if (s.indexOf(46) == -1) {
                s += ".local";
            }
            String s2 = cookie2.getDomain();
            if (s2 == null) {
                s2 = "";
            }
            else if (s2.indexOf(46) == -1) {
                s2 += ".local";
            }
            n = s.compareToIgnoreCase(s2);
        }
        if (n == 0) {
            String path = cookie.getPath();
            if (path == null) {
                path = "/";
            }
            String path2 = cookie2.getPath();
            if (path2 == null) {
                path2 = "/";
            }
            n = path.compareTo(path2);
        }
        return n;
    }
    
    public int compare(final Object o, final Object o2) {
        return this.compare((Cookie)o, (Cookie)o2);
    }
}
