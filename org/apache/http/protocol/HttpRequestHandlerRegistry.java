package org.apache.http.protocol;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;

@Deprecated
@ThreadSafe
public class HttpRequestHandlerRegistry implements HttpRequestHandlerResolver
{
    private final UriPatternMatcher matcher;
    
    public HttpRequestHandlerRegistry() {
        this.matcher = new UriPatternMatcher();
    }
    
    public void register(final String s, final HttpRequestHandler httpRequestHandler) {
        Args.notNull(s, "URI request pattern");
        Args.notNull(httpRequestHandler, "Request handler");
        this.matcher.register(s, httpRequestHandler);
    }
    
    public void unregister(final String s) {
        this.matcher.unregister(s);
    }
    
    public void setHandlers(final Map objects) {
        this.matcher.setObjects(objects);
    }
    
    public Map getHandlers() {
        return this.matcher.getObjects();
    }
    
    public HttpRequestHandler lookup(final String s) {
        return (HttpRequestHandler)this.matcher.lookup(s);
    }
}
