package org.apache.http.client.config;

import org.apache.http.annotation.*;

@Immutable
public final class CookieSpecs
{
    public static final String BROWSER_COMPATIBILITY = "compatibility";
    public static final String NETSCAPE = "netscape";
    public static final String STANDARD = "standard";
    public static final String BEST_MATCH = "best-match";
    public static final String IGNORE_COOKIES = "ignoreCookies";
    
    private CookieSpecs() {
    }
}
