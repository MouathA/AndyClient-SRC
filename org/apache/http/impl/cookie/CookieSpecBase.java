package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.cookie.*;
import org.apache.http.util.*;
import java.util.*;

@NotThreadSafe
public abstract class CookieSpecBase extends AbstractCookieSpec
{
    protected static String getDefaultPath(final CookieOrigin cookieOrigin) {
        final String path = cookieOrigin.getPath();
        path.lastIndexOf(47);
        return path.substring(0, 1);
    }
    
    protected static String getDefaultDomain(final CookieOrigin cookieOrigin) {
        return cookieOrigin.getHost();
    }
    
    protected List parse(final HeaderElement[] array, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        final ArrayList<BasicClientCookie> list = new ArrayList<BasicClientCookie>(array.length);
        while (0 < array.length) {
            final HeaderElement headerElement = array[0];
            final String name = headerElement.getName();
            final String value = headerElement.getValue();
            if (name == null || name.length() == 0) {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
            final BasicClientCookie basicClientCookie = new BasicClientCookie(name, value);
            basicClientCookie.setPath(getDefaultPath(cookieOrigin));
            basicClientCookie.setDomain(getDefaultDomain(cookieOrigin));
            final NameValuePair[] parameters = headerElement.getParameters();
            for (int i = parameters.length - 1; i >= 0; --i) {
                final NameValuePair nameValuePair = parameters[i];
                final String lowerCase = nameValuePair.getName().toLowerCase(Locale.ENGLISH);
                basicClientCookie.setAttribute(lowerCase, nameValuePair.getValue());
                final CookieAttributeHandler attribHandler = this.findAttribHandler(lowerCase);
                if (attribHandler != null) {
                    attribHandler.parse(basicClientCookie, nameValuePair.getValue());
                }
            }
            list.add(basicClientCookie);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public void validate(final Cookie cookie, final CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final Iterator<CookieAttributeHandler> iterator = this.getAttribHandlers().iterator();
        while (iterator.hasNext()) {
            iterator.next().validate(cookie, cookieOrigin);
        }
    }
    
    public boolean match(final Cookie cookie, final CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        final Iterator<CookieAttributeHandler> iterator = this.getAttribHandlers().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().match(cookie, cookieOrigin)) {
                return false;
            }
        }
        return true;
    }
}
