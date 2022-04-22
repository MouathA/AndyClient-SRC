package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import java.net.*;
import org.apache.http.util.*;
import org.apache.http.client.utils.*;
import org.apache.http.client.*;
import org.apache.http.*;
import org.apache.http.params.*;

@Deprecated
@Immutable
public class DefaultRedirectHandler implements RedirectHandler
{
    private final Log log;
    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    
    public DefaultRedirectHandler() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public boolean isRedirectRequested(final HttpResponse httpResponse, final HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        switch (httpResponse.getStatusLine().getStatusCode()) {
            case 301:
            case 302:
            case 307: {
                final String method = ((HttpRequest)httpContext.getAttribute("http.request")).getRequestLine().getMethod();
                return method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("HEAD");
            }
            case 303: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public URI getLocationURI(final HttpResponse httpResponse, final HttpContext httpContext) throws ProtocolException {
        Args.notNull(httpResponse, "HTTP response");
        final Header firstHeader = httpResponse.getFirstHeader("location");
        if (firstHeader == null) {
            throw new ProtocolException("Received redirect response " + httpResponse.getStatusLine() + " but no location header");
        }
        final String value = firstHeader.getValue();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect requested to location '" + value + "'");
        }
        URI resolve = new URI(value);
        final HttpParams params = httpResponse.getParams();
        if (!resolve.isAbsolute()) {
            if (params.isParameterTrue("http.protocol.reject-relative-redirect")) {
                throw new ProtocolException("Relative redirect location '" + resolve + "' not allowed");
            }
            final HttpHost httpHost = (HttpHost)httpContext.getAttribute("http.target_host");
            Asserts.notNull(httpHost, "Target host");
            resolve = URIUtils.resolve(URIUtils.rewriteURI(new URI(((HttpRequest)httpContext.getAttribute("http.request")).getRequestLine().getUri()), httpHost, true), resolve);
        }
        if (params.isParameterFalse("http.protocol.allow-circular-redirects")) {
            RedirectLocations redirectLocations = (RedirectLocations)httpContext.getAttribute("http.protocol.redirect-locations");
            if (redirectLocations == null) {
                redirectLocations = new RedirectLocations();
                httpContext.setAttribute("http.protocol.redirect-locations", redirectLocations);
            }
            URI rewriteURI;
            if (resolve.getFragment() != null) {
                rewriteURI = URIUtils.rewriteURI(resolve, new HttpHost(resolve.getHost(), resolve.getPort(), resolve.getScheme()), true);
            }
            else {
                rewriteURI = resolve;
            }
            if (redirectLocations.contains(rewriteURI)) {
                throw new CircularRedirectException("Circular redirect to '" + rewriteURI + "'");
            }
            redirectLocations.add(rewriteURI);
        }
        return resolve;
    }
}
