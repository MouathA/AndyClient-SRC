package org.apache.http.cookie;

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
public final class CookieSpecRegistry implements Lookup
{
    private final ConcurrentHashMap registeredSpecs;
    
    public CookieSpecRegistry() {
        this.registeredSpecs = new ConcurrentHashMap();
    }
    
    public void register(final String s, final CookieSpecFactory cookieSpecFactory) {
        Args.notNull(s, "Name");
        Args.notNull(cookieSpecFactory, "Cookie spec factory");
        this.registeredSpecs.put(s.toLowerCase(Locale.ENGLISH), cookieSpecFactory);
    }
    
    public void unregister(final String s) {
        Args.notNull(s, "Id");
        this.registeredSpecs.remove(s.toLowerCase(Locale.ENGLISH));
    }
    
    public CookieSpec getCookieSpec(final String s, final HttpParams httpParams) throws IllegalStateException {
        Args.notNull(s, "Name");
        final CookieSpecFactory cookieSpecFactory = this.registeredSpecs.get(s.toLowerCase(Locale.ENGLISH));
        if (cookieSpecFactory != null) {
            return cookieSpecFactory.newInstance(httpParams);
        }
        throw new IllegalStateException("Unsupported cookie spec: " + s);
    }
    
    public CookieSpec getCookieSpec(final String s) throws IllegalStateException {
        return this.getCookieSpec(s, null);
    }
    
    public List getSpecNames() {
        return new ArrayList(this.registeredSpecs.keySet());
    }
    
    public void setItems(final Map map) {
        if (map == null) {
            return;
        }
        this.registeredSpecs.clear();
        this.registeredSpecs.putAll(map);
    }
    
    public CookieSpecProvider lookup(final String s) {
        return new CookieSpecProvider(s) {
            final String val$name;
            final CookieSpecRegistry this$0;
            
            public CookieSpec create(final HttpContext httpContext) {
                return this.this$0.getCookieSpec(this.val$name, ((HttpRequest)httpContext.getAttribute("http.request")).getParams());
            }
        };
    }
    
    public Object lookup(final String s) {
        return this.lookup(s);
    }
}
