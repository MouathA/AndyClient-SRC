package org.apache.http.impl.client;

import org.apache.http.client.*;
import org.apache.http.annotation.*;
import org.apache.http.protocol.*;

@Immutable
public class NoopUserTokenHandler implements UserTokenHandler
{
    public static final NoopUserTokenHandler INSTANCE;
    
    public Object getUserToken(final HttpContext httpContext) {
        return null;
    }
    
    static {
        INSTANCE = new NoopUserTokenHandler();
    }
}
