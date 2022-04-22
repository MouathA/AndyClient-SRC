package org.apache.http.client.protocol;

import org.apache.http.annotation.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.apache.http.auth.*;
import org.apache.http.*;
import java.io.*;

@Deprecated
@Immutable
public class RequestTargetAuthentication extends RequestAuthenticationBase
{
    public void process(final HttpRequest httpRequest, final HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            return;
        }
        if (httpRequest.containsHeader("Authorization")) {
            return;
        }
        final AuthState authState = (AuthState)httpContext.getAttribute("http.auth.target-scope");
        if (authState == null) {
            this.log.debug("Target auth state not set in the context");
            return;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Target auth state: " + authState.getState());
        }
        this.process(authState, httpRequest, httpContext);
    }
}
