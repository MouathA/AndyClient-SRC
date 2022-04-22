package org.apache.http.conn.params;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.*;
import java.net.*;
import org.apache.http.conn.routing.*;

@Deprecated
@NotThreadSafe
public class ConnRouteParamBean extends HttpAbstractParamBean
{
    public ConnRouteParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    public void setDefaultProxy(final HttpHost httpHost) {
        this.params.setParameter("http.route.default-proxy", httpHost);
    }
    
    public void setLocalAddress(final InetAddress inetAddress) {
        this.params.setParameter("http.route.local-address", inetAddress);
    }
    
    public void setForcedRoute(final HttpRoute httpRoute) {
        this.params.setParameter("http.route.forced-route", httpRoute);
    }
}
