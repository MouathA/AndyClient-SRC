package org.apache.http.auth;

import org.apache.http.annotation.*;

@Immutable
public final class AUTH
{
    public static final String WWW_AUTH = "WWW-Authenticate";
    public static final String WWW_AUTH_RESP = "Authorization";
    public static final String PROXY_AUTH = "Proxy-Authenticate";
    public static final String PROXY_AUTH_RESP = "Proxy-Authorization";
    
    private AUTH() {
    }
}
