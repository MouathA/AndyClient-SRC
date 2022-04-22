package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;
import java.util.*;

@Deprecated
@Immutable
public class DefaultProxyAuthenticationHandler extends AbstractAuthenticationHandler
{
    public boolean isAuthenticationRequested(final HttpResponse httpResponse, final HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        return httpResponse.getStatusLine().getStatusCode() == 407;
    }
    
    public Map getChallenges(final HttpResponse httpResponse, final HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(httpResponse, "HTTP response");
        return this.parseChallenges(httpResponse.getHeaders("Proxy-Authenticate"));
    }
    
    @Override
    protected List getAuthPreferences(final HttpResponse httpResponse, final HttpContext httpContext) {
        final List list = (List)httpResponse.getParams().getParameter("http.auth.proxy-scheme-pref");
        if (list != null) {
            return list;
        }
        return super.getAuthPreferences(httpResponse, httpContext);
    }
}
