package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;
import java.util.*;

@Immutable
public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler
{
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (s == null) {
            throw new MalformedCookieException("Missing value for max-age attribute");
        }
        final int int1 = Integer.parseInt(s);
        if (int1 < 0) {
            throw new MalformedCookieException("Negative max-age attribute: " + s);
        }
        setCookie.setExpiryDate(new Date(System.currentTimeMillis() + int1 * 1000L));
    }
}
