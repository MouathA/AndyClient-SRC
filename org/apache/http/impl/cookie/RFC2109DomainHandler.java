package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;
import java.util.*;

@Immutable
public class RFC2109DomainHandler implements CookieAttributeHandler
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
        final String domain = cookie.getDomain();
        if (domain == null) {
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
        }
        if (!domain.equals(host)) {
            if (domain.indexOf(46) == -1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" does not match the host \"" + host + "\"");
            }
            if (!domain.startsWith(".")) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must start with a dot");
            }
            final int index = domain.indexOf(46, 1);
            if (index < 0 || index == domain.length() - 1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must contain an embedded dot");
            }
            final String lowerCase = host.toLowerCase(Locale.ENGLISH);
            if (!lowerCase.endsWith(domain)) {
                throw new CookieRestrictionViolationException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + lowerCase + "\"");
            }
            if (lowerCase.substring(0, lowerCase.length() - domain.length()).indexOf(46) != -1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates RFC 2109: host minus domain may not contain any dots");
            }
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final String host = cookieOrigin.getHost();
        final String domain = cookie.getDomain();
        return domain != null && (host.equals(domain) || (domain.startsWith(".") && host.endsWith(domain)));
    }
}
