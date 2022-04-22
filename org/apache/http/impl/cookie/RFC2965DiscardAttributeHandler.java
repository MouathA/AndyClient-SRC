package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.cookie.*;

@Immutable
public class RFC2965DiscardAttributeHandler implements CookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        if (setCookie instanceof SetCookie2) {
            ((SetCookie2)setCookie).setDiscard(true);
        }
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        return true;
    }
}
