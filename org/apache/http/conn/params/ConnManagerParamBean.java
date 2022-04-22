package org.apache.http.conn.params;

import org.apache.http.annotation.*;
import org.apache.http.params.*;

@Deprecated
@NotThreadSafe
public class ConnManagerParamBean extends HttpAbstractParamBean
{
    public ConnManagerParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    public void setTimeout(final long n) {
        this.params.setLongParameter("http.conn-manager.timeout", n);
    }
    
    public void setMaxTotalConnections(final int n) {
        this.params.setIntParameter("http.conn-manager.max-total", n);
    }
    
    public void setConnectionsPerRoute(final ConnPerRouteBean connPerRouteBean) {
        this.params.setParameter("http.conn-manager.max-per-route", connPerRouteBean);
    }
}
