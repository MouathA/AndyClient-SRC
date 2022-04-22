package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.conn.*;
import org.apache.http.impl.conn.*;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.util.*;

@NotThreadSafe
public class BasicAuthCache implements AuthCache
{
    private final HashMap map;
    private final SchemePortResolver schemePortResolver;
    
    public BasicAuthCache(final SchemePortResolver schemePortResolver) {
        this.map = new HashMap();
        this.schemePortResolver = ((schemePortResolver != null) ? schemePortResolver : DefaultSchemePortResolver.INSTANCE);
    }
    
    public BasicAuthCache() {
        this(null);
    }
    
    protected HttpHost getKey(final HttpHost httpHost) {
        if (httpHost.getPort() <= 0) {
            return new HttpHost(httpHost.getHostName(), this.schemePortResolver.resolve(httpHost), httpHost.getSchemeName());
        }
        return httpHost;
    }
    
    public void put(final HttpHost httpHost, final AuthScheme authScheme) {
        Args.notNull(httpHost, "HTTP host");
        this.map.put(this.getKey(httpHost), authScheme);
    }
    
    public AuthScheme get(final HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        return this.map.get(this.getKey(httpHost));
    }
    
    public void remove(final HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        this.map.remove(this.getKey(httpHost));
    }
    
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
}
