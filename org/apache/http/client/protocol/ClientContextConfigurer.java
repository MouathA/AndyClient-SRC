package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.cookie.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;

@Deprecated
@NotThreadSafe
public class ClientContextConfigurer implements ClientContext
{
    private final HttpContext context;
    
    public ClientContextConfigurer(final HttpContext context) {
        Args.notNull(context, "HTTP context");
        this.context = context;
    }
    
    public void setCookieSpecRegistry(final CookieSpecRegistry cookieSpecRegistry) {
        this.context.setAttribute("http.cookiespec-registry", cookieSpecRegistry);
    }
    
    public void setAuthSchemeRegistry(final AuthSchemeRegistry authSchemeRegistry) {
        this.context.setAttribute("http.authscheme-registry", authSchemeRegistry);
    }
    
    public void setCookieStore(final CookieStore cookieStore) {
        this.context.setAttribute("http.cookie-store", cookieStore);
    }
    
    public void setCredentialsProvider(final CredentialsProvider credentialsProvider) {
        this.context.setAttribute("http.auth.credentials-provider", credentialsProvider);
    }
}
