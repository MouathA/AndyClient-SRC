package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class BrowserCompatVersionAttributeHandler extends AbstractCookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (s == null) {
            throw new MalformedCookieException("Missing value for version attribute");
        }
        Integer.parseInt(s);
        setCookie.setVersion(0);
    }
}
