package org.apache.http.impl.conn;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.util.*;
import org.apache.http.conn.*;

@Immutable
public class DefaultSchemePortResolver implements SchemePortResolver
{
    public static final DefaultSchemePortResolver INSTANCE;
    
    public int resolve(final HttpHost httpHost) throws UnsupportedSchemeException {
        Args.notNull(httpHost, "HTTP host");
        final int port = httpHost.getPort();
        if (port > 0) {
            return port;
        }
        final String schemeName = httpHost.getSchemeName();
        if (schemeName.equalsIgnoreCase("http")) {
            return 80;
        }
        if (schemeName.equalsIgnoreCase("https")) {
            return 443;
        }
        throw new UnsupportedSchemeException(schemeName + " protocol is not supported");
    }
    
    static {
        INSTANCE = new DefaultSchemePortResolver();
    }
}
