package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@ThreadSafe
public class UriHttpRequestHandlerMapper implements HttpRequestHandlerMapper
{
    private final UriPatternMatcher matcher;
    
    protected UriHttpRequestHandlerMapper(final UriPatternMatcher uriPatternMatcher) {
        this.matcher = (UriPatternMatcher)Args.notNull(uriPatternMatcher, "Pattern matcher");
    }
    
    public UriHttpRequestHandlerMapper() {
        this(new UriPatternMatcher());
    }
    
    public void register(final String s, final HttpRequestHandler httpRequestHandler) {
        Args.notNull(s, "Pattern");
        Args.notNull(httpRequestHandler, "Handler");
        this.matcher.register(s, httpRequestHandler);
    }
    
    public void unregister(final String s) {
        this.matcher.unregister(s);
    }
    
    protected String getRequestPath(final HttpRequest httpRequest) {
        String s = httpRequest.getRequestLine().getUri();
        final int index = s.indexOf("?");
        if (index != -1) {
            s = s.substring(0, index);
        }
        else {
            final int index2 = s.indexOf("#");
            if (index2 != -1) {
                s = s.substring(0, index2);
            }
        }
        return s;
    }
    
    public HttpRequestHandler lookup(final HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        return (HttpRequestHandler)this.matcher.lookup(this.getRequestPath(httpRequest));
    }
}
