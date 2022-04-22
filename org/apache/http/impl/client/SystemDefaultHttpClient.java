package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import java.net.*;
import org.apache.http.impl.conn.*;
import org.apache.http.*;
import org.apache.http.impl.*;

@Deprecated
@ThreadSafe
public class SystemDefaultHttpClient extends DefaultHttpClient
{
    public SystemDefaultHttpClient(final HttpParams httpParams) {
        super(null, httpParams);
    }
    
    public SystemDefaultHttpClient() {
        super(null, null);
    }
    
    @Override
    protected ClientConnectionManager createClientConnectionManager() {
        final PoolingClientConnectionManager poolingClientConnectionManager = new PoolingClientConnectionManager(SchemeRegistryFactory.createSystemDefault());
        if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
            final int int1 = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
            poolingClientConnectionManager.setDefaultMaxPerRoute(int1);
            poolingClientConnectionManager.setMaxTotal(2 * int1);
        }
        return poolingClientConnectionManager;
    }
    
    @Override
    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new ProxySelectorRoutePlanner(this.getConnectionManager().getSchemeRegistry(), ProxySelector.getDefault());
    }
    
    @Override
    protected ConnectionReuseStrategy createConnectionReuseStrategy() {
        if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
            return new DefaultConnectionReuseStrategy();
        }
        return new NoConnectionReuseStrategy();
    }
}
