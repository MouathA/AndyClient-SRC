package org.apache.http.client.params;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.params.*;

@Deprecated
@Immutable
public class HttpClientParams
{
    private HttpClientParams() {
    }
    
    public static boolean isRedirecting(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.protocol.handle-redirects", true);
    }
    
    public static void setRedirecting(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.protocol.handle-redirects", b);
    }
    
    public static boolean isAuthenticating(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.protocol.handle-authentication", true);
    }
    
    public static void setAuthenticating(final HttpParams httpParams, final boolean b) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.protocol.handle-authentication", b);
    }
    
    public static String getCookiePolicy(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        final String s = (String)httpParams.getParameter("http.protocol.cookie-policy");
        if (s == null) {
            return "best-match";
        }
        return s;
    }
    
    public static void setCookiePolicy(final HttpParams httpParams, final String s) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.cookie-policy", s);
    }
    
    public static void setConnectionManagerTimeout(final HttpParams httpParams, final long n) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setLongParameter("http.conn-manager.timeout", n);
    }
    
    public static long getConnectionManagerTimeout(final HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        final Long n = (Long)httpParams.getParameter("http.conn-manager.timeout");
        if (n != null) {
            return n;
        }
        return HttpConnectionParams.getConnectionTimeout(httpParams);
    }
}
