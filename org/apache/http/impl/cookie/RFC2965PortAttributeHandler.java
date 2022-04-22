package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;

@Immutable
public class RFC2965PortAttributeHandler implements CookieAttributeHandler
{
    private static int[] parsePortAttribute(final String s) throws MalformedCookieException {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, ",");
        final int[] array = new int[stringTokenizer.countTokens()];
        while (stringTokenizer.hasMoreTokens()) {
            array[0] = Integer.parseInt(stringTokenizer.nextToken().trim());
            if (array[0] < 0) {
                throw new MalformedCookieException("Invalid Port attribute.");
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private static boolean portMatch(final int n, final int[] array) {
        while (0 < array.length && n != array[0]) {
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (setCookie instanceof SetCookie2) {
            final SetCookie2 setCookie2 = (SetCookie2)setCookie;
            if (s != null && s.trim().length() > 0) {
                setCookie2.setPorts(parsePortAttribute(s));
            }
        }
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final int port = cookieOrigin.getPort();
        if (cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("port") && !portMatch(port, cookie.getPorts())) {
            throw new CookieRestrictionViolationException("Port attribute violates RFC 2965: Request port not found in cookie's port list.");
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final int port = cookieOrigin.getPort();
        if (cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("port")) {
            if (cookie.getPorts() == null) {
                return false;
            }
            if (!portMatch(port, cookie.getPorts())) {
                return false;
            }
        }
        return true;
    }
}
