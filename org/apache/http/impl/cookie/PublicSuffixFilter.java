package org.apache.http.impl.cookie;

import java.util.*;
import org.apache.http.cookie.*;
import org.apache.http.client.utils.*;

public class PublicSuffixFilter implements CookieAttributeHandler
{
    private final CookieAttributeHandler wrapped;
    private Set exceptions;
    private Set suffixes;
    
    public PublicSuffixFilter(final CookieAttributeHandler wrapped) {
        this.wrapped = wrapped;
    }
    
    public void setPublicSuffixes(final Collection collection) {
        this.suffixes = new HashSet(collection);
    }
    
    public void setExceptions(final Collection collection) {
        this.exceptions = new HashSet(collection);
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        return !this.isForPublicSuffix(cookie) && this.wrapped.match(cookie, cookieOrigin);
    }
    
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        this.wrapped.parse(setCookie, s);
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        this.wrapped.validate(cookie, cookieOrigin);
    }
    
    private boolean isForPublicSuffix(final Cookie cookie) {
        String s = cookie.getDomain();
        if (s.startsWith(".")) {
            s = s.substring(1);
        }
        String s2 = Punycode.toUnicode(s);
        if (this.exceptions != null && this.exceptions.contains(s2)) {
            return false;
        }
        if (this.suffixes == null) {
            return false;
        }
        while (!this.suffixes.contains(s2)) {
            if (s2.startsWith("*.")) {
                s2 = s2.substring(2);
            }
            final int index = s2.indexOf(46);
            if (index != -1) {
                s2 = "*" + s2.substring(index);
                if (s2.length() > 0) {
                    continue;
                }
            }
            return false;
        }
        return true;
    }
}
