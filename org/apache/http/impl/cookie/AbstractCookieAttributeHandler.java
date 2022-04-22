package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.cookie.*;

@Immutable
public abstract class AbstractCookieAttributeHandler implements CookieAttributeHandler
{
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        return true;
    }
}
