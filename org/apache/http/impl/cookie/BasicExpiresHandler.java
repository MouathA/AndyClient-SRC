package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;
import org.apache.http.client.utils.*;
import java.util.*;

@Immutable
public class BasicExpiresHandler extends AbstractCookieAttributeHandler
{
    private final String[] datepatterns;
    
    public BasicExpiresHandler(final String[] datepatterns) {
        Args.notNull(datepatterns, "Array of date patterns");
        this.datepatterns = datepatterns;
    }
    
    public void parse(final SetCookie setCookie, final String s) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (s == null) {
            throw new MalformedCookieException("Missing value for expires attribute");
        }
        final Date date = DateUtils.parseDate(s, this.datepatterns);
        if (date == null) {
            throw new MalformedCookieException("Unable to parse expires attribute: " + s);
        }
        setCookie.setExpiryDate(date);
    }
}
