package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.cookie.*;
import java.util.*;
import org.apache.http.util.*;

@Immutable
public class NetscapeDomainHandler extends BasicDomainHandler
{
    @Override
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        super.validate(cookie, cookieOrigin);
        final String host = cookieOrigin.getHost();
        final String domain = cookie.getDomain();
        if (host.contains(".")) {
            final int countTokens = new StringTokenizer(domain, ".").countTokens();
            if (isSpecialDomain(domain)) {
                if (countTokens < 2) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification for " + "special domains");
                }
            }
            else if (countTokens < 3) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification");
            }
        }
    }
    
    private static boolean isSpecialDomain(final String s) {
        final String upperCase = s.toUpperCase(Locale.ENGLISH);
        return upperCase.endsWith(".COM") || upperCase.endsWith(".EDU") || upperCase.endsWith(".NET") || upperCase.endsWith(".GOV") || upperCase.endsWith(".MIL") || upperCase.endsWith(".ORG") || upperCase.endsWith(".INT");
    }
    
    @Override
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final String host = cookieOrigin.getHost();
        final String domain = cookie.getDomain();
        return domain != null && host.endsWith(domain);
    }
}
