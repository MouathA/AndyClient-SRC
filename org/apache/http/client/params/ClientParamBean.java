package org.apache.http.client.params;

import org.apache.http.annotation.*;
import org.apache.http.params.*;
import org.apache.http.*;
import java.util.*;

@Deprecated
@NotThreadSafe
public class ClientParamBean extends HttpAbstractParamBean
{
    public ClientParamBean(final HttpParams httpParams) {
        super(httpParams);
    }
    
    @Deprecated
    public void setConnectionManagerFactoryClassName(final String s) {
        this.params.setParameter("http.connection-manager.factory-class-name", s);
    }
    
    public void setHandleRedirects(final boolean b) {
        this.params.setBooleanParameter("http.protocol.handle-redirects", b);
    }
    
    public void setRejectRelativeRedirect(final boolean b) {
        this.params.setBooleanParameter("http.protocol.reject-relative-redirect", b);
    }
    
    public void setMaxRedirects(final int n) {
        this.params.setIntParameter("http.protocol.max-redirects", n);
    }
    
    public void setAllowCircularRedirects(final boolean b) {
        this.params.setBooleanParameter("http.protocol.allow-circular-redirects", b);
    }
    
    public void setHandleAuthentication(final boolean b) {
        this.params.setBooleanParameter("http.protocol.handle-authentication", b);
    }
    
    public void setCookiePolicy(final String s) {
        this.params.setParameter("http.protocol.cookie-policy", s);
    }
    
    public void setVirtualHost(final HttpHost httpHost) {
        this.params.setParameter("http.virtual-host", httpHost);
    }
    
    public void setDefaultHeaders(final Collection collection) {
        this.params.setParameter("http.default-headers", collection);
    }
    
    public void setDefaultHost(final HttpHost httpHost) {
        this.params.setParameter("http.default-host", httpHost);
    }
    
    public void setConnectionManagerTimeout(final long n) {
        this.params.setLongParameter("http.conn-manager.timeout", n);
    }
}
