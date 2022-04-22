package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class BasicPathHandler implements CookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        setCookie.setPath(TextUtils.isBlank(s) ? "/" : s);
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (!this.match(cookie, cookieOrigin)) {
            throw new CookieRestrictionViolationException("Illegal path attribute \"" + cookie.getPath() + "\". Path of origin: \"" + cookieOrigin.getPath() + "\"");
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final String path = cookieOrigin.getPath();
        String s = cookie.getPath();
        if (s == null) {
            s = "/";
        }
        if (s.length() > 1 && s.endsWith("/")) {
            s = s.substring(0, s.length() - 1);
        }
        boolean startsWith = path.startsWith(s);
        if (startsWith && path.length() != s.length() && !s.endsWith("/")) {
            startsWith = (path.charAt(s.length()) == '/');
        }
        return startsWith;
    }
}
