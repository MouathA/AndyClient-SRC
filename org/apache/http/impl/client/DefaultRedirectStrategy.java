package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.commons.logging.*;
import org.apache.http.protocol.*;
import java.net.*;
import org.apache.http.client.protocol.*;
import org.apache.http.client.*;
import org.apache.http.client.config.*;
import org.apache.http.*;
import org.apache.http.client.utils.*;
import java.util.*;
import org.apache.http.util.*;
import org.apache.http.client.methods.*;

@Immutable
public class DefaultRedirectStrategy implements RedirectStrategy
{
    private final Log log;
    @Deprecated
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    public static final DefaultRedirectStrategy INSTANCE;
    
    public DefaultRedirectStrategy() {
        this.log = LogFactory.getLog(this.getClass());
    }
    
    public boolean isRedirected(final HttpRequest httpRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws ProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpResponse, "HTTP response");
        final int statusCode = httpResponse.getStatusLine().getStatusCode();
        final String method = httpRequest.getRequestLine().getMethod();
        final Header firstHeader = httpResponse.getFirstHeader("location");
        switch (statusCode) {
            case 302: {
                return this.isRedirectable(method) && firstHeader != null;
            }
            case 301:
            case 307: {
                return this.isRedirectable(method);
            }
            case 303: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public URI getLocationURI(final HttpRequest httpRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws ProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        final HttpClientContext adapt = HttpClientContext.adapt(httpContext);
        final Header firstHeader = httpResponse.getFirstHeader("location");
        if (firstHeader == null) {
            throw new ProtocolException("Received redirect response " + httpResponse.getStatusLine() + " but no location header");
        }
        final String value = firstHeader.getValue();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect requested to location '" + value + "'");
        }
        final RequestConfig requestConfig = adapt.getRequestConfig();
        URI uri = this.createLocationURI(value);
        if (!uri.isAbsolute()) {
            if (!requestConfig.isRelativeRedirectsAllowed()) {
                throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
            }
            final HttpHost targetHost = adapt.getTargetHost();
            Asserts.notNull(targetHost, "Target host");
            uri = URIUtils.resolve(URIUtils.rewriteURI(new URI(httpRequest.getRequestLine().getUri()), targetHost, false), uri);
        }
        RedirectLocations redirectLocations = (RedirectLocations)adapt.getAttribute("http.protocol.redirect-locations");
        if (redirectLocations == null) {
            redirectLocations = new RedirectLocations();
            httpContext.setAttribute("http.protocol.redirect-locations", redirectLocations);
        }
        if (!requestConfig.isCircularRedirectsAllowed() && redirectLocations.contains(uri)) {
            throw new CircularRedirectException("Circular redirect to '" + uri + "'");
        }
        redirectLocations.add(uri);
        return uri;
    }
    
    protected URI createLocationURI(final String s) throws ProtocolException {
        final URIBuilder uriBuilder = new URIBuilder(new URI(s).normalize());
        final String host = uriBuilder.getHost();
        if (host != null) {
            uriBuilder.setHost(host.toLowerCase(Locale.US));
        }
        if (TextUtils.isEmpty(uriBuilder.getPath())) {
            uriBuilder.setPath("/");
        }
        return uriBuilder.build();
    }
    
    protected boolean isRedirectable(final String s) {
        final String[] redirect_METHODS = DefaultRedirectStrategy.REDIRECT_METHODS;
        while (0 < redirect_METHODS.length) {
            if (redirect_METHODS[0].equalsIgnoreCase(s)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public HttpUriRequest getRedirect(final HttpRequest httpRequest, final HttpResponse httpResponse, final HttpContext httpContext) throws ProtocolException {
        final URI locationURI = this.getLocationURI(httpRequest, httpResponse, httpContext);
        final String method = httpRequest.getRequestLine().getMethod();
        if (method.equalsIgnoreCase("HEAD")) {
            return new HttpHead(locationURI);
        }
        if (method.equalsIgnoreCase("GET")) {
            return new HttpGet(locationURI);
        }
        if (httpResponse.getStatusLine().getStatusCode() == 307) {
            return RequestBuilder.copy(httpRequest).setUri(locationURI).build();
        }
        return new HttpGet(locationURI);
    }
    
    static {
        INSTANCE = new DefaultRedirectStrategy();
        DefaultRedirectStrategy.REDIRECT_METHODS = new String[] { "GET", "HEAD" };
    }
}
