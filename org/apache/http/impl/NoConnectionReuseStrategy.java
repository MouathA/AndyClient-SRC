package org.apache.http.impl;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.protocol.*;

@Immutable
public class NoConnectionReuseStrategy implements ConnectionReuseStrategy
{
    public static final NoConnectionReuseStrategy INSTANCE;
    
    public boolean keepAlive(final HttpResponse httpResponse, final HttpContext httpContext) {
        return false;
    }
    
    static {
        INSTANCE = new NoConnectionReuseStrategy();
    }
}
