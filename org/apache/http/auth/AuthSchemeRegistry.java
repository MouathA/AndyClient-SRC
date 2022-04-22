package org.apache.http.auth;

import org.apache.http.config.*;
import org.apache.http.annotation.*;
import java.util.concurrent.*;
import org.apache.http.util.*;
import org.apache.http.params.*;
import java.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;

@Deprecated
@ThreadSafe
public final class AuthSchemeRegistry implements Lookup
{
    private final ConcurrentHashMap registeredSchemes;
    
    public AuthSchemeRegistry() {
        this.registeredSchemes = new ConcurrentHashMap();
    }
    
    public void register(final String s, final AuthSchemeFactory authSchemeFactory) {
        Args.notNull(s, "Name");
        Args.notNull(authSchemeFactory, "Authentication scheme factory");
        this.registeredSchemes.put(s.toLowerCase(Locale.ENGLISH), authSchemeFactory);
    }
    
    public void unregister(final String s) {
        Args.notNull(s, "Name");
        this.registeredSchemes.remove(s.toLowerCase(Locale.ENGLISH));
    }
    
    public AuthScheme getAuthScheme(final String s, final HttpParams httpParams) throws IllegalStateException {
        Args.notNull(s, "Name");
        final AuthSchemeFactory authSchemeFactory = this.registeredSchemes.get(s.toLowerCase(Locale.ENGLISH));
        if (authSchemeFactory != null) {
            return authSchemeFactory.newInstance(httpParams);
        }
        throw new IllegalStateException("Unsupported authentication scheme: " + s);
    }
    
    public List getSchemeNames() {
        return new ArrayList(this.registeredSchemes.keySet());
    }
    
    public void setItems(final Map map) {
        if (map == null) {
            return;
        }
        this.registeredSchemes.clear();
        this.registeredSchemes.putAll(map);
    }
    
    public AuthSchemeProvider lookup(final String s) {
        return new AuthSchemeProvider(s) {
            final String val$name;
            final AuthSchemeRegistry this$0;
            
            public AuthScheme create(final HttpContext httpContext) {
                return this.this$0.getAuthScheme(this.val$name, ((HttpRequest)httpContext.getAttribute("http.request")).getParams());
            }
        };
    }
    
    public Object lookup(final String s) {
        return this.lookup(s);
    }
}
