package org.apache.http.client.params;

import org.apache.http.params.*;
import org.apache.http.client.config.*;
import org.apache.http.*;
import java.net.*;
import java.util.*;

@Deprecated
public final class HttpClientParamConfig
{
    private HttpClientParamConfig() {
    }
    
    public static RequestConfig getRequestConfig(final HttpParams httpParams) {
        return RequestConfig.custom().setSocketTimeout(httpParams.getIntParameter("http.socket.timeout", 0)).setStaleConnectionCheckEnabled(httpParams.getBooleanParameter("http.connection.stalecheck", true)).setConnectTimeout(httpParams.getIntParameter("http.connection.timeout", 0)).setExpectContinueEnabled(httpParams.getBooleanParameter("http.protocol.expect-continue", false)).setProxy((HttpHost)httpParams.getParameter("http.route.default-proxy")).setLocalAddress((InetAddress)httpParams.getParameter("http.route.local-address")).setProxyPreferredAuthSchemes((Collection)httpParams.getParameter("http.auth.proxy-scheme-pref")).setTargetPreferredAuthSchemes((Collection)httpParams.getParameter("http.auth.target-scheme-pref")).setAuthenticationEnabled(httpParams.getBooleanParameter("http.protocol.handle-authentication", true)).setCircularRedirectsAllowed(httpParams.getBooleanParameter("http.protocol.allow-circular-redirects", false)).setConnectionRequestTimeout((int)httpParams.getLongParameter("http.conn-manager.timeout", 0L)).setCookieSpec((String)httpParams.getParameter("http.protocol.cookie-policy")).setMaxRedirects(httpParams.getIntParameter("http.protocol.max-redirects", 50)).setRedirectsEnabled(httpParams.getBooleanParameter("http.protocol.handle-redirects", true)).setRelativeRedirectsAllowed(!httpParams.getBooleanParameter("http.protocol.reject-relative-redirect", false)).build();
    }
}
