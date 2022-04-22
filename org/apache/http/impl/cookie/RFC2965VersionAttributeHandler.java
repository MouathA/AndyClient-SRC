package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class RFC2965VersionAttributeHandler implements CookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (s == null) {
            throw new MalformedCookieException("Missing value for version attribute");
        }
        Integer.parseInt(s);
        if (-1 < 0) {
            throw new MalformedCookieException("Invalid cookie version.");
        }
        setCookie.setVersion(-1);
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        if (cookie instanceof SetCookie2 && cookie instanceof ClientCookie && !((ClientCookie)cookie).containsAttribute("version")) {
            throw new CookieRestrictionViolationException("Violates RFC 2965. Version attribute is required.");
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        return true;
    }
}
