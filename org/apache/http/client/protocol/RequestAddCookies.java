package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import org.apache.http.client.methods.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.client.*;
import org.apache.http.config.*;
import org.apache.http.*;
import org.apache.http.conn.routing.*;
import org.apache.http.cookie.*;
import java.util.*;
import java.io.*;

@Immutable
public class RequestAddCookies implements HttpRequestInterceptor
{
    private final Log log;
    
    public RequestAddCookies() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            return;
        }
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        final CookieStore cookieStore = adapt.getCookieStore();
        if (cookieStore == null) {
            this.log.debug("Cookie store not specified in HTTP context");
            return;
        }
        final Lookup cookieSpecRegistry = adapt.getCookieSpecRegistry();
        if (cookieSpecRegistry == null) {
            this.log.debug("CookieSpec registry not specified in HTTP context");
            return;
        }
        final HttpHost targetHost = adapt.getTargetHost();
        if (targetHost == null) {
            this.log.debug("Target host not set in the context");
            return;
        }
        final RouteInfo httpRoute = adapt.getHttpRoute();
        if (httpRoute == null) {
            this.log.debug("Connection route not set in the context");
            return;
        }
        String cookieSpec = adapt.getRequestConfig().getCookieSpec();
        if (cookieSpec == null) {
            cookieSpec = "best-match";
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("CookieSpec selected: " + cookieSpec);
        }
        URI uri;
        if (httpRequest instanceof HttpUriRequest) {
            uri = ((HttpUriRequest)httpRequest).getURI();
        }
        else {
            uri = new URI(httpRequest.getRequestLine().getUri());
        }
        final String s = (uri != null) ? uri.getPath() : null;
        final String hostName = targetHost.getHostName();
        int n = targetHost.getPort();
        if (n < 0) {
            n = httpRoute.getTargetHost().getPort();
        }
        final CookieOrigin cookieOrigin = new CookieOrigin(hostName, (n >= 0) ? n : 0, TextUtils.isEmpty(s) ? "/" : s, httpRoute.isSecure());
        final CookieSpecProvider cookieSpecProvider = (CookieSpecProvider)cookieSpecRegistry.lookup(cookieSpec);
        if (cookieSpecProvider == null) {
            throw new HttpException("Unsupported cookie policy: " + cookieSpec);
        }
        final CookieSpec create = cookieSpecProvider.create(adapt);
        final ArrayList list = new ArrayList<Cookie>(cookieStore.getCookies());
        final ArrayList<Cookie> list2 = new ArrayList<Cookie>();
        final Date date = new Date();
        for (final Cookie cookie : list) {
            if (!cookie.isExpired(date)) {
                if (!create.match(cookie, cookieOrigin)) {
                    continue;
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Cookie " + cookie + " match " + cookieOrigin);
                }
                list2.add(cookie);
            }
            else {
                if (!this.log.isDebugEnabled()) {
                    continue;
                }
                this.log.debug("Cookie " + cookie + " expired");
            }
        }
        if (!list2.isEmpty()) {
            final Iterator iterator2 = create.formatCookies(list2).iterator();
            while (iterator2.hasNext()) {
                httpRequest.addHeader(iterator2.next());
            }
        }
        final int version = create.getVersion();
        if (version > 0) {
            for (final Cookie cookie2 : list2) {
                if (version != cookie2.getVersion() || !(cookie2 instanceof SetCookie2)) {}
            }
            if (true) {
                final Header versionHeader = create.getVersionHeader();
                if (versionHeader != null) {
                    httpRequest.addHeader(versionHeader);
                }
            }
        }
        httpContext.setAttribute("http.cookie-spec", create);
        httpContext.setAttribute("http.cookie-origin", cookieOrigin);
    }
}
