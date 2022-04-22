package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class RFC2109VersionHandler extends AbstractCookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (s == null) {
            throw new MalformedCookieException("Missing value for version attribute");
        }
        if (s.trim().length() == 0) {
            throw new MalformedCookieException("Blank value for version attribute");
        }
        setCookie.setVersion(Integer.parseInt(s));
    }
    
    @Override
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        if (cookie.getVersion() < 0) {
            throw new CookieRestrictionViolationException("Cookie version may not be negative");
        }
    }
}
