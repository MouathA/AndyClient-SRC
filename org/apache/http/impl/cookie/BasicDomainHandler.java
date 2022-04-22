package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class BasicDomainHandler implements CookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String domain) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (domain == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        }
        if (domain.trim().length() == 0) {
            throw new MalformedCookieException("Blank value for domain attribute");
        }
        setCookie.setDomain(domain);
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final String host = cookieOrigin.getHost();
        String s = cookie.getDomain();
        if (s == null) {
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
        }
        if (host.contains(".")) {
            if (!host.endsWith(s)) {
                if (s.startsWith(".")) {
                    s = s.substring(1, s.length());
                }
                if (!host.equals(s)) {
                    throw new CookieRestrictionViolationException("Illegal domain attribute \"" + s + "\". Domain of origin: \"" + host + "\"");
                }
            }
        }
        else if (!host.equals(s)) {
            throw new CookieRestrictionViolationException("Illegal domain attribute \"" + s + "\". Domain of origin: \"" + host + "\"");
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final String host = cookieOrigin.getHost();
        String s = cookie.getDomain();
        if (s == null) {
            return false;
        }
        if (host.equals(s)) {
            return true;
        }
        if (!s.startsWith(".")) {
            s = '.' + s;
        }
        return host.endsWith(s) || host.equals(s.substring(1));
    }
}
