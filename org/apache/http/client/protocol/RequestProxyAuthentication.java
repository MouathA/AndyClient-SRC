package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.conn.*;
import org.apache.http.auth.*;
import org.apache.http.*;
import java.io.*;

@Deprecated
@Immutable
public class RequestProxyAuthentication extends RequestAuthenticationBase
{
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (httpRequest.containsHeader("Proxy-Authorization")) {
            return;
        }
        final HttpRoutedConnection httpRoutedConnection = (HttpRoutedConnection)httpContext.getAttribute("http.connection");
        if (httpRoutedConnection == null) {
            this.log.debug("HTTP connection not set in the context");
            return;
        }
        if (httpRoutedConnection.getRoute().isTunnelled()) {
            return;
        }
        final AuthState authState = (AuthState)httpContext.getAttribute("http.auth.proxy-scope");
        if (authState == null) {
            this.log.debug("Proxy auth state not set in the context");
            return;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Proxy auth state: " + authState.getState());
        }
        this.process(authState, httpRequest, httpContext);
    }
}
