package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.client.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.cookie.*;
import java.util.*;

@Immutable
public class ResponseProcessCookies implements HttpResponseInterceptor
{
    private final Log log;
    
    public ResponseProcessCookies() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public void process(final HttpResponse httpResponse, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        final CookieSpec cookieSpec = adapt.getCookieSpec();
        if (cookieSpec == null) {
            this.log.debug("Cookie spec not specified in HTTP context");
            return;
        }
        final CookieStore cookieStore = adapt.getCookieStore();
        if (cookieStore == null) {
            this.log.debug("Cookie store not specified in HTTP context");
            return;
        }
        final CookieOrigin cookieOrigin = adapt.getCookieOrigin();
        if (cookieOrigin == null) {
            this.log.debug("Cookie origin not specified in HTTP context");
            return;
        }
        this.processCookies(httpResponse.headerIterator("Set-Cookie"), cookieSpec, cookieOrigin, cookieStore);
        if (cookieSpec.getVersion() > 0) {
            this.processCookies(httpResponse.headerIterator("Set-Cookie2"), cookieSpec, cookieOrigin, cookieStore);
        }
    }
    
    private void processCookies(final HeaderIterator headerIterator, final CookieSpec cookieSpec, final CookieOrigin cookieOrigin, final CookieStore cookieStore) {
        while (headerIterator.hasNext()) {
            for (final Cookie cookie : cookieSpec.parse(headerIterator.nextHeader(), cookieOrigin)) {
                cookieSpec.validate(cookie, cookieOrigin);
                cookieStore.addCookie(cookie);
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Cookie accepted [" + formatCooke(cookie) + "]");
                }
            }
        }
    }
    
    private static String formatCooke(final Cookie cookie) {
        final StringBuilder sb = new StringBuilder();
        sb.append(cookie.getName());
        sb.append("=\"");
        String s = cookie.getValue();
        if (s.length() > 100) {
            s = s.substring(0, 100) + "...";
        }
        sb.append(s);
        sb.append("\"");
        sb.append(", version:");
        sb.append(Integer.toString(cookie.getVersion()));
        sb.append(", domain:");
        sb.append(cookie.getDomain());
        sb.append(", path:");
        sb.append(cookie.getPath());
        sb.append(", expiry:");
        sb.append(cookie.getExpiryDate());
        return sb.toString();
    }
}
