package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class BasicSecureHandler extends AbstractCookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        setCookie.setSecure(true);
    }
    
    @Override
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        return !cookie.isSecure() || cookieOrigin.isSecure();
    }
}
